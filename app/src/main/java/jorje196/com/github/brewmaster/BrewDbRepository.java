package jorje196.com.github.brewmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_BRANDS_BRAND;
import static jorje196.com.github.brewmaster.BrewDbHelper.COLUMN_NAMES_NAME;
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
    // добавление записи в Brands
    public void insertBrand(String brand) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_BRANDS_BRAND, brand);
        db.insert(TABLE_BRANDS, null, rowValues);
    }
    // добавление записи в Names
    public void insertName(String name) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_NAMES_NAME, name);
        db.insert(TABLE_NAMES, null, rowValues);
    }

}
