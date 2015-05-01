package com.think.parentalcontrol_manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.think.parentalcontrol_adapters.RecordingInfoModels;
import com.think.parentalcontrol_models.CallDetailsModel;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_models.URLModels;

public class JsoneManager {

	public JSONObject getCallJsonInfo(ArrayList<CallDetailsModel> details) {
		// TODO Auto-generated method stub
		JSONObject mainObject = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			for (int i = 0; i < details.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", details.get(i).getId());
				jsonObject.put("number", details.get(i).getNumber());
				jsonObject.put("type", details.get(i).getType());
				jsonObject.put("date", details.get(i).getDate());
				jsonObject.put("time", details.get(i).getTime());
				jsonObject.put("duration", details.get(i).getDuration());
				array.put(jsonObject);
			}
			mainObject.put("CALL", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mainObject;
	}

	public JSONObject getSMSJsonInfo(ArrayList<SmsModel> details) {
		// TODO Auto-generated method stub
		JSONObject mainObject = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			for (int i = 0; i < details.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", details.get(i).getId());
				jsonObject.put("time", details.get(i).getTime());
				jsonObject.put("date", details.get(i).getDate());
				jsonObject.put("content", details.get(i).getContent());
				jsonObject.put("number", details.get(i).getPhoneNumber());
				jsonObject.put("type", details.get(i).getType());
				array.put(jsonObject);
			}
			mainObject.put("SMS", array);

		} catch (JSONException e) {

		}
		return mainObject;
	}

	public JSONObject getURLJsonInfo(ArrayList<URLModels> allURL) {
		// TODO Auto-generated method stub
		JSONObject mainObject = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			for (int i = 0; i < allURL.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("id", allURL.get(i).getId());
				object.put("title", allURL.get(i).getTitle());
				object.put("date", allURL.get(i).getDate());
				object.put("time", allURL.get(i).getTime());
				object.put("url", allURL.get(i).getUrl());
				object.put("status", allURL.get(i).getStatus());
				object.put("visit", allURL.get(i).getVisit_count());
				array.put(object);
			}

			mainObject.put("URL", array);
		} catch (JSONException e) {

		}
		return mainObject;
	}

	public JSONObject getRecordJsonInfo(ArrayList<RecordingInfoModels> list) {
		// TODO Auto-generated method stub
		JSONObject mainJsonObject = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			for (int i = 0; i < list.size(); i++) {

				JSONObject object = new JSONObject();
				object.put("id", list.get(i).getId());
				object.put("number", list.get(i).getNumber());
				object.put("path", list.get(i).getFilePath());
				object.put("file", getFileString(list.get(i).getFilePath()));
				object.put("latitude", list.get(i).getLatitude());
				object.put("longitude", list.get(i).getLongitude());
				object.put("date", list.get(i).getDate());
				object.put("time", list.get(i).getTime());
				array.put(object);
			}
			mainJsonObject.put("RECORD", array);
		} catch (JSONException e) {

		}
		return mainJsonObject;
	}

	private String getFileString(String path) {
		// TODO Auto-generated method stub
		String source = "";
		try {
			FileInputStream stream = new FileInputStream(new File(path));
			byte[] buffer = new byte[stream.available()];
			stream.read(buffer);
			stream.close();
			source = Base64.encodeToString(buffer, Base64.CRLF);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return source;
	}
}
