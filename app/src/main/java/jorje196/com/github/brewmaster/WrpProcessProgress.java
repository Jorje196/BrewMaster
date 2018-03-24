package jorje196.com.github.brewmaster;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

//import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Created by User on 20.03.2018.
 * Класс обеспечивает отображение отображение состояния процесса через ProgressBar'ы ,
 * привязан к шаблону фрагмента детализации варки, создав собственный шаблон его можно
 * и отвязать, но пока нет нужды.
 */

class WrpProcessProgress {
    private final int SCALE_MIN = 0;
    private final int SCALE_OPT =70;
    private final int SCALE_MAX = 100;
    WrpProcessProgress(Context context, View parent, int progressFirst, int progressSecond, String text){
        ProgressBar processProgressBar = (ProgressBar) parent.findViewById(R.id.process_progress);
        TextView processProgBarText = (TextView) parent.findViewById(R.id.process_text);
        processProgressBar.setMax(SCALE_MAX);

        processProgressBar.setProgress(progressFirst);
        processProgressBar.setSecondaryProgress(progressSecond);
        processProgBarText.setText(text);
        if (progressSecond < SCALE_OPT) processProgBarText.setTextColor(context.getResources()
                .getColor(android.R.color.primary_text_dark));
        //processProgBarText.setTextSize(COMPLEX_UNIT_DIP ,10);
        //x = processProgBarText.getTextSize();
        processProgressBar.setVisibility(View.VISIBLE);

        processProgBarText.setVisibility(View.VISIBLE);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  неактуально
        //    processProgBarText.setTextAppearance(R.style.autoscroll);
        // else processProgBarText.setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
