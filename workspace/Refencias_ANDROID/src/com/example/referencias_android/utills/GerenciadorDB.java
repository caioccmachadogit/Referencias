package com.example.referencias_android.utills;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class GerenciadorDB {
	
	/*----------------------------------------------------------------------------------------------------------------------------------------*/  
	// **** VAR DE CONFIGURACOES
	/*----------------------------------------------------------------------------------------------------------------------------------------*/
	public static String PATHDB = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Ref_Android_test/DataBases/";
	public static String NOMEDB = PATHDB+"DB_CRUD.db3";
	public static int VERSAODB = 1;
	public static SQLiteDatabase db;
	public static ArrayList<String> QUERY_CREATE_BANCO_DE_DADOS = new ArrayList<String>();
	public static ArrayList<String> QUERY_UPGRADE_BANCO_DE_DADOS = new ArrayList<String>();
	/*----------------------------------------------------------------------------------------------------------------------------------------*/  
	// **** VAR DE CONFIGURACOES
	/*----------------------------------------------------------------------------------------------------------------------------------------*/
	
	
	public void CriarDirDB(){
		try{
			String state = Environment.getExternalStorageState();
			boolean mExternalStorageAvailable = false; 
			boolean mExternalStorageWriteable = false;
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// Podemos ler e escrever os meios de comunicação 
				mExternalStorageAvailable = mExternalStorageWriteable = true;
					File file = new File (PATHDB);
					if ( !file.exists() ){
						file.mkdirs();
					}
				}
			else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// Só podemos ler a mídia 
				mExternalStorageAvailable = true; 
				mExternalStorageWriteable = false; 
				}
			else { // Outra coisa que está errada. Pode ser um de muitos outros estados, mas tudo o que precisamos // Para saber é que não sabem ler nem escrever 
				mExternalStorageAvailable = mExternalStorageWriteable = false;
				}
		}
		catch(Exception ex){
			ex.getMessage();
		}
	}
	
	public void checkOpeningDataBase(Context context, String dbType){
		try{
			if ( (db == null) || (!db.isOpen()) ){
				db = openDataBase(context, dbType);
			} else {
				db.close();
				db = openDataBase(context, dbType);
			}	
		}
		catch(Exception ex){
			ex.getMessage();
		}
	}
	
	public SQLiteDatabase openDataBase(Context context, String openType){
		GerenciaBancoDeDados gerenciador = new GerenciaBancoDeDados(context, NOMEDB, VERSAODB );
		if(openType.equals("write")){
			return gerenciador.getWritableDatabase();
		}
		else{
			return gerenciador.getReadableDatabase();
		}
		
	}
	
	public SQLiteDatabase closeDataBase(Context context) {
		if ( (db != null) && (db.isOpen()) ){
			db.close();
			db = null;
		}
		return null;		
	}
	
	/*----------------------------------------------------------------------------------------------------------------------------------------*/  
	// **** Class de Criação Inicial e Alteração de Tabelas
	/*----------------------------------------------------------------------------------------------------------------------------------------*/
	public class GerenciaBancoDeDados extends SQLiteOpenHelper {

		private Context ctx;
		
		public GerenciaBancoDeDados(Context context, String nomeBanco, int versaoBanco) {
			super(context, nomeBanco, null, versaoBanco);
			this.ctx = context;
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				for (int i = 0; i < QUERY_CREATE_BANCO_DE_DADOS.size(); i++) {
					String sql = QUERY_CREATE_BANCO_DE_DADOS.get(i);
					db.execSQL(sql);													
				}				
			} catch (Exception e){

			}
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				for (int i = 0; i < QUERY_UPGRADE_BANCO_DE_DADOS.size(); i++) {
					String sql = QUERY_UPGRADE_BANCO_DE_DADOS.get(i);
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
			ctx.deleteDatabase(NOMEDB);
		}
	}
	
	

}
