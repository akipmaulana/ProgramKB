package aprisma.akirah.bingung.holder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User extends KlikBParent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Boolean ISLOGIN = false;

	public static final String TAG_USER = "user";

	private static final String TAG_ID_USER = "id_user";
	private static final String TAG_FULLNAME = "fullname";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_USERPASS = "userpass";
	private static final String TAG_SEX = "sex";
	private static final String TAG_BIRTHDAY = "birthday";
	private static final String TAG_ALAMAT = "alamat";
	private static final String TAG_KOTA = "kota";
	private static final String TAG_PROVINSI = "provinsi";
	private static final String TAG_NEGARA = "negara";
	private static final String TAG_AVATAR = "avatar";
	private static final String TAG_PEKERJAAN = "pekerjaan";
	private static final String TAG_WEBSITE = "website";
	private static final String TAG_HOBBY = "hobby";
	private static final String TAG_BIO = "bio";
	private static final String TAG_PENDIDIKAN = "pendidikan";
	private static final String TAG_STATUS = "status";
	private static final String TAG_USERS_TGL = "users_tgl";

	public static String id_user;
	public static String fullname;
	public static String email;
	public static String userpass;
	public static String sex;
	public static String birthday;
	public static String alamat;
	public static String kota;
	public static String provinsi;
	public static String negara;
	public static String avatar;
	public static String pekerjaan;
	public static String website;
	public static String hobi;
	public static String bio;
	public static String pendidikan;
	public static String status;
	public static String users_tgl;

	public JSONObject loginUser(String email, String userpass) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", LOGIN_TAG));
		params.add(new BasicNameValuePair(TAG_EMAIL, email));
		params.add(new BasicNameValuePair(TAG_USERPASS, userpass));
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		Log.e("JSON", json.toString());
		return json;
	}

	public JSONObject registerUser(String fullname, String email,
			String userpass) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", REGISTRASI_TAG));
		params.add(new BasicNameValuePair(TAG_USERPASS, userpass));
		params.add(new BasicNameValuePair(TAG_EMAIL, email));
		params.add(new BasicNameValuePair(TAG_FULLNAME, fullname));

		// getting JSON Object
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		// return json
		return json;
	}

	public void fetchDataUser(JSONObject jsonOBJECT) throws JSONException {
		JSONObject jsonOBJ = jsonOBJECT.getJSONObject(TAG_USER);
		id_user = jsonOBJ.getString(TAG_ID_USER);
		fullname = jsonOBJ.getString(TAG_FULLNAME);
		email = jsonOBJ.getString(TAG_EMAIL);
		userpass = jsonOBJ.getString(TAG_USERPASS);
		sex = jsonOBJ.getString(TAG_SEX);
		birthday = jsonOBJ.getString(TAG_BIRTHDAY);
		alamat = jsonOBJ.getString(TAG_ALAMAT);
		kota = jsonOBJ.getString(TAG_KOTA);
		provinsi = jsonOBJ.getString(TAG_PROVINSI);
		negara = jsonOBJ.getString(TAG_NEGARA);
		avatar = jsonOBJ.getString(TAG_AVATAR);
		pekerjaan = jsonOBJ.getString(TAG_PEKERJAAN);
		website = jsonOBJ.getString(TAG_WEBSITE);
		hobi = jsonOBJ.getString(TAG_HOBBY);
		bio = jsonOBJ.getString(TAG_BIO);
		pendidikan = jsonOBJ.getString(TAG_PENDIDIKAN);
		status = jsonOBJ.getString(TAG_STATUS);
		users_tgl = jsonOBJ.getString(TAG_USERS_TGL);

	}

	public static JSONObject updateUserPass(String newUserPass) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", SETPASS_TAG));
		params.add(new BasicNameValuePair(TAG_USERPASS, newUserPass));
		params.add(new BasicNameValuePair(TAG_EMAIL, User.email));

		// getting JSON Object
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		// return json
		return json;
	}

	/**
	 * Function get Login status
	 * */
	// public boolean isUserLoggedIn(Context context) {
	// DatabaseHandler db = new DatabaseHandler(context);
	// int count = db.getRowCount();
	// if (count > 0) {
	// // user logged in
	// return true;
	// }
	// return false;
	// }

	/**
	 * Function to logout user Reset Database
	 * */
	// public boolean logoutUser(Context context) {
	// DatabaseHandler db = new DatabaseHandler(context);
	// db.resetTables();
	// return true;
	// }

}
