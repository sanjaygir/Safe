package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kofhearts.safe.R;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Password;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActiveRecord.initialize(this, false);

    }



    public void handleChangePassword(View view){



        EditText oldPassword = (EditText) findViewById(R.id.old_password);
        String olds = oldPassword.getText().toString();

        EditText newPassword = (EditText) findViewById(R.id.new_password);
        String news = newPassword.getText().toString();


        if(Password.getTotalCount() == 0){

            //First time so create the password

            Log.w("asd", "here");

            Password.create(news);


            Toast toast = Toast.makeText(this, "New Password Set", Toast.LENGTH_SHORT);
            toast.show();

        }
        else{


            Log.w("count", String.valueOf(Password.getTotalCount()));
            //Changing an existing password

            try {

                String oldFromDatabase = Password.first().getPassword();

                if(oldFromDatabase.equals(olds) && !news.isEmpty()){

                    Password.first().delete();

                    Password.create(news);



                    Toast toast = Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT);
                    toast.show();

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







        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

    }

}
