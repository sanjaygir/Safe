package com.kofhearts.safe.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActiveRecord.initialize(this, false);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.change_password:

                if(Password.getTotalCount() == 0){

                    Intent intent = new Intent(this, FirstTimeAccountSetupActivity.class);
                    startActivity(intent);
                    return true;

                }
                else{

                    Intent intent = new Intent(this, ChangePasswordActivity.class);
                    startActivity(intent);
                    return true;

                }

        }

        return false;
    }


    /**
     * Handles user login
     *
     * @param view
     */

    public void handleLogin(View view){


        EditText pass = (EditText) findViewById(R.id.login_password);

        String p = pass.getText().toString();


        try {

            String databasePass = Password.first().getPassword();


            if(databasePass.equals(p)){


                Intent intent = new Intent(this, EntryListActivity.class);
                startActivity(intent);


            }
            else{

                Toast toast = Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT);
                toast.show();

            }

        }
        catch(NoRecordFoundException e){


            Toast toast = Toast.makeText(this, "Login Error!", Toast.LENGTH_SHORT);
            toast.show();



        }



    }

}
