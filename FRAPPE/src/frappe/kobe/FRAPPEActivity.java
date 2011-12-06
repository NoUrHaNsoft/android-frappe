package frappe.kobe;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class FRAPPEActivity extends Activity {
	public static final int LOGGED_IN = 1;
	public static final int LOGGED_OUT = 0;
	public static int USER_STATUS;
	
	final int P_UNLOCKED = -1;
	final int P_LOCKED = 1;
	public static ArrayList<Portal> PORTALS = new ArrayList<Portal>();
	public static final File APP_DIR = new File(Environment.getExternalStorageDirectory(), ".FrappeFiles");;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        USER_STATUS = LOGGED_OUT;
        setContentView(R.layout.main);
        
        if(!APP_DIR.exists()){
        	APP_DIR.mkdir();
        }
        Log.e("DIR", APP_DIR.toString());
        
        Intent i = new Intent(this, SplashScreen.class);
        startActivity(i);
		finish();
    }

    @Override
    public void onResume(){
    	super.onResume();
    	
    	switch(USER_STATUS){
    	case LOGGED_IN:
    		Intent li = new Intent(this, PortalList.class);
    		startActivity(li);
    	case LOGGED_OUT:
    		Intent lo = new Intent(this, SplashScreen.class);
    		startActivity(lo);
    	}
    }
}