package com.kofhearts.safe;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kofhearts.safe.data.SafeDbHelperReal;
import com.kofhearts.safe.data.SafeDbHelperTest;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EntryModelInstrumentedTest {



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
    public void testCreateEntries() {

        assertEquals(Entry.getTotalCount(), 0);
        Entry.create("title", "content");
        assertEquals(Entry.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);

    }


    @Test
    public void testDeleteEntries(){

        assertEquals(Entry.getTotalCount(), 0);

        Entry.create("title1", "content");
        Entry.create("title2", "content");
        long last = Entry.create("title3", "content");

        Entry.get(last).delete();


        assertEquals(Entry.getTotalCount(), 2);



        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 2);

    }


    @Test
    public void testTotalCount(){

        assertEquals(Entry.getTotalCount(), 0);

        Entry.create("title1", "content");

        assertEquals(Entry.getTotalCount(), 1);

        Entry.create("title2", "content");

        assertEquals(Entry.getTotalCount(), 2);

        Entry.create("title2", "content");

        assertEquals(Entry.getTotalCount(), 3);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 3);

    }



    @Test
    public void testGet(){

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        assertEquals(ent.getId(), id);
        assertEquals(ent.getTitle(), "title1");
        assertEquals(ent.getContent(), "content");

        assertEquals(Entry.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);

    }


    @Test
    public void testSave(){

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        ent.setTitle("title2");

        ent.save();

        assertEquals(ent.getId(), id);
        assertEquals(ent.getTitle(), "title2");
        assertEquals(ent.getContent(), "content");

        assertEquals(Entry.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);
    }


    @Test
    public void testDelete(){

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        ent.delete();

        assertEquals(Entry.getTotalCount(), 0);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 0);

    }





}
