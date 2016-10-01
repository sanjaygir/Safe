package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kofhearts.safe.R;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

public class NewEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        ActiveRecord.initialize(this, false);

    }

    /**
     *
     * Saves the new entry.
     *
     * @param view
     */

    public void handleSave(View view){

        EditText title = (EditText)findViewById(R.id.title);
        EditText content = (EditText)findViewById(R.id.content);

        String titleValue = title.getText().toString();
        String contentValue = content.getText().toString();

        Entry.create(titleValue, contentValue);

        Toast toast = Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT);
        toast.show();



    }

}
