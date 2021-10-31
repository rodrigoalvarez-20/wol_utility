package mx.ralvarez20.udp.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import mx.ralvarez20.udp.models.MacAddress;

public class MacContract {
    public static abstract class MacEntry implements BaseColumns {
        public static final String TABLE_NAME ="addresses";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MAC_ADDRESS = "mac_address";
    }
}
