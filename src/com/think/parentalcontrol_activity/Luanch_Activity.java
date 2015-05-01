package com.think.parentalcontrol_activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.URLModels;
import com.think.parentalcontrol_observer.BrowserHistoryObserver;
import com.think.parentalcontrol_observer.CallObserver;
import com.think.parentalcontrol_observer.TrackSentSmsObserver;
import com.think.parentalcontrol_services.GpsSerivces;
import com.think.parentalcontrol_services.MydeviceAdminReciever;
import com.think.parentalcontrol_services.Webservices;
import com.think.parentalcontrol_util.AppUtils;
import com.think.parentalcontrol_util.NetWorkUtil;
import com.think.parentalcontrol_util.WebserviceUtil;

public class Luanch_Activity extends Activity implements OnLongClickListener {

	public static final int RESULT_ENABLE = 12;
	LinearLayout about = null;
	private LayoutInflater inflater;
	private TextView subText;
	private BroadcastReceiver broadcastReceiver;
	private TelephonyManager telephonyManager;
	static int id = 100;
	DevicePolicyManager myDevicePolicyManager;
	ActivityManager myActivityManager;
	ComponentName myComponentName;
	String number = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_about);
		

		// creating dir
		File file = new File(AppUtils.DIR_PATH);
		if (!file.isDirectory()) {
			file.mkdirs();
		}

		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI,
				true, new CallObserver(new Handler(), this));
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
		startService(new Intent(Luanch_Activity.this, GpsSerivces.class));
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		// get parent number

		String number = PreferenceManager.getDefaultSharedPreferences(this)
				.getString(AppUtils.PARENT_NUMBER, "");
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		PreferenceManager.getDefaultSharedPreferences(this).edit()
				.putString(AppUtils.IMEI, telephonyManager.getDeviceId())
				.commit();

		if (number.equals("")) {
			if (NetWorkUtil.getConnectivityStatus(this) == NetWorkUtil.TYPE_MOBILE
					|| NetWorkUtil.getConnectivityStatus(this) == NetWorkUtil.TYPE_WIFI) {

				new GetParentNumber().execute(telephonyManager.getDeviceId());
			}
		}

		// =====================================hide app=======================
		myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		myActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		myComponentName = new ComponentName(getApplicationContext(),
				MydeviceAdminReciever.class);

		if (ActivityManager.isUserAMonkey()) {
			// Don't trust monkeys to do the right thing!
			Toast.makeText(getApplicationContext(),
					"Sorry you dont have administrative privilages",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!myDevicePolicyManager.isAdminActive(myComponentName)) {
			Log.d("Check", "enabling");

			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					myComponentName);
			intent.putExtra(
					DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"Enabling Device Administrator Would Enable The Application "
							+ "Not To Be Uninstalled In The Traditional Method.");
			startActivityForResult(intent, RESULT_ENABLE);

		}

		// ========================================================
		about = (LinearLayout) findViewById(R.id.values);

		// inflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ArrayList<View> content = new ArrayList<View>();

		content.add(addToView("\n\tManufacturer", "\t" + Build.MANUFACTURER
				+ "\n", false));
		content.add(addToView("\n\tModel Number", "\t" + Build.MODEL + "\n",
				false));
		content.add(addToView("\n\tAndroid Version", "\tAndroid "
				+ Build.VERSION.RELEASE + " (API :" + Build.VERSION.SDK
				+ " )\n", false));
		content.add(addToView("\n\tBaseband Version",
				"\t" + Build.RADIO + "\n", false));
		content.add(addToView("\n\tKernal Version", "\t" + Build.BOOTLOADER
				+ "\n", false));

		for (View view : content) {

			about.addView(view);

		}

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);

		TextView titleText = new TextView(getApplicationContext());
		titleText.setTextColor(Color.BLACK);
		titleText.setTextSize(18);
		subText = new TextView(getApplicationContext());
		subText.setTextColor(Color.BLUE);
		titleText.setText("\n\tBattery Usage");
		titleText.setTypeface(getRobotoStyle());
		subText.setTypeface(getDroidStyle());
		subText.setTextColor(Color.RED);
		subText.setOnLongClickListener(this);

		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				int level = intent.getIntExtra("level", 0);
				subText.setText("\t" + level + "% \n");

			}
		};

		registerReceiver(broadcastReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		titleText.setTextColor(Color.GRAY);
		layout.addView(getDivider());
		layout.addView(titleText);
		layout.addView(subText);
		layout.addView(getDivider());
		about.addView(layout);
		// registerForContextMenu(about);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_ENABLE:
			if (resultCode == Activity.RESULT_OK) {
				Log.i("DeviceAdminSample", "Admin enabled!");
				hideApp();
				finish();
			} else {
				Log.i("DeviceAdminSample", "Admin enable FAILED!");
			}
			return;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void hideApp() {
		// TODO Auto-generated method stub
		ComponentName componentToDisable = new ComponentName(this,
				Luanch_Activity.class);
		getPackageManager().setComponentEnabledSetting(componentToDisable,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}

	public View addToView(String title, String sub, boolean enabled) {

		View message = inflater.inflate(R.layout.about, null);

		TextView titleText = (TextView) message.findViewById(R.id.textView1);

		TextView subText = (TextView) message.findViewById(R.id.textView2);
		titleText.setText(title);
		titleText.setTypeface(getRobotoStyle());

		subText.setText(sub);
		subText.setTextColor(Color.RED);
		subText.setTypeface(getDroidStyle());
		// Toast.makeText(getApplicationContext(), sub + ":" +
		// subText.getId(),Toast.LENGTH_SHORT).show();
		if (!enabled) {
			titleText.setTextColor(Color.GRAY);

		}

		return message;

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	public View getDivider() {
		View view = new View(getApplicationContext());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				1);

		view.setLayoutParams(layoutParams);
		view.setBackgroundColor(Color.GRAY);
		return view;
	}

	public Typeface getRobotoStyle() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Bold.ttf");
		return tf;
	}

	public Typeface getDroidStyle() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/DroidSerif-Regular.ttf");
		return tf;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		try {
			String username = PreferenceManager.getDefaultSharedPreferences(
					this).getString(AppUtils.USERNAME, "");
			String password = PreferenceManager.getDefaultSharedPreferences(
					this).getString(AppUtils.PASSWORD, "");
			if (username.equals("") && password.equals("")) {
				createDailogue("Login");
			} else {
				createSecondTime("Login");
			}
		} catch (Exception e) {

		}
		return false;
	}

	private void createSecondTime(String title) {
		// TODO Auto-generated method stub
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = 5;
		final EditText usernameEditText = new EditText(this);
		final EditText passwordEditText = new EditText(this);
		usernameEditText.setGravity(Gravity.CENTER);
		passwordEditText.setGravity(Gravity.CENTER);
		passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		usernameEditText.setHint("Username");
		passwordEditText.setHint("Password");
		layout.addView(usernameEditText, params);
		layout.addView(passwordEditText, params);

		final AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle(title).setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Ok", null)
				.setNegativeButton("Cancel", null).setView(layout).create();
		alertDialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(final DialogInterface dialog) {
				// TODO Auto-generated method stub
				Button okButton = alertDialog
						.getButton(AlertDialog.BUTTON_POSITIVE);
				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (usernameEditText.getText().toString().equals("")) {
							usernameEditText.setError("Enter username");
						} else if (passwordEditText.getText().toString()
								.equals("")) {
							passwordEditText.setError("Enter password");
						} else {
							String username = PreferenceManager
									.getDefaultSharedPreferences(
											Luanch_Activity.this).getString(
											AppUtils.USERNAME, "");
							String password = PreferenceManager
									.getDefaultSharedPreferences(
											Luanch_Activity.this).getString(
											AppUtils.PASSWORD, "");
							if (password.equals(passwordEditText.getText()
									.toString())
									&& username.equals(usernameEditText
											.getText().toString())) {
								startActivity(new Intent(Luanch_Activity.this,
										HomeActivity.class));
								finish();
							} else {
								Toast.makeText(Luanch_Activity.this,
										"Invalid username or password",
										Toast.LENGTH_SHORT).show();
							}

						}

					}
				});
			}
		});
		alertDialog.show();

	}

	private void createDailogue(String title) {
		// TODO Auto-generated method stub
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = 5;
		final EditText usernameEditText = new EditText(this);
		final EditText passwordEditText = new EditText(this);
		usernameEditText.setGravity(Gravity.CENTER);
		passwordEditText.setGravity(Gravity.CENTER);
		passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		usernameEditText.setHint("Username");
		passwordEditText.setHint("Password");
		layout.addView(usernameEditText, params);
		layout.addView(passwordEditText, params);

		final AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle(title).setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Ok", null)
				.setNegativeButton("Cancel", null).setView(layout).create();

		alertDialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(final DialogInterface dialog) {
				// TODO Auto-generated method stub
				Button okButton = alertDialog
						.getButton(AlertDialog.BUTTON_POSITIVE);
				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (usernameEditText.getText().toString().equals("")) {
							usernameEditText.setError("Enter username");
						} else if (passwordEditText.getText().toString()
								.equals("")) {
							passwordEditText.setError("Enter password");
						} else {
							// call webservice
							new CheckLogin().execute(usernameEditText.getText()
									.toString(), passwordEditText.getText()
									.toString());
						}

					}
				});
			}
		});
		alertDialog.show();
	}

	private class GetParentNumber extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String rsp = new Webservices().getParentNumber(params[0]);
			Log.d("NUMBER", rsp);
			return rsp;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (!result.equals(WebserviceUtil.ERROR)) {
				if (!result.equals("")) {
					
					/*PreferenceManager
							.getDefaultSharedPreferences(Luanch_Activity.this)
							.edit().putString(AppUtils.PARENT_NUMBER, result)
							.commit();*/
				} else {
					Log.d("TAG", "Error in ");
				}
			} else {
				Log.d("TAG", "Error");
			}
			super.onPostExecute(result);
		}
	}

	private class CheckLogin extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = new ProgressDialog(Luanch_Activity.this);
		private String username;
		private String password;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			this.username = params[0];
			this.password = params[1];
			String rsp = new Webservices().checkLogin(params[0], params[1]);
			return rsp;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading...!");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			if (!result.equals(WebserviceUtil.ERROR)) {
				if (result.equals(WebserviceUtil.TRUE)) {
					PreferenceManager
							.getDefaultSharedPreferences(Luanch_Activity.this)
							.edit().putString(AppUtils.USERNAME, username)
							.putString(AppUtils.PASSWORD, password).commit();
					startActivity(new Intent(Luanch_Activity.this,
							HomeActivity.class));
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Invalid username or password", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Network error",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}
