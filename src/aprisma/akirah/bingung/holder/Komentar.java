package aprisma.akirah.bingung.holder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.ImageView;

public class Komentar extends KlikBParent{

	private String id_komentar;
	private String rating;
	private String judul_komen;
	private String isi_komen;
	private String date_create;
	private String fullname;
	private String website;
	private String avatar;
	private ImageView icon;
	
	public Komentar(String id_komentar, String rating, String judul_komen,
			String isi_komen, String date_create, String fullname, String website, String avatar) {
		this.id_komentar = id_komentar;
		this.rating = rating;
		this.judul_komen = judul_komen;
		this.isi_komen = isi_komen;
		this.date_create = date_create;
		this.fullname = fullname;
		this.website = website;
		this.avatar = "http://klikbingung.com/"+avatar;
		
	}

	public String getId_komentar() {
		return id_komentar;
	}

	public String getRating() {
		return rating;
	}

	public String getJudul_komen() {
		return judul_komen;
	}

	public String getIsi_komen() {
		return isi_komen;
	}

	public String getDate_create() {
		return date_create;
	}

	public String getFullname() {
		return fullname;
	}

	public String getWebsite() {
		return website;
	}
	
	public void setIcon(ImageView image){
		icon = image;
		new DownloadImageTask().execute(this.avatar);
	}
	
	public static JSONObject getCommentJSON(int id_posting){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", COMMENT_TAG));
		params.add(new BasicNameValuePair("id_posting", id_posting+""));
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		return json;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		public Bitmap mIcon11 = null;

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
				int width = mIcon11.getWidth();
				int height = mIcon11.getHeight();
				int newWidth = 110;
				int newHeight = 110;
				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;

				Matrix bMatrix = new Matrix();
				bMatrix.postScale(scaleWidth, scaleHeight);

				mIcon11 = Bitmap.createBitmap(mIcon11, 0, 0, width, height,
						bMatrix, true);
			} catch (Exception e) {
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			icon.setImageBitmap(result);
		}

	}

}
