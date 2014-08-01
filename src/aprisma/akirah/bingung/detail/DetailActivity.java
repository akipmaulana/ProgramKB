package aprisma.akirah.bingung.detail;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.Posting;
import aprisma.akirah.bingung.holder.User;

@SuppressLint("NewApi")
public class DetailActivity extends Activity {

	private String namaku;
	private int id_posting;
	private String filename_img;

	private Posting posting;

	private Bitmap image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		namaku = getIntent().getStringExtra(Klasifikasi.TAG_NAME);

		setIdPosting(namaku);

		new PostingServer().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		PengaturanActivity.SetMenu(menu);

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

	public void comment(View view) {

		Intent intent = null;
		if (User.ISLOGIN) {
			intent = new Intent(this, CommentActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_bottom, R.anim.slide_bottom);
		} else {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}

	}

	private void setIdPosting(String namaku) {
		for (int i = 0; i < MapActivity.timelines.length; i++) {
			if (MapActivity.timelines[i].getNamaku().equals(namaku)) {
				id_posting = MapActivity.timelines[i].getIdPosting();
				filename_img = MapActivity.timelines[i].getImageku();
			}
		}
	}

	private void initView() {
		setContentView(R.layout.detail_activity);

		TextView judul = (TextView) findViewById(R.id.judul);
		judul.setText(posting.getJudul());
		ImageView gambar = (ImageView) findViewById(R.id.img_detail);
		gambar.setImageBitmap(image);
		TextView views = (TextView) findViewById(R.id.views);
		views.setText(posting.getRating());
		TextView liked = (TextView) findViewById(R.id.liked);
		liked.setText(posting.getJum_kom());
		TextView nama = (TextView) findViewById(R.id.nama_posting);
		nama.setText(posting.getNama_merchant());
		TextView alamat = (TextView) findViewById(R.id.alamat_post);
		alamat.setText(posting.getAlamat() + ", " + posting.getKota() + ", "
				+ posting.getProvinsi() + ", " + posting.getNegara());
		TextView telpon = (TextView) findViewById(R.id.telp_posting);
		telpon.setText(posting.getTelepon());
		TextView situs_posting = (TextView) findViewById(R.id.situs_posting);
		situs_posting.setText(posting.getWebsite());
		TextView isi_posting = (TextView) findViewById(R.id.isi_posting);
		isi_posting.setText(posting.getIsi_posting());
	}

	private ProgressDialog pDialog;

	private class PostingServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			posting = new Posting();
			JSONObject json = posting.getReviewJSON(id_posting + "",
					"english");
			try {
				if (json.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
					JSONObject jsonOBJ = json.getJSONObject("posting");
					posting.setJudul(jsonOBJ.getString("judul"));
					posting.setDate_create(jsonOBJ.getString("date_create"));
					posting.setIsi_posting(jsonOBJ.getString("isi_posting"));
					posting.setKeyword(jsonOBJ.getString("keyword"));
					posting.setNama_merchant(jsonOBJ.getString("nama_merchant"));
					posting.setAlamat(jsonOBJ.getString("alamat"));
					posting.setKota(jsonOBJ.getString("kota"));
					posting.setProvinsi(jsonOBJ.getString("provinsi"));
					posting.setNegara(jsonOBJ.getString("negara"));
					posting.setTelepon(jsonOBJ.getString("telepon"));
					posting.setWebsite(jsonOBJ.getString("website"));
					posting.setLat(jsonOBJ.getString("lat"));
					posting.setLon(jsonOBJ.getString("lon"));
					posting.setCounter(jsonOBJ.getString("counter"));
					posting.setName_catalog(jsonOBJ.getString("name_catalog"));
					posting.setFullname(jsonOBJ.getString("fullname"));
					posting.setRating(jsonOBJ.getString("rating"));
					posting.setJum_kom(jsonOBJ.getString("jum_kom"));
					posting.setPrice(jsonOBJ.getString("price"));
					image = getImageView(filename_img);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		private Bitmap getImageView(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
			}
			return mIcon11;
		}

	}

}
