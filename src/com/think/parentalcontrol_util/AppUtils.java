package com.think.parentalcontrol_util;

import java.io.File;

import android.os.Environment;

public interface AppUtils {

	String DIR_PATH = Environment.getExternalStorageDirectory().getPath()
			+ File.separator + "PControl";
	String LATITUDE = "LATITUDE";
	String LONGITUDE = "LONGITUDE";
	String PARENT_NUMBER = "PARENT_NUMBER";
	String USERNAME = "USERNAME";
	String PASSWORD = "PASSWORD";
	String IMEI = "IMEI";
	String APP_LAUNCHER_NUMBER = "*#*1212*#*";
	String TEXT_MESSAGE = "text_message";
}
