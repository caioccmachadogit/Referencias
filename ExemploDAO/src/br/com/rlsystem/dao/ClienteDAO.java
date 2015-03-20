package br.com.rlsystem.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClienteDAO {

	private Context ctx;
	private String table_name = "clientes";
	private String[] colunas = new String[] { "id", "nome", "renda" };

	public ClienteDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(ClienteVO cliente) {
		SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nome", cliente.getNome());
		values.put("renda", cliente.getRenda());
		return (db.insert(table_name, null, values) > 0);
	}

	public List<ClienteVO> lista() {

		List<ClienteVO> lista = new ArrayList<ClienteVO>();
		
		SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
		Cursor c = db.query(table_name, colunas, null, null, null, null, null);

		while (c.moveToNext()) {
			ClienteVO cliente = new ClienteVO();
			cliente.setId(c.getInt(c.getColumnIndex("id")));
			cliente.setNome(c.getString(c.getColumnIndex("nome")));
			cliente.setRenda(c.getDouble(c.getColumnIndex("renda")));
			lista.add(cliente);
		}

		return lista;
	}
}
