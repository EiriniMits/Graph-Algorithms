/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mitsopoulou Eirini
 */
public class Kruskal {
    static int V;

    public int[][] findMST(int[][] weight) {
        int[][] mst = new int[V][V];
        int[] visited = new int[V];
        int min,temp1=0, temp2=0,u = 0,v = 0,numEdges = 1;
        for(int i=0; i<V; i++){
            visited[i] = 0;
            /*if i and j are not connected with an edge, we need to set a huge value in their weight
            to make sure it won't be included in the mst */
            for(int j=0; j<V; j++){
                if(weight[i][j]==0)
                    weight[i][j] = 100000;
            }
        }
	    while (numEdges < V) {
		   min = 100000;
           //find the minimum weight of all edges
		   for (int i = 0; i < V; i++) {
			 for (int j = 0; j < V; j++) {
				if (weight[i][j] < min) {
					min = weight[i][j];
					temp1=u = i;//save the connected vertices
					temp2=v = j;
				}
			 }
		  }
		  while (visited[u] != 0)
			 u = visited[u];
		  while (visited[v] != 0)
			 v = visited[v];
          // Union Find
		  if (u != v) {//if it's not create a cycle add the edge in the tree
			 numEdges++;
			 visited[u] = v;
             mst[temp1][temp2]=mst[temp2][temp1]=min;
         }
		 weight[temp1][temp2] = weight[temp2][temp1] = 100000;
      }
      return mst;
    }

    public static void main(String[] args) throws IOException {
        
       Kruskal h = new Kruskal();
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
      int[][] mst=h.findMST(weight);
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
