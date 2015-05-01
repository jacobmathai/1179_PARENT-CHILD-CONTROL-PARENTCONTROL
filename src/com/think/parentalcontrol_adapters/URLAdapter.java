package com.think.parentalcontrol_adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_models.URLModels;

public class URLAdapter extends ArrayAdapter<URLModels> {

	private Context context;
	private ArrayList<URLModels> list;

	public URLAdapter(Context context, ArrayList<URLModels> list) {
		super(context, R.layout.url_log_row, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.url_log_row, parent, false);
			holder = new ViewHolder();
			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.phoneNumberTextView);
			holder.visitTextView = (TextView) convertView
					.findViewById(R.id.typeTextView);
			holder.dateTextView = (TextView) convertView
					.findViewById(R.id.dateTextView);
			holder.timeTextView = (TextView) convertView
					.findViewById(R.id.timeTextView);
			holder.urlTextView = (TextView) convertView
					.findViewById(R.id.durationTextView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titleTextView.setText(list.get(position).getTitle());
		holder.visitTextView.setText(list.get(position).getVisit_count());
		Log.d("dates", "dates are + " + list.get(position).getDates());
		holder.dateTextView.setText(list.get(position).getDate());
		holder.timeTextView.setText(list.get(position).getTime());
		holder.urlTextView.setText(Html.fromHtml("<html><body><a href="
				+ list.get(position).getUrl() + ">"
				+ list.get(position).getUrl() + "</a></body></html>"));
		return convertView;
	}

	private static class ViewHolder {
		TextView titleTextView;
		TextView visitTextView = null;
		TextView dateTextView = null;
		TextView timeTextView = null;
		TextView urlTextView = null;
	}
}
