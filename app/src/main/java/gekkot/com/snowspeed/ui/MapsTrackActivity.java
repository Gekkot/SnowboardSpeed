package gekkot.com.snowspeed.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gekkot.com.snowspeed.R;
import gekkot.com.snowspeed.data.Movement;
import gekkot.com.snowspeed.db.OrmLiteOpenHelper;

public class MapsTrackActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_track);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        OrmLiteOpenHelper dbOpenHelper = new OrmLiteOpenHelper(getApplicationContext());

        try {
            Dao<Movement, Long> movementDao = dbOpenHelper.getMovementDao();
            QueryBuilder<Movement,Long> queryBuilder = movementDao.queryBuilder();
            List<Movement> query = movementDao.query(queryBuilder.prepare());

            ArrayList<LatLng> latLngs = new ArrayList<>();
            for(Movement movement:query){
                LatLng latLng = getLatLongFromMovement(movement);
                latLngs.add(latLng);
            }
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(latLngs.toArray(new LatLng[latLngs.size()]))
                    .color(Color.BLACK);
            mMap.addPolyline(polylineOptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startMapActivity(Context context) {
        Intent start_map_intent = new Intent(context, MapsTrackActivity.class);
        context.startActivity(start_map_intent);
    }

    public static LatLng getLatLongFromMovement(Movement movement){
        return new LatLng(movement.getLatitude(), movement.getLongitude());

    }
}
