package io.caster.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TimerView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = (TimerView)findViewById(R.id.timer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        timerView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        timerView.stop();
    }
}
