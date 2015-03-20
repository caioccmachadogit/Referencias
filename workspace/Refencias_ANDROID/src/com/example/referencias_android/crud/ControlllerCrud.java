package com.example.referencias_android.crud;

import java.util.ArrayList;

import com.example.referencias_android.utills.GerenciadorDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;

public class ControlllerCrud {
	private DAOCrud dao = null;
	public Context context;
	
	public void criarDB(){
		GerenciadorDB gerDB = new GerenciadorDB();
		// **** CRIA O DIRETORIO NO ARMAZENAMENTO EXTERNO DO DEVICE CASO NÃO EXISTA
		gerDB.CriarDirDB();
		/*----------------------------------------------------------------------------------------------------------------------------------------*/  
		// **** CARREGA UM ARRAY DE QUERYS PARA EXECUÇÃO NO MOMENTO DE CRIAÇÃO DO BANCO DE DADOS
		dao = new DAOCrud();
		GerenciadorDB.QUERY_CREATE_BANCO_DE_DADOS.add(dao.getCreateTable());
		/*----------------------------------------------------------------------------------------------------------------------------------------*/  
		// **** ABRE E FECHA O BANCO DE DADOS PARA CRIAR O BANCO CASO SEJA O PRIMEIRO ACESSO AO SISTEMA
		gerDB.checkOpeningDataBase(context, "write");
		gerDB.closeDataBase(context);	
		/*----------------------------------------------------------------------------------------------------------------------------------------*/
	}
	
	public long Inserir(ArrayList<EditText> lst) throws Exception{
		long retorno = 0;
		try{
			ContentValues valores = new ContentValues();
			valores.put("nome", lst.get(0).getText().toString());
			valores.put("endereco", lst.get(1).getText().toString());
			dao = new DAOCrud();
			retorno = dao.insert(context, valores);
		}
		catch(Exception ex){
			Log.v("Error - InserirCrud: ", ex.getMessage());
			throw ex;
		}
		return retorno; 
	}

	public CrudAdapter PreencherListViewItem(String where){
		CrudAdapter adapter = null;
		Cursor cursor;
		try {
			if(where == null){
				cursor = Listar();
			}
			else {
				cursor = ListarLike(where);
			}
			adapter = new CrudAdapter(context, cursor);
			
		} catch (Exception e) {
			Log.v("ListItemCrud", e.getMessage());
		}
		return adapter;
	}

	private Cursor ListarLike(String whereValor) throws Exception{
		Cursor cursor;
		try{
			dao = new DAOCrud();
			cursor = dao.listarPorLike(context, "nome", whereValor);
		}
		catch (Exception ex){
			throw ex;
		}
		return cursor;
	}

	public Cursor Listar() throws Exception{
		Cursor cursor;
		try{
			dao = new DAOCrud();
			cursor = dao.listar(context);
		}
		catch (Exception ex){
			throw ex;
		}
		return cursor;
	}
	
	public Cursor ListarPorId(String id){
		Cursor cursor = null;
		try{
			dao = new DAOCrud();
			cursor = dao.listarPorId(context, id);
		}
		catch (Exception ex){
			Log.e("Error - ListarPorId: ", ex.getMessage());
		}
		return cursor;
	}

	public int Deletar(String id) {
		int retorno = 0;
		try {
			dao = new DAOCrud();
			retorno = dao.delete(context, id);
		} catch (Exception ex) {
			Log.v("Error - DeleteCrud: ", ex.getMessage());
		}
		return retorno;
	}
	
	public long Editar(ArrayList<EditText> lst, String id) throws Exception{
		long retorno = 0;
		try{
			ContentValues valores = new ContentValues();
			valores.put("nome", lst.get(0).getText().toString());
			valores.put("endereco", lst.get(1).getText().toString());
			dao = new DAOCrud();
			retorno = dao.update(context, valores, id);
		}
		catch(Exception ex){
			Log.v("Error - EditarCrud: ", ex.getMessage());
			throw ex;
		}
		return retorno;
	}
}
