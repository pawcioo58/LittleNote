

package com.littlenote;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class Lista_notatek extends ListActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int PODGLAD_ID = Menu.FIRST + 1;
    private static final int EDYCJA_ID = Menu.FIRST + 2;
    private static final int BLOKADA_ID = Menu.FIRST + 3;
    private static final int WYSLIJ_ID = Menu.FIRST + 4;
    private static final int DUPLIKUJ_ID = Menu.FIRST + 5;
    private static final int USUN_ID = Menu.FIRST + 6;
    private static final int WLASCIWOSCI_ID = Menu.FIRST + 7;
    private static final int USTAWIENIA_ID = Menu.FIRST + 8;
    private static final int USUN_WSZYSTKIE_ID = Menu.FIRST + 9;
    private static final int INFO_O_PROGRAMIE_ID = Menu.FIRST + 10;
    
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String HAS£O_GLOWNE = "haslo";
    private static final String MOTYW = "motyw";
boolean edycja=false;
    private NotesDbAdapter mDbHelper;
    private Cursor mNotesCursor;
    Cursor c;
    long a;
    Intent i;
    TextView title;
    Button add, add2;
    private SharedPreferences preferences;
    
    Calendar calendar;
	String min,msc,day,h;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        if(preferences.getInt(MOTYW, 0)==0)
        setTheme(R.style.Dark_style);
        else
        setTheme(R.style.Light_style);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
Log.d("Little", "1");
        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
        Log.d("Little", "2");
        add = (Button) findViewById(R.id.button1);
        add2 = (Button) findViewById(R.id.Button2);
        Log.d("Little", "3");
        title = (TextView) findViewById(R.id.textView1);
        Log.d("Little", "4");
        add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				createNote();
			}
		});
        Log.d("Little", "5");
add2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(edycja){
					edycja=false;
					title.setText("LittleNote");
					add2.setText("+");
				}
				else
				createNote();
			}
		});
Log.d("Little", "6");
    }

    protected void createNote() {
    	Intent i = new Intent(this, Edit.class);
		startActivity(i);
		
	}

	@SuppressWarnings("deprecation")
	private void fillData() {
        // Get all of the rows from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(mNotesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{NotesDbAdapter.KEY_TITLE,NotesDbAdapter.KEY_Data};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.txtTitle2,R.id.textView1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.notes_row, mNotesCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, USTAWIENIA_ID, 0, "Ustawienia");
        menu.add(0, USUN_WSZYSTKIE_ID, 0, "Usuñ kilka");
        menu.add(0, INFO_O_PROGRAMIE_ID, 0, "Info");
        //menu.add(0, 2, 0, "Ustawienia");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case USTAWIENIA_ID:
                i = new Intent(Lista_notatek.this, Ustawienia.class);
                startActivity(i);
                return true;
            case USUN_WSZYSTKIE_ID:
            	
            	
            	new AlertDialog.Builder(this)
                .setTitle("Usuwanie notatek")
                .setMessage("Jesteœ pewien ¿e chcesz zacz¹æ usuwanie?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                    	title.setText("Usuwanie notatek");
                    	edycja=true;
                    	add2.setText("x");
                        // continue with delete
                    }
                 })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // do nothing
                    }
                 })
               // .setIcon(R.drawable.ic_dialog_alert)
                 .show();
            	return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Akcja");
        menu.add(0, PODGLAD_ID, 0, "Podgl¹d");
        menu.add(0, EDYCJA_ID, 0, "Edytuj");
        menu.add(0, BLOKADA_ID, 0, "Blokada");
        menu.add(0, WYSLIJ_ID, 0, "Udostêpnij");
        menu.add(0, DUPLIKUJ_ID, 0, "Duplikuj");
        menu.add(0, USUN_ID, 0, R.string.menu_delete);
        menu.add(0, WLASCIWOSCI_ID, 0, "W³aœciwoœci");
    }

    @SuppressWarnings("static-access")
	@Override
    public boolean onContextItemSelected(MenuItem item) {
    	final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
        case PODGLAD_ID:
        	///////////////////////////////////////
        	c = mNotesCursor;
            c.moveToPosition(info.position);
        	if(mNotesCursor.getInt(mNotesCursor.getColumnIndex(mDbHelper.KEY_Haslo))==0)
        	{
        		Intent i = new Intent(this, Podglad.class);
        		i.putExtra(NotesDbAdapter.KEY_BODY, c.getString(
        	            c.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        		startActivity(i);
        	}
        	
        	else{
        		
        			Intent i2 = new Intent(this, Haslo.class);
        			i2.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
        			i2.putExtra("task", 2);
        			i2.putExtra(NotesDbAdapter.KEY_BODY, c.getString(
            	            c.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        			startActivity(i2);
        		}
        	
        	
        ///////////////////////////////////
        	
        	return true;
        case EDYCJA_ID:
        	c = mNotesCursor;
            c.moveToPosition(info.position);
        	if(mNotesCursor.getInt(mNotesCursor.getColumnIndex(mDbHelper.KEY_Haslo))==0)
        	{
        		Intent i = new Intent(this, Edit.class);
        		i.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
        		i.putExtra("Edycja", 1);
        		startActivity(i);
        	}
        	
        	else{
        		
        			Intent i2 = new Intent(this, Haslo.class);
        			i2.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
        			i2.putExtra("task", 1);
        			startActivity(i2);
        		}
            
            return true;
        case BLOKADA_ID:
        	c = mNotesCursor;
            c.moveToPosition(info.position);
            if(c.getInt(c.getColumnIndex(NotesDbAdapter.KEY_Haslo))==0){
            	Log.d("LittleNote", "Dodano Has³o"+c.getInt(c.getColumnIndex(NotesDbAdapter.KEY_Haslo)));
            	mDbHelper.Add_haslo(info.id);
            }
            else{
            	Log.d("LittleNote", "Usuniêto Has³o"+c.getInt(c.getColumnIndex(NotesDbAdapter.KEY_Haslo)));
            	mDbHelper.Del_haslo(info.id);
            }
        fillData();
        	return true;
        case DUPLIKUJ_ID:
        	c=mNotesCursor;
        	c.moveToPosition(info.position);
        	String title = c.getString(c.getColumnIndex(mDbHelper.KEY_TITLE));
        	String body = c.getString(c.getColumnIndex(mDbHelper.KEY_BODY));
        	int blokada = c.getInt(c.getColumnIndex(mDbHelper.KEY_Haslo));
        	create_new_note(title,body, blokada);
        	fillData();
        	return true;
            case USUN_ID:
                new AlertDialog.Builder(this)
                .setTitle("Usuñ")
                .setMessage("Jesteœ pewien ¿e chcesz usun¹æ notatkê?\nTej operacji nie da siê cofn¹æ!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                    	c = mNotesCursor;
                        c.moveToPosition(info.position);
                        if(c.getInt(c.getColumnIndex(NotesDbAdapter.KEY_Haslo))==0){
                        	mDbHelper.deleteNote(info.id);
                        }
                        else{
                        	Intent i = new Intent(Lista_notatek.this, Haslo.class);
                    		i.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
                    		i.putExtra("task", 0);
                    		startActivity(i);
                        }
                    	
                        fillData();
                        // continue with delete
                    }
                 })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // do nothing
                    }
                 })
               // .setIcon(R.drawable.ic_dialog_alert)
                 .show();
                return true;
            case WLASCIWOSCI_ID:
            	Intent i = new Intent(Lista_notatek.this, Wlasciwosci.class);
            	i.putExtra("id", info.id);
            	startActivity(i);
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        if(edycja){
             c = mNotesCursor;
             c.moveToPosition(position);
             if(c.getInt(c.getColumnIndex(NotesDbAdapter.KEY_Haslo))==0){
             	mDbHelper.deleteNote(id);
             }
             else{
             	Intent i = new Intent(Lista_notatek.this, Haslo.class);
         		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
         		i.putExtra("task", 0);
         		startActivity(i);
             }
         	
             fillData();
        }
        else{
        	Cursor c = mNotesCursor;
            c.moveToPosition(position);
        	if(mNotesCursor.getInt(mNotesCursor.getColumnIndex(mDbHelper.KEY_Haslo))==0)
        	{
        		Intent i = new Intent(this, Edit.class);
        		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
        		i.putExtra("Edycja", 1);
        		startActivity(i);
        	}
        	
        	else{
        			Intent i2 = new Intent(this, Haslo.class);
        			i2.putExtra(NotesDbAdapter.KEY_ROWID, id);
        			i2.putExtra("task", 1);
        			startActivity(i2);
        		}
        }
    }

    

	private void create_new_note(String title, String body, int blokada) {
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
        mDbHelper.createNote(title, body, data, data,blokada);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
fillData();
		super.onResume();
	}
}
