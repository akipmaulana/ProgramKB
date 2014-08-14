package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import android.widget.AdapterView.OnItemClickListener;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;
import aprisma.akirah.bingung.timeline.TimelineList;
import aprisma.akirah.bingung.timeline.TimelineListAdapter;

public class MyTimelineActivity extends Activity {

	private ListView listView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_timeline);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		TextView connectLay = (TextView) findViewById(R.id.connect);

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);

		listView = (ListView) findViewById(R.id.my_time_list);

		setListenerCustom();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!User.ISLOGIN) {
			Intent intent = new Intent(getApplicationContext(),
					KlasifikasiActivity.class);
			startActivity(intent);
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
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
		case R.id.logout:
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

	/*
	 * Method untuk set listener pada view custom yang clickable atau editable
	 */
	private void setListenerCustom() {

		TimelineList[] list_line = new TimelineList[Klasifikasi.GET_KLASIFIKASI
				.size()];
		for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
			list_line[i] = new TimelineList(
					0,
					0,
					"",
					"Akip",
					"akip_farahakip_farahakip_farahakip_farahakip_farahakip_farahakip_farahakip_farahakip_farahakip_fara",
					"Akirah", "Munyu", true, "-6.737246", "108.550656",
					getApplicationContext());
		}

		if (list_line[0] == null) {
			((TextView) findViewById(R.id.kosong)).setVisibility(View.VISIBLE);
		} else {

			TimelineListAdapter adapter = new TimelineListAdapter(this,
					R.layout.timeline_list, list_line);

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String namaku = ((TextView) view.findViewById(R.id.namaku))
							.getText().toString();
					Toast.makeText(getApplicationContext(),
							"Item clicked: " + namaku, Toast.LENGTH_SHORT)
							.show();
					// Intent intent = new Intent(getApplicationContext(),
					// DetailActivity.class);
					// intent.putExtra(Klasifikasi.TAG_NAME, namaku);
					// startActivity(intent);
					// overridePendingTransition(R.anim.slide_in,
					// R.anim.slide_in);
				}
			});
		}
	}

}
