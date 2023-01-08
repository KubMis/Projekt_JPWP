package com.example.projektjpwp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.graj).setOnClickListener(
                view -> startActivity(new Intent(MainActivity.this, GameActivity.class)));

        final SharedPreferences prefs = getSharedPreferences("Gra", MODE_PRIVATE);

        TextView highScore = findViewById(R.id.najwyzszywynik);

        highScore.setText("Najwyższy wynik: " + prefs.getInt("Najwyższy wynik", 0));



    }
}