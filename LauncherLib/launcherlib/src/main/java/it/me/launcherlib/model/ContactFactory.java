package it.me.launcherlib.model;

import it.me.launcherlib.IOUtil;
import it.me.launcherlib.database.DBFirewallCalls;
import it.me.launcherlib.database.DBFirewallGold;
import it.me.launcherlib.database.DBFirewallSms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ContactFactory {
	
	public static final int TYPE_CALLS = 0;
	public static final int TYPE_SMS = 1;
	public static final int TYPE_GOLD = 2;
	
	static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME,
        Contacts.STARRED,
        Contacts.TIMES_CONTACTED,
        Contacts.CONTACT_PRESENCE,
        Contacts.PHOTO_ID,
        Contacts.LOOKUP_KEY,
        Contacts.HAS_PHONE_NUMBER,
    };

	private String loadContactPhoto(Cursor cursor){
        if (cursor.getString(5) != null)
        {
            Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, cursor.getInt(0));
            return Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY).toString();
        }
        return null;
    }

	public static int insert(Activity activity,Contact contact){
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        Builder builder = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI);
        builder.withValue(RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName());
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getNumber());
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        ops.add(builder.build());

        
        //EMAIL
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.getEmail());
        ops.add(builder.build());
        
        
        // Picture
        if(contact.getPhotoPath()!=null && !contact.getPhotoPath().equals("")){
            try
            {
            	//Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(String.valueOf(rawContactInsertIndex)));
                Uri uri = Uri.fromFile(new File(contact.getPhotoPath()));
            	Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri );
                ByteArrayOutputStream image = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
                builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
                builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
                builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
                builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
                ops.add(builder.build());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if(contact.getUri()!=null){
        	 try
             {
             	//Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(String.valueOf(rawContactInsertIndex)));
                 Uri uri = contact.getUri();
             	 Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri );
                 ByteArrayOutputStream image = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
                 builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
                 builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
                 builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
                 builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
                 ops.add(builder.build());
             }
             catch (Exception e)
             {
                 e.printStackTrace();
                 Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
             }
        }
		
        // Add the new contact
        try{
            activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        
        }catch (Exception e){
            e.printStackTrace();
        }

        String select = "(" + Contacts.DISPLAY_NAME + " == \"" + contact.getName()+ "\" )";
        Cursor c = activity.getContentResolver().query(Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, select, null, Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        activity.startManagingCursor(c);

        return ops.size();
    }
	
	public static int update(Activity activity,Contact contact){
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        Builder builder = ContentProviderOperation.newUpdate(RawContacts.CONTENT_URI);
        builder.withValue(RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(Contacts._ID + " = ? ", new String[]{   contact.getId() });
        //builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName());
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(Contacts._ID + " = ? ", new String[]{   contact.getId() });
        //builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getNumber());
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        ops.add(builder.build());

        //EMAIL
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(Contacts._ID + " = ? ", new String[]{   contact.getId() });
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
        builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.getEmail());
        ops.add(builder.build());
        
        // Picture
        if(!contact.getPhotoPath().equals("")){
            try
            {
            	//Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(String.valueOf(rawContactInsertIndex)));
                Uri uri = Uri.fromFile(new File(contact.getPhotoPath()));
            	Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri );
                ByteArrayOutputStream image = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
                builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
                builder.withSelection(Contacts._ID + " = ? ", new String[]{   contact.getId() });
                //builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex);
                builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
                builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
                ops.add(builder.build());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
		
        // Add the new contact
        try{
            activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        
        }catch (Exception e){
            e.printStackTrace();
        }

        String select = "(" + Contacts.DISPLAY_NAME + " == \"" + contact.getName()+ "\" )";
        Cursor c = activity.getContentResolver().query(Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, select, null, Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        activity.startManagingCursor(c);

        return ops.size();
    }
	
		
		
	
	/*public static void updateContact(Activity activity, Contact contact){
		Log.e("UPDATE", "CONTACT ID: " +  contact.getId());
		Log.e("UPDATE", "CONTACT NAME: " +  contact.getName());
		Log.e("UPDATE", "CONTACT NUMBER: " +  contact.getNumber());
		
		
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        Builder builder = ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI);
        builder.withValue(RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contact.getId())});
        //builder.withSelection(ContactsContract.Contacts._ID + "=?",  new String[]{String.valueOf(contact.getId())});
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName());
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        //builder.withSelection(ContactsContract.Contacts._ID + "=?",  new String[]{String.valueOf(contact.getId())});
        builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contact.getId())});
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getNumber());
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        ops.add(builder.build());

        // Picture
        Toast.makeText(activity, String.valueOf("UPDATING"), Toast.LENGTH_SHORT).show();
        try
        {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse("/mnt/sdcard/tmp.jpg"));
            ByteArrayOutputStream image = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
            
            //byte[] byteArray = IOUtil.readFile("/mnt/sdcard/tmp.jpg");
            
            builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
            //builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contact.getId())});
           
            int rawContactId = getRawContactId(activity, Integer.valueOf(contact.getId()));
            Toast.makeText(activity, String.valueOf(rawContactId), Toast.LENGTH_SHORT).show();
            builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, Integer.valueOf(rawContactId));
            builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
            
            /*builder.withSelection(ContactsContract.CommonDataKinds.Photo.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(contact.getId()), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE});
            builder.withValue(ContactsContract.CommonDataKinds.Photo.RAW_CONTACT_ID, contact.getId());
            builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());*/
            /*builder.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
            builder.withValue(ContactsContract.Data.DATA15, image.toByteArray());
            ops.add(builder.build());
        }
        catch (Exception e)
        {
        	Log.e("UPLOADING CONTACT","ERROR: " + String.valueOf(e.getMessage()));
            e.printStackTrace();
        }

        // Update
        try{
        	activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }catch (Exception e){
        	Log.e("UPDATING ERROR","UPDATING ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }*/

	
	public static void delete(Activity activity,Contact contact){
        Cursor pCur = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{""+ contact.getId()}, null);
        while (pCur.moveToNext()){
            String lookupKey = pCur.getString(pCur.getColumnIndex(Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);
            activity.getContentResolver().delete(uri, null, null);
        }
    }
	
	public static void delete(Activity activity,int idContact){
        Cursor pCur = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{""+ idContact}, null);
        while (pCur.moveToNext()){
            String lookupKey = pCur.getString(pCur.getColumnIndex(Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);
            activity.getContentResolver().delete(uri, null, null);
        }
    }
	
	public static void delete(Activity activity,ArrayList<Contact> contacts){
		if(contacts!=null && contacts.size()>0){
			String whereIn = "(";
			for (int i = 0; i < contacts.size(); i++) {
				Contact contact = contacts.get(i);
				if(i==contacts.size()-1){
					whereIn += String.valueOf(contact.getId()) + ") ";
				}else{
					whereIn += String.valueOf(contact.getId()) + ",";	
				}
			}
			Log.e("WHERE",whereIn);
			Cursor pCur = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" IN " + whereIn, null, null);
	        while (pCur.moveToNext()){
	            String lookupKey = pCur.getString(pCur.getColumnIndex(Contacts.LOOKUP_KEY));
	            Uri uri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);
	            activity.getContentResolver().delete(uri, null, null);
	        }
		}
		
        
    }
	
	public static ArrayList<Contact> getContacts(Activity activity, boolean firewall, int type, ProgressBar progress, Handler publishHandler) {
		ArrayList<String> numbers = null;
		if(firewall){
			if(type == TYPE_CALLS){
				DBFirewallCalls dbFirewallCall = new DBFirewallCalls(activity);
				numbers = dbFirewallCall.getNumbers();	
			}else if(type == TYPE_SMS){
				DBFirewallSms dbFirewallSms = new DBFirewallSms(activity);
				numbers = dbFirewallSms.getNumbers();
			}else if(type == TYPE_GOLD){
				DBFirewallGold dbGold = new DBFirewallGold(activity);
				numbers = dbGold.getNumbers();
			}
		}
		String strOrder = Contacts.DISPLAY_NAME + " ASC ";
    	ArrayList<Contact> list = new ArrayList<Contact>();
    	ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI,null, null, null, strOrder);
        int count = 0;
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		
        		count++;
        		progress.setMax(cur.getCount());
            	progress.setProgress(count);
         		publishHandler.sendEmptyMessage(count);
        		
        		Contact contact = new Contact();
        		String id = cur.getString(cur.getColumnIndex(Contacts._ID));
        		String name = cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME));
        		int photoId = cur.getInt(cur.getColumnIndex(Contacts.PHOTO_ID));
        		
        		
        		
        		contact.setId(id);
        		contact.setName(name);
        		
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(Contacts.HAS_PHONE_NUMBER))) > 0) {
                    	Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
                    		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                    		 new String[]{id}, null);
                    	
                    	while (pCur.moveToNext()) {
                    		String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    		contact.setNumber(phoneNumber.replace(" ", ""));
                    		//Toast.makeText(NativeContentProvider.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    	} 
                     pCur.close();
    			}
        		
        		String email = "";
        		Cursor emailCur = cr.query( 
        				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
        				null,
        				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
        				new String[]{id}, null); 
    			while (emailCur.moveToNext()) { 
    			    email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    		 	    String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)); 
    		 	} 
    		 	emailCur.close();
    		 	contact.setEmail(email);
    		 	
        		
        		if(numbers!=null){
        			if(numbers.contains(contact.getNumber())){
        				contact.setEnabled(true);
        			}else{
        				contact.setEnabled(false);
        			}
        		}
        		list.add(contact);
        	}
        }
        cur.close();
        return list;
	 }
	
	
	public static Contact getContactById(Activity activity, boolean firewall, int type, int contactId) {
		ArrayList<String> numbers = null;
		if(firewall){
			if(type == TYPE_CALLS){
				DBFirewallCalls dbFirewallCall = new DBFirewallCalls(activity);
				numbers = dbFirewallCall.getNumbersGroupPrefix();	
			}else if(type == TYPE_SMS){
				DBFirewallSms dbFirewallSms = new DBFirewallSms(activity);
				numbers = dbFirewallSms.getNumbers();
			}else if(type == TYPE_GOLD){
				DBFirewallGold dbGold = new DBFirewallGold(activity);
				numbers = dbGold.getNumbersGroupPrefix();
			}
		}
		String strOrder = Contacts.DISPLAY_NAME + " ASC ";
		Contact contact = new Contact();
    	ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
       		 new String[]{ String.valueOf(contactId) }, strOrder);
        int count = 0;
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		
        		count++;
        		String id = cur.getString(cur.getColumnIndex(Contacts._ID));
        		String name = cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME));
        		int photoId = cur.getInt(cur.getColumnIndex(Contacts.PHOTO_ID));
        		
        		contact.setId(id);
        		contact.setName(name);
        		//contact.setPhoto(queryContactImage(activity, photoId));
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(Contacts.HAS_PHONE_NUMBER))) > 0) {
                    	Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
                    		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                    		 new String[]{id}, null);
                    	
                    	while (pCur.moveToNext()) {
                    		String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    		contact.setNumber(phoneNumber);
                    		//Toast.makeText(NativeContentProvider.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    	} 
                    	pCur.close();
    			}
        		
        		String email = "";
        		Cursor emailCur = cr.query( 
        				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
        				null,
        				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
        				new String[]{id}, null); 
    			while (emailCur.moveToNext()) { 
    			    email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    		 	    String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)); 
    		 	} 
    		 	emailCur.close();
    		 	contact.setEmail(email);
    		 	
        		if(numbers!=null){
        			if(numbers.contains(contact.getNumber())){
        				contact.setEnabled(true);
        			}else{
        				contact.setEnabled(false);
        			}
        		}
        	}
        }
        cur.close();
        return contact;
	 }
	
	public static Contact getContactByNumber(Activity activity, String displayName) {
		
		String strOrder = Contacts.DISPLAY_NAME + " ASC ";
		Contact contact = new Contact();
    	ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" = ?",
       		 new String[]{ displayName }, strOrder);
        int count = 0;
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		
        		count++;
        		String id = cur.getString(cur.getColumnIndex(Contacts._ID));
        		String name = cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME));
        		int photoId = cur.getInt(cur.getColumnIndex(Contacts.PHOTO_ID));
        		
        		contact.setId(id);
        		contact.setName(name);
        		//contact.setPhoto(queryContactImage(activity, photoId));
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(Contacts.HAS_PHONE_NUMBER))) > 0) {
                    	Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
                    		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                    		 new String[]{id}, null);
                    	
                    	while (pCur.moveToNext()) {
                    		String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    		contact.setNumber(phoneNumber);
                    		//Toast.makeText(NativeContentProvider.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    	} 
                    	pCur.close();
    			}
        		String email = "";
        		Cursor emailCur = cr.query( 
        				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
        				null,
        				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
        				new String[]{id}, null); 
    			while (emailCur.moveToNext()) { 
    			    email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    		 	    String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)); 
    		 	} 
    		 	emailCur.close();
    		 	contact.setEmail(email);
        		
        	}
        }
        cur.close();
        return contact;
	 }
	
	
	public static ArrayList<Contact> getGoldenContacts(Activity activity, ProgressBar progress, Handler publishHandler) {
		DBFirewallGold dbGold = new DBFirewallGold(activity);
		ArrayList<String> numbers = dbGold.getNumbers();
		
		String strOrder = Contacts.DISPLAY_NAME + " ASC ";
    	ArrayList<Contact> list = new ArrayList<Contact>();
    	ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI,null, null, null, strOrder);
        int count = 0;
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		
        		count++;
        		if(progress!=null){
        			progress.setMax(cur.getCount());
                	progress.setProgress(count);
        		}
        		if(publishHandler!=null){
        			publishHandler.sendEmptyMessage(count);	
        		}
        		
        		Contact contact = new Contact();
        		String id = cur.getString(cur.getColumnIndex(Contacts._ID));
        		String name = cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME));
        		int photoId = cur.getInt(cur.getColumnIndex(Contacts.PHOTO_ID));
        		
        		contact.setId(id);
        		contact.setName(name);
        		//contact.setPhoto(queryContactImage(activity, photoId));
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(Contacts.HAS_PHONE_NUMBER))) > 0) {
                    	Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
                    		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                    		 new String[]{id}, null);
                    	
                    	while (pCur.moveToNext()) {
                    		String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    		contact.setNumber(phoneNumber.replace(" ", ""));
                    	} 
                    	pCur.close();
    			}
        		String email = "";
        		Cursor emailCur = cr.query( 
        				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
        				null,
        				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
        				new String[]{id}, null); 
    			while (emailCur.moveToNext()) { 
    			    email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    		 	    String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)); 
    		 	} 
    		 	emailCur.close();
    		 	contact.setEmail(email);
        		
        		if(numbers!=null){
        			if(numbers.contains(contact.getNumber())){
        				contact.setEnabled(true);
        			}else{
        				contact.setEnabled(false);
        			}
        		}
        		if(numbers!=null){
        			if(numbers.contains(contact.getNumber())){
        				list.add(contact);
        			}
        		}
        	}
        }
        cur.close();
        return list;
	 }
	
	
	
	public static int getRawContactId(Context context , int contactId){
	    String[] projection = new String[]{RawContacts._ID};
	    String selection = RawContacts.CONTACT_ID+"=?";
	    String[] selectionArgs = new String[]{String.valueOf(contactId)};
	    
	    int rawContactId=-1;
	    Cursor c = context.getContentResolver().query(RawContacts.CONTENT_URI,projection,selection,selectionArgs , null);
	    if (c.moveToFirst()) {    
	        rawContactId = c.getInt(c.getColumnIndex(RawContacts._ID));
	    }
	    
	    Log.d("TAG","Contact Id: "+contactId+" Raw Contact Id: "+rawContactId);
	    return rawContactId;
	}
	
	public static String getNumberByContactId(Context context , long contactId){
	    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
	    String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?";
	    String[] selectionArgs = new String[]{String.valueOf(contactId)};
	    
	    String number = "";
	    Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection,selection,selectionArgs , null);    
	    if (c.moveToFirst()) {    
	    	number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	    }
	    return number;
	}
	
	
	private static Bitmap queryContactImage(Context context, int imageDataRow) {
	    Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[] {
	        ContactsContract.CommonDataKinds.Photo.PHOTO
	    }, ContactsContract.Data._ID + "=?", new String[] {
	        Integer.toString(imageDataRow)
	    }, null);
	    byte[] imageBytes = null;
	    if (c != null) {
	        if (c.moveToFirst()) {
	            imageBytes = c.getBlob(0);
	        }
	        c.close();
	    }
	    if (imageBytes != null) {
	        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); 
	    } else {
	        return null;
	    }
	}
	
}
