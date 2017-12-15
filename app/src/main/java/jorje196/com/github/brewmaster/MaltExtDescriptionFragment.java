package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by User on 30.11.2017.
 * Фрагмент с описанием охмеленного экстракта
 */

public class MaltExtDescriptionFragment extends Fragment {
    static final String MEDF_TAG = "maltDescription";    // тег фрагмента
    static final String ARG_POSITION = "intPosition"; // текущая позиция в кортеже (0...N-1)
    static final String ARG_SIZE = "intSize";  // количество доступных кортежей ( N )
    static final String ARG_ID = "intPositionId";    // id в табл. Names текущей позиции
    static final String ARG_NAME = "stringName";    // название
    static final String ARG_BRAND = "stringBRAND";    // бренд
    static final String ARG_BITTER = "intBitterness";  // горечь и цвет
    static final String ARG_COLOR = "intColor";
    static final String ARG_DESCRIPT = "stringDescription";  // писание
    static final String ARG_SRCIMG = "stringSrcImg";    // ссылка на ресурс изображения
    static final String ARG_VOLUME = "doubleVolume";    // рекомендованный объем
    static final String ARG_WEIGHT = "doubleWeight";    // вес экстракта

    private int positionId = 0;
    int sizeList = 0;
    int position = 0;



    // Интерфейс для передачи инфы в родительскую активность
    interface FragMaltLink {
        void getFML(String tag, int id, int size, int position);
    }

    FragMaltLink onFragMaltLink;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onFragMaltLink = (FragMaltLink) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ///*****************

        // Получение информации для отображения
        Bundle argsBundle = getArguments();
        setSizeList(argsBundle.getInt(ARG_SIZE));
        setPosition(argsBundle.getInt(ARG_POSITION));
        setPositionId(argsBundle.getInt(ARG_ID));
        // имя ресурса изображения
        String imgSrc = argsBundle.getString(ARG_SRCIMG);
        String fullName = argsBundle.getString(ARG_NAME) + "   " + argsBundle.getString(ARG_BRAND);
        String description = argsBundle.getString(ARG_DESCRIPT);
        int bitterness = argsBundle.getInt(ARG_BITTER);
        int color = argsBundle.getInt(ARG_COLOR);
        Double weight = argsBundle.getDouble(ARG_WEIGHT);
        Double volume = argsBundle.getDouble(ARG_VOLUME);


        bitterness = (int) Math.round(bitterness * weight / volume);
        color = (int) Math.round(color * weight / volume);

        View layout = inflater.inflate(R.layout.fragment_malt, container, false);

        ImageView maltView = (ImageView) layout.findViewById(R.id.malt_imageView);

        // получаем ID ресурса изображения для полученного из DB наименования см. .content.res.Resources
        int resID = getResources().getIdentifier(imgSrc, "drawable", "jorje196.com.github.brewmaster");
        // TODO exception если нет такого ресурса
        maltView.setImageResource(resID);
        TextView maltVeriety = (TextView) layout.findViewById(R.id.malt_veriety);
        TextView maltProperties = (TextView) layout.findViewById(R.id.malt_properties);
        TextView canProperties = (TextView) layout.findViewById(R.id.can_properties);
        TextView maltDescription = (TextView) layout.findViewById(R.id.malt_description);
        maltVeriety.setText(fullName);
        String propeties = getResources().getString(R.string.bitter) + Integer.toString(bitterness) +
                getResources().getString(R.string.color) + Integer.toString(color);
        maltProperties.setText(propeties);
        propeties = getResources().getString(R.string.weight) + Double.toString(weight) +
                getResources().getString(R.string.volume) + volume;
        canProperties.setText(propeties);
        maltDescription.setText(description);
        // setNumId(5);

        // Слушатель кнопок
        Button next = (Button) layout.findViewById(R.id.button_fmalt_next);
        checkSetEnabledNext(layout);
        next.setOnClickListener(myButtonsClickListener);
        Button prev = (Button) layout.findViewById(R.id.button_fmalt_prev);
        checkSetEnablePrev(layout);
        prev.setOnClickListener(myButtonsClickListener);
        return layout;
    }
    // или такой слушатель
    OnClickListener myButtonsClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_fmalt_next :
                    setPosition(getPosition()+1);
                    checkSetEnabledNext(v);
                    break;
                case R.id.button_fmalt_prev :
                    setPosition(getPosition()-1);
                    checkSetEnablePrev(v);
                    break;  // для порядка
            }
            onFragMaltLink.getFML(MEDF_TAG, getPositionId(), getSizeList(), getPosition());
        }
    };
    // проверка и установка доступности кнопок next&prev
    void checkSetEnablePrev(View layout){
        Button prev = (Button) layout.findViewById(R.id.button_fmalt_prev);
        if(getPosition()>0) prev.setEnabled(true);
            else prev.setEnabled(false);
    }
    void checkSetEnabledNext(View layout){
        Button next = (Button) layout.findViewById(R.id.button_fmalt_next);
        if(getPosition()< getSizeList()-1) next.setEnabled(true);
        else next.setEnabled(false);
    }

    // геттеры и сеттеры свойств
    int getPositionId() {
        return positionId;
    }
    void setPositionId(int id){
        positionId = id;
    }
    int getSizeList() {
        return sizeList;
    }
    void setSizeList(int size) {
        sizeList = size;
    }
    int getPosition() {
        return position;
    }
    void setPosition(int pos) {
        position = pos;
    }

}