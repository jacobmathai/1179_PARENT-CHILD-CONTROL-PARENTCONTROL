package com.think.parentalcontrol_observer;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_util.DateUtils;
import com.think.parentalcontrol_util.DbUtils;
import com.think.parentalcontrol_util.SmsUtil;

public class TrackSentSmsObserver extends ContentObserver {
	private Context mContext = null;
	static final Uri SMS_STATUS_URI = Uri.parse("content://sms");
	DbManager db = null;
	String secondTime = "";
	SmsModel model = null;

	public TrackSentSmsObserver(Handler handler, Context context) {
		super(handler);
		mContext = context;
		db = new DbManager(mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onChange(boolean selfChange) {

		Cursor sms_sent_cursor = mContext.getContentResolver().query(
				SMS_STATUS_URI, null, null, null, null);
		if (sms_sent_cursor != null) {
			Uri uriSMS = Uri.parse("content://sms/");
			Cursor cur = mContext.getContentResolver().query(uriSMS, null,
					null, null, null);
			cur.moveToNext(); // this will make it point to the first record,
								// which is the last SMS sent
			String body = cur.getString(cur.getColumnIndex("body"));
			String add = cur.getString(cur.getColumnIndex("address"));
			String time = cur.getString(cur.getColumnIndex("date"));
			String protocol = cur.getString(cur.getColumnIndex("protocol"));
			if (protocol == null) {

				ArrayList<String> list = db.getTimeFromMessages();

				if (list.size() > 0) {
					int flag = 0;
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).equals(
								DateUtils.getTimeFormillies(Long
										.parseLong(time)))) {
							flag = 1;
						}
					}

					if (flag != 1) {
						model = new SmsModel();
						model.setTime(DateUtils.getTimeFormillies(Long
								.parseLong(time)));
						model.setDate(DateUtils.getDateFormillies(Long
								.parseLong(time)));
						model.setContent(body);
						model.setStatus(DbUtils.PENDING);
						model.setPhoneNumber(add);
						model.setType(SmsUtil.TYPE_OUTGOING);
						boolean value = db.insertSMSDetails(model);
						Log.d("TAG", "Insert sms details : " + value);
						Intent braodIntent = new Intent(
								"com.think.NetWork.SEND_INFO");
						mContext.sendBroadcast(braodIntent);

					}
				} else {
					model = new SmsModel();
					model.setTime(DateUtils.getTimeFormillies(Long
							.parseLong(time)));
					model.setDate(DateUtils.getDateFormillies(Long
							.parseLong(time)));
					model.setContent(body);
					model.setStatus(DbUtils.PENDING);
					model.setPhoneNumber(add);
					model.setType(SmsUtil.TYPE_OUTGOING);
					boolean value = db.insertSMSDetails(model);
					Log.d("TAG", "Insert sms details : " + value);
					Intent braodIntent = new Intent(
							"com.think.NetWork.SEND_INFO");
					mContext.sendBroadcast(braodIntent);

				}

			}

			super.onChange(selfChange);
		}

	}
}
