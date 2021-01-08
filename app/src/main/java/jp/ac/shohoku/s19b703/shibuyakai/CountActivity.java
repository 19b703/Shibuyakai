package jp.ac.shohoku.s19b703.shibuyakai;

//歩数計画面

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;


public class CountActivity extends AppCompatActivity implements SensorEventListener  {

    boolean first =true;
    boolean up = false;
    float d0,d=0f;
    int stepcount=0;
    float a=0.6f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);



        SensorManager sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);

        //setStepCounter();
        //moveGraph();
        moveGame();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value[] = sensorEvent.values;
        TextView test = findViewById(R.id.TestText);
        float sum=(float)Math.sqrt(Math.pow(value[0],2)+Math.pow(value[1],2)+Math.pow(value[2],2));

        if(first){
            first=false;
            up=true;
            d0=a*sum;
        }else{
            //ローパスフィルタリング 時系列の細かいデータを平滑化
            d=a*sum+(1-a)*d0;
            if(up&&d<d0){
                up=false;
                stepcount++;
            }else if(!up&& d>d0){
                up=true;
                d0=d;
            }
            SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
            int step = gameData.getInt("step", 0);
            int STEP = stepcount;
            test.setText(STEP+"歩");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //精度が変更されたとき
    }

    /*
    //歩数計センサー　歩数カウント部分
    private void setStepCounter() {
        //センサーの取得
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
        sensorManager.registerListener(new SensorEventListener() {
            @SuppressLint("SetTextI18n")
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

     */

    @Override
    protected void onStop() {
        //歩数の類型をsharedPreferenceに保存
        super.onStop();
        SharedPreferences gameData = CountActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int sharedCount = stepcount + gameData.getInt("step", 0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = gameData.edit();
        editor.putInt("step", sharedCount);
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
