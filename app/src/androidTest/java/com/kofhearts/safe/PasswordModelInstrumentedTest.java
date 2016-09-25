package com.kofhearts.safe;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;
import com.kofhearts.safe.model.Password;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Sanjay1 on 9/25/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PasswordModelInstrumentedTest {


    @Before
    public void setUp(){

        Context appContext = InstrumentationRegistry.getTargetContext();

        appContext.deleteDatabase(SafeDbHelper.DATABASE_NAME_TEST);

        ActiveRecord.initialize(appContext, true);


    }

    @After
    public void tearDown(){

        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(SafeDbHelper.DATABASE_NAME_TEST);

    }



    @Test
    public void testCreatePasswords() {

        assertEquals(Password.getTotalCount(), 0);
        Password.create("pass");
        assertEquals(Password.getTotalCount(), 1);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
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


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
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

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
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

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
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


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
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

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 0);

    }


    @Test
    public void testFirst() throws Exception{

        assertEquals(Password.getTotalCount(), 0);

        long id1 = Password.create("content1");
        long id2 = Password.create("content2");
        long id3 = Password.create("content3");
        long id4 = Password.create("content4");
        long id5 = Password.create("content5");

        Password ent = Password.first();

        assertEquals(ent.getId(), id1);
        assertEquals(ent.getPassword(), "content1");

        Password.get(id1).delete();

        ent = Password.first();

        assertEquals(ent.getId(), id2);
        assertEquals(ent.getPassword(), "content2");


        Password.get(id2).delete();
        Password.get(id3).delete();
        Password.get(id4).delete();
        Password.get(id5).delete();


        try {
            ent = Password.first();
            assertTrue(false);
        }
        catch (NoRecordFoundException e){
            assertTrue(true);
        }


    }




    @Test
    public void testList(){

        assertEquals(Password.getTotalCount(), 0);

        long id1 = Password.create( "content1");
        long id2 = Password.create( "content2");
        long id3 = Password.create( "content3");
        long id4 = Password.create( "content4");
        long id5 = Password.create( "content5");

        Password [] entries = Password.list();

        assertEquals(entries.length, 5);

        assertEquals(entries[0].getId(), id1);
        assertEquals(entries[0].getPassword(), "content1");


        assertEquals(entries[1].getId(), id2);
        assertEquals(entries[1].getPassword(), "content2");

        assertEquals(entries[2].getId(), id3);
        assertEquals(entries[2].getPassword(), "content3");

        assertEquals(entries[3].getId(), id4);
        assertEquals(entries[3].getPassword(), "content4");

        assertEquals(entries[4].getId(), id5);
        assertEquals(entries[4].getPassword(), "content5");


    }



}
