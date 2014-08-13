package aprisma.akirah.bingung.service;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KlasifikasiFacade {

	public static final String TABLE_NAME = "klasifikasi";

	public static final String KEY_ID_CATALOG = "id_catalog";
	public static final String KEY_ID_LANG = "id_lang";
	public static final String KEY_NAME_CATALOG = "name_catalog";

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + KEY_ID_CATALOG + " INTEGER PRIMARY KEY," + KEY_ID_LANG
			+ " INTEGER," + KEY_NAME_CATALOG + " TEXT )";

	private SQLiteDatabase db;

	public KlasifikasiFacade(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Storing data in database
	 * */
	public void insert(String id_catalog, String id_lang, String name_catalog) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID_CATALOG, id_catalog);
		values.put(KEY_ID_LANG, id_lang);
		values.put(KEY_NAME_CATALOG, name_catalog);

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting All data from database
	 * */
	public ArrayList<HashMap<String, String>> viewAll() {
		ArrayList<HashMap<String, String>> catalogs = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> cata = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			while (!cursor.isAfterLast()) {
				cata.put(KEY_ID_CATALOG,
						cursor.getString(cursor.getColumnIndex(KEY_ID_CATALOG)));
				cata.put(KEY_ID_LANG,
						cursor.getString(cursor.getColumnIndex(KEY_ID_LANG)));
				cata.put(KEY_NAME_CATALOG, cursor.getString(cursor
						.getColumnIndex(KEY_NAME_CATALOG)));
				catalogs.add(cata);
			}
		}
		cursor.close();
		db.close();
		return catalogs;
	}

}
