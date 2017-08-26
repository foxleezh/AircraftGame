package com.ispring.gameplane.game;

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
}
