import java.net.*;
import java.util.*;
class sock {
    public static int serverPort = 8000;
    public static int clientPort = 5000;
    public static int buffer_size = 1024;
    public static DatagramSocket ds;
    public static byte buffer[] = new byte[buffer_size];

    public static void TheServer() throws Exception {
		Scanner s=new Scanner(System.in);
		System.out.println("Server Created..");
        while (true) {
            String c = s.nextLine();
			ds.send(new DatagramPacket(c.getBytes(), c.length(), InetAddress.getLocalHost(), clientPort));
        	     }
    						    }

    public static void TheClient() throws Exception {
        while (true) {
            //System.out.println(".");
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            ds.receive(p);
            System.out.println(new String(p.getData(), 0, p.getLength()));
        	     }
    						    }

    public static void main(String args[]) throws Exception {
        if (args.length == 1) {
            ds = new DatagramSocket(serverPort);
            TheServer();

        		      } 
	else {
			System.out.println("Client Created..");
            ds = new DatagramSocket(clientPort);
            TheClient();
       }
    							    }
	}
