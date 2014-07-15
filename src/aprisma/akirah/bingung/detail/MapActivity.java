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
import aprisma.akirah.bingung.timeline.TimelineAcitivity;

public class MapActivity extends Activity {

	private final LatLng CIREBON = new LatLng(-6.737246, 108.550656);

	private GoogleMap map;
	
	private String getKlasifikasi = "";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		getKlasifikasi = getIntent().getStringExtra(KlasifikasiActivity.KLASIFIKASI_REQUEST).toString();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setTitle(getKlasifikasi);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.addMarker(new MarkerOptions().position(CIREBON).title(
				"Akip disini!"));
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CIREBON, 15);
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
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		case R.id.timelineBar:
			Intent intent = new Intent(this,
					TimelineAcitivity.class);
			intent.putExtra(KlasifikasiActivity.KLASIFIKASI_REQUEST, getKlasifikasi);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
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
