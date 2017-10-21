package hamiltoniancycle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mitsopoulou Eirini
 */
public class HamiltonianCycle {

    static int V;
    int path[];
 

    int check(int ver, int graph[][], int path[], int k)
    {
        // if this vertex is not connected to the previous added vertex
        if (graph[path[k - 1]][ver] == 0)
            return 0;
        // if this vertex has already been included to path
        for (int i = 0; i < k; i++){
            if (path[i] == ver)
                return 0;
        }
        return 1;
    } 

    int builtCycle(int graph[][], int path[], int k)
    {
        //If all vertices are included in cycle and there is a connection between last and first who added in the path return 1
        if (k == V)
        {
            if (graph[path[k - 1]][path[0]]== 1)
                return 1;
            else
                return 0;
        }
        // Try different vertices to continue building the path
        for (int ver = 1; ver < V; ver++)
        {
            //if it's ok to add this vertex to cycle
            if (check(ver, graph, path, k)==1)
            {
                path[k] = ver;
                //call it again to built the rest of the path 
                if (builtCycle(graph,path,k+1) == 1)
                    return 1;
                // If vertex doesn't lead to any solution, remove it
                path[k] = -1;
            }
        }
        // If no vertex can be added to cycle return 0
        return 0;
    }
 
    int hCycle(int graph[][]) throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            path = new int[V];
            for (int i = 0; i < V; i++)
                path[i] = -1;
            //start from vertex 0
            path[0] = 0;
            //if there is no cycle
            if (builtCycle(graph, path, 1) == 0)
            {
                bw.write("Solution does not exist");
                return 0;
            }        
            //prints the path
            for (int i = 0; i < V; i++)
                bw.write(path[i] + " ");          
            //print the first vertex again
            bw.write(path[0]+ "");
        }
        return 1;
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
       HamiltonianCycle h = new HamiltonianCycle();

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
      h.hCycle(graph);
    }
}

   
