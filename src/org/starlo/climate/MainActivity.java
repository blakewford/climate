package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.content.*;

import java.util.*;

public class MainActivity extends Activity
{
    public static final String SAVED_ZIP_CODES_KEY = "codes";
    public static final String SAVED_ZIP_CODES_FILE = "zipcodes";
    public static final String DETAIL_INTENT_ZIP_PARAM = "zipcode";

    private final static Set<String> DEFAULT_ZIP_CODES =
        Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("78757", "78758", "78759")));

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onBackPressed()
    {
        ListView listView = (ListView)findViewById(R.id.list);
        int size = listView.getChildCount();
        String[] newList = new String[size];
        for(int i = 0; i < size; i++)
        {
            View item = listView.getChildAt(i);
            newList[i] = ((TextView)item.findViewById(R.id.text)).getText().toString();
        }
        writeBackPreferences(newList);

        super.onBackPressed();
    }

    public TreeSet<String> writeBackPreferences(String[] list)
    {
        TreeSet<String> uniqueSet = new TreeSet<String>(Arrays.asList(list));
        SharedPreferences preferences = MainActivity.this.getSharedPreferences(MainActivity.SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(SAVED_ZIP_CODES_KEY, uniqueSet);
        editor.apply();

        return uniqueSet;
    }

    public static Set<String> readPreferences(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
        TreeSet<String> set = new TreeSet<String>();
        set.addAll(preferences.getStringSet(SAVED_ZIP_CODES_KEY, DEFAULT_ZIP_CODES));

        return set;
    }
}
