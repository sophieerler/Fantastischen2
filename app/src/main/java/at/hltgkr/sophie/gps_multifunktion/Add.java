package at.hltgkr.sophie.gps_multifunktion;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Sophie on 22.05.2015.
 */
public class Add extends Activity implements LocationListener
{
String link = "http://javatechig.com/android/how-to-turn-off-turn-on-wifi-in-android";
String mute = "http://stackoverflow.com/questions/7317974/android-mute-unmute-phone";
    private static LocationManager locMan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void onClickAdd(final View view)
    {

        String sdState = Environment.getExternalStorageState();
        if(!sdState.equals(Environment.MEDIA_MOUNTED))return;
        File outFile = Environment.getExternalStorageDirectory();
        String path = outFile.getAbsolutePath();

        String fullname = path + "/Multifunktionswerkzeug.csv";
        Log.i("pfad",fullname);
        Switch wlanSwitch = (Switch)findViewById(R.id.switchWLAN);
        boolean wlan = wlanSwitch.isChecked();
        SeekBar laut = (SeekBar) findViewById(R.id.seekBar_silent_mode);
        int silent_mode = laut.getProgress();
        SeekBar licht = (SeekBar)findViewById(R.id.seekBar_brightness);
        int brightness_mode = licht.getProgress();
        Switch blue = (Switch)findViewById(R.id.switchBluetooth);
        boolean bluetooth = blue.isChecked();
        try
        {

            File f = new File(fullname);



            if(!f.exists()) {

                f.createNewFile();
                Log.i("erstellt","sd");
            }
            Log.i("asd",  " " + locMan);
            Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("asd",  " " + loc);
            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();
            Log.i("asd", latitude+ " " + longitude);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fullname)));
            out.append(latitude+";"+longitude+";"+wlan+";"+silent_mode+";"+brightness_mode+";"+bluetooth); // Add daten hinzufügen
            out.append("\n");
            out.append("\r");
            out.flush();

            finish();
        }catch(Exception ex){
            Log.i("nicht erstellt",""+ex);}
    }

    protected void onResume()
    {
        super.onResume();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,0, this);
    }

    @Override
    public void onLocationChanged(Location location) {

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
