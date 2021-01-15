package jp.ac.shohoku.s19b703.shibuyakai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

//タイトル画面

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        startService(intent);
    }

    //タップされたときに画面遷移
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //インテントの作成
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        stopService(intent);
    }

}