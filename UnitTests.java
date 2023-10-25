import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class UnitTests {

    private Summarizer summarizer;

    @BeforeEach
    public void setup() {

        summarizer = new Summarizer();
    }

    @Test
    public void testCollect() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> expected = new ArrayList<>(Arrays.asList(1,3,6,7,8,12,13,14,15,21,22,23,24,31));
        Collection<Integer> result = summarizer.collect(input);
        assertEquals(expected, result);
    }

    @Test
    public void testSummarizeCollection() {
        Collection<Integer> input = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";
        String result = summarizer.summarizeCollection(input);
        assertEquals(expected, result);
    }

    @Test
    public void testDescendingOrderAndDuplicates() {
        Collection<Integer> list = summarizer.collect("1,2,3,4,5,5,4,3,2,1");
        String actual = summarizer.summarizeCollection(list);
        String expected = "1-5, 5-1";
        assertEquals(expected, actual);

    }

    @Test
    public void testDuplicates() {
        Collection<Integer> list = summarizer.collect("1,2,3,4,5,1,2,3,4,5");
        String actual = summarizer.summarizeCollection(list);
        String expected = "1-5, 1-5";
        assertEquals(expected, actual);
    }

    @Test
    public void testDescendingOrder() {
        Collection<Integer> list = summarizer.collect("5,4,3,2,1");
        String actual = summarizer.summarizeCollection(list);
        String expected = "5-1";
        assertEquals(expected, actual);
    }


    @Test
    public void testOrderPreservation() {
        Collection<Integer> list = summarizer.collect("34,8,10,1,2,3,4,5,12,14");
        String actual = summarizer.summarizeCollection(list);
        String expected = "34, 8, 10, 1-5, 12, 14";
        assertEquals(expected, actual);
    }

    @Test
    public void testLargeNumbers() {
        String input = "1000000000,1000000001";
        String expected = "1000000000-1000000001";
        String result = summarizer.summarizeCollection(summarizer.collect(input));
        assertEquals(expected, result);
    }

    @Test
    public void testVeryLongSequences() {
        String input = "";

        String comma = ",";
        for (int i = 1; i <= 10000; i++) {

            if (i == 10000) {
                comma = "";
            }

            input += i + comma;

        }

        String expected = "1-10000";
        String result = summarizer.summarizeCollection(summarizer.collect(input));
        assertEquals(expected, result);
    }

    @Test
    public void testMixedOrder() {
        String input = "1,3,2,5,4";
        String expected = "1, 3-2, 5-4";
        String result = summarizer.summarizeCollection(summarizer.collect(input));
        assertEquals(expected, result);
    }

    @Test
    public void testNegativeNumbers() {
        String input = "-3,-2,-1,0,1";
        String expected = "-3-1";
        String result = summarizer.summarizeCollection(summarizer.collect(input));
        assertEquals(expected, result);
    }

    @Test
    public void testNoSequence() {
        String input = "1,5,8";
        String expected = "1, 5, 8";
        String result = summarizer.summarizeCollection(summarizer.collect(input));
        assertEquals(expected, result);
    }

    @Test
    public void testCollectWithSpaces() {
        String input = "1,2, 3 , 4 ";
        Collection<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3,4));
        Collection<Integer> result = summarizer.collect(input);
        assertEquals(expected, result);
    }

    @Test
    public void testInvalidInput() {
        String input = "1,2,t,4";
        Collection<Integer> result = summarizer.collect(input);
        assertEquals(Arrays.asList(1,2), result);
    }

    @Test
    public void testOnlyCommas() {
        String input = ",,,";
        Collection<Integer> result = summarizer.collect(input);
        String summarized = summarizer.summarizeCollection(result);
        assertEquals("", summarized);
    }

    @Test
    public void testEmptyInput() {
        String input = "";
        Collection<Integer> result = summarizer.collect(input);
        String summarized = summarizer.summarizeCollection(result);
        assertEquals("", summarized);
    }

    @Test
    public void testSingleNumber() {
        String input = "5";
        Collection<Integer> result = summarizer.collect(input);
        String summarized = summarizer.summarizeCollection(result);
        assertEquals("5", summarized);
    }

    @Test
    public void testTwoNumbersSequential() {
        String input = "5,6";
        Collection<Integer> result = summarizer.collect(input);
        String summarized = summarizer.summarizeCollection(result);
        assertEquals("5-6", summarized);
    }

}
