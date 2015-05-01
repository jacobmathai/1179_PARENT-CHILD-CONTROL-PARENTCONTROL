package com.think.parentalcontrol_observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.provider.Browser;
import android.util.Log;

import com.think.parentalcontrol_db.DbManager;
import com.think.parentalcontrol_models.URLModels;
import com.think.parentalcontrol_util.DateUtils;
import com.think.parentalcontrol_util.DbUtils;

public class BrowserHistoryObserver {

	private Context context;
	DbManager dbManager = null;

	public BrowserHistoryObserver(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		dbManager = new DbManager(context);

	}

	public ArrayList<URLModels> getBrowserHistory() {
		Date dates = null;
		ArrayList<URLModels> list = new ArrayList<URLModels>();
		String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history,
																// 1 = bookmark
		Cursor mCur = context.getContentResolver().query(Browser.BOOKMARKS_URI,
				Browser.HISTORY_PROJECTION, sel, null, null);
		if (mCur.moveToFirst()) {
			while (mCur.isAfterLast() == false) {

				String title = mCur.getString(mCur
						.getColumnIndex(Browser.BookmarkColumns.TITLE));
				String url = mCur.getString(mCur
						.getColumnIndex(Browser.BookmarkColumns.URL));
				String date = mCur.getString(mCur
						.getColumnIndex(Browser.BookmarkColumns.DATE));
				String visitCount = mCur.getString(mCur
						.getColumnIndex(Browser.BookmarkColumns.VISITS));

				String dateFormillies = DateUtils.getDateFormillies(Long
						.parseLong(date));
				
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					dates = format.parse(dateFormillies);
					Log.d("dates", "date " + dates);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String timeFormillies = DateUtils.getTimeFormillies(Long
						.parseLong(date));

				ArrayList<String> arrayList = dbManager.getTimeFromUrl();
				Log.d("TAG", "TOATAL LIST COUNT : " + arrayList.size());
				if (arrayList.size() > 0) {
					int flag = 0;
					for (int i = 0; i < arrayList.size(); i++) {
						if (arrayList.get(i).equals(timeFormillies)) {
							flag = 1;
						}
					}

					if (flag != 1) {
						URLModels entry = new URLModels();
						entry.setTitle(title);
						entry.setUrl(url);
						entry.setDate(dateFormillies);
						Log.d("dates", "one111 " + dates);
						entry.setTime(timeFormillies);
						entry.setVisit_count(visitCount);
						entry.setStatus(DbUtils.PENDING);
						list.add(entry);
					}
				} else {
					URLModels entry = new URLModels();
					entry.setTitle(title);
					entry.setUrl(url);
					Log.d("dates", "one222 " + dates);
					entry.setDate(dateFormillies);
					entry.setTime(timeFormillies);
					entry.setVisit_count(visitCount);
					entry.setStatus(DbUtils.PENDING);
					list.add(entry);

				}
				mCur.moveToNext();
			}
		}

		mCur.close();
		Log.d("TAG", "LSIT count : " + list.size());
		return list;
	}
}
