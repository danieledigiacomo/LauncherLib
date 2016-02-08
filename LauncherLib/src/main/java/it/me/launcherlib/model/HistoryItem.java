package it.me.launcherlib.model;

public class HistoryItem {

	private int idUser;
	private String packageName;
	private String label;
	private long timestamp;
	
	public HistoryItem(){}
	
	public HistoryItem(int idUser, String packageName, String label, long time){
		this.idUser = idUser;
		this.packageName = packageName;
		this.label = label;
		this.timestamp = time;
	}
	
	public void setIdUser(int idUser){						this.idUser = idUser;			}
	public void setPackageName(String packageName){			this.packageName = packageName;	}
	public void setLabel(String label){						this.label = label;				}
	public void setTime(long time){							this.timestamp= time;			}
	
	public int getIdUser(){									return this.idUser;				}
	public String getPackageName(){							return this.packageName;		}
	public String getLabel(){								return this.label;				}
	public long getTime(){									return this.timestamp;			}
}
