package com.kofhearts.safe.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.kofhearts.safe.R;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryListActivity extends AppCompatActivity {


    private String [] entryTitles;
    private long [] entryIds;


    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener entryClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click

            Intent intent = new Intent(v.getContext(), EntryViewActivity.class);
            intent.putExtra("id", entryIds[position]);


            startActivity(intent);

        }
    };


    /**
     *
     * Initializes the list that will hold the items displayed by list view.
     *
     */


    public void initializeList(){


        Entry[] entries = Entry.list();


        entryTitles = new String[entries.length];
        entryIds = new long[entries.length];

        for(int i=0; i<entries.length; i++){
            entryTitles[i] = entries[i].getTitle();
            entryIds[i] = entries[i].getId();

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view_item, entryTitles);

        ListView listView = (ListView) findViewById(R.id.entry_list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(entryClickedHandler);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.entry_list_search);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);


        ActiveRecord.initialize(this, false);


        initializeList();



        EditText search = (EditText)findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                Entry[] entries = Entry.list();

                List<Long> ids = new ArrayList<Long>();

                for(Entry e: entries){

                    if (e.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            e.getContent().toLowerCase().contains(charSequence.toString().toLowerCase())
                            ){

                        ids.add(e.getId());

                    }

                }



                Entry[] newEntries = new Entry[ids.size()];

                for(int j=0; j<ids.size(); j++){

                    try {

                        newEntries[j] = Entry.get(ids.get(j));

                    }
                    catch (NoRecordFoundException e){

                    }

                }



                entryTitles = new String[newEntries.length];
                entryIds = new long[newEntries.length];

                for(int k=0; k<newEntries.length; k++){

                        entryTitles[k] = newEntries[k].getTitle();
                        entryIds[k] = newEntries[k].getId();


                }


                ArrayAdapter adapter = new ArrayAdapter<String>(EntryListActivity.this, R.layout.activity_list_view_item, entryTitles);

                ListView listView = (ListView) findViewById(R.id.entry_list);
                listView.setAdapter(adapter);

                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_list_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.new_entry:

                Intent intent = new Intent(this, NewEntryActivity.class);
                startActivity(intent);
                return true;


        }

        return true;
    }


}
