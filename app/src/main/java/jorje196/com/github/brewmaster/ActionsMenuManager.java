package jorje196.com.github.brewmaster;

import android.app.ActionBar;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import static android.view.MenuItem.SHOW_AS_ACTION_NEVER;
import static jorje196.com.github.brewmaster.MainBeerActivity.BREW_DESCRIPT_FRAG_NUM;
import static jorje196.com.github.brewmaster.MainBeerActivity.BREW_LIST_FRAG_NUM;
import static jorje196.com.github.brewmaster.MainBeerActivity.DRAWER_CLOSE_NUM;
import static jorje196.com.github.brewmaster.MainBeerActivity.DRAWER_OPEN_NUM;
import static jorje196.com.github.brewmaster.MainBeerActivity.FRAGMENT_IS_ABSENT;
import static jorje196.com.github.brewmaster.MainBeerActivity.MALT_EXT_DESCRIPT_NUM;
import static jorje196.com.github.brewmaster.MainBeerActivity.TOP_FRAG_NUM;
/*
* Класс обеспечивает управление видимостью, оступностью, отображением и т.д.
* Меню действий для задаваемого номера фрагмента или условия. В данном конкретном
* случае условие бывает только одно - положение выдвижной панели. Для общего случая
* можно строить таблицы состояния пунктов меню для каждого фрагмента в зависимости
* от условий, но в конкретном случае при выдвинутой панели не должно быть доступа
* к пунктам меню, поэтому тупо делаем их невидимыми.
* P.S. Построение меню действий исполнено через .xml, отсюда и пути решения . Можно
* было заходить со стороны динамического создания меню с самого начала, но это
* была бы уже другая песня.
 */
abstract class ActionsMenuManager {
    /*Полный список id пунктов меню справочно
    private static final int[] actionsMenuItemsList = {R.id.action_search,
        R.id.action_brew_list, R.id.action_create_brew, R.id.action_share,
        R.id.action_edit, R.id.action_saving,R.id.action_settings}; */
    /* Список фрагментов и условий, для которых задана информация справочно
    private final int[] fragmentList =
        {TOP_FRAG_NUM, MALT_EXT_DESCRIPT_NUM, BREW_LIST_FRAG_NUM,
        BREW_DESCRIPT_FRAG_NUM, DRAWER_OPEN_NUM, DRAWER_CLOSE_NUM, FRAGMENT_IS_ABSENT}; */

    // Таблица видимости (2-мерный массив), в строке первый элемент соответсвует
    // номеру фрагмента, следом список id видимых пунктов меню . Если для фрагмента
    // списка нет. то не будет и изменений, а методы определения индекса и исполнения
    // действия выдадут "-1" и "false" соответственно
    private static final int[][] fragmentMenuItemsVisibility = {
        //{TOP_FRAG_NUM, R.id.action_brew_list, R.id.action_create_brew, R.id.action_settings},
        //{MALT_EXT_DESCRIPT_NUM, R.id.action_brew_list, R.id.action_create_brew,
        //    R.id.action_share, R.id.action_settings},
        //{BREW_LIST_FRAG_NUM, R.id.action_search, R.id.action_settings},
        //{BREW_DESCRIPT_FRAG_NUM, R.id.action_share, R.id.action_edit, R.id.action_saving,
        //    R.id.action_settings},
        {DRAWER_OPEN_NUM},
        {DRAWER_CLOSE_NUM, R.id.action_search, R.id.action_brew_list, R.id.action_create_brew,
                R.id.action_share, R.id.action_edit, R.id.action_saving,R.id.action_settings}
    };
    // Таблица доступности ... , заполняется аналогично видимости
    private static final int[][] fragmentMenuItemsEnabled = {
            {TOP_FRAG_NUM, R.id.action_brew_list, R.id.action_create_brew, R.id.action_settings},
            {MALT_EXT_DESCRIPT_NUM, R.id.action_brew_list, R.id.action_create_brew,
                    R.id.action_share, R.id.action_settings},
            {BREW_LIST_FRAG_NUM, R.id.action_search, R.id.action_create_brew, R.id.action_settings},
            {BREW_DESCRIPT_FRAG_NUM, R.id.action_share, R.id.action_edit, R.id.action_saving,
                    R.id.action_settings},
    };
    // Таблица представления запрещает отображать иконки меню на панели действий
    // перечисленным в списке пунктам через определение параметр showAsAction: never.
    // Этого достаточно  чтобы показать или спрятать картинки в ActionBar
    private static final int[][] fragmentMenuItemsShowAsN = {
            {FRAGMENT_IS_ABSENT,
                R.id.action_search, R.id.action_share, R.id.action_edit,
                    R.id.action_saving},
            {TOP_FRAG_NUM,
                R.id.action_search, R.id.action_share, R.id.action_edit,
                    R.id.action_saving},
            {MALT_EXT_DESCRIPT_NUM,
                R.id.action_search, R.id.action_share, R.id.action_edit,
                    R.id.action_saving},
            {BREW_LIST_FRAG_NUM,
                R.id.action_share, R.id.action_edit, R.id.action_brew_list,
                    R.id.action_saving},
            {BREW_DESCRIPT_FRAG_NUM,
                R.id.action_search, R.id.action_share, R.id.action_brew_list,
                    R.id.action_create_brew},
    };
    // Таблица заголовков
    private static int[][] titleTable = {
        {FRAGMENT_IS_ABSENT, R.string.activity_main_name},
        {TOP_FRAG_NUM, R.string.activity_main_name},
        {MALT_EXT_DESCRIPT_NUM, R.string.title_malt_description},
        {BREW_LIST_FRAG_NUM, R.string.title_brew_list},
        {BREW_DESCRIPT_FRAG_NUM, R.string.title_brew_description},
        {DRAWER_OPEN_NUM, R.string.title_drawer_on}
    };
    // Получение исходных данных для заданного фрагмента
    private static int getFragmentDataIndex(int fNum, final int[][] dataTable ){
        int i, result = -1;
        for(i = 0; i < dataTable.length; i++){
            if (fNum == dataTable[i][0]){
                result = i;
                break;
            }
        }
    return result;
    }

    public static boolean menuItemEnabled(Menu menu, int fNum){
        int itemId, fIndex;
        boolean choiceDone;
        if ((fIndex = getFragmentDataIndex(fNum, fragmentMenuItemsEnabled))< 0) return false;  // Для номера фрагмента нет данных

        for(int i = 0; i < menu.size(); i++) {
            itemId = menu.getItem(i).getItemId();
            choiceDone = false;
            for (int j = 1; j < fragmentMenuItemsEnabled[fIndex].length; j++){
                if (itemId == fragmentMenuItemsEnabled[fIndex][j]){
                    choiceDone = true;
                    break;
                }
            }
            menu.findItem(itemId).setEnabled(choiceDone);
        }
        return true;
    }
    public static boolean menuItemVisibility(Menu menu, int fNum){
        int itemId, fIndex;
        boolean choiceDone;
        if ((fIndex = getFragmentDataIndex(fNum, fragmentMenuItemsVisibility))< 0) return false;  // Для номера фрагмента нет данных

        for(int i = 0; i < menu.size(); i++) {
            itemId = menu.getItem(i).getItemId();
            choiceDone = false;
            for (int j = 1; j < fragmentMenuItemsVisibility[fIndex].length; j++){
                if (itemId == fragmentMenuItemsVisibility[fIndex][j]){
                    choiceDone = true;
                    break;
                }
            }
            menu.findItem(itemId).setVisible(choiceDone);
        }
        return true;
    }
    public static boolean menuItemShowAs(Menu menu, int fNum){
        int itemId, fIndex;
        if ((fIndex = getFragmentDataIndex(fNum, fragmentMenuItemsShowAsN))< 0) return false;  // Для номера фрагмента нет данных

        for(int i = 0; i < menu.size(); i++) {
            itemId = menu.getItem(i).getItemId();
            for (int j = 1; j < fragmentMenuItemsShowAsN[fIndex].length; j++){
                if (itemId == fragmentMenuItemsShowAsN[fIndex][j]){
                    menu.findItem(itemId).setShowAsAction (SHOW_AS_ACTION_NEVER);
                    break;
                }
            }
        }
        return true;
    }
    public static boolean titleDefine(ActionBar actionBar, int fNum){
        int itemId, fIndex;
        if ((fIndex = getFragmentDataIndex(fNum, titleTable))< 0) return false;  // Для номера фрагмента нет данных
        itemId = titleTable[fIndex][1];
        actionBar.setTitle(itemId);
        return true;
    }

}
