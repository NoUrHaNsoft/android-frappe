package frappe.kobe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.*;
import android.provider.MediaStore.Images.Media;


public class LogMeIn extends Activity {

	private static final int CAMERA_REQUEST1 = 1337;
	Bitmap pic2, pic1 = null;
	private final File imageFile1 = new File(FRAPPEActivity.APP_DIR, "image1.tmp");
	private final File imageFile2 = new File(FRAPPEActivity.APP_DIR,"image2.tmp");

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		//launches camera and stores picture taken
		Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile1));
		startActivityForResult(i1, CAMERA_REQUEST1);
		
	}
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 setContentView(R.layout.photo_view);
		 ImageView imagePane = (ImageView) findViewById(R.id.photo_display);  
		 
		 Intent i2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 i2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile2));
		 startActivity(i2);
		 
		 
		 if(requestCode == CAMERA_REQUEST1){
			 
		     try {
		    	 pic1 = Media.getBitmap(getContentResolver(), Uri.fromFile(imageFile1));
		    	 Log.e("LogMeIn pic1", Integer.toString(pic1.getWidth())+' '+Integer.toString(pic1.getHeight()));
		    	 pic2 = Media.getBitmap(getContentResolver(), Uri.fromFile(imageFile2));
		    	 Log.e("LogMeIn pic2", Integer.toString(pic2.getWidth())+' '+Integer.toString(pic2.getHeight()));
		    	 if (pic1 != null && pic2 != null) {
					//imagePane.setImageBitmap(ImageCompare.toGrayscale(pic1));
					if (ImageCompare.imageMatch(pic1, pic2)) {
						Log.e("LogMeIn", "These pictures match");
						Intent pl = new Intent(getBaseContext(), PortalList.class);
						startActivity(pl);
					} else
						Log.e("LogMeIn", "These pictures don't match");
					    Intent i = new Intent(getBaseContext(), SplashScreen.class);
					    startActivity(i);
				}
		    	 else
						Log.e("LogMeIn", "Null pictures");
		    		    	 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	 }
}
