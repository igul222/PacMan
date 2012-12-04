package pacman;

import java.awt.Point;

public class SimpleAI implements AI {

    public void performMoveOnBoard(int x, int y, Board board) {
        Point pacManLoc = board.getPacManLocation();
        
        Point[] possibleMoves = {Board.UP,
            Board.DOWN,
            Board.LEFT,
            Board.RIGHT};
        int bestMove = -1;
        double bestMoveDistance = 99999;
        for (int i = 0; i < possibleMoves.length; i++) {
            Point move = possibleMoves[i];
            int newX = x + move.x;
            int newY = y + move.y;
            char thingAtNewPos = board.getThingAt(newX, newY);

            if (board.isPacManAt(newX, newY)) {
                // This move takes our ghost to Pac-Man, therefore
                // it's clearly the best possible move. Let's do it.
                bestMove = i;
                break;
            }

            if (thingAtNewPos != ' ' && thingAtNewPos != '.') {
                // The space in this direction is occupied; it isn't
                // a valid move. Skip it.
                // In case you've forgotten, "continue" skips to the
                // next iteration of the for-loop.
                continue;
            }

            // Calculate the distance between the ghost and Pac-Man,
            // assuming he makes this move. Distance formula:
            double dist = Math.sqrt(
                    (newX - pacManLoc.x) * (newX - pacManLoc.x)
                    + (newY - pacManLoc.y) * (newY - pacManLoc.y));
            if (dist < bestMoveDistance) {
                bestMove = i;
                bestMoveDistance = dist;
            }
        }

        // Execute the move.
        if (bestMove == -1) {
            // no legal moves, so let's not do anything.
        } else {
            board.move(x, y, possibleMoves[bestMove]);
            int newX = x + possibleMoves[bestMove].x;
            int newY = y + possibleMoves[bestMove].y;

            char thing = board.getThingAt(newX, newY);
            if(thing=='8')
                board.setThingAt(newX, newY, 'm');
            else if(thing=='%')
                board.setThingAt(newX, newY, 'M');
        }
    }
}
