/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galeshapley;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mitsopoulou Eirini
 */
public class GaleShapley {

    static int N;
    int[][] menPref;
    int[][] womenPref;
    
    /** Constructor
     * @param mp
     * @param wp **/
    public GaleShapley(int[][] mp, int[][] wp)
    {
        menPref = mp;
        womenPref = wp;
    }

    private int[][] findStableMarriage()
    {
        int[][]  marriage = new int[N][2];
        int[] menid = new int[N];
        int[] womenid = new int[N];
        int[] womenPartner= new int[N];
        boolean[] menPartner=new boolean[N];
        for( int k=0; k<N; k++)  {    
            menid[k] = k+1; //save men ids 
            womenid[k] = k+1; //save women ids 
            womenPartner[k] = -1; //women have no partners at first
        }
        int count = 0;
        while (count < N)
        {
            int u=0;
            for (u = 0; u < N; u++)
                if (!menPartner[u])
                    break;
 
            for (int i = 0; i < N && !menPartner[u]; i++)
            {
                int v=0,k=0;
                for (int j = 0; j < N; j++)
                    if (womenid[j] ==menPref[u][i])
                        v= j; k++;
                if(k==0)
                    v = -1;
                if (womenPartner[v] == -1) //if woman is not engaged
                {
                    womenPartner[v] = menid[u]; //the woman got engaged with a man
                    menPartner[u] = true;
                    count++;
                }
                else
                {
                    int currentPartner = womenPartner[v];
                    int preference=0;
                    for (int j = 0; j < N; j++) //find the best preference of woman
                    {
                        if (womenPref[v][j]==menid[u]){ //if woman prefers better this man they get engaged
                            preference = 1; break;} 
                        if (womenPref[v][j]==currentPartner){ //if woman prefers her current partner
                            preference = 0; break;}
                    }
                                          
                    if (preference == 1) //if woman prefers a new men partner
                    {
                        int bool =0;k=0;
                        womenPartner[v] = menid[u]; //woman being engaged with the new men partner
                        menPartner[u] = true; //men become engaged
                        for ( i = 0; i < N; i++)
                            if (menid[i]== currentPartner ) //we find the id of the men that the woman left behind
                                bool=i; k++;
                        if(k==0)
                            bool = -1;
                        menPartner[bool] = false; // this men becomes single again
                    }
                }
            }                     
        }
        int j = 0;
        for ( int i = 0; i < N; i++)
        {            //save the final couples 
                marriage[i][j] = womenPartner[i]; j++; //men
                marriage[i][j] = womenid[i]; j=0;        // woman 
              
        }
        return marriage;
    }
    
    public static void main(String[] args) throws IOException 
    {
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            line = in.readLine();
            N= Integer.parseInt(line);
            String[] m=new String[N];
            String[] w=new String[N];
            int[][] mp=new int[N][N];
            int[][] wp=new int[N][N];
            int j=0,k=0;
            while((line = in.readLine()) != null)
            {
                String[] parts2 = line.split(" ");
                if(j<N){
                    for(int i=0;i<N+1;i++){
                        //mens name 
                        if(i==0){
                            m[j]=parts2[i];
                        }
                        else{//mens preferencies
                            mp[j][i-1] =Integer.parseInt(parts2[i]);

                         }
                    }
                    j++;
                }
                else{
                    for(int i=0;i<N+1;i++){
                        //women name 
                        if(i==0){
                            w[k]=parts2[i];
                        }
                        else{//women preferencies
                            wp[k][i-1] =Integer.parseInt(parts2[i]);
                         }
                    }
                    k++;
                }
            }
            GaleShapley gs = new GaleShapley(mp, wp); 
            int[][] marriage = gs.findStableMarriage();
            int menids;
            int womenids;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) { 
                 for ( int i = 0; i < N; i++){           
                    menids=marriage[i][0];
                    womenids=marriage[i][1];
                    bw.write(m[menids-1]+" ");
                    bw.write(w[womenids-1]);
                    bw.write('\n');
                }
            }
        }

    }
}
