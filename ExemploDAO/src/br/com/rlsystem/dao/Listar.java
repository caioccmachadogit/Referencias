package br.com.rlsystem.dao;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class Listar extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ClienteDAO dao = new ClienteDAO(this);
		
		ClienteVO vo = new ClienteVO();
		vo.setNome("Rafael");
		vo.setRenda(0);
		dao.insert(vo);
		
		setListAdapter(new ClienteAdapter(this, dao.lista()));
	}
	
	protected void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
		ClienteVO c = (ClienteVO)l.getItemAtPosition(position);
	}
}