package com.think.parentalcontrol_db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.think.parentalcontrol_adapters.RecordingInfoModels;
import com.think.parentalcontrol_models.CallDetailsModel;
import com.think.parentalcontrol_models.SmsModel;
import com.think.parentalcontrol_models.URLModels;
import com.think.parentalcontrol_util.DbUtils;

public class DbManager extends SQLiteOpenHelper {

	public DbManager(Context context) {
		super(context, DbUtils.NAME, null, DbUtils.VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_CALL_DB = "CREATE TABLE " + DbUtils.TABLE_CALL_DETAILS
				+ "(" + DbUtils.ID_CALL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DbUtils.NUMBER_CALL + " VARCHAR," + DbUtils.TYPE_CALL
				+ " VARCHAR," + DbUtils.DATE_CALL + " VARCHAR,"
				+ DbUtils.TIME_CALL + " VARCHAR," + DbUtils.DURATION_CALL
				+ " VARCHAR)";

		String CREATE_SMS_TABLE = "CREATE TABLE " + DbUtils.TABLE_SMS_DETAILS
				+ "(" + DbUtils.ID_SMS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DbUtils.TIME_SMS + " VARCHAR," + DbUtils.DATE_SMS
				+ " VARCHAR," + DbUtils.CONTENT_SMS + " VARCHAR, "
				+ DbUtils.STATUS_SMS + " VARCHAR," + DbUtils.NUMBER_SMS
				+ " VARCHAR," + DbUtils.TYPE_SMS + " VARCHAR)";

		String CREATE_URL_TABLE = "CREATE TABLE " + DbUtils.TABLE_URL_DETAILS
				+ "(" + DbUtils.ID_URL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DbUtils.TITLE_URL + " VARCHAR," + DbUtils.DATE_URL
				+ " DATE," + DbUtils.TIME_URL + " VARCHAR,"
				+ DbUtils.URL_FROM_URL + " VARCHAR," + DbUtils.STATUS_URL
				+ " VARCHAR," + DbUtils.VISIT_COUNT_URL + " VARCHAR)";

		String CREATE_RECORDING_TABLE = "CREATE TABLE "
				+ DbUtils.TABLE_RECORDING_DETAILS + "(" + DbUtils.ID_REC
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DbUtils.NUMBER_REC
				+ " VARCHAR," + DbUtils.FILE_PATH_REC + " VARCHAR,"
				+ DbUtils.LATITUDE_REC + " VARCHAR," + DbUtils.LONGITUDE_REC
				+ " VARCHAR," + DbUtils.DATE_REC + " VARCHAR,"
				+ DbUtils.TIME_REC + " VARCHAR)";

		db.execSQL(CREATE_CALL_DB);
		db.execSQL(CREATE_SMS_TABLE);
		db.execSQL(CREATE_URL_TABLE);
		db.execSQL(CREATE_RECORDING_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DbUtils.TABLE_CALL_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + DbUtils.TABLE_SMS_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + DbUtils.TABLE_URL_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + DbUtils.TABLE_RECORDING_DETAILS);

	}

	public boolean insertCallDetails(CallDetailsModel model) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbUtils.NUMBER_CALL, model.getNumber());
		values.put(DbUtils.TYPE_CALL, model.getType());
		values.put(DbUtils.DATE_CALL, model.getDate());
		values.put(DbUtils.TIME_CALL, model.getTime());
		values.put(DbUtils.DURATION_CALL, model.getDuration());
		long insert = database.insert(DbUtils.TABLE_CALL_DETAILS, null, values);
		database.close();
		if (insert > 0) {
			return true;
		}
		return false;
	}

	public boolean insertSMSDetails(SmsModel model) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbUtils.TIME_SMS, model.getTime());
		values.put(DbUtils.DATE_SMS, model.getDate());
		values.put(DbUtils.CONTENT_SMS, model.getContent());
		values.put(DbUtils.STATUS_SMS, model.getStatus());
		values.put(DbUtils.NUMBER_SMS, model.getPhoneNumber());
		values.put(DbUtils.TYPE_SMS, model.getType());
		long insert = database.insert(DbUtils.TABLE_SMS_DETAILS, null, values);
		database.close();
		if (insert > 0) {
			return true;
		}
		return false;
	}

	public ArrayList<String> getTimeFromMessages() {
		// TODO Auto-generated method stub
		ArrayList<String> arrayList = null;
		SQLiteDatabase database = getReadableDatabase();
		String sql = "SELECT " + DbUtils.TIME_SMS + " FROM "
				+ DbUtils.TABLE_SMS_DETAILS;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			arrayList = new ArrayList<String>();
			while (cursor.moveToNext()) {
				arrayList.add(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_SMS)));
			}
			cursor.close();
			database.close();
		}
		return arrayList;
	}

	public ArrayList<String> getTimeFromUrl() {
		// TODO Auto-generated method stub
		ArrayList<String> list = null;
		SQLiteDatabase database = getReadableDatabase();
		String sql = "SELECT " + DbUtils.TIME_URL + " FROM "
				+ DbUtils.TABLE_URL_DETAILS + " ORDER BY " + DbUtils.DATE_URL + " DESC";

		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<String>();
			while (cursor.moveToNext()) {
				list.add(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_URL)));
			}
			cursor.close();
			database.close();
		}
		return list;
	}

	public boolean insertURL(URLModels urlModels) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbUtils.TITLE_URL, urlModels.getTitle());
		values.put(DbUtils.DATE_URL, urlModels.getDate());
		values.put(DbUtils.TIME_URL, urlModels.getTime());
		values.put(DbUtils.URL_FROM_URL, urlModels.getUrl());
		values.put(DbUtils.STATUS_URL, urlModels.getStatus());
		values.put(DbUtils.VISIT_COUNT_URL, urlModels.getVisit_count());
		long insert = database.insert(DbUtils.TABLE_URL_DETAILS, null, values);
		database.close();
		if (insert > 0) {
			return true;
		}
		return false;
	}

	public ArrayList<CallDetailsModel> getAllCallDetails(String type) {
		// TODO Auto-generated method stub
		String sql = "";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = null;
		if (type.equals("All")) {
			sql = "SELECT * FROM " + DbUtils.TABLE_CALL_DETAILS;
			cursor = database.rawQuery(sql, null);
		} else {
			sql = "SELECT * FROM " + DbUtils.TABLE_CALL_DETAILS + " WHERE "
					+ DbUtils.TYPE_CALL + "=?";
			cursor = database.rawQuery(sql, new String[] { type });
		}
		ArrayList<CallDetailsModel> list = null;

		if (cursor != null) {
			list = new ArrayList<CallDetailsModel>();
			while (cursor.moveToNext()) {
				CallDetailsModel model = new CallDetailsModel();

				model.setId(cursor.getString(cursor
						.getColumnIndex(DbUtils.ID_CALL)));
				model.setNumber(cursor.getString(cursor
						.getColumnIndex(DbUtils.NUMBER_CALL)));
				model.setType(cursor.getString(cursor
						.getColumnIndex(DbUtils.TYPE_CALL)));
				model.setDate(cursor.getString(cursor
						.getColumnIndex(DbUtils.DATE_CALL)));
				model.setTime(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_CALL)));
				model.setDuration(cursor.getString(cursor
						.getColumnIndex(DbUtils.DURATION_CALL)));
				list.add(model);
			}

			cursor.close();
			database.close();
		}
		return list;
	}

	public ArrayList<String> getTimeFromCall() {
		// TODO Auto-generated method stub
		ArrayList<String> list = null;
		SQLiteDatabase database = getReadableDatabase();
		String sql = "SELECT " + DbUtils.TIME_CALL + " FROM "
				+ DbUtils.TABLE_CALL_DETAILS;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<String>();
			while (cursor.moveToNext()) {
				list.add(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_CALL)));
			}
			cursor.close();
			database.close();
		}
		return list;
	}

	public ArrayList<SmsModel> getAllSMSDetails(String type) {
		// TODO Auto-generated method stub
		String sql = "";
		Cursor cursor = null;
		ArrayList<SmsModel> list = null;
		SQLiteDatabase database = getReadableDatabase();
		if (type.equals("All")) {
			sql = "SELECT * FROM " + DbUtils.TABLE_SMS_DETAILS;
			cursor = database.rawQuery(sql, null);
		} else {
			sql = "SELECT * FROM " + DbUtils.TABLE_SMS_DETAILS + " WHERE "
					+ DbUtils.TYPE_SMS + "=?";
			cursor = database.rawQuery(sql, new String[] { type });
		}

		if (cursor != null) {
			list = new ArrayList<SmsModel>();
			while (cursor.moveToNext()) {
				SmsModel model = new SmsModel();
				model.setId(cursor.getString(cursor
						.getColumnIndex(DbUtils.ID_SMS)));
				model.setPhoneNumber(cursor.getString(cursor
						.getColumnIndex(DbUtils.NUMBER_SMS)));
				model.setType(cursor.getString(cursor
						.getColumnIndex(DbUtils.TYPE_SMS)));
				model.setDate(cursor.getString(cursor
						.getColumnIndex(DbUtils.DATE_SMS)));
				model.setTime(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_SMS)));
				model.setContent(cursor.getString(cursor
						.getColumnIndex(DbUtils.CONTENT_SMS)));
				list.add(model);
			}
			cursor.close();
			database.close();
		}
		return list;
	}

	public ArrayList<URLModels> getAllURL() {
		ArrayList<URLModels> list = null;
		SQLiteDatabase database = getReadableDatabase();
		String sql = "SELECT * FROM " + DbUtils.TABLE_URL_DETAILS;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<URLModels>();
			while (cursor.moveToNext()) {
				URLModels models = new URLModels();
				models.setId(cursor.getString(cursor
						.getColumnIndex(DbUtils.ID_URL)));
				models.setTitle(cursor.getString(cursor
						.getColumnIndex(DbUtils.TITLE_URL)));
				models.setDate(cursor.getString(cursor
						.getColumnIndex(DbUtils.DATE_URL)));
				models.setTime(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_URL)));
				models.setVisit_count(cursor.getString(cursor
						.getColumnIndex(DbUtils.VISIT_COUNT_URL)));
				models.setUrl(cursor.getString(cursor
						.getColumnIndex(DbUtils.URL_FROM_URL)));
				list.add(models);
			}
			cursor.close();
			database.close();
		}
		return list;
	}

	public boolean insertRecordingDetails(RecordingInfoModels infoModels) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbUtils.NUMBER_REC, infoModels.getNumber());
		values.put(DbUtils.FILE_PATH_REC, infoModels.getFilePath());
		values.put(DbUtils.LATITUDE_REC, infoModels.getLatitude());
		values.put(DbUtils.LONGITUDE_REC, infoModels.getLongitude());
		values.put(DbUtils.DATE_REC, infoModels.getDate());
		values.put(DbUtils.TIME_REC, infoModels.getTime());
		long insert = database.insert(DbUtils.TABLE_RECORDING_DETAILS, null,
				values);
		database.close();
		if (insert > 0) {
			return true;
		}
		return false;
	}

	public ArrayList<RecordingInfoModels> getAllRecrdingInfo() {
		// TODO Auto-generated method stub
		ArrayList<RecordingInfoModels> list = null;
		SQLiteDatabase database = getReadableDatabase();
		String sql = "SELECT * FROM " + DbUtils.TABLE_RECORDING_DETAILS;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<RecordingInfoModels>();
			while (cursor.moveToNext()) {
				RecordingInfoModels models = new RecordingInfoModels();
				models.setId(cursor.getString(cursor
						.getColumnIndex(DbUtils.ID_REC)));
				models.setNumber(cursor.getString(cursor
						.getColumnIndex(DbUtils.NUMBER_REC)));
				models.setFilePath(cursor.getString(cursor
						.getColumnIndex(DbUtils.FILE_PATH_REC)));
				models.setLatitude(cursor.getString(cursor
						.getColumnIndex(DbUtils.LATITUDE_REC)));
				models.setLongitude(cursor.getString(cursor
						.getColumnIndex(DbUtils.LONGITUDE_REC)));
				models.setDate(cursor.getString(cursor
						.getColumnIndex(DbUtils.DATE_REC)));
				models.setTime(cursor.getString(cursor
						.getColumnIndex(DbUtils.TIME_REC)));
				list.add(models);
			}
			cursor.close();
			database.close();
		}
		return list;
	}

	public boolean deleteCallInfo() {
		// TODO Auto-generated method stub
		SQLiteDatabase database = getWritableDatabase();
		int delete = database.delete(DbUtils.TABLE_CALL_DETAILS, null, null);
		database.close();
		if (delete > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteUrlInfo() {
		SQLiteDatabase database = getWritableDatabase();
		int delete = database.delete(DbUtils.TABLE_URL_DETAILS, null, null);
		database.close();
		if (delete > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteSmsInfo() {
		SQLiteDatabase database = getWritableDatabase();
		int delete = database.delete(DbUtils.TABLE_SMS_DETAILS, null, null);
		database.close();
		if (delete > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteRecordingInfo() {
		SQLiteDatabase database = getWritableDatabase();
		int delete = database.delete(DbUtils.TABLE_RECORDING_DETAILS, null,
				null);
		database.close();
		if (delete > 0) {
			return true;
		}
		return false;
	}

}
