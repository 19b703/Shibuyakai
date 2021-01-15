package jp.ac.shohoku.s19b703.shibuyakai;

//ゲームメイン画面

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        moveExpo();
        moveCount();
        displayCharacter();

    }

    private void displayCharacter() {
        SharedPreferences gameData = GameActivity.this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int total = gameData.getInt("AllStep", 0);
        ImageView character = findViewById(R.id.characterView);
        int flg = gameData.getInt("flg", 0); //0:しぶや 1:ただ 2:かとう

        Random rnd = new Random();
        if (total > 600) {
            flg = rnd.nextInt(3);
            SharedPreferences.Editor editor = gameData.edit();
            editor.putInt("flg", flg);
            editor.putInt("AllStep",0);
            editor.apply();
            createDialog();
            total = 200;
        }

        switch (flg) {
            case 0:
                if (total < 100) character.setImageResource(R.drawable.case1);
                else if (total < 200) character.setImageResource(R.drawable.s2);
                else if (total < 300) character.setImageResource(R.drawable.s3);
                else if (total < 400) character.setImageResource(R.drawable.s4);
                else character.setImageResource(R.drawable.s5);
                break;
            case 1:
                if (total < 100) character.setImageResource(R.drawable.case1);
                else if (total < 200) character.setImageResource(R.drawable.t2);
                else if (total < 300) character.setImageResource(R.drawable.t3);
                else if (total < 400) character.setImageResource(R.drawable.t4);
                else character.setImageResource(R.drawable.t5);
                break;
            case 2:
                if (total < 100) character.setImageResource(R.drawable.case1);
                else if (total < 200) character.setImageResource(R.drawable.k2);
                else if (total < 300) character.setImageResource(R.drawable.k3);
                else if (total < 400) character.setImageResource(R.drawable.k4);
                else character.setImageResource(R.drawable.k5);
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
                startActivity(intent);
            }
        });
    }

    private void moveExpo() {
        final Button expo = findViewById(R.id.expoButton);
        final Button count = findViewById(R.id.countButton);
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
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

}
