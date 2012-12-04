/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

/**
 *
 * @author ishaan
 */
public interface AI {

    // Accept a board with *only one* ghost, and
    // move that ghost exactly one space on the
    // board. The ghost's spot is denoted by either
    // 'g' or 'G'.
    public void performMoveOnBoard(int x, int y, Board board);

}