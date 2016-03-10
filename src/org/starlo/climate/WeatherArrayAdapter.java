package org.starlo.climate;

import android.view.*;
import android.widget.*;
import android.content.*;

public class WeatherArrayAdapter extends ArrayAdapter<WeatherBrief>
{
    private Context mContext = null;
    private WeatherBrief[] mItems = null;
    private LayoutInflater mInflater = null;

    public WeatherArrayAdapter(Context context, int resource, WeatherBrief[] items)
    {
        super(context, resource, items);

        mContext = context;
        mItems = items;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.weather_item, null);
            WeatherBrief brief = mItems[position];
            ((TextView)convertView.findViewById(R.id.temp)).setText(brief.main.temp.toString()+" F");
            ((TextView)convertView.findViewById(R.id.condition)).setText(brief.weather[0].main);
            ((TextView)convertView.findViewById(R.id.location)).setText(brief.name);
        }

        return convertView;
    }
}
