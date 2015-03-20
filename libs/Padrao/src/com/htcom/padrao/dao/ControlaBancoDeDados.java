package com.htcom.padrao.dao;

import com.htcom.padrao.funcoes.Constantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlaBancoDeDados  {
	
	static Constantes constantes = new Constantes();
	
	public SQLiteDatabase openDataBase(Context context){
		GerenciaBancoDeDados gerenciador = new GerenciaBancoDeDados(context, Constantes.NOME_BANCO, Constantes.VERSAO_BANCO );	
		return gerenciador.getWritableDatabase();
	}
	
	public SQLiteDatabase closeDataBase(SQLiteDatabase db, Context context) {
		if ( (Constantes.db != null) && ( Constantes.db.isOpen()) ){
			Constantes.db.close();
			Constantes.db = null;
		}
		return null;		
	}
	
	public void deleteDataBase(Context ctx) {
		new GerenciaBancoDeDados(ctx, Constantes.NOME_BANCO, Constantes.VERSAO_BANCO ).deleteDataBase();
	}
	
	public class GerenciaBancoDeDados extends SQLiteOpenHelper {

		private Context ctx;
		
		public GerenciaBancoDeDados(Context context, String nomeBanco, int versaoBanco) {
			super(context, nomeBanco, null, versaoBanco);
			this.ctx = context;
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				for (int i = 0; i < Constantes.QUERY_CREATE_BANCO_DE_DADOS.size(); i++) {
					String sql = Constantes.QUERY_CREATE_BANCO_DE_DADOS.get(i);
					db.execSQL(sql);													
				}				
			} catch (Exception e){

			}
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				for (int i = 0; i < Constantes.QUERY_UPGRADE_BANCO_DE_DADOS.size(); i++) {
					String sql = Constantes.QUERY_UPGRADE_BANCO_DE_DADOS.get(i);
					db.execSQL(sql);		
				}
			} catch (Exception e){
				//new AlertaDialog(ctx).showDialogErro(ctx, e);
			}
		}
		
		@SuppressWarnings("null")
		@Override
		public void onOpen(SQLiteDatabase db) {
			if ( (db == null) && !db.isOpen() ){
				super.onOpen(db);
			}
		}
		
		public void deleteDataBase(){
			ctx.deleteDatabase(Constantes.NOME_BANCO);
		}
	}
}