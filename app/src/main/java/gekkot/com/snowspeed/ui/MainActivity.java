package gekkot.com.snowspeed.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import gekkot.com.snowspeed.R;
import gekkot.com.snowspeed.db.OrmLiteOpenHelper;

public class MainActivity extends AppCompatActivity {

    public static final int LAYOUT_ID = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RideActivity.startRideActivity(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_map){
            MapsTrackActivity.startMapActivity(MainActivity.this);
        }
        if (id == R.id.action_clear_points) {
            OrmLiteOpenHelper ormLiteOpenHelper = new OrmLiteOpenHelper(MainActivity.this);
            SQLiteDatabase writableDatabase = ormLiteOpenHelper.getWritableDatabase();
            ormLiteOpenHelper.recreateTables(writableDatabase);
        }

        return super.onOptionsItemSelected(item);
    }
}
