package jorje196.com.github.brewmaster;

import android.app.ListFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by User on 20.12.2017.
 * Отображает список варкок из Db, обеспечивает выбор элемента списка
 * для детального просмотра .
 */

public  class BrewListFragment extends ListFragment {
    static final String BLF_TAG = "brewList";    // тег фрагмента

 String data[] = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
    "aaa", "bbbbbbbbbbbbbbbbbbbbbb", "ccccccccccccccccccccccccccccccccccccccccc", "dddddddd", "e"};

// ? как передать адаптер , определенный в активности ?

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);

    }
    /* Чтобы отображался не layout по умолчанию, а определенный в fragment_brew_list.xml
    Если По умолчанию устраивает, то блок лишний.
    P.S. android:list & android:empty по определению, заменить нельзя  */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInscanceState){
        return inflater.inflate(R.layout.fragment_brew_list, null);
    }

}
