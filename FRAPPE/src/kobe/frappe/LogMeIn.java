package kobe.frappe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;


public class LogMeIn extends Activity {

	private static final int CAMERA_REQUEST = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		//Intent ci = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivityForResult(ci, CAMERA_REQUEST);
		Intent i = new Intent(this, CameraView.class);
		startActivityForResult(i, CAMERA_REQUEST);
	}
	
	 /*@Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 if(requestCode == CAMERA_REQUEST){
			 //do stuff with pic
			 Bitmap pic = (Bitmap)data.getExtras().get("data");
			 setContentView(R.layout.photo_view);
			 ImageView image = (ImageView) findViewById(R.id.photo_display);  
			 image.setImageBitmap(pic);
			 
		 }
	 }*/
}
