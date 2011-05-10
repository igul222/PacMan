package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

public class Window extends JFrame {
    JTextArea textArea;

    public Window() {
        setTitle("Pacman");
        setSize(400,400);
        setLocationRelativeTo(null); // This centers the window on the screen.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Quit app when window closes.

        // Don't worry about what this does, but remember that it's important.
        getContentPane().setLayout(null);

        textArea = new JTextArea();
        textArea.setSize(300, 300);
        textArea.setFont(new Font("Courier New", Font.BOLD, 12));
        getContentPane().add(textArea);

        // The point of this text area is just to show stuff to the user;
        // we don't actually want them to edit it like a real text box,
        // so we'll make it un-editable:
        textArea.setEditable(false);

        // A little bit of visual polish- black on black makes the
        // textbox appear invisible, creating the illusion that it isn't there.
        // Feel free to customize as desired.
        getContentPane().setBackground(Color.BLACK);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        // This will get replaced in another method when our game loads:
        textArea.setText("Loading....");

        // Syntax gets confusing here- follow carefully.
        this.addComponentListener(new ComponentListener() {

            // Center the text area when the window appears
            public void componentShown(ComponentEvent ce) {
                centerTextArea();
            }

            // (re)center the text area when the window changes size
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

}
