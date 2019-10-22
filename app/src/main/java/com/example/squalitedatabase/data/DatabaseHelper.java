package com.example.squalitedatabase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.squalitedatabase.model.PersonalInfo;
import com.example.squalitedatabase.params.Parameter;

import java.util.ArrayList;
import java.util.List;

import static com.example.squalitedatabase.params.Parameter.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, Parameter.DB_NAME,null, Parameter.DB_VERSION);
        Log.d("TAG","Constructor called");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String create="CREATE TABLE "+ Parameter.TABLE_NAME+"( "+Parameter.KEY_ID+" INTEGER PRIMARY KEY,"
               +Parameter.KEY_NAME +" TEXT,"+ Parameter.KEY_PHONE+" TEXT"+")";

        Log.d("TAG","onCreate method called");
        db.execSQL(create);
    }
     @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addContact(PersonalInfo personalInfo){
        ContentValues contentValues =new ContentValues();
        contentValues.put(Parameter.KEY_NAME,personalInfo.getName());
        contentValues.put(Parameter.KEY_PHONE,personalInfo.getPhone_no());
        SQLiteDatabase db=this.getWritableDatabase();
        long x= db.insert(Parameter.TABLE_NAME,null,contentValues);
        Log.d("TAG"," called "+x);

        if(x>=0)
            Log.d("TAG","Successfully inserted");
        db.close();

    }
    public List<PersonalInfo> showQuery(){
        SQLiteDatabase db=this.getReadableDatabase();
        String select="SELECT * FROM "+Parameter.TABLE_NAME;
        Cursor cursor=db.rawQuery(select,null);
        List<PersonalInfo> allContacts=new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                PersonalInfo p1=new PersonalInfo();
                p1.setId(Integer.parseInt(cursor.getString(0)));
                p1.setName(cursor.getString(1));
                p1.setPhone_no(cursor.getString(2));

                allContacts.add(p1);
            }while(cursor.moveToNext());
        }



        return allContacts;
    }
    public int updateRecord(PersonalInfo p){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_NAME,p.getName());
        contentValues.put(KEY_PHONE,p.getPhone_no());
        return db.update(TABLE_NAME,contentValues,KEY_NAME+"=?",new String[]{
                "Jivan"
        });
    }
    public int deleteRecord(){
        SQLiteDatabase db=getWritableDatabase();
       return  db.delete(TABLE_NAME, KEY_NAME +"=?",new String[]{"Samrat"});
    }
}
