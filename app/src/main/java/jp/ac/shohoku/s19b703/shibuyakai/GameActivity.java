package jp.ac.shohoku.s19b703.shibuyakai;

//ゲームメイン画面

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        moveExpo();
        moveCount();
    }
    private void moveCount() {
        Button count = findViewById(R.id.countButton);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this,CountActivity.class);
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
                fragmentTransaction.replace(R.id.expo,ExpoFragment.newInstance());
                fragmentTransaction.commit();

                expo.setVisibility(View.INVISIBLE);
                count.setVisibility(View.INVISIBLE);
            }
        });
    }

}
