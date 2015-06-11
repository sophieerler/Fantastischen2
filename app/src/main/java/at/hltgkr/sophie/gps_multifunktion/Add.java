package at.hltgkr.sophie.gps_multifunktion;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Sophie on 22.05.2015.
 */
public class Add extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

    }

    public void onClickAdd()
    {

        // Unvollständig Daten aus Add.xml fehlen
        String sdState = Environment.getExternalStorageState();
        if(!sdState.equals(Environment.MEDIA_MOUNTED))return;
        File outFile = Environment.getExternalStorageDirectory();
        String path = outFile.getAbsolutePath();
        String fullname = path + "/Multifunktionswerkzeug.csv";
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fullname)));
            out.println(); // Add daten hinzufügen
            out.flush();
            out.close();
        }catch(Exception ex){}
    }

}
