package com.example.cabpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cabpoolnew.R;

public class ChooseActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_choose);
		Button driverButton = (Button) findViewById(R.id.button2);
		Button passengerButton =(Button)findViewById(R.id.button1);
		Button metroButton =(Button)findViewById(R.id.button3);
		Button nehruButton =(Button)findViewById(R.id.button4);
		Toast.makeText(ChooseActivity.this, "Welcome : " +MainActivity.firstName, Toast.LENGTH_LONG).show();
		
		nehruButton.setOnClickListener(new  OnClickListener() 
		{
			 @Override
	         public void onClick(View v) {
	             Intent intent = new Intent(ChooseActivity.this,NehruActivity.class);
	             startActivity(intent);
			 }
		});
		
		metroButton.setOnClickListener(new  OnClickListener() 
		{
			 @Override
	         public void onClick(View v) {
	             Intent intent = new Intent(ChooseActivity.this,MetroActivity.class);
	             startActivity(intent);
			 }
		});
		driverButton.setOnClickListener(new  OnClickListener() {
	    	 @Override
	         public void onClick(View v) {
	             Intent intent = new Intent(ChooseActivity.this,DriverActivity.class);
	             startActivity(intent);
	         }
	     });

		passengerButton.setOnClickListener(new  OnClickListener() {
	    	 @Override
	         public void onClick(View v) {
	             Intent intent = new Intent(ChooseActivity.this,PassengerActivity.class);
	             startActivity(intent);
	         }
	     });

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_choose,
					container, false);
			return rootView;
		}
	}

}
