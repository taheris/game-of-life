package com.taheris.gameoflife;

import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Starts the Game of Life view
 *   
 *   <dt> Description:
 *   <dd> Capture any touch gestures on the view to control game actions
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class GameOfLifeActivity extends Activity {
    private GameOfLifeView view; // game of life view renderer
    private GestureDetector gestureDetector; // detects double tap gestures
    private boolean isZooming = false; // zoom gesture in progress
    private float startDistance; // multi-touch start distance
    private float stopDistance; // multi-touch stop distance
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = (GameOfLifeView) findViewById(R.id.gameOfLifeView);
        
        // check if a specific layout has been requested
        String layout = getIntent().getStringExtra("layout");
        if (layout != null) {
            view.getGame().setLayout(GameOfLifeLayout.valueOf(
                    layout.toUpperCase(Locale.ENGLISH)));
        }
        
        gestureDetector = new GestureDetector(this, new SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // invert game running state on double tap
                view.getGame().setRunning(!view.getGame().isRunning());
                if (view.getGame().isRunning()) {
                    view.startGameThread();
                } else {
                    view.stopGameThread();
                }
                return true;
            }
        });
    }
    
    @Override
    public void onPause() {
        super.onPause();
        view.stopGameThread();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.stopGameThread();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        
        case MotionEvent.ACTION_DOWN: // tap on screen
            if (!view.getGame().isRunning()) { // if game is not already running
                view.redrawGrid(true); // step forward one generation
            }
            break;
            
        case MotionEvent.ACTION_POINTER_DOWN: // start multi-touch
            startDistance = touchDistance(event);
            if (startDistance > 5) {
                isZooming = true;
            }
            break;
            
        case MotionEvent.ACTION_MOVE: // move multi-touch
            if (isZooming) {
                stopDistance = touchDistance(event);
            }
            break;
            
        case MotionEvent.ACTION_POINTER_UP: // end multi-touch
            if (isZooming && Math.abs(stopDistance - startDistance) > 5) {
                view.stopGameThread();
                
                if (stopDistance < startDistance) {
                    view.getGame().getGrid().zoomIn();
                } else {
                    view.getGame().getGrid().zoomOut();
                }
                
                view.getGame().initialiseCells(); // reset cells
                view.redrawGrid(false); // draw new cells
            }
            
            isZooming = false;
            break;
        }
        
        return gestureDetector.onTouchEvent(event);
    }
    
    /** returns the distance between two touch points */
    private float touchDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
