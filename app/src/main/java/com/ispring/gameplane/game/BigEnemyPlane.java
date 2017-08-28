package com.ispring.gameplane.game;

import android.graphics.Bitmap;

import com.ispring.gameplane.R;

/**
 * 大敌机类，体积大，抗打击能力强
 */
public class BigEnemyPlane extends EnemyPlane {

    public BigEnemyPlane(Bitmap bitmap,int level){
        super(bitmap,level);
        setPower(10);//大敌机抗抵抗能力为10，即需要10颗子弹才能销毁大敌机
        setValue(30000);//销毁一个大敌机可以得30000分
        setBulletCount(1);
        setHurt(50);
    }

    @Override
    public void fight(GameView gameView) {
        if(getFrame()%100==0||(level>2&&getFrame()==20)) {
//            float degree = (float) Util.CalulateXYAnagle(getX() + getWidth() / 2, -(getY() + getHeight() / 2),
//                    gameView.getCombatAircraft().getX() + gameView.getCombatAircraft().getWidth() / 2,
//                    -(gameView.getCombatAircraft().getY() + gameView.getCombatAircraft().getHeight() / 2));
            int size=15;
            for (int i = 0; i < size; i++) {
                CurveSprite sprite = new EnemyPlaneBullet(gameView.bitmaps.get(R.drawable.enemybullet1),20);
                sprite.setX(getX()+getWidth()/2);
                sprite.setY(getY()+getHeight()/2);
                sprite.setDegree((float) (2*Math.PI*i/size));
                sprite.setSpeed(2f);
                gameView.addSprite(sprite);
            }
            bulletCount--;
        }
    }

}