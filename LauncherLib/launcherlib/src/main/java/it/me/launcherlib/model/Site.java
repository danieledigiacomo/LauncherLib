package it.me.launcherlib.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a representation of a users video off YouTube
 * @author paul.blundell
 */
public class Site implements Serializable {

	private int id;
	private String title;
	private String url;
	private String thumbUrl;
	private int locked;
	
	public Site(String title, String url, String thumbUrl) {
		super();
		this.title = title;
		this.url = url;
		this.thumbUrl = thumbUrl;
		this.locked = 1;
	}

	/**************
	 * METODI GET *
	 **************/
	public int getId(){					return id;					}
	public String getTitle(){			return title;				}
	public String getUrl() {			return url;					}
	public String getThumbUrl() {		return thumbUrl;			}
	public boolean isLocked(){			return this.locked == 1;		}
	
	/**************
	 * METODI SET *
	 **************/
	public void setId(int value){								this.id = value;					}
	public void setTitle(String value){							this.title = value;					}
	public void setUrl(String value){							this.url = value;					}
	public void setThumbUrl(String value){						this.thumbUrl = value;				}
	public void setLocked(int value){							this.locked = value;				}
	
	
	public static ArrayList<String> hosts;
	
}


