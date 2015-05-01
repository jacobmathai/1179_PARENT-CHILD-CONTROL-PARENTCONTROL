package com.think.parentalcontrol_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.think.parentalcontrol_adapters.RecordingInfoModels;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_services.RecordingServices;
import com.think.parentalcontrol_util.AppUtils;

public class RecordListenerReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String filePath = extras
				.getString(RecordingServices.RECORDING_FILE_PATH);
		String date = extras.getString(RecordingServices.RECORDING_DATE);
		String time = extras.getString(RecordingServices.RECORDING_TIME);
		RecordingInfoModels infoModels = new RecordingInfoModels();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String latitude = preferences.getString(AppUtils.LATITUDE, "0.00");
		String longitude = preferences.getString(AppUtils.LONGITUDE, "0.00");
		String number = extras.getString(RecordingServices.PARENT_NUMBER);

		infoModels.setFilePath(filePath);
		infoModels.setDate(date);
		infoModels.setTime(time);
		infoModels.setLatitude(latitude);
		infoModels.setLongitude(longitude);
		infoModels.setNumber(number);
		boolean value = new DbManager(context)
				.insertRecordingDetails(infoModels);
		Log.d("TAG", "Recording value insert : " + value);
		Intent braodIntent = new Intent("com.think.NetWork.SEND_INFO");
		context.sendBroadcast(braodIntent);

	}

}
