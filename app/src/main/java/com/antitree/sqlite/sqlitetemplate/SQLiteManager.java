package com.antitree.sqlite.sqlitetemplate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mark.manning on 2/17/2015.
 */
public class SQLiteManager {
    Context context;
    private SQLiteDatabase db;

    private final String DB_NAME = "hacking_dbname";
    private final int DB_VERSION = 2; // because I broke #1

    private final String TABLE_NAME = "network_info";
    private final String TABLE_ROW_ID = "id";
    private final String TABLE_ROW_ONE = "bssid";
    private final String TABLE_ROW_TWO = "ssid";
    private final String TABLE_ROW_THREE = "lat";
    private final String TABLE_ROW_FOUR = "lon";

    public SQLiteManager(Context context)
    {
        this.context = context;

        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        this.db = helper.getWritableDatabase();
    }

    // ssid, bssid, lat, lon
    public void addRow(String rowStringOne, String rowStringTwo, String rowStringThree, String rowStringFour)
    {
        ContentValues values = new ContentValues();
        values.put(TABLE_ROW_ONE, rowStringOne);
        values.put(TABLE_ROW_TWO, rowStringTwo);
        values.put(TABLE_ROW_THREE, rowStringThree);
        values.put(TABLE_ROW_FOUR, rowStringFour);

        // ask the database object to insert the new data
        try{db.insert(TABLE_NAME, null, values);}
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        Log.d("SUCCESS", "values entered in database");
    }



    //need the autoincrementing index value
    public void deleteRow(long rowID)
    {
        // ask the database manager to delete the row of given id
        try {db.delete(TABLE_NAME, TABLE_ROW_ID + "=" + rowID, null);}
        catch (Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    //needs row index and all other values
    public void updateRow(long rowID, String rowStringOne, String rowStringTwo, String rowStringThree, String rowStringFour)
    {
        // this is a key value pair holder used by android's SQLite functions
        ContentValues values = new ContentValues();
        values.put(TABLE_ROW_ONE, rowStringOne);
        values.put(TABLE_ROW_TWO, rowStringTwo);
        values.put(TABLE_ROW_THREE, rowStringThree);
        values.put(TABLE_ROW_FOUR, rowStringFour);

        // ask the database object to update the database row of given rowID
        try {db.update(TABLE_NAME, values, TABLE_ROW_ID + "=" + rowID, null);}
        catch (Exception e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }

    //request the row index and return back an array of that row
    public ArrayList<Object> getRowAsArray(long rowID)
    {
        ArrayList<Object> rowArray = new ArrayList<Object>();
        Cursor cursor;

        try
        {
            cursor = db.query
                    (
                            TABLE_NAME,
                            new String[] { TABLE_ROW_ID, TABLE_ROW_ONE, TABLE_ROW_TWO, TABLE_ROW_THREE, TABLE_ROW_FOUR},
                            TABLE_ROW_ID + "=" + rowID,
                            null, null, null, null, null
                    );

            cursor.moveToFirst();

            if (!cursor.isAfterLast())
            {
                do
                {
                    rowArray.add(cursor.getLong(0));
                    rowArray.add(cursor.getString(1));
                    rowArray.add(cursor.getString(2));
                    rowArray.add(cursor.getString(3));
                    rowArray.add(cursor.getString(4));
                }
                while (cursor.moveToNext());
            }

            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        // return the ArrayList
        return rowArray;
    }




    // grab all rows if you want to display a report or something
    public ArrayList<ArrayList<Object>> getAllRowsAsArrays()
    {
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();

        Cursor cursor;

        try
        {
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{TABLE_ROW_ID, TABLE_ROW_ONE, TABLE_ROW_TWO, TABLE_ROW_THREE, TABLE_ROW_FOUR},
                    null, null, null, null, null
            );

            cursor.moveToFirst();

            if (!cursor.isAfterLast())
            {
                do
                {
                    ArrayList<Object> dataList = new ArrayList<Object>();

                    dataList.add(cursor.getLong(0));
                    dataList.add(cursor.getString(1));
                    dataList.add(cursor.getString(2));
                    dataList.add(cursor.getString(3));
                    dataList.add(cursor.getString(4));

                    dataArrays.add(dataList);
                }
                // move the cursor's pointer up one position.
                while (cursor.moveToNext());
            }
        }
        catch (SQLException e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        //this is an ArrayList so make sure you handle it like one
        return dataArrays;
    }

    // I don't like how this is but it works fine
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper
    {
        public CustomSQLiteOpenHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //make sure you update this including the COMMAS!! if you want to expand the schema
            String newTableQueryString = "create table " +
                    TABLE_NAME +
                    " (" +
                    TABLE_ROW_ID + " integer primary key autoincrement not null," +
                    TABLE_ROW_ONE + " text," +
                    TABLE_ROW_TWO + " text," +
                    TABLE_ROW_THREE + " text," +
                    TABLE_ROW_FOUR + " text" +
                    ");";
            // execute the query string to the database.
            db.execSQL(newTableQueryString);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //at least for debugging, you might want to put something
            // here so that if you muck up the schema, you can just
            // increment the number and it'll wipe the old database.
            // otherwise you'll have to manually remve the db in the app
            // folder. Not really necessary for your app otherwise
        }
    }

}
