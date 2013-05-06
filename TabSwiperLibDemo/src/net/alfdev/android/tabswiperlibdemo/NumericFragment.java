package net.alfdev.android.tabswiperlibdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class NumericFragment extends Fragment 
{
	public static NumericFragment NewInstance(int index)
	{
		NumericFragment fragment = new NumericFragment();
		
		Bundle args = new Bundle(1);
		args.putInt("INDEX", index);
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		int index = getArguments().getInt("INDEX");
		
		TextView tv = new TextView(getActivity().getApplicationContext());
		tv.setText("" + index);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(72.0f);
		tv.setTextColor(Color.DKGRAY);
		
		if (index % 2 == 0)		
			tv.setBackgroundColor(Color.RED);
		else
			tv.setBackgroundColor(Color.GREEN);
		//container.addView(tv);
		
		return tv;
	}
}