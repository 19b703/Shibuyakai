package jp.ac.shohoku.s19b703.shibuyakai;

//歩数カウント（バックグラウンド処理）
//MyuKato

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class MonsterLabo extends Service implements SensorEventListener {

    boolean first = true;
    boolean up = false;
    float d0, d = 0f;
    float a = 0.6f;

    public MonsterLabo() {
    }

    @Override
    public void onCreate(){
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] value = sensorEvent.values;

        float sum = (float) Math.sqrt(Math.pow(value[0], 2) + Math.pow(value[1], 2) + Math.pow(value[2], 2));

        SharedPreferences gameData = MonsterLabo.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int AllStep = gameData.getInt("AllStep", 0);
        int DayStep = gameData.getInt("DayStep", 0);
        int MonStep = gameData.getInt("MonStep", 0);

        if (first) {
            first = false;
            up = true;
            d0 = a * sum;
        } else {
            //ローパスフィルタリング 時系列の細かいデータを平滑化
            d = a * sum + (1 - a) * d0;
            if (up && d < d0) {
                up = false;
                AllStep++;
                DayStep++;
                MonStep++;
            } else if (!up && d > d0) {
                up = true;
                d0 = d;
            }
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = gameData.edit();
            editor.putInt("AllStep", AllStep);
            editor.putInt("DayStep", DayStep);
            editor.putInt("MonStep", 0);

            editor.apply();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
