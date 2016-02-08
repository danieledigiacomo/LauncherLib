package it.me.launcherlib.model;

public class Locale {

	private String label;
	private String code;
	
	public Locale(String code, String label){
		this.code = code;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
