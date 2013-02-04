package com.taheris.gameoflife;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Representation of a Game of Life cell
 *   
 *   <dt> Description:
 *   <dd> Defines methods to identify whether cell is alive, or has previously lived
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class GameOfLifeCell {
    private final int row; // row position
    private final int col; // column position
    private boolean isAlive; // is the cell alive
    private boolean hasLived; // has the cell been alive
    
    public GameOfLifeCell(int row, int col) {
        this(row, col, false, false);
    }
    
    public GameOfLifeCell(int row, int col, boolean isAlive) {
        this(row, col, isAlive, isAlive);
    }
    
    public GameOfLifeCell(int row, int col, boolean isAlive, boolean hasLived) {
        this.row = row;
        this.col = col;
        this.isAlive = isAlive;
        this.hasLived = hasLived;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    public boolean hasLived() {
        return hasLived;
    }
    
    public void setHasLived(boolean hasLived) {
        this.hasLived = hasLived;
    }
}
