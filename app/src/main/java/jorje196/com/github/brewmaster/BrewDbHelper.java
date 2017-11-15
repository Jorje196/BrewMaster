package jorje196.com.github.brewmaster;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User on 01.11.2017.
 * Helper для создания и управления базой данных
 */

class BrewDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BrewDb.db";
    private static final int DB_VERSION = 1;

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
            db.execSQL(DbContract.DbBrands.CREATE_TABLE_BRANDS);
            // определяем табл. NAMES
            db.execSQL(DbContract.DbNames.CREATE_TABLE_NAMES);
            // определяем табл. VARIETIES
            db.execSQL(DbContract.DbVerieties.CREATE_TABLE_VERIETIES);

        // первичное заполнение таблиц
                // названия
            DbContract.DbNames.insertName(db, "Draught");
            DbContract.DbNames.insertName(db, "Lager");
            DbContract.DbNames.insertName(db, "Real Ale");
            DbContract.DbNames.insertName(db, "Dark Ale");
            DbContract.DbNames.insertName(db, "Stout");
            DbContract.DbNames.insertName(db, "Indian Pale Ale (IPA)");
            DbContract.DbNames.insertName(db, "English Bitter");
            DbContract.DbNames.insertName(db, "Wheat Beer");
 /*    String str = DbContract.DbNames.getName(db, 1);
            int i = DbContract.DbNames.getNameId(db, "Draught"); */
                // бренды
            DbContract.DbBrands.insertBrand(db, "Coopers");
            DbContract.DbBrands.insertBrand(db, "Muntons");
            DbContract.DbBrands.insertBrand(db, "Finlandia");
            DbContract.DbBrands.insertBrand(db, "BrewDemon");
            DbContract.DbBrands.insertBrand(db, "Brewferm");
            DbContract.DbBrands.insertBrand(db, "Inpinto");

                // итоговая (brand+name) таблица характеристик
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Coopers",
                    31, 10,
                    "http://www.coopersbeer.ru/draught _with-.jpg",
                    "Очень светлое слабо горькое , летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Lager", "Coopers",
                    29, 7,
                    "http://www.coopersbeer.ru/LAGER.jpg",
                    "Очень светлое слабо горькое , летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Real Ale", "Coopers",
                    41, 17,
                    "http://www.coopersbeer.ru/real-ale_.jpg",
                    "Среднее горьковатое , универсальное по сезону, мясо, птица");
            DbContract.DbVerieties.insertVerieties(db, "Wheat Beer", "Muntons",
                    30, 8,
                    "http://www.muntonshomebrew.com/wp-content/uploads/2012/09/connoisseur_wheat_beer.jpg",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица");
            // может длинные вынести в string-ресурс ?


            // тестовая часть
            ArrayList<String> bitterList = new ArrayList<String>();
            bitterList = DbContract.DbVerieties.getVerietiesBitter(db, "1");


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