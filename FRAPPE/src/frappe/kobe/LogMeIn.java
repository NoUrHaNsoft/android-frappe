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
import android.widget.ImageView;
import android.provider.*;
import android.provider.MediaStore.Images.Media;


public class LogMeIn extends Activity {

	private static final int CAMERA_REQUEST = 1337;
	private final File imageFile = new File(FRAPPEActivity.APP_DIR, "image.tmp");

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		//launches camera and stores picture taken in getTempFile
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile) ); 
		startActivityForResult(i, CAMERA_REQUEST);
	}
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 
		 if(requestCode == CAMERA_REQUEST){
			 setContentView(R.layout.photo_view);
			 ImageView imagePane = (ImageView) findViewById(R.id.photo_display);  
			 
		     try {
		    	 //get picture taken and display it. where compare will go
		    	
				Bitmap picBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(imageFile));
		    	Bitmap srcimage = Media.getBitmap(getContentResolver(), Uri.fromFile(new File(FRAPPEActivity.APP_DIR,"srcImage.jpg")));
				if(ImageCompare.imageMatch(picBmp, srcimage)){
					imagePane.setImageBitmap(srcimage);
				}
				else{
					imagePane.setImageBitmap(picBmp);
				}
				Intent pl = new Intent(getApplicationContext(), PortalList.class);
				startActivity(pl);		    	 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    // finish();
		 }
	 }
}
