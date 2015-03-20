package net.rafaeltoledo.restaurante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GerenciadorRestaurantes extends SQLiteOpenHelper {
	
	private static final String NOME_BANCO = "restaurantes.db";
	private static final int VERSAO_SCHEMA = 1;

	public GerenciadorRestaurantes(Context context) {
		super(context, NOME_BANCO, null, VERSAO_SCHEMA);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE restaurantes (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" nome TEXT, endereco TEXT, tipo TEXT, anotacoes TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public void inserir(String nome, String endereco, String tipo, String anotacoes) {
		ContentValues valores = new ContentValues();

		valores.put("nome", nome);
		valores.put("endereco", endereco);
		valores.put("tipo", tipo);
		valores.put("anotacoes", anotacoes);

		getWritableDatabase().insert("restaurantes", "nome", valores);
	}
	
	public void atualizar(String id, String nome, String endereco, String tipo, String anotacoes) {
		ContentValues valores = new ContentValues();
		String[] argumentos = {id};
		
		valores.put("nome", nome);
		valores.put("endereco", endereco);
		valores.put("tipo", tipo);
		valores.put("anotacoes", anotacoes);
		
		getWritableDatabase().update("restaurantes", valores, "_id=?", argumentos);
	}
	
	public Cursor obterTodos() {
		return getReadableDatabase().rawQuery("select _id, nome, endereco, tipo, " +
				"anotacoes FROM restaurantes ORDER BY nome", null);
	}
	
	public String obterNome(Cursor c) {
		return c.getString(1);
	}

	public String obterEndereco(Cursor c) {
		return c.getString(2);
	}

	public String obterTipo(Cursor c) {
		return c.getString(3);
	}

	public String obterAnotacoes(Cursor c) {
		return c.getString(4);
	}
	
	public Cursor obterPorId(String id) {
		String[] argumentos = {id};
		
		return getReadableDatabase().rawQuery(
				"SELECT _id, nome, endereco, tipo, anotacoes " +
				"FROM restaurantes WHERE _id = ?", argumentos);
	}
}
