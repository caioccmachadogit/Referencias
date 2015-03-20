package consumerwsvpro.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import consumerwsvpro.data.Disco;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class ControllerDisco extends AsyncTask<String, Void, ArrayList<Disco>>{
	private String URL;
	public static AsyncResponse delegate=null;
	private ProgressDialog pd;
	private Activity activity;
	
	public ControllerDisco(View rootView, Activity activity){
		this.activity = activity;
	}
	
	@Override
	protected ArrayList<Disco> doInBackground(String... param) {
		URL = param[0];
		return infoDisco(param[1]);
	}
	@Override
	protected void onPreExecute() {
		//---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Executando", "Aguarde...");
	}
	@Override
	protected void onPostExecute(ArrayList<Disco> result) 
	{
		this.pd.dismiss();
		delegate.processFinish(result);
	}
	public interface AsyncResponse {
		void processFinish(ArrayList<Disco> output);
	}
	
	private ArrayList<Disco> infoDisco(String login) {
		ArrayList<Disco> discos = null;
		String respStr;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL+login);
		httpget.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(httpget);
			respStr = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type newType = new TypeToken<ArrayList<Disco>>(){}.getType();
			discos = gson.fromJson(respStr, newType);
		} catch (Exception ex) {

			Log.e("WS", "Error Não pode se conectar! "+ex.getMessage(), ex);
		}
		return discos;
	}

}
