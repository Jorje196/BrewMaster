package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;



import static jorje196.com.github.brewmaster.BrewListFragment.BLF_TAG;
import static jorje196.com.github.brewmaster.BrewListFragment.SQL_BREW_LIST;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW1_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW2_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW3_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW4_STR;
import static jorje196.com.github.brewmaster.DbContract.DbBrands.getBrand;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_FINAL_TEMP;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_ID;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_START_DATA;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_START_WORT_TEMP;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_VOLUME;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.TABLE_BREWS;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_ID;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_VOLUME;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_WEIGHT;
import static jorje196.com.github.brewmaster.DbContract.DbCans.TABLE_CANS;
import static jorje196.com.github.brewmaster.DbContract.DbCans.getCanCortegeById;
import static jorje196.com.github.brewmaster.DbContract.DbCans.getCanWeight;
import static jorje196.com.github.brewmaster.DbContract.DbNames.COLUMN_NAMES_ID;
import static jorje196.com.github.brewmaster.DbContract.DbNames.COLUMN_NAMES_NAME;
import static jorje196.com.github.brewmaster.DbContract.DbNames.TABLE_NAMES;
import static jorje196.com.github.brewmaster.DbContract.DbNames.getName;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_BITTER;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_BRAND_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_CAN_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_COLOR;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_DESCRIPTION;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_NAME_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_SRCIMG;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.TABLE_VERIETIES;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.getVerietyCortegeById;
import static jorje196.com.github.brewmaster.DbContract._COM;
import static jorje196.com.github.brewmaster.DbContract._PNT;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_BITTER;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_BRAND;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_COLOR;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_DESCRIPT;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_ID;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_NAME;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_POSITION;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_SIZE;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_SRCIMG;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_VOLUME;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.ARG_WEIGHT;
import static jorje196.com.github.brewmaster.MaltExtDescriptionFragment.MEDF_TAG;
import static jorje196.com.github.brewmaster.TopFragment.TOPF_TAG;

public class MainBeerActivity extends Activity implements MaltExtDescriptionFragment.FragMaltLink, BrewListFragment.FragBrewListLink {

    private ListView drawerList;    // списковое представление для drawer"а
    private DrawerLayout drawerLayout;
    protected SQLiteDatabase brewDb;
    private Cursor cursor;
    private Cursor cursorBL;


    private int ChoosedId = 0;
    private int numVerietiesInList = 0;
    private int currentPositionInList = 0;
    private String currentFragTag = TOPF_TAG;

 /*   class BrewListSimpeCursorAdapter extends SimpleCursorAdapter {
        BrewListSimpeCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags ){
            super(context, layout, cursor, from, to, flags);
        }
        int currentTextColor = 0;
        @Override
        public void setViewText(TextView v, String text) {
            super.setViewText(v, text);

            if (v.getId() == R.id.text4){
                if (currentTextColor == 0) currentTextColor = v.getCurrentTextColor();
                if (text.equals(getResources().getString(R.string.in_progress))){
                    v.setTextColor(getResources().getColor(R.color.colorAccentNegative));
                } else {
                    v.setTextColor(currentTextColor);
                }

            }

        }
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_beer);

        // подключение к БД
        try   {
            brewDb = new BrewDbHelper(this).getWritableDatabase();
          // проверка по внешним ключам д.б. включена
            brewDb.setForeignKeyConstraintsEnabled(true);
        // TODO Вынести шаблоны строк во фрагмент
          // Инициализация выдвижной панель
            cursor = brewDb.query(TABLE_NAMES,
              new String[] {COLUMN_NAMES_ID, COLUMN_NAMES_NAME},
                    null, null, null, null, COLUMN_NAMES_NAME);
            CursorAdapter dbAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursor, new String[]{COLUMN_NAMES_NAME},
                    new int[]{android.R.id.text1}, 0);

              // ссылка на списочное представление из реса
            drawerList = (ListView) findViewById(R.id.drawer);
              // получить ссылку на drawerLayout
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
              // связываем списочное представление с источником строк через адаптор
            //drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planetsArray));
            drawerList.setAdapter(dbAdapter);
              // создаем экземпляр для слушателя спискового представления
            drawerList.setOnItemClickListener(new DrawerItemClickListener());
              // Выбираем стартовый фрагмент для фрейма
            if (savedInstanceState == null) {
                selectItem(0, 0);
            }
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Problem with Database", Toast.LENGTH_SHORT);
          toast.show();
      }

     }
    // TODO В реализациях методов связи интерфейсов фрагментов : ? нежен ли tag как параметр в обращении ? фрагмент известен и так
    /* Реализация (абстрактного) метода связи getFBLL (BrewListFra...) с родительской активностью */
    @Override
    public void getFBLL(ListFragment fragment, String tag) {
        setCurrentFragTag(tag);
        if(cursorBL==null) {
            // TODO Выбор правил сортировки, нужен ли ?

       /*     String sql1 = "SELECT " + TABLE_VERIETIES + _PNT + COLUMN_VERIETIES_ID + _COM + "CAST(" + COLUMN_CANS_VOLUME + " AS INTEGER)" + '+' + 100 + "*1/3" + " AS " + TEXT_VIEW2_STR +  /* _COM +
                " (" + " 'Volume' ||" + COLUMN_BREWS_VOLUME + "||  'temp' ||" + COLUMN_BREWS_START_WORT_TEMP + ") AS " + TEXT_VIEW2_STR +
                    " FROM " + TABLE_VERIETIES + _COM + TABLE_CANS +" WHERE " + ;
        //    Тренировочный запрос */

            String sql = SQL_BREW_LIST;

            sql += " ORDER BY " + TEXT_VIEW1_STR + " DESC " ;   // Сортировку в отдельную строку для возможности управления её выбором

            String can1, can2, can3;
            //can1 = getCanWeight(brewDb,1);
            //can2 = getCanWeight(brewDb, 2);
            //can3 = getCanWeight(brewDb, 3);
            try {
                cursorBL = brewDb.rawQuery(sql, null);
                int i = 1;
                i++;
            } catch(SQLException e){
                Toast toast = Toast.makeText(this, "Problem with Database", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        int i= cursorBL.getCount();

        CursorAdapter adapterBL = new BrewListFragment.BrewListSimpeCursorAdapter(this,
            R.layout.simple_list_item_3, cursorBL,
            new String[]{TEXT_VIEW1_STR, TEXT_VIEW2_STR, TEXT_VIEW3_STR, TEXT_VIEW4_STR},
            new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4}, 0);


/*
        //    Простейший вариант для экспериментов
        CursorAdapter adapterBL = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursorBL,
                new String[]{TEXT_VIEW2_STR},
                new int[]{android.R.id.text1}, 0);*/


        fragment.setListAdapter(adapterBL);

    }
    /* Реализация метода связи фрагмента MaltExt... с родительской активностью
    (интерфейс MaltExtDescriptionFragment.FragMaltParams ) */
     @Override
     public void getFML(String tag, int id, int size, int position){
        setCurrentPositionInList(position);
        setNumVerietiesInList(size);
        setChoosedId(id);
        setCurrentFragTag(tag);

        Fragment fragment = new MaltExtDescriptionFragment();
        prepareMaltFragment(fragment, getChoosedId(), brewDb);
        startFragment(fragment, getCurrentFragTag());
     }


    // реализуем слушателя к выдвижному списку
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /* видимо, так :
            parent - ListView; view - элемент списка (тут строка) , далее её позиция и id
             */
            selectItem(position, id);
        }
    }

    // Обработка выбранного пункта выдвижного списка
    private void selectItem(int position, long id){
        Fragment fragment;
        String fragTag;
        // выбор фрагмента, передача ему параметров и запуск
        switch ((int)id) {
            case 0 :
                fragment = new TopFragment();
                setCurrentFragTag(TOPF_TAG);
                break;
            default : {
                fragment = new MaltExtDescriptionFragment();
                setCurrentPositionInList(0);
                setChoosedId((int)id);
                setCurrentFragTag(MEDF_TAG);

                prepareMaltFragment(fragment, getChoosedId(), brewDb);

            }
        }

        // выводим фрагмент с исп транзакции фрагмента
        startFragment(fragment, getCurrentFragTag());

        drawerLayout.closeDrawer(drawerList); // закрывает выдвижную панель, связанную с drawerLayout

    }
    //  Метод выполняет стандартные шаги подготовки фраг. описания Malt
    void prepareMaltFragment(Fragment fragment, int id, SQLiteDatabase db) {
        ArrayList<String> stringId;
        stringId = DbContract.DbVerieties.getVerietyIdByNameId(db, id);
        setNumVerietiesInList(stringId.size());
        String verietyId = stringId.get(getCurrentPositionInList());

        // подготовка информации для передачи новому создаваемому фрагменту Malt
        Bundle argBundle = prepareMaltInfo(db, verietyId );
        putPropetiesToFMalt(argBundle);
        fragment.setArguments(argBundle);
    }
    //  Метод выполняет стандартные шаги по активации фрагмента до ft.commit() включительно
    void startFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, tag);
        ft.addToBackStack(null);                // TODO может не копить фрагменты ?
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        ft.commit();

    }
    /* Метод получает из Db информацию для отображения во фрагменте MaltExtDesc...
    Обрабатывает её и готовит пакет к передаче . Можно и ArrayList, но читаемость ухудшается */
    Bundle prepareMaltInfo(SQLiteDatabase db, String VerietyId) {
        Bundle resultBundle = new Bundle();
        ContentValues cortegeCV = getVerietyCortegeById(db, VerietyId);
        resultBundle.putString(ARG_NAME, getName(db, cortegeCV.getAsInteger(COLUMN_VERIETIES_NAME_ID)));
        resultBundle.putString(ARG_BRAND, getBrand(db, cortegeCV.getAsInteger(COLUMN_VERIETIES_BRAND_ID)));
        resultBundle.putString(ARG_SRCIMG, cortegeCV.getAsString(COLUMN_VERIETIES_SRCIMG));
        resultBundle.putString(ARG_DESCRIPT, cortegeCV.getAsString(COLUMN_VERIETIES_DESCRIPTION));
        resultBundle.putInt(ARG_BITTER, cortegeCV.getAsInteger(COLUMN_VERIETIES_BITTER));
        resultBundle.putInt(ARG_COLOR, cortegeCV.getAsInteger(COLUMN_VERIETIES_COLOR));

        String canID = Integer.toString(cortegeCV.getAsInteger(COLUMN_VERIETIES_CAN_ID));
        cortegeCV.clear();  // TODO надо ли ?

        cortegeCV = getCanCortegeById(db, canID);
        resultBundle.putDouble(ARG_VOLUME, cortegeCV.getAsDouble(COLUMN_CANS_VOLUME));
        resultBundle.putDouble(ARG_WEIGHT, cortegeCV.getAsDouble(COLUMN_CANS_WEIGHT));
        return resultBundle;
    }

    // Прикрепляет к заданному Bundle параметры экземпляра
    void putPropetiesToFMalt(Bundle argBundle) {
        argBundle.putInt(ARG_ID, getChoosedId());
        argBundle.putInt(ARG_SIZE, getNumVerietiesInList());
        argBundle.putInt(ARG_POSITION,getCurrentPositionInList());
    }



//**************************************************************
    //для отображения меню на панели действий реализуем метод onCreateOptionsMenu()
    // элементы действий берем из файла ресурсов xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_of_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }



    // выполняется когда выбирается элемент на панели действий, получает объект MenuItem
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_brew_list:
                // Формируем и отоббражаем фрагмент оо списком варок
                BrewListFragment brewListFragment= new BrewListFragment();
                startFragment(brewListFragment, BLF_TAG);

                return true;
            case R.id.action_edit_brew:
                return true;
            case R.id.action_create_brew:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_saving:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDestroy (){
        super.onDestroy();
        cursor.close();
        if(cursorBL!=null) cursorBL.close();
        String forDelDb = brewDb.getPath();
        brewDb.close();
        this.deleteDatabase(forDelDb);  // TODO Нужно только для отладки на эмуляторе
        Log.d ("OnDestroy", "Destroy DONE");    // К отладке
        // sleep(5000);   TODO Добавить прощание ! Не тут
    }
    // геттеры и сеттеры
    int getChoosedId() {
        return ChoosedId;
    }
    void setChoosedId(int id){
        ChoosedId = id;
    }
    int getNumVerietiesInList() {
        return numVerietiesInList;
    }
    void setNumVerietiesInList(int size) {
        numVerietiesInList = size;
    }
    int getCurrentPositionInList() {
        return currentPositionInList;
    }
    void setCurrentPositionInList(int pos) {
        currentPositionInList = pos;
    }
    String getCurrentFragTag() {
        return currentFragTag;
    }
    void setCurrentFragTag(String tag) {
        currentFragTag = tag;
    }
}
