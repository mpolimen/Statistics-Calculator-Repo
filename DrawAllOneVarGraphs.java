import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawAllOneVarGraphs
{
    public DrawAllOneVarGraphs(ArrayList<Double> L1)
    {
        ArrayList<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>(); lists.add(L1);

        StatCalc calc = new StatCalc(lists);

        /*
         * Frames
         */

        // Box Plots
        JFrame frame = new JFrame();
        frame.setTitle("Box Plot");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)(height),(int)(height));
        frame.setResizable(true);
        frame.setLocation(((int)(width)-(int)(height))/2,0);
        frame.add(new BoxPlot(calc));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Histograms
        JFrame frame2 = new JFrame();
        frame2.setTitle("Histogram");
        frame2.setSize((int)(height),(int)(height));
        frame2.setResizable(true);
        frame2.setLocation(((int)(width)-(int)(height))/2,0);
        frame2.add(new Histogram(calc));
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);

        // Normal distribution plots
        JFrame frame3 = new JFrame();
        frame3.setTitle("Normal Distribution Plot");
        frame3.setSize((int)(height),(int)(height));
        frame3.setResizable(true);
        frame3.setLocation(((int)(width)-(int)(height))/2,0);
        frame3.add(new NormalDistributionPlot(calc));
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setVisible(true);

        calc.printOneVarStats(0);
    }

    // Test without list input
    public static void main(String[]args)
    {
        /*
         * List One
         */
        double [] nums = {53.0, 27.0, 45.0, 72.0, 52.0, 4.0, 24.0, 55.0, 79.0, 53.0, 17.0, 210.0};
        ArrayList<Double> L1 = new ArrayList<Double>();
        for(int p=0; p<nums.length; p++)
            L1.add(nums[p]);
        ArrayList<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>(); lists.add(L1);

        StatCalc calc = new StatCalc(lists);

        /*
         * Frames
         */

        JFrame frame = new JFrame();
        frame.setTitle("Box Plot");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int)(height),(int)(height));
        frame.setResizable(true);
        frame.setLocation(((int)(width)-(int)(height))/2,0);
        frame.add(new BoxPlot(calc));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JFrame frame2 = new JFrame();
        frame2.setTitle("Histogram");
        frame2.setSize((int)(height),(int)(height));
        frame2.setResizable(true);
        frame2.setLocation(((int)(width)-(int)(height))/2,0);
        frame2.add(new Histogram(calc));
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);

        JFrame frame3 = new JFrame();
        frame3.setTitle("Normal Distribution Plot");
        frame3.setSize((int)(height),(int)(height));
        frame3.setResizable(true);
        frame3.setLocation(((int)(width)-(int)(height))/2,0);
        frame3.add(new NormalDistributionPlot(calc));
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setVisible(true);

        calc.printOneVarStats(0);
    }
}