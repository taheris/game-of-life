package com.taheris.gameoflife;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Reprsentation of a Game of Life grid
 *   
 *   <dt> Description:
 *   <dd> Responsive layout defined relative to the size of the screen
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class GameOfLifeGrid {
    private int gridWidth; // width of display area in pixels
    private int gridHeight; // height of display area in pixels
    private float cellDiameter; // diameter of each cell in pixels
    
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public GameOfLifeGrid(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        
        int width, height;
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            d.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = d.getWidth();
            height = d.getHeight();
        }
        
        setGridWidth(width);
        setGridHeight(height);
        setCellDiameter((float) (0.02 * Math.min(width, height)));
    }
    
    public GameOfLifeGrid(int gridWidth, int gridHeight, int cellDiameter) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellDiameter = cellDiameter;
    }
    
    public int getGridWidth() {
        return gridWidth;
    }
    
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }
    
    public int getGridHeight() {
        return gridHeight;
    }
    
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }
    
    public float getCellDiameter() {
        return cellDiameter;
    }
    
    public void setCellDiameter(float cellDiameter) {
        this.cellDiameter = cellDiameter;
    }
    
    public void zoomIn() {
        if (cellDiameter < 0.05 * gridWidth && cellDiameter < 0.05 * gridHeight) {
            cellDiameter = (float) (1.1 * cellDiameter);
        }
    }
    
    public void zoomOut() {
        if (cellDiameter > 0.01 * Math.max(gridWidth, gridHeight)) {
            cellDiameter = (float) (0.9 * cellDiameter);
        }
    }
    
    /** returns the number of grid rows */
    public int rows() {
        return (int) (gridHeight / cellDiameter);
    }
    
    /** returns the number of grid columns */
    public int cols() {
        return (int) (gridWidth / cellDiameter);
    }
    
    /** returns the centre row number */
    public int centreRow() {
        return rows() / 2;
    }
    
    /** returns the centre column number */
    public int centreCol() {
        return cols() / 2;
    }
}
