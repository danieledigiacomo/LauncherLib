package it.me.launcherlib.profiles;

import java.io.File;

import com.squareup.picasso.Picasso;

import it.me.launcherlib.Font;
import it.me.launcherlib.Path;
import it.me.launcherlib.model.AvatarType;
import it.me.launcherlib.model.User;
import it.me.launcherlib.widget.CircularImageView;
import it.me.launcherlib.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemProfile {
	
	private Handler selectCallback;
	
	public ItemProfile(Activity activity,View itemView, User user,Handler selectCallback){
		this.setNickname(activity,itemView, user);
		this.setAvatar(activity,itemView, user);
		this.setClickListener(itemView, user);
		this.selectCallback = selectCallback;
	}
	
	
	//SET NICKNAME
	private void setNickname(Activity activity,View itemView, User user){
		final TextView textViewItemProfiles = (TextView) itemView.findViewById(R.id.textViewItemProfiles);
		textViewItemProfiles.setText(user.getNickname());
		textViewItemProfiles.setOnClickListener(onClickListener);
		textViewItemProfiles.setTag(user);
		
		Font.setFont(activity,textViewItemProfiles);
		
	}

	//SET AVATAR 
	private void setAvatar(Activity activity,View itemView, User user){
		CircularImageView imageViewProfiles = (CircularImageView) itemView.findViewById(R.id.imageViewItemProfiles);
		
		if(user.getAvatarType().equals(AvatarType.BUNDLE)){
			Bitmap bitmapFoto = BitmapFactory.decodeFile(Path.getAvatarPath(activity) + "avatar_"+user.getAvatar()+".png");
			imageViewProfiles.setImageBitmap(bitmapFoto);
		}else{
			/*Bitmap bitmapFoto = Util.decodeBitmapFromFile(activity,user.getAvatar(), 120, 80);			
			imageViewProfiles.setImageBitmap(bitmapFoto);*/
			Picasso.with(activity).load(new File(user.getAvatar())).into(imageViewProfiles);
		}
		imageViewProfiles.setOnClickListener(onClickListener);
		imageViewProfiles.setTag(user);
		
	}

	//SET CLICK LISTENER
	private void setClickListener(View itemView,User user){
		itemView.setOnClickListener(onClickListener);
		itemView.setTag(user);
	}
	
	//CLICK LISTENER
	private OnClickListener onClickListener = new OnClickListener() {
	     @Override
	     public void onClick(final View v) {
	    	 
	    	User user = (User) v.getTag();
	    	int id = user.getId();
	    	
	    	ProfilesNavBar.setSelected(id);
	    	selectCallback.sendEmptyMessage(id);
	    	
	    	
	    	//ActParentalSite.reloadContent(GridSiteAdapter.getCurrentMode());
	   }
	}; 

	
	

}
