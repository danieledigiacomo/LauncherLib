package it.me.launcherlib.model;

import it.me.launcherlib.database.DBItem;
import android.content.Context;

public class Item {
	
	public static final String CHILD_ROOT_CODE = "UF0001EU";
	public static final String PARENT_ROOT_CODE = "UF0002EU";
	
	private String parentCode;
	private String label;
	private String code;
	private String apk;
	private String package_;
	private boolean isMethod;
	private int installed;
	private int locked;
	private String type;
	private int position;
	
	private String game_id;
	private int game_score;
	private int selected;
	private boolean isSmsApp;
	private boolean isCallApp;
	
	/*****COSTRUTTORI*****/
	public Item(){}
	
	public Item(String parentCode,String label,String status,String code,String apk,String package_){
		this.setParentCode(parentCode);
		this.setLabel(label);
		this.setCode(code);
		this.setApk(apk);
		this.setPackage(package_);
	}
	
	/*****METODI GET*****/
	public String getParentCode(){			return this.parentCode;			}
	public String getLabel(){				return this.label;				}
	//public String getStatus(){			return this.status;				}
	public String getCode(){				return this.code;				}
	public String getApk(){					return this.apk;				}
	public String getPackage(){				return this.package_;			}
	public boolean isMethod(){ 				return this.isMethod;}
	
	public String getGameId(){				return this.game_id;			}
	public int getGameScore(){				return this.game_score;			}
	
	/*****METODI SET*****/
	public void setParentCode(String parentCode){			this.parentCode = parentCode;				}
	public void setLabel(String label){						this.label=label;						}
	public void setInstalled(int installed){				this.installed=installed;				}
	public void setLocked(int locked){						this.locked=locked;						}
	
	public void setCode(String code){						this.code=code;							}
	public void setApk(String apk){							this.apk = apk;							}
	public void setPackage(String package_){				this.package_=package_;					}
	public void setIsMethod(int isMethod){
		this.isMethod = (isMethod==1);
	}
	
	//ROOT ITEMS
	public static Item childRootItem(){
		Item root = new Item();
		root.setCode(Item.CHILD_ROOT_CODE);
		root.setLabel("HOME");
		return root;
	}
	public static Item parentRootItem(){
		Item root = new Item();
		root.setCode(Item.PARENT_ROOT_CODE);
		root.setLabel("AREA GENITORI");
		return root;
	}
	
	public void setGameId(String game_id){					this.game_id = game_id;					}
	public void setGameScore(int game_score){				this.game_score = game_score;			}
	
	

	public static Item getItem(Context context,String code){
		DBItem db = new DBItem(context);
		Item item = db.getItem(code);
		return item;
	}
	
	
	public boolean isFolder(){
		return this.getApk()==null;
	}
	
	public boolean isGame(){
		return this.game_id != null ;
	}
	
	public boolean isCloudGame(){
		boolean isGame = false;
		if(this.getType()!=null && this.type.equals("C")){
			isGame = true;
		}
		return isGame;
	}
	
	public boolean isApp(){
		return !this.isFolder() && !this.isGame();
	}
	
	
	public boolean isLocked(){
		if(this.locked == 1) return true;
		else return false;
	}
	
	public boolean isInstalled(){
		if(this.installed == 1) return true;
		else return false;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSelected() {
		return selected==1;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isSmsApp() {
		return isSmsApp;
	}

	public void setSmsApp(boolean isSmsApp) {
		this.isSmsApp = isSmsApp;
	}

	public boolean isCallApp() {
		return isCallApp;
	}

	public void setCallApp(boolean isCallApp) {
		this.isCallApp = isCallApp;
	}
}



