package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.support.v4.app.FragmentActivity;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.HttpGet;

import com.google.gson.*;

public class DetailWeatherActivity extends FragmentActivity
{
    private ProgressDialog mDialog = null;
    private DetailWeatherFragment mWeatherFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle(getResources().getString(R.string.working));
        mDialog.setCancelable(false);

        mWeatherFragment = (DetailWeatherFragment)getFragmentManager().findFragmentById(R.id.weather_detail);

        mDialog.show();
        TreeSet set = new TreeSet();
        set.add(getIntent().getStringExtra(MainActivity.DETAIL_INTENT_ZIP_PARAM));
        Iterator iterator = set.iterator();
        new GetDetailWeatherTask(mWeatherFragment, mDialog).execute(iterator);
    }

    private class GetDetailWeatherTask extends GetWeatherTask
    {
        public GetDetailWeatherTask(WeatherFragmentInterface fragmentInterface, ProgressDialog dialog)
        {
            super(fragmentInterface, dialog);
        }

        protected Iterator doInBackground(Iterator... zipcodeIterator)
        {
            Iterator iterator = zipcodeIterator[0];
            try
            {
                String zipcode = (String)iterator.next();
                String buffer = httpGetResponse(OWM_PREAMBLE+zipcode+OWM_POSTAMBLE+OWM_KEY);
                Weather sample = mGson.fromJson(buffer, Weather.class);
                if(sample == null)
                {
                    sample = new WeatherPlaceholder(zipcode);
                }
                sample.zipcode = zipcode;
                mWeatherResults.add(sample);

            }catch(Exception e)
            {
            }

            return iterator;
        }
    }
}
