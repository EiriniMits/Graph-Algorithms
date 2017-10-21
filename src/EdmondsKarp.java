/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/**
 *
 * @author Mitsopoulou Eirini
 */
public class EdmondsKarp {

    private double[] minFlow;
    private double[][] resCap;
    private int[] parent;
    static int V;
 
 
    int maxFlow(int[][] graph,int source,int sink) 
    {
        minFlow = new double[V]; // Minimal flow
        resCap = new double[V][V]; // Residual capacity
        parent = new int[V]; // Parent node in BFS
        int maxFlow=0;
 
	    for (int i = 0; i < V; i++)
		   for (int j = 0; j < V; j++)
			resCap[i][j] = graph[i][j];
        //if there is a path from source to sink in residual graph
        while (BFS(source,sink)==1) {
            maxFlow += minFlow[sink];
            int v = sink, u;
            while (v != source) {
                u = parent[v];               
                // update residual capacities of the edges
                resCap[u][v] -= minFlow[sink];
                resCap[v][u] += minFlow[sink];
                v = u;
            }
        }
        return maxFlow;
    }
    
    //Returns 1 if there is a path from source to sink
    int BFS(int source,int sink) 
    {
	int first=0, last=0; 
        int[] queue=new int[V]; 
        int[] mark = new int[V];            
                
        for (int i = 0; i < V; i++) {
                mark[i] = 0; // Mark all vertices as not visited 
                minFlow[i] = 10000000;
        }

        queue[last++] = source; //enqueue source vertex
        mark[source] = 1; //mark source vertex as visited
        //BFS Loop
        while (first != last) { // While queue is not empty.
              int v = queue[first++];
              for (int u = 0; u < V; u++){
                    if (mark[u] == 0 && resCap[v][u] > 0) {
                            minFlow[u] = Math.min(minFlow[v],resCap[v][u]);
                            parent[u] = v;
                            mark[u] = 1;
                            if (u == sink) //If we reach sink starting from source, then return 1
                                return 1;
                            queue[last++] = u;
                    }
              }
        }
        return 0; //else return 0
    }
 

    public static void main (String[] args) throws java.lang.Exception
    {
        int[][] graph;
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            //reads the first line of the txt file (n m)
            String[] parts = line.split(" ");
            V= Integer.parseInt(parts[0]);
            graph = new int[V][V];
            int j=0;
            //reads the rest lines of the txt file and create the graph
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                for(int i=0;i<V;i++){
                    graph[j][i]= Integer.parseInt(parts2[i]);
                }
                j++;
            }
        }
        EdmondsKarp m = new EdmondsKarp();
        int max_flow=m.maxFlow(graph,0,V-1);
        //prints the max flow
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) { 
                 bw.write("Max flow: " + max_flow);    
        }
    }
    
}
