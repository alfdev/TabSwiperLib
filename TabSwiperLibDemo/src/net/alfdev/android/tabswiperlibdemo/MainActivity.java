package net.alfdev.android.tabswiperlibdemo;


import net.alfdev.android.tabswiperlib.TabSwiper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity 
{

	private MyAdapter adapter;
	private TabSwiper ts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ts = (TabSwiper) findViewById(R.id.tabSwiper1);
		adapter = new MyAdapter(getSupportFragmentManager());
		ts.setAdapter(adapter);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class MyAdapter extends FragmentStatePagerAdapter
	{
		public MyAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) 
		{
			return NumericFragment.NewInstance(position);
		}

		@Override
		public int getCount() 
		{
			return 12;
		}
	}
}
