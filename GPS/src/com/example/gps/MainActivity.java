package com.example.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void posicaoUnica(View v){
		Intent i = new Intent(this , PosicaoUnicaActivity.class);
		startActivity(i);
	}
	
	public void posicaoPeriodica(View v){
		Intent i = new Intent(this , PosicaoPeriodicaActivity.class);
		startActivity(i);
	}
	
	
	public void providers(View v){
		//pegando o objeto location manager para fazer solicitação das coordenadas
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
				
		
				
		for(String provider : locationManager.getAllProviders()){
			Toast.makeText(getApplicationContext(), provider, Toast.LENGTH_LONG).show();
		}
	}

}
