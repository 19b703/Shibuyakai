package jp.ac.shohoku.s19b703.shibuyakai;

//ゲームメイン画面
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        reStart();
        moveExpo();
        moveCount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayCharacter();
    }

    private void displayCharacter() {
        SharedPreferences gameData = GameActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int total = gameData.getInt("MonStep", 0);
        ImageView character = findViewById(R.id.characterView);
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(character);
        int flg = gameData.getInt("flg", 0); //0:しぶや 1:ただ 2:かとう
        Random rnd = new Random();
        if (total > 600) {
            flg = rnd.nextInt(3);
            SharedPreferences.Editor editor = gameData.edit();
            editor.putInt("flg", flg);
            editor.putInt("MonStep", 0);
            editor.apply();
            total = 0;
            createDialog();
        }
        switch (flg) {
            case 0:
                if (total < 100) Glide.with(this).load(R.raw.s00).into(target);
                else if (total < 200) Glide.with(this).load(R.raw.s01).into(target);
                else if (total < 300) Glide.with(this).load(R.raw.s02).into(target);
                else if (total < 400) Glide.with(this).load(R.raw.s03).into(target);
                else Glide.with(this).load(R.raw.s04).into(target);
                break;
            case 1:
                if (total < 100) Glide.with(this).load(R.raw.t00).into(target);
                else if (total < 200) Glide.with(this).load(R.raw.t01).into(target);
                else if (total < 300) Glide.with(this).load(R.raw.t02).into(target);
                else if (total < 400) Glide.with(this).load(R.raw.t03).into(target);
                else Glide.with(this).load(R.raw.t04).into(target);
                break;
            case 2:
                if (total < 100) Glide.with(this).load(R.raw.k00).into(target);
                else if (total < 200) Glide.with(this).load(R.raw.k00).into(target);
                else if (total < 300) Glide.with(this).load(R.raw.k00).into(target);
                else if (total < 400) Glide.with(this).load(R.raw.k00).into(target);
                else Glide.with(this).load(R.raw.k00).into(target);
                break;
            default:
                break;
        }
    }

    private void moveCount() {
        Button count = findViewById(R.id.countButton);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, CountActivity.class);
                GameActivity.this.startActivity(intent);
            }
        });
    }

    private void moveExpo() {
        final Button expo = findViewById(R.id.expoButton);
        final Button count = findViewById(R.id.countButton);
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = GameActivity.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                // パラメータを設定
                fragmentTransaction.replace(R.id.expo, ExpoFragment.newInstance());
                fragmentTransaction.commit();

                expo.setVisibility(View.INVISIBLE);
                count.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void createDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
        dialog.setMessage("成長したモンスターは旅立っていったよ\n新しいモンスターを育てよう!");
        dialog.setPositiveButton("そだてる", null);
        dialog.create().show();
    }

    private void reStart() {
        Button restart = findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharacter();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(getApplication(), MonsterLabo.class);
        stopService(intent);
    }

}