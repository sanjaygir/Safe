package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kofhearts.safe.R;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Password;


/**
 *
 * Represents Change Password Screen.
 *
 */


public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActiveRecord.initialize(this, false);

    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_password_menu, menu);
        return true;
    }

    /**
     *
     * Handles menu click. Handles the case when user clicks on Forgot Password. A toast message is displayed that shows the hint.
     *
     * @param item Menu item clicked
     * @return Boolean
     */

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.forgot_password:

                try {

                    Toast.makeText(this, "Hint: " + Password.first().getHint() , Toast.LENGTH_LONG).show();

                }
                catch(NoRecordFoundException e){

                    Toast.makeText(this, "No email found!", Toast.LENGTH_SHORT).show();
                    return false;
                }


                return true;
        }

        return false;
    }


    /**
     * Changes the old password.
     *
     * @param view View
     */

    public void handleChangePassword(View view){


        EditText oldPassword = (EditText) findViewById(R.id.old_password);
        String olds = oldPassword.getText().toString();

        EditText newPassword = (EditText) findViewById(R.id.new_password);
        String news = newPassword.getText().toString();

        EditText hint = (EditText) findViewById(R.id.change_hint);
        String hin = hint.getText().toString();


        try {

            String oldFromDatabase = Password.first().getPassword();

            if(oldFromDatabase.equals(olds) && !news.isEmpty()){

                Password pa = Password.first();
                pa.setPassword(news);
                pa.setHint(hin);

                pa.save();

                Toast toast = Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT);
                toast.show();

                super.onBackPressed();

            }
            else{

                Toast toast = Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT);
                toast.show();

            }


        }
        catch (NoRecordFoundException e){


            Log.e("ChangePasswordActivity",  "Error when changing password");

        }



    }

}
