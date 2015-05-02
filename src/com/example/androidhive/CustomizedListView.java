package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.cabpool.PassengerActivity;
import com.example.cabpoolnew.R;
import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends Activity {
	// All static variables
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	
	ListView list;
    LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		ArrayList<HashMap<String, String>> rideList = new ArrayList<HashMap<String, String>>();
		List<ParseObject> myList = PassengerActivity.dataList;
		
		for (int i = 0; i < myList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key => value
			map.put(KEY_ID, myList.get(i).getString(Util.CAR_TYPE));
			map.put(KEY_TITLE, myList.get(i).getString(Util.DESTINATION));
			map.put(KEY_ARTIST, myList.get(i).getString(Util.SEATS));
			map.put(KEY_DURATION, "10 hours");
			map.put("objectId", myList.get(i).getObjectId());
			//map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

			// adding HashList to ArrayList
			rideList.add(map);
		}


		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, rideList,getApplicationContext());        
        list.setAdapter(adapter);
        

//        // Click event for single list row
//        list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//							
//
//			}
//		});		
	}	
}