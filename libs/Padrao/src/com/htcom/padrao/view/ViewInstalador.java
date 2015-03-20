package com.htcom.padrao.view;

import java.io.File;
import com.htcom.padrao.funcoes.R;
import com.htcom.padrao.funcoes.Constantes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ViewInstalador extends Activity {
	
	private Button btInstalar;
	private ImageButton ibInstalador1, ibInstalador2, ibInstalador3;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_instalacao_apk);
		btInstalar = (Button) findViewById(R.inst.btSalvar);
		ibInstalador1 = (ImageButton) findViewById(R.inst.ibInstalador1);
		ibInstalador2 = (ImageButton) findViewById(R.inst.ibInstalador2);
		ibInstalador3 = (ImageButton) findViewById(R.inst.ibInstalador3);
		btInstalar.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				instalaArquivo();

			}
		});
		ibInstalador1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//alerta();

			}
		});
		ibInstalador2.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				//alerta();

			}
		});
		ibInstalador3.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				//alerta();

			}
		});
	}

	private void instalaArquivo() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						+ "/download/" +  Constantes.NOME_APK)),
						  "application/vnd.android.package-archive");
		startActivity(intent);
		finish();
	}

}