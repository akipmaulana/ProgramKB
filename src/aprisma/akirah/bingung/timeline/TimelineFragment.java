package aprisma.akirah.bingung.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.detail.DetailActivity;
import aprisma.akirah.bingung.detail.MapActivity;
import aprisma.akirah.bingung.holder.Klasifikasi;

public class TimelineFragment extends ListFragment {

	private TimelineList[] catalogs;

	int mNum;

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
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
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

		for (int i = 0; i < Klasifikasi.GET_KLASIFIKASI.size(); i++) {
			if (mNum == i) {
				initSearchById(i);
				int index = 0;
				for (int j = 0; j < MapActivity.timelines.length; j++) {
					if (MapActivity.timelines[j].getId() == i) {
						catalogs[index++] = MapActivity.timelines[j];
					}
				}
			}
		}

		TimelineListAdapter adapter = new TimelineListAdapter(getActivity(),
				R.layout.timeline_list, catalogs);

		setListAdapter(adapter);

	}

	/* menghitung jumlah catalog seesuai kategori */
	private void initSearchById(int id) {
		int hasil = 0;

		for (int i = 0; i < MapActivity.timelines.length; i++) {
			if (MapActivity.timelines[i].getId() == id) {
				hasil++;
			}
		}

		System.out.println("GET ID " + id + " = " + "" + hasil);

		catalogs = new TimelineList[hasil];

	}
}
