package consumerwsvpro.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.data.Maquina;

public class MaquinaAdapter extends ArrayAdapter<Maquina>{
	private ArrayList<Maquina> Maquinas;
    static class ViewHolder{
    	TextView modelo;
    	TextView fabricante;
    	TextView versao;
    	TextView serial;
    }
    
    public MaquinaAdapter(Context context, ArrayList<Maquina> Maquinas) {
		  super(context, R.layout.list_view_item_infomaquina,Maquinas);
	      this.Maquinas = Maquinas;
	    }
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_infomaquina, parent, false);
			  holder.modelo = (TextView) convertView.findViewById(R.id.tv_modelomaq);
			  holder.fabricante = (TextView) convertView.findViewById(R.id.tv_fabricantemaq);
	          holder.versao = (TextView) convertView.findViewById(R.id.tv_versaomaq);
	          holder.serial = (TextView) convertView.findViewById(R.id.tv_serialmaq);
	          convertView.setTag(holder);
	        }
		  	Maquina m = Maquinas.get(position);
		  	holder.modelo.setText(m.getModelo());
		  	holder.fabricante.setText(m.getFabricante());
		  	holder.versao.setText(m.getVersao());
		  	holder.serial.setText(m.getSerial());
		  return convertView;
	}

}


