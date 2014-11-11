package aprisma.akirah.bingung.detail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;
import aprisma.akirah.bingung.service.DatabaseHandler;
import aprisma.akirah.bingung.service.KlasifikasiFacade;

@SuppressLint("NewApi")
public class KlasifikasiActivity extends Activity {

	// private AutoCompleteTextView inputSearch;
	private ListAdapter adapter;

	private Menu menu;
	private Boolean isReadyMenu = false;

	private ProgressDialog pDialog;
	
	public static DatabaseHandler dbHandler;
	
	private TextView connectLay;
	
	private static Resources res;

	String[] colors = { "#A4C400", "#AA00FF", "#E51400", "#E3C800", "#1BA1E2",
			"#D80073" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.klasifikasi_activity);

		connectLay = (TextView) findViewById(R.id.connect);
		
		res = getResources();
		
		dbHandler = new DatabaseHandler(getApplicationContext());//DatabaseHandler.getInstance(getApplicationContext());

		new Getcatalog().execute();

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.klasifikasi, menu);

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar_klasifikasi); // load your
																	// layout
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // show it

		PengaturanActivity.SetMenu(menu);

		this.menu = menu;
		isReadyMenu = true;

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isReadyMenu) {
			menu.clear();
			onCreateOptionsMenu(menu);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		pDialog.cancel();
	}
	
	@Override
	public void onBackPressed() {
		dbHandler.closeDB();
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.katalogBar:
			new Getcatalog().execute();
			break;
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(),
					PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			break;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.logout:
			User.ISLOGIN = false;
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * set klasifikasi ke dalam list view
	 */
	public void setToList() {
		int j = 0;
		ArrayList<Item> temp1 = new ArrayList<Item>();
		for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
			String id_catalog = Klasifikasi.ID_KLASIFIKASI.get(i);
			String name_catalog = Klasifikasi.GET_KLASIFIKASI.get(i);
			temp1.add(new Item(name_catalog, colors[j++]));
			dbHandler.getKlasifikasiFacade().insert(id_catalog, "1", name_catalog);
			if (j > 5) {
				j = 0;
			}
		}

		adapter = new ListAdapter(this, temp1);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				clicked(v, R.id.texKlas);
			}
		});
	}
	
	/*
	 * When Clicked clasification
	 */
	private void clicked(View v, int id) {
		// checkConnection.Destroy();
		TextView tv = (TextView) v.findViewById(id);
		String hasil = tv.getText().toString();
		Intent intent = new Intent(this, MapActivity.class);
		// intent.setAction(Intent.ACTION_SEARCH);
		intent.putExtra(Klasifikasi.KLASIFIKASI_REQUEST, hasil);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
	}

	public void reload(View v) {
		new Getcatalog().execute();
	}

	/*
	 * Custom class untuk adapter list view
	 */
	@SuppressLint("DefaultLocale")
	private static class ListAdapter extends BaseAdapter {

		Context ctx;
		LayoutInflater lInflater;
		List<Item> data;

		ListAdapter(Context context, List<Item> data) {
			ctx = context;
			this.data = data;
			lInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = lInflater.inflate(R.layout.klasifikasi_item, parent,
						false);
			}

			view.getBackground().setColorFilter(data.get(position).getColor(),
					android.graphics.PorterDuff.Mode.MULTIPLY);

			TextView tv = (TextView) view.findViewById(R.id.texKlas);
			tv.setText(data.get(
					position).getText());
			tv.setBackgroundColor(data.get(position).getColor());
			
			((ImageView) view.findViewById(R.id.loKlas))
				.setBackground(new BitmapDrawable(res,Klasifikasi.IMAGE_KLASIFIKASI.get(position)));

			return view;
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	JSONArray klasifications = null;

	public class Getcatalog extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(KlasifikasiActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("tag",
						Klasifikasi.KLASIFIKASI_TAG));
				JSONObject jsonObj = Klasifikasi.JSONPARSER.getJSONFromUrl(
						Klasifikasi.URL, params);

				// Getting JSON Array node
				klasifications = jsonObj.getJSONArray(Klasifikasi.TAG_ITEM);

				// looping through All Contacts
				Klasifikasi.GET_KLASIFIKASI.clear();
				Klasifikasi.ID_KLASIFIKASI.clear();
				for (int i = 0; i < klasifications.length(); i++) {
					JSONObject c = klasifications.getJSONObject(i);
					String name_item = c.getString(Klasifikasi.TAG_NAME);
					String id_item = c.getString(Klasifikasi.TAG_ID_CATALOG);
					String img_item = "http://klikbingung.com/data/category/"+c.getString(Klasifikasi.TAG_IMG);
					Klasifikasi.GET_KLASIFIKASI.add(name_item);
					Klasifikasi.ID_KLASIFIKASI.add(id_item);
					Klasifikasi.IMAGE_KLASIFIKASI.add(get_bitmap(img_item));
				}

			} catch (Exception e) {
				ArrayList<HashMap<String, String>> classifications = dbHandler.getKlasifikasiFacade().viewAll();
				Klasifikasi.GET_KLASIFIKASI.clear();
				Klasifikasi.ID_KLASIFIKASI.clear();
				for (int i = 0;i<classifications.size();i++){
					String id_catalog = classifications.get(i).
							get(KlasifikasiFacade.KEY_ID_CATALOG);
//					String id_lang = classifications.get(i).
//							get(KlasifikasiFacade.KEY_ID_LANG);
					String name_catalog = classifications.get(i).
							get(KlasifikasiFacade.KEY_NAME_CATALOG);
					Klasifikasi.GET_KLASIFIKASI.add(name_catalog);
					Klasifikasi.ID_KLASIFIKASI.add(id_catalog);
				}
			}
			return null;
		}
		
		protected Bitmap get_bitmap(String urls) {
			String urldisplay = urls;
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				return null;
			}
			return mIcon11;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setToList();
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}
	
	private class Item {
		private String text;
		private String color;

		public Item(String text, String color) {
			this.text = text;
			this.color = color;
		}

		public String getText() {
			return text;
		}

		public int getColor() {
			return Color.parseColor(color);
		}

	}
}
