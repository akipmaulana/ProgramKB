package aprisma.akirah.bingung.detail;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
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

@SuppressLint("NewApi")
public class KlasifikasiActivity extends Activity {

	// private AutoCompleteTextView inputSearch;
	private ListAdapter adapter;
	private SearchView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.klasifikasi_activity);
		
		new Klasifikasi(getApplicationContext(), this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchitem = menu.findItem(R.id.grid_default_search);
		searchView = (SearchView) searchitem.getActionView();
		setupSearchView(searchitem);

		PengaturanActivity.SetMenu(menu);

		return super.onCreateOptionsMenu(menu);
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

		final ListView listView = (ListView) findViewById(R.id.list_klasifikasi);
		listView.setAdapter(adapter);
		
		Log.e("DULU", "FINISH ACTIVITY");

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
		TextView tv = (TextView) v.findViewById(id);
		String hasil = tv.getText().toString();
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra(Klasifikasi.KLASIFIKASI_REQUEST, hasil);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
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

}
