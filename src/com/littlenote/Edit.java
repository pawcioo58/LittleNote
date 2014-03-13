package com.littlenote;



import java.util.Calendar;

import com.littlenote.NotesDbAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends Activity {
    private EditText mBodyText;
    private Long mRowId;
    private String tex,tyt;
    private static final String Edytor_light = "edit_light";
    private static final String ROZMIAR_CZCIONKI = "czcionka";
    private static final String MOTYW = "motyw";
    private static final String POZYCJA_KURSORA = "kursor";
    private SharedPreferences preferences;
    private static final String PREFERENCES_NAME = "myPreferences";
    Bundle extras;
    Calendar calendar;
	String min,msc,day,h;
	
	private NotesDbAdapter mDbHelper;
	private Cursor mNotesCursor;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        if(preferences.getInt(MOTYW, 0)==1||preferences.getBoolean(Edytor_light, false))
        setTheme(R.style.Light_style);
        else
        setTheme(R.style.Dark_style);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
		
        mBodyText = (EditText) findViewById(R.id.editText2);
		Log.d("Little", "Rozmiar czcionki"+preferences.getInt(ROZMIAR_CZCIONKI, 0));
		switch(preferences.getInt(ROZMIAR_CZCIONKI, 0)){
			case 0:mBodyText.setTextSize(TypedValue.COMPLEX_UNIT_PX,12);break;
			case 1:mBodyText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);;break;
			case 2:mBodyText.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);break;
			case 3:mBodyText.setTextSize(TypedValue.COMPLEX_UNIT_PX,28);break;
		}
		
        mRowId = null;
        extras = getIntent().getExtras();
        if (extras != null) {
            
            mRowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
            mNotesCursor = mDbHelper.fetchNote(mRowId);
            String body = mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_BODY));
            if (body != null) {
                mBodyText.setText(body);
                if(preferences.getInt(POZYCJA_KURSORA, 1)==1)
                mBodyText.setSelection(body.length());
            }
        }

	}
	
	public void off(View target){
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}
	
	public void btn_out(View target){
	zamknij();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	     {	    	
	         zamknij();
	     }
	    return super.onKeyDown(keyCode, event);
	}
	
	private void zamknij() {
			tex = mBodyText.getText().toString();
			if (tex.matches("")){
									Toast.makeText(getApplicationContext(), 
									"Nie zapisano", 
									Toast.LENGTH_SHORT).show();
									finish();
								}
			else{
					if(tex.split("\n").length>1){
								tyt = tex.substring(0,tex.indexOf("\n"));
								Log.d("LittleNote", "Pobieranie linii, iloœæ: "+tex.split("\n").length);
					}
					else{
							int i;
							if (tex.length()>79)
									i = 80;
							else
									i = tex.length();
							Log.d("LittleNote", "Pobieranie znaków, iloœæ: "+i);
							tyt = tex.substring(0,i);
						}
					
	        
	        
	        
	        ////////////////////////////////////////////////////////////
	        if(mRowId==null){
	        	Log.d("Little", "Nowa notatka");
	        	String title = tyt;
	        	String body = mBodyText.getText().toString();
	        	create_new_note(title,body);
	        }
	        else{
            if (mRowId != null) {
            	calendar = Calendar.getInstance(); 
            	if(calendar.get(Calendar.MINUTE)<10)min="0"+String.valueOf(calendar.get(Calendar.MINUTE)); 
            	else min=String.valueOf(calendar.get(Calendar.MINUTE));
            	if(calendar.get(Calendar.HOUR_OF_DAY)<10)h="0"+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)); 
            	else h=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            	if(calendar.get(Calendar.MONTH)<10)msc="0"+String.valueOf(calendar.get(Calendar.MONTH)+1); 
            	else msc=String.valueOf(calendar.get(Calendar.MONTH)+1);
            	if(calendar.get(Calendar.DAY_OF_MONTH)<10)day="0"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)); 
            	else day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            	mNotesCursor = mDbHelper.fetchNote(mRowId);
            	String data2 = day+"." + msc+"."+String.valueOf(calendar.get(Calendar.YEAR))+"  "+h+":"+min;;
                String editTitle = mNotesCursor.getString(mNotesCursor.getColumnIndex(NotesDbAdapter.KEY_TITLE));
                String editBody = mBodyText.getText().toString();
                if(!mNotesCursor.getString(mNotesCursor.getColumnIndex(mDbHelper.KEY_BODY)).matches(editBody))
                {
                	Toast.makeText(getApplicationContext(), "Zmieniono", Toast.LENGTH_SHORT).show();
                	mDbHelper.updateNote(mRowId, tyt, mBodyText.getText().toString(), data2);
                }
	        }
	       }
	        
	        
	        finish();
	}
     
	}
	private void create_new_note(String title, String body) {
		calendar = Calendar.getInstance(); 
    	if(calendar.get(Calendar.MINUTE)<10)min="0"+String.valueOf(calendar.get(Calendar.MINUTE)); 
    	else min=String.valueOf(calendar.get(Calendar.MINUTE));
    	if(calendar.get(Calendar.HOUR_OF_DAY)<10)h="0"+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)); 
    	else h=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    	if(calendar.get(Calendar.MONTH)<10)msc="0"+String.valueOf(calendar.get(Calendar.MONTH)+1); 
    	else msc=String.valueOf(calendar.get(Calendar.MONTH)+1);
    	if(calendar.get(Calendar.DAY_OF_MONTH)<10)day="0"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)); 
    	else day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String data = day+"." + msc+"."+String.valueOf(calendar.get(Calendar.YEAR))+"  "+h+":"+min;
        mDbHelper.createNote(title, body, data, data,0);
	}
	
}
