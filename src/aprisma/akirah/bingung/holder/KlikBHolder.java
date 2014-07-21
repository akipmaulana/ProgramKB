package aprisma.akirah.bingung.holder;

import aprisma.akirah.bingung.service.JSONParser;

public interface KlikBHolder {
	// Akip Maulana's Write
	public static final String URL = "http://192.168.143.1/pengung/";
	
	public static final String KLASIFIKASI_TAG = "klasifikasi";
	public static final String LOGIN_TAG = "login";
	public static final String REGISTRASI_TAG = "registrasi";
	
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_ERROR = "error";
	
	public static final JSONParser JSONPARSER = new JSONParser();
	
	
}