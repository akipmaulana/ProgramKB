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
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.detail.DetailActivity;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.User;

public class MainActivity extends Activity {

	private EditText etmail;
	private EditText etpass;

	private String email;
	private String userpass;

	private ProgressDialog pDialog;

	private User user;
	
	private int isBackDetail;

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
		
		if (getIntent().getIntExtra("detail", 0) != 0) {
			isBackDetail = getIntent().getIntExtra("detail", 0);
		}

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
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo nf = cm.getActiveNetworkInfo();
			if (nf != null && nf.isConnected() == true) {
				new Login().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"No Internet Connection", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	private void backDetail(){
		Intent intent = new Intent(
				this,
				DetailActivity.class);
		intent.putExtra(Klasifikasi.TAG_NAME, DetailActivity.namaku);
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
				if (isBackDetail != 0){
					backDetail();
				} else {
					onBackPressed();
				}
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
