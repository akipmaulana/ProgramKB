package aprisma.akirah.bingung.detail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;

@SuppressLint("NewApi")
public class KlasifikasiActivity extends Activity {

	// private AutoCompleteTextView inputSearch;
	private ListAdapter adapter;
	private SearchView searchView;

	private Menu menu;
	private Boolean isReadyMenu = false;

	private ProgressDialog pDialog;

	private TextView connectLay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.klasifikasi_activity);

		connectLay = (TextView) findViewById(R.id.connect);

		new Getcatalog().execute();

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchitem = menu.findItem(R.id.grid_default_search);
		searchView = (SearchView) searchitem.getActionView();
		setupSearchView(searchitem);

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
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
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

	private void setupSearchView(MenuItem searchItem) {

		searchView.setIconifiedByDefault(true);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();

			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			searchView.setSearchableInfo(info);
		} else {
		}

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	/*
	 * set klasifikasi ke dalam list view
	 */
	public void setToList() {
		adapter = new ListAdapter(this, Klasifikasi.GET_KLASIFIKASI);

		ListView listView = (ListView) findViewById(R.id.list_klasifikasi);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				clicked(view, R.id.iniAkuTv);
			}
		});

		// inputSearch = (AutoCompleteTextView) this.findViewById(
		// R.id.inputSearch);
		//
		// ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, GET_KLASIFIKASI);
		//
		// inputSearch.setAdapter(adapterAuto);
		//
		// inputSearch.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
		// clicked(v, android.R.id.text1);
		// }
		// });
	}

	/*
	 * When Clicked clasification
	 */
	private void clicked(View v, int id) {
		//checkConnection.Destroy();
		TextView tv = (TextView) v.findViewById(id);
		String hasil = tv.getText().toString();
		Intent intent = new Intent(this, MapActivity.class);
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
		List<String> data;

		ListAdapter(Context context, List<String> data) {
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
				view = lInflater
						.inflate(R.layout.tv_klasifikasi, parent, false);
			}

			if (position % 2 == 0) {
				view.setBackgroundColor(0xFFFFFFFF);
			} else {
				view.setBackgroundColor(0xFFF3F3F3);
			}

			TextView tv = (TextView) view.findViewById(R.id.iniAkuTv);
			tv.setText(data.get(position));

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
					Klasifikasi.GET_KLASIFIKASI.add(name_item);
					Klasifikasi.ID_KLASIFIKASI.add(id_item);
				}

			} catch (Exception e) {
			}
			return null;
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

}
