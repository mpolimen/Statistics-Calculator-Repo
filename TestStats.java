import java.util.*;
public class TestStats
{
    public static void main(String args [])
    {
        ArrayList<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>();
        /*
         * List One
         */
        ArrayList<Double> L1 = new ArrayList<Double>();
        L1.add(53.0); L1.add(27.0); L1.add(45.0); L1.add(72.0);
        L1.add(52.0); L1.add(4.0); L1.add(24.0); L1.add(55.0);
        L1.add(79.0); L1.add(53.0); L1.add(17.0); L1.add(89.0);
        lists.add(L1);
        /*
         * List Two
         */
        ArrayList<Double> L2 = new ArrayList<Double>();
        L2.add(53.0); L2.add(27.0); L2.add(45.0); L2.add(72.0);
        L2.add(52.0); L2.add(4.0); L2.add(24.0); L2.add(55.0);
        L2.add(79.0); L2.add(53.0); L2.add(17.0);
        lists.add(L2);

        /*
         * List Three
         */
        ArrayList<Double> L3 = new ArrayList<Double>();
        L3.add(44.0); L3.add(84.0); L3.add(58.0); L3.add(15.0);
        L3.add(70.0); L3.add(81.0); L3.add(57.0); L3.add(43.0);
        L3.add(35.0); L3.add(44.0);
        lists.add(L3);
        /*
         * List Four
         */
        ArrayList<Double> L4 = new ArrayList<Double>();
        L4.add(34.0); L4.add(81.0); L4.add(63.0); L4.add(62.0);
        L4.add(55.0); L4.add(64.0); L4.add(57.0); L4.add(48.0);
        L4.add(99.0); L4.add(59.0);
        lists.add(L4);
        /*
         * StatCalc constructor and one-variable statistics.
         */
        StatCalc calc = new StatCalc(lists);
        calc.printOneVarStats(0);
        System.out.println("////////////////////////////////////");
        calc.printOneVarStats(1);
        System.out.println("////////////////////////////////////");
        calc.printTwoVarStats(2,3);
    }
}