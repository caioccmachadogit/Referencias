package in.wptrafficanalyzer.actionbarupnav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("in.wptrafficanalyzer.actionbarupnav.second");				
				startActivity(intent);
			}
		};
        
        
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(listener);
        
    }
}