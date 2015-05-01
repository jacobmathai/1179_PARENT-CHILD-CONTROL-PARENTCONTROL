package com.think.parentalcontrol_adapters;

import java.util.ArrayList;

import com.think.parentalcontrol.R;
import com.think.parentalcontrol_models.CallDetailsModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CallLogAdapter extends ArrayAdapter<CallDetailsModel> {

	private Context context;
	private ArrayList<CallDetailsModel> list;

	public CallLogAdapter(Context context, ArrayList<CallDetailsModel> list) {
		super(context, R.layout.call_log_row, list);
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
			convertView = inflater
					.inflate(R.layout.call_log_row, parent, false);
			holder = new ViewHolder();
			holder.phoneNumberTextView = (TextView) convertView
					.findViewById(R.id.phoneNumberTextView);
			holder.typeTextView = (TextView) convertView
					.findViewById(R.id.typeTextView);
			holder.dateTextView = (TextView) convertView
					.findViewById(R.id.dateTextView);
			holder.timeTextView = (TextView) convertView
					.findViewById(R.id.timeTextView);
			holder.durationTextView = (TextView) convertView
					.findViewById(R.id.durationTextView);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.phoneNumberTextView.setText(list.get(position).getNumber());
		holder.typeTextView.setText(list.get(position).getType());
		holder.dateTextView.setText(list.get(position).getDate());
		holder.timeTextView.setText(list.get(position).getTime());
		holder.durationTextView.setText(list.get(position).getDuration());
		return convertView;
	}

	private static class ViewHolder {
		TextView phoneNumberTextView;
		TextView typeTextView = null;
		TextView dateTextView = null;
		TextView timeTextView = null;
		TextView durationTextView = null;
	}
}
