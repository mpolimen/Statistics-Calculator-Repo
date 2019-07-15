import java.util.*;
import java.awt.*;

/*
 * Marco Polimeni
 * Calculator that takes in lists for statistics calculations and graph drawing.
 * Works for one-variable and two-variable lists.
 * 10/19/18
 */

public class StatCalc
{
    private ArrayList<ArrayList<Double>> lists;
    
    /*
     * Constructor
     */
    public StatCalc(ArrayList<ArrayList<Double>> lists)
    {
        this.lists = lists;
    }

    /*
     * 
     * 
     * 
     * 
     * One-Variable Methods
     * 
     * 
     * 
     */

    public void printOneVarStats(int listNum)
    {
        System.out.println("Sorted List: "+sortedList(listNum));
        System.out.println("n(size): "+lists.get(listNum).size());
        System.out.println("Mean: "+mean(listNum));
        System.out.println("Standard Deviation: "+standardDeviation(listNum));
        System.out.println("Min: "+minVal(listNum));
        System.out.println("Q1: "+Q1(listNum));
        System.out.println("Median: "+median(listNum));
        System.out.println("Q3: "+Q3(listNum));
        System.out.println("Max: "+maxVal(listNum));
        System.out.println("IQR: "+IQR(listNum));
    }

    // Calculates the mean
    public double mean(int listNum)
    {
        ArrayList<Double> list = lists.get(listNum);
        double total = 0;
        for(int p=0; p<list.size(); p++) total+= list.get(p);
        return Math.round(total / list.size() * 1000.0)/1000.0;
    }

    // Calculates the standard deviation
    public double standardDeviation(int listNum)
    {
        ArrayList<Double> list = lists.get(listNum);
        double mean = mean(listNum);
        double total = 0;
        for(int p=0; p<list.size(); p++)
        {
            total += Math.pow(list.get(p) - mean, 2);
        }
        return Math.round(Math.sqrt((total / (list.size() -1))) * 1000.0) / 1000.0;
    }

    // Finds the minimum value
    public double minVal(int listNum){return sortedList(listNum).get(0);}

    // Finds the maximum value
    public double maxVal(int listNum){return sortedList(listNum).get(sortedList(listNum).size()-1);}

    // Finds the median value
    public double median(int listNum)
    {
        ArrayList<Double> list = sortedList(listNum);
        if(list.size()%2==0)
            return (list.get(list.size()/2) + list.get(list.size()/2 - 1))/2;
        return list.get(list.size()/2);
    }

    // Finds the 1st quartile value
    public double Q1(int listNum)
    {
        ArrayList<Double> list = sortedList(listNum);
        int middle = list.size() / 2;
        if(list.size()%2 == 0)
            return ( list.get(middle/2) + list.get((middle/2) - 1) )/2;
        return list.get(middle/2);
    }

    // Finds the 3rd quartile value
    public double Q3(int listNum)
    {
        ArrayList<Double> list = sortedList(listNum);
        int middle = list.size() / 2;
        if(list.size()%2 == 0)
            return ( list.get(middle+(middle/2)) + list.get(middle-1+(middle/2)) )/2;
        return list.get((list.size()-middle)/2 + middle);
    }

    // Calculates the interquartile range
    public double IQR(int listNum)
    {
        return Q3(listNum) - Q1(listNum);
    }

    // Determines whether a data point is an outlier to the data set
    private boolean isOutlier(int listNum, int index)
    {
        double num = lists.get(listNum).get(index);
        double offSet = IQR(listNum) * 1.5;
        return (num <= (Q1(listNum) - offSet) || num >= (Q3(listNum) + offSet));
    }

    // Calculates the standardized score of an observed value
    public double getZScore(int listNum, double value)
    {
        double mean = mean(listNum); double sx = standardDeviation(listNum);
        return (value - mean)/sx;
    }

    /*
     * 
     * 
     * 
     * 
     * Two-Variable Methods
     * 
     * 
     * 
     */
    public void printTwoVarStats(int L1, int L2)
    {
        System.out.println("Correlation(r): "+correlation(L1, L2));
        System.out.println("Coefficient of Determination(r-squared): "+rSqr(L1, L2));
        System.out.println("Least-Squares Regression Line: y = "+interceptLSRL(L1, L2)+" + "+slopeLSRL(L1, L2)+"x");
        System.out.println("Standard Deviation: "+standardDeviation(L1, L2));
    }

    // Calculates a correlation value
    public double correlation(int L1, int L2)
    {
        int n = lists.get(L1).size();
        double sum = 0;
        double xMean = mean(L1), xSD = standardDeviation(L1);
        double yMean = mean(L2), ySD = standardDeviation(L2);
        for(int p=0; p<n; p++)
        {
            double x = lists.get(L1).get(p), y = lists.get(L2).get(p);
            sum += ( (x-xMean)/xSD ) * ( (y-yMean)/ySD );
        }        
        return Math.round((1/(double)(n-1)) * sum * 1000.0)/1000.0;
    }

    // Calculates the coefficient of determination (r^2)
    public double rSqr(int L1, int L2)
    {
        return correlation(L1, L2) * correlation(L1, L2);
    }

    // Calculates the slope of the least-squares regression line
    public double slopeLSRL(int L1, int L2)
    {
        return Math.round(1000.0*correlation(L1, L2)*( standardDeviation(L2) / standardDeviation(L1) ))/1000.0;
    }

    // Calculates the intercept of the least-squares regression line
    public double interceptLSRL(int L1, int L2)
    {
        return mean(L2) - slopeLSRL(L1, L2) * mean(L1);
    }

    // Considering an input value, uses the LSRL to predict the output value
    public double prediction(int L1, int L2, double xVal)
    {
        return interceptLSRL(L1, L2) + slopeLSRL(L1, L2) * xVal;
    }

    // The difference between the observed and expected value of an input
    public double residual(int L1, int L2, double xVal, double yVal)
    {
        return yVal - prediction(L1, L2, xVal);
    }

    // Calculates the standard deviation
    public double standardDeviation(int L1, int L2)
    {
        if(lists.get(L1).size() <= 2)
            return 0;
        int total = 0;
        for(int p=0; p<lists.get(L1).size(); p++)
            total += Math.pow(residual(L1, L2, lists.get(L1).get(p), lists.get(L2).get(p)), 2);
        return Math.round(Math.sqrt(total/(lists.get(L1).size()-2)) * 1000.0)/1000.0;
    }

    /*
     * 
     * 
     * 
     * 
     * Drawing Methods (One-Variable)
     * 
     * 
     * 
     */

    // Draws a box plot
    public void drawBoxPlot(Graphics2D g, int width, int height, int listNum)
    // Can be called up to 4 times to draw box plots.
    {
        ArrayList<Integer> outliers = outlierIndeces(listNum);
        ArrayList<Double> list = sortedList(listNum);
        int n = 0; double maxO = maxVal(listNum); double minO = minVal(listNum);
        // Finds min that is not outlier.
        while(n < outliers.size())
        {
            if(n == outliers.get(n))
            {
                minO = (list.get(n + 1));
            }
            n++;
        }
        int l = lists.get(listNum).size() - 1; n = outliers.size() - 1;
        // Finds max that is not outlier.
        while(n >= 0)
        {
            if(l == outliers.get(n))
            {
                maxO = (double)(list.get(l - 1));
            }
            n--; l--;
        }
        Font f = new Font("Arial", 1, 10);
        g.setFont(f);
        double Q1 = Q1(listNum), Q3 = Q3(listNum), median = median(listNum), min=minVal(listNum), max = maxVal(listNum);;
        int xMargin = (width/9), yMargin = (height/8);
        // Draw the axis bar.
        g.drawLine(xMargin, height - yMargin - (height/4)*listNum, width - xMargin, height - yMargin - (height/4)*listNum);
        double range = max - min; ArrayList<Double> labels = new ArrayList<Double>();
        for(double val = min; val<= max; val+=range/10)
            labels.add(Math.round(val * 100.0) / 100.0);
        // Draws the number labels
        for(int p=0; p<labels.size(); p++)
        {
            g.drawString(""+labels.get(p), xMargin + p*((width - 2*xMargin)/10), height - yMargin + 20 - (height/4)*listNum);
        }
        double minX = ( (minO-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
        double maxX = ( (maxO-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
        // Draw min marker.
        g.drawLine((int)minX, height - yMargin - 70 - (height/4)*listNum, (int)minX, height - yMargin - 50 - (height/4)*listNum);
        // Draw max marker.
        g.drawLine((int)maxX, height - yMargin - 70 - (height/4)*listNum, (int)maxX, height - yMargin - 50 - (height/4)*listNum);
        double Q1x = ( (Q1-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
        double Q3x = ( (Q3-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
        // Draws IQR box
        g.drawRect((int)Q1x, height - yMargin - 90 - (height/4)*listNum, (int)(Q3x-Q1x), 60);

        double medianx = ( (median-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
        // Draws the median line.
        g.drawLine((int)medianx, height - yMargin - 90 - (height/4)*listNum, (int)medianx, height - yMargin - 30 - (height/4)*listNum);
        // Draws arms.
        g.drawLine((int)minX, height - yMargin - 60 - (height/4)*listNum, (int)Q1x, height - yMargin - 60 - (height/4)*listNum);
        g.drawLine((int)Q3x, height - yMargin - 60 - (height/4)*listNum, (int)maxX, height - yMargin - 60 - (height/4)*listNum);
        g.drawString("Box Plot", width/2, yMargin - 20);
        f = new Font("Bold", 1, 18); g.setFont(f);
        for(int p=0; p < outliers.size(); p++)
        {
            double xPos = ( (lists.get(listNum).get(outliers.get(p))-min)/(range/10) * ((width - 2*xMargin)/10) + xMargin );
            g.drawString("*", (int)xPos, height - yMargin - 60 - (height/4)*listNum);
        }
    }

    // Draws a histogram
    public void drawHistogram(Graphics2D g, int width, int height, int listNum)
    {
        ArrayList<Double> list = sortedList(listNum);
        Font f = new Font("Arial", 1, 10);
        g.setFont(f);
        int xMargin = (width/9), yMargin = (height/8);
        double min = minVal(listNum), max = maxVal(listNum);
        // Draw the axes.
        g.drawLine(xMargin, height - yMargin, width - xMargin, height - yMargin);
        g.drawLine(xMargin, yMargin, xMargin, height - yMargin);
        // Draw the x-axis labels.
        double range = max - min; ArrayList<Double> Xlabels = new ArrayList<Double>();
        for(double val = min; val<= max; val+=range/10)
            Xlabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Xlabels.size(); p++)
        {
            g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), height - yMargin + 20);
        }
        // Draw the y-axis labels.
        int maxY = findHighestFrequency(listNum); ArrayList<Double> Ylabels = new ArrayList<Double>();
        for(int p=0; p <= maxY; p++)
            Ylabels.add((double)p);
        for(int p=0; p<Ylabels.size(); p++)
        {
            g.drawString(""+Ylabels.get(p), xMargin - 20, height - yMargin - p*((height - 2*yMargin)/Ylabels.size()));
        }
        // Draw the bars
        for(int p=0; p<Xlabels.size() - 1; p++)
        {
            double start = Xlabels.get(p);
            int freq = getFrequency(listNum, start);
            int y0 = (yMargin +(Ylabels.size()-freq)*((height-2*yMargin)/Ylabels.size()));
            int h = (height - yMargin) - y0;
            if(freq > 0)
                g.drawRect(xMargin+p*((width - 2*xMargin)/10), y0,(width - 2*xMargin)/10, h);
        }
        g.drawString("Histogram", width/2, yMargin - 20);
    }

    // Draws a normal distribution plot
    public void drawNormalDistributionPlot(Graphics2D g, int width, int height, int listNum)
    {
        ArrayList<Double> list1 = lists.get(listNum);
        ArrayList<Double> list2 = new ArrayList<Double>();
        for(int p=0; p<list1.size(); p++)
            list2.add(getZScore(listNum, list1.get(p)));
        Font f = new Font("Arial", 1, 10);
        g.setFont(f);
        int xMargin = (width/9), yMargin = (height/8);
        double minX = minVal(listNum), maxX = maxVal(listNum);
        double minY = Collections.min(list2), maxY = Collections.max(list2);
        double rangeX = maxX - minX; double rangeY = maxY - minY; 
        double zeroAxis = (height - yMargin) - ( (0-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
        // Draw the axes.
        if(minY >= 0)
            g.drawLine(xMargin, height - yMargin, width - xMargin, height - yMargin);
        else
            g.drawLine(xMargin, (int)zeroAxis, width - xMargin, (int)zeroAxis);
        g.drawLine(xMargin, yMargin, xMargin, height - yMargin);

        // Draw the x-axis labels.
        ArrayList<Double> Xlabels = new ArrayList<Double>();
        for(double val = minX; val<= maxX; val+=rangeX/10)
            Xlabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Xlabels.size(); p++)
        {
            if(minY >= 0)
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), height - yMargin + 20);
            else
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), (int)zeroAxis + 20);
        }

        // Draw the y-axis labels.
        ArrayList<Double> Ylabels = new ArrayList<Double>();
        for(double val = minY; val<= maxY; val+=rangeY/10)
            Ylabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Ylabels.size(); p++)
        {
            g.drawString(""+Ylabels.get(p), xMargin - 30, height - yMargin - p*((height - 2*yMargin)/(Ylabels.size()-1)));
        }

        // Plot points
        g.drawString("Normal Distribution Plot", width/2, yMargin - 20);
        f = new Font("Arial", 1, 20);
        g.setFont(f);
        for(int p=0; p<list1.size(); p++)
        {
            double xPos = ( (list1.get(p)-minX)/(rangeX/10) * ((width - 2*xMargin)/10) + xMargin );
            double yPos = (height - yMargin) - ( (list2.get(p)-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
            //Draw centered for more accuracy.
            g.drawString("*", (int)xPos, (int)yPos);
        }
    }

    /*
     * 
     * 
     * 
     * 
     * Drawing Methods (Two-Variable)
     * 
     * 
     * 
     */
    
    // Draws a scatter plot
    public void drawScatterPlot(Graphics2D g, int width, int height, int L1, int L2)
    {
        ArrayList<Double> list1 = lists.get(L1);
        ArrayList<Double> list2 = lists.get(L2);
        Font f = new Font("Arial", 1, 10);
        g.setFont(f);
        int xMargin = (width/9), yMargin = (height/8);
        double minX = minVal(L1), maxX = maxVal(L1);
        double minY = minVal(L2), maxY = maxVal(L2);
        double rangeX = maxX - minX; double rangeY = maxY - minY; 
        double zeroAxis = (height - yMargin) - ( (0-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
        // Draw the axes.
        if(minY >= 0)
            g.drawLine(xMargin, height - yMargin, width - xMargin, height - yMargin);
        else
            g.drawLine(xMargin, (int)zeroAxis, width - xMargin, (int)zeroAxis);
        g.drawLine(xMargin, yMargin, xMargin, height - yMargin);

        // Draw the x-axis labels.
        ArrayList<Double> Xlabels = new ArrayList<Double>();
        for(double val = minX; val<= maxX; val+=rangeX/10)
            Xlabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Xlabels.size(); p++)
        {
            if(minY >= 0)
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), height - yMargin + 20);
            else
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), (int)zeroAxis + 20);
        }

        // Draw the y-axis labels.
        ArrayList<Double> Ylabels = new ArrayList<Double>();
        for(double val = minY; val<= maxY; val+=rangeY/10)
            Ylabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Ylabels.size(); p++)
        {
            g.drawString(""+Ylabels.get(p), xMargin - 30, height - yMargin - p*((height - 2*yMargin)/(Ylabels.size()-1)));
        }

        // Plot points
        g.drawString("Scatter Plot", width/2, yMargin - 20);
        f = new Font("Arial", 1, 20);
        g.setFont(f);
        for(int p=0; p<list1.size(); p++)
        {
            double xPos = ( (list1.get(p)-minX)/(rangeX/10) * ((width - 2*xMargin)/10) + xMargin );
            double yPos = (height - yMargin) - ( (list2.get(p)-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
            //Draw centered for more accuracy.
            g.drawString("*", (int)xPos, (int)yPos);
        }

        // Draw the regression line
        double x0 = ( xMargin );
        double x1 = ( maxX-minX)/(rangeX/10) * ((width - 2*xMargin)/10) + xMargin ;
        double y0 = (height - yMargin) - ( (prediction(L1, L2, minX)-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
        double y1 = (height - yMargin) - ( (prediction(L1, L2, maxX)-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
        g.drawLine((int)x0, (int)y0, (int)x1, (int)y1);
    }

    // Draws a residual plot
    public void drawResidualPlot(Graphics2D g, int width, int height, int L1, int L2)
    {
        ArrayList<Double> list1 = lists.get(L1);
        ArrayList<Double> yList = lists.get(L2);
        ArrayList<Double> list2 = new ArrayList<Double>();
        for(int p=0; p<yList.size(); p++)
            list2.add(residual(L1, L2, list1.get(p), yList.get(p)));
        Font f = new Font("Arial", 1, 10);
        g.setFont(f);
        int xMargin = (width/9), yMargin = (height/8);
        double minX = minVal(L1), maxX = maxVal(L1);
        // Redo these values
        double minY = Collections.min(list2), maxY = Collections.max(list2);
        double rangeX = maxX - minX; double rangeY = maxY - minY; 
        double zeroAxis = (height - yMargin) - ( (0-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
        // Draw the axes.
        if(minY >= 0)
            g.drawLine(xMargin, height - yMargin, width - xMargin, height - yMargin);
        else
            g.drawLine(xMargin, (int)zeroAxis, width - xMargin, (int)zeroAxis);
        g.drawLine(xMargin, yMargin, xMargin, height - yMargin);

        // Draw the x-axis labels.
        ArrayList<Double> Xlabels = new ArrayList<Double>();
        for(double val = minX; val<= maxX; val+=rangeX/10)
            Xlabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Xlabels.size(); p++)
        {
            if(minY >= 0)
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), height - yMargin + 20);
            else
                g.drawString(""+Xlabels.get(p), xMargin + p*((width - 2*xMargin)/10), (int)zeroAxis + 20);
        }

        // Draw the y-axis labels.
        ArrayList<Double> Ylabels = new ArrayList<Double>();
        for(double val = minY; val<= maxY; val+=rangeY/10)
            Ylabels.add(Math.round(val * 100.0) / 100.0);
        for(int p=0; p<Ylabels.size(); p++)
        {
            g.drawString(""+Ylabels.get(p), xMargin - 30, height - yMargin - p*((height - 2*yMargin)/(Ylabels.size()-1)));
        }

        // Plot points
        g.drawString("Residual Plot", width/2, yMargin - 20);
        f = new Font("Arial", 1, 20);
        g.setFont(f);
        for(int p=0; p<list1.size(); p++)
        {
            double xPos = ( (list1.get(p)-minX)/(rangeX/10) * ((width - 2*xMargin)/10) + xMargin );
            double yPos = (height - yMargin) - ( (list2.get(p)-minY)/(rangeY/10) * ((height - 2*yMargin)/10) + yMargin ) + yMargin;
            g.drawString("*", (int)xPos, (int)yPos);
        }
    }

    /*
     * 
     * 
     * 
     * 
     * Helper Methods
     * 
     * 
     * 
     */

    // Returns a sorted version of the list, does not modify
    private ArrayList<Double> sortedList(int listNum)
    {
        ArrayList<Double> list = new ArrayList<Double>();
        for(int p=0; p<lists.get(listNum).size(); p++)
            list.add(lists.get(listNum).get(p));
        Collections.sort(list);
        return list;
    }

    // Finds the indeces of every outlier
    private ArrayList<Integer> outlierIndeces(int listNum)
    {
        ArrayList<Integer> indeces = new ArrayList<Integer>();
        for(int p=0; p< lists.get(listNum).size(); p++)
            if(isOutlier(listNum, p))
                indeces.add(p);
        return indeces;
    }

    // Finds the frequency for a range
    private int getFrequency(int listNum, double start)
    {
        double min = minVal(listNum), max = maxVal(listNum);
        double classSize = (max - min)/10;
        int freq = 0;
        for(int x = 0; x < lists.get(listNum).size(); x++)
        {
            double num = lists.get(listNum).get(x);
            if(num >= start && num < (start + classSize))
                freq++;
        }
        if(start + classSize >= max)freq++;
        return freq;
    }

    // Finds the largest frequency
    private int findHighestFrequency(int listNum)
    // Used for histograms.
    {
        double min = minVal(listNum), max = maxVal(listNum);
        double classSize = (max - min)/10;
        int mostFreq = 1;
        for(double p = min; p <= max - classSize; p+=classSize)
        {
            int freq = 0;
            for(int x = 0; x < lists.get(listNum).size(); x++)
            {
                freq = getFrequency(listNum, p);
            }
            if(freq > mostFreq) mostFreq = freq;
        }
        return mostFreq;
    }

    // Draws a centered string
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