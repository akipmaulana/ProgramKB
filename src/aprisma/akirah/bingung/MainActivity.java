package aprisma.akirah.bingung;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.holder.User;

public class MainActivity extends Activity {

	private EditText etmail;
	private EditText etpass;

	private String email;
	private String userpass;

	private ProgressDialog pDialog;

	private User user;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Hide Header action bar
		getActionBar().hide();

		etmail = (EditText) findViewById(R.id.etmail);
		etpass = (EditText) findViewById(R.id.etpass);

		user = new User();

		TextView tvRegist = (TextView) findViewById(R.id.registrasi_link);
		tvRegist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						RegistrasiActivity.class);
				intent.putExtra(User.TAG_USER, user);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			}
		});

	}

	/*
	 * Button Action Login
	 */
	public void Login(View view) {
		email = etmail.getText().toString();
		userpass = etpass.getText().toString();
		if (email.isEmpty() || userpass.isEmpty()) {
			Toast.makeText(getApplicationContext(),
					"Masukan Email dan Password Anda", Toast.LENGTH_SHORT)
					.show();
		} else {
			new Login().execute();
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				KlasifikasiActivity.class);
		startActivity(intent);
	}

	private class Login extends AsyncTask<Void, Void, Void> {

		private int tag_error;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				JSONObject jsonObj = user.loginUser(email, userpass);

				tag_error = jsonObj.getInt(User.TAG_ERROR);

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

			switch (tag_error) {
			case 0:
				User.ISLOGIN = true;
				// Toast.makeText(getApplicationContext(),
				// "HALLO "+User.fullname, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(),
						KlasifikasiActivity.class);
				intent.putExtra(User.TAG_USER, user);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "Password Salah",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				AlertDialog alertDialog = new AlertDialog.Builder(
						MainActivity.this).create();
				alertDialog.setTitle("Aktivasi Akun");

				alertDialog
						.setMessage("Akun tidak terdaftar. Silahkan klik Registrasi untuk daftar");
				alertDialog.setButton("Registrasi",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int which) {
								Intent intent = new Intent(
										getApplicationContext(),
										RegistrasiActivity.class);
								intent.putExtra(User.TAG_USER, user);
								startActivity(intent);
								overridePendingTransition(R.anim.slide_in,
										R.anim.slide_in);
							}
						});
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.show();
				break;
			default:
				break;
			}
		}
	}
}
