package com.meluo.androiddemo;

import com.example.androiddemo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Person extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	ActionBar actionBar = getActionBar();
    	actionBar.setDisplayShowHomeEnabled(false);
    	//displaying custom ActionBar
    	View mActionBarView = getLayoutInflater().inflate(R.layout.personactionbar, null);
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
    
    public void returnback(View v) {
		Intent intent = new Intent(Person.this, FirstPage.class);
		startActivity(intent);
		Person.this.finish();
	}
}
