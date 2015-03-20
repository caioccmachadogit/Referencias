package com.example.referencias_android.crud;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.refencias_android.R;
import com.example.refencias_android.main.MainActivity;
import com.example.referencias_android.login.LoginActivity;
import com.example.referencias_android.model.Colaborador;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class ListCrudActivity extends Activity implements SearchView.OnQueryTextListener{
	private ControlllerCrud controller = new ControlllerCrud();
	ListView lstView;
	SearchView searchView;
	public static String _ID = "_ID";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setTitle(R.string.crud_title);
		setContentView(R.layout.list_view_crud);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		controller.context = this;
		controller.criarDB();
		AtualizarListView(null);
		setupSearchView();
		//==============CLICK ITEM DO LISTVIEW=================
		/*lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				long _id = arg3;
				int _pos = arg2;
				Log.d("Click Item", "id,pos: " + _id +"|"+ _pos);
			}
			
		} );*/
	}
	
	//============Metódos ListView======================
	private void AtualizarListView(String filtro) {
		CrudAdapter adapter = controller.PreencherListViewItem(filtro);
		lstView = (ListView) findViewById(R.id.lv_crud);
		lstView.setAdapter(adapter);
	}
	
	public void imgBtn_editar(View v){
		String tag = (String)v.getTag();
		int id = Integer.valueOf(tag);
		Intent i = new Intent(this, FormCrudActivity.class);
		i.putExtra(_ID, String.valueOf(id));
		startActivity(i);
	}
	
	public void imgBtn_deletar(View v){
		openAlertConfirma(v);
	}
	//============Metódos ListView======================
	
	public void btn_novo(View v){
		startActivity(new Intent(this, FormCrudActivity.class));
	}
	
	//============Metódos Caixa de Dialogo======================
	private void openAlertConfirma(View v) {
		_ID = (String)v.getTag();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	     
		 alertDialogBuilder.setTitle(this.getTitle()+ " Excluir");
		 alertDialogBuilder.setMessage("Confirma Exclusão?");
		 // set positive button: Yes message
		 alertDialogBuilder.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// Confirma exclusão
					if(controller.Deletar(_ID) == 1){
						dialog.cancel();
						AtualizarListView(null);
						Toast.makeText(getApplicationContext(), "Registro Excluído!", Toast.LENGTH_LONG).show();
					}
					else{
						dialog.cancel();
						Toast.makeText(getApplicationContext(), "Registro Não Excluído!", Toast.LENGTH_LONG).show();
					}
				}
			  });
		 // set negative button: No message
		 alertDialogBuilder.setNegativeButton("Não",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
				}
			});
		  AlertDialog alertDialog = alertDialogBuilder.create();
		 // show alert
		 alertDialog.show();
	}
	//============Metódos Caixa de Dialogo======================
	
	//============Metódos SearchView======================
	private void setupSearchView() {
		searchView = (SearchView) findViewById(R.id.sv_crud);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true); 
        searchView.setQueryHint("Buscar..");
    }
	
	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			AtualizarListView(null);
        } else {
        	AtualizarListView(newText);
        }
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}
	//============Metódos SearchView======================
	
	
	
	
	  
	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	         // Respond to the action bar's Up/Home button
	         case android.R.id.home:
	         Intent i = new Intent();
	         Colaborador colaborador = new Colaborador();
	         colaborador.setNome("Desenvolvimento 3");
             i.setClass(ListCrudActivity.this, MainActivity.class);
             Bundle b = new Bundle();
             b.putSerializable("Colaborador", colaborador);
             i.putExtras(b);
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(i);
	      }
	      return true;
	   }
}
