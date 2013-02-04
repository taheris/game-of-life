package com.taheris.gameoflife;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Display the About layout
 *   
 *   <dt> Description:
 *   <dd> Inflates the about view and sets the background
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class AboutActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        // set background image transparency to make text easier to read
        View backrepeat = findViewById(R.id.svAbout);
        Drawable background = backrepeat.getBackground();
        background.setAlpha(150);
    }
}
