package it.me.launcherlib.model;

public class Notification {

	public final static int CALL = 1;
	public final static int SMS = 2;
	
	private int notificationType;

	public String getNotificationType() {
		String type = "";
		switch (this.notificationType) {
		case CALL:
			type = "Chiamate";
			break;
		case SMS:
			type = "Messaggi";
			break;
		}
		return type;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}
	
	
	
}
