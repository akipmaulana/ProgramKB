package aprisma.akirah.bingung.detail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.PlaceProvider;
import aprisma.akirah.bingung.service.CheckConnection;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;
import aprisma.akirah.bingung.timeline.TimelineFragment;
import aprisma.akirah.bingung.timeline.TimelineList;

public class MapActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {

	private LatLng INIAKU = new LatLng(-6.737246, 108.550656);
	private static final long MIN_DISTANCE_FOR_UPDATE = 10;
	private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
	
	public static double lat = 0;
	public static double lon = 0;

	private GoogleMap map;

	private LocationManager locationManager;

	private String getKlasifikasi = "";

	private ProgressDialog pDialog;

	public static ArrayList<TimelineList> timelines;
	public static Boolean isNew; // untuk mengetahui apakah ada data baru

	private Boolean isViewAll = false;
	private Boolean isResume = false;
	private Boolean isInit = true;
	private Boolean isShowAlert = false;
	private Boolean flagOfSpanner = false;

	private Timeline timeline;

	private OnNavigationListener navigation;

	private Menu menu;
	private Boolean isReadyMenu = false;

	private TextView connectLay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		connectLay = (TextView) findViewById(R.id.connect);

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);

		getKlasifikasi = getIntent().getStringExtra(
				Klasifikasi.KLASIFIKASI_REQUEST).toString();

		timeline = new Timeline();

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				INIAKU = new LatLng(location.getLatitude(),
						location.getLongitude());

			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE,
				MIN_DISTANCE_FOR_UPDATE, locationListener);

		getLocation();

		initActionBar(getKlasifikasi);
		
		handleIntent(getIntent());

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isResume) {
			getLocation();
			isResume = false;
		}

		if (isReadyMenu) {
			menu.clear();
			onCreateOptionsMenu(menu);
		}
	}

	@Override
	protected void onPause() {
		if (isInit) {
			super.onPause();
			isResume = true;
		} else {
			super.onPause();
			isResume = false;
		}
	}

	private void getLocation() {
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location != null) {
			INIAKU = new LatLng(location.getLatitude(), location.getLongitude());
			lat = location.getLatitude();
			lon = location.getLongitude();
			timeline.execute();
		} else {
			Location locationGPS = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (locationGPS != null) {
				INIAKU = new LatLng(locationGPS.getLatitude(),
						locationGPS.getLongitude());
				lat = locationGPS.getLatitude();
				lon = locationGPS.getLongitude();
				timeline.execute();
			} else {
				if (!isShowAlert) {
					showSettingsAlert("LOCATION SOURCES");
				}
			}
		}

	}

	private void showSettingsAlert(String provider) {

		isShowAlert = true;

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MapActivity.this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog.setMessage(provider
				+ " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						isShowAlert = false;
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						MapActivity.this.startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						isShowAlert = false;
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("InlinedApi")
	private void initActionBar(String klasifikasi) {
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_top_search); // load your
		// layout
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_SHOW_CUSTOM); // show it

		ImageView search = (ImageView) actionBar.getCustomView().findViewById(
				R.id.search_view_map);
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSearchRequested();
			}
		});

		makeDropList();

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InlinedApi")
	private void makeDropList() {
		final ArrayList<String> spinners = (ArrayList<String>) Klasifikasi.GET_KLASIFIKASI
				.clone();
		spinners.add(getResources().getString(R.string.retrieveAll));

		SpinnerAdapter adapter = new ArrayAdapter<>(getApplicationContext(),
				android.R.layout.simple_list_item_1, spinners.toArray());

		navigation = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {

				if (flagOfSpanner) {
					if (itemPosition == Klasifikasi.GET_KLASIFIKASI.size()) {
						isViewAll = true;
						Toast.makeText(getApplicationContext(), "VIEW ALL",
								Toast.LENGTH_SHORT).show();
					} else {
						getKlasifikasi = spinners.get(itemPosition);
						Toast.makeText(getApplicationContext(), getKlasifikasi,
								Toast.LENGTH_SHORT).show();
					}

					initialized();
				}

				flagOfSpanner = true;

				return false;
			}
		};

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setListNavigationCallbacks(adapter, navigation);
		getActionBar().setSelectedNavigationItem(convertGetKlasifikasi());
	}

	private int convertGetKlasifikasi() {
		for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
			if (Klasifikasi.GET_KLASIFIKASI.get(i).equals(getKlasifikasi)) {
				return i;
			}
		}
		return 999;
	}

	private void initialized() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		int id = convertGetKlasifikasi();

		if (id == 999) {
			return;
		}

		map.clear();

		for (int i = 0; i < timelines.size(); i++) {
			// timelines id == 0 -> Food
			String rataku = timelines.get(i).getRataku();
			String viewku = timelines.get(i).getViewku();
			if (isViewAll) {
				map.addMarker(new MarkerOptions()
						.position(timelines.get(i).getLatLon())
						.title(timelines.get(i).getNamaku())
						.snippet(rataku + "   " + viewku).anchor(0.5f, 0.5f));

			} else {
				if (timelines.get(i).getId() == id) {
					map.addMarker(new MarkerOptions()
							.position(timelines.get(i).getLatLon())
							.title(timelines.get(i).getNamaku())
							.snippet(rataku + "   " + viewku)
							.anchor(0.5f, 0.5f));
				}
			}
		}

		isViewAll = false;

		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				String namaku = marker.getTitle();
				Intent intent = new Intent(getApplicationContext(),
						DetailActivity.class);
				intent.putExtra(Klasifikasi.TAG_NAME, namaku);
				startActivity(intent);
			}
		});

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(INIAKU, 11);
		map.animateCamera(update);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map_menu, menu);
		if (!User.ISLOGIN) {

			MenuItem setting = menu.findItem(R.id.action_settings);
			setting.setEnabled(false);
			setting.setVisible(false);
			MenuItem logout = menu.findItem(R.id.logout_map);
			logout.setTitle(R.string.masuk);
		}

		this.menu = menu;
		isReadyMenu = true;

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		case R.id.retrieveAll:
			if (timelines != null) {
				if (TimelineFragment.isLoadMore) {
					initialized();
				}
			} else {
				showSettingsAlert("LOCATION SOURCES");
			}
			return true;
		case R.id.timelineBar:
			if (timelines != null) {
				// if (isNew) {
				// for (int i = 0; i < timelines.size(); i++) {
				// timelines.get(i).setBmImage();
				// }
				// isNew = false;
				// }
				// checkConnection.Destroy();
				intent = new Intent(this, TimelineAcitivity.class);
				intent.putExtra(Klasifikasi.KLASIFIKASI_REQUEST, getKlasifikasi);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			} else {
				showSettingsAlert("LOCATION SOURCES");
			}
			return true;
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(),
					PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			return true;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.logout_map:
			User.ISLOGIN = false;
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}

	private class Timeline extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isInit = false;
			pDialog = new ProgressDialog(MapActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			if (timelines == null) {
				Klasifikasi klasifikasi = new Klasifikasi();
				timelines = new ArrayList<>();
				for (int j = 0; j < Klasifikasi.GET_KLASIFIKASI.size(); j++) {
					JSONObject jsonObj = klasifikasi.getTimelineJSON(
							Klasifikasi.ID_KLASIFIKASI.get(j), "0", "english");
					try {
						if (jsonObj.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
							JSONArray catalogs = jsonObj
									.getJSONArray(Klasifikasi.TAG_CATALOGS);

							for (int i = 0; i < catalogs.length(); i++) {
								JSONObject c = catalogs.getJSONObject(i);
								int id_posting = c
										.getInt(Klasifikasi.TAG_ID_POSTING);
								String imageku = c
										.getString(Klasifikasi.TAG_FILLNAME_IMG);
								String namaku = c
										.getString(Klasifikasi.TAG_JUDUL);
								String deskripsiku = c
										.getString(Klasifikasi.TAG_ISI_POSTING);
								String rataku = c
										.getString(Klasifikasi.TAG_COUNTER)
										+ " View";
								String viewku = c.getString("like") + " Like";
								String lat = c.getString(Klasifikasi.TAG_LAT);
								String lon = c.getString(Klasifikasi.TAG_LON);
								timelines.add(new TimelineList(j, id_posting,
										imageku, namaku, deskripsiku, rataku,
										viewku, true, lat, lon,
										getApplicationContext()));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			// Dismiss the progress dialog
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}

			// isNew = true;

			initialized();

		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cLoader = null;
		if (arg0 == 0)
			cLoader = new CursorLoader(getBaseContext(),
					PlaceProvider.SEARCH_URI, null, null,
					new String[] { arg1.getString("query") }, null);
		else if (arg0 == 1)
			cLoader = new CursorLoader(getBaseContext(),
					PlaceProvider.DETAILS_URI, null, null,
					new String[] { arg1.getString("query") }, null);
		return cLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		showLocations(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		try {
			if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
				doSearch(intent.getStringExtra(SearchManager.QUERY));
			} else if (intent.getAction().equals(Intent.ACTION_VIEW)) {
				getPlace(intent.getStringExtra(SearchManager.EXTRA_DATA_KEY));
			}
		} catch (Exception e) {
		} finally {
		}
	}

	private void doSearch(String query) {
		Bundle data = new Bundle();
		data.putString("query", query);
		getSupportLoaderManager().restartLoader(0, data, this);
	}

	private void getPlace(String query) {
		Bundle data = new Bundle();
		data.putString("query", query);
		getSupportLoaderManager().restartLoader(1, data, this);
	}

	private void showLocations(Cursor c) {
		MarkerOptions markerOptions = null;
		LatLng position = null;
//		map.clear();
		while (c.moveToNext()) {
			markerOptions = new MarkerOptions();
			position = new LatLng(Double.parseDouble(c.getString(1)),
					Double.parseDouble(c.getString(2)));
			markerOptions.position(position);
			markerOptions.title(c.getString(0));
			map.addMarker(markerOptions);
		}
		if (position != null) {
			CameraUpdate cameraPosition = CameraUpdateFactory
					.newLatLng(position);
			map.animateCamera(cameraPosition);
		}
	}

}
