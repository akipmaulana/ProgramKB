package aprisma.akirah.bingung.detail;

import java.io.InputStream;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.Komentar;
import aprisma.akirah.bingung.holder.Posting;
import aprisma.akirah.bingung.holder.User;
import aprisma.akirah.bingung.service.CheckConnection;

@SuppressLint("NewApi")
public class DetailActivity extends Activity {

	public static String namaku;
	private int id_posting;
	private String filename_img;

	private Posting posting;

	private Bitmap image;

	private Menu menu;
	private Boolean isReadyMenu = false;

	private TextView connectLay;
	
	private float nilai;
	private int isLike;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (getIntent().getFloatExtra("rating", 0f) != 0f) {
			nilai = getIntent().getFloatExtra("rating", 0f);
		}

		namaku = getIntent().getStringExtra(Klasifikasi.TAG_NAME);

		setIdPosting(namaku);

		new PostingServer().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.review_menu, menu);

		PengaturanActivity.SetMenu(menu);

		this.menu = menu;
		isReadyMenu = true;

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
		case R.id.koment:
			comment();
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
			//ksh ini gk ya ? 
			intent.putExtra("detail", 1);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isReadyMenu) {
			menu.clear();
			onCreateOptionsMenu(menu);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}

	private void setIdPosting(String namaku) {
		for (int i = 0; i < MapActivity.timelines.size(); i++) {
			if (MapActivity.timelines.get(i).getNamaku().equals(namaku)) {
				id_posting = MapActivity.timelines.get(i).getIdPosting();
				filename_img = MapActivity.timelines.get(i).getImageku();
			}
		}
	}

	private void initView() {
		setContentView(R.layout.detail_activity);

		connectLay = (TextView) findViewById(R.id.connect);

		new CheckConnection(
				connectLay,
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
				this);

		RatingBar reting_res = (RatingBar) findViewById(R.id.reting_res);
		if (nilai != 0f){
			reting_res.setRating(nilai);
		}
		TextView judul = (TextView) findViewById(R.id.judul);
		judul.setText(posting.getJudul());
		TextView views = (TextView) findViewById(R.id.views);
		views.setText(posting.getCounter());
		TextView liked = (TextView) findViewById(R.id.liked);
		liked.setText(posting.getLike());
		if (isLike != 0) {
			((Button) findViewById(R.id.setLike)).setText("Unlike");
		}
		TextView rate = (TextView) findViewById(R.id.my_rating);
		rate.setText(Float.parseFloat(posting.getRating()) + "");
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
	
	public void rateLayout(View view){
		Intent intent = null;
		if (User.ISLOGIN) {
			RatingActivity rateShow;
			if (nilai == 0.f){
				rateShow = new RatingActivity(this, id_posting+"");
			} else {
				rateShow = new RatingActivity(this, nilai, id_posting+"");
			}
			rateShow.show();
		} else {
			intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.putExtra("detail", 1);
			startActivity(intent);
		}
	}

	private ProgressDialog pDialog;

	private class PostingServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailActivity.this);
			pDialog.setMessage("Please wait . . .");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			posting = new Posting();
			JSONObject json = posting.getReviewJSON(id_posting + "", "english");
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
					posting.setLike(jsonOBJ.getString("like"));
					new DownloadImageTask().execute(filename_img);
				}
				
				if (json.getString("tag_rate") != null) {
					nilai = Float.parseFloat(json.getString("tag_rate"));
				}
				
				if (json.getString("tag_like") != null) {
					isLike = Integer.parseInt(json.getString("tag_like"));
				}
				
			} catch (Exception e) {
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

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		public Bitmap mIcon11 = null;

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			image = result;
			ImageView gambar = (ImageView) findViewById(R.id.img_detail);
			gambar.setBackground(new BitmapDrawable(getResources(), image));
		}

	}

	private class SentLike extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailActivity.this);
			pDialog.setMessage("Please Wait . . .");
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Komentar.sentLike(User.id_user, id_posting+"");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
			TextView liked;
			Button setLike;
			liked = (TextView) findViewById(R.id.liked);
			setLike = (Button) findViewById(R.id.setLike);
			if (isLike == 0) {
				setLike.setText("Unlike");
				liked.setText(Integer.parseInt(liked.getText().toString()) + 1 + "");
				Posting.isLike = false;
				isLike = 1;
			} else {
				isLike = 0;
				Posting.isLike = true;
				setLike.setText("Like");
				liked.setText(Integer.parseInt(liked.getText().toString()) - 1 + "");
			}
			
		}
		
	}
	
	private void comment() {
		Intent intent = null;
		if (User.ISLOGIN) {
			intent = new Intent(this, CommentActivity.class);
			intent.putExtra("id_posting", id_posting);
			intent.putExtra("id_user", Integer.parseInt(User.id_user));
			startActivity(intent);
			overridePendingTransition(R.anim.slide_bottom, R.anim.slide_bottom);
		} else {
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("detail", 1);
			startActivity(intent);
		}
	}

	public void browsing(View view) {
		try {
			String url = ((TextView) view.findViewById(R.id.situs_posting))
					.getText().toString();
			if (url != "") {
				url = "http://"+url;
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		} catch (Exception ex) {
			
		}
	}

	public void liked(View view) {
		Intent intent = null;
		if (User.ISLOGIN) {
			new SentLike().execute();
		} else {
			intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.putExtra("detail", 1);
			startActivity(intent);
		}
	}

	public void getDirection(View v) {
		String url = "http://maps.google.com/maps?saddr=" + MapActivity.lat
				+ "," + MapActivity.lon + "&daddr=" + posting.getLat() + ","
				+ posting.getLon();

		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(url));
		// par1 : nama package ; par2 : nama kelas
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}
}
