package com.example.projektjpwp;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class GameView extends SurfaceView implements Runnable {
private boolean isPlaying=true;
boolean gameOver=false;
private Thread thread;

private  Paint paint;

private  int currentScore=0;
private Random random;
private Asteroid[] asteroids;
private  Background background1;
private  Background background2;

private SharedPreferences preferences;
private  int screenX =0;
private  int screenY =0;
private final Control control;
private  List<Bullet> bulletList;
public static float screenRatioX;
public static float screenRatioY;

private GameActivity gameActivity;



    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.gameActivity = gameActivity;
        preferences= getContext().getSharedPreferences("Gra", Context.MODE_PRIVATE);
        screenRatioY=720f/screenY;
        screenRatioX=1280f/screenX;

        this.screenX =screenX;
        this.screenY =screenY;
        this.gameActivity=activity;




        background1=new  Background(this.screenX, this.screenY,getResources());
        background2=new Background(this.screenX, this.screenY,getResources());

        control=new Control(this ,screenY,getResources());

        bulletList=new ArrayList<>();
        background2.x=screenX;



        paint=new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.LTGRAY);

        asteroids=new Asteroid[2];
        for(int i=0;i<2;i++){
            Asteroid asteroid=new Asteroid(getResources());
            asteroids[i]=asteroid;
        }

        random=new Random();

    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        background1.x-=10*screenRatioX;
        background2.x-=10*screenRatioX;

        if (background1.x + background1.background.getWidth() < 0){
            background1.x= screenX;
        }

        if (background2.x + background2.background.getWidth() < 0){
            background2.x= screenX;
        }

        if(control.goUp){
            control.Y-=30*screenRatioY;

        }else
            control.Y+=30*screenRatioY;
        if(control.Y<0){
            control.Y=0;
        }
        if(control.Y>= screenY -control.height){
            control.Y= screenY -control.height;
        }

        List<Bullet>usedBullets=new ArrayList<>();

        for(Bullet bullet : bulletList) {
            if (bullet.x > screenX) {
                usedBullets.add(bullet);
            }
            bullet.x += 50 * screenRatioX;

            for(Asteroid asteroid: asteroids){
                if(Rect.intersects(asteroid.getCollison(),bullet.getCollison())){
                    currentScore++;

                    asteroid.X=-500;
                    bullet.x= screenX +500;
                    asteroid.destroyed=true;
                }
            }
        }



        for(Bullet bullet : bulletList){
            usedBullets.remove(bullet);
        }

        for(Asteroid asteroid:asteroids) {

            asteroid.X -= asteroid.speed;

            if (asteroid.X + asteroid.width_x < 0) {

                if (!asteroid.destroyed) {
                    gameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                asteroid.speed = random.nextInt(bound);


            if (asteroid.speed < 10 * screenRatioX) {
                asteroid.speed = (int) (10 * screenRatioX);
            }

            asteroid.X = screenX;
            asteroid.Y = random.nextInt(screenY - asteroid.height_y);

            asteroid.destroyed = false;
        }
            if(Rect.intersects(asteroid.getCollison(),control.getCollision())){
                gameOver = true;
                return;

            }
        }
        /** Updating all data that is required for game to work, checking if game is over, making asteroids and bullets, checking if any collision occurs   */
    }



    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas canvas=getHolder().lockCanvas();

            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            if(gameOver==true){
                isPlaying=false;
                canvas.drawBitmap(control.getBrokenRocket(),control.X,control.Y,paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveHighestScore();
                waitBeforeExit();

                return;
            }
            for (Asteroid asteroid:asteroids){
                canvas.drawBitmap(asteroid.getAsteroidObject(),asteroid.X,asteroid.Y,paint);
            }

            canvas.drawText(currentScore+"",screenX/4f,120,paint);

            canvas.drawBitmap(control.getControl(),control.X, control.Y,paint);

            for(Bullet bullet : bulletList){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y,paint);


            }

            getHolder().unlockCanvasAndPost(canvas);
        }

        /** Drawing all objects on Background additionally checking if game is over, drawing current score  */

    }

    private void waitBeforeExit() {
        try {
            Thread.sleep(5000);
            gameActivity.startActivity(new Intent(gameActivity,MainActivity.class));
            gameActivity.finish();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private void saveHighestScore() {
        if(preferences.getInt("najwyzszy wynik",0) < currentScore){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("najwyzszy wynik", currentScore);
            editor.apply();
        }
        /** saving highest score if current score is higher than highest score   */
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
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

        @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<(screenX /2)){
                    control.goUp=true;
                }
                break;

            case MotionEvent.ACTION_UP:
                    control.goUp=false;
                    if (event.getX() > (screenX / 2)){
                        control.shot++;}
                    break;
        }
        return true;
            /** checking on which side od the screen click was performed and then rocket is either shooting or going up   */
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = control.X + control.width;
        bullet.y = control.Y + (control.height / 2);
        bulletList.add(bullet);
        /** creating new Bullet object on current x and y position   */

    }
}
