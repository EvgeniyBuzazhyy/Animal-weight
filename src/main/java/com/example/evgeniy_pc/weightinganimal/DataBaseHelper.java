package com.example.evgeniy_pc.weightinganimal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DataBaseHelper extends SQLiteOpenHelper
{
    final String LOG_TAG = "myLogs";
    Context context;
    String column, line;
    Cursor cursor;
    int type;

    //Name DataBase
    private static final String DB_NAME = "animals.sqlite";

    public SQLiteDatabase myDataBase;
    //Path folder where will be exist new DataBase
    private String DB_PATH = "/data/data/com.example.evgeniy_pc.weightinganimal/databases/";

//---------------------------------------------------------------------------- The class constructor
    public DataBaseHelper(Context context) throws IOException
    {
        super(context, DB_NAME, null, 1);
        this.context=context;

        boolean existDB = checkDataBase();
        if (existDB)
        {
            //If the database exists, open DB
            openDataBase();
        } else
        {
            //If the database is not exists, create DB
            createDataBase();
        }
    }

//---------------------------------------------------------------------------------- Create DataBase
    public void createDataBase() throws IOException
    {
        boolean existDB = checkDataBase();
        if(existDB)
        {
            //Log.d(LOG_TAG, " DB is not exists ");
        }
        else
        {
            //Allows recording
            this.getReadableDatabase();
            //Call the copy method
            copyDataBase();
        }
    }

// ------------------------------------------------------------------------------------ Check Method
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File fileDB = new File(myPath);
            //If the database exists return true
            checkDB = fileDB.exists();
        } catch(SQLiteException e)
        {
            e.printStackTrace();
        }
        return checkDB;
    }

//------------------------------------------------------------------------------------ Copy DataBase
    private void copyDataBase()
    {
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        InputStream myInput = null;
        int length;

        try
        {
            //Get new DB
            myInput = context.getAssets().open(DB_NAME);
            //Create new file DB on path DB_PATH
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);

            while((length = myInput.read(buffer)) > 0)
            {
                //Copy
                myOutput.write(buffer, 0, length);
            }
            //Close this streams
            myOutput.close();
            //Release stream
            myOutput.flush();
            myInput.close();
            //Open DB
            openDataBase();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

//------------------------------------------------------------------------------------ Open DataBase
    public void openDataBase() throws SQLException
    {
        // Открываем базу данных за определенным адресом:
        String myPath = DB_PATH + DB_NAME;
        // Передаем путь и флаг о том что можно писать и читать нашу базу
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

//----------------------------------------------------------------------------------- Close DataBase
    public synchronized void close()
    {
        if(myDataBase != null)
        {
            myDataBase.close();
        }
        super.close();
    }
//--------------------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase arg0) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
//-------------------------------------------------------------------- Method getting weight animals
    public String getWeightInDB (Cursor cursor ,String column,String line,int type)
    {
        this.cursor = cursor;
        this.column = column;
        this.line = line;
        this.type = type;
        String queryGetWeight = null;
        String result = null;

        switch (type)
        {
            case 1:
                queryGetWeight = "SELECT `"+column+"` FROM pigs WHERE `body`="+line;
                break;
            case 2:
                queryGetWeight = "SELECT `"+column+"` FROM cows WHERE `body`="+line;
                break;
            case 3:
                queryGetWeight = "SELECT `"+column+"` FROM young_cows WHERE `body`="+line;
                break;
        }

        cursor = myDataBase.rawQuery(queryGetWeight,null);

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                String str;
                do
                {
                    str = "";
                    //Read each column
                    for (String cn : cursor.getColumnNames())
                    {
                        str = str.concat(cn + " = "+ cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                        result = cursor.getString(cursor.getColumnIndex(cn));
                    }
                    Log.d(LOG_TAG, str);
                    //Log.d(LOG_TAG, result);
                }
                //Read each line
                while (cursor.moveToNext());
            }
            //Close
            cursor.close();
        }
        else
            Log.d(LOG_TAG, "Cursor is null");

        return result;
    }
//------------------------------------------------------------------- Method adding data to Database
    public boolean addData(int data,String type)
    {
        boolean resultAdd = false;
        //System data and time
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        String time = dt.format(Calendar.getInstance().getTime());

        String queryAddData = "INSERT INTO storage VALUES (null, '"+date+"', '"+time+"', '"+data+"','"+type+"');";

        try
        {
            //Open transaction
            myDataBase.beginTransaction();
            cursor = myDataBase.rawQuery(queryAddData,null);
            myDataBase.setTransactionSuccessful();
            myDataBase.endTransaction();

            if (cursor != null)
            {
                cursor.moveToFirst();
                resultAdd = true;
            }
            else
            {
               Log.d(LOG_TAG, "Cursor is null");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        //Close DB
        cursor.close();
        return resultAdd;
    }
//-------------------------------------------------- Method getting data according to query for List
    public String[] getSavedData(int type)
    {
        int countIterate=0;
        switch (type)
        {
            case 1:
                cursor = myDataBase.rawQuery("SELECT * FROM storage WHERE  `type`= \"Pigs\";", null);
                break;
            case 2:
                cursor = myDataBase.rawQuery("SELECT * FROM storage WHERE  `type`= \"Cows\";", null);
                break;
            case 3:
                cursor = myDataBase.rawQuery("SELECT * FROM storage WHERE  `type`= \"Calves\";", null);
                break;
        }
        //Creating length array equals cursorCount
        String[] arrayStrings = new String[cursor.getCount()];

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                String str;
                do {
                    str = "";
                    for (int i=1; i<cursor.getColumnNames().length-1;i++)
                    {
                        String cn = String.valueOf(cursor.getColumnName(i));
                        str = str.concat(cursor.getString(cursor.getColumnIndex(cn))+"    ");
                    }

                    arrayStrings[countIterate] = str;
                    countIterate++;
                } while (cursor.moveToNext());
            }
        } else
        {
           Log.d(LOG_TAG, "Cursor is null");
        }
        //Close DB
        cursor.close();
        return arrayStrings;
    }
//----------------------------------------------------------------- Method deleting item in Database
    public boolean deleteItemDB(String date,String time, String type)
    {
        boolean resultDeleted = false;
        String queryDeleteItem = "DELETE FROM storage WHERE date='"+date+"' AND time='"+time+"' AND type='"+type+"';";

        try
        {
            cursor = myDataBase.rawQuery(queryDeleteItem,null);

            if (cursor != null)
            {
                //Item deleted
                cursor.moveToFirst();
                resultDeleted = true;
            }
            else
            {
                Log.d(LOG_TAG, "Cursor is null");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        //Close DB
        cursor.close();
        return resultDeleted;
    }
}//--------------------------------------------------------------------------------------- end class
