package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.util.*;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.HttpGet;


public class WeatherActivity extends Activity
{

    private ProgressDialog mDialog = null;

    private static String OWM_PREAMBLE = "http://api.openweathermap.org/data/2.5/weather?zip=";
    private static String OWM_POSTAMBLE = ",us&appid=";
    private static String OWM_KEY = "44db6a862fba0b067b1930da0d769e98";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle(getResources().getString(R.string.working));
        mDialog.setCancelable(false);

        mDialog.show();
        Iterator iterator = MainActivity.readPreferences(this).iterator();
        if(iterator.hasNext())
        {
            new GetWeatherTask().execute(iterator);
        }
    }

    private class GetWeatherTask extends AsyncTask<Iterator, Void, Iterator>
    {
         private String httpGetResponse(String url) throws Exception
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
                httpGetResponse(OWM_PREAMBLE+(String)iterator.next()+OWM_POSTAMBLE+OWM_KEY);
            }catch(Exception e)
            {
            }

            return iterator;
        }

        protected void onPostExecute(Iterator iterator)
        {
            if(iterator.hasNext())
            {
                new GetWeatherTask().execute(iterator);
            }
            else
            {
                mDialog.dismiss();
            } 
        }
    }
}
