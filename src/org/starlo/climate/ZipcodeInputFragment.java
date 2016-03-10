package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;
import android.content.*;

import java.util.*;

public class ZipcodeInputFragment extends Fragment
{
    private ListView mList = null;
    private Context mContext = null;
    private ProgressDialog mDialog = null;
    private EditableArrayAdapter mAdapter = null;
    private final String SAVED_ZIP_CODES_KEY = "codes";
    private final String SAVED_ZIP_CODES_FILE = "zipcodes";
    private final static Set<String> DEFAULT_ZIP_CODES =
        Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("78757", "78758", "78759")));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.zipcode_input, container, false);
        mList = (ListView)view.findViewById(R.id.list);
        Button addButton = (Button)view.findViewById(R.id.add);
        addButton.setOnClickListener(new AddFieldListener());

        SharedPreferences preferences = mContext.getSharedPreferences(SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
        Set<String> data = preferences.getStringSet(SAVED_ZIP_CODES_KEY, DEFAULT_ZIP_CODES);
        mAdapter = new EditableArrayAdapter(mContext, 0, data.toArray(new String[data.size()]));
        mAdapter.setOnRemoveItemClickListener(new RemoveFieldListener());
        mList.setAdapter(mAdapter);

        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle("Working...");
        mDialog.setCancelable(false);

        return view;
    }

    private class AddFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            SharedPreferences preferences = mContext.getSharedPreferences(SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
            Set<String> data = preferences.getStringSet(SAVED_ZIP_CODES_KEY, DEFAULT_ZIP_CODES);
            int size = data.size();
            String[] newList = new String[size+1];
            for(int i = 0; i < size; i++)
            {
                View item = mList.getChildAt(i);
                newList[i] = ((TextView)item.findViewById(R.id.text)).getText().toString();
            }
            newList[size] = "";

            HashSet<String> uniqueSet = new HashSet<String>(Arrays.asList(newList));
            mDialog.show();
            new WriteBackPreferencesTask().execute(uniqueSet);
            mAdapter = new EditableArrayAdapter(getActivity(), 0, uniqueSet.toArray(new String[uniqueSet.size()]));
            mAdapter.setOnRemoveItemClickListener(new RemoveFieldListener());
            mList.setAdapter(mAdapter);
        }
    }

    private class RemoveFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            SharedPreferences preferences = mContext.getSharedPreferences(SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
            Set<String> data = preferences.getStringSet(SAVED_ZIP_CODES_KEY, DEFAULT_ZIP_CODES);

            int index = 0;
            String[] newList = new String[data.size()-1];
            Integer position = (Integer)((View)view.getParent()).getTag();
            for(int i = 0; i < data.size(); i++)
            {
                if(i != position)
                {
                    View item = mList.getChildAt(i);
                    newList[index++] = ((TextView)item.findViewById(R.id.text)).getText().toString();
                }
            }
            HashSet<String> uniqueSet = new HashSet<String>(Arrays.asList(newList));
            mDialog.show();
            new WriteBackPreferencesTask().execute(uniqueSet);
            mAdapter = new EditableArrayAdapter(getActivity(), 0, uniqueSet.toArray(new String[uniqueSet.size()]));
            mAdapter.setOnRemoveItemClickListener(new RemoveFieldListener());
            mList.setAdapter(mAdapter);
        }
    }

    private class WriteBackPreferencesTask extends AsyncTask<HashSet<String>, Void, Void>
    {
        protected Void doInBackground(HashSet<String>... zipcodes)
        {
            SharedPreferences preferences = mContext.getSharedPreferences(SAVED_ZIP_CODES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(SAVED_ZIP_CODES_KEY, zipcodes[0]);
            editor.commit();

            return null;
        }

        protected void onPostExecute(Void result)
        {
            mDialog.dismiss();
        }
    }
}
