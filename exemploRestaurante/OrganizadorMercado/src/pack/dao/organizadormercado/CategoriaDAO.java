package pack.dao.organizadormercado;

import java.util.ArrayList;
import java.util.List;

import pack.model.organizadormercado.Categoria;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CategoriaDAO extends SQLiteOpenHelper{

	private static final String NOME_BANCO = "organizador_mercado.db";
	private static final int VERSAO_SCHEMA = 1;
	private SQLiteDatabase database;
	
	public CategoriaDAO(Context context) {
		super(context, NOME_BANCO, null, VERSAO_SCHEMA);
	}

	public void open() throws SQLException{
		database = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public List<Categoria> Listar(){
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			Cursor cursor = database.rawQuery("select id_categoria, categoria" +
					" FROM cad_categoria ORDER BY categoria", null);
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				Categoria categoria = new Categoria();
				categoria.setId_categoria(cursor.getInt(0));
				categoria.setCategoria(cursor.getString(1));
				categorias.add(categoria);
				cursor.moveToNext();
			}
			cursor.close();
			return categorias;
		} catch (SQLException e){
			Log.e("BD", "Erro ao Buscar as categorias:" + e.toString());
			return null;
		}
	}
}
