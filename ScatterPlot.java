import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
 * A panel to be drawn on the window is created
 */
public class ScatterPlot extends JPanel implements Runnable
{
    private int width, height;
    private Thread main = new Thread(this);
    private StatCalc calc;
    
    public ScatterPlot(StatCalc calc)
    {
        super.setDoubleBuffered(true);
        main.start();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(screenSize.getHeight());
        height = (int)(screenSize.getHeight());

        this.calc = calc;
    }

    public void run()
    {
        while(true)
        {
            repaint();
            try{main.sleep(50);} catch (Exception e){}

            width = this.getWidth();
            height = this.getHeight();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        calc.drawScatterPlot(g2D, width, height, 0, 1);
    }

}

