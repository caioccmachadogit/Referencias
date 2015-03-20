package pack.app.organizadormercado;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button salvar = (Button) findViewById(R.id.categoria);
		salvar.setOnClickListener(invocarCategoria);
	}

	private OnClickListener invocarCategoria = new OnClickListener() {

		public void onClick(View arg0) {
			//Intent i = new Intent(MainActivity.this, ListCategoria.class);
			startActivity(new Intent("android.intent.action.LISTVIEW"));
			finish();
		}
	};
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
