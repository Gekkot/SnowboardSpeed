package gekkot.com.snowspeed.db

import android.database.sqlite.SQLiteDatabase
import gekkot.com.snowspeed.data.Movement
import android.content.ContentValues
import gekkot.com.snowspeed.data.Ride


/**
 * Created by gekko on 04.02.2017.
 */

object SQLRequestsGenerator {
    val RIDE_TABLE_NAME = "Ride"
    val POINTS_TABLE_NAME = "Points"

    fun getCreateRideTableRequest(): String {

        return "CREATE TABLE $RIDE_TABLE_NAME(" +
                "id long primary key autoincrement," +
                "name text," +
                "startTime long," +
                "endTime long" +
                ");"
    }

    fun getCreateMovementPointTableRequest(): String {

        return "CREATE TABLE $POINTS_TABLE_NAME(" +
                "pointId long primary key autoincrement," +
                "time long," +
                "latitude float," +
                "longitude float," +
                "rideId  long" +
                ");"
    }

    fun addMovement(db: SQLiteDatabase, movement: Movement) {
        val contentValues = ContentValues()
        contentValues.put("time", movement.time)
        contentValues.put("latitude", movement.location.latitude)
        contentValues.put("longitude", movement.location.longitude)
        db.insert(POINTS_TABLE_NAME, null, contentValues)
    }

    fun addRide(db: SQLiteDatabase, ride: Ride) {
        val contentValues = ContentValues()
        contentValues.put("name", ride.name)
        contentValues.put("startTime", ride.startTime)
        db.insert(RIDE_TABLE_NAME, null, contentValues)
    }

    fun getDropMovementTableRequest(): String {
        return "DROP TABLE IF EXIST $POINTS_TABLE_NAME;"
    }

    fun getDropRidesTableRequest(): String {
        return "DROP TABLE IF EXIST $RIDE_TABLE_NAME;"
    }
}
