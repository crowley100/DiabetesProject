package com.example.maxim.diabetesireland;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lorcan on 03/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat Tag
    private static final String Log = "DatabaseHelper";

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    public static final String DB_NAME = "usersDB";

    // User Data Table
    public static final String USER_DATA = "userTable";
    public static final String UCOL_1 = "AGE";     // primary key
    public static final String UCOL_2 = "GENDER";         // male / female
    public static final String UCOL_3 = "HEIGHT";         // in metres or/and feet
    public static final String UCOL_4 = "WEIGHT";         // in kg/other
    public static final String UCOL_5 = "GOAL";           // daily targets?

    // Daily Performance Table
    public static final String DAILY_DATA = "daily_table";
    public static final String DCOL_1 = "DATE";           // primary key
    public static final String DCOL_2 = "FAT_INTAKE";
    public static final String DCOL_3 = "PROTEIN_INTAKE";
    public static final String DCOL_4 = "DAIRY_INTAKE";
    public static final String DCOL_5 = "FRUITandVEG_INTAKE";
    public static final String DCOL_6 = "CARBOHYDRATE_INTAKE";
    public static final String DCOL_7 = "ALCOHOL_INTAKE";
    public static final String DCOL_8 = "WATER_INTAKE";
    public static final String DCOL_9 = "TREATS_INTAKE";
    public static final String DCOL_10 = "STEPS";                // calories burned stored in db?
    public static final String DCOL_11 = "LIGHT_EXERCISE";
    public static final String DCOL_12 = "MODERATE_EXERCISE";
    public static final String DCOL_13 = "VIGOROUS_EXERCISE";
    public static final String DCOL_14 = "CALORIE_ALLOWANCE";

    // Create Table Statements
    // User Data table creation
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_DATA
            + "(" + UCOL_1 + " INTEGER PRIMARY KEY," + UCOL_2 + " TEXT," + UCOL_3 + " REAL,"
            + UCOL_4 + " REAL," + UCOL_5 + " TEXT" + ")";

    // Daily Performance table creation
    private static final String CREATE_TABLE_DAILY = "CREATE TABLE " + DAILY_DATA
            + "(" + DCOL_1 + " TEXT PRIMARY KEY," + DCOL_2 + " REAL," + DCOL_3 + " REAL,"
            + DCOL_4 + " REAL," + DCOL_5 + " REAL," + DCOL_6 + " REAL," + DCOL_7 + " REAL,"
            + DCOL_8 + " REAL," + DCOL_9 + " REAL," + DCOL_10 + " INTEGER,"
            + DCOL_11 + " INTEGER," + DCOL_12 + " INTEGER," + DCOL_13 + " INTEGER,"
            + DCOL_14 + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_DAILY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_DATA);
        db.execSQL("DROP TABLE IF EXISTS "+DAILY_DATA);
        onCreate(db);
    }

    // needs testing
    public boolean isEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery("SELECT COUNT(*) FROM " + USER_DATA, null);
        if (myCursor != null){
            myCursor.moveToFirst();
            if (myCursor.getInt(0) == 0){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    // Store User Data from Registration Screen
    public boolean insertUserData(int age, String gender, double height, double weight, String goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL_1, age);
        content.put(UCOL_2, gender);
        content.put(UCOL_3, height);
        content.put(UCOL_4, weight);
        content.put(UCOL_5, goal);
        long result = db.insert(USER_DATA, null, content);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Create new daily entry in database
    public boolean initDailyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        ContentValues content = new ContentValues();
        content.put(DCOL_1, date);
        content.put(DCOL_2, 0.0);
        content.put(DCOL_3, 0.0);
        content.put(DCOL_4, 0.0);
        content.put(DCOL_5, 0.0);
        content.put(DCOL_6, 0.0);
        content.put(DCOL_7, 0.0);
        content.put(DCOL_8, 0.0);
        content.put(DCOL_9, 0.0);
        content.put(DCOL_10, 0);
        content.put(DCOL_11, 0);
        content.put(DCOL_12, 0);
        content.put(DCOL_13, 0);
        content.put(DCOL_14, 6);

        long result = db.insert(DAILY_DATA, null, content);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Methods for updating data in database
    // Update daily data with exercise intake
    public void updateDailyExercise(int duration, String intensity) {
        // currently only updating current day's data
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExists(date)){
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + intensity + " = " + intensity + " + " + duration + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
        else{
            initDailyData();
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + intensity + " = " + intensity + " + " + duration + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
    }

    // Update daily data with food intake
    public void updateDailyFood(float portionSize, String type) {
        // currently only updating current day's data
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExists(date)){
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + type + " = " + type + " + " + portionSize + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
        else{
            initDailyData();
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + type + " = " + type + " + " + portionSize + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
    }

    // Update daily steps
    public void stepsUpdate(int steps){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExists(date)){
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + DCOL_10 + " = " + steps + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
        else{
            initDailyData();
            db.execSQL("UPDATE " + DAILY_DATA + " SET " + DCOL_10 + " = " + steps + " WHERE " + DCOL_1 + " = " + '"'+date+'"');
        }
    }

    // Modify user info to reflect profile changes
    public void updateWeight(double newWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + USER_DATA + " SET " + UCOL_4 + " = " + newWeight);
    }

    public void updateHeight(double newHeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + USER_DATA + " SET " + UCOL_3 + " = " + newHeight);
    }

    // Methods for fetching data from database
    // Fetch User Data
    public double [] fetchUserData() {
        double [] result = new double [3];
        String selectQuery = "SELECT " + UCOL_1 + ", " + UCOL_3 + ", " + UCOL_4 + " FROM " + USER_DATA;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            result[0] = cursor.getDouble(0);
            result[1] = cursor.getDouble(1);
            result[2] = cursor.getDouble(2);
        }
        cursor.close();
        return result;
    }

    // returns user's gender
    public String fetchUserGender() {
        String result = "";
        String selectQuery = "SELECT " + UCOL_2 + " FROM " + USER_DATA;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            result =  cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    // returns user's height
    public double fetchUserHeight() {
        double result = 0.0;
        String selectQuery = "SELECT " + UCOL_3 + " FROM " + USER_DATA;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            result =  cursor.getDouble(0);
        }
        cursor.close();
        return result;
    }

    // returns user's weight
    public double fetchUserWeight() {
        double result = 0.0;
        String selectQuery = "SELECT " + UCOL_4 + " FROM " + USER_DATA;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            result =  cursor.getDouble(0);
        }
        cursor.close();
        return result;
    }

    // Fetch Daily Data
    // for today
    public float [] fetchFoodDrink() {
        float [] result = new float [8];
        //result[4] = 3; // for debug
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        // 2 to 9
        String selectQuery = "SELECT " + DCOL_2 + ", " + DCOL_3 + ", " + DCOL_4 + ", " + DCOL_5
                + ", " + DCOL_6 + ", " + DCOL_7 + ", " + DCOL_8 + ", " + DCOL_9 + " FROM "
                + DAILY_DATA + " WHERE " + DCOL_1 + " = " + '"'+date+'"';
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            for(int i = 0; i < 8; i++){
                result[i] = cursor.getFloat(i);
            }
        }
        cursor.close();
        return result;
    }

    // fetch current day's steps
    public int getSteps(){
        SQLiteDatabase db = this.getReadableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        int steps = 0;
        String query = "SELECT " + DCOL_10 + " FROM "
                + DAILY_DATA + " WHERE " + DCOL_1 + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {date});
        if (cursor.moveToFirst()){
            steps = cursor.getInt(0);
        }
        cursor.close();
        return steps;
    }

    // recorded steps for previous 7 days
    public int [] getWeeklySteps(){
        SQLiteDatabase db = this.getReadableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int [] result = new int [7];

        Date todate1;
        String fromdate;
        int steps;

        Calendar cal = Calendar.getInstance();
        for (int i = 6; i >= 0; i--){
            todate1 = cal.getTime();
            fromdate = dateFormat.format(todate1);
            if (recordExists(fromdate)){
                String query = "SELECT " + DCOL_10 + " FROM "
                        + DAILY_DATA + " WHERE " + DCOL_1 + "=?";
                Cursor cursor = db.rawQuery(query, new String[] {fromdate});
                if (cursor.moveToFirst()){
                    steps = cursor.getInt(0);
                    result[i] = steps;
                }
                cursor.close();
            }
            else{
                result[i] = 0;
            }
            cal.add(Calendar.DATE, -1);
        }
        return result;
    }

    // recorded carb intake for previous 7 days
    public double [] getWeeklyCarbs(){
        SQLiteDatabase db = this.getReadableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        double [] result = new double [7];

        Date todate1;
        String fromdate;
        double carbs;

        Calendar cal = Calendar.getInstance();
        for (int i = 6; i >= 0; i--){
            todate1 = cal.getTime();
            fromdate = dateFormat.format(todate1);
            if (recordExists(fromdate)){
                String query = "SELECT " + DCOL_6 + " FROM "
                        + DAILY_DATA + " WHERE " + DCOL_1 + "=?";
                Cursor cursor = db.rawQuery(query, new String[] {fromdate});
                if (cursor.moveToFirst()){
                    carbs = cursor.getDouble(0);
                    result[i] = carbs;
                }
                cursor.close();
            }
            else{
                result[i] = 0.0;
            }
            cal.add(Calendar.DATE, -1);
        }
        return result;
    }

    // method to check if column (date) exists in db
    public boolean recordExists(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM "
                + DAILY_DATA + " WHERE " + DCOL_1 + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {date});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
    // check recorded alcohol intake for last 7 days
    public int alcoholCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int result = 0;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date todate1;
        String fromdate;

        Calendar cal = Calendar.getInstance();
        for (int i = 6; i >= 0; i--){
            todate1 = cal.getTime();
            fromdate = dateFormat.format(todate1);
            if (recordExists(fromdate)){
                String query = "SELECT " + DCOL_7 + " FROM "
                        + DAILY_DATA + " WHERE " + DCOL_1 + "=?";
                Cursor cursor = db.rawQuery(query, new String[] {fromdate});
                if (cursor.moveToFirst()){
                    result += cursor.getInt(0);
                }
                cursor.close();
            }
            cal.add(Calendar.DATE, -1);
        }
        return result;
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}