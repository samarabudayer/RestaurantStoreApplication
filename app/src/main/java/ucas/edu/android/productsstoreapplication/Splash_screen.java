package ucas.edu.android.productsstoreapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class Splash_screen extends AppCompatActivity {

    private static int splash_time = 1500 ;
    public static final String IS_SIGNED = "isSigned" ;
    Intent isSigned_intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences DefultPrefernce = PreferenceManager.getDefaultSharedPreferences(this) ;
        //   SharedPreferences.Editor DF_Editor = DefultPrefernce.edit() ;

        isSigned_intent = new Intent() ;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (DefultPrefernce.getBoolean(IS_SIGNED, false)){
                    isSigned_intent.setClass(Splash_screen.this , MainActivity.class) ; }
                else {
                    isSigned_intent.setClass(Splash_screen.this , Sign_inActivity.class) ; }

                startActivity(isSigned_intent);
                finish();
            }
        },splash_time) ;
    }}