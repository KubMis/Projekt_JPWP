package com.example.projektjpwp;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;



public class GameView extends SurfaceView implements Runnable {
private boolean isPlaying=true;
private Thread thread;

private  Paint paint;

private  Background background1;
private  Background background2;
private  int width_X=0;
private  int height_y=0;
private final Control control;
private  List<Bullet> bulletList;
public static float screenRatioX;
public static float screenRatioY;


    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        screenRatioY=720f/screenY;
        screenRatioX=1280f/screenX;

        this.width_X=screenX;
        this.height_y=screenY;

        bulletList=new ArrayList<>();

        background1=new  Background(width_X,height_y,getResources());
        background2=new Background(width_X,height_y,getResources());

        control=new Control(this ,screenY,getResources());

        background2.x=screenX;



        paint=new Paint();

    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    private void update() {

        background1.x-=10*screenRatioX;
        background2.x-=10*screenRatioX;

        if (background1.x + background1.background.getWidth() < 0){
            background1.x=width_X;
        }

        if (background2.x + background2.background.getWidth() < 0){
            background2.x=width_X;
        }

        if(control.goUp){
            control.Y-=30*screenRatioY;

        }else
            control.Y+=30*screenRatioY;
        if(control.Y<0){
            control.Y=0;
        }
        if(control.Y>=height_y-control.height){
            control.Y=height_y-control.height;
        }

        List<Bullet>usedBullets=new ArrayList<>();

        for(Bullet bullet : bulletList) {
            if (bullet.x > width_X) {
                usedBullets.add(bullet);
            }
            bullet.x += 50 * screenRatioX;
        }

        for(Bullet bullet : bulletList){
                usedBullets.remove(bullet);
        }
    }
    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas canvas=getHolder().lockCanvas();

            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            canvas.drawBitmap(control.getControl(),control.X, control.Y,paint);

            for(Bullet bullet : bulletList){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    public void start(){
        thread=new Thread(this);
        thread.start();
    }

    public void stop()  {
        try{
             isPlaying = false;
             thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<(width_X/2)){
                    control.goUp=true;}
                break;

            case MotionEvent.ACTION_UP:
                    control.goUp=false;
                    if (event.getX() > (width_X / 2)){
                        control.shot++;}
                    break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = control.X + control.width;
        bullet.y = control.Y + (control.height / 2);
        bulletList.add(bullet);
    }
}
