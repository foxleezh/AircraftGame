package com.ispring.gameplane.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * 动画效果类
 */
public class AnimSprite extends CurveSprite {

    private int segment = 14;//总片段数
    private int column = 14;//列
    private int row = 14;//排
    private int level = 0;//最开始处于动画的第0片段

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    private boolean repeat;

    public void setFrequency(int explodeFrequency) {
        this.frequency = explodeFrequency;
    }

    private int frequency = 2;//每个爆炸片段绘制2帧

    public AnimSprite(Bitmap bitmap, int column , int row){
        super(bitmap);
        this.column=column;
        this.row=row;
        this.segment=column*row;
        setSpeed(0);
    }

    @Override
    public float getHeight() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getHeight() / row;
        }
        return 0;
    }

    @Override
    public float getWidth() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getWidth() / column;
        }
        return 0;
    }

    @Override
    public Rect getBitmapSrcRec() {
        Rect rect = super.getBitmapSrcRec();
        int left = (int)(level%column * getWidth());
        int top = (int)(level/column * getHeight());
        rect.offsetTo(left, top);
        Log.d("foxlee++++++++++","letf="+left+" top="+top);
        return rect;
    }

    @Override
    protected void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            if(getFrame() % frequency == 0){
                //level自加1，用于绘制下个爆炸片段
                level++;
                if (level >= segment) {
                    //当绘制完所有的爆炸片段后，销毁爆炸效果
                    if (repeat) {
                        level=0;
                    } else {
                        destroy();
                    }
                }
            }
        }
    }

    //得到绘制完整爆炸效果需要的帧数，即28帧
    public int getExplodeDurationFrame(){
        return segment * frequency;
    }
}