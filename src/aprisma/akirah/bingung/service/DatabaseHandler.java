/**
 * Author: Ravi Tamada Edited by Akip Maulana
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package aprisma.akirah.bingung.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "klikb";

	private KlasifikasiFacade klasifikasiFacade;
	private TimelineFacade timelineFacade;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		klasifikasiFacade = new KlasifikasiFacade(getWritableDatabase());
		timelineFacade = new TimelineFacade(getWritableDatabase());
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(KlasifikasiFacade.CREATE_TABLE);
		db.execSQL(TimelineFacade.CREATE_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + KlasifikasiFacade.CREATE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TimelineFacade.CREATE_TABLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(KlasifikasiFacade.TABLE_NAME, null, null);
		db.delete(TimelineFacade.TABLE_NAME, null, null);
		db.close();
	}

	public KlasifikasiFacade getKlasifikasiFacade() {
		return klasifikasiFacade;
	}

	public TimelineFacade getTimelineFacade() {
		return timelineFacade;
	}
	
	// closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
