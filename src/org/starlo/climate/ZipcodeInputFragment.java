package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;

public class ZipcodeInputFragment extends Fragment
{
    private ListView mList = null;
    private ArrayAdapter mAdapter = null;
    private final String[] DEFAULT_ZIP_CODES = new String[]{"78757", "78758", "78759"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.zipcode_input, container, false);
        mList = (ListView)view.findViewById(R.id.list);
        mAdapter = new EditableArrayAdapter(getActivity(), 0, DEFAULT_ZIP_CODES);
        mList.setAdapter(mAdapter);
        Button addButton = (Button)view.findViewById(R.id.add);
        addButton.setOnClickListener(new AddFieldListener());

        return view;
    }

    private class AddFieldListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            String[] newList = new String[mAdapter.getCount()+1];
            for(int i = 0; i < DEFAULT_ZIP_CODES.length; i++)
            {
                newList[i] = DEFAULT_ZIP_CODES[i];
            }
            mAdapter = new EditableArrayAdapter(getActivity(), 0, newList);
            mList.setAdapter(mAdapter);
        }
    }
}
