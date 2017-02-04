package gekkot.com.snowspeed.data

import java.util.*

/**
 * Created by gekko on 04.02.2017.
 */
class Ride {
    var name: String = ""
    var rideId: Int = 0
    var endTime: Long = 0L
    var startTime: Long = 0L
    var rideState: RideState = RideState.NOT_START

    constructor(Name: String) {
        this.name = Name
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

}