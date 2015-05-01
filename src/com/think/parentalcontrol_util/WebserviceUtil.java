package com.think.parentalcontrol_util;

public interface WebserviceUtil {

	String GET_PARENT_NUMBER = "retreiveParentNumber";
	String IP = "172.30.14.125";
	String NAME_SPACE = "http://webservices.smartparenting.com/";
	String URL = "http://" + IP
			+ ":8084/Parental_Control/SmartParentingWebService?wsdl";
	String ERROR = "error";
	String CHECK_LOGIN = "loginCheck";
	String TRUE = "true";
	String SEND_CALL_INFO = "addCallInfo";
	String SEND_URL_INFO = "addUrl";
	String SEND_SMS_INFO = "addSmsInfo";
	String SEND_RECORD_INFO = "addRecordingInfo";

}
