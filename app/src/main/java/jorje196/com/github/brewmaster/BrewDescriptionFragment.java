package jorje196.com.github.brewmaster;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Работает с описанием выбранной варки (заполнение, коррекция, подтверждение удаления
 */
public class BrewDescriptionFragment extends Fragment {
    static final String BDF_TAG = "brewDescription";    // тег фрагмента

    public BrewDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_brew, container, false);
        int brewId = 0; // пока случай новой варки
        BrewProcess brewProcess = new BrewProcess(brewId);

        // Inflate the layout for this fragment
        return layout;
    }

}
