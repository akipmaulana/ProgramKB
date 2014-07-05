package aprisma.akirah.bingung.timeline;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import aprisma.akirah.bingung.R;

public class TimelineList extends Fragment{

	public TimelineList() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.timeline_list, container, false);
		return rootView;
	}

}
