/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brelaz;
import java.util.*;
import java.io.*;

/**
 *
 * @author Eirini Mitsopoulou
 */
public class Brelaz {
    static int V;   
    static LinkedList<Integer> graph[]; //Adjacency List  
    static int[] degree,degreeids,colDegree;
    
    Brelaz(){
        graph = new LinkedList[V];
        for (int i=0; i<V; ++i)
            graph[i] = new LinkedList();
        degree=new int[V]; //array with vertices degrees
        degreeids=new int[V];//save vertices ids after degree array sorting
        colDegree=new int[V]; //array with vertices color degrees
    }
    
    int[] coloring()
    {
        int colors[] = new int[V];
        for (int j = 0; j < V; j++)// Set the remaining vertices as uncolored
           colors[j] = -1;  
        boolean used[] = new boolean[V]; // Array to store the used colors
        for (int c = 0; c < V; c++)
            used[c] = false;

        int maxid=degreeids[0]; 
        colors[maxid]  = 0; // Set the first color to vertex with the highest degree
        colDegree[maxid]=degree[0];

        for (int j = 1; j < V; j++) // Set colors to the remaining V-1 vertices
        {
            Iterator<Integer> it = graph[maxid].iterator() ;
            it = graph[maxid].iterator() ;
            while (it.hasNext())
            {// Set the values back to false for the next loop
                int i = it.next();
                //if vertex is uncolored and adjacent to current verted increase the color degree by 1
                if (colors[i] == -1) 
                    colDegree[i]=colDegree[i]+1;
                else // Set the values back to false for the next loop
                    used[colors[i]] = false;
            }
            int maxdegree=-1,max=-1;       
            for(int i = 0; i < V; i++){//find the highest color degree of the uncolored vertices in the graph
                if ((colDegree[i]>max) && (colors[i] == -1))
                        max=colDegree[i];
            }
            for(int i = 0; i < V; i++){
                    //find the uncolored vertices degrees who equal the highest color degree
                if ((colDegree[i]==max) && (colors[i] == -1)){ 
                    if(degree[i]>maxdegree){ //from those get the one with the highest degree
                        maxdegree=degree[i];
                        maxid=i;
                    }
                }
            }              
            it = graph[maxid].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (colors[i] != -1)// find all the colored adjacent vertices and mark their colors as used
                    used[colors[i]] = true;
            }

            int c;
            for (c = 0; c < V; c++)
                 if (used[c] == false)  // Find the first not used color
                      break;
            colors[maxid] = c; // Set the found color
        }
        return colors;
    }

   //sort the vertices degrees and save their places in array (degreeids)
    void sort(){
        for (int j=1; j < V; j++) {         
            for (int i=0; i < V-j; i++) {       
                if (degree[i] < degree[i+1]) {                        
                    int temp = degree[i]; 
                    degree[i] = degree[i+1];  
                    degree[i+1] = temp;
                    int temp2 = degreeids[i]; 
                    degreeids[i] = degreeids[i+1];  
                    degreeids[i+1] = temp2;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
	try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            //reads the first line of the txt file (n m)
            String[] parts = line.split(" ");
            V= Integer.parseInt(parts[0]);
            Brelaz g = new Brelaz();
            int j=0,vDegree=0;

            //reads the rest lines of the txt file and create the weighted graph
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                for(int i=0;i<V;i++){
                    //if the 2 vertices are connected & belong to the left diagonal half of graph 
                    if(Integer.parseInt(parts2[i])!=0 && j<=i){
                        graph[j].add(i);
                        graph[i].add(j);
                        vDegree++;
                     }
                }
                degree[j]=vDegree;
                degreeids[j]=j;
                j++; vDegree=0;
            }// print the results
            g.sort();
            int colors[]=g.coloring();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) { 
                 for (int i = 0; i < V; i++){
                    bw.write("V" + (i+1) +" "+(colors[i]+1));
                    bw.write('\n');
                 }     
            }
        }
    }
    
}
