package kobe.frappe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends Activity {
	int CAMERA_PIC_REQUEST;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_screen);
		Button loginButton = (Button)findViewById(R.id.login_button);
			
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), LogMeIn.class);
				startActivity(i);
			}
		});
	}
}