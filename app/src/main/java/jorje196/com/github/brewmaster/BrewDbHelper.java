package jorje196.com.github.brewmaster;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 01.11.2017.
 * Helper для создания и управления базой данных
 */

class BrewDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BrewDb.db";
    private static final int DB_VERSION = 1;



    public static final String TABLE_BRANDS = "brands";
    public static final String COLUMN_BRANDS_ID = "_id";
    public static final String COLUMN_BRANDS_BRAND = "brand";



    public static final String TABLE_VERIETIES = "verieties";
    public static final String COLUMN_VERIETIES_ID ="_id";
    public static final String COLUMN_VERIETIES_BRAND_ID = "brand_id";
    public static final String COLUMN_VERIETIES_NAME_ID = "name_id";
    public static final String COLUMN_VERIETIES_HREFIMG = "href_img";
    public static final String COLUMN_VERIETIES_BITTER = "bitter";
    public static final String COLUMN_VERIETIES_COLOR = "color";
    public static final String COLUMN_VERIETIES_DESCRIPTION = "description";




    public BrewDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        int i = 1;
    }

     public static long exceptCount = 0;



    @Override
  /* Метод вызывается при создании БД .
  Создаем таблицы и записываем данные.
   */
    public void onCreate(SQLiteDatabase db){
        String sqlString;
        try {
            // опредеяем табл. BRANDS
 /*           String current = CREATE_TABLE + TABLE_BRANDS +
                    "(" + _ID_IPKA + _COM + COLUMN_BRANDS_BRAND + TYPE_INT + ")";
            db.execSQL(current);  */
            // определяем табл. NAMES

            db.execSQL(DbContract.DbNames.CREATE_TABLE_NAMES);
/*            // определяем табл. VARIETIES

            sqlString = CREATE_TABLE + TABLE_VERIETIES +
                "(" + _ID_IPKA + _COM + COLUMN_VERIETIES_BRAND_ID + TYPE_INT + _COM +
                COLUMN_VERIETIES_NAME_ID + TYPE_INT + _COM +
                COLUMN_VERIETIES_BITTER + TYPE_INT + _COM +
                COLUMN_VERIETIES_DESCRIPTION + TYPE_TEXT + _COM +
                COLUMN_VERIETIES_HREFIMG + TYPE_TEXT + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_NAME_ID + ") " +
                "REFERENCES " + TABLE_NAMES + "(" + COLUMN_NAMES_ID + ")" + _COM +
                "FOREIGN KEY (" + COLUMN_VERIETIES_BRAND_ID + ") " +
                "REFERENCES " + TABLE_BRANDS + "(" + COLUMN_BRANDS_ID + "))";

            // Log.i("sqlString: ",sqlString);
            db.execSQL(sqlString); */

        // первичное заполнение таблиц
            // названия
            DbContract.DbNames.insertName(db, "Draught");
            String str = DbContract.DbNames.getName(db, 1);
            int i = DbContract.DbNames.getNameId(db, "Draught");

            DbContract.DbNames.insertName(db, "Lager");
            DbContract.DbNames.insertName(db, "Real Ale");
            DbContract.DbNames.insertName(db, "Dark Ale");
            DbContract.DbNames.insertName(db, "Stout");
            DbContract.DbNames.insertName(db, "Indian Pale Ale (IPA)");
            DbContract.DbNames.insertName(db, "English Bitter");
            DbContract.DbNames.insertName(db, "Wheat Beer");

            /*
            // бренды
            MainBeerActivity.brewDbRepos.insertBrand("Coopers");
            MainBeerActivity.brewDbRepos.insertBrand("Muntons");
            MainBeerActivity.brewDbRepos.insertBrand("Finlandia");
            MainBeerActivity.brewDbRepos.insertBrand("BrewDemon");
            MainBeerActivity.brewDbRepos.insertBrand("Brewferm");
            MainBeerActivity.brewDbRepos.insertBrand("Inpinto");

            ContentValues rowValues = new ContentValues();



            rowValues.put(COLUMN_VERIETIES_NAME_ID, 1);
            rowValues.put(COLUMN_VERIETIES_BRAND_ID, 1);
            rowValues.put(COLUMN_VERIETIES_BITTER, 31);
            rowValues.put(COLUMN_VERIETIES_COLOR, 10);
            rowValues.put(COLUMN_VERIETIES_HREFIMG, "http://www.coopersbeer.ru/draught _with-.jpg");
            rowValues.put(COLUMN_VERIETIES_DESCRIPTION, "Светлое горьковатое , универсальное " +
                "по сезону и продуктам");
            db.insert(TABLE_VERIETIES, null, rowValues);

            rowValues.put(COLUMN_VERIETIES_NAME_ID, 2);
            rowValues.put(COLUMN_VERIETIES_BRAND_ID, 1);
            rowValues.put(COLUMN_VERIETIES_BITTER, 29);
            rowValues.put(COLUMN_VERIETIES_COLOR, 7);
            rowValues.put(COLUMN_VERIETIES_HREFIMG, "http://www.coopersbeer.ru/european-lager.jpg");
            rowValues.put(COLUMN_VERIETIES_DESCRIPTION, "Очень светлое слабо горькое , летнее, " +
                "рыба, белая птица");
            db.insert(TABLE_VERIETIES, null, rowValues);

            rowValues.put(COLUMN_VERIETIES_NAME_ID, 3);
            rowValues.put(COLUMN_VERIETIES_BRAND_ID, 1);
            rowValues.put(COLUMN_VERIETIES_BITTER, 41);
            rowValues.put(COLUMN_VERIETIES_COLOR, 17);
            rowValues.put(COLUMN_VERIETIES_HREFIMG, "http://www.coopersbeer.ru/real-ale_.jpg");
            rowValues.put(COLUMN_VERIETIES_DESCRIPTION, "Среднее горьковатое , универсальное по сезону, " +
                "мясо, птица");
            db.insert(TABLE_VERIETIES, null, rowValues);  */
        } catch (SQLException e) {
            exceptCount++;
        }

    }

    @Override
    // Метод вызывается при необходимости обновить БД
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // определим позже
  }

}
        /*    rowValues.put(COLUMN_NAMES_NAME, "Draught");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Lager");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Real Ale");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Dark Ale");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Stout");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Indian Pale Ale (IPA)");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "English Bitter");
            db.insert(TABLE_NAMES, null, rowValues);
            rowValues.put(COLUMN_NAMES_NAME, "Wheat Beer");
            db.insert(TABLE_NAMES, null, rowValues);*/

                    /*rowValues.put(COLUMN_BRANDS_BRAND, "Coopers");
            db.insert(TABLE_BRANDS, null, rowValues);
            rowValues.put(COLUMN_BRANDS_BRAND, "Muntons");
            db.insert(TABLE_BRANDS, null, rowValues);
            rowValues.put(COLUMN_BRANDS_BRAND, "Finlandia");
            db.insert(TABLE_BRANDS, null, rowValues); */