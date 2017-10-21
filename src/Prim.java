/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prim;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Mitsopoulou Eirini
 */
public class Prim {
static int V;

    public int[][] findMST(int[][] weight) {
        int[][] mst = new int[V][V];
        int visited[] = new int [V];//mark the vertices that have been added in the tree
        int tempweight[] = new int[V];
        int u[] = new int[V];
        int current, total, min;
        for (int i = 0; i < V; i++) 
                tempweight[i] = 10000;
        current = 0;//start with vertex 0
        tempweight[current] = 0;
        total = 1;
        visited[current] = 1;
        while( total != V) {
                //find the connected with the current vertex with the min weight
                for (int i = 0; i < V; i++) {
                    if ( weight[current][i] != 0) {//if the vertices are connected with an edge
                        if( visited[i] == 0) { // if this vertex is not in the mst yet
                            if (tempweight[i] > weight[current][i]) {
                                    tempweight[i] = weight[current][i];
                                    u[i] = current;
                            }
                        }
                    }
                }
                min=10000;
                //set the as current the vertex we choose above
                for (int i = 0 ; i < V; i++) {
                    if (visited[i] == 0) {
                        if (tempweight[i] < min) {
                            min = tempweight[i];
                            current = i;
                        }
                    }
                }
                visited[current]=1; //block from choosing again the same vertex
                total++;
        }
        for(int i=0;i<V;i++)
            mst[i][u[i]]=mst[u[i]][i]=tempweight[i];                          
        return mst;
    }

    public static void main(String[] args) throws IOException {
        
       Prim p = new Prim();
       int[][] weight;
       try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            //reads the first line of the txt file (n m)
            String[] parts = line.split(" ");
            V= Integer.parseInt(parts[0]);
            weight = new int[V][V];
            int j=0;
            //reads the rest lines of the txt file and create the weighted graph
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                for(int i=0;i<V;i++){
                    weight[j][i]= Integer.parseInt(parts2[i]);
                }
                j++;
            }
      }
      int[][] mst=p.findMST(weight);
      // print the matrix
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) { 
            for (int i = 0; i < V; i++){
                for(int j = 0; j < V; j++){
                     bw.write(mst[i][j] + " ");
                }
               bw.write('\n');
            }     
      }
    
   }   
}
