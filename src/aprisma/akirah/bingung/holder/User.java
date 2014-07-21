package aprisma.akirah.bingung.holder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import aprisma.akirah.bingung.service.DatabaseHandler;

public class User extends KlikBParent{

	public static Boolean ISLOGIN = false;
	
	public static final String TAG_USER = "user";
	
	private static final String TAG_FULLNAME = "fullname";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_USERPASS = "userpass";
	private static final String TAG_SEX = "sex";
	private static final String TAG_BIRTHDAY = "birthday";
	private static final String TAG_ALAMAT = "alamat";
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

	private String email;
	private String password;

	private String namaLengkap;
	private String jenisKelamin;
	private String tanggalLahir;
	private String situs;
	private String alamat;
	private String kota;
	private String provinsi;
	private String negara;
	private String pendidikan;
	private String pekerjaan;
	private String hobi;
	private String biografi;

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
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

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String fullname, String email, String userpass) {
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

	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public boolean logoutUser(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNamaLengkap() {
		return namaLengkap;
	}

	public void setNamaLengkap(String namaLengkap) {
		this.namaLengkap = namaLengkap;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public String getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(String tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getSitus() {
		return situs;
	}

	public void setSitus(String situs) {
		this.situs = situs;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getKota() {
		return kota;
	}

	public void setKota(String kota) {
		this.kota = kota;
	}

	public String getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(String provinsi) {
		this.provinsi = provinsi;
	}

	public String getNegara() {
		return negara;
	}

	public void setNegara(String negara) {
		this.negara = negara;
	}

	public String getPendidikan() {
		return pendidikan;
	}

	public void setPendidikan(String pendidikan) {
		this.pendidikan = pendidikan;
	}

	public String getPekerjaan() {
		return pekerjaan;
	}

	public void setPekerjaan(String pekerjaan) {
		this.pekerjaan = pekerjaan;
	}

	public String getHobi() {
		return hobi;
	}

	public void setHobi(String hobi) {
		this.hobi = hobi;
	}

	public String getBiografi() {
		return biografi;
	}

	public void setBiografi(String biografi) {
		this.biografi = biografi;
	}

}
