package consumerwsvpro.view;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerTrocaStatus;
import consumerwsvpro.controller.ControllerTrocaStatus.AsyncResponse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class LigarActivity extends Activity implements AsyncResponse{
	public TextView ligar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ligar);
		ligar = (TextView) findViewById(R.id.tv_ligar);
		String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/changepowerstate/adminsist/2";
		new ControllerTrocaStatus(this.findViewById(android.R.id.content).getRootView(), LigarActivity.this).execute(url,"adminsist");
		ControllerTrocaStatus.delegate = this;
	}

	@Override
	public void processFinish(String output) {
		ligar.setText(output);
	}
	
	public void btn_close(View v){
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}
}
