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
    }

    //タップされたときに画面遷移
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //インテントの作成
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
        return super.onTouchEvent(event);
    }
}