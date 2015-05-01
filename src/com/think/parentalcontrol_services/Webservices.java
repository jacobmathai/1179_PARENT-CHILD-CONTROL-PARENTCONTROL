package com.think.parentalcontrol_services;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.think.parentalcontrol_util.WebserviceUtil;

public class Webservices {

	String SOAP_ACTION = "";

	private String upload(SoapObject object) {
		// TODO Auto-generated method stub
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(object);
		HttpTransportSE se = new HttpTransportSE(WebserviceUtil.URL);
		try {
			se.call(SOAP_ACTION, envelope);
			SoapPrimitive primitive = (SoapPrimitive) envelope.getResponse();
			return primitive.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WebserviceUtil.ERROR;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WebserviceUtil.ERROR;
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			return WebserviceUtil.ERROR;
		} catch (ClassCastException e) {
			// TODO: handle exception
			e.printStackTrace();
			return WebserviceUtil.ERROR;
		}
	}

	public String getParentNumber(String imei) {
		// TODO Auto-generated method stub
		SOAP_ACTION = WebserviceUtil.NAME_SPACE
				+ WebserviceUtil.GET_PARENT_NUMBER;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.GET_PARENT_NUMBER);
		object.addProperty("imei", imei);
		String rsp = upload(object);
		return rsp;
	}

	public String checkLogin(String username, String password) {
		SOAP_ACTION = WebserviceUtil.NAME_SPACE + WebserviceUtil.CHECK_LOGIN;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.CHECK_LOGIN);
		object.addProperty("username1", username);
		object.addProperty("password", password);
		String rsp = upload(object);
		return rsp;
	}

	public String sendCallInfo(String imei, String jsoneString) {
		// TODO Auto-generated method stub
		SOAP_ACTION = WebserviceUtil.NAME_SPACE + WebserviceUtil.SEND_CALL_INFO;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.SEND_CALL_INFO);
		object.addProperty("imeinumber", imei);
		object.addProperty("jsonValue", jsoneString);
		String rsp = upload(object);
		return rsp;
	}

	public String sendUrlInfo(String imei, String jsone) {
		// TODO Auto-generated method stub
		SOAP_ACTION = WebserviceUtil.NAME_SPACE + WebserviceUtil.SEND_URL_INFO;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.SEND_URL_INFO);
		object.addProperty("imeiNumber", imei);
		object.addProperty("jsonValue", jsone);
		String rsp = upload(object);
		return rsp;
	}

	public String sendSmsInfo(String imei, String jsone) {
		SOAP_ACTION = WebserviceUtil.NAME_SPACE + WebserviceUtil.SEND_SMS_INFO;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.SEND_SMS_INFO);
		object.addProperty("imeiNumber", imei);
		object.addProperty("jsonValue", jsone);
		String rsp = upload(object);
		return rsp;
	}

	public String sendRecordingInfo(String imei, String jsone) {
		SOAP_ACTION = WebserviceUtil.NAME_SPACE
				+ WebserviceUtil.SEND_RECORD_INFO;
		SoapObject object = new SoapObject(WebserviceUtil.NAME_SPACE,
				WebserviceUtil.SEND_RECORD_INFO);
		object.addProperty("imeiNumber", imei);
		object.addProperty("jsonValue", jsone);
		String rsp = upload(object);
		return rsp;
	}
}
