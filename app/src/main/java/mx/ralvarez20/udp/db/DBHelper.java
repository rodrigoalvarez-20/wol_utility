package mx.ralvarez20.udp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "macs_saved.db";

    public DBHelper(Context cntx){
        super(cntx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + MacContract.MacEntry.TABLE_NAME + " (" +
                MacContract.MacEntry._ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT," +
                MacContract.MacEntry.NAME + " TEXT NOT NULL UNIQUE, " +
                MacContract.MacEntry.MAC_ADDRESS + " TEXT NOT NULL UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
