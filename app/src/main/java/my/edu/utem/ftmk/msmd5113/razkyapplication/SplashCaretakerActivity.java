package my.edu.utem.ftmk.msmd5113.razkyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.Login.LoginCaretakerActivity;

public class SplashCaretakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_caretaker);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashCaretakerActivity.this, LoginCaretakerActivity.class);
//                Intent i = new Intent(SplashCaretakerActivity.this, MainCaretakerActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 3000);
    }
}
