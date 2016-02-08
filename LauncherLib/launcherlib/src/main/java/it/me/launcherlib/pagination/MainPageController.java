package it.me.launcherlib.pagination;

import android.view.View;
public class MainPageController {

	public CustomPagerAdapter pagerAdapter;
	public static CustomViewPager pager;
	
	public void set(CustomPagerAdapter pagerAdapter,CustomViewPager pager){
		this.pagerAdapter = pagerAdapter;
		this.pager = pager;
		
		//ContainerPageController.pager.setOnPageChangeListener(new ContainerPageListener(ContainerPageController.pager));
	}
	public void setPager(View page, int customViewPagerResId){
		this.pagerAdapter = new CustomPagerAdapter();	
		this.pager = (CustomViewPager) page.findViewById(customViewPagerResId);
		this.pager.setAdapter (this.pagerAdapter);
	}
	
	public View getPage(int numberPage){
		return this.pagerAdapter.getView(numberPage);
	}
	public void addPage(View newPage,boolean goToPage){
		int pageIndex = this.pagerAdapter.addView(newPage);
		this.pagerAdapter.notifyDataSetChanged();
		if(goToPage){
			this.pager.setCurrentItem (pageIndex, true);
		}
		
	}
	public void removePage(View defunctPage){
	    int pageIndex = this.pagerAdapter.removeView (this.pager, defunctPage);
	    if (pageIndex == this.pagerAdapter.getCount())
	    	pageIndex--;
	    this.pager.setCurrentItem (pageIndex);
	    this.pagerAdapter.notifyDataSetChanged();
		
	}
	public void removeAllPages(){
		if(pagerAdapter.getCount() !=0){
			this.pagerAdapter.removeAllViews(this.pager);	
		};
		
	}
	

	public void goToPage (int numberPage,boolean flag){
		this.pager.setCurrentItem(numberPage, flag);
	}
	
	public void refreshPage(View page,int pageNumber){
		this.pagerAdapter.addView (page, pageNumber+1);
		this.pagerAdapter.removeView(pager, pageNumber);
		this.goToPage(pageNumber,false);
		this.pagerAdapter.notifyDataSetChanged();
	}
	
	
	
}
