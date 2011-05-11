package pacman;

public class Game {
    private Board board;

    public Game() {
        board = new Board();
    }

    // Render the current screen of the game. Right now, this method
    // is just a nice way of encapsulating the board's render() method.
    public String render() {
        return board.render();
    }

    public void step() {
        
    }
}
