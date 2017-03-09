package gekkot.com.snowspeed.data

import android.location.Location

/**
 * Created by Konstantin Sorokin on 09.03.2017.
 */
object DistanceHelper {
    fun distFrom(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val earthRadius = 6371000.0 //meters
        val dLat = Math.toRadians((lat2 - lat1).toDouble())
        val dLng = Math.toRadians((lng2 - lng1).toDouble())
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1.toDouble())) * Math.cos(Math.toRadians(lat2.toDouble())) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val dist = (earthRadius * c)

        return dist
    }

    fun distFrom(location1:Location,location2: Location ): Double {
        return distFrom(location1.latitude, location1.longitude, location2.latitude, location2.longitude)
    }
}