package com.example.androiddemo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WelcomeStarBuck extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.starbuck);
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	ActionBar actionBar = getActionBar();
    	actionBar.setDisplayShowHomeEnabled(false);
    	//displaying custom ActionBar
    	View mActionBarView = getLayoutInflater().inflate(R.layout.welcomeactionbar, null);
    	actionBar.setCustomView(mActionBarView);
    	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    	
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        return super.onOptionsItemSelected(item);
    }
    
    public void startbutton(View v) {
		Intent intent = new Intent(WelcomeStarBuck.this, MenuStarBuck.class);
		startActivity(intent);
		WelcomeStarBuck.this.finish();
	}
    
    public void toggleMenu(View v) {
		Intent intent = new Intent(WelcomeStarBuck.this, Person.class);
		startActivity(intent);
		WelcomeStarBuck.this.finish();
	}
}
