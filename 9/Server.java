import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileInputStream; 
import java.net.ServerSocket;
 import java.net.Socket; 
import java.util.Scanner; 

class Server 
{ 
	public static void main(String args[])throws Exception 	
	{ 
		String filename;
 		System.out.println("Enter File Name: "); 
		Scanner sc=new Scanner(System.in);
 		filename=sc.nextLine(); 
		sc.close(); 
		while(true) 
		{ 
			//create server socket on port 5000 
			ServerSocket ss=new ServerSocket(5000); 
			System.out.println ("Waiting for request"); 
			Socket s=ss.accept(); 
			System.out.println ("Connected With "+s.getInetAddress().toString()); 
			DataInputStream din=new DataInputStream(s.getInputStream()); 
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
 			try 
			{ 
				String str=""; str=din.readUTF(); 
				System.out.println("Send start....Ok"); 
				if(!str.equals("stop"))
				{
 					System.out.println("Sending File: "+filename); 
					dout.writeUTF(filename); 
					dout.flush(); 
					File f=new File(filename); 
					FileInputStream fin=new FileInputStream(f); 
					long sz=(int) f.length(); 
					byte b[]=new byte [1024]; 
					int read; 
					dout.writeUTF(Long.toString(sz));
					dout.flush(); 
					System.out.println ("Size: "+sz); 
					System.out.println ("Buf size: "+ss.getReceiveBufferSize()); 

					while((read = fin.read(b)) != -1) 
					{ 
						dout.write(b, 0, read); dout.flush(); 
					} 
					fin.close(); 
					System.out.println("..ok"); 
					dout.flush(); 
				} 
				dout.writeUTF("stop"); 
				System.out.println("Send Complete"); 
				dout.flush(); 
			} 
			catch(Exception e) 
			{ 
				e.printStackTrace(); System.out.println("An error occured"); 
			} 
			din.close(); 
			s.close(); 
			ss.close(); 
		} 
	} 
} 
