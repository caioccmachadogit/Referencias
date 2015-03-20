package com.example.refencias_android.camera;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.refencias_android.R;
import com.example.refencias_android.main.MainActivity;
import com.example.referencias_android.model.Colaborador;
import com.example.referencias_android.model.Fotos;
import com.htcom.padrao.utills.AlertaDialog;
import com.htcom.padrao.utills.BitmapUtills;
import com.htcom.padrao.utills.CriarDirExterno;

@SuppressLint("NewApi") public class CameraActivity extends Fragment{
	@SuppressLint("SimpleDateFormat") private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmssSSS");
	private String date = dateFormat.format(new Date());
	private String photoFile = new String();
	CriarDirExterno criarDirExterno = new CriarDirExterno();
	private File file;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1777;
	private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Ref_Android_test/Fotos/";
	private ListView lv;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.camera_fragment, null);
		Button btnCapturar = (Button) view.findViewById(R.id.btnCapturar);
		btnCapturar.setOnClickListener(new OnClickListener()
		   {
	             @Override
	             public void onClick(View v)
	             {
	                btn_capturar();
	             } 
		   }); 
		
		lv = (ListView) view.findViewById(R.id.lv_fotos);
		ListarFotos();
		//==============CLICK ITEM DO LISTVIEW=================
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				long _id = arg3;
				int _pos = arg2;
				Log.d("Click Item", "id,pos: " + _id +"|"+ _pos);
				TextView path = (TextView) arg1.findViewById(R.id.path);
				Log.d("PATH: ", path.getText().toString());
				openAlertConfirma(path.getText().toString());
			}
			
		} );
		return view;
	}
	
	/*@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.camera_title);
		setContentView(R.layout.camera_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		lv = (ListView) findViewById(R.id.lv_fotos);
		ListarFotos();
		//==============CLICK ITEM DO LISTVIEW=================
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				long _id = arg3;
				int _pos = arg2;
				Log.d("Click Item", "id,pos: " + _id +"|"+ _pos);
				TextView path = (TextView) arg1.findViewById(R.id.path);
				Log.d("PATH: ", path.getText().toString());
				openAlertConfirma(path.getText().toString());
			}
			
		} );
	}*/
	
	private void openAlertConfirma(final String path) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
	     
		 alertDialogBuilder.setTitle(getActivity().getTitle()+ " Excluir");
		 alertDialogBuilder.setMessage("Confirma Exclus�o?");
		 // set positive button: Yes message
		 alertDialogBuilder.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// Confirma exclus�o
					File file = new File(path);
					if(file.exists()){
						if(file.delete()){
							ListarFotos();
							Toast.makeText(getActivity(), "Registro Exclu�do!", Toast.LENGTH_LONG).show();
						}
					}
				}
			  });
		 // set negative button: No message
		 alertDialogBuilder.setNegativeButton("N�o",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
				}
			});
		  AlertDialog alertDialog = alertDialogBuilder.create();
		 // show alert
		 alertDialog.show();
	}
	
	private void ListarFotos() {
		ArrayList<Fotos> fotos = new ArrayList<Fotos>();
		if(criarDirExterno.CriarDirDB("/Ref_Android_test/Fotos/")){
			File diretorio = new File(PATH); 
			String fList[] = diretorio.list(); 
			Log.v("Numero de arquivos no diretorio : ", String.valueOf(fList.length) ); 
			for ( int i = 0; i < fList.length; i++ ){ 
				Log.v("Arquivo: ",fList[i]); 
				Fotos foto = new Fotos();
				foto.setPath(PATH+fList[i]);
				foto.setImg(new File(foto.getPath()));
				fotos.add(foto);
			} 
			FotosAdapter pAdapter = new FotosAdapter(getActivity(), fotos);
			lv.setAdapter(pAdapter);
		}
	}

	public void btn_capturar() {
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		if(criarDirExterno.CriarDirDB("/Ref_Android_test/Fotos/")){
			photoFile = "photo_" + date;
			Random random = new Random();
			photoFile += "-" + random.nextInt(1000) + ".jpg";
			file = new File(criarDirExterno.PATHDIR);
			File dest = new File(file, photoFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(dest));
			startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
				if (!file.exists()) {
					file.mkdirs();
				}
				File dest = new File(file, photoFile);
				Bitmap bitmap = BitmapUtills.decodeSampledBitmapFromFile(dest.getAbsolutePath(), 100, 100);
				if (bitmap != null) 
				{
					BitmapUtills.compactarBitmap(bitmap, dest);
					ListarFotos();
					new AlertaDialog(getActivity()).showDialogAviso(getResources().getString(R.string.geral_Atencao), getResources().getString(R.string.geral_RegistroSalvo));
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						getActivity().finish();
						}
					};
				}
				else
				{	
					
				}
			}
		}
		catch (Exception e) {
			Log.e("ERROR COMPRESS", e.getMessage());
		}
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	         // Respond to the action bar's Up/Home button
	         case android.R.id.home:
				Intent i = new Intent();
				Colaborador colaborador = new Colaborador();
				colaborador.setNome("Desenvolvimento 3");
				i.setClass(getActivity(), MainActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("Colaborador", colaborador);
				i.putExtras(b);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				break;
	      }
	      return true;
	   }
}
