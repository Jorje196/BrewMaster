package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

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
    public static final String NOT_DEFINED = " N/A ";
    public static final String _COM = ", ";  // comma
    public static final String _SPB = " ";  // spacebar

    // *** Вложенный (nested) класс для таблицы Names
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

    // *** Вложенный (nested) класс для таблицы Brands
    public static abstract class DbBrands implements BaseColumns {
        public static final String TABLE_BRANDS = "brands";
        public static final String COLUMN_BRANDS_ID = "_id";
        public static final String COLUMN_BRANDS_BRAND = "brand";
        public static final String CREATE_TABLE_BRANDS = CREATE_TABLE + TABLE_BRANDS +
                "(" + _ID_IPKA + _COM + COLUMN_BRANDS_BRAND + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        public static void insertBrand(SQLiteDatabase db, String brand) {
            insertSimpeTable(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, brand);
        }
        // Получение данных из таблицы
        public static String getBrand(SQLiteDatabase db, int brandID) {
            String strBrandID = Integer.toString(brandID);
            return getCellSimple(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, COLUMN_BRANDS_ID, strBrandID);
        }
        public static int getBrandId(SQLiteDatabase db, String brand) {
            return Integer.parseInt(getCellSimple(db, TABLE_BRANDS, COLUMN_BRANDS_ID, COLUMN_BRANDS_BRAND, brand));
        }
    }

    // *** Вложенный (nested) класс для таблицы Verieties
    public static abstract class DbVerieties implements BaseColumns {
        public static final String TABLE_VERIETIES = "verieties";
        public static final String COLUMN_VERIETIES_ID ="_id";
        public static final String COLUMN_VERIETIES_NAME_ID = "name_id";
        public static final String COLUMN_VERIETIES_BRAND_ID = "brand_id";
        public static final String COLUMN_VERIETIES_BITTER = "bitter";
        public static final String COLUMN_VERIETIES_COLOR = "color";
        public static final String COLUMN_VERIETIES_HREFIMG = "href_img";
        public static final String COLUMN_VERIETIES_DESCRIPTION = "description";
        // метатаблица типов
        public static final String[][] MT_VERIETIES_TYPES =
            {{COLUMN_VERIETIES_NAME_ID, COLUMN_VERIETIES_BRAND_ID, COLUMN_VERIETIES_BITTER,
                    COLUMN_VERIETIES_COLOR, COLUMN_VERIETIES_HREFIMG, COLUMN_VERIETIES_DESCRIPTION},
             {TYPE_INT, TYPE_INT, TYPE_INT, TYPE_INT, TYPE_TEXT, TYPE_TEXT},
                    {"Integer.toString"}   };

        public static final String CREATE_TABLE_VERIETIES = CREATE_TABLE + TABLE_VERIETIES +
                "(" + _ID_IPKA + _COM + COLUMN_VERIETIES_NAME_ID + TYPE_INT + _COM +
                COLUMN_VERIETIES_BRAND_ID + TYPE_INT + _COM +
                COLUMN_VERIETIES_BITTER + TYPE_INT + _COM +
                COLUMN_VERIETIES_COLOR + TYPE_INT + _COM +
                COLUMN_VERIETIES_HREFIMG + TYPE_TEXT + _COM +
                COLUMN_VERIETIES_DESCRIPTION + TYPE_TEXT + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_NAME_ID + ") " +
                "REFERENCES " + DbNames.TABLE_NAMES + "(" + DbNames.COLUMN_NAMES_ID + ")" + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_BRAND_ID + ") " +
                "REFERENCES " + DbBrands.TABLE_BRANDS + "(" + DbBrands.COLUMN_BRANDS_ID + "))";

        // Добавление записи в таблицу
        public static void insertVerieties(SQLiteDatabase db, String name, String brand, String bitter,
                                           String color, String refImg, String description) {
            int bitterInt = DbNames.getNameId(db, bitter);
            int colorInt = DbBrands.getBrandId(db, color);
            insertVerieties(db, name, brand, bitterInt, colorInt, refImg, description);
        }
        public static void insertVerieties(SQLiteDatabase db, String name, String brand, int bitter,
            int color, String refImg, String description) {
            int nameID = DbNames.getNameId(db, name);
            int brandID = DbBrands.getBrandId(db, brand);
            insertVerieties(db, nameID, brandID, bitter, color, refImg, description);
        }
        public static void insertVerieties(SQLiteDatabase db, int nameID, int brandID, int bitter,
            int color, String refImg, String description) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VERIETIES_NAME_ID, nameID);
            values.put(COLUMN_VERIETIES_BRAND_ID, brandID);
            values.put(COLUMN_VERIETIES_BITTER,bitter);
            values.put(COLUMN_VERIETIES_COLOR,color);
            values.put(COLUMN_VERIETIES_HREFIMG,refImg);
            values.put(COLUMN_VERIETIES_HREFIMG,refImg);
            String[] columns = {COLUMN_VERIETIES_NAME_ID, COLUMN_VERIETIES_BRAND_ID,
                    COLUMN_VERIETIES_BITTER, COLUMN_VERIETIES_COLOR, COLUMN_VERIETIES_HREFIMG,
                    COLUMN_VERIETIES_DESCRIPTION};

            insertTable(db, TABLE_VERIETIES, values);
        }
        // Получение данных из таблицы
        // String[2][] strWhere -
        public static ArrayList<String> getVerietiesBitter(SQLiteDatabase db, String strWhere) {

            return getCells(db, TABLE_VERIETIES, COLUMN_VERIETIES_BITTER, COLUMN_VERIETIES_BRAND_ID, strWhere);
        }
    }


    // *************** Общие методы

    // получение содержимого ячейки из простой таблицы  ? рассмотреть расширение до группы строк и столбцов
    // механизм с простыми таблицами как пример реализации метода получения/извлечения данных разных типов
    private static ArrayList<String> getCells(SQLiteDatabase db, String tableName, String columnLF, String columnInf, String strInf) {
        ArrayList<String> result = new ArrayList<String>( );
        int indexALS = 0;
        //    String limit = "1";
        int columnNum;
        Cursor cursor = db.query(tableName, new String[]{columnLF}, columnInf + " = ?",
                new String[]{strInf}, null, null, null);
        if (cursor.moveToFirst()) {
            // для выборки одной колонки её номер всегда 0
            // columnNum = cursor.getColumnIndex(columnLF);
            do {
                columnNum = 0;
                switch (cursor.getType(columnNum)) {
                    case FIELD_TYPE_INTEGER:
                        result.add(Integer.toString(cursor.getInt(columnNum)));
                        break;
                    case FIELD_TYPE_FLOAT:
                        result.add(Float.toString(cursor.getFloat(columnNum)));
                        break;
                    case FIELD_TYPE_STRING:
                        result.add(cursor.getString(columnNum));
                        break;
                    default:
                        result.add(NOT_DEFINED);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    private static String getCellSimple(SQLiteDatabase db, String tableName, String columnLF, String columnInf, String strInf) {
        String result = null;
        String limit = "1";
        int columnNum;
        Cursor cursor = db.query(tableName, new String[]{columnLF}, columnInf + " = ?",
                new String[]{strInf}, null, null, null, limit);
        if (cursor.moveToFirst()) {
            // для выборки одной колонки её номер всегда 0
            // columnNum = cursor.getColumnIndex(columnLF);
            columnNum = 0;
            switch (cursor.getType(columnNum)) {
                case FIELD_TYPE_INTEGER:
                    result = Integer.toString(cursor.getInt(columnNum));
                    break;
                case FIELD_TYPE_FLOAT:
                    result = Float.toString(cursor.getFloat(columnNum));
                    break;
                case FIELD_TYPE_STRING:
                    result = cursor.getString(columnNum);
                    break;
                default:
                    result = NOT_DEFINED;
            }
        }
        cursor.close();
        return result;
    }

    //  метод insert для непростых (>2 столбцов) таблиц
    private static void insertTable(SQLiteDatabase db, String tableName,ContentValues rowValues){
        try {
            long res = db.insertOrThrow(tableName, null, rowValues);
            res++;
        } catch(Exception e) {
            long reso = 3;
            reso++;
        }
    }

    // метод insert для простых (два столбца: _id , info) таблиц
    private static void insertSimpeTable(SQLiteDatabase db, String tableName, String columnName, String cellValue) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(columnName, cellValue);
        db.insert(tableName, null, rowValues);
    }
/*

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
    /* это пока не нужно
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