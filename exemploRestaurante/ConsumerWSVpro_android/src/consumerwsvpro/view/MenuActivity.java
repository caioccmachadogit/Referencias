package consumerwsvpro.view;

import com.example.consumerwsvpro.R;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MenuActivity extends Activity{
	GridView gv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		gv = (GridView) findViewById(R.id.gridView_menu);
		MenuAdapter mAdapter = new MenuAdapter(this);
		gv.setAdapter(mAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	int p = position;
            	switch (p) {
				case 0:
					startActivity(new Intent(getApplicationContext(), StatusActivity.class));
					break;
				case 1:
					startActivity(new Intent(getApplicationContext(), BiosActivity.class));
					break;
				case 2:
					startActivity(new Intent(getApplicationContext(), MaquinaActivity.class));
					break;
				case 3:
					startActivity(new Intent(getApplicationContext(), LigarActivity.class));
					break;
				case 4:
					startActivity(new Intent(getApplicationContext(), DesligarActivity.class));
					break;
				case 5:
					startActivity(new Intent(getApplicationContext(), ReiniciarActivity.class));
					break;
				case 6:
					startActivity(new Intent(getApplicationContext(), DiscoActivity.class));
					break;
				case 7:
					startActivity(new Intent(getApplicationContext(), MemoriaActivity.class));
					break;
				case 8:
					startActivity(new Intent(getApplicationContext(), ProcessadorActivity.class));
					break;

				default:
					break;
				}
            }

			
        });
	}
		
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu, menu);
	    return true;
	}*/
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.func_status:
	        	startActivity(new Intent(this, StatusActivity.class));
	            return true;
	        case R.id.func_ligar:
	        	startActivity(new Intent(this, LigarActivity.class));
	            return true;
	        case R.id.func_desligar:
	        	startActivity(new Intent(this, DesligarActivity.class));
	            return true;
	        case R.id.func_reiniciar:
	        	startActivity(new Intent(this, ReiniciarActivity.class));
	            return true;
	        case R.id.info_disco:
	        	startActivity(new Intent(this, DiscoActivity.class));
	            return true;
	        case R.id.info_memoria:
	        	startActivity(new Intent(this, MemoriaActivity.class));
	            return true;
	        case R.id.info_processador:
	        	startActivity(new Intent(this, ProcessadorActivity.class));
	            return true;
	        case R.id.info_bios:
	        	startActivity(new Intent(this, BiosActivity.class));
	            return true;
	        case R.id.info_maquina:
	        	startActivity(new Intent(this, MaquinaActivity.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}*/

}
