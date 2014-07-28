package aprisma.akirah.bingung.timeline;

import com.google.android.gms.maps.model.LatLng;


public class TimelineList{

	private int id;
	private String imageku;
	private String namaku;
	private String deskripsiku;
	private String rataku;
	private String viewku;
	
	private String lat;
	private String lon;
	
	private LatLng latlon;
	
	public TimelineList(int id, String imageku, String namaku, String deskripsiku, String rataku, String viewku,
			String lat, String lon) {
		this.id = id;
		this.imageku = imageku;
		this.namaku = namaku;
		this.deskripsiku = deskripsiku;
		this.rataku = rataku;
		this.viewku = viewku;
		this.lat = lat;
		this.lon = lon;
	}
	
	public LatLng getLatLon() {
		latlon = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
		return latlon;
	}
	
	public String getLat(){
		return lat;
	}
	
	public String getLon() {
		return lon;
	}

	public int getId() {
		return id;
	}

	public String getImageku() {
		return imageku;
	}


	public String getNamaku() {
		return namaku;
	}


	public String getDeskripsiku() {
		return deskripsiku;
	}


	public String getRataku() {
		return rataku;
	}


	public String getViewku() {
		return viewku;
	}
	
	
}
