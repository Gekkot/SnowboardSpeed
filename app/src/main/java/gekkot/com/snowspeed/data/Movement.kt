package gekkot.com.snowspeed.data

import android.location.Location
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import gekkot.com.snowspeed.db.SQLRequestsGenerator

@DatabaseTable(tableName = SQLRequestsGenerator.POINTS_TABLE_NAME)
class Movement {

    @DatabaseField
    var time: Long

    var location: Location
        set(value) {
            this.latitude = value.latitude
            this.longitude = value.longitude
        }

    @DatabaseField
    var latitude: Double

    @DatabaseField
    var longitude: Double


    @DatabaseField
    var rideId: Long

    constructor() {
        this.time = 0L;
        this.location = Location("");
        this.rideId = 0;
        this.latitude = 0.0
        this.longitude = 0.0
    }

    constructor(time: Long, location: Location, rideId: Long) {
        this.time = time
        this.location = location
        this.rideId = rideId
        this.latitude = location.latitude
        this.longitude = location.longitude
    }
}
