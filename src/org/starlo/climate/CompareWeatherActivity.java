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

public class CompareWeatherActivity extends FragmentActivity
{
    private ProgressDialog mDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle(getResources().getString(R.string.working));
        mDialog.setCancelable(false);

        WeatherFragmentInterface fragment = (WeatherFragmentInterface)getFragmentManager().findFragmentById(R.id.weather);

        mDialog.show();
        Iterator iterator = MainActivity.readPreferences(this).iterator();
        if(iterator.hasNext())
        {
            new GetWeatherTask(fragment, mDialog).execute(iterator);
        }
        else
        {
            mDialog.dismiss();
        }
    }
}
