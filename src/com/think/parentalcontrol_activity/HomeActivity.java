package com.think.parentalcontrol_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.think.parentalcontrol.R;

public class HomeActivity extends Activity implements OnClickListener {
	Button callLogButton = null;
	Button smsLogButton = null;
	Button urlLogButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setUI();
	}

	private void setUI() {
		callLogButton = (Button) findViewById(R.id.callLogButton);
		smsLogButton = (Button) findViewById(R.id.smsLogButton);
		urlLogButton = (Button) findViewById(R.id.urlLogButton);

		//
		callLogButton.setOnClickListener(this);
		smsLogButton.setOnClickListener(this);
		urlLogButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == callLogButton.getId()) {
			startActivity(new Intent(HomeActivity.this,
					CallLogListViewActivity.class));
		} else if (v.getId() == smsLogButton.getId()) {
			startActivity(new Intent(HomeActivity.this,
					SmsLogListViewActivity.class));
		} else {
			startActivity(new Intent(HomeActivity.this,
					URLLogListViewActivity.class));
		}

	}

}
