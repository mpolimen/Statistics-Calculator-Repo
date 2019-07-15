import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
 * User Interface to input data and calculate a numerical summary with graphs.
 */
public class ListInput extends JPanel implements Runnable, KeyListener
{
    private int width, height;
    private Thread main = new Thread(this);
    private int numLists = 0;
    private Font f = new Font("Bold", 1, 25);
    private ArrayList<Double> list1 = new ArrayList<Double>();
    private ArrayList<Double> list2 = new ArrayList<Double>();
    private int currList = 1;
    private Double currNum = null;
    private String output = "";
    
    public ListInput()
    {
        super.setDoubleBuffered(true);
        main.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(screenSize.getHeight());
        height = (int)(screenSize.getHeight());

    }

    public void run()
    {
        while(true)
        {
            repaint();
            try{main.sleep(50);} catch (Exception e){}

            // Resize the font in accordance to the window dimmensions
            width = this.getWidth();
            height = this.getHeight();

            f = new Font("Bold", 1, width/50);
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setColor(Color.black);
        g2D.setFont(f);
        
        // Welcome/selection screen
        if(numLists == 0)
        {
            drawCenteredString(g2D, "How many lists will you be including (type 1 or 2)",
                new Rectangle(width/4, height/4, width/2, height/2), f);
            drawCenteredString(g2D, "C to change lists and ENTER on an empty line to confirm.",
                new Rectangle(width/4, height/2, width/2, height/4 * 3), f);
        }
        // Input of one list
        else if(numLists == 1)
        {
            g2D.drawString("List 1", width/4, 50);
            for(int p=0; p< list1.size(); p++)
                g2D.drawString(list1.get(p)+"", width/4, p*height/22 + 80);
            g2D.drawString(output+"|", width/4, list1.size()*height/22 + 80);
        }
        // Input of two lists
        else
        {
            g2D.drawString("List 1", width/4, 50);
            g2D.drawString("List 2", width/4 * 3, 50);
            for(int p=0; p< list1.size(); p++)
                g2D.drawString(list1.get(p)+"", width/4, p*height/22 + 80);
            for(int p=0; p< list2.size(); p++)
                g2D.drawString(list2.get(p)+"", width/4 * 3, p*height/22 + 80);
            if(currList == 1)
                g2D.drawString(output+"|", width/4, list1.size()*height/22 + 80);
            else
                g2D.drawString(output+"|", width/4 * 3, list2.size()*height/22 + 80);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyCode();
        repaint();
    }

    public void keyReleased(KeyEvent e)
    {
    	/*
    	 * Code for taking user input
    	 */
        int code=e.getKeyCode();
        String digits = "1234567890";
        
        // If a list is being edited
        if(numLists > 0)
        {
        	// If a digit is typed, add it
            if(digits.indexOf(e.getKeyChar()) > -1)
                output += e.getKeyChar();
            // If a negative sign or decimal is added, make sure it's correct
            if(code==KeyEvent.VK_ENTER && 
            (!output.equals("-") && !output.equals(".") && !output.equals("-.") && !output.equals("")) )
            {
                currNum = Double.parseDouble(output);
                if(currList == 1)
                    list1.add(currNum);
                if(currList == 2)
                    list2.add(currNum);
                output = "";
            }
            // The input submission is complete
            else if(code==KeyEvent.VK_ENTER && output.equals(""))
            {
                if(numLists == 1)
                    new DrawAllOneVarGraphs(list1);
                if(numLists == 2)
                    new DrawAllTwoVarGraphs(list1, list2);
            }
            // Add a negative sign
            else if(code==KeyEvent.VK_MINUS && output.length() == 0)
                output += "-";
            // Add a decimal point
            else if(code==KeyEvent.VK_PERIOD && output.indexOf(".") == -1)
            {
                output += ".";
            }
            // Allow for backspacing
            else if(code==KeyEvent.VK_BACK_SPACE && output.length() > 0)
            {
                output = output.substring(0, output.length()-1);
            }
            // Move cursor to the other list
            if(code==KeyEvent.VK_C && numLists == 2)
            {
                if(currList == 1)
                    currList = 2;
                else
                    currList = 1;
            }
        }
        // Allow the user to select between one list or two
        else
        {
            if(code==KeyEvent.VK_1)
                numLists = 1;
            if(code==KeyEvent.VK_2)
                numLists = 2;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e)
    {
        int code=e.getKeyCode();
    }

    private void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) 
    //https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString((text), x, y);
    }
}

