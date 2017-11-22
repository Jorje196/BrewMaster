package jorje196.com.github.brewmaster;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.widget.DrawerLayout;


import java.util.ArrayList;
import java.util.ListIterator;

public class MainBeerActivity extends Activity {
    private String[] planetsArray;    // список планет
    private ListView drawerList;    // списковое представление для drawer"а
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_beer);

        // Инициализация выдвижной панель

        // массив строк из ресурса
        planetsArray = getResources().getStringArray(R.array.planets_array);
        // ссылка на списочное представление из реса
        drawerList = (ListView)findViewById(R.id.drawer);
        // получить ссылку на drawerLayout
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        // связываем списочное представление с массивом строк через адаптор
        drawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, planetsArray));
        // создаем экземпляр для слушателя спискового представления
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        // Выбираем стартовый фрагмент для фрейма
        if (savedInstanceState==null) {
            selectItem(0, 0);
        }


        // выбираем присказку , картинка пока безальтернативна
        int num_saying;
        String[] top_aphorisms = getResources().getStringArray(R.array.aphorisms);
        num_saying = (int) (Math.random() * top_aphorisms.length);
    //    TextView aphorismView = (TextView) findViewById(R.id.top_aphorism);
    //    aphorismView.setText(top_aphorisms[num_saying]);

        // подключение к БД
  /*  2--    SQLiteDatabase brewDb;
        brewDb = new BrewDbHelper(this).getWritableDatabase();
        // проверка по внешним ключам д.б. включена
        brewDb.setForeignKeyConstraintsEnabled (true);

        // тестовая часть: выбираем все Coopers с 30 <= bitter <= 40
        ArrayList<String> bitterList ;
        bitterList = DbContract.DbVerieties.getVerietiesTextByBitterSpan(brewDb, "Coopers",28,43);

        brewDb.close(); --2*/

        /* блок для отладки onCreate & onUpgrade
        String strPath = brewDb.getPath();
        int x;
        if(deleteDatabase(strPath)) {
            x = 1;
        } else {
            x = -1;
        }   */
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
        Fragment fragment = null;  // ? new Fragment()
        switch (position) {
            case 0 :
                fragment = new TopFragment();
                break;
            default : {}
        }
        // выводим фрагмент с исп транзакции фрагмента
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        ft.commit();


        // тут что-то делаем, но надо закрыть выдвижную панель :

        drawerLayout.closeDrawer(drawerList); // закрывает выдвижную панель, связанную с drawerLaayout

    }

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
}
