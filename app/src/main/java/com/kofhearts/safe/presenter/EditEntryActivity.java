package com.kofhearts.safe.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kofhearts.safe.R;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

public class EditEntryActivity extends AppCompatActivity {

    private long entryId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        ActiveRecord.initialize(this, false);

        entryId = getIntent().getExtras().getLong("id");


        Intent intent = new Intent();
        intent.putExtra("id", entryId);
        setResult(1, intent);


        EditText title = (EditText) findViewById(R.id.title_edit);
        EditText content = (EditText) findViewById(R.id.content_edit);

        try {
            Entry entry = Entry.get(entryId);

            title.setText(entry.getTitle());
            content.setText(entry.getContent());
        }
        catch(NoRecordFoundException e){

            Toast toast = Toast.makeText(this, "No record to show!", Toast.LENGTH_SHORT);
            toast.show();

        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }


    /**
     *
     * Updates the Entry
     *
     * @param view View
     */


    public void handleUpdate(View view){

        EditText title = (EditText) findViewById(R.id.title_edit);
        EditText content = (EditText) findViewById(R.id.content_edit);

        try {

            Entry entry = Entry.get(entryId);

            entry.setTitle(title.getText().toString());
            entry.setContent(content.getText().toString());

            entry.save();

            Toast toast = Toast.makeText(this, "Record updated successfully!", Toast.LENGTH_SHORT);
            toast.show();


        }
        catch (NoRecordFoundException e){


            Toast toast = Toast.makeText(this, "No record to update!", Toast.LENGTH_SHORT);
            toast.show();


        }


    }
}
