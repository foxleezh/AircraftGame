package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 子弹类，从下向上沿直线移动
 */
public class Bullet extends CurveSprite {

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    private int hurt=1;

    public Bullet(Bitmap bitmap,float degreen){
        super(bitmap);
        setSpeed(30);//负数表示子弹向上飞
        setDegree(degreen);
    }

}