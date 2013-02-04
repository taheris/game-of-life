package com.taheris.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Controls the screen rendering
 *   
 *   <dt> Description:
 *   <dd> Canvas updates are done in a thread separate from the main GUI
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class GameOfLifeView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameOfLife game; // game of life implementation
    private GameOfLifeThread gameThread; // separate thread for game loop
    private final SurfaceHolder holder = getHolder(); // display surface
    private Canvas canvas = null; // canvas for rendering cells
    private final Paint paintBackground = new Paint(); // background settings
    private final Paint paintLiveCell = new Paint(); // live cell settings
    private final Paint paintLivedCell = new Paint(); // previously alive cell settings
    
    public GameOfLifeView(Context context, AttributeSet attributes) {
        super(context, attributes);
        game = new GameOfLife(context); // instantiate new game
        holder.addCallback(this); // register SurfaceHolder callback listener
        
        paintBackground.setARGB(255, 10, 10, 10); // background colour
        paintLiveCell.setARGB(255, 200, 10, 10); // living cell colour
        paintLivedCell.setARGB(255, 50, 10, 10); // previously alive cell colour
    }
    
    public GameOfLife getGame() {
        return game;
    }
    
    /** start a new game thread */
    public void startGameThread() {
        gameThread = new GameOfLifeThread();
        gameThread.start();
    }
    
    /** ensure the game thread has been stopped */
    public void stopGameThread() {
        boolean retry = true;
        game.setRunning(false);
        
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }
    
    /** draw circles on the canvas to represent living or previously alive cells */
    private void drawGrid(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paintBackground);
        
        float radius = game.getGrid().getCellDiameter() / 2;
        if (radius < 1) {
            radius = 1; // minimum radius size
        }
        
        for (int row = 0, rows = game.getGrid().rows(); row < rows; row++) {
            for (int col = 0, cols = game.getGrid().cols(); col < cols; col++) {
                GameOfLifeCell cell = game.getCell(game.cellNumber(row, col));
                float cx = col * radius * 2 + radius; // cell centre col
                float cy = row * radius * 2 + radius; // cell centre row
                
                if (cell.isAlive()) {
                    canvas.drawCircle(cx, cy, radius, paintLiveCell);
                } else if (cell.hasLived()) {
                    canvas.drawCircle(cx, cy, radius, paintLivedCell);
                }
            }
        }
    }
    
    /** draw the state of the current cells, or draw the state of the next generation of cells */
    public void redrawGrid(boolean generateNext) {
        try {
            // ensure only one thread can access canvas at any time
            canvas = holder.lockCanvas(null);
            
            synchronized(holder) {
                if (generateNext) {
                    game.nextGeneration(); // recalculate living cells
                }
                drawGrid(canvas);
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        game.sizeChanged(xNew, yNew);
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startGameThread();
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGameThread();
    }
    
    /** controls the game loop */
    private class GameOfLifeThread extends Thread {
        @Override
        public void run() {
            redrawGrid(false); // draw current generation
            while (game.isRunning()) {
                redrawGrid(true); // draw next generation
            }
        }
    }
}
