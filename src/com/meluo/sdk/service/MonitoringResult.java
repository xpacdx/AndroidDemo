package com.meluo.sdk.service;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.meluo.sdk.Beacon;
import com.meluo.sdk.Region;
import com.meluo.sdk.internal.Objects;
import com.meluo.sdk.internal.Preconditions;

public class MonitoringResult implements Parcelable {

	public final Region region;
	public final Region.State state;
	public final List<Beacon> beacons;

	public MonitoringResult(Region region, Region.State state, List<Beacon> beacons) {

		this.region = ((Region) Preconditions.checkNotNull(region, "region cannot be null"));
		this.state = ((Region.State) Preconditions.checkNotNull(state, "state cannot be null"));
		this.beacons = new ArrayList<Beacon>(beacons);
	}

	public boolean equals(Object o) {

		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		MonitoringResult that = (MonitoringResult) o;

		if (this.state != that.state)
			return false;
		if (this.region != null ? !this.region.equals(that.region) : that.region != null) {
			return false;
		}
		return true;
	}

	public int hashCode() {

		int result = this.region != null ? this.region.hashCode() : 0;
		result = 31 * result + (this.state != null ? this.state.hashCode() : 0);
		return result;
	}

	public String toString() {

		return Objects.toStringHelper(this).add("region", this.region).add("state", this.state.name())
				.add("beacons", this.beacons).toString();
	}

	public static final Parcelable.Creator<MonitoringResult> CREATOR = new Parcelable.Creator<MonitoringResult>() {

		public MonitoringResult createFromParcel(Parcel source) {

			ClassLoader classLoader = getClass().getClassLoader();
			Region region = (Region) source.readParcelable(classLoader);
			Region.State event = Region.State.values()[source.readInt()];
			List<Beacon> beacons = source.readArrayList(classLoader);
			return new MonitoringResult(region, event, beacons);
		}

		public MonitoringResult[] newArray(int size) {

			return new MonitoringResult[size];
		}
	};

	public int describeContents() {

		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {

		dest.writeParcelable(this.region, flags);
		dest.writeInt(this.state.ordinal());
		dest.writeList(this.beacons);
	}
}
