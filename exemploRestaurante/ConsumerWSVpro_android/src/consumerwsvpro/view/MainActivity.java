package consumerwsvpro.view;

import com.example.consumerwsvpro.R;

import consumerwsvpro.controller.ControllerLogin;
import consumerwsvpro.controller.ControllerLogin.AsyncResponse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements AsyncResponse{
	public EditText login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		login = (EditText) findViewById(R.id.login);
	}

	public void btn_envocar_login(View v){
		if (!login.getText().toString().isEmpty()) {
			String url = "http://192.168.0.8:8080/WebServicesVpro/webresources/wsvpro/login/";
			new ControllerLogin().execute(url,login.getText().toString());
			ControllerLogin.delegate = this;
		}
	}	

	@Override
	public void processFinish(String output) {
		if(output.equals("SUCCESS")){
			startActivity(new Intent(this, MenuActivity.class));
			finish();
		}
		else {
			Toast msn = Toast.makeText(getApplicationContext(), "INSUCCESS - "+output, Toast.LENGTH_SHORT);
			msn.show();
		}
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
