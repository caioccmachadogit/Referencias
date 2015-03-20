package com.example.refencias_android.camera;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.refencias_android.R;
import com.example.referencias_android.model.Fotos;

public class FotosAdapter extends ArrayAdapter<Fotos> {

	private ArrayList<Fotos> Fotos;
	static class ViewHolder{
    	ImageView img;
    	TextView path;
    }
	
	public FotosAdapter(Context context, ArrayList<Fotos> Fotos) {
		super(context, R.layout.list_view_item_fotos ,Fotos);
	      this.Fotos = Fotos;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_fotos, parent, false);
	        }
		  holder.img = (ImageView) convertView.findViewById(R.id.imgFoto);
		  holder.path = (TextView) convertView.findViewById(R.id.path);
          convertView.setTag(holder);
          
          Fotos f = Fotos.get(position);
		  	if (f.getImg().exists())
		  	{
				new BitmapFactory();
				Bitmap bitmap = BitmapFactory.decodeFile(f.getImg().getAbsolutePath());
				holder.img.setImageBitmap(bitmap);
			}
		  	holder.path.setText(f.getPath());
		  return convertView;
	}

	
	
}
