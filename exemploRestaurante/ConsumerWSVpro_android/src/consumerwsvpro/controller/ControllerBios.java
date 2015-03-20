package consumerwsvpro.controller;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import consumerwsvpro.data.Bios;



public class ControllerBios extends AsyncTask<String, Void, Bios>{
	private String URL;
	public static AsyncResponse delegate=null;
	private ProgressDialog pd;
	private Activity activity;
	
	public ControllerBios(View rootView, Activity activity){
		this.activity = activity;
	}
	
	@Override
	protected Bios doInBackground(String... param) {
		URL = param[0];
		return infoBios(param[1]);
	}
	@Override
	protected void onPreExecute() {
		//---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Executando", "Aguarde...");
	}
	@Override
	protected void onPostExecute(Bios result) 
	{
		this.pd.dismiss();
		delegate.processFinish(result);
	}
	public interface AsyncResponse {
		void processFinish(Bios output);
	}
	
	private Bios infoBios(String login) {
		Bios Bios = null;
		String respStr;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL+login);
		httpget.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(httpget);
			respStr = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Bios = gson.fromJson(respStr, Bios.class);
		} catch (Exception ex) {

			Log.e("WS", "Error Não pode se conectar! "+ex.getMessage(), ex);
		}
		return Bios;
	}

}