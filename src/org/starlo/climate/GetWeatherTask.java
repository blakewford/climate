package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.util.*;

import com.google.gson.*;

public class GetWeatherTask extends AsyncTask<Iterator, Void, Iterator>
{
    private ProgressDialog mDialog = null;
    private WeatherFragmentInterface mWeatherFragment = null;

    protected Gson mGson = new Gson(); //Take advantage of caching
    protected static ArrayList<Weather> mValidWeather = new ArrayList<Weather>();

    protected static final String OWM_PREAMBLE = "http://api.openweathermap.org/data/2.5/weather?zip=";
    protected static final String OWM_POSTAMBLE = ",us&units=imperial&appid=";
    protected static final String OWM_KEY = "44db6a862fba0b067b1930da0d769e98";

    public GetWeatherTask(WeatherFragmentInterface fragmentInterface, ProgressDialog dialog)
    {
        mDialog = dialog;
        mWeatherFragment = fragmentInterface;
    }
 
    protected String httpGetResponse(String url) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        HttpGet httpGet = new HttpGet(url);
        HttpEntity entity = new DefaultHttpClient().execute(httpGet).getEntity();
        if(entity != null)
        {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            String line = null;
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            instream.close();
        }

        return sb.toString();
    }

    protected Iterator doInBackground(Iterator... zipcodeIterator)
    {
        Iterator iterator = zipcodeIterator[0];
        try
        {
            String zipcode = (String)iterator.next();
            String buffer = httpGetResponse(OWM_PREAMBLE+zipcode+OWM_POSTAMBLE+OWM_KEY);
            WeatherBrief sample = mGson.fromJson(buffer, WeatherBrief.class);
            if(sample != null)
            {
                sample.zipcode = zipcode;
                mValidWeather.add(new Weather(sample));
            }
        }catch(Exception e)
        {
        }

        return iterator;
    }

    protected void onPostExecute(Iterator iterator)
    {
        if(iterator.hasNext())
        {
            new GetWeatherTask(mWeatherFragment, mDialog).execute(iterator);
        }
        else
        {
            mWeatherFragment.setAdapter(mValidWeather.toArray(new Weather[mValidWeather.size()]));
            mValidWeather.clear();
            mDialog.dismiss();
        } 
    }
}
