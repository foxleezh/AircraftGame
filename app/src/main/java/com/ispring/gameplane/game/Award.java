package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 奖品
 */
public class Award extends AnimSprite {
    public static int STATUS_DOWN1 = 1;
    public static int STATUS_UP2 = 2;
    public static int STATUS_DOWN3 = 3;

    private int status = STATUS_DOWN1;

    public Award(Bitmap bitmap, int column , int row){
        super(bitmap,column,row);
        setRepeat(true);
    }

//    @Override
//    protected void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
//        //在afterDraw中不调用super.afterDraw方法
//        if(!isDestroyed()){
//            //在绘制一定次数后要改变方向或速度
//            int canvasHeight = canvas.getHeight();
//            if(status != STATUS_DOWN3){
//                float maxY = getY() + getHeight();
//                if(status == STATUS_DOWN1){
//                    //第一次向下
//                    if(maxY >= canvasHeight * 0.25){
//                        //当第一次下降到临界值时改变方向，向上
//                        setSpeed(-5);
//                        status = STATUS_UP2;
//                    }
//                }
//                else if(status == STATUS_UP2){
//                    //第二次向上
//                    if(maxY+this.getSpeed() <= 0){
//                        //第二次上升到临界值时改变方向，向下
//                        setSpeed(13);
//                        status = STATUS_DOWN3;
//                    }
//                }
//            }
//            if(status == STATUS_DOWN3){
//                if(getY() >= canvasHeight){
//                    destroy();
//                }
//            }
//        }
//    }
}