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

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class CountActivity extends AppCompatActivity {

    private int stepCounter = 0;
    private int toDaystep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        setStepCounter();
        //moveGraph();
        moveGame();
    }

    //歩数計センサー　歩数カウント部分
    private void setStepCounter() {
        //センサーの取得
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                stepCounter++;
                toDaystep++;
                TextView test = findViewById(R.id.TestText);
                test.setText("event : " + stepCounter + " / count : " + sensorEvent);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        }, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        //歩数の類型をsharedPreferenceに保存
        super.onStop();
        SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int sharedCount = stepCounter + gameData.getInt("step", 0);
        int sharedToDay = toDaystep + gameData.getInt("toDay", 0);
        SharedPreferences.Editor editor = gameData.edit();
        editor.putInt("step", sharedCount);
        editor.putInt("toDay", sharedToDay);
        stepCounter = 0;
    }

    private void moveGame() {
        Button game = findViewById(R.id.gameButton);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
    /*
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
     */
}
