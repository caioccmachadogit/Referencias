package com.htcom.padrao.funcoes;

import java.util.Calendar;

import android.content.Context;
import android.os.Vibrator;

public class Variedades {

	public void vibrar(Context context){
        Vibrator rr = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 30; 
        rr.vibrate(milliseconds); 
    }
	
	public String getDateNow(){
		String data = null;
		Calendar calendario = Calendar.getInstance();
		data = Integer.toString(calendario.get(Calendar.YEAR))  + "-";
		if ( Integer.toString(calendario.get(Calendar.MONTH) + 1).length() == 1 ){
			data = data +  "0" + Integer.toString(calendario.get(Calendar.MONTH) + 1) + "-";
		} else {
			data = data + Integer.toString(calendario.get(Calendar.MONTH) + 1) + "-";
		}
		if ( Integer.toString(calendario.get(Calendar.DATE)).length() == 1 ){
			data = data + "0" + Integer.toString(calendario.get(Calendar.DATE));
		} else {
			data = data + Integer.toString(calendario.get(Calendar.DATE));
		}	
		return data;
	}
}
