package com.ispring.gameplane.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 炸弹奖励
 */
public class BombAward extends Award {

    public BombAward(Bitmap bitmap, int column , int row){
        super(bitmap,column,row);
        setSpeed(2);
//        setDegree((float) (Math.random()*2*Math.PI));
        setDegree((float) (Math.PI/4));
        setFrequency(6);
        setChangeOritation(false);
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        if(getX()+getWidth()/2>canvas.getWidth()||getX()+getWidth()/2<0){
            setDegree((float) (Math.PI-getDegree()));
        }
        else if(getY()+getHeight()/2>canvas.getHeight()){
            setDegree((float) (Math.PI*2-getDegree()));
        }else if(getY()+getHeight()/2<0&&getFrame()>20){
            setDegree((float) (Math.PI*2-getDegree()));
        }
        super.beforeDraw(canvas, paint, gameView);
    }

}