/*
 * Marco Polimeni
 * October 22, 2018
 * Statistics Calculator GUI!
 */

import javax.swing.*;
import java.awt.*;

public class RunCalculator
{
    public RunCalculator()
    {
        JFrame frame = new JFrame();
        frame.setTitle("Statistics Calculator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)(height),(int)(height));
        frame.setResizable(true);
        frame.setLocation(((int)(width)-(int)(height))/2,0);
        frame.add(new ListInput());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /*
     * Press OK To Start
     */
    public static void main(String args [])
    {
        new RunCalculator();
    }
}

