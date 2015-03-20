package consumerwsvpro.view;

import java.util.ArrayList;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerDisco;
import consumerwsvpro.controller.ControllerDisco.AsyncResponse;
import consumerwsvpro.data.Disco;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class DiscoActivity extends Activity implements AsyncResponse{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_infodisco);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getinfodisco/";
		new ControllerDisco(this.findViewById(android.R.id.content).getRootView(), DiscoActivity.this).execute(url,"adminsist");
		ControllerDisco.delegate = this;
	}

	@Override
	public void processFinish(ArrayList<Disco> output) {
		ListView lv = (ListView) findViewById(R.id.lv_infodisco);
		DiscoAdapter dAdapter = new DiscoAdapter(this, output);
		lv.setAdapter(dAdapter);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}
