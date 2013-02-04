package com.taheris.gameoflife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Display the Intro layout
 *   
 *   <dt> Description:
 *   <dd> Shows a splash screen for the specified time then inflates Menu view
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class IntroActivity extends Activity {
    private static final int SPLASH_TIME = 3000;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        Thread splash = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < SPLASH_TIME) {
                        sleep(100);
                        waited += 100;
                    }
                } catch(InterruptedException e) {
                    // Log.d("IntroActivity", e.getMessage());
                } finally {
                    finish();
                    Intent intent = new Intent(IntroActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        };
        
        splash.start();
    }
}
