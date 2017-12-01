package jorje196.com.github.brewmaster;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by User on 30.11.2017.
 * Фрагмент с описанием охмеленного экстракта
 */

public class MaltExtDescriptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //
        View layout = inflater.inflate(R.layout.fragment_malt, container, false);

        String[] top_aphorisms = getResources().getStringArray(R.array.aphorisms);

        TextView aphorismView = (TextView) layout.findViewById(R.id.top_aphorisms);
        aphorismView.setText(top_aphorisms[1]);

        return layout;
    }
}
