package com.reapal.inchannel.tjunionpay.service.util;

public class OrderUtil {
	public static String getDateTimeTemp() {
		java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
		d.applyPattern("yyyyMMddHHmmss");
		java.util.Date nowdate = new java.util.Date();
		String str_date = d.format(nowdate);
		return str_date;
	}

	
	
	public static String getRandomStr(int lenght)
	{
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","u",
				"t","s","o","x","v","p","q","r","w","y","z"};
				
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			Double number = Math.random() * (randomValues.length - 1);
			str.append(randomValues[number.intValue()]);
		}
		return str.toString();
	}
}
