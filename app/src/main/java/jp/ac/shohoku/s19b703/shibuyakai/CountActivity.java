package jp.ac.shohoku.s19b703.shibuyakai;

//歩数計画面

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CountActivity extends AppCompatActivity {
    //センサーの取得
    private SensorManager sensorManager;
    private Sensor sensor;

    private int stepCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                stepCounter = (int) sensorEvent.values[0];
                TextView test = findViewById(R.id.TestText);
                test.setText(stepCounter);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        },sensor, SensorManager.SENSOR_DELAY_FASTEST);

        moveGraph();
        moveGame();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences stepData = CountActivity.this.getSharedPreferences("stepData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stepData.edit();
        editor.putInt("step",stepCounter);
    }
    private void moveGame() {
        Button game = findViewById(R.id.gameButton);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
    }
    private void moveGraph() {
        final Button graph = findViewById(R.id.graphButton);
        final Button game = findViewById(R.id.gameButton);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                // パラメータを設定
                fragmentTransaction.replace(R.id.graph,GraphFragment.newInstance());
                fragmentTransaction.commit();

                graph.setVisibility(View.INVISIBLE);
                game.setVisibility(View.INVISIBLE);
            }
        });
    }
}
