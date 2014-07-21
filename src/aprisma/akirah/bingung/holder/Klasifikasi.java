package aprisma.akirah.bingung.holder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;

public class Klasifikasi extends KlikBParent {

	public final static String KLASIFIKASI_REQUEST = "klasifikasi_request";
	public static ArrayList<String> GET_KLASIFIKASI = new ArrayList<String>();

	// JSON Node names
	private static final String TAG_ITEM = "item";
	private static final String TAG_NAME = "name_catalog";

	JSONArray klasifications = null;

	private Getcatalog getKlasi;
	private KlasifikasiActivity klasActiv;

	public Klasifikasi(Context ctx, KlasifikasiActivity klasActiv) {
		this.klasActiv = klasActiv;
		getKlasi = new Getcatalog();
		getKlasi.execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	public class Getcatalog extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("tag", KLASIFIKASI_TAG));
				JSONObject jsonObj = JSONPARSER.getJSONFromUrl(URL, params);

				// Getting JSON Array node
				klasifications = jsonObj.getJSONArray(TAG_ITEM);

				// looping through All Contacts
				GET_KLASIFIKASI.clear();
				for (int i = 0; i < klasifications.length(); i++) {
					JSONObject c = klasifications.getJSONObject(i);
					String name_item = c.getString(TAG_NAME);
					GET_KLASIFIKASI.add(name_item);
				}


			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			klasActiv.setToList();
		}

	}

}
