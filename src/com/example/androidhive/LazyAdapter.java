package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabpool.ChooseActivity;
import com.example.cabpool.MainActivity;
import com.example.cabpoolnew.R;
import com.example.cabpoolnew.ThankyouActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public Context mContext;
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d,Context c) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
        mContext = c;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        final TextView title = (TextView)vi.findViewById(R.id.title); // title
        final TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
       // TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        final ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        title.setText(song.get(CustomizedListView.KEY_TITLE));
        artist.setText("Available seats : " +song.get(CustomizedListView.KEY_ARTIST));
     //   duration.setText(song.get(CustomizedListView.KEY_DURATION));
        
        thumb_image.setBackgroundResource(R.drawable.tick);
        
        final ProgressDialog pd = new ProgressDialog(mContext);
        
        thumb_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				
				new AsyncTask<Void, Void, Void>()
				{
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						 thumb_image.setBackgroundResource(R.drawable.cross2);
						// artist.setText("Available seats : " +(seatsAvailable-1));
					}
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Driver");
//						pd.setMessage("Joining");
//						pd.show();
						 
						// Retrieve the object by id
						query.getInBackground(data.get(position).get("objectId"), new GetCallback<ParseObject>() {
							
						  public void done(ParseObject parseData, ParseException e) {
							  
							  Log.d("objectId",parseData.getObjectId());
							  final int seatsAvailable = Integer.parseInt(parseData.getString(Util.SEATS));
						    if (e == null && seatsAvailable>0) {
						     
						      
						      String currentMembers=parseData.getString("members");
						      parseData.put(Util.SEATS, Integer.toString(seatsAvailable-1));
						      parseData.put("members",currentMembers +"," +MainActivity.firstName);
						      parseData.saveInBackground();
						      
						      
						      new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
//									ParseObject Driver = new ParseObject("Driver");	
//									Driver.put("Members", MainActivity.firstName);
//									Driver.saveInBackground();
									 artist.setText("Available seats : " +(seatsAvailable-1));
									 thankyouActivity(v);
									// pd.dismiss();
								}
							}).run();
						      
						    }
						    else
						    {
						    	Toast.makeText(v.getContext(), "No more seats available!", Toast.LENGTH_LONG).show();
						    }
						  }
						});
						return null;
					}
					
				}.execute();
				
				
			}
		});
        
        return vi;
    }
    
    private void thankyouActivity(View v) {
	    Intent intent = new Intent(v.getContext(), ThankyouActivity.class);
	    v.getContext().startActivity(intent);
	  }
}