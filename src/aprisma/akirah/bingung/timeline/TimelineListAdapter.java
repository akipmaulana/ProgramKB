package aprisma.akirah.bingung.timeline;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.R;

public class TimelineListAdapter extends ArrayAdapter<TimelineList> {

	private Context context;
	private int layoutResourceId;
	private TimelineList data[] = null;

	public TimelineListAdapter(Context context, int layoutResourceId,
			TimelineList[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TimelineListHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new TimelineListHolder();
			holder.imageku = (ImageView) row.findViewById(R.id.imageku);
			holder.namaku = (TextView) row.findViewById(R.id.namaku);
			holder.deskripsiku = (TextView) row.findViewById(R.id.deskripsiku);
			holder.rataku = (TextView) row.findViewById(R.id.rataku);
			holder.viewku = (TextView) row.findViewById(R.id.viewku);

			row.setTag(holder);
		} else {
			holder = (TimelineListHolder) row.getTag();
		}

		TimelineList list = data[position];
		if (list != null) {
			Toast.makeText(getContext(), "INIT ULANG " + position,
					Toast.LENGTH_SHORT).show();
			new DownloadImageTask(holder.imageku).execute(list.getImageku());
			holder.namaku.setText(list.getNamaku());
			Spanned deskripsi = (Spanned) Html.fromHtml(list.getDeskripsiku())
					.subSequence(0, 100);
			holder.deskripsiku.setText(deskripsi + ". . .");
			holder.rataku.setText(list.getRataku());
			holder.viewku.setText(list.getViewku());
		}
		return row;
	}

	private static class TimelineListHolder {
		private ImageView imageku;
		private TextView namaku;
		private TextView deskripsiku;
		private TextView rataku;
		private TextView viewku;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}

}
