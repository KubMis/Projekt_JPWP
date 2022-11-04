package com.example.projektjpwp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.projektjpwp.GameView.screenRatioX;
import static com.example.projektjpwp.GameView.screenRatioY;

public class Control {
    private final GameView view;
    public boolean goUp=false;
     int shot=0;

    int shotCounter=0;
    int X;
    int Y;
    int height;
    int width;
    int ControlCounter=0;
    Bitmap rocket;
    Bitmap rocket2;
    Bitmap shot1,shot2;

    Bitmap brokenRocket;

    Control(GameView view,int screenY, Resources res){

         this.view=view;

        rocket = BitmapFactory.decodeResource(res, R.drawable.rocket);
        rocket2 = BitmapFactory.decodeResource(res,R.drawable.rocket2);



        width= rocket.getWidth();
        height= rocket.getHeight();

        width/= 4;
        height/=4;

        width=(int)(width*screenRatioX);
        height=(int)(height*screenRatioY);

        rocket =Bitmap.createScaledBitmap(rocket,width,height,false);
        rocket2=Bitmap.createScaledBitmap(rocket2,width,height,false);

        shot1=BitmapFactory.decodeResource(res, R.drawable.shot1);
        shot2=BitmapFactory.decodeResource(res, R.drawable.shot2);

        shot1=Bitmap.createScaledBitmap(shot1,width,height,false);
        shot2=Bitmap.createScaledBitmap(shot2,width,height,false);

        brokenRocket=BitmapFactory.decodeResource(res,R.drawable.brokenrocket);
        brokenRocket=Bitmap.createScaledBitmap(brokenRocket,width,height,false);

        Y=screenY/2;
        X=(int)(64*screenRatioX);
    }

    Bitmap getControl(){

        if (shot !=0){
            if(shotCounter==1){
                shotCounter++;
                return shot1;
            }
            shotCounter=1;
            shot--;
            view.newBullet();

            return shot2;
        }

        if(ControlCounter ==0){
            ControlCounter++;
            return rocket;
        }
        ControlCounter--;
                return rocket2;
     }

    Rect getCollision(){
        return new Rect(X,Y,X+width,Y+height);
    }

    Bitmap getBrokenRocket(){
        return brokenRocket;
    }
}
