package com.htcom.padrao.utills;

import java.io.File;

import android.content.Context;
import android.widget.Toast;

public class FileUtills {
	public boolean Deletar(String path, Context context){
		boolean resp = false;
			File file = new File(path);
			if(file.exists()){
				resp = file.delete();
				Toast.makeText(context, "Registro Excluído!", Toast.LENGTH_LONG).show();
			}
			
		
		return resp;
	}

}
