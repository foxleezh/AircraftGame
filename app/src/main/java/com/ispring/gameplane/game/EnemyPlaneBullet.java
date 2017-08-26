package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 大敌机类，体积大，抗打击能力强
 */
public class EnemyPlaneBullet extends CurveSprite {

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    private int hurt;

    public EnemyPlaneBullet(Bitmap bitmap,int hurt){
        super(bitmap);
        this.hurt=hurt;
    }
}