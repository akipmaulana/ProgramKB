package aprisma.akirah.bingung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.detail.PengaturanActivity;

public class RegistrasiActivity extends Activity {

	public RegistrasiActivity() {
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrasi_activity);

		getActionBar().hide();
	}

	/*
	 * Button Action Registrasi
	 */
	@SuppressWarnings("deprecation")
	public void Registrasi(View view) {
		AlertDialog alertDialog = new AlertDialog.Builder(
				RegistrasiActivity.this).create();
		alertDialog.setTitle("Aktivasi Akun");

		alertDialog.setMessage("Tekan \"Lanjut\" untuk mengaktifkan akun.");
		alertDialog.setButton("Lanjut", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getApplicationContext(), KlasifikasiActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(), PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			break;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
