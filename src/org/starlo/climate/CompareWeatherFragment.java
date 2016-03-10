package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.content.*;

import com.google.gson.*;

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
        Boolean shouldRetry = false;
        for(int i = 0; i < items.length; i++)
        {
            if(items[i].placeholder)
            {
                new RetryWeatherTask(items, i).execute(items[i].zipcode);
            }
        }

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

    private class RetryWeatherTask extends AsyncTask<String, Void, Weather>
    {
        private int mRetryIndex = -1;
        private Weather[] mItems = null;

        private Gson mGson = new Gson(); //Take advantage of caching

        public RetryWeatherTask(Weather[] items, int index)
        {
            mItems = new Weather[items.length];
            System.arraycopy(items, 0, mItems, 0, items.length);
            mRetryIndex = index;
        }

        protected Weather doInBackground(String... zipcode)
        {
            String zipcodeString = zipcode[0];
            Weather entry = new WeatherPlaceholder(zipcodeString);
            try
            {
                String buffer =
                    GetWeatherTask.httpGetResponse(
                        GetWeatherTask.OWM_PREAMBLE+zipcodeString+GetWeatherTask.OWM_POSTAMBLE+GetWeatherTask.OWM_KEY
                    );
                Weather sample = mGson.fromJson(buffer, Weather.class);
                if(sample != null)
                {
                    sample.zipcode = zipcodeString;
                    entry = sample;
                }
            }catch(Exception e)
            {
            }

            return entry;
        }

        protected void onPostExecute(Weather param)
        {
            param.placeholder = false; //Just 1 retry attempt for now. Force placeholder status to off
            mItems[mRetryIndex] = param;
            mAdapter = new WeatherArrayAdapter(mContext, 0, mItems);
            mList.setAdapter(mAdapter);
        }
    }
}
