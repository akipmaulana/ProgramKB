package aprisma.akirah.bingung.timeline;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.slidingmenu.Klasifikasi;

public class TimelineFragment extends ListFragment {

	int mNum;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
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
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timeline_fragment, container, false);
        
        setListenerCustom(v);
        
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, 
                new String[] {"aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd","aKIP","AFAS","asd"}));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "Item clicked: " + id, Toast.LENGTH_SHORT).show();
    }
    
    /*
     * Method untuk set listener pada view custom yang clickable atau editable
     */
    private void setListenerCustom(View vi){
    	TextView tv = (TextView) vi.findViewById(R.id.text);
        tv.setText("Fragment #" + Klasifikasi.GET_KLASIFIKASI.get(mNum));
        tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), Klasifikasi.GET_KLASIFIKASI.get(mNum), Toast.LENGTH_SHORT).show();
			}
		});
    }
}
