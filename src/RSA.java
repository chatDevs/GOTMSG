import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * An implementation of the RSA algorithm
 * 
 * @author Georg Dorndorf
 * @version 29.04.2015
 **/
public class RSA{
  private BigInteger n, d, e;
  private int len = 2048;

  /**
   *Creates an instance, that can encrypt with the given publickey
   **/
  public RSA(BigInteger n, BigInteger e){
    this.n = n;
    this.e = e;
  }
  
  /**
   *Creates an instance, that can decrypt and encrypt with the specified modules 
   **/
  public RSA (BigInteger n, BigInteger e, BigInteger d){
    this.n = n;
    this.e = e;
    this.d = d;
  }
  
  /**
   *Creates an instance with a new private and public key with the specified length
   **/
  public RSA(int len){
    this.len = len;
  
    SecureRandom random = new SecureRandom();
    BigInteger p = new BigInteger(len/2, 100, random);
    BigInteger q = new BigInteger(len/2, 100, random);
    
    n = q.multiply(p);
    
    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    
    e = new BigInteger("3");
    
    while (m.gcd(e).intValue() > 1) {
      e = e.add(new BigInteger("2"));
    }
    d = e.modInverse(m);
  }
  
  /**
   * encrypts 
   **/
  public synchronized byte[] encrypt(byte[] enc){
    return (new BigInteger(enc)).modPow(e, n).toByteArray();
  }
  
  /**
   * decrypts
   **/
  public synchronized byte[] decrypt(byte[] dec){
    return (new BigInteger(dec)).modPow(d, n).toByteArray();
  }
  
  /**
   * @return the modulus
   **/
  public BigInteger getN(){
    return n;
  }
  
  /**
   * @return public module
   **/
  public BigInteger getE(){
    return e;
  }
  
  /**
   * Use with care
   * @return the privatekey
   **/
  public BigInteger getD(){
    return d;
  }
  
  public static void main(String[] args){
    RSA rsa = new RSA(128);
    String test = "Hallo Welt! Hallo Adrian!";
    byte[] t = test.getBytes();
    byte[] d = rsa.encrypt(t);
    System.out.println(new String(d));
    System.out.println(new String(rsa.decrypt(d)));
  }
}