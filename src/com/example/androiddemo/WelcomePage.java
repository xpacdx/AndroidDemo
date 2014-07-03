package com.example.androiddemo;
import com.example.androiddemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomePage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomePage.this, LoginPage.class);
				startActivity(intent);
				WelcomePage.this.finish();
			}
		}, 4000);
	}
}
