package jp.ac.shohoku.s19b703.shibuyakai

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

//ゲームメイン画面
//MyuKato
class GameActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    var x = "gif.gif"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        moveExpo()
        moveCount()
        //displayCharacter();
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(application, MonsterLabo::class.java)
        startService(intent)
    }

    // Gif表示
    private fun displayCharacter() {
        val gameData = getSharedPreferences("gameData", Context.MODE_PRIVATE)
        val total = gameData.getInt("MonStep", 0)
        val character = findViewById<ImageView>(R.id.characterView)
        var flg = gameData.getInt("flg", 0) //0:しぶや 1:ただ 2:かとう
        val rnd = Random()
        if (total > 600) {
            flg = rnd.nextInt(3)
            val editor = gameData.edit()
            editor.putInt("flg", flg)
            editor.putInt("MonStep", 0)
            editor.apply()
            createDialog()
        }
        when (flg) {
            0 ->
                if      (total < 100) x = "s00.gif"
                else if (total < 200) x = "s01.gif"
                else if (total < 300) x = "s02.gif"
                else if (total < 400) x = "s03.gif"
                else                  x = "s04.gif"

            1 ->
                if      (total < 100) x = "t00.gif"
                else if (total < 200) x = "t01.gif"
                else if (total < 300) x = "t02.gif"
                else if (total < 400) x = "t03.gif"
                else                  x = "t04.gif"

            2 ->
                if      (total < 100) x = "s00.gif"
                else if (total < 200) x = "s01.gif"
                else if (total < 300) x = "s02.gif"
                else if (total < 400) x = "s03.gif"
                else                  x = "s04.gif"

            else -> { }
        }
        getGifAnimationDrawableLessThanP()
    }

    private fun moveCount() {
        val count = findViewById<Button>(R.id.countButton)
        count.setOnClickListener {
            val intent = Intent(this@GameActivity, CountActivity::class.java)
            this@GameActivity.startActivity(intent)
        }
    }

    private fun moveExpo() {
        val expo = findViewById<Button>(R.id.expoButton)
        val count = findViewById<Button>(R.id.countButton)
        expo.setOnClickListener {
            val fragmentManager = this@GameActivity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // BackStackを設定
            fragmentTransaction.addToBackStack(null)

            // パラメータを設定
            fragmentTransaction.replace(R.id.expo, ExpoFragment.newInstance())
            fragmentTransaction.commit()
            expo.visibility = View.INVISIBLE
            count.visibility = View.INVISIBLE
        }
    }

    fun createDialog() {
        val dialog = AlertDialog.Builder(this@GameActivity)
        dialog.setMessage("成長したモンスターは旅立っていったよ\n新しいモンスターを育てよう!")
        dialog.setPositiveButton("そだてる", null)
        dialog.create().show()
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent(application, MonsterLabo::class.java)
        stopService(intent)
    }

      private fun getGifAnimationDrawableLessThanP(): CustomAnimatedDrawable {
        val inputStream = assets.open(x)
        return CustomAnimatedDrawable(inputStream)
    }

}