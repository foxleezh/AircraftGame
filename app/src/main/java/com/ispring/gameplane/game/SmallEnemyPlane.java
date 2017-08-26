package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 小敌机类，体积小，抗打击能力低
 */
public class SmallEnemyPlane extends EnemyPlane {

    public SmallEnemyPlane(Bitmap bitmap,int level){
        super(bitmap, level);
        setPower(1);//小敌机抗抵抗能力为1，即一颗子弹就可以销毁小敌机
        setValue(1000);//销毁一个小敌机可以得1000分
        setBulletCount(0);
        setHurt(5);
    }

    @Override
    public void fight(GameView gameView) {
        if(getFrame()%70==0||(level>2&&getFrame()==10)) {
            float degree = (float) Util.CalulateXYAnagle(getX() + getWidth() / 2, -(getY() + getHeight() / 2),
                    gameView.getCombatAircraft().getX() + gameView.getCombatAircraft().getWidth() / 2,
                    -(gameView.getCombatAircraft().getY() + gameView.getCombatAircraft().getHeight() / 2));
            CurveSprite sprite = new EnemyPlaneBullet(gameView.bitmaps.get(3),1);
            sprite.setX(getX());
            sprite.setY(getY());
            sprite.setDegree(degree);
            gameView.addSprite(sprite);
            bulletCount--;
        }
    }


}