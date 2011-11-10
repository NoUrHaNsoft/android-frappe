package frappe.kobe;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePortal extends Activity {
	private EditText mLabel;
	private EditText mCellNum;
	private EditText mKeyphrase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.e("CreatePortal", "onCreate");
		
		setContentView(R.layout.createportal_view);
		setTitle("Create new portal");
		mLabel = (EditText) findViewById(R.id.label);
		mCellNum = (EditText)findViewById(R.id.cellcode);
		mKeyphrase = (EditText)findViewById(R.id.keyphrase);
		Button confirmButton = (Button)findViewById(R.id.confirm);
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String label = mLabel.getText().toString();
				String cellNum = mCellNum.getText().toString();
				String keyphrase = mKeyphrase.getText().toString();
				
				if((mLabel.length() == 0)||(mCellNum.length() == 0)||(mKeyphrase.length() == 0)){
					Toast.makeText(getApplicationContext(), "Failed to create. Empty fields encountered.", Toast.LENGTH_SHORT);
				}
				else{
					FRAPPEActivity.PORTALS.add(new Portal(label, cellNum, keyphrase));
					Log.e("CreatePortal", "Success?");
				}
				
				finish();
			}
		});
	}
}
