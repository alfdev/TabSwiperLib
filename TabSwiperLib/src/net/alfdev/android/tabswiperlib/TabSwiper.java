/**
 * Copyright 2013 Alfredo De Vito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.alfdev.android.tabswiperlib;

import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;

import net.alfdev.android.tabswiperlib.R;
import net.alfdev.android.tabswiperlib.animations.ZoomOutPageTransformer;

public class TabSwiper extends FrameLayout implements OnTouchListener, ViewPager.OnPageChangeListener
{
	public final static String TAG = "TabSwiperLib";
	
	private ViewPager view_pager;
	private LinearLayout pager_balls;
	private TranslateAnimation hide_animation;
	private TranslateAnimation show_animation;
	private AnimatorSet ball_animation;
	private AnimatorSet ball_animation_reverse;
	private int current_page = 0;
	private boolean pager_shown = false; // wheather paging indicator is shown
	
	public TabSwiper(Context context)
	{
		super(context);
		
		Initialize();
	}
	
	public TabSwiper(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		Initialize();
	}
	
	public void setAdapter(FragmentStatePagerAdapter adapter)
	{
		if (view_pager != null)
			view_pager.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
		//TODO: throw exception if view_pager is null
		
		// populate pager indicator
		int count = view_pager.getAdapter().getCount();
		if (count > 0)
		{
			
			for(int i = 0; i < count; i++)
			{
				LayoutInflater.from(getContext()).inflate(R.layout.ts_single_pager_ball, pager_balls);
			}

			ball_animation.setTarget(pager_balls.getChildAt(current_page));
			ball_animation.start();
		}
	}
	
	public void updateAdapter()
	{
		view_pager.getAdapter().notifyDataSetChanged();
		view_pager.postInvalidate();
	}
	
	protected void Initialize()
	{
		// inflate the layout
		LayoutInflater.from(getContext()).inflate(R.layout.ts_view_base, this,  true);
		
		// create animations
		hide_animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		hide_animation.setInterpolator(new DecelerateInterpolator());
		hide_animation.setDuration(120);
		hide_animation.setRepeatCount(0);
		hide_animation.setFillAfter(true);
		hide_animation.setStartOffset(500);
		hide_animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				pager_shown = false;
			}
		});
		
		show_animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		show_animation.setInterpolator(new DecelerateInterpolator());
		show_animation.setDuration(120);
		show_animation.setRepeatCount(0);
		show_animation.setFillAfter(true);
		show_animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				pager_shown = true;
			}
		});
		
		// init views
		view_pager = (ViewPager) findViewById(R.id.ts_pager);
		view_pager.setPageTransformer(true, new ZoomOutPageTransformer());
		view_pager.setOnTouchListener(this);
		view_pager.setOnPageChangeListener(this);
		pager_balls = (LinearLayout) findViewById(R.id.ts_count_pages);
		
		ball_animation = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_simple_scale_ball);
		ball_animation_reverse = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_simple_scale_ball);
		ball_animation_reverse.setInterpolator(new ReverseInterpolator());
		
		ball_animation.setInterpolator(new LinearInterpolator());
		
		// call the hide animation
		pager_balls.startAnimation(hide_animation);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		// handle touch events to show pager ui element
		if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
		{
			if (pager_shown)
				pager_balls.startAnimation(hide_animation);
		} 
		else if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (!pager_shown)
				pager_balls.startAnimation(show_animation);
		}

		return false;
	}

	// ViewPager.OnPageChangeListener implementation
	
	@Override
	public void onPageScrollStateChanged(int state) {}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

	@Override
	public void onPageSelected(int position) 
	{
		//ball_animation_reverse.setInterpolator(new ReverseInterpolator());
		ball_animation_reverse.setTarget(pager_balls.getChildAt(current_page));
		ball_animation_reverse.start();
		
		//ball_animation.setInterpolator(new LinearInterpolator());
		ball_animation.setTarget(pager_balls.getChildAt(position));
		ball_animation.start();
		
		// save current page index
		current_page = position;
	}
	
	// **********************************************
	
	// an interpolator to reverse an Animation
	private class ReverseInterpolator implements Interpolator
	{

		@Override
		public float getInterpolation(float input) 
		{
			return Math.abs(input -1.0f);
		}
		
	}
}