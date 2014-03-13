package com.littlenote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Haslo extends Activity {
	private static final String PREFERENCES_NAME = "myPreferences";
	private SharedPreferences preferences;
	private static final String MOTYW = "motyw";
	private static final String HAS£O_GLOWNE = "haslo";
    Bundle extras;
	 EditText haslo1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		if(preferences.getInt(MOTYW, 0)==0)
	        setTheme(android.R.style.Theme_Holo_Dialog);
	        else
	        setTheme(android.R.style.Theme_Holo_Light_Dialog);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_haslo);
		extras = getIntent().getExtras();
		haslo1 = (EditText) findViewById(R.id.editText1);
	}

	public void Anuluj(View target){
		finish();
	}
	
	public void Ok(View target){
		if(haslo1.getText().toString().matches(preferences.getString(HAS£O_GLOWNE, "0000")))
		{
			Intent i = new Intent(this, Edit.class);
    		i.putExtra(NotesDbAdapter.KEY_ROWID, extras.getLong("_id",0));
    		i.putExtra("Edycja", 1);
    		startActivity(i);
		}
		else
			Toast.makeText(getApplicationContext(), "Has³o nieprawid³owe!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.haslo, menu);
		return true;
	}

}
