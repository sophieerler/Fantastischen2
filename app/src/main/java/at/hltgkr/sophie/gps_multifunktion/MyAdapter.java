package at.hltgkr.sophie.gps_multifunktion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by serler on 12.06.2015.
 */
public class MyAdapter extends ArrayAdapter<String> {

    String username = "";

    public MyAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context ctx = parent.getContext();
        LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.bilder_main, parent, false);

        String[] values = ctx.getResources().getStringArray(R.array.produkte);
        ImageView img = (ImageView) v.findViewById(R.id.imageView1);
        TextView text = (TextView) v.findViewById(R.id.textView1);

        text.setText(values[position]);
        if (values[position].equals("HALLO" )) {
            img.setImageResource(R.drawable.abc_list_selector_background_transition_holo_dark);
        } else if (values[position].equals("LAUTSTÄRKE")) {
           img.setImageResource(android.R.drawable.ic_lock_silent_mode);
        } else if (values[position].equals("WLAN")) {
            img.setImageResource(R.drawable.index);
        } else if (values[position].equals("MUSIK")) {
            img.setImageResource(R.drawable.musik);
        }else if (values[position].equals("Standort"))
        {

        }

        return v;
    }
}