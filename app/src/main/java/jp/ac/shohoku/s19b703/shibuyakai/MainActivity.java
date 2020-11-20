package jp.ac.shohoku.s19b703.shibuyakai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

//タイトル画面

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        move();
    }

    private void move(){
        Button move = findViewById(R.id.test1);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),GameActivity.class);
                startActivity(intent);
            }
        });
    }
    /*
    //タップされたときに画面遷移
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //インテントの作成
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
        return true;
    }

     */
}