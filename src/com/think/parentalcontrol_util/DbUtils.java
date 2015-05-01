package com.think.parentalcontrol_util;

public interface DbUtils {

	String NAME = "parent_db";
	int VERSION = 1;

	// tables
	String TABLE_CALL_DETAILS = "_call_details_db";
	String TABLE_SMS_DETAILS = "_sms_details_db";
	String TABLE_URL_DETAILS = "_url_details_db";
	String TABLE_RECORDING_DETAILS = "_recording_details_db";

	// call table columns
	String ID_CALL = "_id_call";
	String NUMBER_CALL = "_number_call";
	String TYPE_CALL = "_type_call";
	String DATE_CALL = "_date_call";
	String TIME_CALL = "_time_call";
	String DURATION_CALL = "_duration_call";

	// sms table columns
	String ID_SMS = "_id_sms";
	String TIME_SMS = "_time_sms";
	String DATE_SMS = "_date_sms";
	String CONTENT_SMS = "_content_sms";
	String STATUS_SMS = "_status_sms";
	String NUMBER_SMS = "_number_sms";
	String TYPE_SMS = "_type_sms";

	// url table columns
	String ID_URL = "_id_url";
	String TITLE_URL = "_title_url";
	String DATE_URL = "_date_url";
	String TIME_URL = "_time_url";
	String URL_FROM_URL = "_url_url";
	String STATUS_URL = "_status_url";
	String VISIT_COUNT_URL = "_visit_count_url";

	// recording table details
	String ID_REC = "_id_rec";
	String NUMBER_REC = "_number_rec";
	String FILE_PATH_REC = "_file_path_rec";
	String LATITUDE_REC = "_latitude_rec";
	String LONGITUDE_REC = "_longitude_rec";
	String DATE_REC = "_date_rec";
	String TIME_REC = "_time_rec";

	// db status
	String PENDING = "PENDING";

}
