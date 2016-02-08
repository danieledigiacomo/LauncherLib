package it.me.launcherlib;

public class ContactPrefix {
	
	public static String checkPrefix(String number){
		if(!number.contains("+39")){
			number = "+39" + number;
		}
		return number;
	}
	
}
