package com.example.projektjpwp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.projektjpwp.GameView.screenRatioX;
import static com.example.projektjpwp.GameView.screenRatioY;

public class Bullet {
    int x;
    int y;
    int height;
    int width;
    Bitmap bullet;

    Bullet(Resources resources){

        bullet= BitmapFactory.decodeResource(resources, R.drawable.bullet);
        //assigning image to object

        height=bullet.getHeight();
        width=bullet.getWidth();

        width/=4;
        height/=4;
        // making bullet objects smaller

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        // scaling bullet objects

        bullet=Bitmap.createScaledBitmap(bullet,width, height,false);
        // scaling bitmap objects
    }
    Rect getCollison(){
        return new Rect(x,y,x+width, y+ height);
        /** Checking if collision with other objects occurred  */
    }
}
