package aprisma.akirah.bingung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.holder.User;

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
				User.ISLOGIN = true;
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
}
