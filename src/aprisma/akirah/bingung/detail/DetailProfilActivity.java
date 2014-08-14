package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;

public class DetailProfilActivity extends Activity {

	private AlertDialog.Builder alert;

	private static String TITLE = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView connectLay = (TextView) findViewById(R.id.connect);

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);
		
		((TextView) findViewById(R.id.nama_lengkap)).setText(User.fullname);
		((TextView) findViewById(R.id.jekel)).setText(User.sex);
		((TextView) findViewById(R.id.tanggal_lahir)).setText(User.birthday);
		((TextView) findViewById(R.id.mail)).setText(User.email);
		((TextView) findViewById(R.id.situs)).setText(User.website);
		((TextView) findViewById(R.id.alamat)).setText(User.alamat);
		((TextView) findViewById(R.id.kota)).setText(User.kota);
		((TextView) findViewById(R.id.provinsi)).setText(User.provinsi);
		((TextView) findViewById(R.id.negara)).setText(User.negara);
		((TextView) findViewById(R.id.pendidikan)).setText(User.pendidikan);
		((TextView) findViewById(R.id.pekerjaan)).setText(User.pekerjaan);
		((TextView) findViewById(R.id.hobi)).setText(User.hobi);
		((TextView) findViewById(R.id.biografi)).setText(User.bio);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!User.ISLOGIN){
			Intent intent = new Intent(getApplicationContext(),
					KlasifikasiActivity.class);
			startActivity(intent);
			return;
		}
	}

	private void popUpEditText() {
		alert = new AlertDialog.Builder(this);

		alert.setTitle(TITLE);
		alert.setMessage("\n\n");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		input.setHint(TITLE);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.show();
	}

	public void showPopUp(View view) {
		TITLE = view.getTag().toString();
		popUpEditText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_edit, menu);
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
		case R.id.ok_done:
			intent = new Intent(getApplicationContext(), ProfilActivity.class);
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

}
