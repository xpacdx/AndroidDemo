package com.meluo.androiddemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.meluo.sdk.Beacon;
import com.meluo.sdk.Utils;

/**
 * Displays basic information about beacon.
 * 
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class LeDeviceListAdapter extends BaseAdapter {

	private List<Beacon> beacons;
	private LayoutInflater inflater;

	public LeDeviceListAdapter(Context context) {

		this.inflater = LayoutInflater.from(context);
		this.beacons = new ArrayList<Beacon>();
	}

	public void replaceWith(List<Beacon> newBeacons) {

		this.beacons.clear();
		this.beacons.addAll(newBeacons);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return beacons.size();
	}

	@Override
	public Beacon getItem(int position) {

		return beacons.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		view = inflateIfRequired(view, position, parent);
		bind(getItem(position), view);
		return view;
	}

	private void bind(Beacon beacon, View view) {

		ViewHolder holder = (ViewHolder) view.getTag();
		if (beacon.getMajor()==1 || beacon.getMajor()==0)
		{
			holder.nameTextView.setText("Name: " + "Meluo beacons");	
		}
		else
		{
			holder.nameTextView.setText("Name: " + beacon.getName());	
		}

		holder.macTextView.setText(String.format("MAC: %s (%.2fm)", beacon.getMacAddress(),
				Utils.computeAccuracy(beacon)));
		holder.uuidTextView.setText("UUID: " + beacon.getProximityUUID());		
		holder.majorTextView.setText("Major: " + beacon.getMajor());
		holder.minorTextView.setText("Minor: " + beacon.getMinor());
		holder.measuredPowerTextView.setText("MPower: " + beacon.getMeasuredPower());
		holder.rssiTextView.setText("RSSI: " + beacon.getRssi());
	}

	private View inflateIfRequired(View view, int position, ViewGroup parent) {

		if (view == null) {
			view = inflater.inflate(R.layout.device_item, null);
			view.setTag(new ViewHolder(view));
		}
		return view;
	}

	static class ViewHolder {
		final TextView nameTextView;
		final TextView macTextView;
		final TextView uuidTextView;
		final TextView majorTextView;
		final TextView minorTextView;
		final TextView measuredPowerTextView;
		final TextView rssiTextView;

		ViewHolder(View view) {
			nameTextView = (TextView) view.findViewWithTag("name");
			macTextView = (TextView) view.findViewWithTag("mac");
			uuidTextView = (TextView) view.findViewWithTag("uuid");			
			majorTextView = (TextView) view.findViewWithTag("major");
			minorTextView = (TextView) view.findViewWithTag("minor");
			measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
			rssiTextView = (TextView) view.findViewWithTag("rssi");
		}
	}
}
