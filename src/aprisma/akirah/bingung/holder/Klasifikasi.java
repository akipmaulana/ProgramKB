package aprisma.akirah.bingung.holder;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class Klasifikasi extends KlikBParent {

	public final static String KLASIFIKASI_REQUEST = "klasifikasi_request";
	public static ArrayList<String> GET_KLASIFIKASI = new ArrayList<String>();

	// JSON Node names
	private static final String TAG_ITEM = "item";
	private static final String TAG_NAME = "name_catalog";

	JSONArray klasifications = null;

	public Klasifikasi(Context ctx) {

		setKlasifikasi();
	}

	/*
	 * Untuk menyimpan Klasifikasi dari serverke dalam array GET_KLASIFIKASI
	 */
	public void setKlasifikasi() {


		new GetContacts().execute();
		
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			try {
				JSONObject jsonObj = JSONPARSER.getJSONFromUrl(URL);

				// Getting JSON Array node
				klasifications = jsonObj.getJSONArray(TAG_ITEM);
				

				// looping through All Contacts
				//GET_KLASIFIKASI.clear();
				for (int i = 0; i < klasifications.length(); i++) {
					JSONObject c = klasifications.getJSONObject(i);
					String name_item = c.getString(TAG_NAME);
					Log.e("Item: ", name_item);
					
					GET_KLASIFIKASI.add(name_item);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		
	}

}
