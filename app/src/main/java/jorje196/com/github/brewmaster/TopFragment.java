package jorje196.com.github.brewmaster;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *  Фрагмент , отображающийся во фрэйме главной активности
 *  TODO тут ли его место ?
 */
public class TopFragment extends Fragment {
    static final String TOPF_TAG = "topFragment";    // тег фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // выбираем присказку , картинка пока безальтернативна
        View layout = inflater.inflate(R.layout.fragment_top, container, false);
        int num_saying;
        String[] top_aphorisms = getResources().getStringArray(R.array.aphorisms);
        num_saying = (int) (Math.random() * top_aphorisms.length);
        TextView aphorismView = (TextView) layout.findViewById(R.id.top_aphorisms);
        aphorismView.setText(top_aphorisms[num_saying]);

        return layout;
    }
}
