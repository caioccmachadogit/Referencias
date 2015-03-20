package com.htcom.padrao.funcoes;

import com.htcom.padrao.interfaces.IActivity;

import android.content.Context;
import android.content.Intent;

public class IntentHelper {

	// Sem PutExtras
		public void mudarTela(Context context, Class<IActivity> activityDestino ) {
			Intent intent = new Intent();
			intent.setClass(context, activityDestino);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);			
		}
		
	// PutExtras int int;
	public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, int putExtraValue0, 
			String putExtra1, int putExtraValue1) {
		Intent intent = new Intent();
		intent.setClass(context, activityDestino);
		intent.putExtra(putExtra0, putExtraValue0);
		intent.putExtra(putExtra1, putExtraValue1);;
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	// PutExtras String, int int;
	public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, String putExtraValue0, 
			 String putExtra1, int putExtraValue1,
			 String putExtra2, int putExtraValue2) {
		Intent intent = new Intent();
		intent.setClass(context, activityDestino);
		intent.putExtra(putExtra0, putExtraValue0);
		intent.putExtra(putExtra1, putExtraValue1);
		intent.putExtra(putExtra2, putExtraValue2);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	// PutExtras int int, int;
	public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, int putExtraValue0, 
			 																 String putExtra1, int putExtraValue1,
			 																 String putExtra2, int putExtraValue2) {
		Intent intent = new Intent();
		intent.setClass(context, activityDestino);
		intent.putExtra(putExtra0, putExtraValue0);
		intent.putExtra(putExtra1, putExtraValue1);
		intent.putExtra(putExtra2, putExtraValue2);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	// PutExtras int int, int, String;
		public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, int putExtraValue0, 
				 																 String putExtra1, int putExtraValue1,
				 																 String putExtra2, int putExtraValue2,
				 																 String putExtra3, String putExtraValue3) {
			Intent intent = new Intent();
			intent.setClass(context, activityDestino);
			intent.putExtra(putExtra0, putExtraValue0);
			intent.putExtra(putExtra1, putExtraValue1);
			intent.putExtra(putExtra2, putExtraValue2);
			intent.putExtra(putExtra3, putExtraValue3);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		}
		
	// PutExtras int, int, int, String, int;
			public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, int putExtraValue0, 
					 																 String putExtra1, int putExtraValue1,
					 																 String putExtra2, int putExtraValue2,
					 																 String putExtra3, String putExtraValue3,
					 																 String putExtra4, int putExtraValue4) {
				Intent intent = new Intent();
				intent.setClass(context, activityDestino);
				intent.putExtra(putExtra0, putExtraValue0);
				intent.putExtra(putExtra1, putExtraValue1);
				intent.putExtra(putExtra2, putExtraValue2);
				intent.putExtra(putExtra3, putExtraValue3);
				intent.putExtra(putExtra4, putExtraValue4);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
	
	// PutExtras String, int;
	public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, String putExtraValue0, 
			String putExtra1, int putExtraValue1) {
		Intent intent = new Intent();
		intent.setClass(context, activityDestino);
		intent.putExtra(putExtra0, putExtraValue0);
		intent.putExtra(putExtra1, putExtraValue1);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	// PutExtras apenas com um int;
		public void mudarTela(Context context, Class<IActivity> activityDestino, String putExtra0, int putExtraValue0) {
			Intent intent = new Intent();
			intent.setClass(context, activityDestino);
			intent.putExtra(putExtra0, putExtraValue0);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		}
}