package gekkot.com.snowspeed.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.anastr.speedviewlib.SpeedView;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gekkot.com.snowspeed.R;
import gekkot.com.snowspeed.data.DistanceHelper;
import gekkot.com.snowspeed.data.Movement;
import gekkot.com.snowspeed.data.Ride;
import gekkot.com.snowspeed.db.DBOpenHelper;
import gekkot.com.snowspeed.db.OrmLiteOpenHelper;
import gekkot.com.snowspeed.db.SQLRequestsGenerator;

public class RideActivity extends AppCompatActivity {


    ArrayList<Movement> rideMovementsArray = new ArrayList<>();

    LocationManager locationManager;
    MyLocationListener locationListener;

    @BindView(R.id.speedView)
    SpeedView speedView;

    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ride = new Ride();
        ride.startRide();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ride.endRide();
                OrmLiteOpenHelper dbOpenHelper = new OrmLiteOpenHelper(getApplicationContext());
                SQLiteDatabase writableDatabase = dbOpenHelper.getWritableDatabase();
                try {
                    Dao<Ride, Long> rideDao = dbOpenHelper.getRideDao();
                    rideDao.create(ride);
                    List<Ride> rides = rideDao.queryForAll();
                } catch (Exception e) {

                }

                try {
                    Dao<Movement, Long> movementDao = dbOpenHelper.getMovementDao();
                    for (Movement movement : rideMovementsArray) {
                        try {
                            movementDao.create(movement);
                        } catch (Exception e) {
                        }
                    }
                    List<Movement> movements = movementDao.queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(RideActivity.this, "save points:" + rideMovementsArray.size(), Toast.LENGTH_LONG).show();
                rideMovementsArray.clear();
            }
        });

        initSpeedView();

        locationListener = new MyLocationListener();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5,
                locationListener);
    }

    @Override
    protected void onDestroy() {
        locationManager.removeUpdates(locationListener);
        super.onDestroy();
    }

    private void initSpeedView(){
        speedView.setMaxSpeed(20);
        speedView.setUnit("m/s");
    }

    private void onChangeLocation(){
        int movementsCount = rideMovementsArray.size();
        if( movementsCount> 1){
            Movement movement1 = rideMovementsArray.get(movementsCount - 1);
            Movement movement2 = rideMovementsArray.get(movementsCount - 2);
            long timeDif = movement1.getTime() - movement2.getTime();
            double distance = DistanceHelper.INSTANCE.distFrom(movement1.getLocation(), movement2.getLocation());
            double seconds = timeDif/1000;
            double speed = distance / seconds;
            speedView.setSpeedAt((float) speed);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            long time = Calendar.getInstance().getTime().getTime();
            Movement movement = new Movement(time, loc, ride.getRideId());
            rideMovementsArray.add(movement);
            onChangeLocation();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    public static void startRideActivity(Context context) {
        Intent start_about_intent = new Intent(context, RideActivity.class);
        context.startActivity(start_about_intent);
    }

}
