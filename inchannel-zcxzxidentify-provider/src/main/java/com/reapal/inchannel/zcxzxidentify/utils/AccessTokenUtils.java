/*
package com.reapal.inchannel.txskidentify.utils;

import java.io.IOException;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.txsk.demo.model.AccessToken;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenUtils {
	
private static final String url = "http://tianxingshuke.com/api/rest/common/organization/auth";
	
	public static AccessToken getAccessToken(String account, String signature) throws IOException{
		OkHttpClient client = new OkHttpClient();
		FormBody formBody = new FormBody.Builder().add("account", account).add("signature", signature).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Response response = client.newCall(request).execute();
		JsonObject tokenJson = new JsonParser().parse(response.body().string()).getAsJsonObject();
		if(tokenJson.get("success").getAsBoolean()){
			JsonObject dataJson = tokenJson.get("data").getAsJsonObject();
			AccessToken accessToken = new AccessToken();
			accessToken.setAccount(dataJson.get("account").getAsString());
			accessToken.setId(dataJson.get("id").getAsString());
			accessToken.setAccessToken(dataJson.get("accessToken").getAsString());
			accessToken.setExpireTime(new Date(dataJson.get("expireTime").getAsLong()));
			return accessToken;
		}else{
			System.out.println(response.body().string());
			return null;
		}
	}

}
*/
