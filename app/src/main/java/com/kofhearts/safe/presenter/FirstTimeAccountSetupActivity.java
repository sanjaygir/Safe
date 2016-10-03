package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kofhearts.safe.R;
import com.kofhearts.safe.model.Password;



/**
 *
 * Represents Account Setup Screen.
 *
 */


public class FirstTimeAccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_account_setup);
    }


    /**
     *
     * Creates the account.
     *
     * @param view
     */

    public void handleSetAccount(View view) {

        //First time so create the password

        EditText firstPassword = (EditText) findViewById(R.id.first_time_password);
        String pa = firstPassword.getText().toString();

        EditText hint = (EditText) findViewById(R.id.hint);
        String hi = hint.getText().toString();

        Password.create("", pa, hi);

        Toast toast = Toast.makeText(this, "New Password Set", Toast.LENGTH_SHORT);
        toast.show();

        super.onBackPressed();


    }






}
