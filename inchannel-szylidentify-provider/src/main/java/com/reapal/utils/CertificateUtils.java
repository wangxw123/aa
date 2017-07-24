package com.reapal.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

/**
 * 数字签名/加密解密工具包
 *
 * @author IceWee
 * @version 1.0
 * @date 2012-4-26
 */
public class CertificateUtils {

    /**
     * Java密钥库(Java 密钥库，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "PKCS12";

    public static final String X509 = "X.509";

    /**
     * <p>
     * 根据密钥库获得私钥
     * </p>
     *
     * @param keyStore 密钥库输入流
     * @param alias 密钥库别名
     * @param password 密钥库密码
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(final InputStream keyStore, final String alias, String password)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
        final KeyStore keyStore0 = getKeyStore(keyStore, password);
        if (alias != null) {
            return (PrivateKey) keyStore0.getKey(alias, password.toCharArray());
        } else {
            final Enumeration<String> aliases = keyStore0.aliases();
            return (PrivateKey) keyStore0.getKey(aliases.nextElement(), password.toCharArray());
        }
    }

    /**
     * <p>
     * 根据证书获得公钥
     * </p>
     *
     * @param certificate 证书输入流
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(InputStream certificate) throws CertificateException {
        return getCertificate(certificate).getPublicKey();
    }

    /**
     * <p>
     * 获得密钥库
     * </p>
     *
     * @param keyStore 密钥库输入流
     * @param password 密钥库密码
     * @return
     * @throws Exception
     */
    private static KeyStore getKeyStore(final InputStream keyStore, String password)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        final KeyStore result = KeyStore.getInstance(KEY_STORE);
        result.load(keyStore, password.toCharArray());
        return result;
    }

    /**
     * <p>
     * 获得证书
     * </p>
     *
     * @param certificate 证书输入流
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(InputStream certificate) throws CertificateException {
        final CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        return certificateFactory.generateCertificate(certificate);
    }

    /**
     * <p>
     * 根据密钥库获得证书
     * </p>
     *
     * @param keyStore 密钥库输入流
     * @param alias        密钥库别名
     * @param password     密钥库密码
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(InputStream keyStore, String alias, String password) throws Exception {
        return getKeyStore(keyStore, password).getCertificate(alias);
    }

    /**
     * <p>
     * 校验证书当前是否有效
     * </p>
     *
     * @param certificate 证书
     * @return
     */
    public static boolean verifyCertificate(Certificate certificate) {
        return verifyCertificate(new Date(), certificate);
    }

    /**
     * <p>
     * 验证证书是否过期或无效
     * </p>
     *
     * @param date 日期
     * @param certificate 证书
     * @return
     */
    public static boolean verifyCertificate(Date date, Certificate certificate) {
        try {
            final X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <p>
     * 验证数字证书是在给定的日期是否有效
     * </p>
     *
     * @param date 日期
     * @param certificate 证书输入流
     * @return
     */
    public static boolean verifyCertificate(Date date, InputStream certificate) {
        try {
            return verifyCertificate(getCertificate(certificate));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <p>
     * 验证数字证书是在给定的日期是否有效
     * </p>
     *
     * @param keyStore 密钥库输入流
     * @param alias 密钥库别名
     * @param password 密钥库密码
     * @return
     */
    public static boolean verifyCertificate(Date date, InputStream keyStore, String alias, String password) {
        try {
            return verifyCertificate(date, getCertificate(keyStore, alias, password));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <p>
     * 验证数字证书当前是否有效
     * </p>
     *
     * @param keyStore 密钥库存储路径
     * @param alias 密钥库别名
     * @param password 密钥库密码
     * @return
     */
    public static boolean verifyCertificate(InputStream keyStore, String alias, String password) {
        return verifyCertificate(new Date(), keyStore, alias, password);
    }

    /**
     * <p>
     * 验证数字证书当前是否有效
     * </p>
     *
     * @param certificate 证书输入流
     * @return
     */
    public static boolean verifyCertificate(InputStream certificate) {
        return verifyCertificate(new Date(), certificate);
    }

    /**
     * 密钥转换成字符串
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.encodeBase64String(keyBytes);
        return s;
    }

}