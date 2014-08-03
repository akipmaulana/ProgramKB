package aprisma.akirah.bingung.timeline;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

public class TimelineList {

	private int id;
	private int id_posting;
	private String imageku;
	private String namaku;
	private String deskripsiku;
	private String rataku;
	private String viewku;
	private Boolean tagOfLoad; // untuk mengetahui apakah udah di load atau blum
								// ?
	private Bitmap bmImage;

	private String lat;
	private String lon;

	private LatLng latlon;

	public TimelineList(int id, int id_posting, String imageku, String namaku,
			String deskripsiku, String rataku, String viewku,
			Boolean tagOfLoad, String lat, String lon) {
		this.id = id;
		this.id_posting = id_posting;
		this.imageku = imageku;
		this.namaku = namaku;
		this.deskripsiku = deskripsiku;
		this.rataku = rataku;
		this.viewku = viewku;
		this.lat = lat;
		this.lon = lon;
		this.tagOfLoad = tagOfLoad;
		//setBmImage();
	}

	public Boolean getTagOfLoad() {
		return tagOfLoad;
	}

	public void setTagOfLoad() {
		tagOfLoad = false;
	}

	public LatLng getLatLon() {
		latlon = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
		return latlon;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	public int getIdPosting() {
		return id_posting;
	}

	public int getId() {
		return id;
	}

	public String getImageku() {
		return imageku;
	}

	public String getNamaku() {
		return namaku;
	}

	public String getDeskripsiku() {
		return deskripsiku;
	}

	public String getRataku() {
		return rataku;
	}

	public String getViewku() {
		return viewku;
	}

	public Bitmap getBmImage() {
		return bmImage;
	}

	public void setBmImage() {
		new DownloadImageTask().execute(getImageku());
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
			bmImage = result;
		}

	}

}
