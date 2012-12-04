package pacman;

import java.awt.Point;
import java.util.ArrayList;

public class Game {

    private Board board;
    private Point pacManDirection;
    private int steps; // counts how many steps have progressed in the game.

    public Game() {
        steps = 0;
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
    // if necessary, etc. Don't worry much about enforcing rules- that's the
    // Board's job.
    public void step() {
        steps++;

        Point pacManLoc = board.getPacManLocation();
        board.move(pacManLoc.x, pacManLoc.y, pacManDirection);


        // ... and now, on to the ghosts. We'll leave the actual AI work to a
        // separate method in any class implementing the AI interface, focusing
        // instead only on handling and applying that method to the ghosts here.


        // Changing the line below lets you use different AI classes.
        AI ai = new AStarAI();

        // Note: We want to give the human player a solid head start, so we
        // won't even move the AI until at least 15 steps in.

        if (steps >= 15) {

            // Loop through each space on the board:
            for (int x = 0; x < board.width(); x++) {
                for (int y = 0; y < board.height(); y++) {

                    // Check if the space is a ghost:
                    char thing = board.getThingAt(x, y);
                    if (thing == '8' || thing == '%') {
                        // Yup, ghost! Actually call the AI and perform the move:
                        ai.performMoveOnBoard(x, y, board);
                        // Note that the line above will actually replace the ghost
                        // character on the board with either "m" or "M",
                        // ("moved ghost")- this is to prevent the nested for-loop
                        // we're in from getting confused and picking up the same
                        // ghost twice.
                    }
                }
            }

            // Now we have a board with a bunch of ms and Ms. Let's change those back
            // to the usual 8s and %s, eh?
            for (int x = 0; x < board.width(); x++) {
                for (int y = 0; y < board.height(); y++) {
                    char thing = board.getThingAt(x, y);
                    if (thing == 'm') {
                        board.setThingAt(x, y, '8');
                    } else if (thing == 'M') {
                        board.setThingAt(x, y, '%');
                    }
                }
            }
        }
    }

    // A key corresponding to one of the Board directions (left/right/up/down)
    // was just pressed; change Pac-Man's direction and symbol to match.
    public void keyPressed(Point direction) {
        pacManDirection = direction;
        Point loc = board.getPacManLocation();
        if (direction == Board.LEFT) {
            board.setThingAt(loc.x, loc.y, '>');
        } else if (direction == Board.RIGHT) {
            board.setThingAt(loc.x, loc.y, '<');
        } else if (direction == Board.UP) {
            board.setThingAt(loc.x, loc.y, 'v');
        } else if (direction == Board.DOWN) {
            board.setThingAt(loc.x, loc.y, 'ʌ');
        }
    }

    private Point getPacManDirection() {
        Point pacManLoc = board.getPacManLocation();
        char pacMan = board.getThingAt(pacManLoc.x, pacManLoc.y);
        if (pacMan == '<') {
            return Board.RIGHT;
        } else if (pacMan == '>') {
            return Board.LEFT;
        } else if (pacMan == 'v') {
            return Board.UP;
        } else {
            return Board.DOWN;
        }
    }
}
