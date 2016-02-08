package it.me.launcherlib.model;



import it.me.launcherlib.ContactPrefix;
import it.me.launcherlib.R;
import it.me.launcherlib.database.DBFirewall;
import it.me.launcherlib.database.DBFirewallCalls;
import it.me.launcherlib.database.DBFirewallGold;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class Call extends Notification{

	private static int CALLS_FOR_PAGE = 40;
	public static int NO_FILTER = -1;
	
	private String name;
	private String number; 
	private int photoId;
	private int type; 
	private String date; 
	private String duration;
	private String isRead;
	
	public Call(){
		this.setNotificationType(Notification.CALL);
	}
	
	public String getRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public boolean isRead(){
		return this.isRead.equals("1");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasContactName(){
		return getName() != null;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDuration() {
		return duration + "s";
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getHour(){
  		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
  		Date callHourTime = new Date(Long.valueOf(getDate()));
	    String hour = sdf.format(callHourTime); 
	    return hour;
	}
	
	public String getDay(){
  		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
  		Date callDayTime = new Date(Long.valueOf(getDate()));
	    String day = sdf.format(callDayTime); 
	    return day;
	}
	
	public long getDayAsLong(){
  		Date callDayTime = new Date(Long.valueOf(getDate()));
	    String day = String.valueOf(callDayTime.getDay()).concat(String.valueOf(callDayTime.getMonth())).concat(String.valueOf(callDayTime.getYear()));
  		long dayAsLong = Long.valueOf(day);
	    return dayAsLong;
	}
	
	public int getTypeIcon(){
		int type = getType();
		return getTypeIcon(type);//getTypeIcon(Integer.getInteger(getType()));
	}
	
	public static int getTypeIcon(int type){
		int resId = R.drawable.ic_launcher;
		switch (type) {
		case Calls.OUTGOING_TYPE:
			resId = R.drawable.ic_call_outcoming;
			break;
		case Calls.INCOMING_TYPE:
			resId = R.drawable.ic_call_incoming;
			break;
		case Calls.MISSED_TYPE:
			resId = R.drawable.ic_call_missed;
			break;

		}
		return resId;
	}
	
	public static ArrayList<Call> getHystory(Activity activity, int type, int page){
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC LIMIT " + String.valueOf(CALLS_FOR_PAGE) + " OFFSET " + String.valueOf(CALLS_FOR_PAGE*(page));
		
		String whereKeys;
		String[] whereValues;
		if(type!= NO_FILTER){
			whereKeys = android.provider.CallLog.Calls.TYPE + "=?";
			whereValues = new String[]{String.valueOf(type)};	
		}else{
			whereKeys = null;
			whereValues = null;
		}
		
		Cursor cursor = activity.managedQuery(CallLog.Calls.CONTENT_URI, null, whereKeys, whereValues, strOrder);
		ArrayList<Call> calls = createList(activity,cursor);
		cursor.close();
		return calls;
	}
	
	public static ArrayList<Call> getUnrededCalls(Context context, Handler doneHandler){
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC  ";
		
		String whereKeys;
		String[] whereValues;
		whereKeys = android.provider.CallLog.Calls.IS_READ+ "=?";
		whereValues = new String[]{String.valueOf(0)};	
		
		//Cursor cursor = activity.managedQuery(CallLog.Calls.CONTENT_URI, null, whereKeys, whereValues, strOrder);
		Cursor cursor =  context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, whereKeys, whereValues, strOrder);
		ArrayList<Call> calls = createList(context,cursor);
		cursor.close();
		if(doneHandler!=null){
			Message message = new Message();
			message.obj = calls;
			doneHandler.sendMessage(message);
		}
		return calls;
	}
	
	public static ArrayList<Call> createList(Context context, Cursor cursor){
		
		ArrayList<String> numbers = new ArrayList<String>();
		ArrayList<Call> calls = new ArrayList<Call>();
		DBFirewallGold dbGold = new DBFirewallGold(context);
		ArrayList<String> goldNumbers = dbGold.getNumbers();
		DBFirewall db = new DBFirewall(context);
		int callPermission = db.getFirewall().getCalls();
		if(callPermission==0){
			//return calls;
		}else if(callPermission==1){
		}else if(callPermission==2){
			DBFirewallCalls dbCalls = new DBFirewallCalls(context);
			numbers = dbCalls.getNumbers();
		}
		int number = cursor.getColumnIndex(CallLog.Calls.NUMBER); 
		int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int photoId = cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_ID);
		int type = cursor.getColumnIndex(CallLog.Calls.TYPE); 
		int date = cursor.getColumnIndex(CallLog.Calls.DATE); 
		int duration = cursor.getColumnIndex(CallLog.Calls.DURATION); 
		int is_read = cursor.getColumnIndex(CallLog.Calls.IS_READ); 
		
		while (cursor.moveToNext()) { 
			String contactName = cursor.getString(name); 
			String contactPhotoId = cursor.getString(photoId);
			String phNumber = cursor.getString(number); 
			String callType = cursor.getString(type); 
			String callDate = cursor.getString(date); 
			String callDuration = cursor.getString(duration);
			String isRead = cursor.getString(is_read);
			if(isRead==null || isRead.equals(null) || isRead.equals("")){
				isRead = "1";
			}
			phNumber = ContactPrefix.checkPrefix(phNumber);
			
			Call call = new Call();
			call.setName(contactName);
			call.setPhotoId(Integer.parseInt(contactPhotoId));
			call.setNumber(phNumber);
			call.setType(Integer.parseInt(callType));//castType(callType));
			call.setDate(callDate);
			call.setDuration(callDuration);
			call.setIsRead(isRead);
			
			if(goldNumbers.contains(phNumber)){
				calls.add(call);
			}else{
				if(callPermission==1 || (callPermission==2 && numbers.contains(phNumber))){
					calls.add(call);
				}
			}
			
		
		}
		cursor.close();
		return calls;
	}
	
	public static String getContactIdFromNumber(Activity activity,String number) {
	    String[] projection = new String[]{Contacts.Phones.PERSON_ID};
	    Uri contactUri = Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL,
	        Uri.encode(number));
	    Cursor c = activity.getContentResolver().query(contactUri, projection,
	        null, null, null);
	    if (c.moveToFirst()) {
	        String contactId=c.getString(c.getColumnIndex(Contacts.Phones.PERSON_ID));
	        c.close();
	        return contactId;
	    }
	    c.close();
	    return null;
	}
	
	public static int getContactIDFromNumber(String contactNumber,Context context)
	{
	    contactNumber = Uri.encode(contactNumber);
	    int phoneContactID = new Random().nextInt();
	    Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,contactNumber),new String[] {PhoneLookup.DISPLAY_NAME, PhoneLookup._ID}, null, null, null);
	        while(contactLookupCursor.moveToNext()){
	            phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(PhoneLookup._ID));
	            }
	        contactLookupCursor.close();

	    return phoneContactID;
	}
	
	public static Uri getPhotoUri(Activity activity, int contactId) {
	    /*try {
	        Cursor cur = activity.getContentResolver().query(
	                ContactsContract.Data.CONTENT_URI,
	                null,
	                ContactsContract.Data.CONTACT_ID + "=" + contactId + " AND "
	                        + ContactsContract.Data.MIMETYPE + "='"
	                        + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
	                null);
	        if (cur != null) {
	            if (!cur.moveToFirst()) {
	            	cur.close();
	                return null; // no photo
	            }
	            cur.close();
	        } else {
	        	cur.close();
	            return null; // error in cursor process
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }*/
	    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, (long)contactId);
	    	
	    return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
	}
	
	/*private static Integer fetchThumbnailId(Activity activity) {

	    final Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
	    final Cursor cursor = activity.getContentResolver().query(uri, PHOTO_ID_PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

	    try {
	        Integer thumbnailId = null;
	        if (cursor.moveToFirst()) {
	            thumbnailId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
	        }
	        return thumbnailId;
	    }
	    finally {
	        cursor.close();
	    }

	}*/

	
	private static final String[] PHOTO_BITMAP_PROJECTION = new String[] {
	    ContactsContract.CommonDataKinds.Photo.PHOTO
	};
	
	public static Bitmap fetchThumbnail(Activity activity, final int thumbnailId) {
	    final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
	    final Cursor cursor = activity.getContentResolver().query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);
	    try {
	        Bitmap thumbnail = null;
	        if (cursor.moveToFirst()) {
	            final byte[] thumbnailBytes = cursor.getBlob(0);
	            if (thumbnailBytes != null) {
	                thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
	            }
	        }
	        return thumbnail;
	    }
	    finally {
	        cursor.close();
	    }

	}
	
}