package aprisma.akirah.bingung.timeline;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
			holder.imageku.setImageBitmap(list.getBmImage());
			holder.namaku.setText(list.getNamaku());
			Spanned deskripsi = null;
			if (list.getDeskripsiku().length()>100){
				deskripsi = (Spanned) Html.fromHtml(list.getDeskripsiku())
						.subSequence(0, 100);
			} else {
				deskripsi = (Spanned) Html.fromHtml(list.getDeskripsiku());
			}
			
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
}
