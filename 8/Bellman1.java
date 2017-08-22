import java.util.*;
import java.io.*;

public class Bellman1 {
	private int distance[];
	private int n;
	public static final int MAX=999;

	public Bellman1(int n)
	{
	this.n = n;
	distance = new int[n+1];
	}

	public void BellmanFord(int src,int a[][])
	{
	
	for(int node=1;node<=n;node++)
	{
		distance[node] = MAX;
	}
		distance[src] = 0;

		for(int node=1;node <= n;node++)
		{
			for(int source=1;source<=n;source++)
			{
				for(int dest=1;dest<=n;dest++)
				{
					if(a[source][dest] != MAX)
					{
						if(distance[dest]>distance[source]+a[source][dest])
						{
							distance[dest] = distance[source]+a[source][dest];
						}
					}
				}
			}
		}


	for(int source=1;source<=n;source++)
	{
		for(int dest=1;dest<=n;dest++)
		{
			if(a[source][dest] != MAX)
			{
				if(distance[dest]>distance[source] + a[source][dest])
				{
				   System.out.println("The Graph contains negative edge cycle");
				}
			}
		}
	}

	
	for(int vertex=1;vertex<=n;vertex++)
	{
	System.out.println("Distance of source " + src + " to " + vertex + " is "+ distance[vertex]);
	}

	
      }


	public static void main(String[] args)
	{
	int n = 0;
	int src;
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the no of vertices");
	n = sc.nextInt();
	int a[][] = new int[n+1][n+1];
	System.out.println("Enter the adjacency matrix");
	for(int source=1;source<=n;source++)
	{
		for(int dest=1;dest<=n;dest++)
		{
			a[source][dest] = sc.nextInt();
	

			if(source==dest)
			{
				a[source][dest] = 0;
				continue;
			}
	
			if(a[source][dest] == 0)
			{
				a[source][dest] = MAX;
			}
		}
	}

	System.out.println("Enter the source vertex");
	src = sc.nextInt();
	Bellman1 bell = new Bellman1(n);
	bell.BellmanFord(src,a);
	}
}
	
