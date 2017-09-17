import java.io.*;
import java.util.*;

public class Leaky{
	
		public static int que_size,que_data,trans_size,time,data_sec[],data_sent[],data_rec[],data_dropped[];

		public static void input()
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the size of the queue");
			que_size = sc.nextInt();
			System.out.println("Enter the transmission time");
			time = sc.nextInt();
			System.out.println("Enter the size of transmission medium");
			trans_size = sc.nextInt();
			data_sec = new int[time];
			for(int i=0;i<time;i++)
			{
				System.out.println("Enter data in " + i + " sec");
				data_sec[i] = sc.nextInt();
			}
			data_rec = new int[time];
			data_dropped = new int[time];
			for(int i=0;i<time;i++)
			{
				data_rec[i] = 0;
				data_dropped[i] = 0;	
			}
		}

		public static void calc()
		{
			que_data = 0;
			for(int i=0;i<time;i++)
			{
				que_data+=data_sec[i];
				if(que_data <= trans_size)
				{
					data_rec[i] = que_data;
					data_dropped[i]=0;
					que_data = 0;
				}
				else if(que_data <= que_size)
				{
					data_rec[i]= trans_size;
					data_dropped[i] = 0;
					que_data -= trans_size;
				}
				else
				{
					data_rec[i] = trans_size;	
					data_dropped[i] = que_data-que_size;
					que_data = que_size-trans_size;
				}
			}
		}
		
		public static void display()
		{
			System.out.println("Time\tData Sent\tData Received\tData Dropped");
			for(int i=0;i<time;i++)
			{
				System.out.println(i+"\t\t"+data_sec[i]+"\t\t"+data_rec[i]+"\t\t"+data_dropped[i]);
			}
		}

		public static void main(String[] args)
		{
			input();
			calc();
			display();
		}
	}













