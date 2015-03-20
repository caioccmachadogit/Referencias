package com.example.referencias_android.login;

import org.ksoap2.serialization.SoapObject;

import com.htcom.padrao.ws.RequestSoap;

public class WSLogin {
	
	public final String NAMESPACE = "http://tempuri.org/";
	//LOCAWEB
	public final String URL = "http://ws22.hospedagemdesites.ws/tms/wsAPI.asmx?";
	
	public String validarColaborador( String cpf, String IMEI ) throws Exception {
		String retorno;
		try {
			RequestSoap rSoap = new RequestSoap();
			String METHOD_NAME = "validarColaborador";
			String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
			soapObject.addProperty("cpf", cpf);
			soapObject.addProperty("imei", IMEI);
			rSoap.URL = URL;
			retorno = rSoap.conectar(soapObject, SOAP_ACTION).toString();
		}
		catch (Exception e) {
			throw e;
		}
		return retorno; 		
	}

}
