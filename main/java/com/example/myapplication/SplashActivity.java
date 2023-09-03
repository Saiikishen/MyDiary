package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ImageView imageView;
    String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5TtUu77VJRgVPzCsU2LGRDXH6yoa3mGWa-w&usqp=CAU";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageView);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.baseline_image_24)
                .into(imageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("onboardingscreen",MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this,onboarding.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    Intent intent = new Intent(SplashActivity.this,redirection.class);
                    startActivity(intent);
                    finish();

                }



            }

        } ,3000);
    }
}

