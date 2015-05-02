package com.example.cabpool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidhive.CustomizedListView;
import com.example.cabpoolnew.R;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PassengerActivity extends ActionBarActivity implements OnItemSelectedListener {
	AutoCompleteTextView destination;
	ParseObject Passenger;
	String driveDestination,driveTimeInMin,driveTimeInSec,vehicle,seats;
	String name="neha";
	Button submit;
	Context ctx=this;
      int hour,min;
      public static String LOG_TAG = "PassengerActivity";
	private TimePicker timePicker1;
	   private TextView time;
	   private Calendar calendar;
	   private String format = "";
	   public static List<ParseObject> dataList;
	  private Spinner spinner,spinner2;
	  private static final String[]pathsIfBike = {"1"};
	  private static final String[]pathsIfCar = {"1", "2", "3","4","5"};
	  private static final String[]transport = {"Car", "Bike"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passenger);
		// Parse object for Passenger
		Parse.initialize(this, "FGN5I5LFZGNlxvb6dBZm2h7DSDkElHjXXYULR0ZT", "2Z060lnTv3eUwZ0TVeTDamLamsKwzr8v2Y11zikU");
		Passenger = new ParseObject("Passenger");
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		 timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		 timePicker1.setIs24HourView(true);
	      time = (TextView) findViewById(R.id.textView1);
	      calendar = Calendar.getInstance();
	 	 hour = calendar.get(Calendar.HOUR_OF_DAY);
	     min = calendar.get(Calendar.MINUTE);

	    
		destination= (AutoCompleteTextView) findViewById(R.id.editText1);
		destination.setAdapter(new PlaceHolder(PassengerActivity.this,R.layout.list_item));
		//time=(EditText) findViewById(R.id.editText2);		
		submit=(Button) findViewById(R.id.button1);

		 spinner = (Spinner)findViewById(R.id.spinner2);
	     ArrayAdapter<String>adapter = new ArrayAdapter<String>(PassengerActivity.this,
	             android.R.layout.simple_spinner_item,transport);
	     
	     spinner2 = (Spinner)findViewById(R.id.spinner);
	     ArrayAdapter<String>adapterNew = new ArrayAdapter<String>(PassengerActivity.this,
	             android.R.layout.simple_spinner_item,pathsIfCar);

	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     adapterNew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     
	     spinner.setAdapter(adapter);
	     spinner.setOnItemSelectedListener(this);
	     
	     spinner2.setAdapter(adapterNew);
	     spinner2.setOnItemSelectedListener(this);
	     
	 	
	     submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				final ProgressDialog pd = new ProgressDialog(PassengerActivity.this);
				pd.setMessage("Getting Available Rides");
				pd.show();
				driveDestination=destination.getText().toString();
				
				vehicle=spinner.getSelectedItem().toString();
				seats=spinner2.getSelectedItem().toString();	
				hour=timePicker1.getCurrentHour();
				min=timePicker1.getCurrentMinute();
				int totalTime = hour*60 + min;
					// Do something with the time chosen by the user
					  Toast.makeText(getApplicationContext(), " time :" + Integer.toString(hour) + Integer.toString(min), Toast.LENGTH_SHORT).show();
				
					  ParseQuery<ParseObject> query = ParseQuery.getQuery("Driver");
						query.whereEqualTo("Destination", driveDestination);
						query.whereEqualTo("seats", seats);
						query.whereEqualTo("vehicle", vehicle);
						query.whereLessThanOrEqualTo("hour", Integer.toString(hour+1));
						query.whereGreaterThanOrEqualTo("hour", Integer.toString(hour-1));
						query.findInBackground(new FindCallback<ParseObject>() {
						    public void done(List<ParseObject> scoreList, ParseException e) {
						        if (e == null) {
						        	pd.dismiss();
						        	dataList = scoreList;
						        	Intent myIntent = new Intent(PassengerActivity.this, CustomizedListView.class);
						        	myIntent.putExtra("key", "main"); //Optional parameters
						        	PassengerActivity.this.startActivity(myIntent);
						             } else {
						            Log.d("score", "Error: " + e.getMessage());
						        }
						    }
						});

				}
			});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passenger, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_passenger,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		if(parent.getItemAtPosition(position).toString().equals("Bike"))
		{
			ArrayAdapter<String>adapterIfBike = new ArrayAdapter<String>(PassengerActivity.this,
		             android.R.layout.simple_spinner_item,pathsIfBike);
			spinner2.setAdapter(adapterIfBike);
			 spinner2.setOnItemSelectedListener(this);
		}
		else if(parent.getItemAtPosition(position).toString().equals("Car"))
		{
			ArrayAdapter<String>adapterNew = new ArrayAdapter<String>(PassengerActivity.this,
		             android.R.layout.simple_spinner_item,pathsIfCar);  
			spinner2.setAdapter(adapterNew);
			 spinner2.setOnItemSelectedListener(this);
		}
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
	
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	static final String API_KEY = "AIzaSyC7HhGk_SoeejaqPWnDkEiuQxJRoYIr1WI";

	  static  LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static  LatLng KIEL = new LatLng(53.551, 9.993);
	  static  LatLng location;
	  double lat,longi;
	  
	private ArrayList<String> autocomplete(String input) {
	    ArrayList<String> resultList = null;

	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?key=" + API_KEY);
	        sb.append("&components=country:uk");
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));

	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());

	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return resultList;
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }

	    return resultList;
	}
  
  private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	    private ArrayList<String> resultList;

	    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	    }

	    @Override
	    public int getCount() {
	        return resultList.size();
	    }

	    @Override
	    public String getItem(int index) {
	        return resultList.get(index);
	    }

	    @Override
	    public Filter getFilter() {
	        Filter filter = new Filter() {
	            @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                FilterResults filterResults = new FilterResults();
	                if (constraint != null) {
	                    // Retrieve the autocomplete results.
	                    resultList = autocomplete(constraint.toString());

	                    // Assign the data to the FilterResults
	                    filterResults.values = resultList;
	                    filterResults.count = resultList.size();
	                }
	                return filterResults;
	            }

	            @Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	                if (results != null && results.count > 0) {
	                    notifyDataSetChanged();
	                }
	                else {
	                    notifyDataSetInvalidated();
	                }
	            }};
	        return filter;
	    }
	}

public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	// TODO Auto-generated method stub
	  String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	
}

public void onClick(View v) {
	// TODO Auto-generated method stub
	 Geocoder geocoder = new Geocoder(this, Locale.getDefault());
	 
	 
	 List<Address> addresses = null;
	try {
		addresses = geocoder.getFromLocationName(destination.getText().toString(),5);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	lat = addresses.get(0).getLatitude();
	longi = addresses.get(0).getLongitude();
	
	location = new LatLng(lat, longi);
	
	

	
}

public static void getAddressFromLocation(final double latitude, final double longitude,
		final Context context, final Handler handler) {
	Thread thread = new Thread() {
		@Override
		public void run() {
			Geocoder geocoder = new Geocoder(context, Locale.getDefault());
			String result = null;
			try {
				List<Address> addressList = geocoder.getFromLocation(
						latitude, longitude, 1);
				if (addressList != null && addressList.size() > 0) {
					Address address = addressList.get(0);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
						sb.append(address.getAddressLine(i)).append("\n");
					}
					sb.append(address.getLocality()).append("\n");
					sb.append(address.getPostalCode()).append("\n");
					sb.append(address.getCountryName());
					result = sb.toString();
				}
			} catch (IOException e) {
				Log.e("error", "Unable connect to Geocoder", e);
			} finally {
				Message message = Message.obtain();
				message.setTarget(handler);
				if (result != null) {
					message.what = 1;
					Bundle bundle = new Bundle();
					result = "Latitude: " + latitude + " Longitude: " + longitude +
							"\n\nAddress:\n" + result;
					bundle.putString("address", result);
					message.setData(bundle);
				} else {
					message.what = 1;
					Bundle bundle = new Bundle();
					result = "Latitude: " + latitude + " Longitude: " + longitude +
							"\n Unable to get address for this lat-long.";
					bundle.putString("address", result);
					message.setData(bundle);
				}
				message.sendToTarget();
			}
		}
	};
	thread.start();
}

}
