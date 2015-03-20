package consumerwsvpro.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerMaquina;
import consumerwsvpro.controller.ControllerMaquina.AsyncResponse;
import consumerwsvpro.data.Maquina;

public class MaquinaActivity extends Activity implements AsyncResponse{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_infomaquina);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getinfomaquina/";
		new ControllerMaquina(this.findViewById(android.R.id.content).getRootView(), MaquinaActivity.this).execute(url,"adminsist");
		ControllerMaquina.delegate = this;
	}

	@Override
	public void processFinish(Maquina output) {
		ArrayList<Maquina> Maquinas = new ArrayList<Maquina>();
		Maquinas.add(output);
		ListView lv = (ListView) findViewById(R.id.lv_infomaquina);
		MaquinaAdapter pAdapter = new MaquinaAdapter(this, Maquinas);
		lv.setAdapter(pAdapter);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}


