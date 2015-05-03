package com.example.cabpoolnew;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidhive.LazyAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DriverInfoPage extends Activity {
	
	EditText driverName, passangersName;
	Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_info_page);
		
		driverName = (EditText) findViewById(R.id.driverName);
		passangersName = (EditText) findViewById(R.id.passangersName);
		
		okButton = (Button) findViewById(R.id.okButton);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Driver");
		query.getInBackground(LazyAdapter.driverObjectId, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject response, ParseException arg1) {
				// TODO Auto-generated method stub
				driverName.setText(response.getString("owner"));
				String members = response.getString("members");
				String[] a = members.split(",");
				int last = a.length-1;
				String[] mem = Arrays.copyOfRange(a,1,last);
			
				passangersName.setText(Arrays.toString(mem));
			}
			
		});
		
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thankyouActivity(v);
			}
		});
		
	}
	
	 private void thankyouActivity(View v) {
		    Intent intent = new Intent(v.getContext(), ThankyouActivity.class);
		    v.getContext().startActivity(intent);
		  }
}
