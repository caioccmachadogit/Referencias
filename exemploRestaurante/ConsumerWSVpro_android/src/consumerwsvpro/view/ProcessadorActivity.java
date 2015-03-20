package consumerwsvpro.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerProcessador;
import consumerwsvpro.controller.ControllerProcessador.AsyncResponse;
import consumerwsvpro.data.Processador;



public class ProcessadorActivity extends Activity implements AsyncResponse{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_infoprocessador);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getinfoprocessador/";
		new ControllerProcessador(this.findViewById(android.R.id.content).getRootView(), ProcessadorActivity.this).execute(url,"adminsist");
		ControllerProcessador.delegate = this;
	}

	@Override
	public void processFinish(Processador output) {
		ArrayList<Processador> processadors = new ArrayList<Processador>();
		processadors.add(output);
		ListView lv = (ListView) findViewById(R.id.lv_infoprocessador);
		ProcessadorAdapter pAdapter = new ProcessadorAdapter(this, processadors);
		lv.setAdapter(pAdapter);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}
