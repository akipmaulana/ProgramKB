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
import aprisma.akirah.bingung.detail.KlasifikasiActivity;

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
        
        
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	
    	String namaku = ((TextView) v.findViewById(R.id.namaku)).getText().toString();
        Toast.makeText(getActivity(), "Item clicked: " + namaku, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
    }
    
    /*
     * Method untuk set listener pada view custom yang clickable atau editable
     */
    private void setListenerCustom(View vi){
    	
    	TimelineList[] list_line = new TimelineList[KlasifikasiActivity.GET_KLASIFIKASI.size()];
        for (int i=0;i<KlasifikasiActivity.GET_KLASIFIKASI.size();i++){
        	list_line[i]=new TimelineList(
        			R.id.imageku, KlasifikasiActivity.GET_KLASIFIKASI.get(mNum), "farah", "Akirah", "Munyu");
        }
        
        TimelineListAdapter adapter = new TimelineListAdapter(getActivity(), R.layout.timeline_list, list_line);
        
        setListAdapter(adapter);
    }
    
}
