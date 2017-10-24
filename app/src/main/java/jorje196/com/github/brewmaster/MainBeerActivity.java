package jorje196.com.github.brewmaster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainBeerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_beer);
        // получаем ресурс
        String[] top_aphorisms = getResources().getStringArray(R.array.aphorisms);
        // выбираем присказку
        int num_saying;
        num_saying = (int)(Math.random()*top_aphorisms.length);
        TextView aphorismView = (TextView) findViewById(R.id.top_aphorism);
        aphorismView.setText(top_aphorisms[num_saying]);

    }
}
