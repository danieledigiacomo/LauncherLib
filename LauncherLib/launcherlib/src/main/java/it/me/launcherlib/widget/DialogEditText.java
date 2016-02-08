package it.me.launcherlib.widget;

import it.me.launcherlib.Font;
import it.me.launcherlib.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogEditText extends Dialog{
    
	private Activity activity;
	private String text;
	private Handler okHandler;
	
	public DialogEditText(Activity activity,String text,  Handler okHandler){
		super(activity, R.style.ThemeWithCorners);
		
		this.activity = activity;
		activity.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		this.text = text;
		this.okHandler = okHandler;
	}
	
	public DialogEditText(Activity activity, Handler okHandler){
		super(activity, R.style.ThemeWithCorners);
		
		this.activity = activity;
		activity.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		this.text = "";
		this.okHandler = okHandler;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.dialog_edittext);
        this.setCancelable(false);
        this.loadContent();
        
    }

    public void setWidth(int width){
    	LinearLayout content = (LinearLayout) this.findViewById(R.id.dlgOneButton);
    	LayoutParams params = content.getLayoutParams();
    	params.width = width;
    	content.setLayoutParams(params);
    	content.invalidate();
    }
    
    private void loadContent(){
    	this.setText(text);
    	final EditText editText =  (EditText) this.findViewById(R.id.editText);
    	Button buttonOK = (Button) this.findViewById(R.id.btnOK);
    	buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				Message message = new Message();
				message.obj = editText.getEditableText().toString();
				okHandler.sendMessage(message);
			}
		});
    	Font.setFont(activity,buttonOK);
    	
    	Button buttonKO = (Button) this.findViewById(R.id.btnKO);
    	buttonKO.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
    	Font.setFont(activity,buttonKO);
    	
    }
    
    public void setText(String text){
    	EditText editText =  (EditText) this.findViewById(R.id.editText);
    	editText.setText(text);
    	
    }

	
}
