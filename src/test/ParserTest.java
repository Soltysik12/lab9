import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ParserTest {

    @Test
    public void getID() throws Exception {
        Parser parser = new Parser();
        Assert.assertTrue("getID - assertTrue", parser.getID("Sławomir Kłosowski") == 174);
        Assert.assertTrue("getID - assertTrue", parser.getID("Robert Kropiwnicki") == 200);
        Assert.assertTrue("getID - assertTrue", parser.getID("Adam Abramowicz") == 1);
        Assert.assertTrue("getID - assertTrue", parser.getID("Grzegorz Piechowiak") == 1408);
    }

    @Test
    public void getExpenses() throws Exception {
        Parser parser = new Parser();
        Assert.assertEquals("getExpences - assertEquals",
                parser.getExpenses(174), 277061.64,0.00);
        Assert.assertEquals("getExpences - assertEquals",
                parser.getExpenses(200), 284645.21,0.00);
        Assert.assertEquals("getExpences - assertEquals",
                parser.getExpenses(460), 302261.06,0.00);
    }

    @Test
    public void getSpecificExpences() throws Exception {
        Parser parser = new Parser();
        Assert.assertEquals("getSpecificExpences - assertEquals",
                parser.getSpecificExpences("Koszty drobnych napraw i remontów lokalu biura poselskiego",174),8418.42,0.00);
    }

    @Test
    public void averageValue() throws Exception {
        List<Integer> ids = new LinkedList<>();
        ids.add(174);
        ids.add(200);
        ids.add(460);
        Parser parser = new Parser(ids);
        System.out.println(parser.averageValue());
        Assert.assertEquals("averageValue - assertTrue",parser.averageValue(),287989.0,0.00);

    }

    @Test
    public void mostTrips() throws Exception {
        List<Integer> ids = new LinkedList<>();
        Parser parser = new Parser(ids);
        ids.add(174);ids.add(200);ids.add(460);

        List<String> result = new LinkedList<>();
        result.add("Robert Kropiwnicki");
        result.add("Jerzy Żyżyński");
        Assert.assertTrue("mostTrips - assertTrue", Arrays.equals(result.toArray(), parser.mostTrips().toArray()));
    }

    @Test
    public void largestTrip() throws Exception {
        List<Integer> ids = new LinkedList<>();
        Parser parser = new Parser(ids);
        ids.add(174);ids.add(200);ids.add(460);

        List<String> result = new LinkedList<>();
        result.add("Robert Kropiwnicki");
        Assert.assertTrue("largestTrip - assertTrue",Arrays.equals(result.toArray(),parser.largestTrip().toArray()));

        result = new LinkedList<>();
        ids = new LinkedList<>();
        ids.add(174);ids.add(4);
        parser = new Parser(ids);
        Assert.assertTrue("largestTrip - assertTrue",Arrays.equals(result.toArray(),parser.largestTrip().toArray()));
        ids.clear();
        ids.add(1740);
        ids.add(1100);
        parser = new Parser(ids);
        Assert.assertTrue("largestTrip - assertTrue", parser.largestTrip().toArray().length == 0);
    }

    @Test
    public void mostExpensiveTrip() throws Exception {
        List<Integer> ids = new LinkedList<>();
        Parser parser = new Parser(ids);
        ids.add(200);ids.add(174);ids.add(460);


        List<String> result = new LinkedList<>();
        result.add("Robert Kropiwnicki");
        Assert.assertTrue("mostExpensiveTrip - assertTrue",Arrays.equals(result.toArray(),parser.mostExpensiveTrip().toArray()));

        result = new LinkedList<>();
        ids = new LinkedList<>();
        ids.add(174);ids.add(4);
        parser = new Parser(ids);
        Assert.assertTrue("mostExpensiveTrip - assertTrue",Arrays.equals(result.toArray(),parser.mostExpensiveTrip().toArray()));
        ids.clear();
        ids.add(1740);
        ids.add(174);
        parser = new Parser(ids);
        Assert.assertTrue("mostExpensiveTrip - assertTrue", parser.mostExpensiveTrip().toArray().length == 0);
    }

    @Test
    public void visitorsItaly() throws Exception {
        List<Integer> ids = new LinkedList<>();
        Parser parser = new Parser(ids);
        ids.add(200);ids.add(174);ids.add(460);


        List<String> result = new LinkedList<>();
        Assert.assertTrue("mostExpensiveTrip - assertTrue",Arrays.equals(result.toArray(),parser.visitorsItaly().toArray()));

        result = new LinkedList<>();
        ids = new LinkedList<>();
        ids.add(174);ids.add(4);
        parser = new Parser(ids);
        Assert.assertTrue("mostExpensiveTrip - assertTrue",Arrays.equals(result.toArray(),parser.visitorsItaly().toArray()));
    }
}