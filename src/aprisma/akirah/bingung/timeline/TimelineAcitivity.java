package aprisma.akirah.bingung.timeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.detail.PengaturanActivity;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;

@SuppressLint("NewApi")
public class TimelineAcitivity extends FragmentActivity implements
		ActionBar.TabListener {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_layout);
		
		new Timeline().execute();
		
	}

	private void initialized() {
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xffa02065));

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						Log.e("POSISI : ", position + "");
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
			// new Timeline(Klasifikasi.ID_KLASIFIKASI.get(i)).execute();
		}

		Log.e("COUNT", mAppSectionsPagerAdapter.getCount() + "");

		Intent intent = getIntent();
		goestoKlasifikasi(intent
				.getStringExtra(Klasifikasi.KLASIFIKASI_REQUEST).toString());
	}

	/*
	 * Method untuk menentukan klasifikasi apa yang telah dipilih. sehingga
	 * langsung mereference ke kelompoknya.
	 */
	private void goestoKlasifikasi(String klasifikasiRespon) {
		int indexOfsearch = 0;
		for (String s : Klasifikasi.GET_KLASIFIKASI) {
			if (s.equals(klasifikasiRespon)) {
				mViewPager.setCurrentItem(indexOfsearch);
				return;
			}
			indexOfsearch++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		PengaturanActivity.SetMenu(menu);

		return super.onCreateOptionsMenu(menu);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			// This is called when the Home (Up) button is pressed in the action
			// bar.
			// Create a simple intent that starts the hierarchical parent
			// activity and
			// use NavUtils in the Support Package to ensure proper handling of
			// Up.
			Intent upIntent = new Intent(this, KlasifikasiActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				// This activity is not part of the application's task, so
				// create a new task
				// with a synthesized back stack.
				TaskStackBuilder.from(this)
				// If there are ancestor activities, they should be added here.
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				// This activity is part of the application's task, so simply
				// navigate up to the hierarchical parent activity.
				NavUtils.navigateUpTo(this, upIntent);
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
		case R.id.logout:
			User.ISLOGIN = false;
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		// index untuk GET_KLASIFIKASI dari kelas Klasifikasi
		private int indeksOfKlasfikasi = 0;

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			return TimelineFragment.newInstance(i);
		}

		@Override
		public int getCount() {
			// Akip's Code
			int count = 0;
			for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
				count++;
			}
			return count;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return Klasifikasi.GET_KLASIFIKASI.get(indeksOfKlasfikasi++);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}

	private ProgressDialog pDialog;

	public static TimelineList[] timelines;

	private class Timeline extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(TimelineAcitivity.this);
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
							String imageku = c
									.getString(Klasifikasi.TAG_FILLNAME_IMG);
							String namaku = c.getString(Klasifikasi.TAG_JUDUL);
							String deskripsiku = c.getString(
									Klasifikasi.TAG_ISI_POSTING);
							String rataku = "RATING";
							String viewku = c
									.getString(Klasifikasi.TAG_COUNTER) + " Like";
							timelines[index++] = new TimelineList(j, imageku, namaku,
									deskripsiku, rataku, viewku);
							Log.e("JENATE", j+"");
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
