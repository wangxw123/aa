package com.reapal.inchannel.tjunionpay.service.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author ZhaoChenxu
 * @Date 2015��4��20������5:01:18
 * @Description TODO
 * @File Base64Util.java
 */
public class Base64Util {

	static public InputStream decodeBase64(String image_base64) throws IOException
	{
		try
		{
			byte[] b = Base64.decode(image_base64);
			for(int i = 0; i < b.length; ++i)
			{
				if(b[i] < 0)
					b[i] += 256;
			}
			InputStream is = new ByteArrayInputStream(b);
			return is;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	static public String encodeBase64(BufferedImage image) throws IOException
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			return new String(Base64.encode(bytes));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return "";
		}
	}
}
