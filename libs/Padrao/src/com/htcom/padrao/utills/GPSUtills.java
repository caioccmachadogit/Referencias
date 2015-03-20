package com.htcom.padrao.utills;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class GPSUtills {
	
	public Location getlocation;
	private LocationManager locationManager;
	public String provider;
	
	public LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {

			// CHAMADO QUANDO UM NOVO LOCAL É ENCONTRADO PELO LOCAL DE REDE
			//getlocation = location;
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}
	
	public void VerificarGPS(Context context){
		try {
			// LIGA O GPS CASO ESTEJA DESATIVADO
			if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
				ligaDesligaGPS(context);
			}
		}
		catch (Exception e) {
			Log.e("ERROR LIGARGPS" , e.getMessage());
		}
	}
	
	private void ligaDesligaGPS(Context context) throws Exception{
		try {
			Intent intent = new Intent();
			intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
			intent.setData(Uri.parse("3"));
			context.sendBroadcast(intent);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean RegistrarGPSCriteria() {
		boolean resp = false;
		try {
			Criteria criteria = new Criteria ();
			provider = locationManager.getBestProvider(criteria, false);
			
			Location location = locationManager.getLastKnownLocation(provider);
			if(location != null){
				Log.v("PROVIDER" , " Provider " + provider + " foi selecionado .");
				getlocation = location;
				resp = true;
			}
		}
		catch (Exception e) {
			Log.e("ERROR PROVIDER" , e.getMessage());	
		}
		return resp;
	}

	public void RemoveUpdates(){
		locationManager.removeUpdates(locationListener);
	}
}
