package it.me.launcherlib.pagination;

import java.util.ArrayList;
import java.util.List;


public class Pagination {

	public static final int ITEMS_FOR_PAGE = 15;
	
	public static final int ITEMS_FOR_PAGE_6 = 6;
	
	public static <T> ArrayList<ArrayList<T>> chopped(List<T> list, final int L) {
		 ArrayList<ArrayList<T>> parts = new ArrayList<ArrayList<T>>();
	     final int N = list.size();
	     for (int i = 0; i < N; i += L) {
	         parts.add(new ArrayList<T>(
	             list.subList(i, Math.min(N, i + L)))
	         );
	         
	     }
	     return parts;
	 
	}
	
	
}
