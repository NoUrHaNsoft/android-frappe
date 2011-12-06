package frappe.kobe;


import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class PortalList extends ListActivity {
	private PortalAdapter pa;
    private static final int MENU_LOGOUT = Menu.FIRST;
    private static final int MENU_ADDPORTAL= Menu.FIRST + 1;
    private static final int CONTEXT_MENU_TOGGLE = Menu.FIRST;
    private static final int CONTEXT_MENU_DELPORT = Menu.FIRST + 1;
    private static final int ACTIVITY_CREATEPORTAL = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
	    	FRAPPEActivity.PORTALS.clear();
	        FRAPPEActivity.PORTALS.add(0, new Portal("TestBoard", "3479832690", "x,1,Testing"));
	        
		  pa = new PortalAdapter(this, R.layout.portal_listview, FRAPPEActivity.PORTALS);
		  setListAdapter(pa);
		  //pa.notifyDataSetChanged();
		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  
/*		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		    	String cheese = FRAPPEActivity.PORTALS.get(position).getCellNumber();
		    	String passphrase = FRAPPEActivity.PORTALS.get(position).getKey();
		        SmsManager sm = SmsManager.getDefault();
		        sm.sendTextMessage(cheese, null, passphrase, null, null);
		        FRAPPEActivity.PORTALS.get(position).toggleState();
		    	
		    	
		    	pa.notifyDataSetChanged();
		    }});*/
		  
		  
		  registerForContextMenu(lv);
		  lv.setLongClickable(true);

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		menu.add(0, CONTEXT_MENU_TOGGLE, 0, "Unlock");	
		menu.add(0, CONTEXT_MENU_DELPORT, 0, R.string.cmenu_delport);
		Log.e("polist", FRAPPEActivity.PORTALS.get(info.position).getStatus());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		  switch (item.getItemId()) {
		  case CONTEXT_MENU_TOGGLE:
			  String delivered = "Door unlocked";
			  PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(delivered), 0);
			  
		        registerReceiver(new BroadcastReceiver(){
		            @Override
		            public void onReceive(Context arg0, Intent arg1) {
		                switch (getResultCode())
		                {
		                    case Activity.RESULT_OK:
		                        Toast.makeText(getBaseContext(), "SMS delivered", 
		                                Toast.LENGTH_SHORT).show();
		                        break;
		                    case Activity.RESULT_CANCELED:
		                        Toast.makeText(getBaseContext(), "SMS not delivered", 
		                                Toast.LENGTH_SHORT).show();
		                        break;                        
		                }
		            }},new IntentFilter(delivered));  
			  
			  String cheese = FRAPPEActivity.PORTALS.get(info.position).getCellNumber();
			  String passphrase = FRAPPEActivity.PORTALS.get(info.position).getKey();
			  SmsManager sm = SmsManager.getDefault();
			  sm.sendTextMessage(cheese, null, passphrase, null, deliveredPI);

			  
			  Intent pl = new Intent(getApplicationContext(), PortalList.class);
			  startActivity(pl);
			  
			  return true;
		  case CONTEXT_MENU_DELPORT:
			  FRAPPEActivity.PORTALS.remove(info.position);
			  pa.notifyDataSetChanged();
			  return true;
		  default:
			  return super.onContextItemSelected(item);
		  }
	}
		
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_LOGOUT,0, R.string.menu_logout);
        menu.add(0,MENU_ADDPORTAL,0, R.string.menu_addnew);        
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case MENU_LOGOUT:
        	FRAPPEActivity.USER_STATUS = FRAPPEActivity.LOGGED_OUT;
        	finish();
        	break;
        case MENU_ADDPORTAL:
        	Intent i = new Intent(getApplicationContext(), CreatePortal.class);
        	startActivityForResult(i, ACTIVITY_CREATEPORTAL);
        	break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	switch(requestCode){
    	case ACTIVITY_CREATEPORTAL:
    		pa.notifyDataSetChanged();
    		break;
    	}
    }
    
/*    private boolean readPortals(){
    	File pfile = new File(FRAPPEActivity.APP_DIR, orange.getCellNumber()+".txt");
    	
    	
    	return true;
    }*/
}
