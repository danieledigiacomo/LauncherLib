package it.me.launcherlib.model;

import android.util.Log;

public class StorageItem{
	
	public static final int PHOTO_TYPE = 0;
	public static final int FOLDER_TYPE = 1;
	public static final int AUDIO_TYPE = 2;
	public static final int VIDEO_TYPE = 3;

	public final static int UNSELECTED_MODE = 4;
	public final static int SELECTED_MODE = 5;
	
	public int type;
	private String path;
	private int photoNumber;
	private int pageNumber;
	
	private String code;
	private String galleryFolder;
	
	private int tag;
	
	public StorageItem(){}
	
	public StorageItem(int type, String path){
		this.type = type;
		this.path = path;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof StorageItem))return false;
	    StorageItem otherMyClass = (StorageItem)other;
	    return this.getPath().equals(otherMyClass.getPath());
	
	}
	
	/*****METODI SET*****/
	public void setType(int value){				this.type = value;			}
	public void setPhotoNumber(int value){		this.photoNumber = value;	}
	public void setPageNumber(int value){		this.pageNumber = value;	}

	public void setCode(String value){					this.code=value;					}
	public void setGalleryFolder(String value){			this.galleryFolder = value;			}
	public void setPath(String value){					this.path = value;					}
	public void setTag(int value){						this.tag = value;					}
	
	/*****METODI GET*****/
	public String getPath(){				return path;				}
	public int getPhotoNumber(){			return photoNumber;			}
	public int getPageNumber(){				return pageNumber;			}
	
	public String getCode(){						return this.code;						}
	public String getGalleryFolder(){				return this.galleryFolder;				}
	
	public int getTag(){							return this.tag;						}
	
	
	
	public boolean isFolder(){		return this.type == FOLDER_TYPE;	}
	
}
