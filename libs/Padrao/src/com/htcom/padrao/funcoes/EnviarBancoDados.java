package com.htcom.padrao.funcoes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.htcom.padrao.funcoes.R;
import com.htcom.padrao.funcoes.AplicativosInstalados;
/*import com.htcom.padrao.funcoes.ServicosWeb;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;*/

public class EnviarBancoDados {
	private static String database;
	private Context context;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public EnviarBancoDados( Context context ) {
		this.context = context;
		StringBuilder sb = new StringBuilder();
		sb.append("data/data/");
		sb.append(context.getPackageName());
		sb.append("/databases/");
		sb.append( Constantes.NOME_BANCO );

		database = sb.toString();
	}

	/*public void enviarBanco() {
		try {
			File file = new File(database);
			if (file.exists()) {
				byte[] buffer = new byte[(int) file.length()];

				fis = new FileInputStream(file);
				int tamanho = (int) file.length();
				fis.read(buffer, 0, tamanho);

				AplicativosInstalados apps = new AplicativosInstalados();
				ArrayList<AplicativosInstalados> aplicativosInstalados = apps
						.getPackages(context);

				XStream xstream = new XStream(new DomDriver("UTF-8"));
				xstream.alias("aplicativo", AplicativosInstalados.class);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				Writer writer = new OutputStreamWriter(byteArrayOutputStream,
						Charset.defaultCharset());
				xstream.toXML(aplicativosInstalados, writer);

				ServicosWeb enviaBancoDeDados = new ServicosWeb();
				enviaBancoDeDados.enviaBancoDados( buffer, byteArrayOutputStream.toString() );
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviar() {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(context.getResources().getString(R.string.geral_Atencao));
		progressDialog.setMessage(context.getResources().getString(R.string.geral_EnviaBkp));
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setCancelable(false);
		progressDialog.show();

		new Thread(new Runnable() {
			public void run() {
				try {
					enviarBanco();
				} catch (Exception e) {
					Log.i("ERRO", "BACKUP");
				} finally {
					if (progressDialog.isShowing())
						progressDialog.dismiss();
				}
			}
		}).start();
	}*/

	public void sobrescreverBancoDados() {
		try {
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/Download/backup.txt");
			fis = new FileInputStream(f);
			File novoArquivo = new File(database);
			novoArquivo.mkdirs();
			File saida = new File(novoArquivo, Constantes.NOME_BANCO );
			fos = new FileOutputStream(saida);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
	}
}