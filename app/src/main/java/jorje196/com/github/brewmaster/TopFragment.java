package jorje196.com.github.brewmaster;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *  Фрагмент , отображающийся во фрэйме главной активности
 *  TODO Вернуть картинку и генератор случайных фраз
 */
public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_top, container,false);
    }
}
