package frappe.kobe;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FRAPPEActivity extends Activity {
	public static final int LOGGED_IN = 1;
	public static final int LOGGED_OUT = 0;
	public static int USER_STATUS;
	
	final int P_UNLOCKED = -1;
	final int P_LOCKED = 1;
	public static ArrayList<Portal> PORTALS = new ArrayList<Portal>();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PORTALS.add(PORTALS.size(), new Portal("Door1", P_UNLOCKED));
        PORTALS.add(PORTALS.size(), new Portal("Door2", P_LOCKED));
        
        USER_STATUS = LOGGED_OUT;
        setContentView(R.layout.main);
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