package com.littlenote;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class Ustawienia extends Activity {
TextView czcionka, kursor, motyw;
 String[] array,array_kursor,array_motyw;
 String TAG="LittleNote2";
 CheckBox zabezpieczenie_haslo,edit_light;
 int rozmiar_czcionki_position=0, pozycia_kursora_position=0,motyw_position=0;
 private SharedPreferences preferences;
 
 // Static String Settings
 
 private static final String PREFERENCES_NAME = "myPreferences";
 private static final String ROZMIAR_CZCIONKI = "czcionka";
 private static final String POZYCJA_KURSORA = "kursor";
 private static final String HAS£O_GLOWNE = "haslo";
 private static final String DOMYSLNE_ZABEZPIECZENIE = "domyslne_haslo";
 private static final String MOTYW = "motyw";
 private static final String Edytor_light = "edit_light";
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        if(preferences.getInt(MOTYW, 0)==0)
        setTheme(R.style.Dark_style);
        else
        setTheme(R.style.Light_style);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ustawienia);
		czcionka = (TextView) findViewById(R.id.textView5);
		kursor = (TextView) findViewById(R.id.textView3);
		motyw = (TextView) findViewById(R.id.textView15);
 	   	array = getResources().getStringArray(R.array.Czcionka);
 	   array_kursor = getResources().getStringArray(R.array.Kursor);
 	  array_motyw = getResources().getStringArray(R.array.Motyw);
 	edit_light = (CheckBox) findViewById(R.id.checkBox3);
 	 zabezpieczenie_haslo = (CheckBox) findViewById(R.id.checkBox2);
 	odzczyt();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ustawienia, menu);
		return true;
	}
	public void off(View target){
		finish();
	}
	public void zmiana_motyw(View target){
		Dialog dialog =  onCreateDialog_motyw();
		dialog.show();
	}
public void czcionka(View target){
	Dialog dialog =  onCreateDialog();
	dialog.show();
}
public void kursor(View target){
	Dialog dialog =  onCreateDialog_kursor();
	dialog.show();
}

public void edytor_light(View target){
	Log.d(TAG, "Klikniêto edit_light");
if(edit_light.isChecked())
	edit_light.setChecked(false);
else
	edit_light.setChecked(true);
}
public void domyslne_zabezpieczenie(View target){
	Log.d(TAG, "Klikniêto Zabezpieczenie");
if(zabezpieczenie_haslo.isChecked())
	zabezpieczenie_haslo.setChecked(false);
else
	zabezpieczenie_haslo.setChecked(true);
}
public void haslo(View target){
	String haslo = preferences.getString(HAS£O_GLOWNE, "0000");
	if(haslo.matches("0000")){
		Intent i = new Intent(Ustawienia.this, Nowe_haslo.class);
		startActivity(i);
	}
	else{
		Intent i = new Intent(Ustawienia.this, Zmiana_Hasla.class);
		startActivity(i);
	}
}
public Dialog onCreateDialog() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Rozmiar czcionki")
	           .setItems(R.array.Czcionka, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	rozmiar_czcionki_position=which;
	            	   czcionka.setText(array[which]);
               }
           });
    	return builder.create();
	}
public Dialog onCreateDialog_kursor() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pozycja kursora")
           .setItems(R.array.Kursor, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
pozycia_kursora_position=which;
            	   kursor.setText(array_kursor[which]);
           }
       });
	return builder.create();
}  
public Dialog onCreateDialog_motyw() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Motyw")
           .setItems(R.array.Motyw, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
motyw_position=which;
            	   motyw.setText(array_motyw[which]);
           }
       });
	return builder.create();
}
public void zapisz(){
	SharedPreferences.Editor preferencesEditor = preferences.edit();
    preferencesEditor.putBoolean(DOMYSLNE_ZABEZPIECZENIE, zabezpieczenie_haslo.isChecked());
    preferencesEditor.putBoolean(Edytor_light, edit_light.isChecked());
    preferencesEditor.putInt(ROZMIAR_CZCIONKI, rozmiar_czcionki_position);
    preferencesEditor.putInt(POZYCJA_KURSORA, pozycia_kursora_position);
    preferencesEditor.putInt(MOTYW, motyw_position);
    preferencesEditor.commit();
}
public void odzczyt(){
	czcionka.setText(array[preferences.getInt(ROZMIAR_CZCIONKI, 0)]);
	kursor.setText(array_kursor[preferences.getInt(POZYCJA_KURSORA, 0)]);
	motyw.setText(array_motyw[preferences.getInt(MOTYW, 0)]);
	zabezpieczenie_haslo.setChecked(preferences.getBoolean(DOMYSLNE_ZABEZPIECZENIE, false));
	edit_light.setChecked(preferences.getBoolean(Edytor_light, false));
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event)  
{
     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
     {	    	
    	 zapisz();
     }
    return super.onKeyDown(keyCode, event);
}
}
