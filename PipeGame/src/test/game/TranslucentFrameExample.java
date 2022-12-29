package test.game;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TranslucentFrameExample {
    public static void main(String[] args) {
        // create the frame
        JFrame frame = new JFrame("Translucent Frame Example");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        // set the frame to be translucent
        frame.setOpacity(0.5f);

        // add a label to the frame
        frame.add(new JLabel("Hello, World!"));

        // set the size and position of the frame
        frame.setSize(200, 100);
        frame.setLocationRelativeTo(null);

        // make the frame visible
        frame.setVisible(true);
    }
}
