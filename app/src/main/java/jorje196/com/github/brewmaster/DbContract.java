package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_STRING;

/**
 * Created by User on 07.11.2017.
 * Оформляем работу с БД через класс-репозиторий
 */

public final class DbContract {

    public DbContract(Context context) {
    } // Во избежание создания объектов пустой

    // определение общих для таблиц констант
    private static final String COLUMN_ID = "_id";
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String _ID_IPKA = "_id INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String TYPE_INT = " INTEGER";
    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_REAL = " REAL";
    public static final String _COM = ", ";  // comma
    public static final String _SPB = " ";  // spacebar

    // Вложенный (nested) класс для таблицы Names
    public static abstract class DbNames implements BaseColumns {
        public static final String TABLE_NAMES = "names";
        public static final String COLUMN_NAMES_ID = "_id";
        public static final String COLUMN_NAMES_NAME = "name";
        public static final String CREATE_TABLE_NAMES = CREATE_TABLE + TABLE_NAMES +
                "(" + _ID_IPKA + _COM + COLUMN_NAMES_NAME + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        public static void insertName(SQLiteDatabase db, String name) {
            insertSimpeTable(db, TABLE_NAMES, COLUMN_NAMES_NAME, name);
        }

        // Получение данных из таблицы
        public static String getName(SQLiteDatabase db, int nameID) {
            String strNameID = Integer.toString(nameID);
            return getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_NAME, COLUMN_NAMES_ID, strNameID);
        }

        public static int getNameId(SQLiteDatabase db, String name) {
            return Integer.parseInt(getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_ID, COLUMN_NAMES_NAME, name));
        }
    }
    // ***********************************************************
    // получение содержимого ячейки из простой таблицы
    private static String getCellSimple(SQLiteDatabase db, String tableName, String columnLF, String columnInf, String strInf) {
        String result = null;
        String limit = "1";
        int columnNum;

        Cursor cursor = db.query(tableName, new String[]{columnLF}, columnInf + " = ?",
                new String[]{strInf}, null, null, limit);
        if (cursor.moveToFirst()) {
            // для выборки одной колонки её номер всегда 0
            // columnNum = cursor.getColumnIndex(columnLF);
            columnNum = 0;
            switch (cursor.getType(columnNum)) {
                case FIELD_TYPE_INTEGER :
                    result = Integer.toString(cursor.getInt(columnNum));
                    break;
                case FIELD_TYPE_FLOAT :
                    result = Float.toString(cursor.getFloat(columnNum));
                    break;
                case FIELD_TYPE_STRING :
                    result = cursor.getString(columnNum);
                    break;
                default : result = null;
            }
        }
        cursor.close();
        return result;
    }



    // метод insert для простых (два столбца: _id , info) таблиц
    private static void insertSimpeTable(SQLiteDatabase db, String tableName, String columnName, String cellValue) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(columnName, cellValue);
        db.insert(tableName, null, rowValues);
    }

    // получение данных из Brands
/*    public String getBrand(int brandID) {
        return getStr(TABLE_BRANDS, COLUMN_BRANDS_BRAND, brandID);
    }
    public int getBrandId(String brand){
        return getStrID(TABLE_BRANDS, COLUMN_BRANDS_ID, COLUMN_BRANDS_BRAND, brand);
    } */
    // добавление записи в Verieties
 /*   public void insertVerieties(String name, String brand, int bitter, int color,
                                String hrefimg, String description){
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_NAMES_NAME, name);
        rowValues.put(COLUMN_BRANDS_BRAND, brand);
        int nameId = getNameId(name); // определяем Id из табл имен
        int brandId = getBrandId(brand); // определяем Id из таблицы брендов
        insertVerieties(nameId, brandId, bitter, color, hrefimg, description );
    }
    public void insertVerieties(int nameId, int brandId, int bitter, int color,
                                String hrefimg, String description){
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_VERIETIES_NAME_ID, nameId);
        rowValues.put(COLUMN_VERIETIES_BRAND_ID, brandId);
        rowValues.put(COLUMN_VERIETIES_BITTER, bitter);
        rowValues.put(COLUMN_VERIETIES_COLOR, color);
        rowValues.put(COLUMN_VERIETIES_HREFIMG, hrefimg);
        rowValues.put(COLUMN_VERIETIES_DESCRIPTION, description);
        db.insertOrThrow(TABLE_NAMES, null, rowValues);
    }
    // Блок редактирования записи в таблице
    // Метод update пока не нужен, при необходимости - дописать
    public void replaceInRowNames(String newVolue, String where ) {
        String tableName = TABLE_NAMES;
        String columnName = COLUMN_NAMES_NAME;
        replaceInRow(tableName, columnName, newVolue, where);
    }
    public void replaceInRowBrands(String newVolue, String where){
        String tableName = TABLE_BRANDS;
        String columnName = COLUMN_BRANDS_BRAND;
        replaceInRow(tableName, columnName, newVolue, where);
    }
    /* это покане нужно
    public void replaceInRowVerieties(double newVolue, String columnName, String where) {
        replaceInRowVerieties(Double.toString(newVolue), columnName, where);
    } */
 /*   public void replaceInRowVerieties(String newVolue, String columnName, String where){
        String tableName = TABLE_VERIETIES;
        replaceInRow(tableName, columnName, newVolue, where);
    }

    private void replaceInRow(String tableName, String columnName, String newVolue, String where) {
        String strWhere = "UPDATE " + tableName + "SET " + columnName + "=" + newVolue +
                "WHERE " + where;
        try {
            db.execSQL (strWhere);
        } catch(SQLException e) {

        }
    }


    // Блок удаления строк из таблиц

    public int deleteVerietiesRows(String strWhere, String[] strArgs){
        return deleteRows(TABLE_VERIETIES, strWhere, strArgs);
    }
    public int deleteNamesRows(String strWhere, String[] strArgs){
        return deleteRows(TABLE_NAMES, strWhere, strArgs);
    }
    public int deleteBrandsRows(String strWhere, String[] strArgs){
        return deleteRows(TABLE_BRANDS, strWhere, strArgs);
    }
    // удаление строк из заданной таблицы
    private int deleteRows(String tableName, String strWhere, String[] strArgs){
        return db.delete(tableName, strWhere, strArgs);
    }*/
    // получение записей с условиями (where = строка условий SQL) и без
    /* public ArrayList<ContentValues> getData(String where) {
        ArrayList<ContentValues> list = new ArrayList<ContentValues>();
        ...
        return null;
    } */
}