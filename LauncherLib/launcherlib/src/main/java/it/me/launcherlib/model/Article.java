package it.me.launcherlib.model;

public class Article {
	
	private int id;
	private String code;
	private String urType;
	private String title;
	private String description;
	
	/*****COSTRUTTORI*****/
	public Article(){}
	
	
	/*****METODI GET*****/
	public int getId(){					 	return this.id;					}
	public String getCode(){				return this.code;				}
	public String getUrType(){				return this.urType;				}
	public String getTitle(){				return this.title;				}
	public String getDescription(){			return this.description;		}
	
	
	/*****METODI SET*****/
	public void setId(int id){								this.id=id;								}
	public void setCode(String value){						this.code=value;						}
	public void setUrType(String value){					this.urType = value;					}
	public void setTitle(String value){						this.title = value;						}
	public void setDescription(String value){				this.description = value;				}
	
}



