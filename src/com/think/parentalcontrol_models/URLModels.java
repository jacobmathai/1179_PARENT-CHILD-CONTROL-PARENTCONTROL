package com.think.parentalcontrol_models;

import java.util.Date;

public class URLModels {
	String id = "";
	String title = "";
	String url = "";
	String date = "";
	String time = "";
	String status = "";
	String visit_count = "";
	Date dates;

	public Date getDates() {
		return dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

	public String getVisit_count() {
		return visit_count;
	}

	public void setVisit_count(String visit_count) {
		this.visit_count = visit_count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
