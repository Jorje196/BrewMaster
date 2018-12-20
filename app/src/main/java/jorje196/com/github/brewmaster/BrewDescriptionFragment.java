package jorje196.com.github.brewmaster;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;  это для случая со spinner'ом
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
//import android.widget.Spinner;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.*;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import jorje196.com.github.brewmaster.AreometerAC3;
import static jorje196.com.github.brewmaster.BrewProcess.HOUR_IN_MLS;
import static jorje196.com.github.brewmaster.BrewProcess.NUL_TEMPERATURE;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_COMPAUND;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_LACK;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_NUMBER_OF_COMPONENTS;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_OK;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_OVERAGE;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_THICK_OVERAGE;
import static jorje196.com.github.brewmaster.BrewProcess.PRIMER_TYPO;
import static jorje196.com.github.brewmaster.DbContract.GRAD_C;
import static jorje196.com.github.brewmaster.DbContract.RHO_C;


/**
 * A simple {@link Fragment} subclass.
 * Работает с описанием выбранной варки (заполнение, коррекция, подтверждение удаления
 */

public class BrewDescriptionFragment extends Fragment {
    static final String BDF_TAG = "brewDescription";    // тег фрагмента
    static final String ARG_SOURCE = "call_source";     // источник вызова, определяет таблицу
    static final String ARG_CORTEGE_ID = "cortege_id";  // _id кортежа в таблице
    static final String EMPTY_STRING = "";
    static final String HYPHEN = "-";

    // Константы шагов выполнения
    // Можно формализовать для 2-мерной матрицы этапов/шагов и
    // цикла в цикле, м б будет красиво, но лень
    static final int DESTINATION_UNDEFINED = -1;
    static final int PREPARE_INGREDIENTS_CHOICE = 1;
    static final int PREPARE_INGREDIENTS_MALT_EXT_CHOICE = 111;
    static final int CHOOSING_INGREDIENTS_MALT_NAME = 112;
    static final int CHOOSED_INGREDIENTS_MALT_NAME = 113;
    static final int PREPARE_MALT_BRAND_CHOICE = 121;
    static final int CHOOSING_INGREDIENTS_MALT_BRAND = 122;
    static final int CHOOSED_INGREDIENTS_MALT_BRAND = 123;
    static final int CHOOSED_INGREDIENTS_MALT_EXT = 124;
    static final int PREPARE_PRIMER_CHOICE = 131;
    static final int CHOOSING_PRIMER_INGREDIENTS_START = 132;
    static final int CHOOSING_PRIMER_INGREDIENTS_EDIT = 133;
    static final int CHOOSED_INGREDIENTS_PRIMER = 134;
    static final int PREPARE_START_BREW = 2;
    static final int PREPARE_START_BREW_DATE = 211;
    static final int CHOOSING_START_BREW_DATE = 212;
    static final int CHOOSED_START_BREW_DATE = 213;
    static final int PREPARE_START_BREW_TIME = 221;
    static final int CHOOSING_START_BREW_TIME = 222;
    static final int CHOOSED_START_BREW_TIME = 223;
    static final int PREPARE_START_WORT_TEMPERATURE = 231;
    static final int CHOOSING_START_WORT_TEMPERATURE = 232;
    static final int CHOOSED_START_WORT_TEMPERATURE = 233;
    static final int PREPARE_START_AMBIENT_TEMPERATURE = 241;
    static final int CHOOSING_START_AMBIENT_TEMPERATURE = 242;
    static final int CHOOSED_START_AMBIENT_TEMPERATURE = 243;
    static final int PREPARE_START_WORT_DENSITY = 251;
    static final int CHOOSING_START_WORT_DENSITY = 252;
    static final int CHOOSED_START_WORT_DENSITY = 253;
    static final int SHOW_START_BREW_DATA = 261;
    static final int SHOW_FERMENTATION_PROGRESS = 262;
    static final int PREPARE_BOTTLING = 3;
    static final int PREPARE_BOTTLING_DATE = 311;
    static final int CHOOSING_BOTTLING_DATE = 312;
    static final int CHOOSED_BOTTLING_DATE = 313;
    static final int PREPAR_BOTTLING_TEMPERATURE = 321;
    static final int CHOOSING_BOTTLING_TEMPERATURE = 322;
    static final int CHOOSED_BOTTLING_TEMPERATURE = 323;
    static final int PREPARE_BOTTLING_WORT_DENSITY = 331;
    static final int CHOOSING_BOTTLING_WORT_DENSITY = 332;
    static final int CHOOSED_BOTTLING_WORT_DENSITY = 333;
    static final int PREPARE_BOTTLING_VOLUME = 341;
    static final int CHOOSING_BOTTLING_VOLUME = 342;
    static final int CHOOSED_BOTTLING_VOLUME = 343;
    static final int SHOW_BOTTLING_RESULT = 351;
    private static final int SHOW_BREW_RESULT = 4;
    private static final int SHOW_PROCESS_PROGRESS = 40;
    static final int SHOW_CARBONATION_PROGRESS = 41;
    static final int SHOW_CONDITIONING_PROGRESS = 42;
    static final int SHOW_MATURATION_PROGRESS = 43;


/*    static final int[]CURRENT_STEP = {CHOOSING_INGREDIENTS_MALT_NAME, CHOOSED_INGREDIENTS_MALT_NAME,
            CHOOSING_INGREDIENTS_MALT_BRAND, CHOOSED_INGREDIENTS_MALT_BRAND,
            CHOOSING_PRIMER_INGREDIENTS_START}; */

    Calendar calendarDateAndTime = Calendar.getInstance();
    Calendar currentDateAndTime = (Calendar) calendarDateAndTime.clone();


    public BrewDescriptionFragment() {
        // Required empty public constructor
        //this.wortGravity = 0;
        toggleCallRead = false; // не факт что надо
    }

    // Интерфейс для взаимодействия фрагмента с родительской активностью
    interface BrewDescriptionFLink{
        void getDrawerToggle();
        String getMaltExtName();
        ArrayList[] getMaltExtBrands();
        int getIdMaltExtName();
        void getExitFragment();
        void setDrawerDenied(boolean allowed);
        // !!! Из-за усечения функционала  делаем так
        //int getIdMaltExtBrand(String maltExtBrand);
        int getMaltExtBitter(String maltExtName, String maltExtBrand);
        //double getMaltExtCanWeight(int idMaltExtName, int idMaltExtBrand);
        //int getMaltExtVolumeRec(int idMaltExtName, int idMaltExtBrand);  // факультативно
    }
    BrewDescriptionFLink brewDescriptionFLink;
    // Переопределяем обработку пунктов меню ActionBar
    //public boolean onOptionsItemSelected(MenuItem item){
    //    if ()
    //    return true;
    //}

    /*
    * Часть идентификаторов рабочих объектов выводим на уровень класса для удобства
    * исследования путей решения задачи через написание для них разных методов с теми
    * же актерами */

    private Context contextBDF;

    private BrewProcess brewProcess; // идент экземпляра класса варки
    PrimerItem[] primerItems;       //  массив эл-тов вн.класса
    ArrayList[] availableBrandName;
    boolean toggleCallRead;

    String currentMaltName;
    int currentMaltNameId;
    String currentBrandName;
    double currentCanWeight;
    String currentPrimer;

    // Набор флагов на разные случаи жизни
    boolean modeEditBrewProcess = false;
    boolean modeEditIngredients = false;
    boolean modeEditFermentation = false;   // true - режим редактирования
    boolean modeEditBottling = false;

        // Элементы интерфейса с пользоватем
    private View layoutBDF;                 // это сам фрагмент
            // Блок выбора экстракта
    Button buttonMaltExtChoose;    // Кн запуска выбора названия экстракта
    TextView maltNameChoosed;        // Поле вывода инфы об экстракте
    Button buttonMaltBrandChoose;         // Кн продолжения формирования полного названия экстракта
    //private Spinner spinnerBrandName;       // Спиннер выбора бренда
    private Button buttonPrimerChoose;      // Кн перехода к выбору состава праймера

            // Блок набора праймера
    TextView primerChoosed;             // Информация о выбранном составе
    private Button buttonStartBrew;         // Кн перехода к варке

    private int callSource;
    public void setCallSource(int callSource) {
        this.callSource = callSource;
    }
    public int getCallSource() {
        return callSource;
    }

    private int cortegeId;
    public void setCortegeId(int cortegeId) {
        this.cortegeId = cortegeId;
    }
    public int getCortegeId() {
        return cortegeId;
    }

    // Для сообщений о некритических событиях в тостике
    private void infoToast(String message){
        this.infoToast(message, true);
    }
    void infoToast(String message, boolean lasting){
        Toast toast = Toast.makeText(contextBDF, message,
                (lasting)? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public void onAttach(Context context){ // с API 23 Context
        super.onAttach(context);
        if(VERSION.SDK_INT >= VERSION_CODES.M) {
            this.contextBDF = context;
            try {
                brewDescriptionFLink = (BrewDescriptionFLink) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() +
                        " must implement onSomeEventListener");
            }
        }
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(VERSION.SDK_INT < VERSION_CODES.M) {
            this.contextBDF = activity;
            try {
                brewDescriptionFLink = (BrewDescriptionFLink) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement onSomeEventListener");
            }
        }
    }
    @Override               // 2* Отмена портретного эксклюзива
    public void onPause(){
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        brewDescriptionFLink.setDrawerDenied(false);
        super.onPause();
    }

    @Override
    public void onResume(){
        //brewDescriptionFLink.setMenuItemsBDF();
        super.onResume();
    }
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1 Фрагмент работает тольков портрете (отмену см. 2*)
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Откл. возможность вызова/закрытия выдвижной панели рукой
        brewDescriptionFLink.setDrawerDenied(true);
        Bundle argsBundle = getArguments();
        setCallSource(argsBundle.getInt(ARG_SOURCE));
        setCortegeId(argsBundle.getInt(ARG_CORTEGE_ID));
            // Для отладки ( или при отсутствии альтернативных источников)
        setCallSource(0);
        setCortegeId(0);
        currentDateAndTime.setTimeInMillis(0);  // уст. текущего календаря в 0
        long t = currentDateAndTime.getTimeInMillis();
        Date d = currentDateAndTime.getTime();

        // Создаем экземпляр класса BrewProcess, с которым будем работать
        if(brewProcess == null)
            if (getCortegeId() == 0)
                brewProcess = new BrewProcess();
            else if (getCallSource() == 0)
                brewProcess = new BrewProcess(getCortegeId());
            else brewProcess = new BrewProcess(getCortegeId(), getCallSource());

        //Activity a = getActivity(); // справочно
        if (layoutBDF == null)
            layoutBDF = inflater.inflate(R.layout.fragment_brew, container, false);

        stepHandler(PREPARE_INGREDIENTS_CHOICE);
        return layoutBDF;
    }  //  end  Inflate the layout for this fragment

    // Определям порядок и содержание обработки реакций пользователя
    void stepHandler(int destinaitionPoint){
        this.stepHandler(destinaitionPoint, false, null, null);
    }
    void stepHandler(int destinaitionPoint, boolean bool){
        this.stepHandler(destinaitionPoint, bool, null, null);
    }
    void stepHandler(int destinaitionPoint, boolean bool, int[] ints){
        this.stepHandler(destinaitionPoint, bool, ints, null);
    }
    void stepHandler(int destinaitionPoint, boolean bool, double[] doubles ){
        this.stepHandler(destinaitionPoint, bool, null, doubles);
    }

    private int attamptCount;
    private int incAttamptCount() {
        return ++attamptCount;
    }

    public void clearAttamptCount() {
        this.attamptCount = 0;
    }

    void stepHandler(int destinaitionPoint, boolean bool, int[] ints, double[] doubles){
        int j; j = 0;
        switch(destinaitionPoint) {
            case DESTINATION_UNDEFINED:
                infoToast("Не определена точка перехода", true);
                break;
            case PREPARE_INGREDIENTS_CHOICE:
                if (buttonMaltExtChoose == null)
                    buttonMaltExtChoose = (Button) layoutBDF.findViewById(R.id.malt_name_choice_b);
                if (!buttonMaltExtChoose.hasOnClickListeners())
                    buttonMaltExtChoose.setOnClickListener(buttonMaltExtChooseListener);
                if (buttonMaltBrandChoose == null)
                    buttonMaltBrandChoose = (Button) layoutBDF.findViewById(R.id.malt_brand_choice_b);
                if (!buttonMaltBrandChoose.hasOnClickListeners())
                    buttonMaltBrandChoose.setOnClickListener(buttonMaltBrandChooseListener);
                buttonMaltBrandChoose.setText("выбор производителя");
                clearAttamptCount();

            case PREPARE_INGREDIENTS_MALT_EXT_CHOICE:
                if ((currentMaltName = brewProcess.getNameMaltExt()).equals(EMPTY_STRING)) {
                    modeEditIngredients = true;
                    currentBrandName = EMPTY_STRING;
                } else {
                    currentBrandName = brewProcess.getBrandMaltExt();
                }
                if (modeEditIngredients) {
                    buttonMaltExtChoose.setVisibility(View.VISIBLE);
                    break;
                }


            case CHOOSING_INGREDIENTS_MALT_NAME:
                if (modeEditIngredients){
                    if (buttonMaltExtChoose.getVisibility() == View.VISIBLE)
                        buttonMaltExtChoose.setVisibility(View.INVISIBLE);
                    brewDescriptionFLink.getDrawerToggle();
                }
            case CHOOSING_INGREDIENTS_MALT_BRAND:
                if (modeEditIngredients){
                    buttonMaltBrandChoose.setVisibility(View.VISIBLE);
                    break;
                }


            case CHOOSED_INGREDIENTS_MALT_NAME:

                if (modeEditIngredients){
                    // Получаем _id выбранного экстракта
                    if ((currentMaltNameId = brewDescriptionFLink.getIdMaltExtName())< 0){
                        int i; String s;
                        if ((i = incAttamptCount()) < 3) {
                            switch (i){
                                case 1:
                                    s = "и  ещё  разок"; break;
                                case 2:
                                    s = "последняя попытка"; break;
                                default: s = "Так не бывает";
                            }
                            buttonMaltBrandChoose.setText(s);
                            stepHandler(PREPARE_INGREDIENTS_MALT_EXT_CHOICE);
                            infoToast("Нет данных из базы");
                            break;
                        } else {
                            brewDescriptionFLink.getExitFragment();
                            //stepHandler(PREPARE_INGREDIENTS_MALT_EXT_CHOICE);
                            //infoToast("Не поступают данные из базы", true);
                            break;
                        }
                    }
                    // и строку названия выбранного экстракта
                    currentMaltName = brewDescriptionFLink.getMaltExtName();
                    // Получаем список доступных брендов и выбираем в диалоге
                    availableBrandName = brewDescriptionFLink.getMaltExtBrands();
                    buttonMaltBrandChoose.setVisibility(View.INVISIBLE);
                }

            case CHOOSED_INGREDIENTS_MALT_BRAND:
                if (modeEditIngredients){
                    // Вариант с диалогом (spinner уж больно криво смотрится )
                    String[] brandList = new String[availableBrandName[0].size()];
                    availableBrandName[0].toArray(brandList);
                    //int i;  // форматирование для диалога
                    for (int i = 0; i < brandList.length; i++) {
                        brandList[i] = "   ".concat(brandList[i].toUpperCase());
                    }
                    setListDialog(getString(R.string.title_malt_extract_brand),
                            getString(R.string.alert_malt_extract_brand), brandList, false,
                            listDialogOnClickListener, listOnClickListener);
                    break;
                }

            case CHOOSED_INGREDIENTS_MALT_EXT:
                currentBrandName = availableBrandName[0].get(numberInList).toString();
                maltNameChoosed = (TextView) layoutBDF.findViewById(R.id.malt_name_choosed);
                currentCanWeight = brewProcess.getWeightMaltExtCan();
                // todo Здесь определять Bitter
                maltNameChoosed.setText(formatMaltAndBrandOutput(currentMaltName, currentBrandName, currentCanWeight));
                maltNameChoosed.setVisibility(View.VISIBLE);
                // Сохраняем полученные данные в экземпляре варки
                brewProcess.setNameMaltExt(currentMaltName);
                brewProcess.setBrandMaltExt(currentBrandName);
                // см. коммент в main
                brewProcess.setBitterMaltExt(brewDescriptionFLink.getMaltExtBitter(currentMaltName, currentBrandName));
                // TODO здесь вписать горечь и вес и объем

                modeEditIngredients = false;
                // todo туда же и ID
                brewProcess.setWeightMaltExt(currentCanWeight);     // todo это делать из базы

            case PREPARE_PRIMER_CHOICE:
                initPrimerItemsList();
                // Делаем доступной кнопку перехода к составу праймера и подключаем слушатель
                if (brewProcess.isPrimerEmpty()) {
                    buttonPrimerChoose = (Button) layoutBDF.findViewById(R.id.primer_choose_b);
                    buttonPrimerChoose.setOnClickListener(buttonPrimerChooseListener);
                    buttonPrimerChoose.setVisibility(View.VISIBLE);
                    break;
                }

            case CHOOSING_PRIMER_INGREDIENTS_START:
                if (brewProcess.isPrimerEmpty()) {
                    buttonPrimerChoose.setVisibility(View.GONE);    // в тень  todo get late

                    for (PrimerItem pI : primerItems) {
                        // Показываем CheckBox'ы
                        pI.getCheckBox().setVisibility(View.VISIBLE);
                        // При наличии значений заполняем поля и показываем
                        double w;
                        if ((w = pI.getWeightFromBrewProcess()) != 0) {
                            pI.checkBox.setChecked(true);
                            pI.setMeVisibility(View.VISIBLE);
                            pI.setInputWeight(w);
                        }
                        pI.setCurrentWeight(w);
                    }
                    break;
                }
            case CHOOSING_PRIMER_INGREDIENTS_EDIT:
                if (brewProcess.isPrimerEmpty()) {
                    for (PrimerItem pI : primerItems) {
                        if (pI.isItemChecked()) {
                            pI.requestMeFocus();
                            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                            break;
                        }
                    }
                    break;
                }
            case CHOOSED_INGREDIENTS_PRIMER:
                // Сохранить значения и скрыть элементы выбора компонентов
                if (brewProcess.isPrimerEmpty()) {
                    for (PrimerItem pI : primerItems) {
                        pI.getCheckBox().setVisibility(View.INVISIBLE);
                        pI.setMeVisibility(View.INVISIBLE);
                        pI.setWeightToBrewProcess();    // сохраняем в экземпляре процесса
                    }
                }
                // Отображение строк инфы о составе праймера
                currentPrimer = formatPrimerString(primerItems);
                primerChoosed = (TextView) layoutBDF.findViewById(R.id.primer_choosed);
                primerChoosed.setText(currentPrimer);
                primerChoosed.setVisibility(View.VISIBLE);


            case PREPARE_START_BREW:
                //modeEditFermentation = modeEditBrewProcess;
                // Информационные блоки для отображения параметров загрузки
                if (textStartDate == null)
                    textStartDate = (TextView) layoutBDF.findViewById(R.id.start_fermentation_date);
                else
                    textStartDate.setVisibility(View.INVISIBLE);
                if (textStartTemperature == null)
                    textStartTemperature = (TextView) layoutBDF.findViewById
                            (R.id.start_fermentation_temperature);
                else textStartTemperature.setVisibility(View.INVISIBLE);
                if (textStartDensity == null)
                    textStartDensity = (TextView) layoutBDF.findViewById
                            (R.id.start_fermentation_density);
                else textStartDensity.setVisibility(View.INVISIBLE);
                if (textStartHeader == null)
                    textStartHeader = (TextView) layoutBDF.findViewById(R.id.brew_heading2);
                else textStartHeader.setVisibility(View.INVISIBLE);

                if (buttonStartBrew == null)
                    buttonStartBrew = (Button) layoutBDF.findViewById(R.id.brew_start);
                if (!buttonStartBrew.hasOnClickListeners())
                    buttonStartBrew.setOnClickListener(startBrewClickListener);


            case PREPARE_START_BREW_DATE:
                long l;  // рабочая переменная

                l = brewProcess.getBeginingDateTimeMls();
                if (l == 0) {
                    modeEditFermentation = true;
                    currentDateAndTime.setTimeInMillis(System.currentTimeMillis()); // TODO
                    currentDateAndTime.set(Calendar.MILLISECOND, 0);
                    buttonStartBrew.setText(R.string.button_start_brew);
                } else {
                    currentDateAndTime.setTimeInMillis(l);
                    buttonStartBrew.setText(R.string.button_edit_brew);
                }
                currentDateAndTime.set(Calendar.MINUTE, 0);
                if (modeEditFermentation) {
                    buttonStartBrew.setVisibility(View.VISIBLE);
                    break;
                }

            case CHOOSING_START_BREW_DATE:
                if (buttonStartBrew.getVisibility() == View.VISIBLE)
                    buttonStartBrew.setVisibility(View.GONE);
                if (modeEditFermentation) {
                    // Диалог выбора даты начала
                    wrpDateDialogPicker = new WrpDateDialogPicker(contextBDF,
                        currentDateAndTime.get(Calendar.YEAR), currentDateAndTime.get(Calendar.MONTH),
                        currentDateAndTime.get(Calendar.DAY_OF_MONTH));
                    wrpDateDialogPicker.setDestinationSet(CHOOSED_START_BREW_DATE);
                    wrpDateDialogPicker.setDestinationNegative(CHOOSING_START_BREW_DATE);
                    wrpDateDialogPicker.setDestinationCancel(PREPARE_START_BREW_DATE);
                    wrpDateDialogPicker.show();
                    break;
                }

            case CHOOSED_START_BREW_DATE:
                modeEditFermentation = (bool) ? bool : modeEditFermentation;
                if (modeEditFermentation) {
                    if (ints != null && ints.length == 3) {
                        currentDateAndTime.set(ints[0], ints[1], ints[2]);
                        brewProcess.setBeginingDateTimeHrs(currentDateAndTime.getTimeInMillis());
                    } else {
                        infoToast("Проблемы с датой: Не 3");
                        break;
                    }
                }
                //String[] s = toStringBrewDateAndTime(startDateAndTime);
                //textStartDate.setText(s[0] + s[1]);
                //textStartDate.setVisibility(View.VISIBLE);
            case PREPARE_START_BREW_TIME:
            // Пусто, так как нулевой даты быть уже не может, а 0 часов вполне допустимо
            case CHOOSING_START_BREW_TIME:
                if (modeEditFermentation) {
                    // уточняем время
                    setTimeDialog(currentDateAndTime.get(Calendar.HOUR_OF_DAY),
                            currentDateAndTime.get(Calendar.MINUTE));
                break;
                }
            case CHOOSED_START_BREW_TIME:
                modeEditFermentation = (bool) ? bool : modeEditFermentation;
                if (modeEditFermentation) {
                    if (ints != null && ints.length == 2) {
                        // методом добавления
                        currentDateAndTime.setTimeInMillis(brewProcess.getBeginingDateTimeMls());
                        currentDateAndTime.set(Calendar.HOUR_OF_DAY, ints[0]);
                        brewProcess.setBeginingDateTimeHrs(currentDateAndTime.getTimeInMillis());
                    } else {
                        infoToast("Проблемы со временем: Не 2");
                        break;
                    }
                }
                // Переходим к диалогу определения температуры сусла
            case PREPARE_START_WORT_TEMPERATURE:
                int tw;
                if((tw = brewProcess.getBeginningWortTempereture()) == NUL_TEMPERATURE){
                    modeEditFermentation = true;
                    setWortTempetature(brewProcess.getOptimumStartWortTemperature());
                } else setWortTempetature(tw);

            case CHOOSING_START_WORT_TEMPERATURE:
                if(modeEditFermentation) {
                    // Формируем диалог выбора температуры сусла на старте
                    setTemperatureDialog(brewProcess.getMinWortTemperature(),
                        brewProcess.getMaxWortTemperature(), getWortTempetature(),
                        getString(R.string.title_wort_temperature),
                        getString(R.string.prompt_value_choice), CHOOSED_START_WORT_TEMPERATURE,
                        CHOOSING_START_BREW_TIME, PREPARE_START_BREW);
                    break;
                }
            case CHOOSED_START_WORT_TEMPERATURE:
                modeEditFermentation = (bool) ? bool : modeEditFermentation;
                if (modeEditFermentation)
                    if (ints != null && ints.length == 1) {
                        setWortTempetature(ints[0]);
                        brewProcess.setBeginningWortTempereture(ints[0]);
                    } else {
                        infoToast("Проблемы с температурой сусла: Не 1 ");
                        break;
                    }

            case PREPARE_START_AMBIENT_TEMPERATURE:
                int ta;
                if((ta = brewProcess.getAmbientTemperature()) == NUL_TEMPERATURE){
                    modeEditFermentation = true;
                    setAmbientTemperature(brewProcess.getOptimumAmbientTemperature());
                } else setAmbientTemperature(ta);

            case CHOOSING_START_AMBIENT_TEMPERATURE:
                if(modeEditFermentation){
                    // Диалог определения температуры окружающей среды на старте
                    setTemperatureDialog(brewProcess.getMinAmbientTemperature(),
                        brewProcess.getMaxAmbientTemperature(), getAmbientTemperature(),
                        getString(R.string.title_ambient_temperature),
                        getString(R.string.prompt_value_choice), CHOOSED_START_AMBIENT_TEMPERATURE,
                        CHOOSING_START_WORT_TEMPERATURE, PREPARE_START_BREW);
                    break;
                }

            case CHOOSED_START_AMBIENT_TEMPERATURE:
                modeEditFermentation = (bool) ? bool : modeEditFermentation;
                if (modeEditFermentation)
                    if (ints != null && ints.length == 1){
                        setAmbientTemperature(ints[0]);
                        brewProcess.setAmbientTemperature(ints[0]);
                    } else {
                        infoToast("Проблемы с окружающей средой : Не 1 ");
                        break; }

            case PREPARE_START_WORT_DENSITY:
                double d;
                if ((d = brewProcess.getOriginalWortGravity()) == 0){
                    modeEditFermentation = true;
                    setWortGravity(brewProcess.getMinOriginalWortGravity() + brewProcess.getStepWortGravity()*4);
                } else setWortGravity(d);

            case CHOOSING_START_WORT_DENSITY:
                if (modeEditFermentation){
                    // Определение начального содержания сахара
                    double min, max, step;
                    min = brewProcess.getMinOriginalWortGravity();
                    max = brewProcess.getMaxOriginalWortGravity();
                    step = brewProcess.getStepWortGravity();
                    setDensityDialog(getWortDensityIndex(min, max, step, getWortGravity()),
                        getDensityArrayString(min, max,step), getString(R.string.title_wort_density),
                        getString(R.string.prompt_value_choice), CHOOSED_START_WORT_DENSITY,
                        CHOOSING_START_AMBIENT_TEMPERATURE, PREPARE_START_BREW);
                    break;
                }

            case CHOOSED_START_WORT_DENSITY:
                modeEditFermentation = (bool) ? bool : modeEditFermentation;
                if (modeEditFermentation)
                    if (doubles != null && doubles.length == 1){
                        setWortGravity(doubles[0]);
                        brewProcess.setOriginalWortGravity(doubles[0]);
                    } else {
                        infoToast("Проблемы с плотностью : Не 1 ");
                        break; }

            case SHOW_START_BREW_DATA:
                textStartHeader.setVisibility(View.VISIBLE);
                String s[], w;
                s = toStringBrewDateAndTime(currentDateAndTime);
                w = s[0] + s[1]; textStartDate.setText(w);
                s = toStringBrewTemperatures(getWortTempetature(), getAmbientTemperature());
                w = s[0] + s[1]; textStartTemperature.setText(w);
                s = toStringWortDensity(getWortGravity());
                w = s[0] + s[1]; textStartDensity.setText(w);
                textStartDate.setVisibility(View.VISIBLE);
                textStartTemperature.setVisibility(View.VISIBLE);
                textStartDensity.setVisibility(View.VISIBLE);

            case SHOW_FERMENTATION_PROGRESS :
                fermentationProgressBar = (ProgressBar) layoutBDF.findViewById(R.id.fermentation_progress);
                fermentationProgBarText = (TextView) layoutBDF.findViewById(R.id.fermentation_text);
                fermentationProgressBar.setMax(brewProcess.getMinFermentationDurationHrs());
                int tf;
                if ((tf = (int)(System.currentTimeMillis()/HOUR_IN_MLS - brewProcess.getBeginingDateTimeHrs()))
                         < brewProcess.getMinFermentationDurationHrs()){
                    fermentationProgressBar.setProgress(tf);
                    if (tf > 24) fermentationProgBarText.setText("БУЛЬКИ СЧИТАЕМ");
                    fermentationProgressBar.setVisibility(View.VISIBLE);
                    break;
                } else {
                    if (tf < brewProcess.getMaxFermentationDurationHrs()) {
                        fermentationProgressBar.setProgress((int) Math.floor(brewProcess.getMinFermentationDurationHrs() * 0.95));
                        fermentationProgressBar.setSecondaryProgress(brewProcess.getMinFermentationDurationHrs());
                        fermentationProgBarText.setText(R.string.fermentation_progress_bar_msg1);
                    } else {
                        fermentationProgressBar.setProgress((int) Math.floor(brewProcess.getMinFermentationDurationHrs())); // * 1
                        fermentationProgBarText.setText(R.string.fermentation_progress_bar_msg2);
                    }
                }
            case PREPARE_BOTTLING: //
                // modeEditFermentation = false;
                modeEditBottling = modeEditBrewProcess;
                // Информационные блоки для отображения параметров загрузки
                if (textBottlingDate == null)
                    textBottlingDate = (TextView) layoutBDF.findViewById(R.id.bottling_date);
                else
                    textBottlingDate.setVisibility(View.INVISIBLE);
                if (textBottlingTemperature == null)
                    textBottlingTemperature = (TextView) layoutBDF.findViewById
                            (R.id.bottling_temperature);
                else textBottlingTemperature.setVisibility(View.INVISIBLE);
                if (textBottlingDensity == null)
                    textBottlingDensity = (TextView) layoutBDF.findViewById(R.id.bottling_density);
                else textBottlingDensity.setVisibility(View.INVISIBLE);

                if (textBottlingHeader == null)
                    textBottlingHeader = (TextView) layoutBDF.findViewById(R.id.bottling_heading);
                else textBottlingHeader.setVisibility(View.INVISIBLE); // todo why?
                if (buttonBottling == null)
                    buttonBottling = (Button) layoutBDF.findViewById(R.id.bottling_start);
                if (!buttonBottling.hasOnClickListeners())
                    buttonBottling.setOnClickListener(startBottlingClickListener);

            case PREPARE_BOTTLING_DATE:
                long lb;
                lb = brewProcess.getDurationOfFermentationMls();
                if (lb == 0) {
                    modeEditBottling = true;
                    currentDateAndTime.setTimeInMillis(brewProcess.getBeginingDateTimeMls() +
                            brewProcess.getMinFermentationDurationHrs() * HOUR_IN_MLS);
                    buttonBottling.setText(R.string.button_start_bottling);
                } else {
                    currentDateAndTime.setTimeInMillis(brewProcess.getBeginingDateTimeMls() + lb);
                    buttonBottling.setText(R.string.button_edit_bottling);
                }
                if (modeEditBottling) {
                    buttonBottling.setVisibility(View.VISIBLE);
                    fermentationProgBarText.setVisibility(View.VISIBLE);
                    fermentationProgressBar.setVisibility(View.VISIBLE);
                    break;
                }
            case CHOOSING_BOTTLING_DATE:
                if (buttonBottling.getVisibility() == View.VISIBLE)
                    buttonBottling.setVisibility(View.GONE);
                if (fermentationProgressBar.getVisibility() == View.VISIBLE) {
                    fermentationProgressBar.setVisibility(View.GONE);
                    fermentationProgBarText.setVisibility(View.GONE);
                }
                if (modeEditBottling) {
                    // Диалог выбора даты розлива
                    wrpDateDialogPicker = new WrpDateDialogPicker(contextBDF,
                        currentDateAndTime.get(Calendar.YEAR), currentDateAndTime
                            .get(Calendar.MONTH), currentDateAndTime.get(Calendar.DAY_OF_MONTH));
                    //else wrpDateDialogPicker.updateDate(...)
                    wrpDateDialogPicker.setDestinationSet(CHOOSED_BOTTLING_DATE);
                    wrpDateDialogPicker.setDestinationNegative(CHOOSING_BOTTLING_DATE);
                    wrpDateDialogPicker.setDestinationCancel(PREPARE_BOTTLING_DATE);
                    wrpDateDialogPicker.show();
                    break;
                }
            case CHOOSED_BOTTLING_DATE:
                modeEditBottling = (bool) ? bool : modeEditBottling;
                if (modeEditBottling) {
                    if (ints != null && ints.length == 3) {
                        currentDateAndTime.set(ints[0], ints[1], ints[2]);
                        brewProcess.setDurationOfFermentationMls(currentDateAndTime
                                .getTimeInMillis() - brewProcess.getBeginingDateTimeMls());
                    } else {
                        infoToast("Проблемы с датой розлива: Не 3");
                        break;
                    }
                }
            case PREPAR_BOTTLING_TEMPERATURE:
                int tb;
                if((tb = brewProcess.getBottlingWortTemperature()) == NUL_TEMPERATURE){
                    modeEditBottling = true;
                    setWortTempetature(brewProcess.getOptimumStartWortTemperature()-3);
                } else setWortTempetature(tb);

            case CHOOSING_BOTTLING_TEMPERATURE:
                if(modeEditBottling) {
                    // Формируем диалог выбора температуры сусла при розливе
                    setTemperatureDialog(brewProcess.getMinWortTemperature(),
                            brewProcess.getMaxWortTemperature(), getWortTempetature(),
                            getString(R.string.title_wort_temperature),
                            getString(R.string.prompt_value_choice), CHOOSED_BOTTLING_TEMPERATURE,
                            CHOOSING_BOTTLING_DATE, PREPARE_BOTTLING_DATE);
                    break;
                }
            case CHOOSED_BOTTLING_TEMPERATURE:
                modeEditFermentation = (bool) ? bool : modeEditBottling;
                if (modeEditBottling)
                    if (ints != null && ints.length == 1) {
                        setWortTempetature(ints[0]);
                        brewProcess.setBottlingWortTemperature(ints[0]);
                    } else {
                        infoToast("Проблемы с температурой сусла: Не 1 ");
                        break;
                    }
            case PREPARE_BOTTLING_WORT_DENSITY:
                double db;
                if ((db = brewProcess.getFinalWortGravity()) == 0){
                    modeEditBottling = true;
                    setWortGravity(brewProcess.getMinOriginalWortGravity());
                } else setWortGravity(db);
            case CHOOSING_BOTTLING_WORT_DENSITY:
                if (modeEditBottling){
                    // Определение начального содержания сахара
                    double min, max, step;
                    min = brewProcess.getMinFinalWortGravity();
                    max = brewProcess.getMaxFinalWortGravity();
                    step = brewProcess.getStepWortGravity();
                    setDensityDialog(getWortDensityIndex(min, max, step, getWortGravity()),
                            getDensityArrayString(min, max,step), getString(R.string.title_wort_density),
                            getString(R.string.prompt_value_choice), CHOOSED_BOTTLING_WORT_DENSITY,
                            CHOOSING_BOTTLING_TEMPERATURE, PREPARE_BOTTLING_DATE);
                    break;
                }
            case CHOOSED_BOTTLING_WORT_DENSITY:
                modeEditBottling = (bool) ? bool : modeEditBottling;
                if (modeEditBottling)
                    if (doubles != null && doubles.length == 1){
                        setWortGravity(doubles[0]);
                        brewProcess.setFinalWortGravity(doubles[0]);
                    } else {
                        infoToast("Проблемы с плотностью : Не 1 ");
                        break; }
            case PREPARE_BOTTLING_VOLUME:
                if ((db = brewProcess.getVolumeActual())== 0){
                    modeEditBottling = true;
                    setWortVolume(0);
                } else setWortVolume(db);
            case CHOOSING_BOTTLING_VOLUME:
                if (modeEditBottling){
                    brewOutput = new OutputVolumePicker(contextBDF,
                        new double[]{0.5, 1.0}, brewProcess.getVolumeRecomend(), getWortVolume(),
                        buttonPositiveListenerOUP, buttonNegativeListenerOUP, cancelListenerOUP);
                    brewOutput.show();
                    break;
                }
            case CHOOSED_BOTTLING_VOLUME:
                modeEditBottling = (bool) ? bool : modeEditBottling;
                if (modeEditBottling) {
                    if (doubles != null && doubles.length == 1) {
                        setWortVolume(doubles[0]);
                        if (!brewProcess.setVolumeActual(doubles[0])){
                            stepHandler(CHOOSING_BOTTLING_VOLUME, false);
                            break;
                        }
                    } else {
                        infoToast("Ошибка возврата данных из диалога ");
                        break;
                    }
                    if (ints != null && ints.length == 2) {
                        // todo Куда приставить количество бутылок ? Пока не нужны
                    }
                }
            case SHOW_BOTTLING_RESULT:
                textBottlingHeader.setVisibility(View.VISIBLE);

                s = toStringBrewDateAndTime(currentDateAndTime);
                //w = s[0] + s[1];
                textBottlingDate.setText(s[0]);
                s = toStringBrewTemperatures(getWortTempetature(), 0);
                //w = s[0] + s[1];
                textBottlingTemperature.setText(s[0]);
                s = toStringWortDensity(getWortGravity());
                //w = s[0] + s[1];
                textBottlingDensity.setText(s[0]);
                textBottlingDate.setVisibility(View.VISIBLE);
                textBottlingTemperature.setVisibility(View.VISIBLE);
                textBottlingDensity.setVisibility(View.VISIBLE);

            case SHOW_BREW_RESULT:
                if (textProductHeader == null)
                    textProductHeader =(TextView)layoutBDF.findViewById(R.id.product_heading);
                textProductHeader.setVisibility(View.VISIBLE);
                if (textProductVolume == null)
                    textProductVolume = (TextView) layoutBDF.findViewById(R.id.actual_volume);
                else textProductAlc.setVisibility(View.INVISIBLE);
                if (textProductAlc == null)
                    textProductAlc = (TextView) layoutBDF.findViewById(R.id.alcohol_content);
                else textProductAlc.setVisibility(View.INVISIBLE);
                if (textProductBitter == null)
                    textProductBitter = (TextView) layoutBDF.findViewById(R.id.actual_bitter);
                else textProductAlc.setVisibility(View.INVISIBLE);
                // Данные для расчетов по результату берем из первоисточника
                w = "V = " + Double.toString(brewProcess.getVolumeActual()) + getString(R.string.litr);
                textProductVolume.setText(w);

                if((db = brewProcess.calcAlcContent(new AreometerAC3())) != brewProcess.getAlcoholContent()){
                    brewProcess.setAlcoholContent(db);
                }
                w = "Alc " + Double.toString(db) + getString(R.string.vol_percent);
                textProductAlc.setText(w);
                db = brewProcess.getBitterMaltExt() * brewProcess.getWeightMaltExt()
                        / brewProcess.getVolumeActual();
                w = "Bitt = " + Integer.toString((int) Math.rint(db));
                textProductBitter.setText(w);

                textProductVolume.setVisibility(View.VISIBLE);
                textProductAlc.setVisibility(View.VISIBLE);
                textProductBitter.setVisibility(View.VISIBLE);

            case SHOW_PROCESS_PROGRESS:
                brewProcess.calcProcessStageAndTime();
                 processProgress = new WrpProcessProgress(contextBDF, layoutBDF,
                    brewProcess.getProcessProgressFirst(), brewProcess.getProcessProgressSecond(),
                    getResources()
                        .getStringArray(R.array.process_stage)[brewProcess.getProcessStage()]);
                break;
            default: {

                infoToast("UCB - MODE !?");
                break;
            }
        }

    } // end stepHandler

    WrpDateDialogPicker wrpDateDialogPicker;
    //  Индикатор прогресса ферментации
    ProgressBar fermentationProgressBar;
    TextView fermentationProgBarText;

    // ************* Этап розлива и карбонизации

    // Элементы процесса розлива
    Button   buttonBottling;
    TextView textBottlingHeader;
    TextView textBottlingDate;
    TextView textBottlingTemperature;
    TextView textBottlingDensity;
    // Элеиенты отбражения состояния процесса после розлива
    int[] stageInfo;
    TextView textProductHeader;
    TextView textProductVolume;
    TextView textProductAlc;
    TextView textProductBitter;
    WrpProcessProgress processProgress;
    ProgressBar processProgressBar;
    TextView processProgBarText;

    OutputVolumePicker brewOutput;

    OnClickListener startBottlingClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            stepHandler(CHOOSING_BOTTLING_DATE);
        }
    };

    // Слушатели для диалога ввода результатов варки (объем и кол-во в бутылках
    DialogInterface.OnClickListener buttonPositiveListenerOUP = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which){
            stepHandler(CHOOSED_BOTTLING_VOLUME, true, new int[]{brewOutput.getQuantity(0),
                    brewOutput.getQuantity(1)}, new double[]{brewOutput.getSumVolume()});
        }
    };
    DialogInterface.OnClickListener buttonNegativeListenerOUP = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which){
            stepHandler(CHOOSING_BOTTLING_WORT_DENSITY, false);
        }
    };
    DialogInterface.OnCancelListener cancelListenerOUP = new DialogInterface.OnCancelListener(){
        @Override
        public void onCancel(DialogInterface dialog){
            stepHandler(PREPARE_BOTTLING_DATE, false);
        }
    };

    // ************* Текущие значения для задания Н.У. варки
    // Дата и время начала
    //DatePickerDialog startDatePickerDialog;
    TimePickerDialog startTimePickerDialog;
    TextView textStartHeader;
    TextView textStartDate;
    TextView textStartTemperature;
    TextView textStartDensity;

    // Температура сусла
    int wortTempetature = NUL_TEMPERATURE;
    public int getWortTempetature() {
        return this.wortTempetature;
    }
    public void setWortTempetature(int wortTempetature) {
        this.wortTempetature = wortTempetature;
    }
    // Температура окружающей среды
    int ambientTemperature = NUL_TEMPERATURE;
    public int getAmbientTemperature() {
        return this.ambientTemperature;
    }
    public void setAmbientTemperature(int ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    // Плотность сусла
    private double wortGravity;
    double getWortGravity() {
        return this.wortGravity;
    }

    public void setWortGravity(double wortGravity) {
        this.wortGravity = wortGravity;
    }

    // Объем чудотворной жижи
    private double wortVolume;
    double getWortVolume() {
        return wortVolume;
    }
    void setWortVolume(double wortVolume) {
        this.wortVolume = wortVolume;
    }

    OnClickListener startBrewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            stepHandler(CHOOSING_START_BREW_DATE);
        }
    };

    /* Внутренний класс-обертка для DatePicerDialog, цель - возможность применения пикера на
    разных этапах процесса. Обеспечивает переход на заданые точки через обращение к методу
    stepHandler(destinationPoint, параметры для возврата из слушателей) суперкласса из слушателей*/
    class WrpDateDialogPicker extends DatePickerDialog{
        private int destinationSet = DESTINATION_UNDEFINED;
        private int destinationNegative = DESTINATION_UNDEFINED;
        private int destinationCancel = DESTINATION_UNDEFINED;
        // Вдруг захочется слушателей вынести куда-нибудь
        int getDestinationSet() {
            return destinationSet;
        }
        int getDestinationNegative() {
            return destinationNegative;
        }
        int getDestinationCancel() {
            return destinationCancel;
        }
            // Установка точек перехода по Set, Neg & Cancel
        void setDestinationSet(int destinationSet) {
            this.destinationSet = destinationSet;
        }
        void setDestinationNegative(int destinationNegative) {
            this.destinationNegative = destinationNegative;
        }
        void setDestinationCancel(int destinationCancel) {
            this.destinationCancel = destinationCancel;
        }

        WrpDateDialogPicker(Context context, int year, int month, int dayOfMonth){
            // Если не задавать новых текстов на кнопках
            this(context, year, month, dayOfMonth, getString(R.string.dialog_positive_button_save),
                    getString(R.string.dialog_negative_button_backstep));
        }
        WrpDateDialogPicker(Context context, int year, int month, int dayOfMonth,
                    CharSequence textButtonPositive, CharSequence textButtonNegative){
            super(context, null, year, month, dayOfMonth);
            super.setButton(BUTTON_POSITIVE, textButtonPositive, bottlingDatePositiveListener);
            super.setButton(BUTTON_NEGATIVE, textButtonNegative, bottlingDateNegativeListener);
            super.setOnCancelListener(bottlingDateCancelListener);
        }
            // Слушатели пока внутри, OnDateSet с API 24
        DialogInterface.OnClickListener bottlingDatePositiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker datePicker = ((DatePickerDialog)dialog).getDatePicker();
                stepHandler(getDestinationSet(), true, new int[]
                        {datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()});
            }
        }; // второй вариант - фиксировать изменения и записывать по Positive
        DialogInterface.OnClickListener bottlingDateNegativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stepHandler(getDestinationNegative(), false);
                infoToast("Последний рубеж", false);
            }
        };
        DatePickerDialog.OnCancelListener bottlingDateCancelListener = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stepHandler(getDestinationCancel());
                infoToast("Выбор неизбежен !", false);
            }
        };
    }  // end WrpDatePickerDialog

/*    @Override     todo Посмотреть как сработает
    public boolean onOptionsItemSelected(MenuItem item) {
        int i;
        i=0;
        i++;
        super.onOptionsItemSelected(item);
        return true;
    }  */
    boolean viewHour24 = true;
    //  Диалог выбора времени
    void setTimeDialog(int hour, int minute){
        startTimePickerDialog = new TimePickerDialog(contextBDF,
                brewTimeSetListener, hour, minute, viewHour24 );
        //startTimePickerDialog.setCancelable(false);         // Back исключаем
        startTimePickerDialog.setOnCancelListener(startTimeCancelListener);
        startTimePickerDialog.setButton(BUTTON_NEGATIVE,
            getString(R.string.dialog_negative_button_backstep),brewStartTimeNegativeListener );
        startTimePickerDialog.show();
    }
    //  и его слушатели
    TimePickerDialog.OnTimeSetListener brewTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            currentDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Resources.Theme i = startTimePickerDialog.getContext().getTheme();  // любопытство
            stepHandler(CHOOSED_START_BREW_TIME, true, new int[]{hourOfDay, minute});
        }
    };
    TimePickerDialog.OnClickListener brewStartTimeNegativeListener = new TimePickerDialog.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which){
            stepHandler(CHOOSING_START_BREW_DATE, false);
            infoToast("Шаг назад", false);
        }
    };
    TimePickerDialog.OnCancelListener startTimeCancelListener = new TimePickerDialog.OnCancelListener(){
        @Override
        public void onCancel(DialogInterface dialog) {
            stepHandler(PREPARE_START_BREW);
            infoToast("Выбор неизбежен !");
        }
    };
/* Последовательность прохождения по слушателям следующая :
 Кн BACK -> onCansel -> onDismiss
 Кн OK ->  onTomeSet -> onDismiss
 Кн CANCEL -> onDismiss
 */

        // Формирование строки отображения даты и времени варки
    private String[] toStringBrewDateAndTime(Calendar dateAndTime){
        // todo сохраняем полученные данные
        int month, day;
        day = dateAndTime.get(Calendar.DAY_OF_MONTH);
        month = dateAndTime.get(Calendar.MONTH);
        String dateAndHour[] = new String[2];
        dateAndHour[0] = Integer.toString(dateAndTime.get(Calendar.YEAR)) + HYPHEN +
                ((month>8)? "" : "0") + Integer.toString(month + 1) + HYPHEN +
                ((day>9)? "" : "0") + Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH));
        dateAndHour[1] = "\n     (" + Integer.toString(dateAndTime.get(Calendar.HOUR_OF_DAY)) +
                getResources().getString(R.string.char_hour) + ")";
        return dateAndHour;
    }

    // ************ Параметры в начале варки определяем
    /*
    Можно было сделать диалоги более формализованными, передавать слушатели в качестве
    параметров, но сделано как селано для лучшей читаемости пошагового выполнения , так
    переходы лучше различимы при чтении с экрана.
     */

    // Формирование строки отображения температур варки
    private String[] toStringBrewTemperatures(int tWort, int tAmbient){
        String temperatureWortAndAmbient[] = new String[2];
        temperatureWortAndAmbient[0] = (tWort>0)?("Tw = " + Integer.toString(tWort) + " " + GRAD_C):"Нет данных";
        temperatureWortAndAmbient[1] = (tAmbient>0)?("\nTa = " + Integer.toString(tAmbient) + " " + GRAD_C):"";
        return temperatureWortAndAmbient;
    }

    // Подготовка массива строк для stringPicker плотности
    String[] getDensityArrayString(double minDen, double maxDen, double stepDen){
        ArrayList<String> density = new ArrayList<>(0);
        int i =0;
        double x; x = minDen;
        do {
            density.add(Double.toString(x));
            x += stepDen; i++;
            } while ((x <= maxDen) && i < 20);

        if(minDen<0 || maxDen<0 || minDen>=maxDen || stepDen<=0 || i==20) {
            String[] message = {"Некорректные параметры"};
            return message;
            }
        return density.toArray(new String[density.size()]);
        }

    // todo сделать нормальные get & set

    public int getWortDensityIndex(double minDen, double maxDen, double stepDen, double currentDen) {
        int i;
        double x = minDen;
        for(i = 0; x < maxDen ; x = minDen + stepDen*++i ){
            if (x == currentDen) break;
        };
        return (x < maxDen)? i : 0;
    }


    private AlertDialog.Builder gravityDialogBuilder;
    // Формируем диалог для определения плотности (содержания сахара) в сусле
    void setDensityDialog(int currentD, final String[] values, String sTitle, String sMessage,
            final int positiveDestinaition, final int negativeDestinaition, final int cancelDestinaition){
        if(gravityDialogBuilder ==null) gravityDialogBuilder = new AlertDialog.Builder(contextBDF);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customViewD = inflater.inflate(R.layout.choice_gravity, null);
        TextView areometerInfo = (TextView)customViewD.findViewById(R.id.density_meter);
        areometerInfo.setText("  АС-3\n  в %");
        final NumberPicker strPicker = (NumberPicker)customViewD.findViewById(R.id.string_picker);
        strPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        strPicker.setMinValue(0);
        strPicker.setMaxValue(values.length-1);
        strPicker.setValue(currentD);
        strPicker.setDisplayedValues(values);
        strPicker.setWrapSelectorWheel(false);

        gravityDialogBuilder.setTitle(sTitle)
                .setMessage(sMessage)
                //.setCancelable(true)
                .setView(customViewD)
                .setPositiveButton(R.string.dialog_positive_button_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel(); не надо, сам закрывается
                        stepHandler(positiveDestinaition, true,
                                new int[]{strPicker.getValue()}, // индекс в массиве строк
                                new double[]{Double.parseDouble(values[strPicker.getValue()])});
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button_backstep, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.cancel();
                        stepHandler(negativeDestinaition, false);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        stepHandler(cancelDestinaition, false);
                    }
                });
        AlertDialog densityDialog = gravityDialogBuilder.create();
        densityDialog.show();
    }

    private AlertDialog.Builder temperatureDialogBuilder;
    /* Формируем диалог для опр температуры
    Как альтернатива внутреннуму классу для дат использована функция создания диалогов .
     */
    void setTemperatureDialog(int minT, int maxT, int currentT, String sTitle, String sMessage,
            final int positiveDestinaition, final int negativeDestination, final int cancelDestination){
        if(temperatureDialogBuilder==null)
            temperatureDialogBuilder = new AlertDialog.Builder(contextBDF);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // root : (ViewGroup)getActivity().findViewById(R.id.fragment_brew) не катит
        View customViewT = inflater.inflate(R.layout.choice_temperature, null);
        final NumberPicker numPicker = (NumberPicker)customViewT.findViewById(R.id.number_picker);
        numPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//блок курсора и клавы
        numPicker.setMaxValue(maxT);
        numPicker.setMinValue(minT);
        int x = (maxT+minT)/2;
        numPicker.setValue((currentT>0)? currentT : x);
        //numPicker.isC
        numPicker.setWrapSelectorWheel(true);
        temperatureDialogBuilder.setTitle(sTitle)
                .setMessage(sMessage)
                //.setCancelable(false)
                .setView(customViewT)
                .setPositiveButton(R.string.dialog_positive_button_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Сохраняем выбранное значение T
                        //setCurrentTemperature(numPicker.getValue());
                        // dialog.cancel(); // Вроде как диалог сам закрывает
                        stepHandler(positiveDestinaition, true, new int[]{numPicker.getValue()});
                         // nextStepTemperatureAmbient();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button_backstep, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Без изменений, переходим к следующему шагу
                        // dialog.cancel();
                        stepHandler(negativeDestination, false);
                        //nextStepTemperatureAmbient();.
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog){
                        stepHandler(cancelDestination, false);
                    }
                }) ;
        AlertDialog temperatureDialog = temperatureDialogBuilder.create();
        temperatureDialog.show();
    }

    /* Внутренний класс-обертка для формирования AlertDialog с numeric.picker + textView (NPTV) в
     среднем View, исп. для записи температур (может и ещё где пригодится), обеспечивает переход
     в заданную точку процесса через обращение к stepHandler суперкласса, точка перехода опре-
     деляется нажатой кнопкой .      */
    class WrpAlertDialogNPTV extends AlertDialog.Builder{
        WrpAlertDialogNPTV(Context context){
            super(context);
        }

    }


    String[] toStringWortDensity(double wDensity){
        String[] result = new String[2];
        result[0] = RHO_C + " = " + Double.toString(wDensity);
        result[1] = "\nсахар, %";
        return result;
    }


    // Метод создает AlertDialog "Да/Нет". На выходе true/false,
    // на входе текстовые строки сообщений и кнопок
    // todo Может его заглобалить ?
    private void getYesNoAlertDialog(String title, String message,
        String buttonYes, String buttonNo, boolean cancelAble, DialogInterface.OnClickListener yesNoDialogOnClickListener){
        AlertDialog.Builder getYesNoBuilder = new AlertDialog.Builder(contextBDF);
        getYesNoBuilder.setCancelable(false)    // можно сделать параметром, если понадобится
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonYes, yesNoDialogOnClickListener)
            .setNegativeButton(buttonNo, yesNoDialogOnClickListener);
        AlertDialog yesNoDialog = getYesNoBuilder.create();
        yesNoDialog.show();
    }

    // Сборка и форматирование строки для вывода на экран инфы о выбранном экстракте
    private String formatMaltAndBrandOutput(String malt, String brand, double canWeight){
        return getString(R.string.name_output_prefix) + malt + getString(R.string.name_output_separator1) + brand + ", " + Double.toString(canWeight) + getString(R.string.name_output_postfix);

    }
    // Реакции на нажатие Кн выбора названия охмеленки и марки
    OnClickListener buttonMaltExtChooseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            stepHandler(CHOOSING_INGREDIENTS_MALT_NAME);
        }
    };
    OnClickListener buttonMaltBrandChooseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            stepHandler(CHOOSED_INGREDIENTS_MALT_NAME);
        }
    };

    // реализуем слушателя к выдвижному списку
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /* видимо, так :
            parent - ListView; view - элемент списка (тут строка) , далее её позиция и id
             */
            int i = 0;
            i++;
            //selectDrawerItem(position, id);
        }
    }

    //***  Блок выбора бренда.  Вариант с AlertDialog со списком
    //     На входе ArrayList с доступными для выбранного названия брендами
    private AlertDialog.Builder listDialogBuilder;
    //  Переменные для взаимодействия слушателей метода setListDialog
    private int numberInList;   // todo с номером криво как-то

    // Слушатель для обработки списка в диалоге выбора бренда
    DialogInterface.OnClickListener listOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            numberInList = id;
        }
    };
    // Слушатель для кнопок в диалоге выбора бренда
    DialogInterface.OnClickListener listDialogOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which){
                case BUTTON_NEGATIVE:
                    stepHandler(CHOOSING_INGREDIENTS_MALT_NAME);
                    break;
                case BUTTON_POSITIVE:
                    stepHandler(CHOOSED_INGREDIENTS_MALT_EXT);
                    break;
                default:
                    infoToast("Где такая кнопка ?!");
            }

        }
    };
    // Метод формирования диалога(alert) с выбором из списка одного значения
    void setListDialog(String title, String alert, String[] list,
                       boolean cancelable, DialogInterface.OnClickListener listDialogOCL,
                       DialogInterface.OnClickListener listOCL){
        numberInList = 0;       // при желании можно добавить параметр метода и определять снаружи
        if (listDialogBuilder == null) listDialogBuilder   = new AlertDialog.Builder(contextBDF);
        ArrayAdapter adapter = new ArrayAdapter<>(contextBDF, android.R.layout.simple_list_item_single_choice, list);
        listDialogBuilder.setSingleChoiceItems(adapter, numberInList, listOCL )
                .setTitle(title)
                .setCancelable(cancelable)
                .setNegativeButton(R.string.dialog_negative_button_cancel, listDialogOCL);
        if (list.length == 0) listDialogBuilder.setMessage(alert);
        else listDialogBuilder.setPositiveButton(R.string.dialog_positive_button_save, listDialogOCL);
        AlertDialog dialog = listDialogBuilder.create();
        dialog.setInverseBackgroundForced(false);  // todo проверить
        dialog.show();
    }

    //***  Внутренний класс пункта компонента праймера
    class PrimerItem {
        private PrimerItem(View v, String name, int resIdCheckBox, int resIdEditText){
            this.name = name;
            this.checkBox = (CheckBox)v.findViewById(resIdCheckBox);
            this.inputWeight = (EditText)v.findViewById(resIdEditText);
        }
        private PrimerItem(View v, int resNameId, int resIdCheckBox, int resIdEditText){
            this(v, getString(resNameId), resIdCheckBox, resIdEditText);
        }
        private String name;
        private double currentWeight;   // отображаемый вес компонента
        private CheckBox checkBox;      // CheckBox выбора компонента
        private EditText inputWeight;   // EditText ввода веса компонента
        @Override
        public String toString(){
            return name;
        }
        // Запись заданного веса в поле набора EditText
        void setInputWeight(double cWeight){
            String s = EMPTY_STRING;
            int i;
            if ((i=(int)cWeight)!= 0) s = Integer.toString(i);
            this.inputWeight.setText(s);
        }
        // И его чтение (в случае пустой строки возвращает "-1.0"
        double getInputWeight(){
            String s = this.inputWeight.getText().toString();
            if (!s.equals(EMPTY_STRING)) return Double.parseDouble(s);
            else {
                return -1;
            }
        }
        // Вес соответствующего компонента из экземпляра процесса
        double getWeightFromBrewProcess(){
            double cWeight;
            switch(this.inputWeight.getId()){
                case R.id.item1_weight_input:
                    currentWeight = brewProcess.getWeightDextrosa();
                    break;
                case R.id.item2_weight_input:
                    currentWeight = brewProcess.getWeightSugar();
                    break;
                case R.id.item3_weight_input:
                    currentWeight = brewProcess.getWeightEnhancer();
                    break;
                case R.id.item4_weight_input:
                    currentWeight = brewProcess.getWeightThickener();
                    break;
                default:
                    infoToast("Нет такого компонента");
                    currentWeight = 0;
            }
            return currentWeight;
        }
        void  setWeightToBrewProcess(){
            setWeightToBrewProcess(currentWeight);
        }
        void setWeightToBrewProcess(double cWeight){
            switch(this.inputWeight.getId()){
                case R.id.item1_weight_input:
                    brewProcess.setWeightDextrosa(cWeight);
                    break;
                case R.id.item2_weight_input:
                    brewProcess.setWeightSugar(cWeight);
                    break;
                case R.id.item3_weight_input:
                    brewProcess.setWeightEnhancer(cWeight);
                    break;
                case R.id.item4_weight_input:
                    brewProcess.setWeightThickener(cWeight);
                    break;
                default:
                    infoToast("Нет такого компонента");
                    cWeight = 0;
            }
        }
        // Текущее значение веса компонента
        double getCurrentWeight(){
            return currentWeight;
        }
        void setCurrentWeight(double currentWeight){
            this.currentWeight = currentWeight;
        }
        //  Проверка избранности (есть галочка = true)
        boolean isItemChecked(){
            return this.checkBox.isChecked();
        }
        // Получение элементов управления
        CheckBox getCheckBox(){
            return checkBox;
        }
        EditText getInputWeightView(){
            return inputWeight;
        }
        void setMeVisibility(int visibiity){
            inputWeight.setVisibility(visibiity);
        }
        void requestMeFocus(){
            inputWeight.requestFocus();
        }
    }
    // Формирование строки для вывода информации о праймере
    String formatPrimerString(PrimerItem[] primerIt){
        StringBuilder s = new StringBuilder("Pr:");
        int i = 0;
        for (PrimerItem pI: primerIt ) {
            if (pI.currentWeight > 0){
                s.append(" ").append(pI.toString().toLowerCase().trim()).append(" "); // название
                s.append((int ) pI.currentWeight).append("г,").append((i%2!=0)? "\n":""); // вес в граммах
            }
            i++;
        }
        s.setCharAt(s.lastIndexOf(","), '.');
        return s.toString();
    }


    OnClickListener buttonPrimerChooseListener = new OnClickListener() {
        @Override
        public void onClick(View v){
            stepHandler(CHOOSING_PRIMER_INGREDIENTS_START);
        }
    };

    InputMethodManager imm;

    /* Пока не очень и нужен
boolean d = false;
OnClickListener primerOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
        //if(!((EditText)v).isCursorVisible()) ((EditText)v).setCursorVisible(true);
       // d = true;
    }
}; */
    /* Пока не очень и нужен
    View.OnFocusChangeListener primerOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int resId = v.getId();
        }
    }; */

    OnCheckedChangeListener primerCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton bV, boolean isChecked) {
            if (imm == null)
                imm = (InputMethodManager) contextBDF.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean flagKeyboardOff;
            flagKeyboardOff = true;
            int resId = bV.getId();
            for (PrimerItem pI : primerItems) {
                if (pI.getCheckBox().getId() == resId) {
                    if (isChecked) {
                        pI.setInputWeight(pI.getCurrentWeight());
                        pI.setMeVisibility(View.VISIBLE);
                        pI.requestMeFocus();
                    } else {
                        pI.setCurrentWeight(0);
                        pI.setMeVisibility(View.INVISIBLE);
                        for (PrimerItem pIi: primerItems) {
                            if (pIi.isItemChecked() && pIi.getInputWeight()<0){
                                pIi.requestMeFocus();
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            // Если нет выбранных скрыть клаву
            for (PrimerItem pI: primerItems) {
                if (pI.isItemChecked()) {
                    flagKeyboardOff = false;
                    break;
                }
            }
            View v = getView();
            if (flagKeyboardOff && v != null) {
                IBinder iB = v.getWindowToken();
                imm.hideSoftInputFromWindow(iB, 0);
            }
        }
    };

    TextView.OnEditorActionListener editTextActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Для сравнения через switch, а не цикл
            boolean result;
            result = false;     // нормальный выход с передачей на стандартную обработку
            outer:
            // вне только return, на случай вставок и коррекций в будущем

            switch (actionId) {
                case IME_ACTION_NEXT:
                case IME_ACTION_DONE:
                    for (PrimerItem pI : primerItems) {
                        if (pI.isItemChecked() && pI.getInputWeight() < 0) {
                            if (pI.inputWeight.isFocused()) infoToast("Тут необходимо число");
                            pI.requestMeFocus();
                            result = true;
                            break outer;
                        }
                    }
                    if (actionId == IME_ACTION_NEXT) break;
                    double[] primerValidParams = new double[PRIMER_NUMBER_OF_COMPONENTS];
                    int i = 0;
                    for (PrimerItem pI: primerItems) {
                        double x;
                        x = pI.getInputWeight();
                        if (x > 0) {
                            pI.setCurrentWeight(x);
                        }
                        primerValidParams[i] = (x == -1)? 0 : x;
                        i++;
                    }
                    // Проверка корректности праймера и вызов диалога Yes/No
                    StringBuilder message_alert = new StringBuilder(EMPTY_STRING);
                    switch (brewProcess.primerValidation(primerValidParams)) {
                        case PRIMER_OK: {
                            message_alert.append(getString(R.string.message_primer_dialog));
                            break;
                        }
                        case PRIMER_LACK: {
                            message_alert.append(getString(R.string.alert1_primer_dialog));
                            break;
                        }
                        case PRIMER_OVERAGE: {
                            message_alert.append(getString(R.string.alert2_primer_dialog));
                            break;
                        }
                        case PRIMER_TYPO: {
                            message_alert.append(getString(R.string.alert4_primer_dialog));
                            break;
                        }
                        case PRIMER_THICK_OVERAGE: {
                            message_alert.append(getString(R.string.alert8_primer_dialog));
                            break;
                        }
                        case PRIMER_COMPAUND: {
                            message_alert.append(getString(R.string.alert16_primer_dialog));
                            break;
                        }
                        default:
                            message_alert.append("Так не бывает");
                    }
                    for (PrimerItem pI: primerItems) {  // форм. строки сообщения
                        if (pI.getCurrentWeight()>0){
                            message_alert.append("\n    -   ").append(pI.toString().trim()
                                    .toLowerCase()).append(" ").append((int)pI.getCurrentWeight())
                                    .append(" г");
                        }
                    }
                    getYesNoAlertDialog(getString(R.string.title_primer_dialog),
                            message_alert.toString(), getString(R.string.dialog_positive_button_save),
                            getString(R.string.dialog_edit_button), false, yesNoDialogOCL);
                    break;
                default:
                    infoToast("It's impossible : actionId = " + Integer.toString(actionId));
            }
            return result;
        }
    };

    // Реализация интерфейса слушателя диалога Yes/No для этапа формирования праймера
    DialogInterface.OnClickListener yesNoDialogOCL = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case BUTTON_POSITIVE:
                    stepHandler(CHOOSED_INGREDIENTS_PRIMER);
                    break;
                case BUTTON_NEGATIVE:
                    stepHandler(CHOOSING_PRIMER_INGREDIENTS_EDIT);
                    break;
                default:
            }
        }
    };
        // Инициализация списка пунктов компонентов праймера
    void initPrimerItemsList(){
        if (primerItems == null){
            primerItems = new PrimerItem[PRIMER_NUMBER_OF_COMPONENTS];
            // Для декстрозы
            primerItems[0] = new PrimerItem(layoutBDF, R.string.name_primer_item1,
                    R.id.primer_item1_selected, R.id.item1_weight_input);
            // Для сахара
            primerItems[1] = new PrimerItem(layoutBDF, R.string.name_primer_item2,
                    R.id.primer_item2_selected, R.id.item2_weight_input);
            // Для Enhancer (~усилитель)
            primerItems[2] = new PrimerItem(layoutBDF, R.string.name_primer_item3,
                    R.id.primer_item3_selected, R.id.item3_weight_input);
            // Для загустителя
            primerItems[3] = new PrimerItem(layoutBDF, R.string.name_primer_item4,
                    R.id.primer_item4_selected, R.id.item4_weight_input);
            // И подключаем слушателей
            for (PrimerItem pI : primerItems) {
                // Привязываем слушателей  todo может и слушателей через свои методы подключать ?
                pI.getCheckBox().setOnCheckedChangeListener(primerCheckedChangeListener);
                pI.getInputWeightView().setOnEditorActionListener(editTextActionListener);
                // И считываем значения весов компонентов из процесса в текущие
                pI.setCurrentWeight(pI.getWeightFromBrewProcess());
                // Пока не востребованы
                //pI.getInputWeightView().setOnFocusChangeListener(primerOnFocusChangeListener);
                //pI.getInputWeightView().setOnClickListener(primerOnClickListener);
            }
        }

    }
}

/*
    //     Выбор бренда . Вариант со Spinner, отметен как громоздкий
    //     На входе ArrayList<String> от Db , отфильтрованный по наличию
    private void f1_SpinnerBrandName(){
        if(availableBrandName.size()==3){
            currentBrandName = availableBrandName.get(0);
            brewProcess.setBrandMaltExt(currentBrandName);
            String sb = "СЭ "+ currentMaltName + " от " + currentBrandName;
            maltNameChoose.setText(sb);
            // К следующему шагу (праймеры)
            f1_PrimerChoose();
        } else {
            spinnerBrandName = (Spinner)layoutBDF.findViewById(R.id.brand_name_choose);
            spinnerBrandName.setOnItemSelectedListener(spinnerBrandNameChooseListener);
            adapterBrandName = new ArrayAdapter<>(contextBDF, R.layout.my_simple_spinner_item1, availableBrandName);
            adapterBrandName.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item1);
            spinnerBrandName.setAdapter(adapterBrandName);
            spinnerBrandName.setSelection(availableBrandName.size()-1);
            spinnerFirstRunFlag = true;

            // spinnerBrandName.setDropDownHorizontalOffset(30);
            //spinnerBrandName.setDropDownVerticalOffset(180);

            spinnerBrandName.setVisibility(View.VISIBLE);
        }

    }
    private void f1_OnItemSelected(int position, long id){
        int i;

        i = (int)id;
        if (spinnerFirstRunFlag){
            availableBrandName.remove(availableBrandName.size()-1);
            availableBrandName.add("HURRY UP !");
            // adapterBrandName.notifyDataSetChanged(); это мешает
            spinnerFirstRunFlag = false;
        } else {
            currentBrandName = availableBrandName.get(i);
            brewProcess.setBrandMaltExt(currentBrandName);
            //String sb = "СЭ "+ currentMaltName + " от " + currentBrandName;
            maltNameChoose.setText(formatMaltAndBrandOutput());
            spinnerBrandName.setVisibility(View.GONE);
        // К следующему шагу (праймеры)
            f1_PrimerChoose();
        }
    }

    OnItemSelectedListener spinnerBrandNameChooseListener = new OnItemSelectedListener() {
        @Override
        public void onNothingSelected(AdapterView<?> parent){
            //
            int i = 0;
            i++;
        }
        @Override       // Для варианта с f1
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           // ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
            f1_OnItemSelected(position, id);

        }

    }; */
    /* На входе массив строк ...
    private void f2_SpinnerBrandName() {
        int i;
        if (availableBrandName.length == 1) {
            currentBrandName = availableBrandName[0];
            String sb = currentMaltName + "  " + currentBrandName;
            maltNameChoose.setText(sb);

        } else {
            spinnerBrandName = (Spinner) layout.findViewById(R.id.brand_name_choose);
            spinnerBrandName.setOnItemSelectedListener(spinnerBrandNameChooseListener);
            adapterBrandName = new ArrayAdapter<String>(contextBDF, R.layout.my_simple_spinner_item1, availableBrandName);
            adapterBrandName.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item1);
            spinnerBrandName.setAdapter(adapterBrandName);
            spinnerBrandName.setSelection(availableBrandName.length-1);
            spinnerFirstRunFlag = true;
            i = layout.getHeight();
            i = layout.getWidth();
            i = spinnerBrandName.getDropDownWidth();
            // spinnerBrandName.setDropDownHorizontalOffset(-30);
            //spinnerBrandName.setDropDownVerticalOffset(0);
            spinnerBrandName.setVisibility(View.VISIBLE);
        }
    }
    private void f2_OnItemSelected(int position, long id){
        int i;
        i = spinnerBrandName.getDropDownWidth();
        i = position;
        i = (int)id;
        if (spinnerFirstRunFlag){
            //availableBrandName.remove(i);
           // availableBrandName.add("Выбираем, не тормозим");
            //i = availableBrandName.size();
            // adapterBrandName.notifyDataSetChanged(); это мешает
            availableBrandName[availableBrandName.length-1]="HURRY UP !";
            spinnerFirstRunFlag = false;
        } else {
            currentBrandName = availableBrandName[i];
            String sb = currentMaltName + "  " + currentBrandName;
            maltNameChoose.setText(sb);
            spinnerBrandName.setVisibility(View.GONE);
        }
    } */