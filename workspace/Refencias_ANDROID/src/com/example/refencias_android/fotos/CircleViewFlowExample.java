/*
 * Copyright (C) 2011 Patrik ?kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.refencias_android.fotos;

import java.io.File;
import java.util.ArrayList;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.refencias_android.R;
import com.example.refencias_android.camera.FotosAdapter;
import com.example.referencias_android.model.Fotos;

public class CircleViewFlowExample extends Activity {

	private ViewFlow viewFlow;
	private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Ref_Android_test/Fotos/";
	private FotosAdapter pAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.circle_title);
		setContentView(R.layout.circle_layout);

		ListarFotos();
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		viewFlow.setAdapter(pAdapter, 0);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
	}
	/* If your min SDK version is < 8 you need to trigger the onConfigurationChanged in ViewFlow manually, like this */	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}
	
	private void ListarFotos() {
		ArrayList<Fotos> fotos = new ArrayList<Fotos>();
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
		pAdapter = new FotosAdapter(this, fotos);
	}
}