package gekkot.com.snowspeed.data

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import gekkot.com.snowspeed.db.SQLRequestsGenerator
import java.util.*

/**
 * Created by gekko on 04.02.2017.
 */
@DatabaseTable(tableName = SQLRequestsGenerator.RIDE_TABLE_NAME)
class Ride {

    @DatabaseField
    var name: String = ""

    @DatabaseField(generatedId = true)
    var rideId: Int = 0

    @DatabaseField
    var endTime: Long = 0L

    @DatabaseField
    var startTime: Long = 0L

    var rideState: RideState = RideState.NOT_START

    constructor(name: String) {
        this.name = name
        this.rideId = RideIdController.ride;
        RideIdController.incRideId()
    }

    constructor() {
        this.rideId = RideIdController.ride;
        RideIdController.incRideId()
        this.name = "ride $rideId"
    }

    fun startRide() {
        rideState = RideState.START
        val calendar = Calendar.getInstance()
        if (calendar != null) {
            startTime = calendar.time.time;
        }
    }

    fun endRide() {
        rideState = RideState.FINISH
        val calendar = Calendar.getInstance()
        if (calendar != null) {
            endTime = calendar.time.time;
        }
    }

    companion object {
        object RideIdController {

            var ride: Int = 1
                private set

            public fun incRideId() {
                ride++
            }
        }
    }

}