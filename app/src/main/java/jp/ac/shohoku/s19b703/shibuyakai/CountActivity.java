package jp.ac.shohoku.s19b703.shibuyakai;

//歩数計画面
//MyuKato

import android.annotation.SuppressLint;
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

import java.util.Calendar;

public class CountActivity extends AppCompatActivity implements SensorEventListener {

    boolean first = true;
    boolean up = false;
    float d0, d = 0f;
    int stepcount;
    float a = 0.6f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

        moveGame();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value[] = sensorEvent.values;
        TextView CountAll = findViewById(R.id.AllStep);
        TextView CountDay = findViewById(R.id.DayStep);

        float sum = (float) Math.sqrt(Math.pow(value[0], 2) + Math.pow(value[1], 2) + Math.pow(value[2], 2));

        if (first) {
            first = false;
            up = true;
            d0 = a * sum;
        } else {
            //ローパスフィルタリング 時系列の細かいデータを平滑化
            d = a * sum + (1 - a) * d0;
            if (up && d < d0) {
                up = false;
                stepcount++;
            } else if (!up && d > d0) {
                up = true;
                d0 = d;
            }
            SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
            int AllStep = gameData.getInt("AllStep", 0);
            int DayStep = gameData.getInt("DayStep", 0);
            int AllSteps = stepcount + AllStep;
            int DaySteps = stepcount + DayStep;
            CountAll.setText(AllSteps + "歩");
            CountDay.setText(DaySteps + "歩");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //精度が変更されたとき
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar toDay = Calendar.getInstance();
        int year = toDay.get(Calendar.YEAR);
        int month = toDay.get(Calendar.MONTH) + 1;
        int day = toDay.get(Calendar.DATE);

        SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gameData.edit();
        int oldY = gameData.getInt("YEAR", 0);
        int oldM = gameData.getInt("MONTH", 0);
        int oldD = gameData.getInt("DAY", 0);

        TextView test = findViewById(R.id.test);
        test.setText(oldY + "/" + oldM + "/" + oldD);

        if (!(year == oldY && month == oldM && day == oldD)) {
            editor.putInt("DayStep", 0);
            editor.putInt("YEAR", year);
            editor.putInt("MONTH", month);
            editor.putInt("DAY", day);
            editor.apply();
        }
        stepcount = 0;
    }

    @Override
    protected void onStop() {
        //歩数の類型をsharedPreferenceに保存
        super.onStop();
        SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int sharedCount = stepcount + gameData.getInt("AllStep", 0);
        int toDayCount = stepcount + gameData.getInt("DayStep", 0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = gameData.edit();
        editor.putInt("AllStep", sharedCount);
        editor.putInt("DayStep", toDayCount);
        editor.apply();
        stepcount = 0;
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
}
