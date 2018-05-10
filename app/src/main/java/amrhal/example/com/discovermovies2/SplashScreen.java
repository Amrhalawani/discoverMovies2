package amrhal.example.com.discovermovies2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        finishAfter(3300);
    }


    private void finishAfter(int SPLASH_TIME_OUT) {


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(in);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
