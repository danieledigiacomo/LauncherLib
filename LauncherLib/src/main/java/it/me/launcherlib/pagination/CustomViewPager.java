package it.me.launcherlib.pagination;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

@SuppressLint("ClickableViewAccessibility")
public class CustomViewPager extends ViewPager {

	private boolean enabled;
	
	public CustomViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.enabled = true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (this.enabled) {
	        return super.onTouchEvent(event);
	    }
	
	    return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
	    if (this.enabled) {
	        return super.onInterceptTouchEvent(event);
	    }
	
	    return false;
	}
	
	public void setPagingEnabled(boolean enabled) {
	    this.enabled = enabled;
	}
	
	

	/*public void removeAllPagerExceptPages(int whiteList[]) {
		Log.e("this.getAdapter().getCount()",String.valueOf(this.getAdapter().getCount()));
		for(int pageNumber=this.getAdapter().getCount()-1; pageNumber>0; pageNumber--){
			Log.e("pageNumber",String.valueOf(pageNumber));
			for(int lockedPage=0; lockedPage<whiteList.length;lockedPage++){
				Log.e("*pageNumber="+String.valueOf(pageNumber),"whiteList[lockedPage]="+String.valueOf(whiteList[lockedPage]));
				
				if(pageNumber!=whiteList[lockedPage]){
					Log.e("*****","pageNumber!=whiteList[lockedPage]");
					((ContainerPagerAdapter)this.getAdapter()).views.remove(pageNumber);
				    this.setAdapter((ContainerPagerAdapter)this.getAdapter());
					//((MainPagerAdapter)this.getAdapter()).removeView(this, pageNumber);
				}
			}
			Log.e("pagenumber",String.valueOf(pageNumber));
		}
		((ContainerPagerAdapter)this.getAdapter()).notifyDataSetChanged();
		
	}*/



}