package com.taheris.gameoflife;

import android.content.Context;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Start a new Game of Life
 * 
 *   <dt> Description:
 *   <dd> Controls the state of the game
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class GameOfLife {
    private boolean isRunning = false; // game animation state
    private final GameOfLifeGrid grid; // holds state on grid size
    private GameOfLifeCell[] cells; // list of Cells holding the state of each
    private GameOfLifeLayout layout = GameOfLifeLayout.RANDOM; // current grid layout
    
    public GameOfLife(Context context) {
        grid = new GameOfLifeGrid(context);
        initialiseCells();
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    public GameOfLifeGrid getGrid() {
        return grid;
    }
    
    public GameOfLifeCell getCell(int cellNumber) {
        return cells[cellNumber];
    }
    
    /** resets the game state to that defined by layout */
    public void setLayout(GameOfLifeLayout layout) {
        this.layout = layout;
        initialiseCells();
    }
    
    /** resets the game based on the current layout for the new grid size */
    public void sizeChanged(int xNew, int yNew) {
        grid.setGridWidth(xNew);
        grid.setGridHeight(yNew);
        grid.setCellDiameter((float) (0.02 * Math.min(xNew, yNew)));
        initialiseCells();
    }
    
    /** returns a one-dimensional cell number for the two-dimensional cell position */
    public int cellNumber(int row, int col) {
        return col * grid.rows() + row;
    }
    
    /** initialise all cells to a dead state, then set alive based on current layout */
    public void initialiseCells() {
        cells = new GameOfLifeCell[grid.rows() * grid.cols()];
        
        for (int row = 0, rows = grid.rows(); row < rows; row++) {
            for (int col = 0, cols = grid.cols(); col < cols; col++) {
                cells[cellNumber(row, col)] = new GameOfLifeCell(row, col);
            }
        }
        
        if (layout == GameOfLifeLayout.RANDOM) {
            setRandom(0.15f);
        } else {
            setAlive(layout.getAlive());
        }
    }
    
    /** @param probability : the chance that each cell will be alive */
    private void setRandom(float probability) {
        for (int row = 0, rows = grid.rows(); row < rows; row++) {
            for (int col = 0, cols = grid.cols(); col < cols; col++) {
                int cell = cellNumber(row, col);
                boolean isAlive = Math.random() < probability;
                
                cells[cell].setAlive(isAlive);
                cells[cell].setHasLived(isAlive);
            }
        }
    }
    
    /** @param life : array of rows, where a # in each item char position denotes live cell column */
    private void setAlive(String[] life) {
        int rows = life.length;
        int cols = life[0].length();
        int startRow = grid.centreRow() - (rows / 2);
        int startCol = grid.centreCol() - (cols / 2);
        
        if (grid.rows() < (rows + 2) || grid.cols() < (cols + 2)) {
            return; // array too big for grid
        }
        
        for (int row = 0; row < rows; row++) {
            char[] rowChars = life[row].toCharArray();
            
            for (int col = 0; col < cols; col++) {
                int cell = cellNumber(startRow + row, startCol + col);
                boolean isAlive = (rowChars[col] == '#');
                
                cells[cell].setAlive(isAlive);
                cells[cell].setHasLived(isAlive);
            }
        }
    }
    
    /** identify whether each cell will be dead or alive in the next generation */
    public void nextGeneration() {
        GameOfLifeCell[] nextCells = new GameOfLifeCell[grid.rows() * grid.cols()];
        
        for (int row = 0, rows = grid.rows(); row < rows; row++) {
            for (int col = 0, cols = grid.cols(); col < cols; col++) {
                int cell = cellNumber(row, col);
                int neighbours = countNeighbours(row, col);
                int[] life = cells[cell].isAlive() ? layout.getLive() : layout.getBorn();
                
                boolean isAlive = false;
                for (int num : life) {
                    if (num == neighbours) {
                        isAlive = true;
                    }
                }
                
                nextCells[cell] = new GameOfLifeCell(row, col, isAlive,
                        isAlive || cells[cell].hasLived());
            }
        }
        
        cells = nextCells;
    }
    
    /** returns a count of the number of live cells in the eight surrounding cells */
    private int countNeighbours(int row, int col) {
        int rows = grid.rows();
        int cols = grid.cols();
        
        // wrap the grid edges to create a toroid shape
        int top = (row == 0 ? rows - 1 : row - 1);
        int bottom = (row == rows - 1 ? 0 : row + 1);
        int left = (col == 0 ? cols - 1 : col - 1);
        int right = (col == cols - 1 ? 0 : col + 1);
        
        int neighbours = 0;
        
        if (cells[cellNumber(top, left)].isAlive()) neighbours++;
        if (cells[cellNumber(top, col)].isAlive()) neighbours++;
        if (cells[cellNumber(top, right)].isAlive()) neighbours++;
        if (cells[cellNumber(row, left)].isAlive()) neighbours++;
        if (cells[cellNumber(row, right)].isAlive()) neighbours++;
        if (cells[cellNumber(bottom, left)].isAlive()) neighbours++;
        if (cells[cellNumber(bottom, col)].isAlive()) neighbours++;
        if (cells[cellNumber(bottom, right)].isAlive()) neighbours++;
        
        return neighbours;
    }
}
