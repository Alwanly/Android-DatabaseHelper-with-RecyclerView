package com.example.mad.articlenews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    //nama database
    public static final String DATABASE_NAME = "Article.db";
    //nama table
    public static final String TABLE_NAME = "student_table";
    //versi database
    private static final int DATABASE_VERSION = 1;
    //table field
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "ARTICLE";
    public static final String COL_4 = "AUTHOR";

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //create table pertamakali
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table student_table(id integer primary key autoincrement," +
                "title text, " +
                "article text," +
                "author integer);");
    }

    //ketika database version diubah menjadi lebih tinggi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    //ketika database version diubah menjadi lebih rendah
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //metode untuk tambah data
    public boolean insertData(String title, String article, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,article);
        contentValues.put(COL_4,author);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //metode untuk mengambil data
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from student_table", null);
        return res;
    }

    //metode untuk merubah data
    public boolean updateData(String id,String title,String article,String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,article);
        contentValues.put(COL_4,author);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }

    //metode untuk menghapus data
    public int deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}
