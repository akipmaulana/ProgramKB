package aprisma.akirah.bingung.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.detail.DetailActivity;
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
		// args.putParcelableArray("timeline", (Parcelable[]) time);

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
		// timelines = (TimelineList[]) (getArguments() != null ?
		// getArguments().getParcelableArray("timeline") : null);

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
		Toast.makeText(getActivity(), "Item clicked: " + namaku,
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getActivity(), DetailActivity.class);
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
				for (int j = 0; j < TimelineAcitivity.timelines.length; j++) {
					if (TimelineAcitivity.timelines[j].getId() == i) {
						catalogs[index++] = TimelineAcitivity.timelines[j];
					}
				}
				// timelines = new TimelineList[i];
				// int count = getSearchById(i);
				// catalogs = new TimelineList[count];
				// for (int j = 0; j < count; j++) {
				//
				// catalogs[j] = timelines[i + j];
				//
				// timelines[j] = new TimelineList(0, R.id.imageku,
				// Klasifikasi.GET_KLASIFIKASI.get(mNum),
				// Klasifikasi.GET_KLASIFIKASI.get(mNum),
				// Klasifikasi.GET_KLASIFIKASI.get(mNum),
				// Klasifikasi.GET_KLASIFIKASI.get(mNum));
				// }
			}
		}

		TimelineListAdapter adapter = new TimelineListAdapter(getActivity(),
				R.layout.timeline_list, catalogs);

		setListAdapter(adapter);

	}

	/* menghitung jumlah catalog seesuai kategori */
	private void initSearchById(int id) {
		int hasil = 0;

		for (int i = 0; i < TimelineAcitivity.timelines.length; i++) {
			if (TimelineAcitivity.timelines[i].getId() == id) {
				hasil++;
			}
		}

		System.out.println("GET ID " + id + " = " + "" + hasil);

		catalogs = new TimelineList[hasil];

	}
}
