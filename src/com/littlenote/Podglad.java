package com.littlenote;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Podglad extends Activity {
TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podglad);
		
		text = (TextView) findViewById(R.id.textView2);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String body = extras.getString(NotesDbAdapter.KEY_BODY);

            if (body != null) {
                text.setText(body);
            }
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.podglad, menu);
		return true;
	}
	public void btn_out(View target){
		finish();
	}

}
