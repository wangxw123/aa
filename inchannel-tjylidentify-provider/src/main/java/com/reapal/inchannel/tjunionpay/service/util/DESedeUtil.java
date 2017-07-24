package com.reapal.inchannel.tjunionpay.service.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;


/**
 * 3DES加密解密工具类
 * @author William
 * @date 2013-04-19
 */
public class DESedeUtil {    
    private static final String Algorithm = "DESede";    
    
    
    /**
     * 加密
     * @param src 
     * @return 
     */
    public static byte[] encryptMode(byte[] src, String key, String encoding) {
        try {
             SecretKey deskey = new SecretKeySpec(build3DesKey(key, encoding), Algorithm); 
             Cipher c1 = Cipher.getInstance(Algorithm);    
             c1.init(Cipher.ENCRYPT_MODE, deskey);  
             return c1.doFinal(src);
         } catch (java.security.NoSuchAlgorithmException e1) {
             e1.printStackTrace();
         } catch (javax.crypto.NoSuchPaddingException e2) {
             e2.printStackTrace();
         } catch (Exception e3) {
             e3.printStackTrace();
         }
         return null;
     }


    /**
     * 解密
     * @param src
     * @return
     */
    public static byte[] decryptMode(byte[] src, String key, String encoding) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key, encoding), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
     }
    
    
    /**
     * 构建3DES秘钥
     * @param keyStr
     * @return 
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr, String encoding) throws UnsupportedEncodingException{
        byte[] key = new byte[24];   
        byte[] temp = keyStr.getBytes(encoding);  
        
        if(key.length > temp.length){
            System.arraycopy(temp, 0, key, 0, temp.length);
        }else{
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    } 
    
}
