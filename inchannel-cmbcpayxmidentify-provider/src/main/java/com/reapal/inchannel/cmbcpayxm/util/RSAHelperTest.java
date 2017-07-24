package com.reapal.inchannel.cmbcpayxm.util;

import java.io.UnsupportedEncodingException;

public class RSAHelperTest {

	// public static final String privKey =
	// "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDW+J3i8/zTkMvD"
	// + "yCftOsGNNVEmoodxUr23VeYUW9UI6do8U83ExJVtF9vX2O/RUru7ExJTMOxVg2QZ"
	// + "h/IekFFv67iVPSfGBX70DC8vobZKEfhE8V82tWmCtH45zx6U73hVALiV6X8EslIo"
	// + "sdT9xWAMY2NH0HvZ7V/06SvNNO0zKOm2vPyspzbwRDUpA89dmjEAwAlDx12/X+EY"
	// + "AR8VEU9ctLjVwYFXuFjc/BCryColK9HIg21P3vubHZM6GKIXfq6zJawV8lTkJlb5"
	// + "QEdcNk33VjpMhGs7ZRzJXgMG07uaPIBI6k7VQ2UZM7GtKUH8KBfWzViQ+Asz2cpE"
	// + "0t0VUoF9AgMBAAECggEBAKh9zwp+oDCW8g7vB9RZ1DDAlG2KwEwjRQ24pxBX9f75"
	// + "hBL6wHI0fsY2CBsDLtzLUtdLGHbaBrLzu/aC5lPsW9g0UsWuXElKL3pLPoS/5Cfk"
	// + "M8qdwToZMKzAmZrn6xljJNbDLOpbTDI7Lkg1MjMBi8nJ8JvuHdTux+InDCzYCf6o"
	// + "51aZN2XMLmut4e5zEdjHHknV1On2Jjgc5Gu0CbzrLTUpwOlhICRGiWQY/Mnt73HJ"
	// + "vZv7EQ0FjZk7UnWEweEfbgDBok283z6NaClvU5/MUaND2wWVdJfNPaUjHYtf7cE9"
	// + "S3rNrY5mpwVdAHmvAFKfx9PhB4A0hnY2h+i1VmLjBoECgYEA9542ca50sXNsbWwq"
	// + "W3T/78ZWKmQi9nP4MAtZkXzEFJtgEyIXT5CU6J8L1RhZ80J9tokR9FH3cwNUZl9/"
	// + "PiGD8l6jTjd08LfSW9t9z+4E0M6XelHG7ZfRtfV3HgzIHaBWI+nxd7yg4BoY21mg"
	// + "Tmypz+88aPHDvC8Ss8T3jkJZul0CgYEA3j9+5JXt9sYbfw2Yln3XSRAXS9MDWRCN"
	// + "K/NZiQ4RZ22DpMOjNanH4jypIpuV1pnU8BM1iv4e5l49Kx2I5vqnE/GDkouhEzM8"
	// + "vPhBKHKLXloKtziIAR0lJOGGER2FtU3iuqSb7nRjt53G9OSnfJ2IL7H6Br7L0O+r"
	// + "679ofKiSsaECgYB2T0iyHmmxE3Yd/g1q70cN+FTZIkk2Ogi+Y93izpsdQXOxEJvU"
	// + "rz8Gul877MulmAJawbkrZDJ36IJd+4jfVcImfqNGTub30MyYiRHe1FnGrr7fec0z"
	// + "XlObvfGxEOhYh3BA7pkp3Z18FdwEihk2/2JPcH4LomAkPNWRwS2K8hbPHQKBgQCc"
	// + "BYdXgcmkzD7RWwIb5AwWxq0UFfbrt6rjh9r7VFzzdvZL3Ove6GnicSNroD34gdXz"
	// + "FAkqomue3dmjQwCw5pYUciAj6NITYIzrPHzBoGgmvJ95ML6JyaQh2BD+QvNy7FKX"
	// + "JKgzJpI6fREHKt5JpW3NzevwgFElRJw0zBLWMKGLAQKBgCXMuSed+xQk5q8of4SY"
	// + "AoHi+1JFQQw7GLa06PpphmlUJxj8WBijnpPJSfktLrgx9SAQ5Bfqw4cGiRsS7zIu"
	// + "c+FDrSSnQrHCwrRmgsaTm4kvqxglcbJAnbuNescAPPkvkK9ZI2gMsIHeHMi0lLSk"
	// + "kwO17IvdhztJbUzFhOSSiMe6";
	//
	// public static final String pubKey =
	// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1vid4vP805DLw8gn7TrB"
	// + "jTVRJqKHcVK9t1XmFFvVCOnaPFPNxMSVbRfb19jv0VK7uxMSUzDsVYNkGYfyHpBR"
	// + "b+u4lT0nxgV+9AwvL6G2ShH4RPFfNrVpgrR+Oc8elO94VQC4lel/BLJSKLHU/cVg"
	// + "DGNjR9B72e1f9OkrzTTtMyjptrz8rKc28EQ1KQPPXZoxAMAJQ8ddv1/hGAEfFRFP"
	// + "XLS41cGBV7hY3PwQq8gqJSvRyINtT977mx2TOhiiF36usyWsFfJU5CZW+UBHXDZN"
	// + "91Y6TIRrO2UcyV4DBtO7mjyASOpO1UNlGTOxrSlB/CgX1s1YkPgLM9nKRNLdFVKB"
	// + "fQIDAQAB";

	// public static final String plaintext = "你好，测试";

	public static void main(String[] args) {

		CrossBankDkReq req = new CrossBankDkReq();
		req.setMerId("test");
		req.setMerName("test");
		req.setPayerAcc("011111111111120112");
		req.setPayerName("liyugang");
		req.setCertType("ZR01");
		req.setTranAmt("102");
		req.setCertNo("123046562102159895");
		req.setProvNo("320125");
		req.setCityNo("210315");
        req.createXml();
		String plaintext = req.getRequestXml();

		System.out.println(req.getRequestXml());

		System.out.println("=====> init <=====");
		try {

			RSAHelper.initKey(2048);

			System.out.println("=====> sign & verify <=====");

			// 签名
			byte[] signBytes = RSAHelper.signRSA(plaintext.getBytes("UTF-8"),
					false, "UTF-8");

			System.out.println("签名结果==========" + signBytes);
			// 验证签名
			boolean isValid = RSAHelper.verifyRSA(plaintext.getBytes("UTF-8"),
					signBytes, false, "UTF-8");

			System.out.println("isValid: " + isValid);

			// 加密和解密
			System.out.println("=====> encrypt & decrypt <=====");
			// 对明文加密
			byte[] cryptedBytes = RSAHelper.encryptRSA(
					plaintext.getBytes("UTF-8"), false, "UTF-8");

			System.out.println("加密结果==========" + cryptedBytes);
			// 对密文解密
			byte[] decryptedBytes = RSAHelper.decryptRSA(cryptedBytes, false,"UTF-8");
					
			System.out.println("decrypted: "+ new String(decryptedBytes, "UTF-8"));
					
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
