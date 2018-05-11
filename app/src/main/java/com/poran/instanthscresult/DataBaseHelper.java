package com.poran.instanthscresult;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by poran on 27-Dec-17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME="MessageDataBase";
    private static final String TABLE_NAME="messageTable";
    private static final String NUMBER="5554";
    private  Context context;
    private static final String HOUR ="hour";
    private static final String MINUTE="min";
    private static final String ID="id_";
    private static final String MESSAGE="message";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +MESSAGE+" VARCHAR,"+ HOUR +" INTEGER,"+ MINUTE +" INTEGER);";
    private static final String DROP_TAB="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String QUERY="SELECT * FROM "+TABLE_NAME;
    long i;

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
            Log.d("DataBaseTable","Created Successfully");
        }catch (Exception e){
            Log.d("create Table",e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DROP_TAB);
            onCreate(db);
            Log.d("DataBaseTable","Drop ");

        }catch (Exception e){
            Log.d("Update",e.getMessage());
        }

    }

    public boolean addData(SendMessageForInter data){

        SQLiteDatabase db=this.getWritableDatabase();
        try {
            ContentValues c = new ContentValues();


            c.put(MESSAGE,data.getMessage());
            c.put(HOUR,data.getHour());
            c.put(MINUTE,data.getMinute());
            i=db.insert(TABLE_NAME,null,c);
            db.close();




        }catch (Exception e){
            Log.d("Add Data :",e.getMessage());

        }

        if(i!=-1){
            return true;
        }else{
            return false;
        }
    }

    public Cursor readData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(QUERY,null);
        return c;
    }

    public int deleMessage(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+" = ?",new String[]{ id });
    }
}
