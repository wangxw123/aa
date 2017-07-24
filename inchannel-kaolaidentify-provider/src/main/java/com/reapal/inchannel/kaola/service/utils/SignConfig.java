package com.reapal.inchannel.kaola.service.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;


public class SignConfig {

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("klConfig");

	public static String PRIVATE_KEY = resourceBundle.getString("privateKey");
	public static String LKL_PUBLIC_KEY = resourceBundle.getString("lklPublicKey");
	
	//MD5签名
	public static String MD5="MD5";
	
	//RSA签名
	public static String RSA="0001";
	
	// 签名方式，选择项：0001(RSA)、MD5
	public static String SIGNTYPE = "0001";
	
	/**
	 * MD5 key
	 */
	public static String KEY = "";

//	public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKWmOMF5zQBqc8xA41zq9BKHI8GUdZdJR8qP57mv8y2N7T/TD1YeNEx3UH7cSWpsZpdzV5BWKAhH7sTOWfhIU+EhrXrWbQWAndWcT2+ZDYlI0ctJ5Bo1gM0MEU01+g3mhOf70I9DJrYGvoVYV815m+F46bjq8qVEL06zZZLEKCTPAgMBAAECgYEAjZl3rrvFp/NXpWRadtVJaoUm5ZVYp8g2nEtDVJG5mFlYU1TCKWWMY0kjAC6ie1zKnfA1C+b6NYn36zhR5FE/kTSwUYT1P6INT4rD7JUEiwE8hi4MTvWIDCyqeUmb2H+abHpBo9VZymmh5wmwRTi1PgPQGTDq5uP519lgD00DzEECQQDZzHPSvgy0GztmD6Uip1mHDI9j7syIEVCEPtuIsNzH63GQdR5jLH7hZn0WRXEgrZa+f3gKZnFIew+lj6Ip1lPRAkEAwrQrwe6dcioJHdc0PsW/In5TOBTY+ppVKhrkJ6x9UOzZT/BYuXUFVJL8kiKGIK0wihOzMhHK57HjoN5fT5w2nwJBAJ5D4npmXgbWrxAYGFCZOQZYyy28DmZl5pNitdabZqPj5A8r/Bvm7oBOIGF5rp4nZh4htJIiJPmdax5MxHMQarECQH8yMPvqpJT2fSovcwQnL2ybVkZm6DEfLc/p7W81slBxyq38eBoAJtFPjQzy3Ojv+6vYntJw6Ttf7TMk0uMxTEUCQDw1ZXU5jiKoktSYjHjFL2EfqtfT9F8xTHVyaruKL+R67Z0OvxMNlM0j77ADUS/BAFlwF2bCsSkMT3PLvcNtpPk=";
//	public static String LKL_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+lfEZkoqFAr9EhV1NgAEkPkm6Um8bJ/YDAmlknvDlBL6pniQeX/NeqdBCz62pqkJZg4Lz04mMMnuCVAAkJqCD8TsukQin9igJfM07+L1fFmR5kIPkHegFDfqWChqBR0z9kpgnR16cuPz5PxvA81njk5ek1jLx/0ZMWBreujv3FQIDAQAB";

//	public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIcwwKmDoJEgtH1KdRbllfUvS/BiGpviROJXZ3C1ZtgP3kQMSh2c0UZm75Pac5jPhhc0HjwmGU1Cw7WNbvlpD6nCPMb6mi/i+VTlCQDKWWPuHoeZRaLaT+sB5mVn1T+LbE/ZBXFZzcwyzQO5N90sjTNNQGugFbl9LyCl1wcE1k29AgMBAAECgYAM8SvgjotWmWzt+azP7la2zDpW3u1xMu4pRPjQH22jDZHXrd2CjDq0pLIW03jxjhxmKEUkuPj/Hn8h6FJRai9nivKY0HY8V/1n9ydC51fKTfXtFJ89cdxGhZsVvHEnqf3V85tGueerqQaMnMSJhuF245/W9QczwNgc9zd1OUE6gQJBANnPRT894YcLef9vrzkmxJ+AFATILxhMyUZ67sbyF5arXhRKSUBATXol3b3cT7CkVM9CNaWsT3IbBjcaPF+JfnECQQCe5P0T8Ul0GErWnWWJQOr3dCMDDc/D5o3IvNogUg4fIMC+4wx39JT4tKDT5Qw+LLGQaAfqp7rU+pFxP/9AvgINAkEA11mskKvBOUFqnNiy+aHQFBeRM20tyyvYcZwIS6F/GxRST2Nna2RodhXMWPTjwbWouMcwvZ5RanM3wPFqsdcCsQJBAIkDbDeGGJDDYcUudB+mmvZGbupzPnTumdQ/BNhZ6VNuKsZvgpDtloffOc998tCqOXMMQcdWWwTMDB5b0P9C2QECQHNhqVSlfeYCkng6BW7U0Z++GJeC4wbCSG4II629aYwcXnMz3F9eLP/AAOxnlCpG+uYRHH1xo9fc41xufC4XGyY=";
//	public static String LKL_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyU3NcBz9iGd2MyY1OE/x7boHdykuiDxadbu1alQ2g4NAlE5rniAiBfD5iR+vETvheEOYCgIZBzGKku65vLVNFCNbKm7I7POeLDHWr4JKg4DtQ9ieobnj6sM/Mfiby2GH5TtNZk5MeSwstekyRqtuMpzaCuWFnMNJA/ipDKZCvAwIDAQAB";


	
	public static String ALGORITHM="RSA";
	
	public static PrivateKey pkey ;
	
	public static PublicKey pubkey ;
	
    /**
	* 得到私钥对象
	* @param key 密钥字符串（经过base64编码的秘钥字节）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String privateKey)  {
		try {
		byte[] keyBytes;
		
		keyBytes = Base64.decode(privateKey);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privatekey = keyFactory.generatePrivate(keySpec);
		
		return privatekey;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	 
    /**
	* 获取公钥对象
	* @param key 密钥字符串（经过base64编码秘钥字节）
	* @throws Exception
	*/
	public static PublicKey getPublicKey(String publicKey) {

		try {
			
			byte[] keyBytes;
			
			keyBytes = Base64.decode(publicKey);
			
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			PublicKey publickey = keyFactory.generatePublic(keySpec);
			
			return publickey;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
}
