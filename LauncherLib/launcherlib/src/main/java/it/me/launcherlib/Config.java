package it.me.launcherlib;

import android.content.Context;

import it.me.launcherlib.database.DBConfig;
import it.me.launcherlib.database.Database;

public class Config {

	public static final String PACKAGE_CAMERA = "it.udanet.US0431EU";
	public static final String MAIN_ACTIVITY_CAMERA = "it.udanet.US0431EU.ActVideoCamera";

	//TODO aggiornare nome package
	public static final String PACKAGE_CONFIGURATOR = "it.udanet.configuratore";

	public static final String DEFAULT_CD_LOCALE = "it";
	public static final String RECOVERY_KEY = "liscianigiochimiotab";
	
	public static boolean INITIALIZED = false;
	
	private String dataPath;
	private String imagePath;
	private String avatarsPath;
	private String backgroundsPath;
	private String sessionPath;
	private String flagsPath;
	private String dbApiPath;
	private String dbPath;
	private String installerPath;
	private String appFolderPath;
	private String appImagePath;
	private String appAudioPath;
	private String appVideoPath;
	
	private String sysPreinstallPath;
	private String sysPackageListPath;
	private String sysPackageXmlPath;
	
	private String signalKeyEventDone;
	private String cloudUrl;
	private String notifyStatusGeneric;
	private String notifyStatusApp;
	private String notifyStatusEco;
	private String notifyStatusPromos;
	
	

	public Config(){}
	
	/*****METODI SET*****/
	public void setDataPath(String value){							this.dataPath = value;						}
	public void setImagesPath(String value){						this.imagePath = value;						}
	public void setAvatarsPath(String value){						this.avatarsPath = value;					}
	public void setBackgroundsPath(String value){					this.backgroundsPath = value;				}
	
	public void setSessionPath(String value){						this.sessionPath = value;					}
	public void setFlagsPath(String value){							this.flagsPath = value;						}
	public void setDBApiPath(String value){							this.dbApiPath = value;						}
	public void setDBPath(String value){							this.dbPath = value;						}
	
	public void setInstallerPath(String value){						this.installerPath = value;					}
	public void setAppFolderPath(String value){						this.appFolderPath = value;					}
	public void setAppImagesPath(String value){						this.appImagePath = value;					}
	public void setAppAudioPath(String value){						this.appAudioPath = value;					}
	public void setAppVideoPath(String value){						this.appVideoPath = value;					}

	public void setSysPreinstallPath(String value){					this.sysPreinstallPath= value;				}
	public void setSysPackageListPath(String value){				this.sysPackageListPath= value;				}
	public void setSysPackageXmlPath(String value){					this.sysPackageXmlPath= value;				}

	
	public void setKeyEventDone(String value){						this.signalKeyEventDone = value; 			}
	public void setCloudUrl(String value){							this.cloudUrl = value;						}	

	public void setNotifyStatusGeneric(String value){				this.notifyStatusGeneric = value;			}
	public void setNotifyStatusApp(String value){					this.notifyStatusApp = value;				}
	public void setNotifyStatusEco(String value){					this.notifyStatusEco = value;				}
	public void setNotifyStatusPromos(String value){				this.notifyStatusPromos = value;			}


	/*****METODI GET*****/
	public String getDataPath(){						return this.dataPath;									}	
	public String getImagesPath(){						return this.imagePath;									}
	public String getAvatarsPath(){						return this.avatarsPath;								}
	public String getBackgroundsPath(){					return this.backgroundsPath;							}
	
	public String getSessionPath(){						return this.sessionPath;								}
	public String getFlagsPath(){						return this.flagsPath;									}
	public String getDBApiPath(){						return this.dbApiPath;									}
	public String getDBPath(){							return this.dbPath;										}
	
	public String getInstallePath(){					return this.installerPath;								}
	public String getAppFolderPath(){					return this.appFolderPath;								}
	public String getAppImagesPath(){					return this.appImagePath;								}
	public String getAppAudioPath(){					return this.appAudioPath;								}
	public String getAppVideoPath(){					return this.appVideoPath;								}
	
	public String getSysPreinstallPath(){				return this.sysPreinstallPath;							}
	public String getSysPackageListPath(){				return this.sysPackageListPath;							}
	public String getSysPackageXmlPath(){				return this.sysPackageXmlPath;							}
	public String getCloudUrl(){						return this.cloudUrl;									
	}

	
	public int getKeyEventDone(){						return Integer.parseInt(this.signalKeyEventDone);		}
	
	
	public String getNotifyStatusGeneric(){				return this.notifyStatusGeneric;						}
	public String getNotifyStatusApp(){					return this.notifyStatusApp;							}
	public String getNotifyStatusEco(){					return this.notifyStatusEco;							}
	public String getNotifyStatusPromos(){				return this.notifyStatusPromos;							}
	
	public boolean wantsPromosNotification(){			return this.notifyStatusPromos.equals("1");				}
	public boolean wantsEcosNotification(){				return this.notifyStatusEco.equals("1");				}
	public boolean wantsAppsNotification(){				return this.notifyStatusApp.equals("1");				}
	
	public static Config create(Context context){
		DBConfig database = new DBConfig(context);
		Config config = database.getConfig();
		return config;
	}
	
	public static String getCloudUrl(Context context){						
		DBConfig db = new DBConfig(context);
		String url  = db.getValue(Database.KEY_CLOUD_URL);
		return url;									
	}
	
	
	
}
