package it.me.launcherlib.model;



import it.me.launcherlib.database.DBArticles;

import java.io.Serializable;

import android.content.Context;

public class TabletInfo implements Serializable {

	private String productCode;
	private int type;
	public String firmwareLang;
	
	public TabletInfo(String productCode) {
		this.productCode = productCode;
		this.type = this.getType(productCode);
		this.firmwareLang = this.getFirmwareLanguage(productCode);
	}
	
	public boolean isValidCode(Context context){
		DBArticles database = new DBArticles(context);
		Article article = database.getArticle(productCode);
		if(article!=null){
			return true;
		}else{
			return false;
		}
		
	}
	
	//GET TYPE
	public int getType(){					return this.type;				}
	//GET PRODUCT CODE
	public String getProductCode(){			return this.productCode;		}
	
	//GET TYPE BY PRODUCT CODE
	private int getType(String productCode){
		
		/**  splitto productCode **/
		if(productCode.equals(Code.SMART_GDO) || productCode.equals(Code.SMART_NTR) || 
		   productCode.equals(Code.SMART_POL) || productCode.equals(Code.SMART_FRA)){
																
																return Type.SMARTKIDS;
																
																	
		}else if(productCode.equals(Code.EVO_GDO) || productCode.equals(Code.EVO_NTR) ){			
			
																return Type.EVOLUTION;		
		
		}else if(productCode.equals(Code.FAMILY)){				return Type.FAMILY;		
		
		}else if(productCode.equals(Code.BARBIE)){				return Type.BARBIE;		}
		
		
		return type;
		
	}

	//GET FIRMWARE LANGUAGE
	private String getFirmwareLanguage(String productCode){
		
		/**  splitto productCode **/
		/*if(productCode.equals(Code.LSC_PRE_14_FR)){					return Language.FRENCH;		}
		else if(productCode.equals(Code.LSC_PRE_14_EN)){			return Language.ENGLISH;		}
		else if(productCode.equals(Code.LSC_PRE_14_PL)){			return Language.POLISH;		}
		
		else*/ return Language.ITALIAN;
	}
	
	
	/*************
    * class CODE *
    *************/
	public class Code{

		public static final String SMART_GDO = "51465";
		public static final String SMART_NTR = "51519";
		public static final String SMART_POL = "P45327";
		public static final String SMART_FRA = "F45167";
		
		public static final String EVO_GDO = "51526";
		public static final String EVO_NTR = "51533";
		
		public static final String FAMILY = "51540";
		public static final String BARBIE = "51557";
		
	}
	
	
	/**************
    * class TYPE *
    *************/
	public class Type{
		
		public static final int SMARTKIDS = 1;
		public static final int EVOLUTION = 2;
		public static final int BARBIE = 3;
		public static final int FAMILY = 4;
	}
	/*****************
    * class LANGUAGE *
    *****************/
	public static class Language{
		
		public static final String ITALIAN = "IT";
		public static final String FRENCH = "FR";
		public static final String POLISH = "PL";
		public static final String ENGLISH = "EN";
	
	}

}
