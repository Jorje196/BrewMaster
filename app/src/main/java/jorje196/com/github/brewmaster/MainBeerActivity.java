package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;
import jorje196.com.github.brewmaster.MaltExtDescriptionFragment.FragMaltLink;
import jorje196.com.github.brewmaster.BrewListFragment.BrewListFLink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static jorje196.com.github.brewmaster.BrewDescriptionFragment.ARG_CORTEGE_ID;
import static jorje196.com.github.brewmaster.BrewDescriptionFragment.ARG_SOURCE;
import static jorje196.com.github.brewmaster.BrewListFragment.BLF_TAG;
import static jorje196.com.github.brewmaster.BrewListFragment.SQL_BREW_LIST;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW1_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW2_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW3_STR;
import static jorje196.com.github.brewmaster.BrewListFragment.TEXT_VIEW4_STR;
import static jorje196.com.github.brewmaster.DbContract.DbBrands.getBrand;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_VOLUME;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_WEIGHT;
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
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_NAME_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_SRCIMG;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.getVerietyCortegeById;
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


public class MainBeerActivity extends Activity implements FragMaltLink, BrewListFLink, BrewDescriptionFragment.BrewDescriptionFLink {
    static final String TAG_FOR_ALL = "visible_fragment";
    static final int TOP_FRAG_NUM = 1;              // TopFragment
    static final int MALT_EXT_DESCRIPT_NUM = 2;     // MaltExtDescriptionFragment
    static final int BREW_LIST_FRAG_NUM = 3;        // BrewListFragment
    static final int BREW_DESCRIPT_FRAG_NUM = 4;    // BrewDescriptionFragment


    Context cntMBA;
    /*@org.jetbrains.annotations.Contract(pure = true)
    public static Context getCntMBA() {
        return cntMBA;
    }*/
    private ListView drawerList;    // списковое представление для drawer"а
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    protected SQLiteDatabase brewDb;
    private Cursor cursor;
    private Cursor cursorBL;
    private ShareActionProvider shareActionProvider;


    private int ChoosedId = 0;
    private int numVerietiesInList = 0;
    private int currentPositionInList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_beer);
        if(cntMBA==null)cntMBA = getApplicationContext();

        //NumberPicker numPicker = (NumberPicker)findViewById(R.id.numberPicker);
        //int orientation = getResources().getConfiguration().orientation;
        //orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
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

            // Для управления drawer и составом меню в ActionBar создаем :
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.string.open_drawer, R.string.close_drawer) {
                @Override   // Вызыв. при переходе drawer в полностью закрытое сост.
                public void onDrawerClosed (View view) {
                    super.onDrawerClosed(view);
                    // todo тут свой код
                }
                @Override   // Вызыв. при переходе drawer в полностью открытое сост.
                public void onDrawerOpened (View view) {
                    super.onDrawerOpened(view);
                    // todo свой код
                }
            };
            // И связываем объект с DrawerLayout
            drawerLayout.addDrawerListener(drawerToggle); // отключить = remove

            // Включение Кн Вверх для использования её объектом drawerToggle
            this.getActionBar().setHomeButtonEnabled(true);
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
            //this.getActionBar().get

            // Выбираем стартовый фрагмент для фрейма
            if (savedInstanceState == null) {
                selectDrawerItem(0, 0);

            }
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Problem with Database", Toast.LENGTH_SHORT);
          toast.show();
      }

     }

    // Для синхронизации значка выдвижной панели с её состоянием (после onRestoreInstanceState)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null)
            drawerToggle.syncState();
    }

    /* При изменении конфигурации передаем ActionBarDrawerToggle информацию об
    изменениях (велено руководством разработчика, и логично).
    P.S. Добавить проверку в обработку кликов на панели действий (верх меню)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null)
            drawerToggle.onConfigurationChanged(newConfig);

    }

     // Метод определения текущего активного фрагмента, связанного с активностью
    private int searchCurrentFragment() {
        int fragmentNum = 0;
        FragmentManager fragMng = getFragmentManager();
        // Фрагмент, в настоящее время связанный с активностью (поиск начинается с активного)
        Fragment fragment = fragMng.findFragmentByTag(TAG_FOR_ALL);
        // ищем , к какому типу относится фраг и возвращаем результат
        if (fragment instanceof TopFragment){
            fragmentNum = TOP_FRAG_NUM;
        } else if (fragment instanceof MaltExtDescriptionFragment){
            fragmentNum = MALT_EXT_DESCRIPT_NUM;
        } else if (fragment instanceof BrewListFragment){
            fragmentNum = BREW_LIST_FRAG_NUM;
        } else if (fragment instanceof BrewDescriptionFragment)
            fragmentNum = BREW_DESCRIPT_FRAG_NUM;
        return fragmentNum;
    }

    // TODO В реализациях методов связи интерфейсов фрагментов
    private int idMaltExtName;
    private void setIdMaltExtName(int idMaltExtName) {
        this.idMaltExtName = idMaltExtName;
    }
    private String maltExtName;
    private void setMaltExtName(String maltExtName) {
        this.maltExtName = maltExtName;
    }
    private ArrayList[] maltAvailableBrands;
    public void setMaltAvailableBrands(ArrayList[] maltAvailableBrands) {
        this.maltAvailableBrands = maltAvailableBrands;
    }

    // Реализация метода изменения состояния выдвижной панели (BDF)
    @Override
    public void getDrawerToggle(){
        int i=0, j; j = 1;
        setIdMaltExtName(-1);
        MenuItem itemHome = new MenuItemStub(android.R.id.home);
        onOptionsItemSelected(itemHome);

    }
    @Override
    public String getMaltExtName(){
        return maltExtName;
    }
    @Override
    public ArrayList[] getMaltExtBrands(){
        return maltAvailableBrands;
    }
    @Override
    public int getIdMaltExtName(){
        int res = idMaltExtName;
        idMaltExtName = -1; // надо ли ?
        return res;
    }
    @Override
    public void getExitFragment(){
        infoToast("Не удалось получить данные из базы");
        onBackPressed();
    }

    /* Реализация (абстрактного) метода связи getFBLL (BrewListFra...) с родительской активностью */
    @Override
    public void getFBLL(ListFragment fragment, String tag) {
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
                int i = 1;  // отладочное
                i++;    // отладочное
            } catch(SQLException e){
                Toast toast = Toast.makeText(this, "Problem with rawQuery ", Toast.LENGTH_SHORT);
                toast.show();
            } catch (ArithmeticException e) {
                Toast toast = Toast.makeText(this, "Problem with Arithmetic in rawQuery ", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        int i= cursorBL.getCount();     // отладочное

        CursorAdapter adapterBL = new BrewListFragment.BrewListSimpeCursorAdapter(this,
            R.layout.simple_list_item_3, cursorBL,
            new String[]{TEXT_VIEW1_STR, TEXT_VIEW2_STR, TEXT_VIEW3_STR, TEXT_VIEW4_STR},
            new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4}, 0);

     /*    //    Простейший вариант для экспериментов
        CursorAdapter adapterBL = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursorBL,
                new String[]{TEXT_VIEW2_STR},
                new int[]{android.R.id.text1}, 0);*/

        fragment.setListAdapter(adapterBL);

    }

    /* Реализация метода связи фрагмента MaltExt... с родительской активностью
    (интерфейс MaltExtDescriptionFragment.FragMaltParams ) */
     @Override
     public void getFML(String tag, int id, int size, int position){        //todo Нужны ли String tag  линках ?
        setCurrentPositionInList(position);
        setNumVerietiesInList(size);
        setChoosedId(id);

        Fragment fragment = new MaltExtDescriptionFragment();
        prepareMaltFragment(fragment, getChoosedId(), brewDb);
        startFragment(fragment, TAG_FOR_ALL);
     }

    // реализуем слушателя к выдвижному списку
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /* видимо, так :
            parent - ListView; view - элемент списка (тут строка) , далее её позиция и id
             */
            selectDrawerItem(position, id);
        }
    }


    // Обработка выбранного пункта выдвижного списка
    private void selectDrawerItem(int position, long id){
        if (searchCurrentFragment() != BREW_DESCRIPT_FRAG_NUM) {
            Fragment fragment;
            // выбор фрагмента, передача ему параметров и запуск
            switch ((int) id) {
                case 0:
                    fragment = new TopFragment();
                    break;
                default: {
                    fragment = new MaltExtDescriptionFragment();
                    setCurrentPositionInList(0);
                    setChoosedId((int) id);
                    prepareMaltFragment(fragment, getChoosedId(), brewDb);
                }
            }
            // выводим фрагмент с исп транзакции фрагмента
            startFragment(fragment, TAG_FOR_ALL);
        } else {
            int _id; _id = (int)id;
            setIdMaltExtName(_id);
            setMaltExtName(getMaltExtNameById(_id));
            setMaltAvailableBrands(getAvailableMaltBrandsByNameId(_id));
        }
        drawerLayout.closeDrawer(drawerList); // пункт выбран => закрываем выдвижную панель

    }
    //  Метод выполняет стандартные шаги подготовки фраг. описания Malt
    void  prepareMaltFragment(Fragment fragment, int id, SQLiteDatabase db) {
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
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        ft.commit();

    }

    // Метод получает из Db строковое представление названия экстракта
    private String getMaltExtNameById(int id){
        String strMaltExtName = "";     // todo Подчистить код
        strMaltExtName = DbContract.DbNames.getName(brewDb, id);
        return strMaltExtName;
    }
    private ArrayList[] getAvailableMaltBrandsByNameId(int id){
        ArrayList[] listBrands = new ArrayList[2];

        ArrayList<String> listNameBrands; // = new ArrayList<>(); это избыточно
        ArrayList<String> listIdBrands = new ArrayList<>();
        String _str;
        listNameBrands = DbContract.DbVerieties.getVerietyIdByNameId(brewDb, id);
        for (int i = 0; i < listNameBrands.size(); i++) {
            _str = (DbContract.DbVerieties.getVerietyCortegeById(brewDb, listNameBrands.get(i)).getAsString(DbContract.DbVerieties.COLUMN_VERIETIES_BRAND_ID));
            listIdBrands.add(i, _str);
            listNameBrands.set(i, DbContract.DbBrands.getBrand(brewDb, _str));
        }
        if (listIdBrands.size() == listNameBrands.size()) {
            listBrands[0] = listNameBrands;
            listBrands[1] = listIdBrands;
        }
        return listBrands;
    }

    // Для сообщений о некритических событиях в тостике
    private void infoToast(String message){
        this.infoToast(message, true);
    }
    void infoToast(String message, boolean lasting){
        Toast toast = Toast.makeText(cntMBA, message,
                (lasting)? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.show();
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

/**************************************************************
    Работа с фрагментом детализации варки */

    // Обработка клика на выборе экстракта
public void maltChoiseInBrewDescription(View view) {
    int i=0;
    i++;
}

//**************************************************************
    //для отображения меню на панели действий реализуем метод onCreateOptionsMenu()
    // элементы действий берем из файла ресурсов xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_of_actions, menu);

        /* Блок добавлен для устранения непонятности с применением support v7, и
          принудительно устанавливает видимость иконок на ActionBar. Возможно дело
          в неустоявшейся версии библиотеки поддержки , стоит проверять с обновлениями,
          нестыковка может пропасть . Пенка в восприятии пространств имен app / android */
        int i = menu.size();  // отладочное
        MenuItem n = menu.findItem(R.id.action_brew_list);
        n.setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        n = menu.findItem(R.id.action_create_brew);
        n.setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        i++;     // отладочное

        /* С ShareActionProvider та же проблема с пространствами имен.
         Пока в xml убрал app вернул android . Если само не пройдет, решать аналогично */

        return    super.onCreateOptionsMenu(menu);
    }
    // Метод создает интент и передает его провайдеру действия (для action_share)
    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND); // todo корректировать, пусть возвращает интент
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    // выполняется когда выбирается элемент на панели действий, получает объект MenuItem
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Чтобы объект ActionBarDrawerToggle реагировал на выбор (клики, щелчки ..)
// drawerToggle.
        if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) return true;
        //i++;
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_brew_list:
                // Формируем и отоббражаем фрагмент оо списком варок
                BrewListFragment brewListFragment= new BrewListFragment();
                startFragment(brewListFragment, BLF_TAG);

                return true;

            case R.id.action_create_brew:
                // Начинаем новую варку
                BrewDescriptionFragment brewDescriptionFragment = new BrewDescriptionFragment();

                Bundle bundleBDF = new Bundle();
                bundleBDF.putInt(ARG_SOURCE, 0);
                bundleBDF.putInt(ARG_CORTEGE_ID, 0);
                brewDescriptionFragment.setArguments(bundleBDF);
                startFragment(brewDescriptionFragment, TAG_FOR_ALL);

                return true;
            case R.id.action_share:
                // Получение ссылки на провайдера действия передачи информации
                shareActionProvider = (ShareActionProvider) item.getActionProvider();
                setIntent("This is test"); // отладочная

                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_saving:
                return true;
//            case android.R.id.home:

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
    // геттеры и сеттеры ? для кого , так ли надо ?
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

}
