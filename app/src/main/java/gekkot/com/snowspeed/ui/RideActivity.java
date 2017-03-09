package gekkot.com.snowspeed.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.Calendar;

import gekkot.com.snowspeed.R;
import gekkot.com.snowspeed.data.DistanceHelper;
import gekkot.com.snowspeed.data.Movement;
import gekkot.com.snowspeed.data.Ride;

public class RideActivity extends AppCompatActivity {


    ArrayList<Movement> rideMovementsArray = new ArrayList<>();

    LocationManager locationManager;
    MyLocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    private void onChangeLocation(){
        int movementsCount = rideMovementsArray.size();
        if( movementsCount> 1){
            Movement movement1 = rideMovementsArray.get(movementsCount - 1);
            Movement movement2 = rideMovementsArray.get(movementsCount - 2);
            long timeDif = movement1.getTime() - movement2.getTime();
            double distance = DistanceHelper.INSTANCE.distFrom(movement1.getLocation(), movement2.getLocation());
            double seconds = timeDif/1000;
            double speed = distance / seconds;
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            long time = Calendar.getInstance().getTime().getTime();
            Movement movement = new Movement(time, loc, 1);
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
