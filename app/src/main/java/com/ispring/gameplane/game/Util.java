package com.ispring.gameplane.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2017/8/25.
 */

public class Util {


    public static double CalulateXYAnagle(double startx, double starty, double endx, double endy)
    {
        //除数不能为0
        double tan = Math.atan(Math.abs((endy - starty) / (endx - startx)));
        if (endx > startx && endy > starty)//第一象限
        {
            tan=-tan;
        }
        else if (endx > startx && endy < starty)//第二象限
        {
        }
        else if (endx < startx && endy > starty)//第三象限
        {
            tan=  tan - Math.PI;
        }
        else
        {
            tan=  Math.PI - tan;
        }
        return tan;
    }

    public static Bitmap createRepeater(int heightrate, Bitmap src) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight()*heightrate, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < heightrate; ++idx) {
            canvas.drawBitmap(src, 0, idx * src.getHeight(), null);
        }
        return bitmap;
    }

}
