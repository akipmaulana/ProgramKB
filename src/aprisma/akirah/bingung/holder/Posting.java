package aprisma.akirah.bingung.holder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class Posting extends KlikBParent{

	public static boolean isLike = true;
	
	private String id_posting;
	private String judul;
	private String date_create;
	private String isi_posting;
	private String keyword;
	private String nama_merchant;
	private String alamat;
	private String kota;
	private String provinsi;
	private String negara;
	private String telepon;
	private String website;
	private String lat;
	private String lon;
	private String counter; // view
	private String like; // like
	private String name_catalog;
	private String fullname;
	private String rating;
	private String jum_kom;
	private String id_catalog;
	private String price;
	
	public Posting() {
	}
	
	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getId_posting() {
		return id_posting;
	}

	public String getJudul() {
		return judul;
	}

	public String getDate_create() {
		return date_create;
	}

	public String getIsi_posting() {
		return isi_posting;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getNama_merchant() {
		return nama_merchant;
	}

	public String getAlamat() {
		return alamat;
	}

	public String getKota() {
		return kota;
	}

	public String getProvinsi() {
		return provinsi;
	}

	public String getNegara() {
		return negara;
	}

	public String getTelepon() {
		if (telepon == ""){
			telepon = "0";
		}
		return telepon;
	}

	public String getWebsite() {
		if (website == ""){
			website = "-";
		}
		return website;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	public String getCounter() {
		return counter;
	}

	public String getName_catalog() {
		return name_catalog;
	}

	public String getFullname() {
		return fullname;
	}

	public String getRating() {
		if (rating == "null" || rating == ""){
			rating = "0";
		}
		return rating;
	}

	public String getJum_kom() {
		return jum_kom;
	}

	public String getId_catalog() {
		return id_catalog;
	}

	public String getPrice() {
		return price;
	}

	public void setId_posting(String id_posting) {
		this.id_posting = id_posting;
	}

	public void setJudul(String judul) {
		this.judul = judul;
	}

	public void setDate_create(String date_create) {
		this.date_create = date_create;
	}

	public void setIsi_posting(String isi_posting) {
		this.isi_posting = isi_posting;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setNama_merchant(String nama_merchant) {
		this.nama_merchant = nama_merchant;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public void setKota(String kota) {
		this.kota = kota;
	}

	public void setProvinsi(String provinsi) {
		this.provinsi = provinsi;
	}

	public void setNegara(String negara) {
		this.negara = negara;
	}

	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public void setName_catalog(String name_catalog) {
		this.name_catalog = name_catalog;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setJum_kom(String jum_kom) {
		this.jum_kom = jum_kom;
	}

	public void setId_catalog(String id_catalog) {
		this.id_catalog = id_catalog;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public JSONObject getReviewJSON(String id_posting, String lang){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", REVIEW_TAG));
		params.add(new BasicNameValuePair("id_posting", id_posting));
		params.add(new BasicNameValuePair("lang", lang));
		params.add(new BasicNameValuePair("id_user", User.id_user));
		JSONObject json = JSONPARSER.getJSONFromUrl(URL, params);
		return json;
	}
	
	

}

