package consumerwsvpro.view;

import com.example.consumerwsvpro.R;


import consumerwsvpro.controller.ControllerStatus;
import consumerwsvpro.controller.ControllerStatus.AsyncResponse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatusActivity extends Activity implements AsyncResponse{
	public TextView status;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		status = (TextView) findViewById(R.id.tv_status);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/getpowerstate/";
		new ControllerStatus(this.findViewById(android.R.id.content).getRootView(), StatusActivity.this).execute(url,"adminsist");
		ControllerStatus.delegate = this;
	}

	@Override
	public void processFinish(String output) {
		status.setText(output);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

}
