package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;

import java.util.*;
import java.text.*;

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
            String buffer = weather.main.temp_min.toString()+" to "+weather.main.temp_max.toString()+" F";
            ((TextView)mActivity.findViewById(R.id.temp_range)).setText(buffer);
            buffer = "Humidity "+weather.main.humidity.toString()+"% "+weather.main.pressure.toString()+" hPa";
            ((TextView)mActivity.findViewById(R.id.humidity_pressure)).setText(buffer);
            ((TextView)mActivity.findViewById(R.id.condition)).setText(weather.weather[0].main);
            ((TextView)mActivity.findViewById(R.id.condition_detail)).setText(weather.weather[0].description);
            ((TextView)mActivity.findViewById(R.id.location)).setText(weather.name);
            buffer = weather.coord.lat+", "+weather.coord.lon;
            ((TextView)mActivity.findViewById(R.id.coordinates)).setText(buffer);

            //Some wind fields are optional, should do more to protect all values
            StringBuilder builder = new StringBuilder();
            builder.append("Wind Speed ");
            if(weather.wind.speed != null) builder.append(weather.wind.speed.toString());
            if(weather.wind.deg != null) builder.append(" at ").append(weather.wind.deg.toString());
            if(weather.wind.gust != null) builder.append(" gusting to ").append(weather.wind.gust.toString());
            buffer = builder.toString();

            ((TextView)mActivity.findViewById(R.id.wind)).setText(buffer);
            buffer = "Cloud Cover "+weather.clouds.all.toString()+"%";
            ((TextView)mActivity.findViewById(R.id.cloud)).setText(buffer);
        }
    }
}
