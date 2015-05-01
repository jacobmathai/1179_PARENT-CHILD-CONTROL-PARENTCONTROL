package com.think.parentalcontrol_observer;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.util.Log;

import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.CallDetailsModel;
import com.think.parentalcontrol_services.RecordingServices;
import com.think.parentalcontrol_util.AppUtils;
import com.think.parentalcontrol_util.CallUtils;
import com.think.parentalcontrol_util.DateUtils;

public class CallObserver extends ContentObserver {

	public static final String PARENT_NUMBER = "PARENT_NUMBER";
	private Context context;
	DbManager dbManager = null;
	private String dir;

	public CallObserver(Handler handler, Context context) {
		super(handler);
		this.context = context;
		dbManager = new DbManager(context);

		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean deliverSelfNotifications() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		// TODO Auto-generated method stub
		Cursor managedCursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		if (managedCursor.moveToNext()) {
			CallDetailsModel model = new CallDetailsModel();
			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			String callDuration = managedCursor.getString(duration);

			String dateString = DateUtils.getDateFormillies(Long
					.parseLong(callDate));
			String timeString = DateUtils.getTimeFormillies(Long
					.parseLong(callDate));
			String caluculatedDuration = DateUtils
					.getCaluculatedDuration(callDuration);

			int dircode = Integer.parseInt(callType);
			switch (dircode) {
			case CallLog.Calls.OUTGOING_TYPE:
				dir = CallUtils.TYPE_OUTGOING;
				break;

			case CallLog.Calls.INCOMING_TYPE:
				dir = CallUtils.TYPE_RECEIVED;
				break;

			case CallLog.Calls.MISSED_TYPE:
				dir = CallUtils.TYPE_MISSED;
				break;
			default:
				dir = CallUtils.TYPE_REJECTED;
				break;

			}
			Log.d("NUMBER", "PHONE NUMBERSSSSSS " + phNumber);
			if (phNumber.equals("+91"+PreferenceManager
					.getDefaultSharedPreferences(context).getString(
							AppUtils.PARENT_NUMBER, ""))
					&& (CallLog.Calls.MISSED_TYPE == dircode || dir == CallUtils.TYPE_REJECTED)) {
			
				Log.d("TAG", "PARENT");
				context.startService(new Intent(context,
						RecordingServices.class).putExtra(PARENT_NUMBER,
						phNumber));
			} else {
				// set to model
				ArrayList<String> list = dbManager.getTimeFromCall();
				if (list.size() > 0) {
					int flag = 0;
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).equals(timeString)) {
							flag = 1;
						}
					}

					if (flag != 1) {
						model.setNumber(phNumber);
						model.setType(dir);
						model.setDate(dateString);
						model.setTime(timeString);
						model.setDuration(caluculatedDuration);

						boolean value = dbManager.insertCallDetails(model);
						Log.d("TAG", " INSERT : " + value);
						Intent braodIntent = new Intent(
								"com.think.NetWork.SEND_INFO");
						context.sendBroadcast(braodIntent);
					}
				} else {
					model.setNumber(phNumber);
					model.setType(dir);
					model.setDate(dateString);
					model.setTime(timeString);
					model.setDuration(caluculatedDuration);

					boolean value = dbManager.insertCallDetails(model);
					Log.d("TAG", " INSERT : " + value);
					Intent braodIntent = new Intent(
							"com.think.NetWork.SEND_INFO");
					context.sendBroadcast(braodIntent);
				}
				Log.d("TAG", " INSERT inner");
			}
		}

	}
}
