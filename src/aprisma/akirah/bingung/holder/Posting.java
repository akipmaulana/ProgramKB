package aprisma.akirah.bingung.holder;

public class Posting extends KlikBParent{

	private String id_posting;
	private String judul;
	private String nama_merchant;
	private String alamat;
	private String isi_posting;
	private String name_catalog;
	private String id_catalog;
	private String rating;
	private String jum_com;
	private String filename_image;
	private String desc;
	private String meta_keyword;
	private String img;
	
	public Posting() {
		// TODO Auto-generated constructor stub
	}
	
	public Posting(String id_posting, String judul, String nama_merchant,
			String alamat, String isi_posting, String name_catalog, String id_catalog, String rating,
			String jum_com, String filename_image, String desc, String meta_keyword, String img) {
		
		this.id_posting = id_posting;
		this.judul = judul;
		this.nama_merchant = nama_merchant;
		this.alamat = alamat;
		this.isi_posting = isi_posting;
		this.name_catalog = name_catalog;
		this.id_catalog = id_catalog;
		this.rating = rating;
		this.jum_com = jum_com;
		this.filename_image = filename_image;
		this.desc = desc;
		this.meta_keyword = meta_keyword;
		this.img = img;
		
	}

	public String getId_posting() {
		return id_posting;
	}

	public String getJudul() {
		return judul;
	}

	public String getNama_merchant() {
		return nama_merchant;
	}

	public String getAlamat() {
		return alamat;
	}

	public String getIsi_posting() {
		return isi_posting;
	}

	public String getName_catalog() {
		return name_catalog;
	}
	
	public String getId_catalog() {
		return id_catalog;
	}

	public String getRating() {
		return rating;
	}

	public String getJum_com() {
		return jum_com;
	}

	public String getFilename_image() {
		return filename_image;
	}

	public String getDesc() {
		return desc;
	}

	public String getMeta_keyword() {
		return meta_keyword;
	}

	public String getImg() {
		return img;
	}

}

