package com.example.projektjpwp;

import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.example.projektjpwp.R;

public class Background {

     int x=0,y=0;
    Bitmap background;

    Background( int Xposistion,int Yposition, Resources resource){
        background= BitmapFactory.decodeResource(resource, R.drawable.background);
        background=Bitmap.createScaledBitmap(background,Xposistion,Yposition,false);

    }
}
