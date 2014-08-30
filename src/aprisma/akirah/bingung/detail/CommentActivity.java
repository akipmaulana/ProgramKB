package aprisma.akirah.bingung.detail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.Komentar;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;

@SuppressLint("NewApi")
public class CommentActivity extends Activity {

	private int id_posting;

	private ArrayList<Komentar> comments = new ArrayList<Komentar>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);

		// Hide Header action bar
		getActionBar().hide();

		TextView connectLay = (TextView) findViewById(R.id.connect);

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);

		id_posting = getIntent().getIntExtra("id_posting", 0);

		new CommentServer().execute();
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
	}

	private void initView() {
		LinearLayout koment_list = (LinearLayout) findViewById(R.id.list_comment);

		for (int i = 0; i < comments.size(); i++) {
			Komentar komentar = comments.get(i);
			View item = getLayoutInflater().inflate(R.layout.koment_item,
					koment_list, false);
			((TextView) item.findViewById(R.id.nama_comment)).setText(komentar
					.getFullname());
			((TextView) item.findViewById(R.id.waktu_comment)).setText(komentar
					.getDate_create());
			((TextView) item.findViewById(R.id.isi_comment)).setText(komentar
					.getIsi_komen());
			komentar.setIcon(((ImageView) item.findViewById(R.id.img_coment)));
			koment_list.addView(item, i);
		}
	}

	private ProgressDialog pDialog;

	private class CommentServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(CommentActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = Komentar.getCommentJSON(id_posting);
			try {
				if (json.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
					JSONArray koments = json.getJSONArray("koments");
					for (int i = 0; i < koments.length(); i++) {
						for (int z = 0; z < 3; z++) {
							JSONObject j = koments.getJSONObject(i);
							Komentar komentar = new Komentar(
									j.getString("id_komentar"),
									j.getString("rating"),
									j.getString("judul_komen"),
									j.getString("isi_komen"),
									j.getString("date_create"),
									j.getString("fullname"),
									j.getString("website"),
									j.getString("avatar"));
							comments.add(komentar);
						}
					}
				}
			} catch (Exception ex) {
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

			initView();
		}

	}

}
