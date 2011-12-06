package frappe.kobe;

import java.util.ArrayList;

import frappe.kobe.Portal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PortalAdapter extends ArrayAdapter<Portal> {
	private Context context;
    private int resource;
    private ArrayList<Portal> portals;

    public PortalAdapter(Context context, int resource, ArrayList<Portal> portals) {
        super(context, resource, portals);
        this.context = context;
        this.resource = resource;
        this.portals = portals;
    }

@Override
    public View getView(int position, View convertView, ViewGroup parent){
	
		Log.e("portalAdapter", portals.get(position).getLabel());
		Log.e("portalAdapter", portals.get(position).getStatus());
		Log.e("portalAdapter", Integer.toString(portals.get(position).mState));
		
        View view = convertView;
        if (view == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.portal_listview, null);
        }
        Portal portal = portals.get(position);
        view.setId(position);
        if (portal != null){
            TextView label = (TextView) view.findViewById(R.id.portalLabel);
            label.setText(portal.getLabel());
            //TextView status = (TextView) view.findViewById(R.id.portalStatus);
            //status.setText(portal.getStatus());
        }
        return view;
    }

}
