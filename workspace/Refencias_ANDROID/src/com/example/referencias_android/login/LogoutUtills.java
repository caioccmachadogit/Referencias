package com.example.referencias_android.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.htcom.padrao.utills.SharedPreferencesUtills;

public class LogoutUtills extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openAlertLogout();
	}
	
	private void openAlertLogout() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Sair");
		alertDialogBuilder.setMessage("Deseja realmente Sair da aplica��o?");
		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("Sim",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SharedPreferencesUtills.deletePreferences("CPF", LogoutUtills.this);
						startActivity(new Intent(LogoutUtills.this, LoginActivity.class));
					}
				});
		// set negative button: No message
		alertDialogBuilder.setNegativeButton("N�o",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// cancel the alert box and put a Toast to the user
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show alert
		alertDialog.show();
	}
}
