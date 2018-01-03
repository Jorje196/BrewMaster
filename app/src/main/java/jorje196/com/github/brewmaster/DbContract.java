package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

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
    } // Во избежание создания объектов конструктор пустой

    //public static final String DB_NAME = "brewDb";
    // определение общих для таблиц констант
    static final String CREATE_TABLE = "CREATE TABLE ";
    static final String _ID_IPKA = "_id INTEGER PRIMARY KEY AUTOINCREMENT";
    static final String TYPE_INT = " INTEGER";
    static final String TYPE_TEXT = " TEXT";
    static final String TYPE_REAL = " REAL";
    static final String NOT_DEFINED_TYPE = " N/A ";
    static final String NOT_DEFINED_DATA = "n/a";
    static final String NOT_NULL = " NOT NULL";

    static final String _COM = ", ";  // comma
    static final String _PNT = "."; // point
    static final String GRAD_C = "'\u2103'";
    // static final String _SPB = " ";  // spacebar
    static final int BUFFER_STRING_SIZE = 400;  // description может быть длинным !

    // *** Вложенный (nested) класс для таблицы Brews
    static abstract class DbBrews implements BaseColumns {
        static final String TABLE_BREWS = "brews";
        static final String COLUMN_BREWS_ID = "_id";
        static final String COLUMN_BREWS_VERIETY_ID = "veriety_id";
        static final String COLUMN_BREWS_VOLUME = "volume";     // по факту
        static final String COLUMN_BREWS_START_DATA = "st_data";
        static final String COLUMN_BREWS_START_GRAVITY = "st_gravity";
        static final String COLUMN_BREWS_START_WORT_TEMP = "st_temp";
        static final String COLUMN_BREWS_FINAL_GRAVITY = "fn_gravity";
        static final String COLUMN_BREWS_FINAL_TEMP = "fn_temp";
        static final String COLUMN_BREWS_BOTTLED_DATA = "btl_data";
        static final String COLUMN_BREWS_ALC_PERCENT = "alco_persent";
        static final String COLUMN_BREWS_SECOND_FERMENT_DATA = "fn_second_ferm";
        static final String COLUMN_BREWS_PROCESS_STATE = "process_state_id";
        static final String COLUMN_BREWS_SUGAR = "sugar";
        static final String COLUMN_BREWS_DEXTROSE = "dextrose";
        static final String COLUMN_BREWS_THICKENERS = "thickeners";
        static final String COLUMN_BREWS_THICK_WEIGHT = "thick_weight";
        static final String COLUMN_BREWS_ENHANCERS = "enhancers";
        static final String COLUMN_BREWS_ENHANCERS_WEIGHT = "enhanc_weight";
        static final String COLUMN_BREWS_NOTES = "notes";
        static final String COLUMN_BREWS_BITTER = "bitter_actual";  // это шаг назад от нормализации, но удобно

        // В метаданные можно добавить ограничения, начальные значения, etc.
        static final String[][] MT_BREWS_COLUMNS_TYPES = {{COLUMN_BREWS_ID, TYPE_INT,},
            {COLUMN_BREWS_VERIETY_ID, TYPE_INT, NOT_NULL},
            {COLUMN_BREWS_VOLUME, TYPE_REAL},
            {COLUMN_BREWS_START_DATA, TYPE_TEXT}, {COLUMN_BREWS_START_GRAVITY, TYPE_REAL},
            {COLUMN_BREWS_START_WORT_TEMP, TYPE_INT}, {COLUMN_BREWS_FINAL_GRAVITY, TYPE_REAL},
            {COLUMN_BREWS_FINAL_TEMP, TYPE_INT}, {COLUMN_BREWS_BOTTLED_DATA, TYPE_TEXT},
            {COLUMN_BREWS_ALC_PERCENT, TYPE_REAL}, {COLUMN_BREWS_SECOND_FERMENT_DATA, TYPE_TEXT},
            {COLUMN_BREWS_PROCESS_STATE, TYPE_INT}, {COLUMN_BREWS_SUGAR, TYPE_REAL},
            {COLUMN_BREWS_DEXTROSE, TYPE_REAL},
            {COLUMN_BREWS_THICKENERS, TYPE_INT}, {COLUMN_BREWS_THICK_WEIGHT, TYPE_REAL},
            {COLUMN_BREWS_ENHANCERS, TYPE_INT}, {COLUMN_BREWS_ENHANCERS_WEIGHT, TYPE_REAL},
            {COLUMN_BREWS_NOTES, TYPE_TEXT}, {COLUMN_BREWS_BITTER, TYPE_INT}};

        static final String CREATE_TABLE_BREWS = CREATE_TABLE + TABLE_BREWS + "(" + _ID_IPKA +
            getInitString(MT_BREWS_COLUMNS_TYPES) + _COM +
                "FOREIGN KEY (" + COLUMN_BREWS_VERIETY_ID + ") " + "REFERENCES " +
                DbVerieties.TABLE_VERIETIES + "(" + DbVerieties.COLUMN_VERIETIES_ID + ")" +
                _COM + "FOREIGN KEY (" + COLUMN_BREWS_THICKENERS + ") " + "REFERENCES " +
                DbThickeners.TABLE_THICKENERS + "(" + DbThickeners.COLUMN_THICKENERS_ID + ")" +
                _COM + "FOREIGN KEY (" + COLUMN_BREWS_ENHANCERS + ") " + "REFERENCES " +
                DbEnhancers.TABLE_ENHANCERS + "(" + DbEnhancers.COLUMN_ENHANCERS_ID + ")" +
                _COM + "FOREIGN KEY (" + COLUMN_BREWS_PROCESS_STATE + ") " + "REFERENCES " +
                DbStates.TABLE_STATES + "(" + DbStates.COLUMN_STATES_ID + ")" +
                ")";

        static long insertBrews(SQLiteDatabase db, int veriety, double volume, String startDataT,
            double startGravity, int startTemp, double finalGravity, int finalTemp, String bottledData,
                double alcPercent, String secondFermData, int processState, double sugarWeight,
                double dextroseWeight, int thickenerId, double thickenerWeight, int enhancerId,
                double enhancerWeight, String notes, int bitter) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BREWS_VERIETY_ID, veriety);
            values.put(COLUMN_BREWS_VOLUME, volume);
            values.put(COLUMN_BREWS_START_DATA, startDataT);
            values.put(COLUMN_BREWS_START_GRAVITY, startGravity);
            values.put(COLUMN_BREWS_START_WORT_TEMP, startTemp);
            values.put(COLUMN_BREWS_FINAL_GRAVITY, finalGravity);
            values.put(COLUMN_BREWS_FINAL_TEMP, finalTemp);
            values.put(COLUMN_BREWS_BOTTLED_DATA, bottledData);
            values.put(COLUMN_BREWS_ALC_PERCENT, alcPercent);
            values.put(COLUMN_BREWS_SECOND_FERMENT_DATA, secondFermData);
            values.put(COLUMN_BREWS_PROCESS_STATE, processState);
            values.put(COLUMN_BREWS_SUGAR, sugarWeight);
            values.put(COLUMN_BREWS_DEXTROSE, dextroseWeight);
            values.put(COLUMN_BREWS_THICKENERS, thickenerId);
            values.put(COLUMN_BREWS_THICK_WEIGHT, thickenerWeight);
            values.put(COLUMN_BREWS_ENHANCERS, enhancerId);
            values.put(COLUMN_BREWS_ENHANCERS_WEIGHT, enhancerWeight);
            values.put(COLUMN_BREWS_NOTES, notes);
            values.put(COLUMN_BREWS_BITTER, bitter);


            return insertTable(db, TABLE_BREWS, values);
        }
        // TODO добавить необходимый набор set & get
        // TODO ДОБАВИТЬ СТРОКУ ОГРАНИЧИТЕЛЕЙ СТОЛБЦА И ИХ ОБРАБОТКУ
    }



    // *** Вложенный (nested) класс для таблицы Cans
    static abstract class DbCans implements BaseColumns {
        static final String TABLE_CANS = "cans";
        static final String COLUMN_CANS_ID = "_id";
        static final String COLUMN_CANS_WEIGHT = "weight";  // вес охмеленного экстракта
        static final String COLUMN_CANS_VOLUME = "volume";  // рекомендованный объем, справочное значение
        // некоторая избыточность по коду при работе с REAL имеет место преднамеренно
        static final String CREATE_TABLE_CANS = CREATE_TABLE + TABLE_CANS +
                "(" + _ID_IPKA + _COM + COLUMN_CANS_WEIGHT + TYPE_REAL + _COM +
        COLUMN_CANS_VOLUME + TYPE_REAL + ")";
        // Добавление записи в таблицу
        static long insertCans(SQLiteDatabase db, double weight, double volume) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CANS_WEIGHT, weight);
            values.put(COLUMN_CANS_VOLUME, volume);
            return insertTable(db, TABLE_CANS, values);
        }
        // Получение id
        static int getCanId(SQLiteDatabase db, double weight, double volume) {
            return getCanId(db, Double.toString(weight), Double.toString(volume));
        }
        static int getCanId(SQLiteDatabase db, String weight, String volume) {
            String columnInf = COLUMN_CANS_WEIGHT + "=?  AND " + COLUMN_CANS_VOLUME + "=? ";
            return Integer.parseInt(getCells(db, TABLE_CANS,COLUMN_CANS_ID, columnInf, new String[]{weight, volume}).get(0));
        }

        // Получение weight по id (String)
        static String getCanWeight(SQLiteDatabase db, String canID) {
            return getCellSimple(db, TABLE_CANS, COLUMN_CANS_WEIGHT, COLUMN_CANS_ID, canID);
        }
        // Получение weight по id (int)
        static String getCanWeight(SQLiteDatabase db, int canID) {
            return getCanWeight(db, Integer.toString(canID));
        }
        // Получение volume по id (String)
        static String getCanVolume(SQLiteDatabase db, String canID) {
            return getCellSimple(db, TABLE_CANS, COLUMN_CANS_VOLUME, COLUMN_CANS_ID, canID);
        }
        // Получение volume по id (int)
        static String getCanVolume(SQLiteDatabase db, int canID) {
            return getCanVolume(db, Integer.toString(canID));
        }
        // Получение кортежа из таблицы Cans по заданному Id
        public static ContentValues getCanCortegeById (SQLiteDatabase db, String Id){
            return getSelectColumnsByFilter(db, TABLE_CANS,
            null, COLUMN_CANS_ID + " = ?", new String[] {Id}, null) .get(0);
        }
    }


    // *** Вложенный (nested) класс для таблицы Thickeners
    static abstract class DbThickeners implements BaseColumns {
        static final String TABLE_THICKENERS = "thickeners";
        static final String COLUMN_THICKENERS_ID = "_id";
        static final String COLUMN_THICKENERS_NAME = "name";
        static final String CREATE_TABLE_THICKENERS = CREATE_TABLE + TABLE_THICKENERS +
                "(" + _ID_IPKA + _COM + COLUMN_THICKENERS_NAME + TYPE_TEXT + ")";
        // Добавление записи в таблицу
        static long insertThickener(SQLiteDatabase db, String thickener) {
            return insertSimpeTable(db, TABLE_THICKENERS, COLUMN_THICKENERS_NAME, thickener);
        }
        // Получение id по названию
        static int getThickenerId(SQLiteDatabase db, String thickener) {
            return Integer.parseInt(getCellSimple(db, TABLE_THICKENERS, COLUMN_THICKENERS_ID, COLUMN_THICKENERS_NAME, thickener));
        }
        // Получение thickener по id (String)
        static String getThickener(SQLiteDatabase db, String thickenerID) {
            return getCellSimple(db, TABLE_THICKENERS, COLUMN_THICKENERS_NAME, COLUMN_THICKENERS_ID, thickenerID);
        }
        // Получение thickener по id (int)
        static String getThickener(SQLiteDatabase db, int thickenerID) {
            return getThickener(db, Integer.toString(thickenerID));
        }
    }

    // *** Вложенный (nested) класс для таблицы Enhancers
    static abstract class DbEnhancers implements BaseColumns {
        static final String TABLE_ENHANCERS = "enhancers";
        static final String COLUMN_ENHANCERS_ID = "_id";
        static final String COLUMN_ENHANCERS_NAME = "name";
        static final String CREATE_TABLE_ENHANCERS = CREATE_TABLE + TABLE_ENHANCERS +
                "(" + _ID_IPKA + _COM + COLUMN_ENHANCERS_NAME + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        static long insertEnhancer(SQLiteDatabase db, String enhancer) {
            return insertSimpeTable(db, TABLE_ENHANCERS, COLUMN_ENHANCERS_NAME, enhancer);
        }
        // Получение id по названию
        static int getEnhancerId(SQLiteDatabase db, String enhancer) {
            return Integer.parseInt(getCellSimple(db, TABLE_ENHANCERS, COLUMN_ENHANCERS_ID, COLUMN_ENHANCERS_NAME, enhancer));
        }
        // Получение enhancer по id (String)
        static String getEnhancer(SQLiteDatabase db, String enhancerID) {
            return getCellSimple(db, TABLE_ENHANCERS, COLUMN_ENHANCERS_NAME, COLUMN_ENHANCERS_ID, enhancerID);
        }
        // Получение enhancer по id (int)
        static String getEnhancer(SQLiteDatabase db, int enhancerID) {
            return getEnhancer(db, Integer.toString(enhancerID));
        }
    }

    // *** Вложенный (nested) класс для таблицы States
    static abstract class DbStates implements BaseColumns {
        static final String TABLE_STATES = "states";
        static final String COLUMN_STATES_ID = "_id";
        static final String COLUMN_STATES_TEXT = "state_text";
        static final String CREATE_TABLE_STATES = CREATE_TABLE + TABLE_STATES +
                "(" + _ID_IPKA + _COM + COLUMN_STATES_TEXT + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        static long insertState(SQLiteDatabase db, String processState) {
            return insertSimpeTable(db, TABLE_STATES, COLUMN_STATES_TEXT, processState);
        }
    }

    // *** Вложенный (nested) класс для таблицы Names
    static abstract class DbNames implements BaseColumns {
        static final String TABLE_NAMES = "names";
        static final String COLUMN_NAMES_ID = "_id";
        static final String COLUMN_NAMES_NAME = "name";
        static final String CREATE_TABLE_NAMES = CREATE_TABLE + TABLE_NAMES +
                "(" + _ID_IPKA + _COM + COLUMN_NAMES_NAME + TYPE_TEXT + ")";

        // Добавление записи в таблицу
        static long insertName(SQLiteDatabase db, String name) {
            return insertSimpeTable(db, TABLE_NAMES, COLUMN_NAMES_NAME, name);
        }

        // Получение данных из таблицы
        public static String getName(SQLiteDatabase db, int nameID) {
            String strNameID = Integer.toString(nameID);
            return getCellSimple(db, TABLE_NAMES, COLUMN_NAMES_NAME, COLUMN_NAMES_ID, strNameID);
        }
        public static String getName(SQLiteDatabase db, String nameID) {
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
        static long insertBrand(SQLiteDatabase db, String brand) {
            return insertSimpeTable(db, TABLE_BRANDS, COLUMN_BRANDS_BRAND, brand);
        }

        // Получение данных из таблицы
        public static String getBrand(SQLiteDatabase db, int brandID) {
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
        static final String COLUMN_VERIETIES_CAN_ID = "can_id";
        static final String COLUMN_VERIETIES_SRCIMG = "src_img";
        static final String COLUMN_VERIETIES_HREFIMG = "href_img";
        static final String COLUMN_VERIETIES_DESCRIPTION = "description";
        // метатаблица типов
        static final String[][] MT_VERIETIES_COLUMNS_TYPES = {{COLUMN_VERIETIES_ID, TYPE_INT},
            {COLUMN_VERIETIES_NAME_ID, TYPE_INT}, {COLUMN_VERIETIES_BRAND_ID, TYPE_INT},
            {COLUMN_VERIETIES_BITTER, TYPE_INT}, {COLUMN_VERIETIES_COLOR, TYPE_INT},
            {COLUMN_VERIETIES_CAN_ID, TYPE_INT}, {COLUMN_VERIETIES_SRCIMG, TYPE_TEXT},
            {COLUMN_VERIETIES_HREFIMG, TYPE_TEXT}, {COLUMN_VERIETIES_DESCRIPTION, TYPE_TEXT}};

        static final String CREATE_TABLE_VERIETIES = CREATE_TABLE + TABLE_VERIETIES +
                "(" + _ID_IPKA + getInitString(MT_VERIETIES_COLUMNS_TYPES) + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_NAME_ID + ") " +
                "REFERENCES " + DbNames.TABLE_NAMES + "(" + DbNames.COLUMN_NAMES_ID + ")" + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_BRAND_ID + ") " +
                "REFERENCES " + DbBrands.TABLE_BRANDS + "(" + DbBrands.COLUMN_BRANDS_ID + ")" + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_CAN_ID + ") " +
                "REFERENCES " + DbCans.TABLE_CANS + "(" + DbCans.COLUMN_CANS_ID + "))";

        // Добавление записи в таблицу
        static long insertVerieties(SQLiteDatabase db, String name, String brand, int bitter,
            int color, int canID, String srcImg, String refImg, String description) {
            int nameID = DbNames.getNameId(db, name);
            int brandID = DbBrands.getBrandId(db, brand);
            return insertVerieties(db, nameID, brandID, bitter, color, canID, srcImg, refImg, description);
        }
        static long insertVerieties(SQLiteDatabase db, int nameID, int brandID, int bitter,
            int color, int canID, String srcImg, String refImg, String description) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VERIETIES_NAME_ID, nameID);
            values.put(COLUMN_VERIETIES_BRAND_ID, brandID);
            values.put(COLUMN_VERIETIES_BITTER,bitter);
            values.put(COLUMN_VERIETIES_COLOR,color);
            values.put(COLUMN_VERIETIES_CAN_ID,canID);
            values.put(COLUMN_VERIETIES_SRCIMG,srcImg);
            values.put(COLUMN_VERIETIES_HREFIMG,refImg);
            values.put(COLUMN_VERIETIES_DESCRIPTION,description);
            return insertTable(db, TABLE_VERIETIES, values);
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

        // Получение кортежа из таблицы Verieties по заданному Id
        public static ContentValues getVerietyCortegeById (SQLiteDatabase db, String Id) {

        return getSelectColumnsByFilter(db, TABLE_VERIETIES,
            null, COLUMN_VERIETIES_ID + " = ?", new String[] {Id}, null) .get(0);
        }

        // Получение всех колонок кроме ссылки на img для всех Coopers, чей bitter = [bitterMin...bitterMax]
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
        // Получение списка id кортежей, содержащих выбранное название
        static ArrayList<String> getVerietyIdByNameId (SQLiteDatabase db, long nameId) {
            String columnInf = COLUMN_VERIETIES_NAME_ID + "=? ";
            return getCells(db, TABLE_VERIETIES, COLUMN_VERIETIES_ID, columnInf, new String[] {Long.toString(nameId)});
        }
    }


    // *************** Общие методы

    // *** Блок методов получения данных из таблиц

    // Применение различных вариантов для разных задач (местами ради чистого знания).
    @NonNull
    private static String getInitString(String[][] metaData) {
        StringBuffer result = new StringBuffer(BUFFER_STRING_SIZE);
        for (int i=1; i< metaData.length; i++) {
            result.append(" ,").append(metaData[i][0]).append(metaData[i][1]);
            // Можно добавить установку начальных значений, ограничений и так далее
        }
        return result.toString();
        }

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
                                resultRow.put(columnName, cursor.getDouble(columnNum));
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
                        result.add(Double.toString(cursor.getDouble(columnNum)));
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
    // выборка единственнй ячейки по условию в одной строке (типа имя по id или наоборот
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
                    result = Double.toString(cursor.getDouble(columnNum));
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
    private static long insertSimpeTable(SQLiteDatabase db, String tableName, String columnName, String cellValue) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(columnName, cellValue);
        return db.insert(tableName, null, rowValues);
    }

    //  метод insert для непростых (>2 столбцов) таблиц
    private static long insertTable (SQLiteDatabase db, String tableName,ContentValues rowValues){
        long il = 0;
        try {
            il = db.insert(tableName, null, rowValues);

        //    res++;
        } catch(SQLiteException e) {
           // "SQLiteDb TABLE " + tableName + " insert fault";
            // TODO доработать Exception

        }
        return il;
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