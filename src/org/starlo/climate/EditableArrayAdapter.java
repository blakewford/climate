package org.starlo.climate;

import android.view.*;
import android.widget.*;
import android.content.*;

public class EditableArrayAdapter extends ArrayAdapter<String>
{
    private Context mContext = null;
    private String[] mZipcodes = null;
    private LayoutInflater mInflater = null;

    public EditableArrayAdapter(Context context, int resource, String[] zipcodes)
    {
        super(context, resource, zipcodes);

        mContext = context;
        mZipcodes = zipcodes;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = mInflater.inflate(R.layout.zipcode_item, null);
        ((TextView)view.findViewById(R.id.text)).setText(mZipcodes[position]);

        return view;
    }
}
