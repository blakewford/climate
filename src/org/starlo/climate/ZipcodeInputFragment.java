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
            SharedPreferences.Editor editor = preferences.edit();
            HashSet<String> uniqueSet = new HashSet<String>(Arrays.asList(newList));
            editor.putStringSet(SAVED_ZIP_CODES_KEY, uniqueSet);
            editor.commit();

            mAdapter = new EditableArrayAdapter(getActivity(), 0, uniqueSet.toArray(new String[uniqueSet.size()]));
            mAdapter.setOnRemoveItemClickListener(new RemoveFieldListener());
            mList.setAdapter(mAdapter);
        }
    }

    private class RemoveFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            Integer position = (Integer)((View)view.getParent()).getTag();
        }
    }
}
