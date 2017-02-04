package gekkot.com.snowspeed.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gekko on 04.02.2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteAllData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getDropMovementTableRequest());
        sqLiteDatabase.execSQL(SQLRequestsGenerator.INSTANCE.getDropRidesTableRequest());
    }
}
