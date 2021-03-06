package at.hltgkr.sophie.gps_multifunktion;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements LocationListener {
    private static LocationManager locMan;
    ArrayList <String[]>funktionen = new ArrayList<>();


    ArrayList<double[]> koordinaten = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.i("asdf", "asdf");
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
        BufferedReader br;
        String line = "";
        try
        {
            br = new BufferedReader(new FileReader(fullname));
            while ((line = br.readLine()) != null) {
                String[]fkt = line.split(";");

                funktionen.add(fkt);
                fkt[0].replace(" ","");
                Log.i(fkt[0], "");
                Double latitude = 37.422005;                       //Double.parseDouble(fkt[0]);
                Double longitude = -122.084095;                  //Double.valueOf(fkt[1]).doubleValue();
                Log.i(""+latitude,""+longitude);

                double[] doublearr = {latitude, longitude};
                koordinaten.add(doublearr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            Log.i("asdsda",""+ex);
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

    protected void onPause()
    {
        super.onPause();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
    }
    protected void onResume()
    {
        super.onResume();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("aktuelle", "FUFu " + koordinaten.size());

        for(int i = 0; i<koordinaten.size(); i++)
        {
            Double latGPS = location.getLatitude();
            Double loGPS = location.getLongitude();
            Log.i("aktuelle",""+latGPS + " "+loGPS);



            double[] latloList = koordinaten.get(i);
            Log.i("aktuelle", ""+latloList[0]+ " "+latloList[1]);
            if(latGPS==latloList[0]&& loGPS == latloList[1] )
            {
                Log.i("aktuelle","FUnktionen");
                aktFunkt(i);
            }
        }
    }

    private void aktFunkt(int i) {
      String[]funkt = funktionen.get(i);

      boolean wlan = true;//Boolean.parseBoolean(funkt[2]);
      int silent = 100;//Integer.parseInt(funkt[3]);
      int bright = 50; //Integer.parseInt(funkt[4]);
      boolean bluetooth = true;//Boolean.parseBoolean(funkt[5]);




          setWifi(wlan);
        Log.i("aktuelle", "FUnktionen WLAN");
          setBluetooth(bluetooth);
        Log.i("aktuelle","FUnktionen bluetooth");

    }

    private void setBluetooth(boolean enable) {

        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (enable && !isEnabled) {
                bluetoothAdapter.enable();
                Log.i("asdf", "Bluetooth");
            } else if (!enable && isEnabled) {
                bluetoothAdapter.disable();
            }
        } catch (NullPointerException ex) {
            Toast.makeText(this,"Bluetooth eingeschaltet", Toast.LENGTH_LONG).show();
        }

    }



        public void setWifi(boolean status) {
               WifiManager wifiManager = (WifiManager) this
                    .getSystemService(Context.WIFI_SERVICE);
            if (status == true && !wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                Toast.makeText(this,"WLan eingeschaltet", Toast.LENGTH_LONG).show();

            } else if (status == false && wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);

            }
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



    /*public void changeRingerMode(Context context){

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        AudioManager audioManagerRing = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

    }*/


}
