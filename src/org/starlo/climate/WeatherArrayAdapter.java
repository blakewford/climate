package org.starlo.climate;

import android.view.*;
import android.widget.*;
import android.content.*;

public class WeatherArrayAdapter extends ArrayAdapter<Weather>
{
    private Context mContext = null;
    private Weather[] mItems = null;
    private LayoutInflater mInflater = null;

    public WeatherArrayAdapter(Context context, int resource, Weather[] items)
    {
        super(context, resource, items);

        mContext = context;
        mItems = items;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = mInflater.inflate(R.layout.weather_item, null);
        ((TextView)view.findViewById(R.id.location)).setText(mItems[position].name);

        return view;
    }
}
