import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class RSA {
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger privateKey;
   private BigInteger publicKey;
   private BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   RSA(int N) {
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
      System.out.println("The first random number is " + p);
      System.out.println("The second random number is " + q);
      System.out.println("The value of Phi is " + phi);
      modulus    = p.multiply(q);
      publicKey  = new BigInteger("7");     // common value in practice = 2^16 + 1. We input public key value
      privateKey = publicKey.modInverse(phi); // Finding private key using modular inverse
   }


   BigInteger encrypt(BigInteger message) {
      return message.modPow(publicKey, modulus);
   }
   
    BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus);
   }

   public String toString() {
      String s = "";
      s += "The Public key is = " + publicKey  + "\n";
      s += "The Private key is = " + privateKey + "\n";
      s += "The Modulus is  = " + modulus;
      return s;
   }

   public static void main(String[] args) {
      int N = Integer.parseInt(args[0]);
      RSA key = new RSA(N);
      System.out.println(key);

      // create random message, encrypt and decrypt
      BigInteger message = new BigInteger(N-1, random);
      BigInteger encrypt = key.encrypt(message);
      BigInteger decrypt = key.decrypt(encrypt);
      System.out.println("The Random message generated is = " + message);
      System.out.println("The Encrypted key is = " + encrypt);
      System.out.println("The Decrypted key is = " + decrypt);
   }
}
