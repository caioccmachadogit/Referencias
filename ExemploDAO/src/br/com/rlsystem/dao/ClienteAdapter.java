package br.com.rlsystem.dao;

import java.util.List;

import br.com.rlsysten.dao.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClienteAdapter extends BaseAdapter{

	private List<ClienteVO> lista;
	private Context ctx;
	
	public ClienteAdapter(Context ctx, List<ClienteVO> vo){
		this.lista = vo;
		this.ctx = ctx;
	}
	
	public int getCount() {
		return lista.size();
	}


	public Object getItem(int position) {
		return lista.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ClienteVO clienteVO = lista.get(position);
		
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.lista, null);
		
		
		TextView txt1 = (TextView)v.findViewById(R.id.textView1);
		
		if(clienteVO.getId() == 1){
			txt1.setBackgroundColor(Color.RED);
		}
		
		txt1.setText(String.valueOf(clienteVO.getId()));
		
		TextView txt2 = (TextView)v.findViewById(R.id.textView2);
		txt2.setText(clienteVO.getNome());
		
		TextView txt3 = (TextView)v.findViewById(R.id.textView3);
		txt3.setText(String.valueOf(clienteVO.getRenda()));

		
		return v;
	}

}
