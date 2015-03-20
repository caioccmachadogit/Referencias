package consumerwsvpro.view;

import com.example.consumerwsvpro.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import consumerwsvpro.controller.ControllerTrocaStatus;
import consumerwsvpro.controller.ControllerTrocaStatus.AsyncResponse;

public class DesligarActivity extends Activity implements AsyncResponse{
	public TextView desligar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desligar);
		desligar = (TextView) findViewById(R.id.tv_desligar);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/changepowerstate/adminsist/8";
		new ControllerTrocaStatus(this.findViewById(android.R.id.content).getRootView(), DesligarActivity.this).execute(url,"adminsist");
		ControllerTrocaStatus.delegate = this;
	}

	@Override
	public void processFinish(String output) {
		desligar.setText(output);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}
