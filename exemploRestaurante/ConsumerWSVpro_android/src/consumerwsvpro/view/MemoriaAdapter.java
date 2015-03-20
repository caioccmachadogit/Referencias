package consumerwsvpro.view;

import java.util.ArrayList;

import com.example.consumerwsvpro.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import consumerwsvpro.data.Memoria;

public class MemoriaAdapter extends ArrayAdapter<Memoria>{
	private ArrayList<Memoria> memorias;
    static class ViewHolder{
    	TextView modulo;
    	TextView fabricante;
    	TextView serial;
    	TextView capacidade;
    	TextView velocidade;
    	TextView factor;
    	TextView modelo;
        TextView numero;
    }
    
    public MemoriaAdapter(Context context, ArrayList<Memoria> memorias) {
		  super(context, R.layout.list_view_item_infomemoria,memorias);
	      this.memorias = memorias;
	    }
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_infomemoria, parent, false);
			  holder.modulo = (TextView) convertView.findViewById(R.id.tv_modulo);
			  holder.fabricante = (TextView) convertView.findViewById(R.id.tv_fabricante);
			  holder.serial = (TextView) convertView.findViewById(R.id.tv_serialm);
	          holder.capacidade = (TextView) convertView.findViewById(R.id.tv_capacidadem);
	          holder.velocidade = (TextView) convertView.findViewById(R.id.tv_velocidade);
	          holder.factor = (TextView) convertView.findViewById(R.id.tv_factor);
	          holder.modelo = (TextView) convertView.findViewById(R.id.tv_modelom);
	          holder.numero = (TextView) convertView.findViewById(R.id.tv_numero);
	          convertView.setTag(holder);
	        }
		  	Memoria m = memorias.get(position);
		  	holder.modulo.setText(m.getModulo());
		  	holder.fabricante.setText(m.getFabricante());
		  	holder.serial.setText(m.getSerial());
		  	holder.capacidade.setText(m.getCapacidade());
		  	holder.velocidade.setText(m.getVelocidade());
		  	holder.factor.setText(m.getFactor());
		  	holder.modelo.setText(m.getModelo());
		  	holder.numero.setText(m.getNumero());
		  	
		  return convertView;
	}

}
