package com.example.cabpool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cabpoolnew.R;

public class MetroActivity extends ActionBarActivity implements OnItemSelectedListener {
	private Spinner spinner;
	private static final String[]type = {"Car", "Bike"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metro);
		
		spinner = (Spinner)findViewById(R.id.spinner);
	     ArrayAdapter<String>adapter = new ArrayAdapter<String>(MetroActivity.this,
	             android.R.layout.simple_spinner_item,type);
	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     spinner.setAdapter(adapter);
	     spinner.setOnItemSelectedListener(this);
	     
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.metro, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_metro,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 switch (position) {
         case 0:
             // Whatever you want to happen when the first item gets selected
             break;
         case 1:
             // Whatever you want to happen when the second item gets selected
             break;
         case 2:
             // Whatever you want to happen when the thrid item gets selected
             break;

     }
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
