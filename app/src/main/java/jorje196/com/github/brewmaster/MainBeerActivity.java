package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainBeerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_beer);
        // получаем ресурс
        String[] top_aphorisms = getResources().getStringArray(R.array.aphorisms);
        // выбираем присказку , картинка пока безальтернативна
        int num_saying;
        num_saying = (int) (Math.random() * top_aphorisms.length);
        TextView aphorismView = (TextView) findViewById(R.id.top_aphorism);
        aphorismView.setText(top_aphorisms[num_saying]);
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
