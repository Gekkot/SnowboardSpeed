package gekkot.com.snowspeed.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import gekkot.com.snowspeed.R;
import gekkot.com.snowspeed.data.Movement;
import gekkot.com.snowspeed.data.Ride;

public class OrmLiteOpenHelper extends OrmLiteSqliteOpenHelper {

    private Dao<Ride, Long> rideDao;
    private Dao<Movement, Long> movementDao;

    public OrmLiteOpenHelper(Context context) {
        super(context, DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Ride.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Ride.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<Ride, Long> getRideDao() throws SQLException {
        if (rideDao == null) {
            rideDao = getDao(Ride.class);
        }
        return rideDao;
    }

    public Dao<Movement, Long> getMovementDao() throws SQLException {
        if (movementDao == null) {
            movementDao = getDao(Movement.class);
        }
        return movementDao;
    }
}
