package consumerwsvpro.view;

import java.util.ArrayList;
import java.util.List;

import com.example.consumerwsvpro.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MenuAdapter extends BaseAdapter{
	private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;
	
	public MenuAdapter(Context context){
		inflater = LayoutInflater.from(context);

        items.add(new Item("Status",R.drawable.ic_status));
        items.add(new Item("Bios",      R.drawable.ic_bios));
        items.add(new Item("Máquina",      R.drawable.ic_pc));
        
        items.add(new Item("Ligar", R.drawable.ic_on));
        items.add(new Item("Desligar", R.drawable.ic_off));
        items.add(new Item("Reiniciar", R.drawable.ic_recicle));
        
        items.add(new Item("Disco",     R.drawable.ic_hd));
        items.add(new Item("Memória",      R.drawable.ic_ram));
        items.add(new Item("Processador",R.drawable.ic_processor));
	}

	@Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).drawableId;
    }

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View v = view;
        ImageView picture;
        TextView name;

        if(v == null) {
            v = inflater.inflate(R.layout.grid_view_item_menu, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        Item item = (Item)getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
	}
	
	private class Item {
        final String name;
        final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }

}
