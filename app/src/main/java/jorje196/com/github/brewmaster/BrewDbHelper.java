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
            DbContract.DbNames.insertName(db, "Preacher's Hefe Wheat");
            DbContract.DbNames.insertName(db, "Australian Pale Ale");
            DbContract.DbNames.insertName(db, "Canadian Blonde");
            DbContract.DbNames.insertName(db, "European Lager");
            DbContract.DbNames.insertName(db, "Mexican Cerveza");
            DbContract.DbNames.insertName(db, "Irish Stout");

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
            DbContract.DbCans.insertCans(db, 1.8, 23.);
            DbContract.DbCans.insertCans(db, 1.5, 18.);


                // итоговая (brand+name) таблица характеристик
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Coopers",
                    420, 130, 1, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Muntons",
                    420, 130, 2, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Finlandia",
                    420, 130, 3, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Lager", "Coopers",
                    390, 75, 1, "lager_small",
                    "http://store.coopers.com.au/lager-1-7kg.html",
                    "Очень светлое слабо горькое , летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "English Bitter", "Coopers",
                    620, 420, 1, "english_bitter_small",
                    "http://store.coopers.com.au/english-bitter-1-7kg.html",
                    "Горькое, универсальное по сезону, ближе к мясу с соответствующими соусами и гарниром");
            DbContract.DbVerieties.insertVerieties(db, "Real Ale", "Coopers",
                    560, 230, 1, "real_ale_small",
                    "http://store.coopers.com.au/real-ale-1-7kg.html",
                    "Среднее горьковатое , универсальное по сезону, мясо, птица");
            DbContract.DbVerieties.insertVerieties(db, "Dark Ale", "Coopers",
                    590, 650, 1, "dark_ale_small",
                    "http://store.coopers.com.au/dark-ale.html",
                    "Горькое, темное, универсальное по сезону, но приятнее зимой и к мясу ");
            DbContract.DbVerieties.insertVerieties(db, "Stout", "Coopers",
                    710, 1800, 1, "stout_small",
                    "http://store.coopers.com.au/stout-1-7kg.html",
                    "Стаут, универсальное по сезону, но особенно хорош зимним вечером ");
            DbContract.DbVerieties.insertVerieties(db, "Indian Pale Ale (IPA)", "Coopers",
                    830, 230, 1, "brew_a_ipa_small",
                    "http://store.coopers.com.au/thomas-coopers-brew-a-ipa-1-7kg.html",
                    "Горькое, самодостаточное в любое время года, если к мясу, то на гриле");
            DbContract.DbVerieties.insertVerieties(db, "Wheat Beer", "Muntons",
                    30, 8, 1, "mexican_cerveza_small",
                    "http://www.muntonshomebrew.com/wp-content/uploads/2012/09/connoisseur_wheat_beer.jpg",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Preacher's Hefe Wheat", "Coopers",
                    340, 65, 1, "preacher_s_hefe_wheat_small",
                    "http://store.coopers.com.au/thomas-coopers-preacher-s-hefe-wheat-1-7kg.html",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица и пряности");
            DbContract.DbVerieties.insertVerieties(db, "Australian Pale Ale", "Coopers",
                    340, 90, 1, "australian_pale_ale_small",
                    "http://store.coopers.com.au/australian-pale-ale-1-7kg.html",
                    "Светлое, скорее летнее, средней горечи, хорошо уживается с любым темным мясом");
            DbContract.DbVerieties.insertVerieties(db, "Canadian Blonde", "Coopers",
                    420, 55, 1, "canadian_blonde_small",
                    "http://store.coopers.com.au/canadian-blonde-1-7kg.html",
                    "светлое, скорее летнее, средней горечи, хорошо уживается с любым темным мясом");
            DbContract.DbVerieties.insertVerieties(db, "European Lager", "Coopers",
                    340, 90, 1, "european_lager_small",
                    "http://store.coopers.com.au/european-lager-1-7kg.html",
                    "светлое, скорее летнее, средней горечи, предпочтительно к рыбе или птице");
            DbContract.DbVerieties.insertVerieties(db, "Mexican Cerveza", "Coopers",
                    420, 53, 1, "mexican_cerveza_small",
                    "http://store.coopers.com.au/mexican-cerveza-1-7kg.html",
                    "светлое, летнее, средней горечи со сладковатым привкусом, хорошо сочетается с " +
                            "'острыми' блюдами");
            DbContract.DbVerieties.insertVerieties(db, "Irish Stout", "Coopers",
                    560, 1650, 1, "irish_stout_small",
                    "http://store.coopers.com.au/irish-stout-1-7kg.html",
                    "очень темое, абсолютно самодостаточно, хорошо сочетается с " +
                            "холодными зимними вечерами, промозглая погода тоже подходит. " +
                            "но употреблять строго во второй половине дня !");

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