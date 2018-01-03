package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static jorje196.com.github.brewmaster.DbContract.DbBrands.COLUMN_BRANDS_BRAND;
import static jorje196.com.github.brewmaster.DbContract.DbBrands.COLUMN_BRANDS_ID;
import static jorje196.com.github.brewmaster.DbContract.DbBrands.TABLE_BRANDS;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_ALC_PERCENT;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_BITTER;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_ID;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_PROCESS_STATE;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_START_DATA;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_VERIETY_ID;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.COLUMN_BREWS_VOLUME;
import static jorje196.com.github.brewmaster.DbContract.DbBrews.TABLE_BREWS;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_ID;
import static jorje196.com.github.brewmaster.DbContract.DbCans.COLUMN_CANS_WEIGHT;
import static jorje196.com.github.brewmaster.DbContract.DbCans.TABLE_CANS;
import static jorje196.com.github.brewmaster.DbContract.DbNames.COLUMN_NAMES_ID;
import static jorje196.com.github.brewmaster.DbContract.DbNames.COLUMN_NAMES_NAME;
import static jorje196.com.github.brewmaster.DbContract.DbNames.TABLE_NAMES;
import static jorje196.com.github.brewmaster.DbContract.DbStates.COLUMN_STATES_ID;
import static jorje196.com.github.brewmaster.DbContract.DbStates.COLUMN_STATES_TEXT;
import static jorje196.com.github.brewmaster.DbContract.DbStates.TABLE_STATES;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_BITTER;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_BRAND_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_CAN_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.COLUMN_VERIETIES_NAME_ID;
import static jorje196.com.github.brewmaster.DbContract.DbVerieties.TABLE_VERIETIES;
import static jorje196.com.github.brewmaster.DbContract._COM;
import static jorje196.com.github.brewmaster.DbContract._PNT;

/**
 * Created by User on 20.12.2017.
 * Отображает список варок из Db, обеспечивает выбор элемента списка
 * для детального просмотра .
 */

public  class BrewListFragment extends ListFragment {
    static final String BLF_TAG = "brewList";    // тег фрагмента
        // Имена полей в собственном макете элемента списка
    static final String TEXT_VIEW1_STR = "brewListView1";
    static final String TEXT_VIEW2_STR = "brewListView2";
    static final String TEXT_VIEW3_STR = "brewListView3";
    static final String TEXT_VIEW4_STR = "brewListView4";
        // Сокращения имен таблиц для текста запроса
    static final String TBD = TABLE_BRANDS + ".";
    static final String TBW = TABLE_BREWS + ".";
    static final String TCN = TABLE_CANS + ".";
    static final String TVR = TABLE_VERIETIES + ".";
    static final String TNM = TABLE_NAMES + ".";
    static final String TST = TABLE_STATES + ".";

        // Строка SQL-запроса формирования курсора для работы с фрагментом списка

    static final String SQL_BREW_LIST = "SELECT " + TBW + COLUMN_BREWS_ID  + _COM +
        TBW + COLUMN_BREWS_START_DATA  + " AS " + TEXT_VIEW1_STR + _COM +
        " ( " + TNM + COLUMN_NAMES_NAME + "||'   '||" +
        TBD + COLUMN_BRANDS_BRAND + " ) AS " + TEXT_VIEW2_STR + _COM +
        " (" + " 'Output ' ||" + TBW + COLUMN_BREWS_VOLUME + "||  'л   Alc ' ||" +
        TBW + COLUMN_BREWS_ALC_PERCENT + "||  ' %об.    Bitt ' ||" +
        "CAST(ROUND(" + TVR + COLUMN_VERIETIES_BITTER + "*" + TCN + COLUMN_CANS_WEIGHT +
        "/"  + TBW + COLUMN_BREWS_VOLUME + ", 0) AS INTEGER)" + ") AS " + TEXT_VIEW3_STR + _COM +
        TST + COLUMN_STATES_TEXT  + " AS " + TEXT_VIEW4_STR +
            " FROM " +
        TABLE_BREWS +_COM + TABLE_VERIETIES + _COM + TABLE_BRANDS + _COM +
        TABLE_NAMES + _COM + TABLE_STATES + _COM + TABLE_CANS +
            " WHERE " +
        TBW + COLUMN_BREWS_VERIETY_ID + "=" + TVR + COLUMN_VERIETIES_ID + " AND " +
        TVR + COLUMN_VERIETIES_BRAND_ID + "=" + TBD + COLUMN_BRANDS_ID + " AND " +
        TVR + COLUMN_VERIETIES_NAME_ID + "=" + TNM + COLUMN_NAMES_ID + " AND " +
        TVR + COLUMN_VERIETIES_CAN_ID + "=" + TCN + COLUMN_CANS_ID + " AND " +
        TST + COLUMN_STATES_ID + "=" + TBW + COLUMN_BREWS_PROCESS_STATE;

    /*  nested inner class
        Класс адаптера курсора, расширяющий SimpleCursorAdapter, создан для получения возможности переписывать методы адаптера курсора,
    что может быть необходимо для управления отображением элемента списка . Пока пока используется только для выделения текста цветом.
    Курсор один для всех экземпляров класса фрагмента ! И это правильно.
     */
    static class BrewListSimpeCursorAdapter extends SimpleCursorAdapter {
        Context ctx;
        BrewListSimpeCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags ){
            super(context, layout, cursor, from, to, flags);
            ctx = context;
        }
        int currentTextColor = 0;
        @Override
        public void setViewText(TextView v, String text) {
            super.setViewText(v, text);
            if (v.getId() == R.id.text4){
                if (currentTextColor == 0) currentTextColor = v.getCurrentTextColor();
                if (text.equals(ctx.getResources().getString(R.string.in_progress))){
                    v.setTextColor(ctx.getResources().getColor(R.color.colorAccentNegative));
                } else {
                // Без этого цвет сохраняется при уходе элемента в тень и возвращении , цветовое выделение плодится бесконтрольно
                    v.setTextColor(currentTextColor);
                }
            }
        }
    }  // end BrewListSimpeCursorAdapter

    // Интерфейс для связи (передачи информации в родительскую активность
    interface FragBrewListLink {
        void getFBLL(ListFragment fragment, String tag);
    }
    FragBrewListLink onFragBrewListLink;

    @Override
    public void onAttach(Activity context) {    // Activity, not Context, else NullPointerException
        super.onAttach(context);
        try {
            onFragBrewListLink = (FragBrewListLink)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onFragBrewListLink.getFBLL(this, BLF_TAG);
       // TODO Для исключения утечки памяти надо открывать и закрывать курсоры для адаптера в callback'ах по onActivityCreate и ? onDesrtoy ?

    }
    /* Чтобы отображался не layout по умолчанию, а определенный в fragment_brew_list.xml
    Если По умолчанию устраивает, то блок лишний.
    P.S. android:list & android:empty по определению, заменить нельзя
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInscanceState){
        return inflater.inflate(R.layout.fragment_brew_list, null);
    }      */


}
