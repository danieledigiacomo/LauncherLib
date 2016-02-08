package it.me.launcherlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.me.launcherlib.AppTool;
import it.me.launcherlib.Config;
import it.me.launcherlib.Font;
import it.me.launcherlib.R;
import it.me.launcherlib.Receivers;

/**
 * Created by daniele on 05/02/16.
 */



public class DialogDBNotFound extends Dialog {

    private Context context;
    private int width;

    public DialogDBNotFound(Context context){
        super(context, R.style.ThemeWithCorners);

        this.context = context;
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        this.width = -1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.dialog_text_and_pass);
        this.setCancelable(false);
        this.loadContent();

        this.setText("Ops.. Database non trovato. Inserisci la password genitore per scaricarlo.");

        /*if(this.width!=-1){
        	setWidth(600);
        }*/

    }

    public void setWidth(int width){
        LinearLayout content = (LinearLayout) this.findViewById(R.id.dlgOneButton);
        ViewGroup.LayoutParams params = content.getLayoutParams();
        params.width = width;
        content.setLayoutParams(params);
        content.invalidate();
    }

    private void loadContent(){
        TextView buttonOK = (TextView) this.findViewById(R.id.btnOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                //okHandler.sendMessage(new Message());
                if(AppTool.packageExists(context, Config.PACKAGE_CONFIGURATOR)){
                    Receivers.broadcast(context, Receivers.ACTION_DOWNLOAD_UDNT_SECURE);
                }
            }
        });

        TextView buttonKO = (TextView) this.findViewById(R.id.btnKO);
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
            Font.setFont(context, textView);
        }



    }


}
