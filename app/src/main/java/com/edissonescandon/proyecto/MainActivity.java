package com.edissonescandon.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashani);

        Animation animation1= AnimationUtils.loadAnimation(this, R.anim.desplasamineto_arriba);
        Animation animation2= AnimationUtils.loadAnimation(this, R.anim.despalsamiento_abajo);

        TextView bienvedidoTxt =findViewById(R.id.TextBienvenido);
        TextView unifashionTxt= findViewById(R.id.TextUnifashion);
        ImageView logoImageView = findViewById(R.id.LogoImageView);

        bienvedidoTxt.setAnimation(animation2);
        unifashionTxt.setAnimation(animation2);
        logoImageView.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
