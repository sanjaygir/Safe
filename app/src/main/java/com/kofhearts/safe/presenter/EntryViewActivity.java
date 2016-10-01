package com.kofhearts.safe.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.kofhearts.safe.R;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

public class EntryViewActivity extends AppCompatActivity {

    private long entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);


        ActiveRecord.initialize(this, false);



        if(getIntent().getExtras() != null) {
            entryId = getIntent().getExtras().getLong("id");
        }


        TextView title = (TextView) findViewById(R.id.title_view);
        TextView content = (TextView) findViewById(R.id.content_view);



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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if(resultCode == 1) {
                long id =data.getExtras().getLong("id");
                //mTextView.setText(myStr);


                TextView title = (TextView) findViewById(R.id.title_view);
                TextView content = (TextView) findViewById(R.id.content_view);


                try {
                    title.setText(Entry.get(id).getTitle());
                    content.setText(Entry.get(id).getContent());
                }
                catch(NoRecordFoundException e){

                    Toast toast = Toast.makeText(this, "Error occurred fetching entry!", Toast.LENGTH_SHORT);
                    toast.show();
                }



            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_view_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.edit:
                Intent intent = new Intent(this, EditEntryActivity.class);
                intent.putExtra("id", entryId);
                startActivityForResult(intent, 1);
                return true;

            case R.id.delete:

                try {
                    Entry.get(entryId).delete();

                    Toast toast = Toast.makeText(this, "Entry deleted successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                catch(NoRecordFoundException e){

                    Toast toast = Toast.makeText(this, "Entry delete failed!", Toast.LENGTH_SHORT);
                    toast.show();

                }

                return true;
        }

        return false;
    }


}
