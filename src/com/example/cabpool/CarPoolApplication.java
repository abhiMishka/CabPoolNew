package com.example.cabpool;

import android.app.Application;

import com.example.cabpoolnew.R;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class CarPoolApplication extends Application {
	
	private static final String PARSE_APP_ID = "Oy4fzGlxqCdaeDNjHLeWFzy80paaCxU1QNs5Gugt";
	private static final String PARSE_CLIENT_KEY = "yLoJ8EUeT8imLRSlDTDvkzrKzBiiiSmqp3AAlmPc";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "Vcr6ota8RgV2ONy2jLjbGH6UMhCYfRa86daqg2iL",
				"RIaC45hl6y3VkqIrvqr9j9J7QKZbekOTnEGwoIes");

		ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
		
	}

}
