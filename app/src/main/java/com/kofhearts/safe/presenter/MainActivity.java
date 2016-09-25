package com.kofhearts.safe.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kofhearts.safe.R;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Password;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveRecord.initialize(this, false);

        long id = Password.create("asd");

        Log.w("totalrecords", String.valueOf(Password.getTotalCount()));




    }
}
