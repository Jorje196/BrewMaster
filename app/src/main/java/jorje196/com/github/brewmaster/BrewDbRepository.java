package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_BRANDS_BRAND;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_BRANDS_ID;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_NAMES_ID;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_NAMES_NAME;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_VERIETIES_BRAND_ID;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_VERIETIES_NAME_ID;
import static jorje196.com.github.brewmaster.BrewDbHelper.TABLE_BRANDS;
import static jorje196.com.github.brewmaster.BrewDbHelper.TABLE_NAMES;

/**
 * Created by User on 07.11.2017.
 * Оформляем работу с БД через паттерн Репозиторий
 */

public class BrewDbRepository {
    private SQLiteDatabase db;
    public BrewDbRepository(Context context) {
        // подключение к БД
        db = new BrewDbHelper(context).getWritableDatabase();
    }
    // получение записей с условиями (where = строка условий SQL) и без
    /* public ArrayList<ContentValues> getData(String where) {
        ArrayList<ContentValues> list = new ArrayList<ContentValues>();
        ...
        return null;
    } */
    // получение данных из Brands
    public String getBrand(int brandID) {
        return getStr(TABLE_BRANDS, COLUMN_BRANDS_BRAND, brandID);
    }
    public int getBrandId(String brand){
        return getStrID(TABLE_BRANDS, COLUMN_BRANDS_ID, COLUMN_BRANDS_BRAND, brand);
    }
    // получение данных из Names
    public String getName(int nameID) {
        return getStr(TABLE_NAMES, COLUMN_NAMES_NAME, nameID);
    }
    public int getNameId(String name){
        return getStrID(TABLE_NAMES, COLUMN_NAMES_ID, COLUMN_NAMES_NAME, name);
    }

    // получение строки из простой таблицы
    private String getStr(String tableName, String columnName, int strID) {
        String str = null ;
        Cursor cursor = db.query(tableName, new String[]{columnName}, "_id = ?",
                new String[]{Integer.toString(strID)}, null, null, null);
        if(cursor.moveToFirst()) {
            str = cursor.getString(0);
        }
        return str;
    }
    // получение id из простой таблицы
    private int getStrID(String tableName, String columnID, String columnInf, String str){
        int strId = -1;
        String limit = "1";
        Cursor cursor = db.query(tableName, new String[]{columnID}, columnInf + " = ?",
                new String[]{str}, null, null, limit);
        if(cursor.moveToFirst()) {
            strId = cursor.getInt(0);
        }
        return strId;
    }


    // добавление записи в Brands
    public void insertBrand(String brand) {
        insertSimpeTable(TABLE_BRANDS, COLUMN_BRANDS_BRAND, brand);
    }
    // добавление записи в Names
    public void insertName(String name) {
        insertSimpeTable(TABLE_NAMES, COLUMN_NAMES_NAME, name);
    }
    // метод insert для простых (два столбца: _id , info) таблиц
    private void insertSimpeTable(String tableName, String columnName, String cellValue) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(columnName, cellValue);
        db.insert(tableName, null, rowValues);
    }
    // добавление записи в Verieties
    public void insertVerieties(String name, String brand, int bitter, int color,
                                String hrefimg, String description){
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_NAMES_NAME, name);
        rowValues.put(COLUMN_BRANDS_BRAND, brand);
        int nameId = 1; // определяем Id из табл имен
        int brandId = 1; // определяем Id из таблицы брендов
        this.insertVerieties(nameId, brandId, bitter, color, hrefimg, description );
    }
    public void insertVerieties(int nameId, int brandId, int bitter, int color,
                                String hrefimg, String description){
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_VERIETIES_NAME_ID, nameId);
        rowValues.put(COLUMN_VERIETIES_BRAND_ID, brandId);
        rowValues.put(COLUMN_BRANDS_BRAND, bitter);
        rowValues.put(COLUMN_BRANDS_BRAND, color);
        rowValues.put(COLUMN_BRANDS_BRAND, hrefimg);
        rowValues.put(COLUMN_BRANDS_BRAND, description);
        db.insert(TABLE_NAMES, null, rowValues);
    }

}
