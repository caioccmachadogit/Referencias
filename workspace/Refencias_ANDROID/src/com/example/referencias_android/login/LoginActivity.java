package com.example.referencias_android.login;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.refencias_android.R;
import com.example.refencias_android.main.MainActivity;
import com.example.refencias_android.main.SharedPrefActivity;
import com.example.referencias_android.login.AsyncLogin.AsyncResponse;
import com.example.referencias_android.model.Colaborador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htcom.padrao.funcoes.Mascaras;
import com.htcom.padrao.funcoes.ValidaCpf;
import com.htcom.padrao.utills.AlertaDialog;
import com.htcom.padrao.utills.FormUtills;
import com.htcom.padrao.utills.SharedPreferencesUtills;


public class LoginActivity extends Activity implements AsyncResponse{
	
	private EditText edtCpf;
	ArrayList<EditText> lstValidade = new ArrayList<EditText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(SharedPreferencesUtills.loadSavedPreferencesString("CPF", this) !=""){
			startActivity(new Intent(this, SharedPrefActivity.class));
		}
		else {
			setContentView(R.layout.form_view_login);
			edtCpf = (EditText) findViewById(R.id.edtCpf);
			edtCpf.addTextChangedListener(Mascaras.insert("###.###.###-##", edtCpf));
			edtCpf.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(14)});
			lstValidade.add(edtCpf);
		}
	}
	
	public void BtnEntrar(View view) {
		 if(FormUtills.validarEditTxt(lstValidade)){
			 if(ValidaCpf.isValidCPF(Mascaras.unmask(edtCpf.getText().toString()))){
				 TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
				 Colaborador colaborador = new Colaborador();
				 colaborador.setCpf(edtCpf.getText().toString());
				 colaborador.setImei(telephonyManager.getDeviceId());
				 new AsyncLogin(this.findViewById(android.R.id.content).getRootView(), LoginActivity.this).execute(colaborador);
				 AsyncLogin.delegate = this;
			 }
			 else {
				 new AlertaDialog(this).showDialogAviso("Atenção!", "Cpf inválido, por favor verifique!");
			}
		 }

	}

	@Override
	public void processFinish(String output) {
		// Retorno do AsyncLogin
		try {
			if(!output.equals("0") && !output.equals("x") && output != null){
				ConverterJson(output);
				finish();
			}
			else{
			//Problema de conexão ou Usuario não valido
				if(output.equals("0")){
					new AlertaDialog(this).showDialogAviso("Atenção!", "Usuário inválido, por favor verifique!");
				}
				else {
					new AlertaDialog(this).showDialogAviso("Atenção!", "Tente novamente mais tarde! Se esta situação persistir, entre em contato com a HTCOM!");
				}
			}
		}
		catch (Exception e) {
			Log.e("ERROR PROCESSFINISH", e.getMessage());
		}
		
	}
	
	private void ConverterJson(String respWs) {
		try {
			GsonBuilder		 builder = new GsonBuilder();	
			Gson 			 gson    = builder.create();
			Colaborador colaborador = gson.fromJson(respWs, Colaborador.class);																								
			if (colaborador != null ){
				SharedPreferencesUtills.savePreferences("CPF", colaborador.getCpf(), this);
				Intent i = new Intent();
                i.setClass(LoginActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Colaborador", colaborador);
                i.putExtras(b);
                startActivity(i);
			}
			else{
				new AlertaDialog(this).showDialogAviso("Atenção!", "Usuário inválido, por favor verifique! #cod1");
			}	
		}
		catch (Exception e) {
			Log.e("ERROR CONVERTERJSON", e.getMessage());
			new AlertaDialog(this).showDialogAviso("Atenção!", "Usuário inválido, por favor verifique! #cod2");
		}
	}

}
