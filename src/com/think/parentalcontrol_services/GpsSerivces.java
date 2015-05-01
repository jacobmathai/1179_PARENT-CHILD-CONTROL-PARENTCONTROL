package com.think.parentalcontrol_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.think.parentalcontrol_util.AppUtils;

public class GpsSerivces extends Service implements LocationListener {

	private LocationManager locationManager;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
				.edit().putString(AppUtils.LATITUDE, arg0.getLatitude() + "")
				.putString(AppUtils.LONGITUDE, arg0.getLongitude() + "")
				.commit();
		Toast.makeText(
				getApplicationContext(),
				"Latitude :" + arg0.getLatitude() + "Longitude : "
						+ arg0.getLongitude(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
				MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		super.onCreate();
	}
}
