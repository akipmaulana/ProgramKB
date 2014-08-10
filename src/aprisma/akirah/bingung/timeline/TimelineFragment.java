package aprisma.akirah.bingung.timeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.detail.DetailActivity;
import aprisma.akirah.bingung.detail.MapActivity;
import aprisma.akirah.bingung.holder.Klasifikasi;

public class TimelineFragment extends ListFragment {

	private TimelineList[] catalogs;

	int mNum;

	int m_PreviousTotalCount = 0;

	// footer view
	private RelativeLayout mFooterView;
	private ProgressBar progress;
	private Context ctx;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static TimelineFragment newInstance(int num) {
		TimelineFragment f = new TimelineFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);

		f.setArguments(args);

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;

	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timeline_fragment, container, false);
		ctx = getActivity().getApplicationContext();
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		LayoutInflater inflater = getLayoutInflater(savedInstanceState);
		mFooterView = (RelativeLayout) inflater.inflate(R.layout.progress_bar,
				getListView(), false);
		progress = (ProgressBar) mFooterView
				.findViewById(R.id.load_more_progressBar);
		getListView().addFooterView(mFooterView);
		setListenerCustom();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String namaku = ((TextView) v.findViewById(R.id.namaku)).getText()
				.toString();
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra(Klasifikasi.TAG_NAME, namaku);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in,
				R.anim.slide_in);
	}

	/*
	 * Method untuk set listener pada view custom yang clickable atau editable
	 */
	private void setListenerCustom() {

		loadData();

		TimelineListAdapter adapter = new TimelineListAdapter(getActivity(),
				R.layout.timeline_list, catalogs);

		setListAdapter(adapter);

		getListView().setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (totalItemCount == 0 || getListAdapter() == null
						|| m_PreviousTotalCount == totalItemCount) {
					return;
				}

				Boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

				if (loadMore) {
					m_PreviousTotalCount = totalItemCount;

					// Toast.makeText(
					// getActivity(),
					// "ini " + m_PreviousTotalCount + " adalah onScroll "
					// + Klasifikasi.ID_KLASIFIKASI.get(mNum),
					// Toast.LENGTH_SHORT).show();

					new LoadMore().execute();
				}

			}
		});
	}

	private void loadData() {
		for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
			if (mNum == i) {
				initSearchById(i);
				int index = 0;
				for (int j = 0; j < MapActivity.timelines.size(); j++) {
					if (MapActivity.timelines.get(j).getId() == i) {
						catalogs[index++] = MapActivity.timelines.get(j);
					}
				}
			}
		}
	}

	/* menghitung jumlah catalog seesuai kategori */
	private void initSearchById(int id) {
		int hasil = 0;

		for (int i = 0; i < MapActivity.timelines.size(); i++) {
			if (MapActivity.timelines.get(i).getId() == id) {
				hasil++;
			}
		}

		// System.out.println("GET ID " + id + " = " + "" + hasil);

		catalogs = new TimelineList[hasil];

	}

	private class LoadMore extends AsyncTask<Void, Void, Void> {

		int isLoadMore = 0;// apakah data yang di load jika > 0 maka ya

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			Klasifikasi klasifikasi = new Klasifikasi();
			JSONObject jsonObj = klasifikasi.getTimelineJSON(
					Klasifikasi.ID_KLASIFIKASI.get(mNum), m_PreviousTotalCount
							+ "", "english");
			try {
				if (jsonObj.getInt(Klasifikasi.TAG_SUCCESS) == 1) {
					JSONArray catalogs = jsonObj
							.getJSONArray(Klasifikasi.TAG_CATALOGS);

					for (int i = 0; i < catalogs.length(); i++) {
						JSONObject c = catalogs.getJSONObject(i);
						int id_posting = c.getInt(Klasifikasi.TAG_ID_POSTING);
						String imageku = c
								.getString(Klasifikasi.TAG_FILLNAME_IMG);
						String namaku = c.getString(Klasifikasi.TAG_JUDUL);
						String deskripsiku = c
								.getString(Klasifikasi.TAG_ISI_POSTING);
						String rataku = c.getString(Klasifikasi.TAG_COUNTER)
								+ " View";
						String viewku = c.getString("like") + " Like";
						String lat = c.getString(Klasifikasi.TAG_LAT);
						String lon = c.getString(Klasifikasi.TAG_LON);

						MapActivity.timelines.add(new TimelineList(mNum,
								id_posting, imageku, namaku, deskripsiku,
								rataku, viewku, true, lat, lon, ctx));

						isLoadMore++;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (isLoadMore > 0) {
				Toast.makeText(getActivity(), "Load More", Toast.LENGTH_SHORT)
						.show();
				setListenerCustom();
			}
			progress.setVisibility(View.GONE);
		}

	}
}
