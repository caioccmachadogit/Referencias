package com.htcom.padrao.funcoes;

import android.content.Context;

import com.htcom.padrao.interfaces.IActivity;

public abstract class Control {

	protected Context context;
	
	public Control() {

	}
	
	public Control(Context ctx) {
		this.context = ctx;
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino);
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, String putExtra0, int putExtraValue0, 
												 String putExtra1, int putExtraValue1){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, putExtra1, putExtraValue1 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Context context, Class<?> activityName, String putExtra0, int putExtraValue0, 
												 String putExtra1, int putExtraValue1){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, putExtra1, putExtraValue1 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, String putExtra0, String putExtraValue0, 
			String putExtra1, int putExtraValue1, String putExtra2,  int putExtraValue2){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, 
												   putExtra1, putExtraValue1, 
												   putExtra2, putExtraValue2 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, 
								   String putExtra0, int putExtraValue0, 
								   String putExtra1, int putExtraValue1, 
								   String putExtra2, int putExtraValue2,
								   String putExtra3, String putExtraValue3,
								   String putExtra4, int putExtraValue4){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, 
												   putExtra1, putExtraValue1, 
												   putExtra2, putExtraValue2,
												   putExtra3, putExtraValue3,
												   putExtra4, putExtraValue4);
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, 
								   String putExtra0, int putExtraValue0, 
								   String putExtra1, int putExtraValue1, 
								   String putExtra2, int putExtraValue2,
								   String putExtra3, String putExtraValue3){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, 
												   putExtra1, putExtraValue1, 
												   putExtra2, putExtraValue2,
												   putExtra3, putExtraValue3);
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, 
												String putExtra0, int putExtraValue0, 
												String putExtra1, int putExtraValue1, 
												String putExtra2, int putExtraValue2){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, 
												   putExtra1, putExtraValue1, 
												   putExtra2, putExtraValue2 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Context context, Class<?> activityName, 
												String putExtra0, int putExtraValue0, 
												String putExtra1, int putExtraValue1, 
												String putExtra2, int putExtraValue2){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, 
												   putExtra1, putExtraValue1, 
												   putExtra2, putExtraValue2 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, String putExtra0, String putExtraValue0, 
												 String putExtra1, int putExtraValue1){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0, putExtra1, putExtraValue1 );
	}
	
	@SuppressWarnings("unchecked")
	public void mudarTela(Class<?> activityName, String putExtra0, int putExtraValue0){
		Class<IActivity> activityDestino = (Class<IActivity>) activityName;
		IntentHelper helper = new IntentHelper();
		helper.mudarTela(context, activityDestino, putExtra0, putExtraValue0 );
	}
}
