package com.kofhearts.safe;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;
import com.kofhearts.safe.model.ActiveRecord;
import com.kofhearts.safe.model.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


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

        appContext.deleteDatabase(SafeDbHelper.DATABASE_NAME_TEST);

        ActiveRecord.initialize(appContext, true);

    }

    @After
    public void tearDown(){

        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(SafeDbHelper.DATABASE_NAME_TEST);

    }


    @Test
    public void testCreateEntries() {

        assertEquals(Entry.getTotalCount(), 0);
        Entry.create("title", "content");
        assertEquals(Entry.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);

    }


    @Test
    public void testDeleteEntries() throws Exception{

        assertEquals(Entry.getTotalCount(), 0);

        Entry.create("title1", "content");
        Entry.create("title2", "content");
        long last = Entry.create("title3", "content");

        Entry.get(last).delete();


        assertEquals(Entry.getTotalCount(), 2);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
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


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 3);

    }



    @Test
    public void testGet() throws Exception{

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        assertEquals(ent.getId(), id);
        assertEquals(ent.getTitle(), "title1");
        assertEquals(ent.getContent(), "content");

        assertEquals(Entry.getTotalCount(), 1);

        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);


        try {
            Entry.get(1238723);
            assertTrue(false);
        }
        catch(NoRecordFoundException e){
            assertTrue(true);
        }


    }


    @Test
    public void testSave() throws Exception{

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        ent.setTitle("title2");

        ent.save();

        assertEquals(ent.getId(), id);
        assertEquals(ent.getTitle(), "title2");
        assertEquals(ent.getContent(), "content");

        assertEquals(Entry.getTotalCount(), 1);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 1);
    }


    @Test
    public void testDelete() throws Exception{

        assertEquals(Entry.getTotalCount(), 0);

        long id = Entry.create("title1", "content");

        Entry ent = Entry.get(id);

        ent.delete();

        assertEquals(Entry.getTotalCount(), 0);


        Cursor cursor = ActiveRecord.getReadableDatabase().rawQuery("SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        assertEquals(count, 0);

    }


    @Test
    public void testFirst() throws Exception{

        assertEquals(Entry.getTotalCount(), 0);

        long id1 = Entry.create("title1", "content1");
        long id2 = Entry.create("title2", "content2");
        long id3 = Entry.create("title3", "content3");
        long id4 = Entry.create("title4", "content4");
        long id5 = Entry.create("title5", "content5");

        Entry ent = Entry.first();

        assertEquals(ent.getId(), id1);
        assertEquals(ent.getTitle(), "title1");
        assertEquals(ent.getContent(), "content1");

        Entry.get(id1).delete();

        ent = Entry.first();

        assertEquals(ent.getId(), id2);
        assertEquals(ent.getTitle(), "title2");
        assertEquals(ent.getContent(), "content2");


        Entry.get(id2).delete();
        Entry.get(id3).delete();
        Entry.get(id4).delete();
        Entry.get(id5).delete();


        try {
            ent = Entry.first();
            assertTrue(false);
        }
        catch (NoRecordFoundException e){
            assertTrue(true);
        }


    }




    @Test
    public void testList(){

        assertEquals(Entry.getTotalCount(), 0);

        long id1 = Entry.create("title1", "content1");
        long id2 = Entry.create("title2", "content2");
        long id3 = Entry.create("title3", "content3");
        long id4 = Entry.create("title4", "content4");
        long id5 = Entry.create("title5", "content5");


        Entry [] entries = Entry.list();

        assertEquals(entries.length, 5);

        assertEquals(entries[0].getId(), id1);
        assertEquals(entries[0].getTitle(), "title1");
        assertEquals(entries[0].getContent(), "content1");

        assertEquals(entries[1].getId(), id2);
        assertEquals(entries[1].getTitle(), "title2");
        assertEquals(entries[1].getContent(), "content2");

        assertEquals(entries[2].getId(), id3);
        assertEquals(entries[2].getTitle(), "title3");
        assertEquals(entries[2].getContent(), "content3");

        assertEquals(entries[3].getId(), id4);
        assertEquals(entries[3].getTitle(), "title4");
        assertEquals(entries[3].getContent(), "content4");

        assertEquals(entries[4].getId(), id5);
        assertEquals(entries[4].getTitle(), "title5");
        assertEquals(entries[4].getContent(), "content5");


    }




}
