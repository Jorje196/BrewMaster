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

    BrewDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
  /* Метод вызывается при создании БД .
  Создаем таблицы и записываем данные.
   */
    public void onCreate(SQLiteDatabase db){

        try {
            // создание таблиц , справочные first
            db.execSQL(DbContract.DbBrands.CREATE_TABLE_BRANDS);
            db.execSQL(DbContract.DbNames.CREATE_TABLE_NAMES);
            db.execSQL(DbContract.DbCans.CREATE_TABLE_CANS);
            db.execSQL(DbContract.DbThickeners.CREATE_TABLE_THICKENERS);
            db.execSQL(DbContract.DbEnhancers.CREATE_TABLE_ENHANCERS);

            db.execSQL(DbContract.DbVerieties.CREATE_TABLE_VERIETIES);

            db.execSQL(DbContract.DbBrews.CREATE_TABLE_BREWS);


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


                // бренды
            DbContract.DbBrands.insertBrand(db, "Coopers");
            DbContract.DbBrands.insertBrand(db, "Muntons");
            DbContract.DbBrands.insertBrand(db, "Finlandia");
            DbContract.DbBrands.insertBrand(db, "BrewDemon");
            DbContract.DbBrands.insertBrand(db, "Brewferm");
            DbContract.DbBrands.insertBrand(db, "Inpinto");

                // загустители
            DbContract.DbThickeners.insertThickener(db, "maltodextrin");

                // усилители
            DbContract.DbEnhancers.insertEnhancer(db, "Coopers Light Dry Malt");
            DbContract.DbEnhancers.insertEnhancer(db, "Muntons Light Malt Extract");
            DbContract.DbEnhancers.insertEnhancer(db, "Muntons Dark Malt Extract");

                // банки
            DbContract.DbCans.insertCans(db, 1.7, 23.);

                // итоговая (brand+name) таблица характеристик
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Coopers",
                    31, 10,
                    "http://www.coopersbeer.ru/draught_with-.jpg",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Lager", "Coopers",
                    29, 7,
                    "http://www.coopersbeer.ru/LAGER.jpg",
                    "Очень светлое слабо горькое , летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "English Bitter", "Coopers",
                    43, 31,
                    "http://www.coopersbeer.ru/imgs/beer/s_english_bitter.jpg",
                    "Горькое, универсальное по сезону, ближе к мясу с соответствующими соусами и гарниром");
            DbContract.DbVerieties.insertVerieties(db, "Real Ale", "Coopers",
                    41, 17,
                    "http://www.coopersbeer.ru/real-ale_.jpg",
                    "Среднее горьковатое , универсальное по сезону, мясо, птица");
            DbContract.DbVerieties.insertVerieties(db, "Dark Ale", "Coopers",
                    43, 48,
                    "http://www.coopersbeer.ru/diy-dark-ale_with-glass_1450063652.jpg",
                    "Горькое, темное, универсальное по сезону, но приятнее зимой и к мясу ");
            DbContract.DbVerieties.insertVerieties(db, "Stout", "Coopers",
                    52, 133,
                    "http://www.coopersbeer.ru/diy-stout_.jpg",
                    "Стаут, универсальное по сезону, но особенно хорош зимним вечером ");
            DbContract.DbVerieties.insertVerieties(db, "Indian Pale Ale (IPA)", "Coopers",
                    62, 17,
                    "http://www.coopersbeer.ru/brew_glass_ipa_.jpg",
                    "Горькое, самодостаточное в любое время года, если к мясу, то на гриле");
            DbContract.DbVerieties.insertVerieties(db, "Wheat Beer", "Muntons",
                    30, 8,
                    "http://www.muntonshomebrew.com/wp-content/uploads/2012/09/connoisseur_wheat_beer.jpg",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица");
            // TODO может длинные вынести в string-ресурс ?

                // варки
            DbContract.DbBrews.insertBrews(db, 1, 23.0, "2016-09-23 17ч", 9.0, 30, 0.5);
            DbContract.DbBrews.insertBrews(db, 2, 23.0, "2016-10-12 12ч", 9.5, 28, 1.0);
            DbContract.DbBrews.insertBrews(db, 3, 23.0, "2016-10-21:11ч", 9.5, 27, 1.0);
            DbContract.DbBrews.insertBrews(db, 4, 23.0, "2016-11-03:19ч", 9.0, 26, 1.5);
            DbContract.DbBrews.insertBrews(db, 5, 23.0, "2016-11-20:13ч", 10.5, 28, 1.0);
            DbContract.DbBrews.insertBrews(db, 6, 23.0, "2016-12-01:23ч", 10.0, 29, 1.5);


        } catch (SQLException e) {
            // exceptCount++;
            // TODO определить обрабоку исключения
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