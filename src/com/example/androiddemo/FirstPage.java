package com.example.androiddemo;

import com.example.androiddemo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.os.RemoteException;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.utils.L;

import static com.estimote.sdk.BeaconManager.MonitoringListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FirstPage extends Activity {

	private static final String TAG = WelcomeStarBuck.class.getSimpleName();

	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON = "extrasBeacon";
    private static final int NOTIFICATION_ID = 123;
	  
	private static final int REQUEST_ENABLE_BT = 1234;
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
	  
	private BeaconManager beaconManager;
    private NotificationManager notificationManager;    
	private int counter=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
	    getActionBar().setDisplayHomeAsUpEnabled(true);		


	    
	    // Configure verbose debug logging.
	    L.enableDebugLogging(true);
		
	    // Configure BeaconManager.
	    beaconManager = new BeaconManager(this);
	    

	    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	    // Default values are 5s of scanning and 25s of waiting time to save CPU cycles.
	    // In order for this demo to be more responsive and immediate we lower down those values.
	    beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);
	    
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	        @Override
	        public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
	          // Note that results are not delivered on UI thread.
	          runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	              // Note that beacons reported here are already sorted by estimated
	              // distance between device and beacon.
	              getActionBar().setSubtitle("Found beacons: " + beacons.size());
	            }
	          });
	        }
	      });
	    beaconManager.setMonitoringListener(new MonitoringListener() {
		      @Override
		      public void onEnteredRegion(Region region, List<Beacon> beacons) {
		    	  postNotificationEnter(region,beacons);
		      }

		      @Override
		      public void onExitedRegion(Region region) {
		        ;
		      }
		    });
	    
	  }	
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	//super.onCreateOptionsMenu(menu);
    	//menu.clear();
    	//MenuInflater inflater = getMenuInflater(); 
    	//inflater.inflate(R.menu.main, menu);
    	
    	ActionBar actionBar = getActionBar();
    	actionBar.setDisplayShowHomeEnabled(false);
    	//displaying custom ActionBar
    	View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar, null);
    	actionBar.setCustomView(mActionBarView);
    	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    	
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
    	return true;
        //return super.onOptionsItemSelected(item);
    }
    
    public void startbutton(View v) {
		Intent intent = new Intent(FirstPage.this, WelcomeStarBuck.class);
		startActivity(intent);
		FirstPage.this.finish();
	}
    
    public void toggleMenu(View v) {
		Intent intent = new Intent(FirstPage.this, Person.class);
		startActivity(intent);
		FirstPage.this.finish();
	}

    @Override
    protected void onDestroy() {
      notificationManager.cancel(NOTIFICATION_ID);
      beaconManager.disconnect();

      super.onDestroy();
    }

    @Override
    protected void onStart() {
      super.onStart();

      // Check if device supports Bluetooth Low Energy.
      if (!beaconManager.hasBluetooth()) {
        Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
        return;
      }

      // If Bluetooth is not enabled, let user enable it.
      if (!beaconManager.isBluetoothEnabled()) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
      } else {
        connectToService();
      }
    }

    @Override
    protected void onStop() {
//	  beaconManager.disconnect();

      super.onStop();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == REQUEST_ENABLE_BT) {
        if (resultCode == Activity.RESULT_OK) {
          connectToService();
        } else {
          Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
          getActionBar().setSubtitle("Bluetooth not enabled");
        }
      }
      super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void connectToService() {

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
          @Override
          public void onServiceReady() {
            try {
              beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            } catch (RemoteException e) {
              Toast.makeText(FirstPage.this, "Cannot start ranging, something terrible happened",
                  Toast.LENGTH_LONG).show();
              Log.e(TAG, "Cannot start ranging", e);
            }
            
            try {
            	beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS_REGION); 
              } catch (RemoteException e) {
                Toast.makeText(FirstPage.this, "Cannot start monitor, something terrible happened",
                    Toast.LENGTH_LONG).show();
                Log.e(TAG, "Cannot start ranging", e);
              }
 
          }
        });
      }    

    private void postNotificationEnter(Region region, List<Beacon> beacons) {
    	
        Intent notifyIntent = new Intent(FirstPage.this, FirstPage.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(
        		FirstPage.this,
            0,
            new Intent[]{notifyIntent},
            PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(FirstPage.this)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Meluo Demo")
            .setContentText("entered")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notificationManager.notify(NOTIFICATION_ID, notification);

    	Intent intent = new Intent(FirstPage.this, WelcomeStarBuck.class);
	    startActivity(intent); 
      
      }      
}
