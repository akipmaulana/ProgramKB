package aprisma.akirah.bingung.detail;

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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;
import aprisma.akirah.bingung.timeline.TimelineList;

public class MapActivity extends Activity {

	private LatLng INIAKU = new LatLng(-6.737246, 108.550656);
	private static final long MIN_DISTANCE_FOR_UPDATE = 10;
	private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

	private GoogleMap map;

	private LocationManager locationManager;

	private String getKlasifikasi = "";

	private ProgressDialog pDialog;

	public static TimelineList[] timelines;

	private Boolean isViewAll = false;
	private Boolean isResume = false;
	private Boolean isInit = true;
	private Boolean isShowAlert = false;

	private Timeline timeline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

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

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isResume) {
			getLocation();
			isResume = false;
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
			timeline.execute();
		} else {
			Location locationGPS = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (locationGPS != null) {
				INIAKU = new LatLng(locationGPS.getLatitude(),
						locationGPS.getLongitude());
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

		SearchView search = (SearchView) actionBar.getCustomView()
				.findViewById(R.id.search_view_map);
		search.setQuery(klasifikasi.toLowerCase(), false);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(klasifikasi);
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

		for (int i = 0; i < timelines.length; i++) {
			// timelines id == 0 -> Food
			if (isViewAll) {
				map.addMarker(new MarkerOptions()
						.position(timelines[i].getLatLon())
						.title(timelines[i].getNamaku())
						.snippet("NILAI : " + timelines[i].getRataku()).anchor(0.5f, 0.5f));
			} else {
				if (timelines[i].getId() == id) {
					map.addMarker(new MarkerOptions()
							.position(timelines[i].getLatLon())
							.title(timelines[i].getNamaku())
							.snippet("NILAI : " + timelines[i].getRataku()).anchor(0.5f, 0.5f));
				}
			}
		}

		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				String namaku = marker.getTitle();
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
				Toast.makeText(getApplicationContext(), "View All", Toast.LENGTH_SHORT).show();
				isViewAll = true;
				initActionBar("View All");
				initialized();
			} else {
				showSettingsAlert("LOCATION SOURCES");
			}
			return true;
		case R.id.timelineBar:
			if (timelines != null) {
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
			// TODO Auto-generated method stub
			super.onPreExecute();
			isInit = false;
			pDialog = new ProgressDialog(MapActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Klasifikasi klasifikasi = new Klasifikasi();
			timelines = new TimelineList[klasifikasi.getCountTotal()];
			int index = 0;
			for (int j = 0; j < Klasifikasi.GET_KLASIFIKASI.size(); j++) {
				JSONObject jsonObj = klasifikasi.getTimelineJSON(
						Klasifikasi.ID_KLASIFIKASI.get(j), "0", "english");
				try {
					if (jsonObj.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
						JSONArray catalogs = jsonObj
								.getJSONArray(Klasifikasi.TAG_CATALOGS);

						for (int i = 0; i < catalogs.length(); i++) {
							JSONObject c = catalogs.getJSONObject(i);
							int id_posting = c.getInt(Klasifikasi.TAG_ID_POSTING);
							String imageku = c
									.getString(Klasifikasi.TAG_FILLNAME_IMG);
							String namaku = c.getString(Klasifikasi.TAG_JUDUL);
							String deskripsiku = c
									.getString(Klasifikasi.TAG_ISI_POSTING);
							String rataku = "RATING";
							String viewku = c
									.getString(Klasifikasi.TAG_COUNTER)
									+ " Like";
							String lat = c.getString(Klasifikasi.TAG_LAT);
							String lon = c.getString(Klasifikasi.TAG_LON);
							timelines[index++] = new TimelineList(j, id_posting, imageku,
									namaku, deskripsiku, rataku, viewku, true, lat,
									lon);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

			initialized();

		}

	}

}
