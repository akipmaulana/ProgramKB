package aprisma.akirah.bingung.holder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

public class Klasifikasi extends KlikBParent {

	public final static String KLASIFIKASI_REQUEST = "klasifikasi_request";
	public static ArrayList<String> GET_KLASIFIKASI = new ArrayList<String>();
	public static ArrayList<String> ID_KLASIFIKASI = new ArrayList<String>();
	public static ArrayList<Bitmap> IMAGE_KLASIFIKASI = new ArrayList<Bitmap>();

	// JSON Node names
	public static final String TAG_CATALOGS = "catalogs";
	public static final String TAG_ITEM = "item";
	public static final String TAG_NAME = "name_catalog";
	public static final String TAG_ID_CATALOG = "id_catalog";
	public static final String TAG_ID_POSTING = "id_posting";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_NAME_MERCHANT = "nama_merchant";
	public static final String TAG_ALAMAT = "alamat";
	public static final String TAG_COUNTER = "counter";
	public static final String TAG_ISI_POSTING = "isi_posting";
	public static final String TAG_RATING = "rating";
	public static final String TAG_JUM_COM = "jum_com";
	public static final String TAG_FILLNAME_IMG = "filename_image";
	public static final String TAG_DESC = "desc";
	public static final String TAG_META_KEYWORD = "meta_keyword";
	public static final String TAG_IMG = "img";
	public static final String TAG_LAT = "lat";
	public static final String TAG_LON = "lon";
	
	public Klasifikasi() {
		// TODO Auto-generated constructor stub
	}
	
	public JSONObject getTimelineJSON(String id_catalog, String page, String lang){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", CATEGORY_TAG));
		params.add(new BasicNameValuePair("id_catalog", id_catalog));
		params.add(new BasicNameValuePair("page", page));
		params.add(new BasicNameValuePair("lang", lang));
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		return json;
	}
	
	public int getCountTotal(){
		int hasil = 0;
		
		for (int j = 0; j < Klasifikasi.GET_KLASIFIKASI.size(); j++) {
			JSONObject jsonObj = getTimelineJSON(
					Klasifikasi.ID_KLASIFIKASI.get(j), "0", "english");
			try {
				if (jsonObj.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
					JSONArray catalogs = jsonObj
							.getJSONArray(Klasifikasi.TAG_CATALOGS);
					
					hasil += catalogs.length();
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Log.e("TOTAL COUNT", hasil+"");
		
		return hasil;
	}
	
}
