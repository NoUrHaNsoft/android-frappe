package frappe.kobe;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;


public class PortalList extends ListActivity {
	private PortalAdapter pa;
    private static final int MENU_LOGOUT = Menu.FIRST;
    private static final int MENU_ADDPORTAL= Menu.FIRST + 1;
    private static final int ACTIVITY_CREATEPORTAL = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  pa = new PortalAdapter(this, R.layout.portal_listview, FRAPPEActivity.PORTALS);
		  setListAdapter(pa);

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  
		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	FRAPPEActivity.PORTALS.get(position).toggleState();
		    	String cheese = FRAPPEActivity.PORTALS.get(position).getCellNumber();
		    	String passphrase = FRAPPEActivity.PORTALS.get(position).getKey();
		        SmsManager sm = SmsManager.getDefault();
		        sm.sendTextMessage(cheese, null, passphrase, null, null);
		    	
		    	pa.notifyDataSetChanged();
		    }});
		  
		  
/*		  lv.setLongClickable(true);
		  lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
				registerForContextMenu(view);
				return false;
			}});*/
	}
	
/*	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		
	}*/
		
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
        	Log.e("PortalList", "return from new portal");
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
}
