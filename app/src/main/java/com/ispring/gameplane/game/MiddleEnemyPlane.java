package com.ispring.gameplane.game;

import android.graphics.Bitmap;

import com.ispring.gameplane.R;

/**
 * 中敌机类，体积中等，抗打击能力中等
 */
public class MiddleEnemyPlane extends EnemyPlane {

    public MiddleEnemyPlane(Bitmap bitmap,int level){
        super(bitmap,level);
        setPower(8);//中敌机抗抵抗能力为4，即需要4颗子弹才能销毁中敌机
        setValue(6000);//销毁一个中敌机可以得6000分
        setBulletCount(1);
        setHurt(30);
    }

    @Override
    public void fight(GameView gameView) {
        if(getFrame()%100==0||(level>2&&getFrame()==10)) {
            float degree = (float) Util.CalulateXYAnagle(getX() + getWidth() / 2, -(getY() + getHeight() / 2),
                    gameView.getCombatAircraft().getX() + gameView.getCombatAircraft().getWidth() / 2,
                    -(gameView.getCombatAircraft().getY() + gameView.getCombatAircraft().getHeight() / 2));
            CurveSprite sprite = new EnemyPlaneBullet(gameView.bitmaps.get(R.drawable.enemybullet1),15);
            sprite.setX(getX());
            sprite.setY(getY());
            sprite.setDegree(degree);
            gameView.addSprite(sprite);
            bulletCount--;
        }
    }

}