package consumerwsvpro.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerBios;
import consumerwsvpro.controller.ControllerBios.AsyncResponse;
import consumerwsvpro.data.Bios;

public class BiosActivity extends Activity implements AsyncResponse{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_infobios);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getinfobios/";
		new ControllerBios(this.findViewById(android.R.id.content).getRootView(), BiosActivity.this).execute(url,"adminsist");
		ControllerBios.delegate = this;
	}

	@Override
	public void processFinish(Bios output) {
		ArrayList<Bios> Bioss = new ArrayList<Bios>();
		Bioss.add(output);
		ListView lv = (ListView) findViewById(R.id.lv_infobios);
		BiosAdapter pAdapter = new BiosAdapter(this, Bioss);
		lv.setAdapter(pAdapter);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}

