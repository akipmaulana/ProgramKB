package aprisma.akirah.bingung.detail;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.slidingmenu.Klasifikasi;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;

public class MapActivity extends Activity {

	private final LatLng LOCATION_BURNABY = new LatLng(49.27645, -122.917587);
	private final LatLng LOCATION_SURRREY = new LatLng(49.187500, -122.849000);

	private GoogleMap map;
	
	private String getKlasifikasi = "";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		getKlasifikasi = getIntent().getStringExtra(Klasifikasi.KLASIFIKASI_REQUEST).toString();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.addMarker(new MarkerOptions().position(LOCATION_SURRREY).title(
				"Find me here!"));
		
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_BURNABY, 9);
		map.animateCamera(update);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.timelineBar:
			Intent intent = new Intent(this,
					TimelineAcitivity.class);
			intent.putExtra(Klasifikasi.KLASIFIKASI_REQUEST, getKlasifikasi);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
}
