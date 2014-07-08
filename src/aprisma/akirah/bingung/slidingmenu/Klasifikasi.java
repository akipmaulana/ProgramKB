package aprisma.akirah.bingung.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;

@SuppressLint("NewApi")
public class Klasifikasi extends Fragment {

	public final static String KLASIFIKASI_REQUEST = "klasifikasi_request";
	public static ArrayList<String> GET_KLASIFIKASI = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.klasifikasi_activity,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		setKlasifikasi();
		setToList();

	}

	/*
	 * Untuk menyimpan Klasifikasi dari serverke dalam array GET_KLASIFIKASI
	 */
	private void setKlasifikasi() {
		GET_KLASIFIKASI.clear();
		GET_KLASIFIKASI.add("Makanan");
		GET_KLASIFIKASI.add("Jalan-jalan");
		GET_KLASIFIKASI.add("Hiburan");
		GET_KLASIFIKASI.add("Hotel");
		GET_KLASIFIKASI.add("Bisnis");
		GET_KLASIFIKASI.add("Akip");
		GET_KLASIFIKASI.add("Farah");
		GET_KLASIFIKASI.add("Akirah");
		GET_KLASIFIKASI.add("Puppy");
		GET_KLASIFIKASI.add("Pimmy");
		GET_KLASIFIKASI.add("Minyi");
		GET_KLASIFIKASI.add("Pinoy");
		GET_KLASIFIKASI.add("Mumuy");
		GET_KLASIFIKASI.add("Munyun");
		GET_KLASIFIKASI.add("Lainnya");
	}

	/*
	 * set klasifikasi ke dalam list view
	 */
	private void setToList() {
		ListAdapter adapter = new ListAdapter(getActivity(), GET_KLASIFIKASI);

		final ListView listView = (ListView) getActivity().findViewById(
				R.id.list_klasifikasi);
		listView.setAdapter(adapter);

		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Boolean Found = false;
				int indexOfsearch = 0;
				for (String s : GET_KLASIFIKASI) {
					if (position == indexOfsearch && !Found) {
						Found = true;
						Intent intent = new Intent(getActivity(),
								TimelineAcitivity.class);
						intent.putExtra(KLASIFIKASI_REQUEST, s);
						startActivity(intent);
					}
					indexOfsearch++;
				}

			}
		};

		listView.setOnItemClickListener(listener);

	}

	/*
	 * Custom class untuk adapter list view
	 */
	private static class ListAdapter extends BaseAdapter {

		Context ctx;
		LayoutInflater lInflater;
		List<String> data;

		ListAdapter(Context context, List<String> data) {
			ctx = context;
			this.data = data;
			lInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = lInflater
						.inflate(R.layout.tv_klasifikasi, parent, false);
			}

			if (position % 2 == 0) {
				view.setBackgroundColor(0xFFFFFFFF);
			} else {
				view.setBackgroundColor(0xFFF3F3F3);
			}
			
			TextView tv = (TextView) view.findViewById(R.id.iniAkuTv);
			tv.setText(data.get(position));

			return view;
		}

	}

}
