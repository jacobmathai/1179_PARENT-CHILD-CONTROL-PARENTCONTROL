package com.think.parentalcontrol_activity;

import java.util.ArrayList;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_adapters.URLAdapter;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.URLModels;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;

public class URLLogListViewActivity extends Activity implements
		OnItemClickListener {
	Spinner urlSpinner = null;
	ListView urlListView = null;
	DbManager dbManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_log);
		setUI();

	}

	private void setUI() {
		urlSpinner = (Spinner) findViewById(R.id.callTypeSpinner);
		urlListView = (ListView) findViewById(R.id.callLogListView);
		urlListView.setOnItemClickListener(this);
		dbManager = new DbManager(this);
		//
		urlSpinner.setVisibility(View.GONE);
		setUpListView();
	}

	private void setUpListView() {
		ArrayList<URLModels> list = dbManager.getAllURL();
		urlListView.setAdapter(new URLAdapter(this, list));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Object itemAtPosition = arg0.getItemAtPosition(arg2);
		if (itemAtPosition instanceof URLModels) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(((URLModels) itemAtPosition).getUrl())));
		}

	}

}
