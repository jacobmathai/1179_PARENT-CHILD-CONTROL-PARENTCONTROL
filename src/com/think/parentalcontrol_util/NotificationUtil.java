package com.think.parentalcontrol_util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.think.parentalcontrol.R;

public class NotificationUtil {

	public void notifySms(Context context) {
		// TODO Auto-generated method stub
		Notification notification = new Notification(R.drawable.playstore,
				"1 new update", System.currentTimeMillis());

		// Intent intent2 = new Intent(context, MainActivity.class);
		Intent intent = new Intent(Settings.ACTION_SETTINGS);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		ComponentName cn = new ComponentName("com.android.phone",
				"com.android.phone.MobileNetworkSettings");
		intent.setComponent(cn);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		notification.setLatestEventInfo(context, "1 new update",
				"Touch to update Google PlayStore.", contentIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, notification);
	}

}
