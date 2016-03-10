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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.zipcode_input, container, false);
        mList = (ListView)view.findViewById(R.id.list);
        Button addButton = (Button)view.findViewById(R.id.add);
        addButton.setOnClickListener(new AddFieldListener());
        Button nextButton = (Button)view.findViewById(R.id.next);
        nextButton.setOnClickListener(new NextListener());

        updateAdapter(MainActivity.readPreferences(mContext));

        return view;
    }

    private void updateAdapter(Set<String> data)
    {
        mAdapter = new EditableArrayAdapter(mContext, 0, data.toArray(new String[data.size()]));
        mAdapter.setOnSingleItemClickListener(new SingleNextListener());
        mAdapter.setOnRemoveItemClickListener(new RemoveFieldListener());
        mList.setAdapter(mAdapter);
    }

    private String readFromListUI(int index)
    {
        View item = mList.getChildAt(index);
        return ((TextView)item.findViewById(R.id.text)).getText().toString();
    }

    private class AddFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            Set<String> data = MainActivity.readPreferences(mContext);
            int size = data.size();
            String[] newList = new String[size+1];
            for(int i = 0; i < size; i++)
            {
                newList[i] = readFromListUI(i);
            }
            newList[size] = "";
            updateAdapter(((MainActivity)mContext).writeBackPreferences(newList));
        }
    }

    private class RemoveFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            Set<String> data = MainActivity.readPreferences(mContext);

            int index = 0;
            String[] newList = new String[data.size()-1];
            Integer position = (Integer)((View)view.getParent()).getTag();
            for(int i = 0; i < data.size(); i++)
            {
                if(i != position)
                {
                    newList[index++] = readFromListUI(i);
                }
            }
            updateAdapter(((MainActivity)mContext).writeBackPreferences(newList));
        }
    }

    private class NextListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            Set<String> data = MainActivity.readPreferences(mContext);
            String[] newList = new String[data.size()];
            for(int i = 0; i < data.size(); i++)
            {
                newList[i] = readFromListUI(i);
            }
            updateAdapter(((MainActivity)mContext).writeBackPreferences(newList));
            mContext.startActivity(new Intent(mContext, CompareWeatherActivity.class));
        }
    }

    private class SingleNextListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            Intent intent = new Intent(mContext, DetailWeatherActivity.class);
            View parent = (View)view.getParent();
            intent.putExtra(MainActivity.DETAIL_INTENT_ZIP_PARAM,((TextView)parent.findViewById(R.id.text)).getText().toString());
            mContext.startActivity(intent);
        }
    }
}
