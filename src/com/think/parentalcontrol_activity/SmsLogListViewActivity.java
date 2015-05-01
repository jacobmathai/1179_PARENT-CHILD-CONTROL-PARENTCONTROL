package com.think.parentalcontrol_activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_adapters.SMSAdapter;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_util.SmsUtil;

public class SmsLogListViewActivity extends Activity implements
		OnItemSelectedListener {
	Spinner smsTypeSpinner = null;
	ListView smsLogListView = null;
	DbManager dbManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_log);
		setUI();
	}

	private void setUI() {
		smsLogListView = (ListView) findViewById(R.id.callLogListView);
		smsTypeSpinner = (Spinner) findViewById(R.id.callTypeSpinner);

		dbManager = new DbManager(this);

		// setup spinner
		smsTypeSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.sms_type)));

		//
		smsTypeSpinner.setOnItemSelectedListener(this);

		setUpListView("All");
	}

	private void setUpListView(String type) {
		// TODO Auto-generated method stub
		ArrayList<SmsModel> list = dbManager.getAllSMSDetails(type);
		smsLogListView.setAdapter(new SMSAdapter(this, list));
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String item = (String) arg0.getItemAtPosition(arg2);
		if (item.equals("All")) {
			setUpListView("All");
		} else if (item.equals("Received")) {
			setUpListView(SmsUtil.INCOMING_MESSAGES);
		} else {
			setUpListView(SmsUtil.TYPE_OUTGOING);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
