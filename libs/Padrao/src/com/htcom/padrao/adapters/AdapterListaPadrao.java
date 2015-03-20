package com.htcom.padrao.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.htcom.padrao.funcoes.R;

import com.htcom.padrao.funcoes.VariaveisGlobais;

public class AdapterListaPadrao  extends ArrayAdapter<String> {

	private ArrayList<String> lista;
	private LayoutInflater mInflater;
	private int color;
	private int textViewResourceId;
	 
	public AdapterListaPadrao(Context context, int textViewResourceId,
			ArrayList<String> list, int color) {
		super(context, textViewResourceId, list);
		
		this.lista = list;
		this.textViewResourceId = textViewResourceId;
		this.mInflater = LayoutInflater.from(context);
		this.color = color;
	}

	public int getCount() {		
		return lista.size();
	}

	public String getItem(int position) {		
		return lista.get(position);
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(final int position, View view, ViewGroup viewGroup) {
		
		view = mInflater.inflate(textViewResourceId, null);
		TextView txtDescricao = (TextView) view.findViewById(R.lay_lista_simples.textView);
		txtDescricao.setText(lista.get(position));
		txtDescricao.setTypeface(VariaveisGlobais.typeFacePadraoLight);
		txtDescricao.setTextColor(color);
		
		return view;
	}
}