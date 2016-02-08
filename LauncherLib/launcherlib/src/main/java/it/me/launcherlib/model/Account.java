package it.me.launcherlib.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Account {

	
	private String username;
	private String password;
	private String apiKey;
	private String firstname;
	private String lastname;
	private TabletInfo tabletInfo;
	private String userLang;
	
	/***************
	 * COSTRUTTORI *
	 ***************/
	public Account(){}
	
	public Account(String username, String password){
		this.setUsername(username);
		this.setPassword(password);
	}

	public Account(JSONObject obj){
		try {
			this.setUsername(obj.getString("username"));
			this.setLastName(obj.getString("lastname"));
			this.setPassword(obj.getString("password"));
			this.setFirstname(obj.getString("firstname"));
			this.setAPIKey(obj.getString("api_key"));
			this.setTabletInfo(new TabletInfo(obj.getString("cd_article")));
			this.setUserLanguage(obj.getString("cd_locale"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	/**************
	 * METODI SET *
	 **************/
	public void setUsername(String value){							this.username = value;					}
	public void setPassword(String value){							this.password = value;					}
	public void setAPIKey(String value){							this.apiKey = value;					}
	public void setFirstname(String value){							this.firstname = value;					}
	public void setLastName(String value){							this.lastname = value;					}
	public void setTabletInfo(TabletInfo value){					this.tabletInfo = value;				}
	
	public void setTabletInfo(String productCode){					
		this.setTabletInfo(new TabletInfo(productCode));
	}
	
	
	public void setUserLanguage(String value){						this.userLang = value;					}
	
	/**************
	 * METODI GET *
	 **************/
	public String getUsername(){							return this.username;							}
	public String getPassword(){							return this.password;							}
	public String getAPIKey(){								return this.apiKey;								}
	public String getFirstname(){							return this.firstname;							}
	public String getLastname(){							return this.lastname;							}
	public TabletInfo getTabletInfo(){						return this.tabletInfo;							}
	public String getUserLanguage(){						return this.userLang;							}
	
	
	
	
	
	
}
