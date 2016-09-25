package com.kofhearts.safe;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kofhearts.safe.data.SafeDbHelperReal;
import com.kofhearts.safe.data.SafeDbHelperTest;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;
import com.kofhearts.safe.model.Password;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Sanjay1 on 9/25/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PasswordModelInstrumentedTest {


    @Before
    public void setUp(){

        Context appContext = InstrumentationRegistry.getTargetContext();

        appContext.deleteDatabase(SafeDbHelperTest.DATABASE_NAME);

        ActiveRecord.initialize(appContext, true);


    }

    @After
    public void tearDown(){

        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(SafeDbHelperTest.DATABASE_NAME);

    }



    @Test
    public void testCreatePasswords() {

        assertEquals(Password.getTotalCount(), 0);
        Password.create("pass");
        assertEquals(Password.getTotalCount(), 1);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);

    }


    @Test
    public void testDeletePasswords(){

        assertEquals(Password.getTotalCount(), 0);

        Password.create("pass1");
        Password.create("pass2");

        long last = Password.create("pass3");

        Password.get(last).delete();

        assertEquals(Password.getTotalCount(), 2);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 2);

    }


    @Test
    public void testTotalCount(){

        assertEquals(Password.getTotalCount(), 0);

        Password.create("pass");

        assertEquals(Password.getTotalCount(), 1);

        Password.create("content");

        assertEquals(Password.getTotalCount(), 2);

        Password.create("content2");

        assertEquals(Password.getTotalCount(), 3);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 3);

    }


    @Test
    public void testGet(){

        assertEquals(Password.getTotalCount(), 0);

        long id = Password.create("content");

        Password ent = Password.get(id);

        assertEquals(ent.getId(), id);
        assertEquals(ent.getPassword(), "content");

        assertEquals(Password.getTotalCount(), 1);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);

    }


    @Test
    public void testSave(){

        assertEquals(Entry.getTotalCount(), 0);

        long id = Password.create("content");

        Password ent = Password.get(id);

        ent.setPassword("title2");

        ent.save();

        assertEquals(ent.getId(), id);
        assertEquals(ent.getPassword(), "title2");

        assertEquals(Password.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);
    }


    @Test
    public void testDelete(){

        assertEquals(Password.getTotalCount(), 0);

        long id = Password.create("content");

        Password ent = Password.get(id);

        ent.delete();

        assertEquals(Password.getTotalCount(), 0);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 0);

    }

}
