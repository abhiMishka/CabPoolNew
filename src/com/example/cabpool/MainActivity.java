package com.example.cabpool;


import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.cabpoolnew.R;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;


public class MainActivity extends Activity {
	private Dialog progressDialog;
	protected static final String TAG = "LoginActivity";
	public static String facebookId,firstName,lastName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		 
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
					permitAll().build(); StrictMode.setThreadPolicy(policy);
		}
		 // Check if there is a currently logged in user
	    // and it's linked to a Facebook account.
	    ParseUser currentUser = ParseUser.getCurrentUser();
	    if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
	      // Go to the user info activity
	      showUserDetailsActivity();
	    }
		
		setContentView(R.layout.activity_main);
				
		Button orderButton = (Button) findViewById(R.id.button1);
		orderButton.setOnClickListener(new  OnClickListener() {
	    	 @Override
	         public void onClick(View v) {
	    		 onLoginClick(v);
	             
	         }
	     });
		
				

	  }
	    
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	  }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	 public void onLoginClick(View v) {
		    progressDialog = ProgressDialog.show(MainActivity.this, "", "Logging in...", true);
		    
		    List<String> permissions = Arrays.asList("public_profile", "email");
		    // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
		    // (https://developers.facebook.com/docs/facebook-login/permissions/)
		    
		    ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
		      @Override
		      public void done(ParseUser user, ParseException err) {
		        progressDialog.dismiss();
		        if (user == null) {
		          Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
		        } else if (user.isNew()) {
		        	firstName = user.getUsername();
		          Log.d(TAG, "User signed up and logged in through Facebook!");
		          showUserDetailsActivity();
		        } else {
		        	firstName = user.getUsername();
		          Log.d(TAG, "User logged in through Facebook!");
		          makeMeRequest();
		        }
		      }
		    });
		  }

		  private void showUserDetailsActivity() {
		    Intent intent = new Intent(this, ChooseActivity.class);
		    startActivity(intent);
		  }
		  
		  private void makeMeRequest() {
			    Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
			      new Request.GraphUserCallback() {
			        @Override
			        public void onCompleted(GraphUser user, Response response) {
			          if (user != null) {
			            // Create a JSON object to hold the profile info
			            JSONObject userProfile = new JSONObject();
			            try {
			              // Populate the JSON object
			              userProfile.put("facebookId", user.getId());
			              facebookId = user.getId();
			              userProfile.put("name", user.getName());
			              firstName = user.getFirstName();
			              lastName = user.getLastName();
			              if (user.getProperty("gender") != null) {
			                userProfile.put("gender", user.getProperty("gender"));
			              }
			              if (user.getProperty("email") != null) {
			                userProfile.put("email", user.getProperty("email"));
			              }

			              // Save the user profile info in a user property
			              ParseUser currentUser = ParseUser.getCurrentUser();
			              currentUser.put("profile", userProfile);
			              currentUser.saveInBackground();

			              // Show the user info
			              showUserDetailsActivity();
			            } catch (JSONException e) {
			              Log.d(TAG, "Error parsing returned user data. " + e);
			            }

			          } else if (response.getError() != null) {
			            if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY) || 
			              (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
			              Log.d(TAG, "The facebook session was invalidated." + response.getError());
			              //logout();
			            } else {
			              Log.d(TAG, 
			                "Some other error: " + response.getError());
			            }
			          }
			        }
			      }
			    );
			    request.executeAsync();
			  }

}