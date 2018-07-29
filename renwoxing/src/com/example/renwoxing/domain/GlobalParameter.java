package com.example.renwoxing.domain;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.graphics.Bitmap;

public class GlobalParameter extends Application{
	public static String globalUrl = "";
	private String cookie;
//	private User user;
	
	public static Map<Integer, Bitmap> picMap = new HashMap<Integer, Bitmap>();
	
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}

