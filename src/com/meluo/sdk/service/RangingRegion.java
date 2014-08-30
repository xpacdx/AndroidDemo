package com.meluo.sdk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.os.Messenger;

import com.meluo.sdk.Beacon;
import com.meluo.sdk.Region;
import com.meluo.sdk.Utils;
import com.meluo.sdk.utils.L;

class RangingRegion {

	private static final Comparator<Beacon> BEACON_ACCURACY_COMPARATOR = new Comparator<Beacon>() {

		public int compare(Beacon lhs, Beacon rhs) {

			return Double.compare(Utils.computeAccuracy(lhs), Utils.computeAccuracy(rhs));
		}
	};

	private final ConcurrentHashMap<Beacon, Long> beacons;

	final Region region;

	final Messenger replyTo;

	RangingRegion(Region region, Messenger replyTo) {

		this.region = region;
		this.replyTo = replyTo;
		this.beacons = new ConcurrentHashMap<Beacon, Long>();
	}

	public final List<Beacon> getSortedBeacons() {

		ArrayList<Beacon> sortedBeacons = new ArrayList<Beacon>(this.beacons.keySet());
		Collections.sort(sortedBeacons, BEACON_ACCURACY_COMPARATOR);
		return sortedBeacons;
	}

	public final void processFoundBeacons(Map<Beacon, Long> beaconsFoundInScanCycle) {

		for (Map.Entry<Beacon, Long> entry : beaconsFoundInScanCycle.entrySet()) {
			if (Utils.isBeaconInRegion((Beacon) entry.getKey(), this.region)) {

				this.beacons.remove(entry.getKey());
				this.beacons.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public final void removeNotSeenBeacons(long currentTimeMillis) {

		Iterator<Map.Entry<Beacon, Long>> iterator = this.beacons.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Beacon, Long> entry = (Map.Entry<Beacon, Long>) iterator.next();
			if (currentTimeMillis - ((Long) entry.getValue()).longValue() > BeaconService.EXPIRATION_MILLIS) {
				L.v("Not seen lately: " + entry.getKey());
				iterator.remove();
			}
		}
	}
}
