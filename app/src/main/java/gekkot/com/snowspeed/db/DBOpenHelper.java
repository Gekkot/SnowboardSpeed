package gekkot.com.snowspeed.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gekko on 04.02.2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DATABASE_NAME = "rides.db";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                        DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getCreateRideTableRequest());
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getCreateMovementPointTableRequest());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        deleteAllData(sqLiteDatabase);
    }

    public void deleteAllData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getDropMovementTableRequest());
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getDropRidesTableRequest());
    }
}
