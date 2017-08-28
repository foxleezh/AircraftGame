package com.ispring.gameplane.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;

import java.util.List;

/**
 * 战斗机类，可以通过交互改变位置
 */
public class CombatAircraft extends Sprite {
    private boolean collide = false;//标识战斗机是否被击中
    private int bombAwardCount = 0;//可使用的炸弹数
    private Bitmap anim;

    public void setCrazyBitmap(Bitmap crazyBitmap) {
        this.crazyBitmap = crazyBitmap;
    }

    private Bitmap crazyBitmap;

    private int column = 14;//列
    private int row = 14;//排
    private int segment = 14;//总片段数
    private int currentSegment = 0;//当前动画片段
    private int currentWingsSegment = 0;//当前动画片段
    private int wingsSegment = 60;//当前动画片段
    private int frequency = 10;//每个爆炸片段绘制2帧

    private boolean isAnim;//是否执行动画
    private boolean turnover;//是否反序执行动画



    public void setFire(Bitmap fire) {
        this.fire = fire;

    }

    private Bitmap fire;

    public void setWingsLeft(Bitmap wingsLeft) {
        this.wingsLeft = wingsLeft;
    }

    private Bitmap wingsLeft;

    public void setWingsRight(Bitmap wingsRight) {
        this.wingsRight = wingsRight;
    }

    private Bitmap wingsRight;
    private RectF fireRect;

    private int lifeCount = 100;//生命数

    //双发子弹相关
    private boolean crazy ;//标识是否发的是单一的子弹
    private int bulletCount=2;
    private int doubleTime = 0;//当前已经用双子弹绘制的次数
    private int maxDoubleTime = 250;//使用双子弹最多绘制的次数

    //被撞击后闪烁相关
    private long beginFlushFrame = 0;//要在第beginFlushFrame帧开始闪烁战斗机
    private int flushTime = 0;//已经闪烁的次数
    private int flushFrequency = 16;//在闪烁的时候，每隔16帧转变战斗机的可见性
    private int maxFlushTime = 10;//最大闪烁次数


    public int getLifeCount() {
        return lifeCount;
    }

    public CombatAircraft(Bitmap anim,int column,int row){
        super(anim);
        this.anim=anim;
        this.column=column;
        this.row=row;
        this.segment=column*row;
        wingsSegment=getExplodeDurationFrame()/4;
        currentSegment =0;
        mXfermode=new PorterDuffXfermode(PorterDuff.Mode.ADD);
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            //确保战斗机完全位于Canvas范围内
            validatePosition(canvas);

            //每隔7帧发射子弹
            if(getFrame() % 3 == 0){
                fight(gameView);
            }
        }
    }

    private Xfermode mXfermode;

    @Override
    public void onDraw(Canvas canvas, Paint paint, GameView gameView) {
        if(getVisibility()) {
            /** 绘制喷火效果，将画笔设置为混色模式*/
            paint.setXfermode(mXfermode);
            Rect srcRect = getFireSrcRec();
            if (fireRect == null) {
                fireRect = new RectF();
            }
            fireRect.left = getX() + (getWidth() - srcRect.width()) / 2;
            fireRect.top = getY()-getHeight()/4;
            fireRect.right = fireRect.left + srcRect.width();
            fireRect.bottom = fireRect.top + srcRect.height();
            canvas.drawBitmap(fire, srcRect, fireRect, paint);

            if(isAnim||crazy){
                canvas.drawBitmap(wingsLeft, null, getWingDstRec(true), paint);
                canvas.drawBitmap(wingsRight, null, getWingDstRec(false), paint);
            }

            paint.setXfermode(null);


        }
        super.onDraw(canvas, paint, gameView);
    }

    //确保战斗机完全位于Canvas范围内
    private void validatePosition(Canvas canvas){
        if(getX() < 0){
            setX(0);
        }
        if(getY() < 0){
            setY(0);
        }
        RectF rectF = getRectF();
        int canvasWidth = canvas.getWidth();
        if(rectF.right > canvasWidth){
            setX(canvasWidth - getWidth());
        }
        int canvasHeight = canvas.getHeight();
        if(rectF.bottom > canvasHeight){
            setY(canvasHeight - getHeight());
        }
    }

    //发射子弹
    public void fight(GameView gameView){
        //如果战斗机被撞击了或销毁了，那么不会发射子弹
        if(collide || isDestroyed()){
            return;
        }

        float y = getY() - 5;

        float offset = getWidth() / 4;
        float offsetDegree = 0.15f;
        float x= getX() + getWidth() / 2 - (offset * (bulletCount-1) / 2f);



        float degree=0;

        /** 进入疯狂状态*/
        if(crazy){
            float degree1=0;
            int crazyBulletCount=6;
            float offset1=getWidth()/16;
            float x1= getX() + getWidth() / 2 - (offset1 * (crazyBulletCount-1) / 2f);
            float y1= getY();

            /** 疯狂状态发射高级子弹*/
            for (int i = 0; i < crazyBulletCount; i++) {
                degree1= (float) (3*Math.PI/2-offsetDegree*(crazyBulletCount-1)/2f)+i*offsetDegree;
                Bullet crazyBullet = new Bullet(crazyBitmap,degree1);
                crazyBullet.setHurt(4);
                crazyBullet.setSpeed(100);
                crazyBullet.moveTo(x1+i*offset1-crazyBullet.getWidth()/2, y1);
                gameView.addSprite(crazyBullet);
            }
        }

        for (int i = 0; i < bulletCount; i++) {
            if(bulletCount<6){
                degree= (float) (3*Math.PI/2);
            }else if(bulletCount<8){
                degree= (float) (3*Math.PI/2-offsetDegree*(bulletCount-1)/2f)+i*offsetDegree;
            }else {
                if(i>1&i<6){
                    degree= (float) (3*Math.PI/2);
                }else {
                    degree= (float) (3*Math.PI/2-offsetDegree*(bulletCount-1)/2f)+i*offsetDegree;
                }
            }
            Bitmap yellowBulletBitmap = gameView.getYellowBulletBitmap();
            Bullet yellowBullet = new Bullet(yellowBulletBitmap,degree);
            /** 疯狂状态子弹伤害升级*/
            yellowBullet.setHurt(crazy?2:1);
            yellowBullet.moveTo(x+i*offset-yellowBullet.getWidth()/2, y);
            gameView.addSprite(yellowBullet);
        }
        if(bulletCount==8){
            Bitmap yellowBulletBitmap0 = gameView.getYellowBulletBitmap();
            Bullet yellowBullet0 = new Bullet(yellowBulletBitmap0, (float) Math.PI);
            yellowBullet0.moveTo(x+0*offset, y);
            gameView.addSprite(yellowBullet0);
            Bitmap yellowBulletBitmap8 = gameView.getYellowBulletBitmap();
            Bullet yellowBullet8 = new Bullet(yellowBulletBitmap8,0);
            yellowBullet8.moveTo(x+7*offset, y);
            gameView.addSprite(yellowBullet8);
        }
    }

    //战斗机如果被击中，执行爆炸效果
    //具体来说，首先隐藏战斗机，然后创建爆炸效果，爆炸用28帧渲染完成
    //爆炸效果完全渲染完成后，爆炸效果消失
    //然后战斗机会进入闪烁模式，战斗机闪烁一定次数后销毁
    protected void afterDraw(Canvas canvas, Paint paint, GameView gameView){
        if(isDestroyed()){
            return;
        }

        //在飞机当前还没有被击中时，要判断是否将要被敌机击中
        if(!collide){
            List<EnemyPlane> enemies = gameView.getAliveEnemyPlanes();
            for(EnemyPlane enemyPlane : enemies){
                Point p = getSelfCollidePointWithOther(enemyPlane);
                if(p != null){
                    enemyPlane.explode(gameView);
                    //p为战斗机与敌机的碰撞点，如果p不为null，则表明战斗机被敌机击中
                    lifeCount-=enemyPlane.getHurt();
                    bulletCount--;
                    if(bulletCount<1){
                        bulletCount=1;
                    }
                    if(lifeCount<0){
                        lifeCount=0;
                    }
                    if(lifeCount==0) {
//                        explode(gameView);
                    }
                    break;
                }
            }
            List<EnemyPlaneBullet> bullets = gameView.getEnemyPlaneBullet();
            for(EnemyPlaneBullet enemyPlane : bullets){
                Point p = getSelfCollidePointWithOther(enemyPlane);
                if(p != null){
                    enemyPlane.destroy();
                    lifeCount-=enemyPlane.getHurt();
                    bulletCount--;
                    if(bulletCount<1){
                        bulletCount=1;
                    }
                    //p为战斗机与敌机的碰撞点，如果p不为null，则表明战斗机被敌机击中
                    if(lifeCount<0){
                        lifeCount=0;
                    }
                    if(lifeCount==0) {
//                        explode(gameView);
                    }
                    break;
                }
            }
        }

        //beginFlushFrame初始值为0，表示没有进入闪烁模式
        //如果beginFlushFrame大于0，表示要在第如果beginFlushFrame帧进入闪烁模式
        if(beginFlushFrame > 0){
            long frame = getFrame();
            //如果当前帧数大于等于beginFlushFrame，才表示战斗机进入销毁前的闪烁状态
            if(frame >= beginFlushFrame){
                if((frame - beginFlushFrame) % flushFrequency == 0){
                    flushTime++;
                    if(flushTime >= maxFlushTime){
                        //如果战斗机闪烁的次数超过了最大的闪烁次数，那么销毁战斗机
                        destroy();
                        //Game.gameOver();
                    }
                }
            }
        }

        //在没有被击中的情况下检查是否获得了道具
        if(!collide){
            //检查是否获得炸弹道具
            List<BombAward> bombAwards = gameView.getAliveBombAwards();
            for(BombAward bombAward : bombAwards){
                Point p = getCollidePointWithOther(bombAward);
                if(p != null){
                    bombAwardCount++;
                    bombAward.destroy();
                    //Game.receiveBombAward();
                }
            }

            //检查是否获得子弹道具
            List<BulletAward> bulletAwards = gameView.getAliveBulletAwards();
            for(BulletAward bulletAward : bulletAwards){
                Point p = getCollidePointWithOther(bulletAward);
                if(p != null){
                    bulletAward.destroy();
                    bulletCount++;
                    upgrade(gameView);
                    if(bulletCount>5){
//                        upgrade(gameView);
                        bulletCount=5;
                    }
//                    crazy = false;
//                    doubleTime = 0;
                }
            }
        }

        if(isAnim){
            if(turnover) {
                currentWingsSegment--;
                if(currentWingsSegment<=0){
                    currentWingsSegment=0;
                }
            }else {
                currentWingsSegment++;
                if(currentWingsSegment>=wingsSegment){
                    currentWingsSegment=wingsSegment-1;
                }
            }
            if(getFrame() % frequency == 0){
                //level自加1，用于绘制下个爆炸片段
                if(turnover){
                    currentSegment--;
                    if (currentSegment <=0) {
                        currentSegment =0;
                        currentWingsSegment=0;
                        isAnim=false;
                    }
                }else {
                    currentSegment++;
                    if (currentSegment >= segment) {
                        isAnim=false;
                        currentSegment =segment-1;
                        currentWingsSegment=wingsSegment-1;
                    }
                }

            }
        }

        if(crazy) {
            doubleTime++;
            if (doubleTime >= maxDoubleTime) {
                crazy = false;
                downgrade(gameView);
                doubleTime = 0;
            }
        }
    }

    //战斗机爆炸
    private void explode(GameView gameView){
        if(!collide){
            collide = true;
            setVisibility(false);
            float centerX = getX() + getWidth() / 2;
            float centerY = getY() + getHeight() / 2;
            AnimSprite explosion = new AnimSprite(gameView.getExplosionBitmap(),14,1);
            explosion.centerTo(centerX, centerY);
            explosion.setFrequency(10);
            gameView.addSprite(explosion);
            beginFlushFrame = getFrame() + explosion.getExplodeDurationFrame();
        }
    }

    //战斗机升级
    private void upgrade(GameView gameView){
        crazy =true;
        doubleTime=0;
        isAnim=true;
        turnover=false;
    }

    //战斗机降级
    private void downgrade(GameView gameView){
        isAnim=true;
        turnover=true;
    }

    //获取可用的炸弹数量
    public int getBombCount(){
        return bombAwardCount;
    }

    //战斗机使用炸弹
    public void bomb(GameView gameView){
        if(collide || isDestroyed()){
            return;
        }

        if(bombAwardCount > 0){
            List<EnemyPlane> enemyPlanes = gameView.getAliveEnemyPlanes();
            for(EnemyPlane enemyPlane : enemyPlanes){
                enemyPlane.explode(gameView);
            }
            List<EnemyPlaneBullet> bullets = gameView.getEnemyPlaneBullet();
            for(EnemyPlaneBullet bullet : bullets){
                bullet.destroy();
            }
            bombAwardCount--;
        }
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
        int left = (int)(currentSegment %column * getWidth());
        int top = (int)(currentSegment /column * getHeight());
        rect.offsetTo(left, top);
        return rect;
    }


    public RectF getWingDstRec(boolean isleft){

        float rate=((float)currentWingsSegment)/wingsSegment;
        float rate1=2;
        if(isleft) {
            fireRect.left = getX() - getWidth() / 4 + getWidth() / 2 * (1 - rate * rate1);
            fireRect.top = getY() + getHeight() / 2 * (1 - rate * rate1);
        }else {
            fireRect.left = getX() + getWidth() / 4 + getWidth() / 2 * (1 - rate * rate1);
            fireRect.top = getY() + getHeight() / 2 * (1 - rate * rate1);
        }
        fireRect.right=fireRect.left+getWidth()*rate*rate1;
        fireRect.bottom=fireRect.top+getHeight()*rate*rate1;
        return fireRect;
    }

    //
    public Rect getFireSrcRec() {
        Rect rect =new Rect();

        int time=getFrame()/6;


        rect.left = (int)((time % 2)* (fire.getWidth()/2f));
        rect.top = 0;
        rect.right = rect.left+(int) (fire.getWidth()/2f);
        rect.bottom = fire.getHeight();
        return rect;
    }

    //得到绘制完整爆炸效果需要的帧数，即28帧
    public int getExplodeDurationFrame(){
        return segment * frequency;
    }

    public boolean isCollide(){
        return collide;
    }

    public void setNotCollide(){
        collide = false;
    }
}