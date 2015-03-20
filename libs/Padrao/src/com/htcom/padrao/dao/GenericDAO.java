package com.htcom.padrao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.htcom.padrao.funcoes.Constantes;
import com.htcom.padrao.interfaces.IPersistente;

public abstract class GenericDAO extends ControlaBancoDeDados {	
	
	public abstract String getCreateTable();
	public abstract String getNomeTabela();
	public abstract String[] getColunas();	
	public abstract ContentValues getContentValues(IPersistente persistente);
	
	public long insert(Context context, ContentValues contentValues){
		long resultadoInsercao = 0;
		checkOpeningDataBase(context);		
		resultadoInsercao = Constantes.db.insert(getNomeTabela(), null, contentValues);
		Constantes.db = closeDataBase(Constantes.db, context);
		return resultadoInsercao;
	}
	
	public int update(Context context, ContentValues contentValues, String where ){
		int resultadoUpdate = 0;
		checkOpeningDataBase(context);		
		resultadoUpdate = Constantes.db.update(getNomeTabela(), contentValues, where, null);
		Constantes.db = closeDataBase(Constantes.db, context);
		return resultadoUpdate;
	}
	
	public int delete( Context context, String where ){
		int resultadoDelete;
		checkOpeningDataBase(context);
		resultadoDelete = Constantes.db.delete( getNomeTabela(), where, null );
		Constantes.db = closeDataBase(Constantes.db, context);
		return resultadoDelete;		
	}
	
	public Cursor select(Context context, boolean distinct,  String[] columns, String selection, String[] selectionArgs, String groupBy, 
			String having, String orderBy, String limit ){
		checkOpeningDataBase(context);
		Cursor cursor = null;
		if ( Constantes.db.isOpen() ){
			cursor = Constantes.db.query(distinct, getNomeTabela(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		} else {
			Constantes.db = null;
			checkOpeningDataBase(context);
			cursor = Constantes.db.query(distinct, getNomeTabela(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		}
		return cursor;
	}
	
	public void checkOpeningDataBase(Context context){
		if ( (Constantes.db == null) || (!Constantes.db.isOpen()) ){
			Constantes.db = openDataBase(context);
		} else {
			Constantes.db.close();
			Constantes.db = openDataBase(context);
		}		
	}
	
	public int getSequencia(Context context, String nomeTabela, String campoAutoIncrement){
		
		SQLiteDatabase db = null;
		if ( db == null ){
			 db = openDataBase(context);
		}
		if ( db != null ) {
			Cursor cursor = db.rawQuery("SELECT CASE WHEN MAX("+ campoAutoIncrement + ") IS NULL THEN 0 ELSE MAX(" + campoAutoIncrement + ") END + 1 AS " + campoAutoIncrement +" FROM " + nomeTabela, null);
			if ( cursor.moveToFirst() ){
				return cursor.getInt(cursor.getColumnIndex(campoAutoIncrement));
			} else {
				return 0;
			}
		} 
		return 0;
	}
}