package aprisma.akirah.bingung.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GetPlaces extends AsyncTask<String, Void, ArrayList<String>> {

	private ArrayAdapter<String> adapter;
	private Context ctx;
	private AutoCompleteTextView textView;

	public GetPlaces(Context ctx, ArrayAdapter<String> adapter,
			AutoCompleteTextView textView) {
		this.ctx = ctx;
		this.adapter = adapter;
		this.textView = textView;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		Log.d("gottaGo", "doInBackground");

		ArrayList<String> predictionsArr = new ArrayList<String>();

		try {

			URL googlePlaces = new URL(
			// URLEncoder.encode(url,"UTF-8");
					"https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
							+ URLEncoder.encode(params.toString(), "UTF-8")
							+ "&types=geocode&language=fr&sensor=true&key=AIzaSyATMBKHBn3w6NXmtuTgFMwJNKV3j6pxIss");
			URLConnection tc = googlePlaces.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			String line;
			StringBuffer sb = new StringBuffer();
			// take Google's legible JSON and turn it into one big string.
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			// turn that string into a JSON object
			JSONObject predictions = new JSONObject(sb.toString());
			// now get the JSON array that's inside that object
			JSONArray ja = new JSONArray(predictions.getString("predictions"));

			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				// add each entry to our array
				predictionsArr.add(jo.getString("description"));
			}
		} catch (IOException e) {

			Log.e("YourApp", "GetPlaces : doInBackground", e);

		} catch (JSONException e) {

			Log.e("YourApp", "GetPlaces : doInBackground", e);

		}

		return predictionsArr;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		Log.d("YourApp", "onPostExecute : " + result.size());
		// update the adapter
		adapter = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_list_item_1);
		adapter.setNotifyOnChange(true);
		// attach the adapter to textview
		textView.setAdapter(adapter);

		for (String string : result) {

			Log.d("YourApp", "onPostExecute : result = " + string);
			adapter.add(string);
			adapter.notifyDataSetChanged();

		}

		Log.d("YourApp",
				"onPostExecute : autoCompleteAdapter" + adapter.getCount());

	}

}
