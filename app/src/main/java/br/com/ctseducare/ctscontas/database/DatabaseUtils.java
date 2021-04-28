package br.com.ctseducare.ctscontas.database;

import android.content.Context;

public class DatabaseUtils {

    private static DatabaseStructure database = null;

    private DatabaseUtils() {

    }

    public static DatabaseStructure getDatabase(Context context) {
        if (database == null) {
            database = new DatabaseStructure(context, DatabaseSettings.DATABASE_NAME, null, DatabaseSettings.DATABASE_VERSION);
        }
        return database;
    }

}
