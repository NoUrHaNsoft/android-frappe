package frappe.kobe;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;


public class PortalList extends ListActivity {
	private PortalAdapter pa;
    private static final int MENU_LOGOUT = Menu.FIRST;
    private static final int MENU_ADDPORTAL= Menu.FIRST + 1;
    
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
		    	//String cheese = FRAPPEActivity.PORTALS.get(position).getPhoneNumber();
		    	//startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"+ cheese)));
		    	pa.notifyDataSetChanged();
		    }});
		  
		  
		  // lv.setLongClickable(true);
/*		  lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}});*/
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
//        	Intent i = new Intent(getApplicationContext(), SplashScreen.class);
//        	startActivity(i);
        	finish();
        	break;
        case MENU_ADDPORTAL:
        	int x = 0;
        	break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
