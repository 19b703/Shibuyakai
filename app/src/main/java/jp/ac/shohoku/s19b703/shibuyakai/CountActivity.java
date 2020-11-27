package jp.ac.shohoku.s19b703.shibuyakai;

//歩数計画面

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        moveGraph();
        moveGame();
    }
    private void moveGame() {
        Button game = findViewById(R.id.gameButton);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
    }
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
}
