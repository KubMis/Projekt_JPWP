package com.example.projektjpwp;

import android.app.Activity;
import android.graphics.Point;
import android.view.WindowManager;
import android.os.Bundle;

public class GameActivity extends Activity {
    
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point =new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView=new GameView(this,point.x,point.y);


        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.start();
    }
}