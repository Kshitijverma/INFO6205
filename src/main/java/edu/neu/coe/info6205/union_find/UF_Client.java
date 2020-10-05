package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_Client {
	
	public static int count(int n) {
		
		int no_of_connections = 0; 
		UF_HWQUPC uf_client = new UF_HWQUPC(n);
		Random random_conn = new Random();
		
		while (uf_client.components()!=1)   
		{
			int p = random_conn.nextInt(n);
			
			int q = random_conn.nextInt(n);
			
			if(!uf_client.connected(p,q)) {
				uf_client.union(p, q);
				no_of_connections++;
			}	
		}
		return no_of_connections;
	}
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		for(int i=0; i<10;i++) {
			
			int n = rand.nextInt(100000);
			System.out.println("No. of Objects(n):" + n + "  No. of Pairs(m):" + count(n));
		}
	}

}
