package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.*;

public class ZipcodeInputFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final String[] zipcodes = new String[]{"78757", "78758", "78759"};
        View view = inflater.inflate(R.layout.zipcode_input, container, false);
        ListView listView = (ListView)view.findViewById(R.id.list);
        listView.setAdapter(new EditableArrayAdapter(getActivity(), 0, zipcodes));

        return view;
    }
}
