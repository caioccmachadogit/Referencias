package net.rafaeltoledo.restaurante;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class FormularioDetalhes extends Activity {
	
	EditText nome = null;
	EditText endereco = null;
	EditText anotacoes = null;
	RadioGroup tipos = null;
	GerenciadorRestaurantes gerenciador;
	String idRestaurante = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_detalhes);
		
		gerenciador = new GerenciadorRestaurantes(this);

		nome = (EditText) findViewById(R.id.nome);
		endereco = (EditText) findViewById(R.id.end);
		anotacoes = (EditText) findViewById(R.id.anotacoes);
		tipos = (RadioGroup) findViewById(R.id.tipos);

		Button salvar = (Button) findViewById(R.id.salvar);
		salvar.setOnClickListener(onSave);
		
		idRestaurante = getIntent().getStringExtra(ListaRestaurantes._ID);
		
		if (idRestaurante != null) {
			carregar();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		gerenciador.close();
	}
	
	private OnClickListener onSave = new OnClickListener() {

		public void onClick(View arg0) {
			String tipo = null;

			switch (tipos.getCheckedRadioButtonId()) {
			case R.id.rodizio:
				tipo = "rodizio";
				break;
			case R.id.fast_food:
				tipo = "fast_food";
				break;
			case R.id.a_domicilio:
				tipo = "a_domicilio";
				break;
			}
			
			if (idRestaurante == null) {
				gerenciador.inserir(nome.getText().toString(), 
						endereco.getText().toString(), 
						tipo, anotacoes.getText().toString());
			} else {
				gerenciador.atualizar(idRestaurante, 
						nome.getText().toString(), 
						endereco.getText().toString(), 
						tipo, anotacoes.getText().toString());
			}
			
			finish();
		}
	};
	
	private void carregar() {
		Cursor c = gerenciador.obterPorId(idRestaurante);
		
		c.moveToFirst();
		nome.setText(gerenciador.obterNome(c));
		endereco.setText(gerenciador.obterEndereco(c));
		anotacoes.setText(gerenciador.obterAnotacoes(c));
		
		if (gerenciador.obterTipo(c).equals("rodizio")) {
			tipos.check(R.id.rodizio);
		} else if (gerenciador.obterTipo(c).equals("fast_food")) {
			tipos.check(R.id.fast_food);
		} else {
			tipos.check(R.id.a_domicilio);
		}
		
		c.close();
	}
}
