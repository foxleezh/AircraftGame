package com.ispring.gameplane;

import android.app.Activity;
import android.os.Bundle;

import com.ispring.gameplane.game.GameView;


public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView)findViewById(R.id.gameView);
        //0:combatAircraft
        //1:explosion
        //2:yellowBullet
        //3:blueBullet
        //4:smallEnemyPlane
        //5:middleEnemyPlane
        //6:bigEnemyPlane
        //7:bombAward
        //8:bulletAward
        //9:pause1
        //10:pause2
        //11:bomb
        int[] bitmapIds = {
                R.drawable.explosion,R.drawable.planebullet1,R.drawable.enemybullet1,R.drawable.small,
                R.drawable.middle,R.drawable.big,R.drawable.pause,R.drawable.main_bg,
                R.drawable.bomb,R.drawable.crazybullet1,R.drawable.plane1wing,R.drawable.plane1wing1
        };
        int[] animIds = {
                R.drawable.plane1body,R.drawable.plane1fire,R.drawable.prop3,R.drawable.prop7,
        };
        gameView.start(bitmapIds,animIds);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gameView != null){
            gameView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gameView != null){
            gameView.destroy();
        }
        gameView = null;
    }
}