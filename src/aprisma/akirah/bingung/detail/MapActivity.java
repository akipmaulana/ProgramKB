package aprisma.akirah.bingung.detail;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;

public class MapActivity extends Activity {

	private final LatLng CIREBON = new LatLng(-6.737246, 108.550656);
	private final LatLng RS = new LatLng(-6.709351, 108.551992);
	private final LatLng SG = new LatLng(-6.708201, 108.549503);
	private final LatLng GRAGE = new LatLng(-6.707945, 108.553344);

	private GoogleMap map;

	private String getKlasifikasi = "";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		getKlasifikasi = getIntent().getStringExtra(
				Klasifikasi.KLASIFIKASI_REQUEST).toString();

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_top_search); // load your
																// layout
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_SHOW_CUSTOM); // show it

		SearchView search = (SearchView) actionBar.getCustomView()
				.findViewById(R.id.search_view_map);
		search.setQuery(getKlasifikasi.toString(), false);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getKlasifikasi);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.addMarker(new MarkerOptions().position(CIREBON).title(
				"Akip disini!"));

		map.addMarker(new MarkerOptions().position(RS).title("RS disini!"));

		map.addMarker(new MarkerOptions().position(SG).title("SG disini!"));

		map.addMarker(new MarkerOptions().position(GRAGE)
				.title("GRAGE disini!"));

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CIREBON, 13);
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
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		case R.id.timelineBar:
			Intent intent = new Intent(this, TimelineAcitivity.class);
			intent.putExtra(Klasifikasi.KLASIFIKASI_REQUEST,
					getKlasifikasi);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
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

}
