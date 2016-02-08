package it.me.launcherlib.model;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import it.me.launcherlib.database.DBFirewall;
import it.me.launcherlib.database.DBFirewallCalls;
import it.me.launcherlib.database.DBFirewallGold;
import it.me.launcherlib.database.DBFirewallSms;
import it.me.launcherlib.database.Database;

public class Firewall {

	public static String PREFIX = "+39";
	
	public static final int NESSUNO = 0;
	public static final int TUTTI = 1;
	public static final int DA_RUBRICA = 2;
	
	public static final int OFF = 0;
	public static final int ON = 1;
	
	
	private int calls;
	private int sms;
	private int email;
	private int keyboard;
	private int earphones;
	
	public Firewall(){}

	
	
	public int getCalls() {
		return calls;
	}

	public void setCalls(int calls) {
		this.calls = calls;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public int getEmail(){
		return email;
	}
	
	public void setEmail(int email){
		this.email = email;
	}
	public int getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(int keyboard) {
		this.keyboard = keyboard;
	}

	public int getEarphones() {
		return earphones;
	}

	public void setEarphones(int earphones) {
		this.earphones = earphones;
	}
	
	public boolean emailIsEnabled(){
		return this.email== 1;
	}
	
	public boolean keyboardIsEnabled(){
		return this.keyboard == 1;
	}
	
	public boolean earphonesIsRequired(){
		return this.earphones == 1;
	}
	
	public static boolean canCall(Context context, String number){
		boolean result = false;
		if(isGoldNumber(context, number)){
			result = true;
		}else{
			
			if(checkEarphones(context)){
				DBFirewall dbFirewall = new DBFirewall(context);
				Firewall firewall = dbFirewall.getFirewall();
				int permission = firewall.getCalls();
				switch (permission) {
				case TUTTI:
					result = true;
					break;

				case NESSUNO:
					result = false;
					break;

				case DA_RUBRICA:
					DBFirewallCalls dbCalls = new DBFirewallCalls(context);
					result = dbCalls.has(number);
					break;
				}
			}else{
				result = false;
			}
			
		}
		return result;
	}
	
	public static boolean canMessage(Context context, String number){
		boolean result = false;
		if(isGoldNumber(context, number)){
			result = true;
		}else{
			//ERRORE: eliminare if(checkEarphones)
			if(checkEarphones(context)){
				DBFirewall dbFirewall = new DBFirewall(context);
				Firewall firewall = dbFirewall.getFirewall();
				int permission = firewall.getSms();
				switch (permission) {
				case TUTTI:
					result = true;
					break;

				case NESSUNO:
					result = false;
					break;

				case DA_RUBRICA:
					DBFirewallSms dbCalls = new DBFirewallSms(context);
					result = dbCalls.has(number);
					break;
				}
			}else{
				result = false;
			}
		}
		return result;
	}
	
	public static boolean emailEnabled(Context context){
		boolean result = false;
		DBFirewall dbFirewall = new DBFirewall(context);
		Firewall firewall = dbFirewall.getFirewall();
		result = firewall.emailIsEnabled();
		return result;
	}
	
	public static boolean checkEarphones(Context context){
		boolean result = true;
		DBFirewall db = new DBFirewall(context);
		String value = db.getValue(DBFirewall.KEY_EARPHONES);
		if(value.equals("1")){
			if(earphonesPluggedIn(context)){
				result = true;
			}else{
				result = false;
			}
		}else{
			result = true;
		}
		return result;
	}
	
	public static boolean earphonesPluggedIn(Context context){
		AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		return am.isWiredHeadsetOn();
	}
	
	public static boolean isGoldNumber(Context context, String number){
		DBFirewallGold db = new DBFirewallGold(context);
		return db.has(number);
	}
	
	public static int getGoldNumberSize(Context context){
		int size = 0;
		DBFirewallGold db = new DBFirewallGold(context);
		size = db.getNumbersGroupPrefix().size();
		return size;
	}
	
}
