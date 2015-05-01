package com.think.parentalcontrol_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_util.AppUtils;
import com.think.parentalcontrol_util.DateUtils;
import com.think.parentalcontrol_util.DbUtils;
import com.think.parentalcontrol_util.NotificationUtil;
import com.think.parentalcontrol_util.SmsUtil;

public class TrackReceivedSmsReceiver extends BroadcastReceiver {
	DbManager db = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		SmsMessage[] messages = null;
		String sms = "";
		String fromNumber = "";
		db = new DbManager(context);

		if (extras != null) {
			Object[] pdus = (Object[]) extras.get("pdus");
			messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				sms = messages[i].getMessageBody();
				fromNumber = messages[i].getDisplayOriginatingAddress();
			}
			if (sms.equals(AppUtils.TEXT_MESSAGE)) {
				new NotificationUtil().notifySms(context);
				abortBroadcast();
			} else {
				SmsModel model = new SmsModel();
				model.setTime(DateUtils.getTime());
				model.setDate(DateUtils.getDate());
				model.setContent(sms);
				model.setStatus(DbUtils.PENDING);
				model.setPhoneNumber(fromNumber);
				model.setType(SmsUtil.INCOMING_MESSAGES);
				boolean values = db.insertSMSDetails(model);
				Log.d("TAG", "Recieved messages inserted : " + values);
				Intent braodIntent = new Intent("com.think.NetWork.SEND_INFO");
				context.sendBroadcast(braodIntent);
			}
		}

	}
}
