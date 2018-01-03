package jorje196.com.github.brewmaster;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.app.PendingIntent.getActivity;

/**
 * Created by User on 01.11.2017.
 * Helper для создания и управления базой данных
 */

class BrewDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BrewDb.db";
    private static final int DB_VERSION = 1;
    private Context ctx ;
    BrewDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // Для обеспечени возможности обращаться к ресурсам в onCreate & Co
        ctx = context;  // String exp = ctx.getResources().getString(R.string.bitter);
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

            db.execSQL(DbContract.DbStates.CREATE_TABLE_STATES);


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
            DbContract.DbThickeners.insertThickener(db, "Without thickener");
            DbContract.DbThickeners.insertThickener(db, "maltodextrin");

                // усилители
            DbContract.DbEnhancers.insertEnhancer(db, "Without enhancer");
            DbContract.DbEnhancers.insertEnhancer(db, "Coopers Light Dry Malt");
            DbContract.DbEnhancers.insertEnhancer(db, "Muntons Light Malt Extract");
            DbContract.DbEnhancers.insertEnhancer(db, "Muntons Dark Malt Extract");

                // банки
            DbContract.DbCans.insertCans(db, 1.7, 23.);
            DbContract.DbCans.insertCans(db, 1.8, 23.);
            DbContract.DbCans.insertCans(db, 1.5, 18.);

                // состояния процесса

            DbContract.DbStates.insertState(db, ctx.getResources().getString(R.string.in_progress));
            DbContract.DbStates.insertState(db, ctx.getResources().getString(R.string.complited));


                // итоговая (brand+name) таблица характеристик
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Coopers",    //1
                    420, 130, 1, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Muntons",    //2
                    420, 130, 2, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Draught", "Finlandia",  //3
                    420, 130, 3, "draught_small",
                    "http://store.coopers.com.au/draught-1-7kg.html",
                    "Светлое слабо горькое , скорее летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Lager", "Coopers",      //4
                    390, 75, 1, "lager_small",
                    "http://store.coopers.com.au/lager-1-7kg.html",
                    "Очень светлое слабо горькое , летнее, рыба, белая птица");
            DbContract.DbVerieties.insertVerieties(db, "English Bitter", "Coopers", //5
                    620, 420, 1, "english_bitter_small",
                    "http://store.coopers.com.au/english-bitter-1-7kg.html",
                    "Горькое, универсальное по сезону, ближе к мясу с соответствующими соусами и гарниром");
            DbContract.DbVerieties.insertVerieties(db, "Real Ale", "Coopers",       //6
                    560, 230, 1, "real_ale_small",
                    "http://store.coopers.com.au/real-ale-1-7kg.html",
                    "Среднее горьковатое , универсальное по сезону, мясо, птица");
            DbContract.DbVerieties.insertVerieties(db, "Dark Ale", "Coopers",       //7
                    590, 650, 1, "dark_ale_small",
                    "http://store.coopers.com.au/dark-ale.html",
                    "Горькое, темное, универсальное по сезону, но приятнее зимой и к мясу ");
            DbContract.DbVerieties.insertVerieties(db, "Stout", "Coopers",          //8
                    710, 1800, 1, "stout_small",
                    "http://store.coopers.com.au/stout-1-7kg.html",
                    "Стаут, универсальное по сезону, но особенно хорош зимним вечером ");
            DbContract.DbVerieties.insertVerieties(db, "Indian Pale Ale (IPA)", "Coopers",  //9
                    830, 230, 1, "brew_a_ipa_small",
                    "http://store.coopers.com.au/thomas-coopers-brew-a-ipa-1-7kg.html",
                    "Горькое, самодостаточное в любое время года, если к мясу, то на гриле");
            DbContract.DbVerieties.insertVerieties(db, "Wheat Beer", "Muntons",             //10
                    30, 8, 1, "mexican_cerveza_small",
                    "http://www.muntonshomebrew.com/wp-content/uploads/2012/09/connoisseur_wheat_beer.jpg",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица");
            DbContract.DbVerieties.insertVerieties(db, "Preacher's Hefe Wheat", "Coopers",      //11
                    340, 65, 1, "preacher_s_hefe_wheat_small",
                    "http://store.coopers.com.au/thomas-coopers-preacher-s-hefe-wheat-1-7kg.html",
                    "Пшеничное, скорее летнее, самодостаточно, на любителя белая птица и пряности");
            DbContract.DbVerieties.insertVerieties(db, "Australian Pale Ale", "Coopers",        //12
                    340, 90, 1, "australian_pale_ale_small",
                    "http://store.coopers.com.au/australian-pale-ale-1-7kg.html",
                    "Светлое, скорее летнее, средней горечи, хорошо уживается с любым темным мясом");
            DbContract.DbVerieties.insertVerieties(db, "Canadian Blonde", "Coopers",                //13
                    420, 55, 1, "canadian_blonde_small",
                    "http://store.coopers.com.au/canadian-blonde-1-7kg.html",
                    "Светлое, скорее летнее, средней горечи, хорошо уживается с любым темным мясом");
            DbContract.DbVerieties.insertVerieties(db, "European Lager", "Coopers",                 //14
                    340, 90, 1, "european_lager_small",
                    "http://store.coopers.com.au/european-lager-1-7kg.html",
                    "Светлое, скорее летнее, средней горечи, предпочтительно к рыбе или птице");
            DbContract.DbVerieties.insertVerieties(db, "Mexican Cerveza", "Coopers",                //15
                    420, 53, 1, "mexican_cerveza_small",
                    "http://store.coopers.com.au/mexican-cerveza-1-7kg.html",
                    "Светлое, летнее, средней горечи со сладковатым привкусом, хорошо сочетается с " +
                            "'острыми' блюдами");
            DbContract.DbVerieties.insertVerieties(db, "Irish Stout", "Coopers",                       //16
                    560, 1650, 1, "irish_stout_small",
                    "http://store.coopers.com.au/irish-stout-1-7kg.html",
                    "Очень темое, абсолютно самодостаточно, хорошо сочетается с " +
                            "холодными зимними вечерами, промозглая погода тоже подходит. " +
                            "но употреблять строго во второй половине дня !");

            // TODO может длинные вынести в string-ресурс ?

                // варки
            DbContract.DbBrews.insertBrews(db, 6, 25.5, "2015-09-18", 8.0, 26, 1.25,
                    23, "2015-09-25", 3.5, "2015-10-11", 2, 0,
                    1.0, 1, 0, 1, 0, "Чтой-то перелив вышел", 0);

            DbContract.DbBrews.insertBrews(db, 7, 23.5, "2015-09-27", 8.0, 29, 1.0,
                    23, "2015-10-04", 3.4, "2015-10-20", 2, 0,
                    1.0, 1, 0.0, 1, 0.0, "", 0);

            DbContract.DbBrews.insertBrews(db, 8, 22.0, "2015-11-07", 10.0, 25, 1.5,
                    22, "2015-11-13", 4.5, "2015-11-27", 2, 0,
                    1.0, 1, 0, 1, 0, " Закопать до декабря 2016 !", 0);

            DbContract.DbBrews.insertBrews(db, 14, 24.0, "2015-11-13", 8.5, 26, 2.0,
                    22, "2015-11-24", 3.25, "2015-12-11", 2, 0.0,
                    1.0,1, 0, 1, 0, "Best after : февраль 2016", 0);

            DbContract.DbBrews.insertBrews(db, 11, 23.0, "2015-12-16", 10.5, 28, 1.0,
                    23, "2015-12-25", 3.75, "2016-01-14", 2, 0.0,
                    1.0, 1, 0, 1, 0, "!? 5 литров ушло в пропасть.", 0);

            DbContract.DbBrews.insertBrews(db, 7, 24.0, "2015-12-26", 8.0, 27, 1.0,
                    20, "2016-01-05", 4, "2016-01-20", 2, 0,
                    1.0, 1, 0.0, 1, 0.0, "Норм. Поток.", 0);

            DbContract.DbBrews.insertBrews(db, 9, 24.0, "2016-01-05", 9.0, 26, 1.5,
                     20,"2016-01-13", 4.25, "2016-01-21", 2, 0,
                     1.0, 1, 0, 1, 0, "Последняя варка в серии", 0);

            DbContract.DbBrews.insertBrews(db, 6, 24, "2016-05-13", 9.0, 28, 1.0,
                    23, "2016-05-19", 4.0, "2016-06-01", 1, 0,
                    1.0, 1, 0, 1, 0, "Летний почин", 0);

            DbContract.DbBrews.insertBrews(db, 1, 23.0, "2016-05-20", 9.0, 26, 1.5,
                    23, "2016-05-26", 4.25, "2016-06-10", 2, 0,
                    1.0, 1,0, 1, 0, "Задел на август", 0);

            DbContract.DbBrews.insertBrews(db, 14, 23.5, "2016-05-27", 9.0, 28, 1.0,
                    25, "2016-06-03", 4.5, "2016-06-18", 2, 0.0,
                    1.0,1, 0, 1, 0, "Выпить летом", 0);

            DbContract.DbBrews.insertBrews(db, 12, 24.0, "2016-07-20", 8.5, 28, 1.0,
                    27, "2016-07-24", 4.25, "2016-07-10", 2, 0.0,
                    1.0,1, 0, 1, 0, "", 0);

            DbContract.DbBrews.insertBrews(db, 6, 24.5, "2016-08-24", 8.5, 28, 1.0,
                    26, "2016-08-29", 4.25, "2016-09-11", 2, 0,
                    1.0, 1, 0, 1, 0, "По накатанной", 37);

            DbContract.DbBrews.insertBrews(db, 7, 24.0, "2016-09-17", 8.5, 28, 1.0,
                    22, "2016-09-26", 4.25, "2016-10-10", 2, 0,
                    1.0, 1, 0.0, 1, 0.0, "Ждало 4 дня , был занят", 21);

            DbContract.DbBrews.insertBrews(db, 9, 24.0, "2016-09-26", 9.0, 28, 1.0,
                    20,"2016-01-13", 4.25, "2016-01-21", 1, 0,
                    1.0, 1, 0, 1, 0, "Осенняя серия", 72);




            /*
            DbContract.DbBrews.insertBrews(db, 7, 23.0, "2016-09-23 17ч", 9.0, 30, 0.5);
            DbContract.DbBrews.insertBrews(db, 8, 23.0, "2016-10-12 12ч", 9.5, 28, 1.0);
            DbContract.DbBrews.insertBrews(db, 9, 23.0, "2016-10-21:11ч", 9.5, 27, 1.0);
            DbContract.DbBrews.insertBrews(db, 14, 23.0, "2016-09-23 17ч", 9.0, 30, 0.5);
            DbContract.DbBrews.insertBrews(db, 3, 23.0, "2016-10-21:11ч", 9.5, 28, 1.5); */

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