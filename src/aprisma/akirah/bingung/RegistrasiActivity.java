package aprisma.akirah.bingung;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.holder.User;

public class RegistrasiActivity extends Activity {

	private ProgressDialog pDialog;

	private User user;

	private EditText et_fullname;
	private EditText et_email;
	private EditText et_userpass;
	private EditText et_re_userpass;

	private String fullname;
	private String email;
	private String userpass;
	private String re_userpass;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrasi_activity);

		getActionBar().hide();

		user = (User) getIntent().getSerializableExtra(User.TAG_USER);

		et_fullname = (EditText) findViewById(R.id.reg_full);
		et_email = (EditText) findViewById(R.id.reg_mail);
		et_userpass = (EditText) findViewById(R.id.reg_pass);
		et_re_userpass = (EditText) findViewById(R.id.reg_pass2);

	}

	/*
	 * Button Action Registrasi
	 */
	public void Registrasi(View view) {

		fullname = et_fullname.getText().toString();
		email = et_email.getText().toString();
		userpass = et_userpass.getText().toString();
		re_userpass = et_re_userpass.getText().toString();

		if (fullname.isEmpty() || email.isEmpty() || userpass.isEmpty()
				|| re_userpass.isEmpty()) {
			Toast.makeText(getApplicationContext(),
					"Masukan Data Anda Dengan Lengkap", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (userpass.equals(re_userpass)) {
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo nf = cm.getActiveNetworkInfo();
				if (nf != null && nf.isConnected() == true) {
					new Registrasi().execute();
				} else {
					Toast.makeText(getApplicationContext(),
							"No Internet Connection", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Konfirmasi Password Salah", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}

	private class Registrasi extends AsyncTask<Void, Void, Void> {

		private int tag_success;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(RegistrasiActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				JSONObject jsonObj = user.registerUser(fullname, email,
						userpass);

				tag_success = jsonObj.getInt(User.TAG_SUCCESS);

				user.fetchDataUser(jsonObj);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			switch (tag_success) {
			case 0:
				Toast.makeText(getApplicationContext(), "Email Sudah Ada",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				AlertDialog alertDialog = new AlertDialog.Builder(
						RegistrasiActivity.this).create();
				alertDialog.setTitle("Aktivasi Akun");

				alertDialog
						.setMessage("Tekan \"Lanjut\" untuk mengaktifkan akun.");
				alertDialog.setButton("Lanjut",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int which) {
								User.ISLOGIN = true;
								final Intent intent = new Intent(
										getApplicationContext(),
										KlasifikasiActivity.class);
								startActivity(intent);
								overridePendingTransition(R.anim.slide_in,
										R.anim.slide_in);
							}
						});
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.show();
				break;
			case 2:
				break;
			default:
				break;
			}
		}

	}
}
