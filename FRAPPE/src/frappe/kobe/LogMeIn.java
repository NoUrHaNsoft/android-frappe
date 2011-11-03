package frappe.kobe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.provider.*;
import android.provider.MediaStore.Images.Media;


public class LogMeIn extends Activity {

	private static final int CAMERA_REQUEST = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		//launches camera and stores picture taken in getTempFile
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)) ); 
		startActivityForResult(i, CAMERA_REQUEST);
	}
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 
		 if(requestCode == CAMERA_REQUEST){
			 final File file = getTempFile(this);
			 setContentView(R.layout.photo_view);
			 ImageView image = (ImageView) findViewById(R.id.photo_display);  
			 
		     try {
		    	 //get picture taken and display it. where compare will go
				Bitmap picBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
				image.setImageBitmap(picBmp);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		     FRAPPEActivity.USER_STATUS = FRAPPEActivity.LOGGED_IN;
		     //file.delete(); need to delete whole folder?
		     Intent pl = new Intent(getApplicationContext(), PortalList.class);
		     startActivity(pl);
		     finish();
		 }
	 }
	 

		private File getTempFile(Context context){
		  //it will return /sdcard/image.tmp
		  final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
		  if(!path.exists()){
		    path.mkdir();
		  }
		  return new File(path, "image.tmp");
		}
}