package pacman;

import java.awt.Point;
import java.util.ArrayList;

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
    // if necessary, etc. Don't worry much about enforcing rules- that's the
    // Board's job.
    public void step() {
       Point pacManLoc = board.getPacManLocation();
       board.move(pacManLoc.x,pacManLoc.y,pacManDirection);

       // ... and now, on to the ghosts. We'll use a simple AI strategy in which
       // each ghost acts independently (they don't collaborate) and moves in
       // Pac-Man's approximate direction.

       // More on this line later.
       ArrayList<Point> movedGhosts = new ArrayList<Point>();
       
       for(int x=0;x<board.maxX();x++) {
           for(int y=0;y<board.maxY();y++) {
               if(movedGhosts.indexOf(new Point(x,y)) != -1)
                   continue; // We've already moved a ghost to this point.

               if(board.getThingAt(x, y)=='*') {
                   // There's a ghost at (x,y). Let's figure out where to move
                   // him and do it.

                   // Step 1: find all possible valid moves for this ghost.
                   Point[] possibleMoves =
                            {Board.UP,
                             Board.DOWN,
                             Board.LEFT,
                             Board.RIGHT};
                   int bestMove = -1;
                   double bestMoveDistance = 99999;
                   for(int i=0;i<possibleMoves.length;i++) {
                       Point move = possibleMoves[i];
                       int newX = x + move.x;
                       int newY = y + move.y;
                       char thing = board.getThingAt(newX, newY);

                       if(board.isPacManAt(newX, newY)) {
                           // This move takes our ghost to Pac-Man, therefore
                           // it's clearly the best possible move. Let's do it.
                           bestMove = i;
                           break;
                       }

                       if(thing != ' ' && thing != '.') {
                           // The space in this direction is occupied; it isn't
                           // a valid move. Skip it.
                           // In case you've forgotten, "continue" skips to the
                           // next iteration of the for-loop.
                           continue;
                       }

                       // Calculate the distance between the ghost and Pac-Man,
                       // assuming he makes this move. Distance formula:
                       double dist = Math.sqrt(
                               (newX-pacManLoc.x)*(newX-pacManLoc.x)+
                               (newY-pacManLoc.y)*(newY-pacManLoc.y));
                       if(dist < bestMoveDistance) {
                           bestMove = i;
                           bestMoveDistance = dist;
                       }
                   }

                   // Execute the move.
                   if(bestMove == -1) {
                       // no legal moves, so let's not do anything.
                   } else {
                       board.move(x, y, possibleMoves[bestMove]);
                   }

                   // One last thing: because we've just moved a ghost, and
                   // we're looping through the coordinates to find ghosts
                   // to move right now, we need to make sure we don't move
                   // this ghost twice. To do this, we'll add its new location
                   // to a list of locations to skip in the loop.
                   movedGhosts.add(new Point(
                           x+possibleMoves[bestMove].x,
                           y+possibleMoves[bestMove].y));
               }
           }
       }
    }

    // A key corresponding to one of the Board directions (left/right/up/down)
    // was just pressed; change Pac-Man's direction and symbol to match.
    public void keyPressed(Point direction) {
        pacManDirection = direction;
        Point loc = board.getPacManLocation();
        if(direction==Board.LEFT)
            board.setThingAt(loc.x, loc.y, '>');
        else if(direction==Board.RIGHT)
            board.setThingAt(loc.x, loc.y, '<');
        else if(direction==Board.UP)
            board.setThingAt(loc.x, loc.y, 'v');
        else if(direction==Board.DOWN)
            board.setThingAt(loc.x, loc.y, 'ʌ');
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
