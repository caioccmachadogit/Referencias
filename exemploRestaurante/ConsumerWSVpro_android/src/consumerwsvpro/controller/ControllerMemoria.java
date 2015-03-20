package consumerwsvpro.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
import com.google.gson.reflect.TypeToken;

import consumerwsvpro.data.Memoria;

public class ControllerMemoria extends AsyncTask<String, Void, ArrayList<Memoria>>{
	private String URL;
	public static AsyncResponse delegate=null;
	private ProgressDialog pd;
	private Activity activity;
	
	public ControllerMemoria(View rootView, Activity activity){
		this.activity = activity;
	}
	
	@Override
	protected ArrayList<Memoria> doInBackground(String... param) {
		URL = param[0];
		return infoMemoria(param[1]);
	}
	@Override
	protected void onPreExecute() {
		//---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Executando", "Aguarde...");
	}
	@Override
	protected void onPostExecute(ArrayList<Memoria> result) 
	{
		this.pd.dismiss();
		delegate.processFinish(result);
	}
	public interface AsyncResponse {
		void processFinish(ArrayList<Memoria> output);
	}
	
	private ArrayList<Memoria> infoMemoria(String login) {
		ArrayList<Memoria> memoria = null;
		String respStr;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL+login);
		httpget.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(httpget);
			respStr = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type newType = new TypeToken<ArrayList<Memoria>>(){}.getType();
			memoria = gson.fromJson(respStr, newType);
		} catch (Exception ex) {

			Log.e("WS", "Error Não pode se conectar! "+ex.getMessage(), ex);
		}
		return memoria;
	}

}