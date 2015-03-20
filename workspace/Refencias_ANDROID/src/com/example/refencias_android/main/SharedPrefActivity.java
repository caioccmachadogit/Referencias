package com.example.refencias_android.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.refencias_android.R;
import com.example.referencias_android.model.Colaborador;
import com.htcom.padrao.utills.SharedPreferencesUtills;

public class SharedPrefActivity extends Activity{
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.shared_title);
		setContentView(R.layout.sharedpref_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView tv_cpf = (TextView)findViewById(R.id.tv_cpf);
        tv_cpf.setText("CPF: "+SharedPreferencesUtills.loadSavedPreferencesString("CPF", this));
	}

	
	@Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	         // Respond to the action bar's Up/Home button
	         case android.R.id.home:
	         Intent i = new Intent();
	         Colaborador colaborador = new Colaborador();
	         colaborador.setNome("Desenvolvimento 3");
          i.setClass(SharedPrefActivity.this, MainActivity.class);
          Bundle b = new Bundle();
          b.putSerializable("Colaborador", colaborador);
          i.putExtras(b);
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(i);
	      }
	      return true;
	   }
}
