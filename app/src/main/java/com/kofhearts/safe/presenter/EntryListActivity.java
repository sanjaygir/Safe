package com.kofhearts.safe.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kofhearts.safe.R;

public class EntryListActivity extends AppCompatActivity {


    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener entryClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click

            Intent intent = new Intent(v.getContext(), EntryViewActivity.class);
            startActivity(intent);

        }
    };


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



        listView.setOnItemClickListener(entryClickedHandler);


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
