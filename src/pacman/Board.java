package pacman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {
    private char[][] board;

    public Board() {
        // Initialize the 'board' variable.
        board = getNewBoard();
    }

    // Return a text-based "picture" of what the board looks like.
    public String render() {
        // Since our internal representation of the game board, the 'board'
        // variable, is already pretty good looking, we'll just turn it into
        // a string and display it.
        StringBuffer result = new StringBuffer();
        for(int i=0;i<board.length;i++) {
            result.append(board[i]);
            result.append("\n");
        }
        return result.toString();
    }


    // This code is used in the Board constructor, and also in the Window
    // class to determine dimensions. getNewBoard() returns a character array
    // representing a clean board from board.txt
    public static char[][] getNewBoard() {
        
        // Load board.txt into an array and return it. InputStreams are
        // annoying; see the note at convertStreamToString()
        InputStream boardIS = Board.class.getResourceAsStream("board.txt");
        String boardStr = "";
        try {
            boardStr = convertStreamToString(boardIS);
        } catch (IOException ex) {
            System.out.println("convertStreamToString() failed!");
        }

        String[] lines = boardStr.split("\r\n|\r|\n");

        char[][] result = new char[lines.length][1];

        for(int i=0;i<lines.length;i++) {
            result[i] = new char[lines[i].length()];
            for(int k=0;k<lines[i].length();k++) {
                result[i][k] = lines[i].charAt(k);
            }
        }

        return result;
    }

    // An InputStream presents an awkward, slightly annoying way of
    // getting data from a file. Unfortunately, Java doesn't have anything
    // better, so we use it. This method "reads" data from an InputStream
    // and returns the contents of the file as a String.
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

}
