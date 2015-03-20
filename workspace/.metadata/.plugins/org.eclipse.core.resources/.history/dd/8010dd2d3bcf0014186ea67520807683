package com.example.referencias_android.crud;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.refencias_android.R;

public class CrudAdapter extends CursorAdapter{
	@SuppressWarnings("deprecation")
	public CrudAdapter(Context context, Cursor cruds) {
		super(context, cruds);
	}
	private Cursor Cruds;
	
	static class ViewHolder{
    	TextView nome;
    	TextView endereco;
    	ImageButton editar;
    	ImageButton deletar;
    }
	

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
	    View v = inflater.inflate(R.layout.list_view_item_crud, parent, false);
        return v;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = new ViewHolder();
		    holder.nome = (TextView)view.findViewById(R.id.tv_nome);               
		    holder.nome.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		    holder.endereco = (TextView)view.findViewById(R.id.tv_end);               
		    holder.endereco.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
		    holder.editar = (ImageButton)view.findViewById(R.id.editarCrud);
		    holder.editar.setTag(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
		    holder.deletar = (ImageButton)view.findViewById(R.id.deletarCrud);
		    holder.deletar.setTag(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
		
	}

	

}
