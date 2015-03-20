package pack.app.organizadormercado;

import java.util.ArrayList;
import java.util.List;

import pack.dao.organizadormercado.CategoriaDAO;
import pack.model.organizadormercado.Categoria;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;

public class ListCategoria extends ListActivity{

	private CategoriaDAO dao = new CategoriaDAO(this);
	Cursor listaRestaurantes = null;
	AdaptadorCategoria adaptador = null;
	private List<Categoria> categorias = new ArrayList<Categoria>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_categoria);
		
		dao.open();
		List<Categoria> categorias = dao.Listar();
		ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, categorias);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		dao.open();
		super.onResume();
		
		categorias = dao.Listar();
		ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, categorias);
		setListAdapter(adapter);
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	class AdaptadorCategoria extends CursorAdapter {
		AdaptadorCategoria(Cursor c) {
			super(ListCategoria.this, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
