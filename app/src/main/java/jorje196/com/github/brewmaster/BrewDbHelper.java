package jorje196.com.github.brewmaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 01.11.2017.
 * Helper для создания и управления базой данных
 */

class BrewDbHelper extends SQLiteOpenHelper {
  private static final String DB_NAME = "BrewDb";
  private static final int DB_VERSION = 1;

  // определение констант для таблиц
  public static final String CREATE_TABLE = "CREATE TABLE";
  public static final String _ID_IPKA = "_id INTEGER PRIMARY KEY AUTOINCREMENT";
  public static final String TYPE_INT = "INTEGER";
  public static final String TYPE_TEXT = "TEXT";
  public static final String TYPE_REAL = "REAL";

  public static final String TABLE_BRANDS = "brands";
  public static final String COLUMN_BRANDS_ID = "_id";
  public static final String COLUMN_BRANDS_BRAND = "brand";

  public static final String TABLE_NAMES = "names";
  public static final String COLUMN_NAMES_ID = "_id";
  public static final String COLUMN_NAMES_NAME ="name";

  public static final String TABLE_VERIETIES = "verieties";
  public static final String COLUMN_VERIETIES_ID ="_id";
  public static final String COLUMN_VERIETIES_BRAND = "brand";
  public static final String COLUMN_VERIETIES_NAME = "name";
  public static final String COLUMN_VERIETIES_HREFIMG = "href_img";
  public static final String COLUMN_VERIETIES_BITTER = "bitter";
  public static final String COLUMN_VERIETIES_COLOR = "color";
  public static final String COLUMN_VERIETIES_DESCRIPTION = "description";





  // http://www.coopersbeer.ru/european-lager.jpg


  private Context dbContext;                      //  a) надо ли оно ?

  public BrewDbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    this.dbContext = context;                     // см a)
  }

  @Override         // при первом запуске определяем DB
  public void onCreate(SQLiteDatabase db){
    // опредеяем таблицу BRANDS
    db.execSQL(CREATE_TABLE + TABLE_BRANDS +
            "(" + _ID_IPKA + "," + COLUMN_BRANDS_BRAND + TYPE_INT + ")");
  }

  @Override         // апгрейд можно определить позже
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

}
