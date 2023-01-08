package com.example.projektjpwp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.projektjpwp.GameView.screenRatioX;
import static com.example.projektjpwp.GameView.screenRatioY;

public class Asteroid {
    public int speed=20;
    int width_x;
    int asteroidCounter=1;
    int height_y;
    int X=0;
    int Y;
    public boolean destroyed=true;
    Bitmap asteroid1, asteroid2;

    Asteroid(Resources resources){

        asteroid1= BitmapFactory.decodeResource(resources,R.drawable.asteroid1);
        asteroid2= BitmapFactory.decodeResource(resources,R.drawable.asteroid2);
    //assigning image to object

        width_x=asteroid1.getWidth();
        height_y=asteroid1.getHeight();

        width_x/=6;
        height_y/=6;

        // making asteroid objects smaller

        width_x=(int)(width_x*screenRatioX);
        height_y=(int)(height_y*screenRatioY);

        // scaling asteroid objects to screen width and height

        asteroid1=Bitmap.createScaledBitmap(asteroid1,width_x,height_y,false);
        asteroid2=Bitmap.createScaledBitmap(asteroid2,width_x,height_y,false);
        // scaling bitmap objects
        Y =-height_y;



    }

    Bitmap getAsteroidObject(){

        if(asteroidCounter==1)
        {
            asteroidCounter++;
            return asteroid1;
        }
        asteroidCounter=1;
        return asteroid2;

        /** Creating asteroid objects and animating them  */
    }

    Rect getCollison(){

        return new Rect(X, Y, X +width_x, Y +height_y);

        /** Checking if collision with other objects occurred  */
    }



}
