package com.reapal.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

/**
 * 加解密工具包
 *
 * @author YaoFeng
 */
public class EncryptUtils {

    private static final String ALG_RSA = "RSA";
    private static final String ALG_DSA = "DSA";

    /** 算法常量：SHA1withRSA */
    private static final String ALGORITHM_SHA1RSA = "SHA1withRSA";

    /**
     * 公钥加密
     *
     * @param data 源数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, final PublicKey publicKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher(data, cipher, getBlockSize(publicKey) - 11);
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedData, final PrivateKey privateKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher(encryptedData, cipher, getBlockSize(privateKey));
    }

    /**
     * 使用私钥签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(final byte[] data, final PrivateKey privateKey) throws Exception {
        final Signature st = Signature.getInstance(ALGORITHM_SHA1RSA);
        st.initSign(privateKey);
        st.update(data);
        byte[] signed  = st.sign();

        return Base64.encodeBase64String(signed);
    }

    /**
     * 使用公钥验签
     *
     * @param data
     * @param signature
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(final byte[] data, final byte[] signature, final PublicKey publicKey) throws Exception {
        final Signature st = Signature.getInstance(ALGORITHM_SHA1RSA);
        st.initVerify(publicKey);
        st.update(data);
        return st.verify(signature);
    }



    /**
     * 私钥加密
     *
     * @param data 源数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    @Deprecated
    public static byte[] encrypt(byte[] data, final PrivateKey privateKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher(data, cipher, getBlockSize(privateKey) - 11);
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    @Deprecated
    public static byte[] decrypt(byte[] encryptedData, final PublicKey publicKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher(encryptedData, cipher, getBlockSize(publicKey));
    }

    private static byte[] cipher(byte[] data, Cipher cipher, int blockSize) throws Exception {
        final ByteArrayInputStream in = new ByteArrayInputStream(data);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] cache = new byte[blockSize];
        while (true) {
            final int r = in.read(cache);
            if (r < 0) {
                break;
            }
            final byte[] temp = cipher.doFinal(cache, 0, r);
            out.write(temp, 0, temp.length);
        }
        return out.toByteArray();
    }

    private static int getBlockSize(final Key key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String alg = key.getAlgorithm();
        final KeyFactory keyFactory = KeyFactory.getInstance(alg);
        if (key instanceof PublicKey) {
            final BigInteger prime;
            if (ALG_RSA.equals(alg)) {
                prime = keyFactory.getKeySpec(key, RSAPublicKeySpec.class).getModulus();
            } else if (ALG_DSA.equals(alg)) {
                prime = keyFactory.getKeySpec(key, DSAPublicKeySpec.class).getP();
            } else {
                throw new NoSuchAlgorithmException("不支持的解密算法：" + alg);
            }
            return prime.toString(2).length() / 8;
        } else if (key instanceof PrivateKey) {
            final BigInteger prime;
            if (ALG_RSA.equals(alg)) {
                prime = keyFactory.getKeySpec(key, RSAPrivateKeySpec.class).getModulus();
            } else if (ALG_DSA.equals(alg)) {
                prime = keyFactory.getKeySpec(key, DSAPrivateKeySpec.class).getP();
            } else {
                throw new NoSuchAlgorithmException("不支持的解密算法：" + alg);
            }
            return prime.toString(2).length() / 8;
        } else {
            throw new RuntimeException("不支持的密钥类型：" + key.getClass());
        }
    }
}
