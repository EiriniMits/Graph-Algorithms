/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boruvka;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Mitsopoulou Eirini
 */
public class Boruvka {
    static int V;
    static int E;
    int numTrees=V,minTrees[]=new int[V];
    Edge edge[]=new Edge[E];
    Tree trees[]=new Tree[V];
    
    class Edge {
        int ver1;
        int ver2;
        int weight;
    }

    class Tree {
        int parent;
        int rank;
    }

    Boruvka() {
        for (int i = 0; i < E; ++i) {
            edge[i]=new Edge();
        }
        for (int i = 0; i < V; ++i)
        {
            trees[i]=new Tree();
            trees[i].parent = i;
            trees[i].rank = 0;
            minTrees[i] = -1;
        }
    }
    
    int find(int ver) {

        Tree tempTree=trees[ver];
        while(trees[ver].parent!=ver) {
            int parentVer=trees[ver].parent;
            ver=trees[parentVer].parent;
        }
        return tempTree.parent;
    }
    
    void union(int ver1,int ver2) {
        int xroot=find(ver1);
        int yroot=find(ver2);
        // connect the smaller rank tree under the root of higher rank tree
        if (trees[xroot].rank < trees[yroot].rank)
            trees[xroot].parent = yroot;
        else if (trees[xroot].rank > trees[yroot].rank)
            trees[yroot].parent = xroot;
        else // If ranks are the same make one as root and increase the ones rank
        {
            trees[yroot].parent = xroot;
            trees[xroot].rank++;
        }
    }

    int[][] boruvkaMST() {
        int[][] mst = new int[V][V];
        
        while (numTrees > 1)
        {
            // find the minimum weighted edge of every vertex
            for (int i=0; i<E; i++)
            {
                // Find trees of two corners of current edge
                int tree1 = find( edge[i].ver1);
                int tree2 = find(edge[i].ver2);
                // If two corners of current edge belong to the same tree ignore current edge
                if (tree1 == tree2)
                    continue;
                // check if current edge is closer to previous min weighted edges of tree1 and tree2
                else
                {
                    if (minTrees[tree1] == -1 ||
                            edge[minTrees[tree1]].weight > edge[i].weight)
                        minTrees[tree1] = i;

                    if (minTrees[tree2] == -1 ||
                            edge[minTrees[tree2]].weight > edge[i].weight)
                        minTrees[tree2] = i;
                }
            }
            // Add the above min weighted edges to mst
            for (int i=0; i<V; i++)
            {
                // Check if min edge already exists in the mst
                if (minTrees[i] != -1)
                {
                    int set1 = find( edge[minTrees[i]].ver1);
                    int set2 = find( edge[minTrees[i]].ver2);
                    if (set1 == set2)
                        continue;
                    mst[edge[minTrees[i]].ver1][edge[minTrees[i]].ver2]=edge[minTrees[i]].weight;
                    mst[edge[minTrees[i]].ver2][edge[minTrees[i]].ver1]=edge[minTrees[i]].weight;
                    // union of tree1 and tree2 and decrease number of trees
                    union(set1, set2);
                    numTrees--;
                }
            }            
            for (int i=0; i<V; i++)
            {
                minTrees[i]=-1;
            }
        }
        return mst;      
    }
    
    public static void main(String[] args) throws IOException {

       
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            //reads the first line of the txt file (n m)
            String[] parts = line.split(" ");
            V= Integer.parseInt(parts[0]);
            E=Integer.parseInt(parts[1]);
            Boruvka graph=new Boruvka();
            int j=0;
            int numEdges=0;
            //reads the rest lines of the txt file and create the weighted graph
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                for(int i=0;i<V;i++){
                    //if the 2 vertices are connected & belong to the left diagonal half of graph 
                    if(Integer.parseInt(parts2[i])!=0 && j<=i ){
                        graph.edge[numEdges].ver1 = j;
                        graph.edge[numEdges].ver2 = i;
                        graph.edge[numEdges].weight = Integer.parseInt(parts2[i]);
                        numEdges++;
                     }
                }
                j++;
            }// print the matrix
            int[][] mst = graph.boruvkaMST();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) { 
                 for (int i = 0; i < V; i++){
                     for(int k = 0; k < V; k++){
                          bw.write(mst[i][k] + " ");
                     }
                    bw.write('\n');
                 }     
            }
        }
    }
    
}
