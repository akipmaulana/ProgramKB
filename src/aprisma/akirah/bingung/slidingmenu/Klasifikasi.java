package aprisma.akirah.bingung.slidingmenu;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.timeline.TimelineAcitivity;

@SuppressLint("NewApi")
public class Klasifikasi extends Fragment {

	public final static String KLASIFIKASI_REQUEST = "klasifikasi_request";
	public static ArrayList<String> GET_KLASIFIKASI = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.klasifikasi_activity, container, false);
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		setKlasifikasi();
		getKlasifikasi();
		
	}
	
	/*
	 * Untuk menyimpan Klasifikasi dari serverke dalam array GET_KLASIFIKASI
	 */
	private void setKlasifikasi(){
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
	 * Untuk membuat button dari klasifikasi server
	 */
	private void getKlasifikasi() {

		LinearLayout klas_button = (LinearLayout) getActivity().findViewById(R.id.klasi_button);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 16, 0, 0); // seharusnya dinamis jangan statis

		for (int i = 0; i < GET_KLASIFIKASI.size(); i++) {
			Button vb = new Button(getActivity());
			vb.setBackgroundColor(0xFFF36B27); //Orange
			vb.setTextColor(0xFFFFFFFF); //White
			vb.setText(GET_KLASIFIKASI.get(i));
			vb.setId(i);
			vb.setLayoutParams(params);
			vb.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Boolean Found = false;
					int indexOfsearch = 0;
					for(String s:GET_KLASIFIKASI){
						if (v.getId()==indexOfsearch && !Found){
							Found = true;
							Intent intent = new Intent(getActivity(), TimelineAcitivity.class);
							intent.putExtra(KLASIFIKASI_REQUEST, s);
							startActivity(intent);
						}
						indexOfsearch++;
					}
				}
			});

			klas_button.addView(vb);

		}
	}

}
