package frappe.kobe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

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
				
				if((label.length() == 0)
					||(cellNum.length() == 0)
					||(keyphrase.length() == 0)){
					Toast.makeText(getBaseContext(), "Failed to create. Empty fields encountered.", Toast.LENGTH_LONG);
				}
				else{
					Portal orange = new Portal(label, cellNum, keyphrase);
					FRAPPEActivity.PORTALS.add(orange);
//					writePortal(orange);
					
					
					Log.e("CreatePortal", "Success?");
				}
				
				finish();
			}
		});
	}
	
/*	private boolean writePortal(Portal orange){
		File pfile = new File(FRAPPEActivity.APP_DIR+"portals/", orange.getCellNumber()+".txt");
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(pfile));
			pw.write(orange.getLabel());
			pw.write(orange.getCellNumber());
			pw.write(orange.getKey());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}*/
}
