package com.reapal.inchannel.tjunionpay.service.util;

/**
 * Created by weileek on 2015-8-4.
 */


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Base64
{
    private static final Encoder encoder = new Base64Encoder();

    public static byte[] encode(byte[] paramArrayOfByte)
    {
        int i = (paramArrayOfByte.length + 2) / 3 * 4;
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
        try
        {
            encoder.encode(paramArrayOfByte, 0, paramArrayOfByte.length, localByteArrayOutputStream);
        }
        catch (IOException localIOException)
        {
            throw new RuntimeException("exception encoding base64 string: " + localIOException);
        }
        return localByteArrayOutputStream.toByteArray();
    }
    public static byte[] decode(String paramString)
    {
        int i = paramString.length() / 4 * 3;
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
        try
        {
            encoder.decode(paramString, localByteArrayOutputStream);
        }
        catch (IOException localIOException)
        {
            throw new RuntimeException("exception decoding base64 string: " + localIOException);
        }
        return localByteArrayOutputStream.toByteArray();
    }
}
