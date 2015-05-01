package com.think.parentalcontrol_receivers;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.think.parentalcontrol_adapters.RecordingInfoModels;
import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_manager.JsoneManager;
import com.think.parentalcontrol_models.CallDetailsModel;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_models.URLModels;
import com.think.parentalcontrol_services.Webservices;
import com.think.parentalcontrol_util.AppUtils;
import com.think.parentalcontrol_util.NetWorkUtil;
import com.think.parentalcontrol_util.WebserviceUtil;

public class SendInfoToWebReciever extends BroadcastReceiver {
	DbManager dbManager = null;
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		dbManager = new DbManager(context);
		if (NetWorkUtil.getConnectivityStatus(context) == NetWorkUtil.TYPE_MOBILE) {
			new SendCallInfoToWeb().execute();
		} else if (NetWorkUtil.getConnectivityStatus(context) == NetWorkUtil.TYPE_WIFI) {
			new SendCallInfoToWeb().execute();
		} else {

		}

	}

	private class SendCallInfoToWeb extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JsoneManager jsoneManager = new JsoneManager();
			// manage call details
			ArrayList<CallDetailsModel> callDetails = dbManager
					.getAllCallDetails("All");
			if (callDetails.size() > 0) {
				JSONObject jsonObject = jsoneManager
						.getCallJsonInfo(callDetails);
				String rsp = new Webservices().sendCallInfo(
						PreferenceManager.getDefaultSharedPreferences(context)
								.getString(AppUtils.IMEI, ""), jsonObject
								.toString());
				Log.d("TAG", "WEB CALLED : " + rsp);
				if (rsp.equals(WebserviceUtil.TRUE)) {
					dbManager.deleteCallInfo();
				}
				return rsp;
			} else {
				return WebserviceUtil.TRUE;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (!result.equals(WebserviceUtil.ERROR)) {
				// manage sms details
				if (result.equals(WebserviceUtil.TRUE)) {
					new SendUrlInfoToWeb().execute();
				}
			}

		}
	}

	private class SendUrlInfoToWeb extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DbManager dbManager = new DbManager(context);
			ArrayList<URLModels> allURL = dbManager.getAllURL();
			if (allURL.size() > 0) {
				JSONObject urlJsonInfo = new JsoneManager()
						.getURLJsonInfo(allURL);
				String rsp = new Webservices().sendUrlInfo(
						PreferenceManager.getDefaultSharedPreferences(context)
								.getString(AppUtils.IMEI, ""), urlJsonInfo
								.toString());
				if (rsp.equals(WebserviceUtil.TRUE)) {
					dbManager.deleteUrlInfo();
				}
				return rsp;
			} else {
				return WebserviceUtil.TRUE;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (!result.equals(WebserviceUtil.ERROR)) {
				// manage sms details
				if (result.equals(WebserviceUtil.TRUE)) {
					new SendSmsInfo().execute();
				}
			}

		}

	}

	private class SendSmsInfo extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DbManager dbManager = new DbManager(context);
			ArrayList<SmsModel> allSMSDetails = dbManager
					.getAllSMSDetails("All");
			if (allSMSDetails.size() > 0) {
				JSONObject smsJsonInfo = new JsoneManager()
						.getSMSJsonInfo(allSMSDetails);
				String rsp = new Webservices().sendSmsInfo(
						PreferenceManager.getDefaultSharedPreferences(context)
								.getString(AppUtils.IMEI, ""), smsJsonInfo
								.toString());
				if (rsp.equals(WebserviceUtil.TRUE)) {
					dbManager.deleteSmsInfo();
				}
				return rsp;
			} else {
				return WebserviceUtil.TRUE;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (!result.equals(WebserviceUtil.ERROR)) {
				// manage sms details
				if (result.equals(WebserviceUtil.TRUE)) {
					new SendRecoridingInfo().execute();
				}
			}
		}

	}

	private class SendRecoridingInfo extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DbManager dbManager = new DbManager(context);
			ArrayList<RecordingInfoModels> allRecrdingInfo = dbManager
					.getAllRecrdingInfo();
			if (allRecrdingInfo.size() > 0) {
				JSONObject recordJsonInfo = new JsoneManager()
						.getRecordJsonInfo(allRecrdingInfo);
				String rsp = new Webservices().sendRecordingInfo(
						PreferenceManager.getDefaultSharedPreferences(context)
								.getString(AppUtils.IMEI, ""), recordJsonInfo
								.toString());
				if (rsp.equals(WebserviceUtil.TRUE)) {
					dbManager.deleteRecordingInfo();
				}
				return rsp;
			} else {
				return WebserviceUtil.TRUE;
			}
		}

	}
}
