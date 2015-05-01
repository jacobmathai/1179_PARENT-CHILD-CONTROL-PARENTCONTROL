package com.think.parentalcontrol_activity;

import java.util.ArrayList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.URLModels;
import com.think.parentalcontrol_observer.BrowserHistoryObserver;
import com.think.parentalcontrol_observer.CallObserver;
import com.think.parentalcontrol_observer.TrackSentSmsObserver;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI,
				true, new CallObserver(new Handler(), MainActivity.this));
		final Uri SMS_STATUS_URI = Uri.parse("content://sms");
		TrackSentSmsObserver smsSentObserver = new TrackSentSmsObserver(
				new Handler(), this);
		getContentResolver().registerContentObserver(SMS_STATUS_URI, true,
				smsSentObserver);

		ArrayList<URLModels> browserHistory = new BrowserHistoryObserver(this)
				.getBrowserHistory();
		for (URLModels urlModels : browserHistory) {
			boolean value = new DbManager(this).insertURL(urlModels);
			Log.d("TAG", "URL INSERTED : " + value);
		}

	}
}
