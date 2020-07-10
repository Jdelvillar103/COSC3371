import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
// BEGIN SOLUTION
// please import only standard libraries and make sure that your code compiles and runs without unhandled exceptions 
// END SOLUTION
 
public class HW2 {    
  static void P1() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher1.bmp"));
    
    // BEGIN SOLUTION
    byte[] iv = new byte[16];
    byte[] key = new byte[]{(byte) 1, (byte) 2, (byte) 3,(byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16};
    SecretKeySpec bob = new SecretKeySpec(key,"AES");
    IvParameterSpec bill = new IvParameterSpec(iv);
    Cipher blob = Cipher.getInstance("AES/CBC/ISO10126Padding");
    blob.init(Cipher.DECRYPT_MODE,bob,bill);
    cipherBMP = blob.update(cipherBMP);
    byte[] plainBMP = cipherBMP;    
    // END SOLUTION
    
    Files.write(Paths.get("plain1.bmp"), plainBMP);
  }

  static void P2() throws Exception {
    byte[] cipher = Files.readAllBytes(Paths.get("cipher2.bin"));
    // BEGIN SOLUTION
    byte[] modifiedCipher = new byte[48];
    byte[] iv = new byte[16];
    byte[] key = new byte[]{(byte) 1, (byte) 2, (byte) 3,(byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16};
    SecretKeySpec bob = new SecretKeySpec(key,"AES");
    IvParameterSpec bill = new IvParameterSpec(iv);
    Cipher blob = Cipher.getInstance("AES/CBC/NoPadding");
    
    int j=0;
    int j2=16;
    int j3=32;
    
    for(int i=0;i<16;i++)
    {
      modifiedCipher[i] = cipher[j3];
      //ctr++;
      j3++;
     // modifiedCipher[16] = cipher[0];
    }  
    for(int i=16;i<32;i++)
    {
      modifiedCipher[i] = cipher[j2];
     // ctr2++;
      j2++;
     // modifiedCipher[16] = cipher[0];
    }    
    for(int i=32;i<48;i++)
    {
      modifiedCipher[i] = cipher[j];
     // ctr3++;
      j++;
     // modifiedCipher[16] = cipher[0];
    }      
    blob.init(Cipher.DECRYPT_MODE,bob,bill);
    modifiedCipher = blob.update(modifiedCipher);
    byte[] plain = modifiedCipher;
    // END SOLUTION
    
    Files.write(Paths.get("plain2.txt"), plain);
  }

  static void P3() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher3.bmp"));
    byte[] otherBMP = Files.readAllBytes(Paths.get("plain1.bmp"));
    
    // BEGIN SOLUTION
    byte[] modifiedBMP = cipherBMP;  
    for(int i=0;i<5000;i++)
      {modifiedBMP[i]=otherBMP[i];}
    byte[] key = new byte[]{(byte) 1, (byte) 2, (byte) 3,(byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16};
    SecretKeySpec bob = new SecretKeySpec(key,"AES");
    Cipher blob = Cipher.getInstance("AES/ECB/NoPadding");
    blob.init(Cipher.DECRYPT_MODE,bob); 
    //cipherBMP = blob.doFinal(cipherBMP);
    blob.doFinal(modifiedBMP);
    //modifiedBMP = blob.doFinal(modifiedBMP);
    // END SOLUTION
    
    Files.write(Paths.get("cipher3_modified.bmp"), modifiedBMP);
  }

  static void P4() throws Exception {
    byte[] plainA = Files.readAllBytes(Paths.get("plain4A.txt"));
    byte[] cipherA = Files.readAllBytes(Paths.get("cipher4A.bin"));
    byte[] cipherB = Files.readAllBytes(Paths.get("cipher4B.bin"));
    
    // BEGIN SOLUTION
    byte[] plainB = cipherB;
    for(int i=0;i<plainB.length;i++)
    {
      plainB[i] = (byte) ((int)cipherA[i]^(int)cipherB[i]);
    }
    byte[] iv = new byte[16];
    for(int i=0;i<plainB.length;i++)
    {
      plainB[i] = (byte) ((int)plainA[i]^(int)plainB[i]);
    }
    IvParameterSpec bill = new IvParameterSpec(iv);
    byte[] key = new byte[]{(byte) 1, (byte) 2, (byte) 3,(byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16};
    SecretKeySpec bob = new SecretKeySpec(key,"AES");
    Cipher blob = Cipher.getInstance("AES/CTR/NoPadding");
    blob.init(Cipher.DECRYPT_MODE,bob,bill); 
    blob.doFinal(plainB);
    // END SOLUTION
    
    Files.write(Paths.get("plain4B.txt"), plainB);
  }

  static void P5() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher5.bmp"));
    
    // BEGIN SOLUTION
    boolean ploop = false;
    byte[] otherBMP = Files.readAllBytes(Paths.get("plain1.bmp"));
    byte[] plainBMP=cipherBMP;
    /*for(int i=0;i<5000;i++)
      {plainBMP[i]=otherBMP[i];}*/
    byte[] key = new byte[] {   0,   0,    0,   0, 
                                0,   0,    0,   0,
                                0,   0,    0,   0,
                                0,   0,    0,   0 }; 
    byte[] key2= new byte[16];
    //System.out.println(key);
    for(int i=0;i<100;i++)
    {
      key[0]=(byte)i;
      for(int j=0;j<13;j++)
      {
        key[1]=(byte)j;
        for(int x=0;x<32;x++)
        {
          key[2]=(byte)x;
          
                      
            byte[] iv = new byte[16];
            plainBMP=cipherBMP;
            //System.out.println(key);
            SecretKeySpec bob = new SecretKeySpec(key,"AES");
            IvParameterSpec bill = new IvParameterSpec(iv);
            Cipher blob = Cipher.getInstance("AES/CBC/ISO10126Padding");
            try{
            blob.init(Cipher.DECRYPT_MODE,bob,bill);
            blob.doFinal(plainBMP); 
            
            if(plainBMP[0]==otherBMP[0]&&plainBMP[1]==otherBMP[1]&&plainBMP[2]==otherBMP[2]&&plainBMP[3]==otherBMP[3]&&plainBMP[4]==otherBMP[4]&&plainBMP[5]==otherBMP[5])
            {ploop=true;
              key2=key;
              System.out.println("I GOT THE KEY");
              break;
            }
            else{
              //System.out.println("WRONG KEY");
            throw new BadPaddingException();
            }
          }
          catch (BadPaddingException e) {
            
           }
           if(ploop)
           {
            
            break;
           }
        }
        if(ploop)
            break;
      }  
      if(ploop)
      break;                
    }                            
    
      
      // decryption might throw a BadPaddingException!
      plainBMP = cipherBMP;
      byte[] iv = new byte[16];
      SecretKeySpec bob = new SecretKeySpec(key2,"AES");
      IvParameterSpec bill = new IvParameterSpec(iv);
      Cipher blob = Cipher.getInstance("AES/CBC/ISO10126Padding");
      blob.init(Cipher.DECRYPT_MODE,bob,bill);
      blob.doFinal(plainBMP); 
      /*System.out.println(key2);
      System.out.println(key);*/
    // END SOLUTION
    
    Files.write(Paths.get("plain5.bmp"), plainBMP);
  }

  public static void main(String [] args) {
    try {  
      P1();
      P2();
      P3();
      P4();
      P5();//couldn't get to work
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
