import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawAllTwoVarGraphs
{
    public DrawAllTwoVarGraphs(ArrayList<Double> L3, ArrayList<Double> L4)
    {
        ArrayList<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>(); 
        lists.add(L3); lists.add(L4);
        StatCalc calc = new StatCalc(lists);

        /*
         * Frames
         */
        
        // Scatter plots
        JFrame frame = new JFrame();
        frame.setTitle("Scatter Plot");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)(height),(int)(height));
        frame.setResizable(true);
        frame.setLocation(((int)(width)-(int)(height))/2,0);
        frame.add(new ScatterPlot(calc));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Residual plots
        JFrame frame2 = new JFrame();
        frame2.setTitle("Residual Plot");
        frame2.setSize((int)(height),(int)(height));
        frame2.setResizable(true);
        frame2.setLocation(((int)(width)-(int)(height))/2,0);
        frame2.add(new ResidualPlot(calc));
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);

        calc.printTwoVarStats(0, 1);
    }

    // Test without list input
    public static void main(String[]args)
    {
        /*
         * List Three
         */
        double [] nums1 = {44.0, 84.0, 58.0, 15.0, 70.0, 81.0, 57.0, 43.0, 35.0, 44.0};
        ArrayList<Double> L3 = new ArrayList<Double>();
        for(int p=0; p<nums1.length; p++)
            L3.add(nums1[p]);
        /*
         * List Four
         */
        double [] nums2 = {34.0, 81.0, 63.0, 62.0, 55.0, 64.0, 57.0, 48.0, 99.0, 59.0};
        ArrayList<Double> L4 = new ArrayList<Double>();
        for(int p=0; p<nums2.length; p++)
            L4.add(nums2[p]);

        ArrayList<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>(); 
        lists.add(L3); lists.add(L4);
        StatCalc calc = new StatCalc(lists);

        /*
         * Frames
         */
        JFrame frame = new JFrame();
        frame.setTitle("Scatter Plot");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)(height),(int)(height));
        frame.setResizable(true);
        frame.setLocation(((int)(width)-(int)(height))/2,0);
        frame.add(new ScatterPlot(calc));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JFrame frame2 = new JFrame();
        frame2.setTitle("Residual Plot");
        frame2.setSize((int)(height),(int)(height));
        frame2.setResizable(true);
        frame2.setLocation(((int)(width)-(int)(height))/2,0);
        frame2.add(new ResidualPlot(calc));
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);

        calc.printTwoVarStats(0, 1);
    }
}