package com.example.referencias_android.crud;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.refencias_android.R;

public class FormCrudActivity extends Activity{
	
	private ControlllerCrud controller = new ControlllerCrud();
	EditText nome = null;
	EditText endereco = null;
	String IDCRUD = null;
	
	
	ArrayList<EditText> lstValidade = new ArrayList<EditText>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_view_crud);
		
		nome = (EditText) findViewById(R.id.nome);
		endereco = (EditText) findViewById(R.id.end);
		lstValidade.add(nome);
		lstValidade.add(endereco);
		
		IDCRUD = getIntent().getStringExtra(ListCrudActivity._ID);
		
		if (IDCRUD != null) {
			carregarForm();
		}
	}
	
	private void carregarForm() {
		controller.context = this;
		Cursor cursor = controller.ListarPorId(IDCRUD);
		nome.setText(cursor.getString(cursor.getColumnIndex("nome")));
		endereco.setText(cursor.getString(cursor.getColumnIndex("endereco")));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void btn_salvar(View v){
		try{
			if(validarEditTxt(lstValidade)){
				controller.context = this;
				long retorno = 0;
				String msg = null;
				if(IDCRUD == null){
					retorno = controller.Inserir(lstValidade);
					msg = "Registro Inserido!";
				}
				else{
					retorno = controller.Editar(lstValidade, IDCRUD);
					msg = "Registro Editado!";
				}
				if( retorno >= 1){
					Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
					startActivity(new Intent(this, ListCrudActivity.class));
					finish();
				}
				else {
					Toast.makeText(this, "Ação Não Realizada!", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception ex){
			Toast.makeText(this, "Houve problemas! "+ ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	@SuppressLint("NewApi") private boolean validarEditTxt(ArrayList<EditText> lstValidade){
		boolean validadeOk = true;
		for(int i=0; i < lstValidade.size();i++){
			if(lstValidade.get(i).getText().toString().isEmpty()){
				lstValidade.get(i).setError("Informe o valor para o campo!");
				validadeOk = false;
			}
			else{
				lstValidade.get(i).setError(null);
			}
		}
		return validadeOk;
	}


}
