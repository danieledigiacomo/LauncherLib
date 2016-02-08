package it.me.launcherlib.model;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.net.rtp.RtpStream;
import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.Telephony.Sms;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SMS{
	
	public static int TYPE_INCOMING = 0;
	public static int TYPE_OUTCOMING = 1;
	
	private static int MESSAGES_FOR_PAGE = 10;
	private static final String SMS_INCOMING_URI = "content://sms/inbox";
	private static final String SMS_OUTCOMING_URI = "content://sms/sent";
	public static final String FORMAT_DAY = "dd MMMM yyyy";
	public static final String FORMAT_HOUR = "HH:mm";

	
	private String id; 
	private String conversationId; 
	private String address;
	private String number;
	private String type;
	private String body;
	private String date;
	private String status;
	
	/*****COSTRUTTORI*****/
	public SMS(){}
	
	/*****METODI SET*****/
	private void setId(String id){							this.id = id;			}
	public void setAddress(String address){					this.address = address;			}
	public void setBody(String body){						this.body = body;				}
	public void setDate(String date){						this.date = date;				}
	public void setStatus(String status){					this.status = status;			}
	
	/*****METODI GET*****/
	public String getId(){						return this.id;					}
	public String getAddress(){					return this.address;			}
	public String getBody(){					return this.body;				}
	public String getDate(){					return this.date;				}
	public String getStatus(){					return this.status;				}
		
	public boolean isSelf(){
		return !type.equals(String.valueOf(Sms.MESSAGE_TYPE_INBOX));
	}
	
	public String getDate(String formatType){
		Date date = new Date(Long.valueOf(this.date));
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date); 
                
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
	
	public boolean isReaded(){
		Log.e("STATUS",String.valueOf(status));
		return status.equals("1");
	}
	
	public static ArrayList<SMS> getConversation(Context context, String threadId, int page,  Handler doneHandler){
		String strOrder = android.provider.CallLog.Calls.DATE + " ASC ";		
		if(page!=-1){
			strOrder.concat("LIMIT " + String.valueOf(MESSAGES_FOR_PAGE) + " OFFSET " + String.valueOf(MESSAGES_FOR_PAGE*(page)));
		}
		ArrayList<SMS> list = new ArrayList<SMS>();
		ContentResolver contentResolver = context.getContentResolver();
		final String[] projection = new String[]{"*"};
		Uri uri = Uri.parse("content://sms/" );
		Cursor cur = contentResolver.query(uri, null,  "thread_id = "+ threadId, null, strOrder);
		
		while (cur.moveToNext()) {
			SMS sms = new SMS();
			String address = cur.getString(cur.getColumnIndexOrThrow("address"));
			sms.setNumber(address);
			sms.setId(cur.getString(cur.getColumnIndexOrThrow("_id")));
			sms.setAddress(getContactDisplayNameByNumber(context, address));
			sms.setBody(cur.getString(cur.getColumnIndexOrThrow("body")));
			sms.setDate(cur.getString(cur.getColumnIndexOrThrow("date")));
			sms.setType(cur.getString(cur.getColumnIndexOrThrow("type")));
			
			//sms.setStatus(cur.getString(cur.getColumnIndexOrThrow("read")));			
			list.add(sms);
		}
		cur.close();
		if(doneHandler!=null){
			Message message = new Message();
			message.obj = list;
			doneHandler.sendMessage(message);	
		}
		return list;
		
	}
	public static String getConversationIdByNumber(Context context, String number,  Handler doneHandler){
		String strOrder = android.provider.CallLog.Calls.DATE + " ASC ";		
		
		ArrayList<SMS> list = new ArrayList<SMS>();
		ContentResolver contentResolver = context.getContentResolver();
		final String[] projection = new String[]{"*"};
		Uri uri = Uri.parse("content://sms/" );
		Cursor cur = contentResolver.query(uri, new String[] { "thread_id"} ,  "address=?", new String[]{number}, strOrder);
		String threadId = "";
		while (cur.moveToNext()) {
			SMS sms = new SMS();
			threadId = cur.getString(cur.getColumnIndexOrThrow("thread_id"));
			
		}
		cur.close();
		if(doneHandler!=null){
			Message message = new Message();
			message.obj = list;
			doneHandler.sendMessage(message);	
		}
		return threadId;
		
	}
	
	public static ArrayList<SMS> getMessages(Context context, int type, Handler doneHandler){
		ArrayList<SMS> list = new ArrayList<SMS>();
		String uri;
		if(type == TYPE_INCOMING){
			uri = SMS_INCOMING_URI;
		}else{
			uri = SMS_OUTCOMING_URI;
		}
		Uri uriSMSURI = Uri.parse(uri);
		Cursor cur = context.getContentResolver().query(uriSMSURI, null, null, null, null);
		
		while (cur.moveToNext()) {
			SMS sms = new SMS();
			String address = cur.getString(cur.getColumnIndexOrThrow("address"));
			sms.setId(cur.getString(cur.getColumnIndexOrThrow("_id")));
			sms.setConversationId(cur.getString(cur.getColumnIndexOrThrow("thread_id")));
			sms.setAddress(getContactDisplayNameByNumber(context, address));
			sms.setBody(cur.getString(cur.getColumnIndexOrThrow("body")));
			sms.setDate(cur.getString(cur.getColumnIndexOrThrow("date")));
			sms.setStatus(cur.getString(cur.getColumnIndexOrThrow("read")));			
			list.add(sms);
		}
		cur.close();
		Message message = new Message();
		message.obj = list;
		doneHandler.sendMessage(message);
		return list;
		
	}
	
	/*public static void sendSMS(final Context context,String phoneNumber, String message, final ConversationAdapter adapter, final int position){        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
            new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
            new Intent(DELIVERED), 0);

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if(adapter!=null){
                	switch (getResultCode()){
                    case Activity.RESULT_OK:
                    	((SMS)adapter.getItem(position)).setType(String.valueOf(Sms.MESSAGE_TYPE_SENT));
                        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                        
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    	((SMS)adapter.getItem(position)).setType(String.valueOf(Sms.MESSAGE_TYPE_FAILED));
                        Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    	((SMS)adapter.getItem(position)).setType(String.valueOf(Sms.MESSAGE_TYPE_FAILED));
                    	Toast.makeText(context, "No service",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    	((SMS)adapter.getItem(position)).setType(String.valueOf(Sms.MESSAGE_TYPE_FAILED));
                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                    	((SMS)adapter.getItem(position)).setType(String.valueOf(Sms.MESSAGE_TYPE_FAILED));
                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                	}
                	adapter.notifyDataSetChanged();
                }
            	
            }
        }, new IntentFilter(SENT));

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered",Toast.LENGTH_SHORT).show();
                        break;
                        
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;                        
                }
                
            }
        }, new IntentFilter(DELIVERED));        

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }*/
	
	
	public static void setReaded(Context context, String id, boolean readed){
		ContentValues values = new ContentValues();
		values.put("read",readed);
		context.getContentResolver().update(Uri.parse("content://sms/"),values, "_id="+id, null);
	}
	
	
	
	public static String getContactDisplayNameByNumber(Context context, String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String name = number;

	    ContentResolver contentResolver = context.getContentResolver();
	    Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
	            ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	            contactLookup.moveToNext();
	            name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	            //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
	        }
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return name;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}




}
