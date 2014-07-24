package aprisma.akirah.bingung.timeline;


public class TimelineList{

	private int id;
	private String imageku;
	private String namaku;
	private String deskripsiku;
	private String rataku;
	private String viewku;
	
	
	public TimelineList(int id, String imageku, String namaku, String deskripsiku, String rataku, String viewku) {
		this.id = id;
		this.imageku = imageku;
		this.namaku = namaku;
		this.deskripsiku = deskripsiku;
		this.rataku = rataku;
		this.viewku = viewku;
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
