import java.math.BigInteger;
import java.security.SecureRandom;
import javax.net.ssl.SSLServerSocket;

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
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }
    
    public synchronized String encrypt(String msg){
        byte[] baseMSG = org.apache.commons.codec.binary.Base64.encodeBase64(msg.getBytes());
        BigInteger text = new BigInteger(baseMSG);
        BigInteger t = encrypt(text);
        return "" + t;
    }

    /**
     *
     * decrypts
     **/
    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
        //return new String(t.toByteArray());
    }
    
    public synchronized String decrypt(String msg){
        BigInteger dec = new BigInteger(msg);
        decrypt(dec);
        byte[] decrypted = org.apache.commons.codec.binary.Base64.decodeBase64(dec.toByteArray());
        return new String(decrypted);
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

    public void test(){
        System.out.println("N " + n.toString() + " E " + e.toString() + " D " + d.toString());
        
        String test = "abc12!2 34 ! df 324$234 SEC";
        
        byte[] baseText = org.apache.commons.codec.binary.Base64.encodeBase64(test.getBytes());
        System.out.println("Base Text: " + new String(baseText));
        
        BigInteger text = new BigInteger(baseText);
        System.out.println("Big int bas text: " + text);
       
        BigInteger t = encrypt(text);
        System.out.println("verschlüsselt big int: "+t);
        
        BigInteger ent = decrypt(t);
        System.out.println("base text  " + new String(ent.toByteArray()));
        byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(ent.toByteArray());
        System.out.println("big Int  dec " + dec);
        
        System.out.println("entschlüsselt: "+ new String(dec));
    }

}