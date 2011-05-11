package pacman;

import java.awt.Point;

public class Game {
    private Board board;
    private Point pacManDirection;

    public Game() {
        board = new Board();
        pacManDirection = getPacManDirection();
    }

    // Render the current screen of the game. Right now, this method
    // is just a nice way of encapsulating the board's render() method.
    public String render() {
        return board.render();
    }

    // Here's where the actual game-playing happens: step() is called several
    // times per second (by Window). Its job is to advance the game by one
    // small bit: move Pac-Man and the ghosts forward a little bit, turn
    // if necessary, etc. Don't worry about enforcing rules- that's the Board's
    // job.
    public void step() {
       Point pacManLoc = board.getPacManLocation();
       board.move(pacManLoc.x,pacManLoc.y,pacManDirection);
    }

    private Point getPacManDirection() {
        Point pacManLoc = board.getPacManLocation();
        char pacMan = board.getThingAt(pacManLoc.x,pacManLoc.y);
        if(pacMan=='<')
            return Board.RIGHT;
        else if(pacMan=='>')
            return Board.LEFT;
        else if(pacMan=='v')
            return Board.UP;
        else
            return Board.DOWN;
    }
}
