package com.activities.dwtaplin.jobsearchfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.internet.MessagingService;

public class LocalDatabaseManager extends SQLiteOpenHelper {
    public static final String databaseName = "jobsearch.db";

    public SQLiteDatabase sqLiteDatabase;
    public LocalDatabaseManager(Context context) {
        super(context, databaseName, null, 1);
        sqLiteDatabase = getWritableDatabase();

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("CREATE TABLE USER(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " F_NAME TEXT, L_NAME TEXT, U_NAME TEXT, EMAIL TEXT, DESC_ TEXT, CITY TEXT, SERVER_ID INTEGER, FILE_PATH TEXT, QUAL TEXT, FCM_TOKEN TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE TOKEN(TKN TEXT)");
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TOKEN");
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        onCreate(sqLiteDatabase);

    }
    public boolean userExists(){
        sqLiteDatabase.beginTransaction();
        Cursor rawQuery = (sqLiteDatabase).rawQuery("SELECT * FROM USER", new String[]{});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        while(rawQuery.moveToNext()){
            rawQuery.close();
            return true;
        }
        rawQuery.close();
        return false;
    }
    public void updateUser(User user){
        clearUser();
        ContentValues contentValues = new ContentValues();
        contentValues.put("F_NAME", user.getFirstName());
        contentValues.put("L_NAME", user.getLastName());
        contentValues.put("U_NAME", user.getUserName());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("DESC_", user.getDesc());
        contentValues.put("CITY", user.getLocation());
        contentValues.put("SERVER_ID", user.getServerId());
        contentValues.put("FILE_PATH", user.getImgFilePath());
        contentValues.put("FCM_TOKEN", MessagingService.getToken());
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.insert("USER", null, contentValues);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();

    }

    public void updateToken(String token){
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete("token", null, null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TKN", token);
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.insert("TOKEN", null, contentValues);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public String getToken() {
        sqLiteDatabase.beginTransaction();
        Cursor rawQuery = (sqLiteDatabase).rawQuery("SELECT * FROM TOKEN", new String[]{});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        while(rawQuery.moveToNext()){
            String token = rawQuery.getString(0);
            rawQuery.close();
            return token;
        }
        rawQuery.close();
        return null;
    }

    public int getUserServerId() {
        sqLiteDatabase.beginTransaction();
        Cursor rawQuery = (sqLiteDatabase).rawQuery("SELECT SERVER_ID FROM USER", new String[]{});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        while(rawQuery.moveToNext()){
            int token = rawQuery.getInt(0);
            rawQuery.close();
            return token;
        }
        rawQuery.close();
        return -1;
    }

    public User getUser() {
        sqLiteDatabase.beginTransaction();
        Cursor rawQuery = (sqLiteDatabase).rawQuery("SELECT SERVER_ID, F_NAME, L_NAME, EMAIL, U_NAME, DESC_, QUAL, FILE_PATH, CITY FROM USER", new String[]{});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        while(rawQuery.moveToNext()){
            int serverId = rawQuery.getInt(0);
            String fName = rawQuery.getString(1);
            String lName = rawQuery.getString(2);
            String uName = rawQuery.getString(3);
            String email = rawQuery.getString(4);
            String city = rawQuery.getString(5);
            String qual = rawQuery.getString(6);
            String desc = rawQuery.getString(7);
            String filePath = rawQuery.getString(8);
            rawQuery.close();
            return new User(fName, lName, uName, city,
                    email, qual, serverId, filePath, desc);
        }
        rawQuery.close();
        return null;
    }

    public void clearUser() {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete("USER", null, null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }
}
