package com.example.referencias_android.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.example.referencias_android.utills.GerenciadorDB;

public class DAOCrud {
	
	GerenciadorDB gerDB = new GerenciadorDB();
	
	private final String NomeTabela = "cruds";
	
	private String createTable = "CREATE TABLE cruds (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" nome TEXT, endereco TEXT);";
	
	public String getCreateTable() {
		return createTable;
	}
	

	
	
	public long insert(Context context, ContentValues contentValues) throws Exception{
		long resultadoInsercao = 0;
		try{
			gerDB.checkOpeningDataBase(context, "write");		
			resultadoInsercao = GerenciadorDB.db.insert(NomeTabela, null, contentValues);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			throw ex;
		}
		return resultadoInsercao;
	}
	
	public Cursor listar(Context context) throws Exception{
		Cursor retorno;
		String dump;
		try{
			gerDB.checkOpeningDataBase(context, "read");		
			retorno = GerenciadorDB.db.rawQuery("select * " +
					"FROM "+NomeTabela+" ORDER BY _id DESC", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores CursorCrud",  dump);
		return retorno;
	}
	
	public Cursor listarPorLike(Context context, String whereColuna, String valor) throws Exception{
		Cursor retorno;
		String dump;
		try{
			gerDB.checkOpeningDataBase(context, "read");		
			retorno = GerenciadorDB.db.rawQuery("select * " +
					"FROM "+NomeTabela+" WHERE "+whereColuna+" LIKE '%"+valor+"%' ORDER BY nome ASC", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores CursorCrud",  dump);
		return retorno;
	}
	
	public Cursor listarPorId(Context context, String id) throws Exception{
		Cursor retorno;
		String dump;
		try {
			String[] argumentos = {id};
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+" WHERE _id = ?", argumentos);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		} catch (Exception e) {
			throw e;
		}
		retorno.moveToFirst();
		return retorno;
	}

	public int delete(Context context, String id) throws Exception{
		int retorno=0;
		try {
			gerDB.checkOpeningDataBase(context, "write");
			retorno = GerenciadorDB.db.delete(NomeTabela, "_id="+id, null);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch (Exception e) {
			throw e;
		}
		return retorno;
	}
	
	public long update(Context context, ContentValues contentValues, String id) throws Exception{
		long resultadoEdicao = 0;
		String[] argumentos = {id};
		try{
			gerDB.checkOpeningDataBase(context, "write");		
			resultadoEdicao = GerenciadorDB.db.update(NomeTabela, contentValues, "_id=?", argumentos);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			throw ex;
		}
		return resultadoEdicao;
	}
}
