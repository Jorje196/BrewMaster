package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.database.Cursor;
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

    public DbContract() {
    } // Во избежание создания объектов пустой

    // определение общих для таблиц констант
    static final String CREATE_TABLE = "CREATE TABLE ";
    static final String _ID_IPKA = "_id INTEGER PRIMARY KEY AUTOINCREMENT";
    static final String TYPE_INT = " INTEGER";
    static final String TYPE_TEXT = " TEXT";
    //static final String TYPE_REAL = " REAL";
    static final String NOT_DEFINED_TYPE = " N/A ";
    static final String _COM = ", ";  // comma
    // static final String _SPB = " ";  // spacebar
    static final int BUFFER_STRING_SIZE = 200;  // description может быть длинным !


    // *** Вложенный (nested) класс для таблицы Names
    static abstract class DbNames implements BaseColumns {
        static final String TABLE_NAMES = "names";
        static final String COLUMN_NAMES_ID = "_id";
        static final String COLUMN_NAMES_NAME = "name";
        static final String CREATE_TABLE_NAMES = CREATE_TABLE + TABLE_NAMES +
                "(" + _ID_IPKA + _COM + COLUMN_NAMES_NAME + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        static void insertName(SQLiteDatabase db, String name) {
            insertSimpeTable(db, TABLE_NAMES, COLUMN_NAMES_NAME, name);
        }

        // Получение данных из таблицы
        static String getName(SQLiteDatabase db, int nameID) {
            String strNameID = Integer.toString(nameID);
            return getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_NAME, COLUMN_NAMES_ID, strNameID);
        }
        static String getName(SQLiteDatabase db, String nameID) {
            return getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_NAME, COLUMN_NAMES_ID, nameID);
        }

        static int getNameId(SQLiteDatabase db, String name) {
            return Integer.parseInt(getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_ID, COLUMN_NAMES_NAME, name));
        }
    }

    // *** Вложенный (nested) класс для таблицы Brands
    static abstract class DbBrands implements BaseColumns {
        static final String TABLE_BRANDS = "brands";
        static final String COLUMN_BRANDS_ID = "_id";
        static final String COLUMN_BRANDS_BRAND = "brand";
        static final String CREATE_TABLE_BRANDS = CREATE_TABLE + TABLE_BRANDS +
                "(" + _ID_IPKA + _COM + COLUMN_BRANDS_BRAND + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        static void insertBrand(SQLiteDatabase db, String brand) {
            insertSimpeTable(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, brand);
        }

        // Получение данных из таблицы
        static String getBrand(SQLiteDatabase db, int brandID) {
            String strBrandID = Integer.toString(brandID);
            return getCellSimple(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, COLUMN_BRANDS_ID, strBrandID);
        }
        static String getBrand(SQLiteDatabase db, String brandID) {
            return getCellSimple(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, COLUMN_BRANDS_ID, brandID);
        }


        static int getBrandId(SQLiteDatabase db, String brand) {
            return Integer.parseInt(getCellSimple(db, TABLE_BRANDS, COLUMN_BRANDS_ID, COLUMN_BRANDS_BRAND, brand));
        }
    }

    // *** Вложенный (nested) класс для таблицы Verieties
    static abstract class DbVerieties implements BaseColumns {
        static final String TABLE_VERIETIES = "verieties";
        static final String COLUMN_VERIETIES_ID ="_id";
        static final String COLUMN_VERIETIES_NAME_ID = "name_id";
        static final String COLUMN_VERIETIES_BRAND_ID = "brand_id";
        static final String COLUMN_VERIETIES_BITTER = "bitter";
        static final String COLUMN_VERIETIES_COLOR = "color";
        static final String COLUMN_VERIETIES_HREFIMG = "href_img";
        static final String COLUMN_VERIETIES_DESCRIPTION = "description";
        // метатаблица типов
        static final String[][] MT_VERIETIES_TYPES =
            {{COLUMN_VERIETIES_NAME_ID, COLUMN_VERIETIES_BRAND_ID, COLUMN_VERIETIES_BITTER,
                    COLUMN_VERIETIES_COLOR, COLUMN_VERIETIES_HREFIMG, COLUMN_VERIETIES_DESCRIPTION},
             {TYPE_INT, TYPE_INT, TYPE_INT, TYPE_INT, TYPE_TEXT, TYPE_TEXT}};

        static final String CREATE_TABLE_VERIETIES = CREATE_TABLE + TABLE_VERIETIES +
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
        static void insertVerieties(SQLiteDatabase db, String name, String brand, String bitter,
                                    String color, String refImg, String description) {
            int bitterInt = DbNames.getNameId(db, bitter);
            int colorInt = DbBrands.getBrandId(db, color);
            insertVerieties(db, name, brand, bitterInt, colorInt, refImg, description);
        }
        static void insertVerieties(SQLiteDatabase db, String name, String brand, int bitter,
                                    int color, String refImg, String description) {
            int nameID = DbNames.getNameId(db, name);
            int brandID = DbBrands.getBrandId(db, brand);
            insertVerieties(db, nameID, brandID, bitter, color, refImg, description);
        }
        static void insertVerieties(SQLiteDatabase db, int nameID, int brandID, int bitter,
                                    int color, String refImg, String description) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VERIETIES_NAME_ID, nameID);
            values.put(COLUMN_VERIETIES_BRAND_ID, brandID);
            values.put(COLUMN_VERIETIES_BITTER,bitter);
            values.put(COLUMN_VERIETIES_COLOR,color);
            values.put(COLUMN_VERIETIES_HREFIMG,refImg);
            values.put(COLUMN_VERIETIES_DESCRIPTION,description);
            insertTable(db, TABLE_VERIETIES, values);
        }
        // Получение данных из таблицы
        // Получение всех name (+ brand) заданного бренда brand с горькостью из диапазона [bitterMin,bitterMax]
        static ArrayList<String> getVerietiesNameByBitterSpan(SQLiteDatabase db, String brand, int bitterMin, int bitterMax) {
            String columnLF = COLUMN_VERIETIES_NAME_ID;
            String columnInf = COLUMN_VERIETIES_BRAND_ID + "=?  AND " +
                    COLUMN_VERIETIES_BITTER + ">=? AND " + COLUMN_VERIETIES_BITTER + " <=?";
            String[] strWhere = {Integer.toString(DbBrands.getBrandId(db,brand)), Integer.toString(bitterMin), Integer.toString(bitterMax)};
            ArrayList<String> result = new ArrayList<>();
            for (String strW : getCells (db, TABLE_VERIETIES, columnLF, columnInf, strWhere) ) {
                result.add(DbNames.getName(db, strW) + " " + brand);
            }
            return result;
        }
        // Получение всех колонок кроме ссылки на img для всех Coopers, чей bitter = [31... 43]
        static ArrayList<String> getVerietiesTextByBitterSpan(SQLiteDatabase db, String brand, int bitterMin, int bitterMax){
            String[] columnLF = {COLUMN_VERIETIES_NAME_ID, COLUMN_VERIETIES_BRAND_ID, COLUMN_VERIETIES_BITTER,
                    COLUMN_VERIETIES_COLOR, COLUMN_VERIETIES_DESCRIPTION};
            ArrayList<ContentValues> resultCV;
            String columnsInFltr = COLUMN_VERIETIES_BRAND_ID + "=?  AND " +
                    COLUMN_VERIETIES_BITTER + " BETWEEN " + "? AND ?";
        // либо как вариант            COLUMN_VERIETIES_BITTER + ">=? AND " + COLUMN_VERIETIES_BITTER + " <=?";
            String orderedBy = COLUMN_VERIETIES_BITTER + " ASC";
            String[] argsInFilter = {Integer.toString(DbBrands.getBrandId(db,brand)), Integer.toString(bitterMin), Integer.toString(bitterMax)};
            resultCV = getSelectColumnsByFilter(db, TABLE_VERIETIES, columnLF, columnsInFltr, argsInFilter, orderedBy);
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < resultCV.size(); i++) {
                StringBuffer cicleW = new StringBuffer(BUFFER_STRING_SIZE);
            for (String columnName : columnLF) {
                switch (columnName) {
                    case COLUMN_VERIETIES_NAME_ID:
                        cicleW.append(" Beer ").append(DbNames.getName(db, resultCV.get(i).getAsString(columnName)));
                        break;
                    case COLUMN_VERIETIES_BRAND_ID:
                        cicleW.append(" ").append(DbBrands.getBrand(db, resultCV.get(i).getAsString(columnName)));
                        break;
                    case COLUMN_VERIETIES_BITTER:
                        cicleW.append(",  bitter = ").append(resultCV.get(i).getAsString(columnName));
                        break;
                    case COLUMN_VERIETIES_COLOR:
                        cicleW.append(",  color = ").append(resultCV.get(i).getAsString(columnName));
                        break;
                    case COLUMN_VERIETIES_DESCRIPTION:
                        cicleW.append(".  ").append(resultCV.get(i).getAsString(columnName));
                        break;
                    default: cicleW.append(" !? Unknown column !? ");
                }
            }
            result.add(cicleW.toString());
            }
            return result;
        }


    }


    // *************** Общие методы

    // *** Блок методов получения данных из таблиц

    // Применение различных вариантов для разных задач (местами ради чистого знания).

    // Получение выбранных колонок заданной таблицы , удовлетворяющих условиям, определенным
    // в запросе, с сохранением "родных" колонкам типов
    private static ArrayList<ContentValues> getSelectColumnsByFilter(SQLiteDatabase db,
        String tableName, String[] columnsLF, String columnsInFilter, String[] argsInFilter, String orderedBy) {
        Cursor cursor = db.query(tableName, columnsLF, columnsInFilter, argsInFilter, null, null, orderedBy);
        ArrayList<ContentValues> result = new ArrayList<>();
        int columnNum ;
            if (cursor.moveToFirst()){
                columnsLF = cursor.getColumnNames();
                do {
                    columnNum = 0;
                    ContentValues resultRow = new ContentValues();
                    for (String columnName : columnsLF) {
                        switch (cursor.getType(columnNum)) {
                            case FIELD_TYPE_INTEGER:
                                resultRow.put(columnName, cursor.getInt(columnNum));
                                break;
                            case FIELD_TYPE_FLOAT:
                                resultRow.put(columnName, cursor.getFloat(columnNum));
                                break;
                            case FIELD_TYPE_STRING:
                                resultRow.put(columnName, cursor.getString(columnNum));
                                break;
                            default:
                                resultRow.put(columnName, NOT_DEFINED_TYPE);
                        }
                        columnNum++;
                    }
                    result.add(resultRow);
                } while (cursor.moveToNext());
            }
        cursor.close();
        return result;
    }

     // Получение данных из выбранного столбца заданной таблицы , все строки, удовлетворяющие
     // заданным в запросе условиям(можжно по нескольким колонкам). Результаты в текстовом формате.
    private static ArrayList<String> getCells(SQLiteDatabase db, String tableName, String columnLF, String columnInf, String[] strInf) {
        ArrayList<String> result = new ArrayList<>( );
        int columnNum;
        Cursor cursor = db.query(tableName, new String[] {columnLF}, columnInf , strInf, null, null, null);
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
                        result.add(NOT_DEFINED_TYPE);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
    // выборка единственнй ячейки по условию в одной колонке (типа имя по id или наоборот
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
                    result = NOT_DEFINED_TYPE;
            }
        }
        cursor.close();
        return result;
    }
    /*  ****** Блок общих методов insert  */

    // метод insert для простых (два столбца: _id , info) таблиц
    private static void insertSimpeTable(SQLiteDatabase db, String tableName, String columnName, String cellValue) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(columnName, cellValue);
        db.insert(tableName, null, rowValues);
    }

    //  метод insert для непростых (>2 столбцов) таблиц
    private static long insertTable (SQLiteDatabase db, String tableName,ContentValues rowValues){
        try {
            return db.insertOrThrow(tableName, null, rowValues);
        //    res++;
        } catch(Exception e) {
           // "SQLiteDb TABLE " + tableName + " insert fault";

        }
        return (-1);
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