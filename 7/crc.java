import java.util.*;
import java.lang.*;

class crc{

	public static String msg, gp;
	//msg stores Dataword
	//gp stores generated polynomial, i.e., divisor

	public static String generateCodeword(){     // Sender side
		String dataword = msg;	//Duplication of Message
		int len_msg = msg.length();
		int len_gp = gp.length();

		//Append with Zeros
		for(int i = 0; i < len_gp - 1; i++)
			dataword += "0";

		int len_dataword = dataword.length();

		//Generation of Codeword
		int k = 0;
		while(k < len_msg){

			//Point to the next bit '1'
			while(k < len_msg && dataword.charAt(k) == '0')
				k++;

			if(k < len_msg){

				//X-OR Operation
				int j = 0;
				while(j < len_gp){
					if(dataword.charAt(k + j) == gp.charAt(j)){
						//Change bit to 0
						dataword = dataword.substring(0, k + j) + "0" + dataword.substring(k + j + 1, len_dataword);
					}
					else{
						//Change bit to 1
						dataword = dataword.substring(0, k + j) + "1" + dataword.substring(k + j + 1, len_dataword);
					}
					j++;
				}
			}
		}
		return (msg + dataword.substring(len_msg, len_dataword));
	}


	//msg stores Received Codeword
	//gp stores the divisor

	public static void checkCodeword(){    // Receiver Side
		String codeword = msg;	//Duplication of Received Coedword
		int len_msg = msg.length();
		int len_gp = gp.length();
		int len_codeword = codeword.length();

		//Data verifictaion
		int k = 0;
		while(k <= len_msg - len_gp){

			//Point to the next bit '1'
			while(k <= len_msg - len_gp && codeword.charAt(k) == '0')
				k++;

			if(k <= len_msg - len_gp){

				//X-OR Operation
				int j = 0;
				while(j < len_gp){
					if(codeword.charAt(k + j) == gp.charAt(j)){
						//set bit to 0
						codeword = codeword.substring(0, k + j) + "0" + codeword.substring(k + j + 1, len_codeword);
					}
					else{
						//set bit to 1
						codeword = codeword.substring(0, k + j) + "1" + codeword.substring(k + j + 1, len_codeword);
					}
					j++;
				}
			}
		}

		//Declare comparison string
		String checker = "";
		for(int i = 0; i < len_gp - 1; i++)
			checker += "0";

		//Check the Remainder
		if(codeword.substring(len_msg - len_gp + 1, len_codeword).equals(checker)){
			//No Error
			System.out.println("The data received at the receiver is uncorrupted\n The dataword is " + msg.substring(0, len_msg - len_gp + 1));
		}
		else{
			//Error Detected
			System.out.println("The data received at the receiver is corrupted data");
		}

	
	}
	




	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Dataword (or) the message: ");
		msg = sc.next();
		System.out.println("Enter the Generated Polynomial: ");
		gp = sc.next();
		System.out.println("Generated Codeword is: " + generateCodeword());
		System.out.println("Enter the Generated Codeword: ");
		msg = sc.next();
		System.out.println("Enter the Divisor Polynomial (or) Generator Polynomial: ");
		gp = sc.next();
		checkCodeword();

	}
}

