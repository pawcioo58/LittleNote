package com.littlenote;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Nowe_haslo extends Activity {
EditText haslo1, haslo2;
private static final String PREFERENCES_NAME = "myPreferences";
private SharedPreferences preferences;
private static final String MOTYW = "motyw";
private static final String HAS£O_GLOWNE = "haslo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		if(preferences.getInt(MOTYW, 0)==0)
	        setTheme(android.R.style.Theme_Holo_Dialog);
	        else
	        setTheme(android.R.style.Theme_Holo_Light_Dialog);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nowe_haslo);
		haslo1 = (EditText) findViewById(R.id.editText1);
		haslo2 = (EditText) findViewById(R.id.editText1);
		
	}
	
	public void Ok(View target)
	{
		if(haslo1.getText().toString().matches(haslo2.getText().toString())){
		SharedPreferences.Editor preferencesEditor = preferences.edit();
	    preferencesEditor.putString(HAS£O_GLOWNE, haslo1.getText().toString());
	    preferencesEditor.commit();}
		else
			Toast.makeText(getApplicationContext(), "Has³a s¹ ró¿ne!", Toast.LENGTH_SHORT).show();
	}
	public void Anuluj(View target){
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nowe_haslo, menu);
		return true;
	}

}
