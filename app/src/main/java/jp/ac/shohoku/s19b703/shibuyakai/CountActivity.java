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
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CountActivity extends AppCompatActivity implements SensorEventListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

        moveGame();
        resetTotal();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        TextView CountAll = findViewById(R.id.AllStep);
        TextView CountDay = findViewById(R.id.DayStep);
        SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int AllStep = gameData.getInt("AllStep", 0);
        int DayStep = gameData.getInt("DayStep", 0);
        CountAll.setText(AllStep + "歩");
        CountDay.setText(DayStep + "歩");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //精度が変更されたとき
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        startService(intent);

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
    }

    @Override
    protected void onStop(){
        super.onStop();
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        stopService(intent);
    }

    private void moveGame() {
        Button game = findViewById(R.id.gameButton);
        game.setOnClickListener(v -> {
            Intent intent = new Intent(CountActivity.this, GameActivity.class);
            startActivity(intent);
        });

    }

    private void resetTotal() {
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(v -> {
            SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = gameData.edit();
            editor.putInt("AllStep", 0);
            TextView CountAll = findViewById(R.id.AllStep);
            CountAll.setText("0歩");
        });
    }
}
