package in.wptrafficanalyzer.actionbarupnav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class SecondActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.second_layout);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
			case android.R.id.home:
				Intent intent = new Intent(this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
		}	
		return true;
	}
}
