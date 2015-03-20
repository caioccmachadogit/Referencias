package consumerwsvpro.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.data.Bios;

public class BiosAdapter extends ArrayAdapter<Bios>{
	private ArrayList<Bios> Bioss;
    static class ViewHolder{
    	TextView fabricante;
    	TextView versao;
    }
    
    public BiosAdapter(Context context, ArrayList<Bios> Bioss) {
		  super(context, R.layout.list_view_item_infobios,Bioss);
	      this.Bioss = Bioss;
	    }
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_infobios, parent, false);
			  holder.fabricante = (TextView) convertView.findViewById(R.id.tv_fabricanteb);
	          holder.versao = (TextView) convertView.findViewById(R.id.tv_versaob);
	          convertView.setTag(holder);
	        }
		  	Bios p = Bioss.get(position);
		  	holder.fabricante.setText(p.getFabricante());
		  	holder.versao.setText(p.getVersao());
		  return convertView;
	}

}

