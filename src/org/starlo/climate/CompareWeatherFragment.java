package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.content.*;

public class CompareWeatherFragment extends Fragment implements WeatherFragmentInterface
{
    private ListView mList = null;
    private Context mContext = null;
    private WeatherArrayAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.weather_dashboard, container, false);
        mList = (ListView)view.findViewById(R.id.list);
        mList.setOnItemClickListener(new LocationClickListener());

        return view;
    }

    public void setAdapter(Weather[] items)
    {
        mAdapter = new WeatherArrayAdapter(mContext, 0, items);
        mList.setAdapter(mAdapter);
    }

    private class LocationClickListener implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(mContext, DetailWeatherActivity.class);
            intent.putExtra(MainActivity.DETAIL_INTENT_ZIP_PARAM, ((WeatherBrief)view.getTag()).zipcode);
            startActivity(intent);
        }
    }
}
