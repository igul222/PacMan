package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Window extends JFrame implements KeyListener {
    JTextArea textArea;
    Game game;

    public Window() {
        setTitle("Pacman");
        setSize(500,500);
        setLocationRelativeTo(null); // This centers the window on the screen.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Quit app when window closes.

        // Don't worry about what this does, but remember that it's important.
        getContentPane().setLayout(null);

        textArea = new JTextArea();
        textArea.setSize(300, 300);
        textArea.setFont(new Font("Courier New", Font.BOLD, 12));
        getContentPane().add(textArea);
        
        // Change textArea to a more appropriate size.
        char[][] board = Board.getNewBoard();
        textArea.setRows(board[0].length);
        textArea.setColumns(board.length);
        textArea.setSize(textArea.getPreferredSize());

        // The point of this text area is just to show stuff to the user;
        // we don't actually want them to edit it like a real text box,
        // so we'll make it un-editable:
        textArea.setEditable(false);

        // This prevents text selection, but has the unfortunate side effect
        // of making the text color 50% darker. Not a big deal for me, but
        // it might be for you. Just saying, y'know?
        textArea.setEnabled(false);

        // A little bit of visual polish- black on black makes the
        // textbox appear invisible, creating the illusion that it isn't there.
        // Feel free to customize as desired.
        getContentPane().setBackground(Color.BLACK);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        // This will get replaced eventually when our game loads:
        textArea.setText("Loading....");

        // Syntax gets confusing here- follow carefully.
        this.addComponentListener(new ComponentListener() {

            public void componentShown(ComponentEvent ce) {
                // Center the text area when the window appears.
                centerTextArea();

                // This is where the game starts! Create a Game object and
                // start "playing" it. Pay attention here- syntax gets tricky.
                game = new Game();
                Action stepGameAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        game.step();
                        textArea.setText(game.render());
                    }
                };
                new Timer(100, stepGameAction).start();
            }

            // Recenter the text area when the window changes size.
            public void componentResized(ComponentEvent ce) {
                centerTextArea();
            }

            public void componentMoved(ComponentEvent ce) {
                // do nothing
            }

            public void componentHidden(ComponentEvent ce) {
                // do nothing
            }
        });

        // The last thing we need to do here is set up a mechanism for listening
        // to the keys the user presses and forwarding them along to our Game
        // class. We'll implement the methods of the KeyListener interface
        // in this class, and then "register" this class as a key listener for
        // itself and the text area. If a user types a key with this window
        // selected, or with the text field selected, these methods will be
        // triggered and we can forward the message along. Ready? Here we go.
        textArea.addKeyListener(this);
        this.addKeyListener(this);
    }

    // centers the text area within the window
    public void centerTextArea() {
        int windowWidth = getContentPane().getWidth();
        int windowHeight = getContentPane().getHeight();

        textArea.setLocation(
                (windowWidth/2 - textArea.getWidth()/2),
                (windowHeight/2 - textArea.getHeight()/2)
                );
    }

    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
            game.keyPressed(Board.RIGHT);
        else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
            game.keyPressed(Board.LEFT);
        else if(ke.getKeyCode() == KeyEvent.VK_DOWN)
            game.keyPressed(Board.DOWN);
        else if(ke.getKeyCode() == KeyEvent.VK_UP)
            game.keyPressed(Board.UP);
    }

    public void keyTyped(KeyEvent ke) {
        // required for the KeyListener interface, but we don't really have anything
        // to do here, so do nothing.
    }

    public void keyReleased(KeyEvent ke) {
        // required for the KeyListener interface, but we don't really have anything
        // to do here, so do nothing.
    }
}
