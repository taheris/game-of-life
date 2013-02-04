package com.taheris.gameoflife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Display the Menu view
 *   
 *   <dt> Description:
 *   <dd> Show the menu then attach event listeners to all buttons
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class MenuActivity extends Activity {
    private Button buttonRandom;
    private Button buttonLayout;
    private Button buttonAbout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        // get buttons from view
        buttonRandom = (Button) findViewById(R.id.btnStart);
        buttonLayout = (Button) findViewById(R.id.btnLayout);
        buttonAbout = (Button) findViewById(R.id.btnAbout);
        
        // add click event listeners
        buttonRandom.setOnClickListener(new ButtonListener(GameOfLifeActivity.class));
        buttonLayout.setOnClickListener(new ButtonListener(LayoutActivity.class));
        buttonAbout.setOnClickListener(new ButtonListener(AboutActivity.class));
        
        // set background transparency
        buttonRandom.getBackground().setAlpha(180);
        buttonLayout.getBackground().setAlpha(180);
        buttonAbout.getBackground().setAlpha(180);
    }
    
    private class ButtonListener implements OnClickListener {
        private final Class<?> cls;
        
        ButtonListener(Class<?> cls) {
            this.cls = cls;
        }
        
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, cls);
            startActivity(intent);
        }
    }
}
