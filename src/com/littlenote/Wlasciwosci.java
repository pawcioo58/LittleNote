package com.littlenote;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Wlasciwosci extends Activity {
TextView tytul, linie, litery, data_add, data_edit, blokada;
private static final String PREFERENCES_NAME = "myPreferences";
private static final String MOTYW = "motyw";
private SharedPreferences preferences;

private NotesDbAdapter mDbHelper;
private Cursor mNotesCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        if(preferences.getInt(MOTYW, 0)==0)
        setTheme(android.R.style.Theme_Holo_Dialog);
        else
        setTheme(android.R.style.Theme_Holo_Light_Dialog);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wlasciwosci);
		
		tytul = (TextView) findViewById(R.id.textView2);
		linie = (TextView) findViewById(R.id.textView4);
		litery = (TextView) findViewById(R.id.textView6);
		data_add = (TextView) findViewById(R.id.textView8);
		data_edit = (TextView) findViewById(R.id.TextView01);
		blokada = (TextView) findViewById(R.id.TextView03);
		
		mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        
        Bundle extras = getIntent().getExtras();
        
        mNotesCursor = mDbHelper.fetchNote(extras.getLong("id",0));
        String blokada_not, text;
        text = mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_BODY));
        int znaki = text.length();
        int linie_not = text.split("\n").length;
        if (mNotesCursor.getInt(mNotesCursor.getColumnIndex(mDbHelper.KEY_Haslo))==0)
        blokada_not="Nie";
        else
        	blokada_not="Tak";
        tytul.setText(mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_TITLE)));
        data_add.setText(mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_Data_Add)));
        data_edit.setText(mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_Data)));
        litery.setText(String.valueOf(znaki));
        linie.setText(String.valueOf(linie_not));
        blokada.setText(blokada_not);
        
	}

	public void Ok(View target){
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wlasciwosci, menu);
		return true;
	}

}
