package gekkot.com.snowspeed.data

import android.location.Location
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import gekkot.com.snowspeed.db.SQLRequestsGenerator

@DatabaseTable(tableName = SQLRequestsGenerator.POINTS_TABLE_NAME)
class Movement(@DatabaseField var time: Long,
               @DatabaseField var location: Location,
               @DatabaseField var rideId: Long)