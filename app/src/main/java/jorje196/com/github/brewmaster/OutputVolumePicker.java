package jorje196.com.github.brewmaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by User on 12.03.2018.      Относится к BrewDescriptionFragment
 * Диалог формирования набора заполненных целебной жижей ёмкостей формируем через
 * данный класс . При необходимости можно сделать список колес прокрутки со
 * скроллингом,  или вообще другой вложенный View, но в реале размеров максимум два.
 * Класс вынесен для разгрузки файла кода фрагмента варки, но может и ещё пригодится.
 * Важен толко объем, но количества бутылок тоже доступно (см.слушателей).
 */
class OutputVolumePicker extends AlertDialog{
    static private final int ZERO = 0;
    private double[] sizeList;        // список размеров бутылок
    private double getSize(int i) {
        return (i < sizeList.length)? sizeList[i]: -1 ;
    }
    private void setSizeList(double[] sizeList) {
        this.sizeList = new double[sizeList.length];
        for(int i = 0; i < sizeList.length; i++){
            this.sizeList[i] = (sizeList[i] > 0)? sizeList[i] : ZERO;
        }
    }

    private int[] quantityList;       // список количеств бутылок (привязано к размерам)
    int  getQuantity(int i) {
        return (i < quantityList.length)? quantityList[i] : -1;
    }
    int[] getQuantityList(){
        return quantityList;
    }
    private void setQuantity(int i, int quantity) {
        this.quantityList[i] = quantity;
    }

    private double sumVolume;                // суммарный объем на выходе
    double getSumVolume() {
        return sumVolume;
    }
    private void setSumVolume() {
        this.sumVolume = 0;
        for(int i = 0; i < sizeList.length; i++) {
            this.sumVolume += getSize(i) * getQuantity(i);
        }
    }
    private CharSequence actualVolumeS;
    private double nominalVolume;           // номинальный объем сусла;
    private double getNominalVolume() {
        return nominalVolume;
    }
    private void setNominalVolume(double nominalVolume) {
        this.nominalVolume = nominalVolume;
    }

    boolean volumeVerify(){
        return (Math.abs(getNominalVolume() - getSumVolume()) / getNominalVolume() < 0.2);
    }   // может ещё пригодится

    private NumberPicker[] numberPicker; //numberPicker1, numberPicker2;
    private TextView textResult;
    static private final int[] RES_NP = {R.id.number_picker1, R.id.number_picker2};
    static private final int[] RES_TITLE_NP = {R.id.title_np1, R.id.title_np2};
    static private final double CONST_MAX = 1.2;


    OutputVolumePicker(Context context, double[] sizeList, double nominalVolume,
        double actualVolume, DialogInterface.OnClickListener buttonPositiveListenerOUP,
                       DialogInterface.OnClickListener buttonNegativeListenerOUP,
                       DialogInterface.OnCancelListener cancelListenerOUP){
        this(context, sizeList, nominalVolume, actualVolume, buttonPositiveListenerOUP,
                buttonNegativeListenerOUP, cancelListenerOUP,
                context.getString(R.string.dialog_positive_button_save),
                context.getString(R.string.dialog_negative_button_backstep),
                context.getString(R.string.title_brew_result),
                context.getString(R.string.msg_brew_result));
    }
    OutputVolumePicker(Context context, double[] sizeList, double nominalVolume,
            double actualVolume, DialogInterface.OnClickListener buttonPositiveListenerOUP,
            DialogInterface.OnClickListener buttonNegativeListenerOUP,
            DialogInterface.OnCancelListener cancelListenerOUP,
            CharSequence buttonPositive, CharSequence buttonNegative,
            CharSequence title, CharSequence msg){
        super(context);
        setSizeList(sizeList);
        int _length = sizeList.length;
        setNominalVolume(nominalVolume);
        this.actualVolumeS = Double.toString(actualVolume) + context.getString(R.string.litr);
        this.quantityList = new int[_length];
        this.numberPicker = new NumberPicker[_length];
        TextView[] title_np = new TextView[_length];
        int n;
        final ViewGroup nullParent = null;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View pickersView = inflater.inflate(R.layout.choice_volume, nullParent, false);
        super.setTitle(title);
        super.setMessage(msg);
        super.setView(pickersView);
        super.setButton(BUTTON_POSITIVE, buttonPositive, buttonPositiveListenerOUP);
        super.setButton(BUTTON_NEGATIVE, buttonNegative, buttonNegativeListenerOUP);
        super.setOnCancelListener(cancelListenerOUP);
        NumberPicker.OnClickListener onClickListener = new NumberPicker.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = -1, i = -1;
                int pickerId = view.getId();
                if (pickerId == RES_NP[0]) {
                    j = 0;
                    i = 1;
                } else if (pickerId == RES_NP[1]) {
                    j = 1;
                    i = 0;
                }
                if (i >= ZERO) {
                    setQuantity(j, numberPicker[j].getValue());
                    calcAndShow();
                }
            }
        };
        NumberPicker.OnValueChangeListener valueChangeListener =
                                        new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int j = -1, i = -1;
                int pickerId = picker.getId();
                if (pickerId == RES_NP[0]){
                    j = 0; i = 1;
                } else if (pickerId == RES_NP[1]){
                    j = 1; i = 0;
                }
                quantityList[j] = newVal;

                if (quantityList[i] == ZERO){
                    numberPicker[i].setValue((int)Math.ceil(nMax( i, j, newVal)/CONST_MAX));

                    quantityList[i] = numberPicker[i].getValue();
                }
                calcAndShow();
            }
        };
        CharSequence s;
        for (int i = 0; i < _length; i++) {
            quantityList[i] = ZERO;
            title_np[i] = (TextView) pickersView.findViewById(RES_TITLE_NP[i]);
            s = Double.toString(sizeList[i]) + context.getString(R.string.litr);
            title_np[i].setText(s);
            numberPicker[i] = (NumberPicker) pickersView.findViewById(RES_NP[i]);
            numberPicker[i].setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            numberPicker[i].setMinValue(0);
            n = nMax(i);
            numberPicker[i].setMaxValue(n);
            numberPicker[i].setValue(ZERO);
            numberPicker[i].setOnValueChangedListener(valueChangeListener);
            numberPicker[i].setOnClickListener(onClickListener);
        }
        textResult = (TextView)pickersView.findViewById(R.id.volume_result);
        calcAndShow();
    }
    private void calcAndShow(){ // Процедура расчета и отображения суммарного объема(2x:
        CharSequence cs;        // исходного (верх) и соответствующего кол-ву бутылок)
        setSumVolume();
        cs = actualVolumeS + "\n\n" + Double.toString(getSumVolume()) +
                                                    getContext().getString(R.string.litr);
        textResult.setText(cs);
    }
    private int nMax(int i){
        int n;
        n = (int) Math.floor(nominalVolume * CONST_MAX / sizeList[i]);
        return n;
    }
    private int nMax(int i, int j, int value){
        return (int)Math.floor((nominalVolume - sizeList[j]*value)/sizeList[i] * CONST_MAX);
    }
}