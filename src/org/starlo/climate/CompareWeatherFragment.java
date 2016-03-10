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

        return view;
    }

    public void setAdapter(Weather[] items)
    {
        mAdapter = new WeatherArrayAdapter(mContext, 0, items);
        mList.setAdapter(mAdapter);
    }
}
