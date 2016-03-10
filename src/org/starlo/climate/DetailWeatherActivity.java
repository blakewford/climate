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
        new GetWeatherTask(mWeatherFragment, mDialog).execute(iterator);
    }
}
