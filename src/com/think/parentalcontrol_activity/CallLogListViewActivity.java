package com.think.parentalcontrol_activity;

import java.util.ArrayList;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_adapters.CallLogAdapter;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.CallDetailsModel;
import com.think.parentalcontrol_util.CallUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class CallLogListViewActivity extends Activity implements
		OnItemSelectedListener {

	Spinner callTypeSpinner = null;
	ListView calLogListView = null;
	DbManager dbManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_log);
		setUI();
	}

	private void setUI() {
		callTypeSpinner = (Spinner) findViewById(R.id.callTypeSpinner);
		calLogListView = (ListView) findViewById(R.id.callLogListView);
		// set up spinner
		callTypeSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.call_type)));
		callTypeSpinner.setOnItemSelectedListener(this);

		//
		dbManager = new DbManager(this);
		setUpListView("All");
	}

	private void setUpListView(String type) {
		ArrayList<CallDetailsModel> models = dbManager.getAllCallDetails(type);
		calLogListView.setAdapter(new CallLogAdapter(this, models));
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		String item = (String) arg0.getItemAtPosition(arg2);
		if (item.equals("All")) {
			setUpListView("All");
		} else if (item.equals("Missed Call")) {
			setUpListView(CallUtils.TYPE_MISSED);
		} else if (item.equals("Receive Call")) {
			setUpListView(CallUtils.TYPE_RECEIVED);
		} else if (item.equals("Rejected Call")) {
			setUpListView(CallUtils.TYPE_REJECTED);
		} else {
			setUpListView(CallUtils.TYPE_OUTGOING);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
