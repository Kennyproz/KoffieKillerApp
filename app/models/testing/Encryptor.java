package models.testing;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

    byte[] keyBytes, ivBytes, input;
    SecretKeySpec key;
    IvParameterSpec ivSpec;
    Cipher cipher;
    int enc_len;


    public Encryptor() {
        initialize();
    }

    private void initialize(){
        keyBytes = "SecretKey".getBytes();
        ivBytes = "PublicKey".getBytes();
        key = new SecretKeySpec(keyBytes,"DES");
        ivSpec = new IvParameterSpec(ivBytes);
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void ecrypt(String input) {
        this.input = input.getBytes();
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encrypted = new byte[cipher.getOutputSize(this.input.length)];
            enc_len = cipher.update(this.input, 0, this.input.length, encrypted, 0);
            enc_len += cipher.doFinal(encrypted, enc_len);
            System.out.println("Encrypted text = " + encrypted);
          //  return encrypted.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void decrypt(String input){
        byte[] encrypted = input.getBytes();
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
            int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
            dec_len += cipher.doFinal(decrypted, dec_len);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
