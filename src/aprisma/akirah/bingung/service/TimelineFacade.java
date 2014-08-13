package aprisma.akirah.bingung.service;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimelineFacade {

	public static final String TABLE_NAME = "timeline";

	public static final String KEY_ID_POSTING = "id_posting";
	public static final String KEY_ID_CATEGORY = "id_category";
	public static final String KEY_JUDUL = "judul";
	public static final String KEY_COUNTER = "counter";
	public static final String KEY_ISI_POSTING = "isi_posting";
	public static final String KEY_FILENAME_IMAGE = "filename_image";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LON = "lon";
	public static final String KEY_LIKE = "like";
	public static final String KEY_RATING = "rating";
	public static final String KEY_META_KEYWORD = "meta_keyword";

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + KEY_ID_POSTING + " INTEGER PRIMARY KEY," + KEY_ID_CATEGORY
			+ " INTEGER," + KEY_JUDUL + " TEXT," + KEY_COUNTER + " INTEGER,"
			+ KEY_ISI_POSTING + " TEXT," + KEY_FILENAME_IMAGE + " TEXT,"
			+ KEY_LAT + " TEXT," + KEY_LON + " TEXT," + KEY_LIKE + " INTEGER,"
			+ KEY_RATING + " TEXT," + KEY_META_KEYWORD + " TEXT " + ")";

	private SQLiteDatabase db;

	public TimelineFacade(SQLiteDatabase db) {
		this.db = db;
	}

	public void insert(String id_posting, int id_category, String judul,
			int counter, String isi_posting, String filename_img, String lat,
			String lon, int like, String rating, String meta_keyword) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID_POSTING, id_posting);
		values.put(KEY_ID_CATEGORY, id_category);
		values.put(KEY_JUDUL, judul);
		values.put(KEY_COUNTER, counter);
		values.put(KEY_ISI_POSTING, isi_posting);
		values.put(KEY_FILENAME_IMAGE, filename_img);
		values.put(KEY_LAT, lat);
		values.put(KEY_LON, lon);
		values.put(KEY_LIKE, like);
		values.put(KEY_RATING, rating);
		values.put(KEY_META_KEYWORD, meta_keyword);

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
	
	/**
	 * Getting All data from database
	 * */
	public ArrayList<HashMap<String, String>> viewAll() {
		ArrayList<HashMap<String, String>> timelinelist = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> item = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			while (!cursor.isAfterLast()) {
				item.put(KEY_ID_POSTING,
						cursor.getString(cursor.getColumnIndex(KEY_ID_POSTING)));
				item.put(KEY_ID_CATEGORY,
						cursor.getString(cursor.getColumnIndex(KEY_ID_CATEGORY)));
				item.put(KEY_JUDUL, cursor.getString(cursor
						.getColumnIndex(KEY_JUDUL)));
				item.put(KEY_COUNTER,
						cursor.getString(cursor.getColumnIndex(KEY_COUNTER)));
				item.put(KEY_ISI_POSTING,
						cursor.getString(cursor.getColumnIndex(KEY_ISI_POSTING)));
				item.put(KEY_FILENAME_IMAGE, cursor.getString(cursor
						.getColumnIndex(KEY_FILENAME_IMAGE)));
				item.put(KEY_LAT,
						cursor.getString(cursor.getColumnIndex(KEY_LAT)));
				item.put(KEY_LON,
						cursor.getString(cursor.getColumnIndex(KEY_LON)));
				item.put(KEY_LIKE, cursor.getString(cursor
						.getColumnIndex(KEY_LIKE)));
				item.put(KEY_RATING, cursor.getString(cursor
						.getColumnIndex(KEY_RATING)));
				item.put(KEY_META_KEYWORD, cursor.getString(cursor
						.getColumnIndex(KEY_META_KEYWORD)));
				timelinelist.add(item);
			}
		}
		cursor.close();
		db.close();
		return timelinelist;
	}

}
