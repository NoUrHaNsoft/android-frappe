package kobe.frappe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_screen);
		Button unlockButton = (Button)findViewById(R.id.status_button);
			
		unlockButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(this, CameraView.class);
				Intent i = new Intent(v.getContext(), CameraView.class);
				startActivity(i);
			}
		});
	}
}
	  

	 /*@Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  switch(requestCode) {
	   case (GET_PHOTO) : 
	    if(resultCode == RESULT_OK) {
	    // go on to compare images
	    }
	    break;
	   
	  }
	 }*/