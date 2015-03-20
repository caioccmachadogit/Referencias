package consumerwsvpro.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.consumerwsvpro.R;

import consumerwsvpro.data.Processador;

public class ProcessadorAdapter extends ArrayAdapter<Processador>{
	private ArrayList<Processador> Processadors;
    static class ViewHolder{
    	TextView fabricante;
    	TextView versao;
    	TextView capacidade;
    	TextView capacidadeMax;
    }
    
    public ProcessadorAdapter(Context context, ArrayList<Processador> Processadors) {
		  super(context, R.layout.list_view_item_infoprocessador,Processadors);
	      this.Processadors = Processadors;
	    }
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder = new ViewHolder();
		  if (convertView == null) {
			  LayoutInflater vi = LayoutInflater.from(getContext());
			  convertView = vi.inflate(R.layout.list_view_item_infoprocessador, parent, false);
			  holder.fabricante = (TextView) convertView.findViewById(R.id.tv_fabricantep);
	          holder.capacidade = (TextView) convertView.findViewById(R.id.tv_capacidadep);
	          holder.versao = (TextView) convertView.findViewById(R.id.tv_versaop);
	          holder.capacidadeMax = (TextView) convertView.findViewById(R.id.tv_capacidademaxp);
	          convertView.setTag(holder);
	        }
		  	Processador p = Processadors.get(position);
		  	holder.fabricante.setText(p.getFabricante());
		  	holder.versao.setText(p.getVersao());
		  	holder.capacidade.setText(p.getCapacidade());
		  	holder.capacidadeMax.setText(p.getCapacidadeMax());
		  return convertView;
	}

}
