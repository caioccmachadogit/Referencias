package com.example.gps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PosicaoPeriodicaActivity extends Activity {
	
	List<Location> locs = new ArrayList<Location>();
	LocationListener listener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posicao_periodica);
		
		
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		
		
		long tempo = 500 ; //5 minutos
		float distancia = 30; // 30 metros
		
		this.listener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_LONG).show();				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				Toast.makeText(getApplicationContext(), "Provider Habilitado", Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				Toast.makeText(getApplicationContext(), "Provider Desabilitado", Toast.LENGTH_LONG).show();				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				TextView numero = (TextView) findViewById( R.id.numero);
				TextView latitude = (TextView) findViewById( R.id.latitude);
				TextView longitude = (TextView) findViewById( R.id.longitude);
				TextView time = (TextView) findViewById( R.id.time);
				TextView acuracy = (TextView) findViewById( R.id.Acuracy);
				
				if( location != null ){
					locs.add(location);
					numero.setText( "Número de posições travadas: "+locs.size() );
					latitude.setText( "Latitude: "+location.getLatitude() );
					longitude.setText( "Longitude: "+location.getLongitude() );
					acuracy.setText( "Precisão: "+location.getAccuracy()+"" );
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					time.setText( "Data:"+sdf.format( location.getTime() ) );
					
				}
				
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , tempo , distancia, this.listener , null );
		
	}
	
	public void parar(View v){
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		
		locationManager.removeUpdates( this.listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.posicao_periodica, menu);
		return true;
	}

}
