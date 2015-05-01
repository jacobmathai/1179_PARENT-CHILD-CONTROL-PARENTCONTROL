package com.think.parentalcontrol_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.think.parentalcontrol_activity.Luanch_Activity;
import com.think.parentalcontrol_util.AppUtils;

public class AppLauncherBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("HELLO", "inside broadcast");

		if (intent.getAction()
				.equals("android.intent.action.NEW_OUTGOING_CALL")) {
			Log.e("HELLO", "inside broadcast outgoing..");

			String outgoingNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

			if (outgoingNumber.equals(AppUtils.APP_LAUNCHER_NUMBER)) {
				Log.i("HELLO", "phone number detetcted: " + outgoingNumber);
				Intent i = new Intent(context, Luanch_Activity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				setResultData(null);
				Log.i("HELLO", "activity started");
			}

		}
	}
}
