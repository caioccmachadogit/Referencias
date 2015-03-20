package consumerwsvpro.view;

import java.util.ArrayList;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerMemoria;
import consumerwsvpro.controller.ControllerMemoria.AsyncResponse;
import consumerwsvpro.data.Memoria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MemoriaActivity extends Activity implements AsyncResponse{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_infomemoria);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getinfomemoria/";
		new ControllerMemoria(this.findViewById(android.R.id.content).getRootView(), MemoriaActivity.this).execute(url,"adminsist");
		ControllerMemoria.delegate = this;
	}

	@Override
	public void processFinish(ArrayList<Memoria> output) {
		ListView lv = (ListView) findViewById(R.id.lv_infomemoria);
		MemoriaAdapter dAdapter = new MemoriaAdapter(this, output);
		lv.setAdapter(dAdapter);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}