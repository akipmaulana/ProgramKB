package aprisma.akirah.bingung.timeline;


public class TimelineList{

	private int imageku;
	private String namaku;
	private String deskripsiku;
	private String rataku;
	private String viewku;
	
	
	public TimelineList(int imageku, String namaku, String deskripsiku, String rataku, String viewku) {
		this.imageku = imageku;
		this.namaku = namaku;
		this.deskripsiku = deskripsiku;
		this.rataku = rataku;
		this.viewku = viewku;
	}


	public int getImageku() {
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
