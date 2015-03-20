package consumerwsvpro.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;


public class ControllerLogin extends AsyncTask<String, Void, String>{
	private String URL;
	public static AsyncResponse delegate=null;

	@Override
	protected String doInBackground(String... param) {
		URL = param[0];
		return validarLogin(param[1]);
	}
	@Override
	protected void onPostExecute(String result) 
	{
		delegate.processFinish(result);
		//--- Encerra o Dialog---
	}
	public interface AsyncResponse {
		void processFinish(String output);
	}
	
	private String validarLogin(String login) {
		String respStr;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL+login);
		httpget.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(httpget);
			respStr = EntityUtils.toString(resp.getEntity());
		} catch (Exception ex) {

			Log.e("WS", "Error Não pode se conectar! "+ex.getMessage(), ex);
			respStr = ex.getMessage();
		}
		return respStr;
	}
	

}
