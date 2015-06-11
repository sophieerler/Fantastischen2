package at.hltgkr.sophie.gps_multifunktion;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements LocationListener {
    private static LocationManager locMan = null;
    ArrayList <String[]>funktionen = new ArrayList<>();
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        fillList();
        updateList();
    }

    private void updateList() {

    }

    private void fillList() {
        String sdState = Environment.getExternalStorageState();
        if(!sdState.equals(Environment.MEDIA_MOUNTED))return;
        File inFile = Environment.getExternalStorageDirectory();
        String path = inFile.getAbsolutePath();
        String fullname = path + "/Multifunktionswerkzeug.csv";
        BufferedReader br = null;
        String line = "";
        try
        {
            br = new BufferedReader(new FileReader(fullname));
            while ((line = br.readLine()) != null) {
             String[]fkt = line.split(";");
            funktionen.add(fkt);
            latitude =  Double.parseDouble(fkt[0]);
            longitude =  Double.parseDouble(fkt[1]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent addIntent = new Intent(this,Add.class);
            startActivity(addIntent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
