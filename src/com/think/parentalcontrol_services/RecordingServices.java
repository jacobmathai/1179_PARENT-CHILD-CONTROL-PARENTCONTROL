package com.think.parentalcontrol_services;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.think.parentalcontrol_util.AppUtils;
import com.think.parentalcontrol_util.DateUtils;

public class RecordingServices extends Service implements OnInfoListener {
	public static final String RECORDING_FILE_PATH = "RECORDING_FILE_PATH";
	public static final String RECORDING_DATE = "RECORDING_DATE";
	public static final String RECORDING_TIME = "RECORDING_TIME";
	public static final String PARENT_NUMBER = "PARENT_NUMBER";
	MediaRecorder recorder = null;
	String sourcePath = "";
	String date = "";
	String time = "";
	String parent_number = "";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		sourcePath = AppUtils.DIR_PATH + File.separator
				+ System.currentTimeMillis() + ".3gp";
		date = DateUtils.getDate();
		time = DateUtils.getTime();
		parent_number = PreferenceManager.getDefaultSharedPreferences(
				getApplicationContext()).getString(AppUtils.PARENT_NUMBER, "");
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setMaxDuration(1000 * 60);
		recorder.setOutputFile(sourcePath);
		recorder.setOnInfoListener(this);
		try {
			recorder.prepare();
			recorder.start();
			Log.d("TAG", "Audio started");
		} catch (Exception e) {

		}
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		// TODO Auto-generated method stub
		if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
			recorder.stop();
			recorder.release();
			recorder = null;
			Log.d("TAG", "Audio Stoped");
			Intent intent = new Intent("com.think.Recorder.FINISH");
			intent.putExtra(RECORDING_FILE_PATH, sourcePath);
			intent.putExtra(RECORDING_DATE, date);
			intent.putExtra(RECORDING_TIME, time);
			intent.putExtra(PARENT_NUMBER, parent_number);
			getApplication().sendBroadcast(intent);
			stopSelf();
		}
	}
}
