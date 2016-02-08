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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogConfirm extends Dialog{
    
	private Activity activity;
	private String text;
	private int width;
	private Handler okHandler;
	
	public DialogConfirm(Activity activity,String text){
		super(activity, R.style.ThemeWithCorners);
		
		this.activity = activity;
		activity.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		this.text = text;
		this.width = -1;
	}
	
	public DialogConfirm(Activity activity, Handler okHandler){
		super(activity, R.style.ThemeWithCorners);
		
		this.activity = activity;
		activity.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		this.text = "";
		this.okHandler = okHandler;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.dialog_two_button);
        this.setCancelable(false);
        this.loadContent();
        
        this.setText(text);
        
        /*if(this.width!=-1){
        	setWidth(600);	
        }*/
        
    }

    public void setWidth(int width){
    	LinearLayout content = (LinearLayout) this.findViewById(R.id.dlgOneButton);
    	LayoutParams params = content.getLayoutParams();
    	params.width = width;
    	content.setLayoutParams(params);
    	content.invalidate();
    }
    
    private void loadContent(){
    	ImageView buttonOK = (ImageView) this.findViewById(R.id.btnOK);
    	buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				okHandler.sendMessage(new Message());
			}
		});
    	
    	ImageView buttonKO = (ImageView) this.findViewById(R.id.btnKO);
    	buttonKO.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
    	
    }
    
    public void setText(String text){
    	if(text.equals("")){
        	TextView textView =  (TextView) this.findViewById(R.id.dialogText);
        	textView.setVisibility(View.GONE);
        }else{
        	TextView textView =  (TextView) this.findViewById(R.id.dialogText);
        	textView.setVisibility(View.VISIBLE);
        	textView.setText(text);
        	Font.setFont(activity,textView);
        }
    	
    	
    	
    }

	
}
