package happyface.rpgtraveler.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter_old{
	SharedPreferences metadata;
	SharedPreferences.Editor editor;

	public static final String DBNAME = "GAMEDATA";

	public static final int DBVERSION = 1;

	private static final String CREATE_TABLE_LOCAL = "create table " + DBTables.NAME_LOCAL +" (" + DBTables.ID + " integer primary key autoincrement, " + DBTables.CAT + " text not null, " + DBTables.NAME + " text not null, " + DBTables.DESC + " text not null," + DBTables.ALIAS + " integer not null" + ")";
//	private static final String CREATE_TABLE_EXTERNAL = "create table " + DBAdapterExternal.TABLE_NAME +" (" + DBAdapterExternal.ID + " integer primary key autoincrement, " + DBAdapterExternal.GAME + " integer not null, " + DBAdapterExternal.CAT + " text not null, " + DBAdapterExternal.NAME + " text not null, " + DBAdapterExternal.DESC + " text not null" + ")";
	private static final String CREATE_TABLE_LINK_LOCAL = "create table " + DBTables.NAME_LINK +" (" + DBTables.ID + " integer primary key autoincrement, " + DBTables.GAMEID + " int not null, " + DBTables.MAJVER + " integer not null, " + DBTables.MINVER + " integer not null, " + DBTables.PARENT + " integer not null, " + DBTables.FOCUS + " integer not null, " + DBTables.ARRANGE + " integer, " + DBTables.SCORE + " text not null, " + DBTables.GAME_TRAIT + " integer not null, " + DBTables.VISIBLE + " integer not null, " + DBTables.ALIAS + " integer not null" + ")";
	private static final String CREATE_TABLE_LINK_PREREQUISITS = "create table " + DBTables.NAME_PREREQS +" (" + DBTables.ID + " integer primary key autoincrement, " + DBTables.GAMEID + " integer not null, " + DBTables.FOCUS + " integer not null, " + DBTables.PREREQ + " integer not null, " + DBTables.SCORE + " integer not null," + DBTables.ALIAS + " integer not null" + ")";
	private static final String CREATE_TABLE_LINK_INHERITED = "create table " + DBTables.NAME_INHERITED +" (" + DBTables.ID + " integer primary key autoincrement, " + DBTables.LINK + " integer not null, " + DBTables.TYPE + " integer not null, " + DBTables.ALIAS + " integer not null" + ")";
	private static final String CREATE_TABLE_LINK_RESTRICTIONS = "create table " + DBTables.NAME_RESTRICTIONS + " (" + DBTables.ID + " integer primary key autoincrement, " + DBTables.GAMEID + " integer not null, " + DBTables.FOCUS + " integer not null, " + DBTables.SCORE + " text not null, " + DBTables.ALIAS + " integer not null" + ")";
	//DBAda pterLinks

	public static Context context;
	public static DatabaseHelper DBHelper;
	public static SQLiteDatabase db;

	public DBAdapter(Context context) {
		this.context = context;
		this.DBHelper = new DatabaseHelper(this.context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DBNAME, null, DBVERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_LOCAL);
//			db.execSQL(CREATE_TABLE_EXTERNAL);
//
			db.execSQL(CREATE_TABLE_LINK_LOCAL);
			db.execSQL(CREATE_TABLE_LINK_PREREQUISITS);
			db.execSQL(CREATE_TABLE_LINK_INHERITED);
			db.execSQL(CREATE_TABLE_LINK_RESTRICTIONS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

    public DBAdapter open() throws SQLException {
        metadata = this.context.getSharedPreferences("metadata", 0);
        editor = metadata.edit();
        editor.putInt("OpenDB", metadata.getInt("OpenDB", 0)+1);
        editor.commit();
		this.db = this.DBHelper.getWritableDatabase();
		return this;
    }

	public boolean deleteRow(int ID, String TableName) {
		return db.delete(TableName, DBTables.ID + "=" + ID, null) > 0;
	}

	public Cursor getRow(int ID, String TableName) throws SQLException {
		Cursor cur = db.query(true, TableName, null, DBTables.ID + "=" + ID, null, null, null, null, null);
		cur.moveToFirst();
		return cur;
	}

    public static void remove(String tableName) throws SQLException {
    	db.execSQL("drop table " + tableName);
    }

    public void close(){
        metadata = this.context.getSharedPreferences("metadata", 0);
        editor = metadata.edit();
        editor.putInt("OpenDB", metadata.getInt("OpenDB", 0)-1);
        editor.commit();
    	this.DBHelper.close();
    }
}
