package com.htcom.padrao.ws;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

public class RequestSoap {
	
	public String URL;
	
	public Object conectar(SoapObject soapObject, String soapAction)
			throws Exception {

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(soapObject);
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		HttpTransportSE httpTransport = new HttpTransportSE(URL);
		Object response = null;
		try {
			httpTransport.call(soapAction, envelope);
			response = envelope.getResponse();
		} catch (Exception exception) {
			Log.e("ERROR REQUESTSOAP", exception.getMessage());
			response = "x";
		}
		return response;
	}
	
	public class MarshalDouble implements Marshal 
	{


	    public Object readInstance(XmlPullParser parser, String namespace, String name, 
	            PropertyInfo expected) throws IOException, XmlPullParserException {
	        
	        return Double.parseDouble(parser.nextText());
	    }


	    public void register(SoapSerializationEnvelope cm) {
	         cm.addMapping(cm.xsd, "double", Double.class, this);
	        
	    }


	    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
	           writer.text(obj.toString());
	        }
	    
	}

}
