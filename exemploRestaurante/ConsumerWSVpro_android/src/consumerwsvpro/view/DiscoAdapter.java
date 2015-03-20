package consumerwsvpro.view;

import java.util.ArrayList;

import com.example.consumerwsvpro.R;

import consumerwsvpro.data.Disco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiscoAdapter extends ArrayAdapter<Disco>{
    private ArrayList<Disco> discos;
    static class ViewHolder{
    	TextView capacidade;
    	TextView disco;
    	TextView modelo;
    	TextView serial;
    }
	
	  public DiscoAdapter(Context context, ArrayList<Disco> discos) {
		  super(context, R.layout.list_view_item_infodisco,discos);
	      this.discos = discos;
	    }
	  
	  @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_infodisco, parent, false);
			  holder.disco = (TextView) convertView.findViewById(R.id.tv_disco);
			  holder.modelo = (TextView) convertView.findViewById(R.id.tv_modelo);
			  holder.serial = (TextView) convertView.findViewById(R.id.tv_serial);
	          holder.capacidade = (TextView) convertView.findViewById(R.id.tv_capacidade);
	          convertView.setTag(holder);
	        }
		  	Disco d = discos.get(position);
		  	holder.capacidade.setText(d.getCapacidade());
		  	holder.disco.setText(d.getDisco());
		  	holder.modelo.setText(d.getModelo());
		  	holder.serial.setText(d.getSerial());
		  return convertView;
	}

}
