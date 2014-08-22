package aprisma.akirah.bingung.holder;

import aprisma.akirah.bingung.service.JSONParser;

public interface KlikBHolder {
	// Akip Maulana's Write
//	public static final String URL = "http://192.168.143.1/pengung/";

	 public static final String URL = "http://klikbingung.com/pengung/";

	public static final String KLASIFIKASI_TAG = "klasifikasi";
	public static final String LOGIN_TAG = "login";
	public static final String REGISTRASI_TAG = "registrasi";
	public static final String SETPASS_TAG = "setpass";
	public static final String SETDATA_TAG = "setdata";
	public static final String GETDATA_TAG = "getdata";
	public static final String CATEGORY_TAG = "category";
	public static final String REVIEW_TAG = "review";

	public static final String TAG_SUCCESS = "success";
	public static final String TAG_ERROR = "error";

	public static final JSONParser JSONPARSER = new JSONParser();

}
