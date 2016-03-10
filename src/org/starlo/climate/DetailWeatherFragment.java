package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;

public class DetailWeatherFragment extends Fragment implements WeatherFragmentInterface
{
    private Activity mActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mActivity = getActivity();
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    public void setAdapter(Weather[] items)
    {
        if(items.length > 0)
        {
            Weather weather = items[0];
            ((TextView)mActivity.findViewById(R.id.temp)).setText(weather.main.temp.toString()+" F");
            ((TextView)mActivity.findViewById(R.id.condition)).setText(weather.weather[0].main);
            ((TextView)mActivity.findViewById(R.id.location)).setText(weather.name);
        }
    }
}
