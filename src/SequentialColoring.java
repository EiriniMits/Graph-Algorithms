/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequentialcoloring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Eirini Mitsopoulou
 */
public class SequentialColoring {

    static int V;   
    static LinkedList<Integer> graph[]; //Adjacency List
 
    SequentialColoring()
    {
        graph = new LinkedList[V];
        for (int i=0; i<V; ++i)
            graph[i] = new LinkedList();
    }

    int[] coloring()
    {
        int colors[] = new int[V];
        colors[0]  = 0; // Set the first color to first vertex
        for (int j = 1; j < V; j++)// Set the remaining V-1 vertices as uncolored
           colors[j] = -1; 
 
        // Array to store the used colors
        boolean used[] = new boolean[V];
        for (int c = 0; c < V; c++)
            used[c] = false;
 
        // Set colors to the remaining V-1 vertices
        for (int j = 1; j < V; j++)
        {
            Iterator<Integer> it = graph[j].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (colors[i] != -1) // find all the colored adjacent vertices and mark their colors as used
                    used[colors[i]] = true;
            }
            int c;
            for (c = 0; c < V; c++) // Find the first not used color
                if (used[c] == false) 
                    break;
            colors[j] = c; // Set the found color
            
            it = graph[j].iterator() ;
            while (it.hasNext())
            {// Set the values back to false for the next loop
                int i = it.next();
                if (colors[i] != -1)
                    used[colors[i]] = false;
            }
        }
        return colors;
    }
    
    public static void main(String[] args) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            //reads the first line of the txt file (n m)
            String[] parts = line.split(" ");
            V= Integer.parseInt(parts[0]);
            SequentialColoring g = new SequentialColoring();
            int j=0;

            //reads the rest lines of the txt file and create the graph LinkedList
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                for(int i=0;i<V;i++){
                    //if the 2 vertices are connected  
                    if(Integer.parseInt(parts2[i])!=0 ){
                        graph[j].add(i);
                        graph[i].add(j);
                     }
                }
                j++;
            }// print the results
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
