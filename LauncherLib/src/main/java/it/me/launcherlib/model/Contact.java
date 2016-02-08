package it.me.launcherlib.model;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.net.Uri;


public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String BUNDLE_KEY = "contact";
	
	private String id;
	private String name;
	private String number;
	private Bitmap photo;
	private String email;
	private boolean enabled;
	private String photoPath;
	private Uri uri;
	
	public Contact(){}
	
	public Contact(String name, String number, Bitmap photo,String email) {
        this.name = name;
        this.number = number;
        this.photo = photo;
        this.email = email;
    }

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Contact))return false;
	    Contact otherMyClass = (Contact)other;
	    if(otherMyClass.getId() == this.getId()){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public Bitmap getPhoto() {
        return photo;
    }
    public String getEmail() {
        return email;
    }
    

    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
    public void setEmail(String email) {
        this.email = email;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}
   

}
