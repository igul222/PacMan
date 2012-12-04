/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ishaan
 */

// The A* pathfinding algorithm is implemented in the AStar.java, which
// was found on the Internet. The AStar class provides a general version of
// the algorithm, which we fit into our Pac-Man board model and AI interface
// through this AStarAI class.
public class AStarAI extends AStar implements AI {

    Board board;

    public void performMoveOnBoard(int x, int y, Board theBoard) {
        board = theBoard;

        List<Point> path = compute(new Point(x, y));
        if (path == null) {
            // There is no valid path to the target.
            if (board.getThingAt(x, y) == '8') {
                board.setThingAt(x, y, 'm');
            } else // board.getThingAt(next.x, next.y)=='%'
            {
                board.setThingAt(x, y, 'M');
            }
            return;
        }

        Point next = path.get(1);
        int dx = next.x - x;
        int dy = next.y - y;
        Point move = new Point(dx, dy);

        board.move(x, y, move);

        if (board.getThingAt(next.x, next.y) == '8') {
            board.setThingAt(next.x, next.y, 'm');
        } else // board.getThingAt(next.x, next.y)=='%'
        {
            board.setThingAt(next.x, next.y, 'M');
        }
    }

    @Override
    protected boolean isGoal(Object node) {
        Point point = (Point) node;
        boolean result = board.isPacManAt(point.x, point.y);
        return result;
    }

    // Return the time (# of steps) required to go from one point to another.
    @Override
    protected Double g(Object from, Object to) {
        Point a = (Point) from;
        Point b = (Point) to;
        int cost = (Math.abs(a.x - b.x) + Math.abs(a.y + b.y));
        return new Double((double) cost);
    }

    // Effectively, h(from,to) should behave identically to g(from,to) here.
    // (That is, of course, a huge oversimplification, but let's go with it.)
    @Override
    protected Double h(Object from, Object to) {
        return g(from, to);
    }

    @Override
    protected List generateSuccessors(Object node) {
        ArrayList<Point> list = new ArrayList<Point>();
        Point p = (Point) node;

        Point[] possibleMoves = {Board.UP,
            Board.DOWN,
            Board.LEFT,
            Board.RIGHT};

        for(int i=0;i<possibleMoves.length;i++) {
            int newX = p.x+possibleMoves[i].x;
            int newY = p.y+possibleMoves[i].y;

            char thing = board.getThingAt(newX, newY);
            if(thing==' '||thing=='.'||board.isPacManAt(newX,newY)) {
                list.add(new Point(newX,newY));
            }
        }
        
        return list;
    }
}
