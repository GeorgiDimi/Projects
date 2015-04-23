package com.example.passwordkeeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.app.ListActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

public class DisplayMessageActivity extends ListActivity implements OnItemClickListener{
    private PasswdAdapter mDbHelper = new PasswdAdapter(this);
    ListView lv;
    String oldPass, newPass,password;
    ArrayAdapter<String> arrayAdapter;
    Cursor c;
	public static final String PREFS_NAME = "MyPasswordsFile";
    private static final int DELETE_ID = 5;
    private static final int CHANGE_ID = 6;
    private static final int DELETE_ID2 = Menu.FIRST+1;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT=1;
	private static final int ACTIVITY_EXIT = 3;
	private static final int ACTIVITY_CHANGE = 4;
	private static final int ACTIVITY_FAILED = 5;
	public final static String EXTRA_PASSWORD = "com.example.myfirstapp.MESSAGE";
	private static final int BACKUP_ID = Menu.FIRST+1;
	private static final int READ_ID = 7;
	private Context context;	
    private static final String LIST_STATE = "listState";
    private static final String LIST_POS = "listPosition";
    private Parcelable mListState = null;
    private int mListPos = 0;
    
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mListState = state.getParcelable(LIST_STATE);
        lv = (ListView)findViewById(android.R.id.list);
        int pos = state.getParcelable(LIST_POS);
        lv.scrollTo(0,pos); 
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        fillData();
        lv = (ListView)findViewById(android.R.id.list);
        if (mListState != null)
            lv.onRestoreInstanceState(mListState);
        mListState = null;
    }
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        lv = (ListView)findViewById(android.R.id.list);
        mListState = getListView().onSaveInstanceState();
        mListPos = getListView().getScrollY();// .getPositionForView(lv);
        state.putParcelable(LIST_STATE, mListState);
        state.putInt(LIST_POS, mListPos);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
        mDbHelper.open();
        fillData();
        Log.v("GGGGGGGGGGGGGGG","GGGGGGGGGGGGGGGGGG");
        context = getApplicationContext();
        registerForContextMenu(findViewById(android.R.id.list));
		// Show the Up button in the action bar.
		setupActionBar();
//		Intent intent = getIntent();
//		password = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		lv = (ListView)findViewById(android.R.id.list);
		lv.setOnItemClickListener(new OnItemClickListener() {
		            @Override
					public void onItemClick(AdapterView<?> arg0, View view,
		                    int position, long id) {
				        		begin(position, id);
		            		}   
		        	});
	}
	public void begin(int position, long id){
		Intent i2 = new Intent(this, NoteEditActivity.class);
		Cursor c2 = c;
    	c2.moveToPosition(position);
        i2.putExtra(PasswdAdapter.KEY_ROWID, id);
        i2.putExtra(PasswdAdapter.KEY_TITLE, c2.getString(
                c2.getColumnIndexOrThrow(PasswdAdapter.KEY_TITLE)));
        i2.putExtra(PasswdAdapter.KEY_USERNAME, c2.getString(
                c2.getColumnIndexOrThrow(PasswdAdapter.KEY_USERNAME)));
        i2.putExtra(PasswdAdapter.KEY_PASSWORD, c2.getString(
                c2.getColumnIndexOrThrow(PasswdAdapter.KEY_PASSWORD)));
        i2.putExtra(PasswdAdapter.KEY_EXTRA, c2.getString(
                c2.getColumnIndexOrThrow(PasswdAdapter.KEY_EXTRA)));
        startActivityForResult(i2, ACTIVITY_EDIT);		
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID2, 0, R.string.delete);
    }
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(false);
		}
	}
	// to add items when you press the menu key beside the home button
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		boolean result = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.display_message, menu);
        menu.add(0, DELETE_ID, 0, R.string.delete);
        menu.add(0, CHANGE_ID, 0, R.string.change);
        menu.add(0,BACKUP_ID,0,R.string.backup);
        menu.add(0,READ_ID,0,R.string.read);
		return result;
	}
	@Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID2:
//            	onSaveInstanceState();
            	String title = "blaaa";
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                Cursor c3 = c;
                c3.moveToPosition(info.position);
                title = c3.getString(
                        c3.getColumnIndexOrThrow(PasswdAdapter.KEY_TITLE));
                mDbHelper.deleteNote(title);
//                fillData();
                onResume();
                return true;
        }
        return super.onContextItemSelected(item);
    }
	// when you click my message at the top it uses this method to go somewhere
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.item1:	
			createNote();
    		return true;
    		//this is to "delete all" messages option which is disabled
		case DELETE_ID:
			mDbHelper.deleteALL();
			fillData();
//			onResume();
			return true;
		case BACKUP_ID:
			backup();
			return true;
		case READ_ID:
			read();
			return true;
		case CHANGE_ID:
			Intent intent3 = new Intent(this,ChangePassActivity.class);
			startActivityForResult(intent3,ACTIVITY_CHANGE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}	
	
	public void read() //reads from a txt file
	{
		int gg=1;
		//External storage read
		File newF = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"PasswordKeeper.txt");
		StringBuilder text = new StringBuilder();
		StringBuilder word = new StringBuilder();
		StringBuilder extraw = new StringBuilder();
//		StringBuilder tempT = new StringBuilder();
		StringBuilder tempU = new StringBuilder();
		StringBuilder tempP = new StringBuilder();
		try{
			BufferedReader br = new BufferedReader(new FileReader(newF));
		    int column = 1,index = 0,listL = 0,length;
		    String freshString,line;
		    while((line = br.readLine())!=null)
		    {
		    	text.append(line);
		        freshString = text.toString();
		        length = freshString.length();
		        if(length>0 &&freshString.charAt(0)=='|')
		        {
		        	listL++;
		        }
		        text = new StringBuilder();		        
		    }
		    br.close();
		    br = new BufferedReader(new FileReader(newF));
		    text = new StringBuilder();
		    Toast.makeText(context, "number of records "+listL, Toast.LENGTH_SHORT).show();
//		    String[] title = new String[listL+1], username = new String[listL+1], password = new String[listL+1], extra = new String[listL+1];
		    String[] title = new String[listL], username = new String[listL], password = new String[listL], extra = new String[listL];

		    while ((line = br.readLine()) != null ) {
		    	int s=0,f=0;		    	
		        text.append(line); // appends the line to "text" which is a string builder empty variable
		        freshString = text.toString(); // 
		        length = freshString.length(); // gets the length of the string
		        if(length>0 && freshString.charAt(0)=='|') // means a new entry is found (they are separated by the | char) 
		        {
		        	column=1;
		        	index++;
		        	gg=1;
		        	extraw = new StringBuilder();
		        }
		        if(length>0&&freshString.charAt(0)!='|')//&&freshString.charAt(0)!='\n')
		        {
		        	while(s<length&& length!=0)
		        	{
		        		while((f+1)<length && freshString.charAt(f+1)!='\t')
		        		{
		        			f++;	   
		        		}		        		
		        		for(;s<=f&&s<length;s++)
		        		{
		        			word.append(freshString.charAt(s));		        			
		        		}
		        		if(column==1)
		        		{
		        			title[index] = word.toString(); 
		        			tempU = new StringBuilder();
		        		}
		        		else if(column==2)
		        		{
		        			tempU.append(word);
		        			tempU.append(' ');
		        			username[index]=tempU.toString();
		        			tempP = new StringBuilder();
		        		}
		        		else if (column==3)
		        		{
		        			tempP.append(word);
		        			tempP.append(' ');
		        			password[index]=tempP.toString();
		        		}
		        		else if (column==4)
		        		{
		        			if(gg==1)
		        			{
		        				extraw = word;
		        				gg++;
		        			}
		        			else if(gg>1)
		        			{
		        				extraw.append('\n');
		        				extraw.append(word);		        				
		        			}
		        			extra[index]=extraw.toString();		        		
		        		}		
		        		if(column<4&&f+2<length&&freshString.charAt(f+1)=='\t')
		        			column++;
		        		if(f+2<length)
		        		{
		        			s=f+2;f=s;
		        			Log.v("s is "+s,"length is "+ length);
		        		}
		        		else if (f+2==length)
		        		{
		        			s++;s++;
		        		}
		        		else if (f+1==length)
		        			s++;		        		
		        		word = new StringBuilder(); // causing allocation error!!!!!!!!!!!!!!!!!!!!!!!!
		        	}		        	
		        }
		        text = new StringBuilder(); // clears the sb
//		        text.append('\n'); // added to the end
		    }
		    br.close();
		    Toast.makeText(context, "done processing", Toast.LENGTH_SHORT).show();
		    mDbHelper.createALLNote(title, username, password, extra);
		}
		catch (IOException e) {}	
	}
	public void backup()
	{
		// this saves to a downloads directory
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"PasswordKeeper.txt");
		
		//this saves to a private directory that is NOT accesible by the user or by other apps
//		String path = context.getFilesDir().getAbsolutePath();
//		File file = new File(path + "/my-file-name.txt");
		
		FileOutputStream stream;
		try{
			stream = new FileOutputStream(file);
			c = mDbHelper.fetchAllNotes();
	    	startManagingCursor(c);
	    	if (c.moveToFirst()) {
	            do {	            	
	            	stream.write((c.getString(1)).getBytes());
	            	stream.write('\t');
	            	stream.write((c.getString(2)).getBytes());
	            	stream.write('\t');
	            	stream.write((c.getString(3)).getBytes());
	            	stream.write('\t');
	            	if(c.getString(4)==null)
	            		stream.write(' ');
	            	else
	            		stream.write((c.getString(4)).getBytes());
	            	stream.write('\n'); 
	            	stream.write("|\n".getBytes()); // denotes new entry
	            } while (c.moveToNext());
	        }			
			stream.close();
		}
		catch(Exception e){}		
	}
	public void createNote() {		
		Intent intent2 = new Intent(this, NoteEditActivity.class);
		startActivityForResult(intent2,ACTIVITY_CREATE);
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	        Bundle extras = intent.getExtras();
	        switch(requestCode) {
	            case ACTIVITY_CREATE:
	                String title = extras.getString(PasswdAdapter.KEY_TITLE);
	                String username = extras.getString(PasswdAdapter.KEY_USERNAME);
	                String password = extras.getString(PasswdAdapter.KEY_PASSWORD);
	                String extra = extras.getString(PasswdAdapter.KEY_EXTRA);
	                if(title!=null)
	                	mDbHelper.createNote(title, username, password, extra);
	                if(!PasswdAdapter.dbStatus)
	                	Toast.makeText(this, "Duplicate Entry" , Toast.LENGTH_LONG).show();
	                fillData();
	                break;
	            case ACTIVITY_EDIT:
	                Long rowId = extras.getLong(PasswdAdapter.KEY_ROWID);
	                if (rowId != null) {
	                    String editTitle = extras.getString(PasswdAdapter.KEY_TITLE);
	                    String editUsername = extras.getString(PasswdAdapter.KEY_USERNAME);
	                    String editPassword = extras.getString(PasswdAdapter.KEY_PASSWORD);
	                    String editExtra = extras.getString(PasswdAdapter.KEY_EXTRA);
	                    String oldTitle = extras.getString(PasswdAdapter.KEY_OLDTITLE);
	                    mDbHelper.updateNote(rowId, editTitle, editUsername,editPassword,editExtra,oldTitle );
	                    if(!PasswdAdapter.dbStatus)
		                	Toast.makeText(this, "Duplicate Entry" , Toast.LENGTH_LONG).show();
	                    fillData();
	                }
	                fillData();
	                break;
	            case ACTIVITY_EXIT:
//	            	fillData();
	            	onResume();
	            	break;
	        }
	        switch (resultCode) {
	        case ACTIVITY_FAILED:
//	        	fillData();
	        	onResume();
            	break;
	        case ACTIVITY_EXIT:
//	        	fillData();
	        	onResume();
	        	break;
	        }
	    }
	public void fillData() {
    	c = mDbHelper.fetchAllNotes();
    	List<String> all = new ArrayList<String>();
    	startManagingCursor(c);
    	if (c.moveToFirst()) {
            do {
                all.add(c.getString(1));
            } while (c.moveToNext());
        }
    	lv = (ListView)findViewById(android.R.id.list);
    	arrayAdapter = new ArrayAdapter<String>(DisplayMessageActivity.this, android.R.layout.simple_list_item_1,all );
    	lv.setAdapter(arrayAdapter);
    	
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		
	}
}
