package org.starlo.climate;

import android.os.*;
import android.app.*;
import android.view.*;

public class ZipcodeInputFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.zipcode_input, container, false);
    }
}
