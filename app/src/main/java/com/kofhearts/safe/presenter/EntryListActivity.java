package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kofhearts.safe.R;

public class EntryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.entry_list_search);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

        String [] entries = {"a", "asd", "qwe", "erty", "fgh"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view_item, entries);

        ListView listView = (ListView) findViewById(R.id.entry_list);
        listView.setAdapter(adapter);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_list_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }


}
