import java.util.ArrayList;
import java.util.Collection;

public class Summarizer implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {

        ArrayList<Integer> collectedValues = new ArrayList<Integer>();

        if (input.length() > 0) {

            for (String numString : input.split(",")) {

                try {

                    collectedValues.add(Integer.parseInt(numString.trim()));

                } catch (NumberFormatException e) {
                   System.out.println("Input string should only consist of Integers and commas");
                   return collectedValues;
                }

            }

        }

        return collectedValues;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {

        ArrayList<Integer> inputNumbers = (ArrayList<Integer>) input;
        String output = "";

        for (int i = 0; i < inputNumbers.size(); i++) {

            int current = inputNumbers.get(i);
            int counter = current + 1;

            boolean isSequential = false;
            boolean isReverseOrder = false;


            if (i < inputNumbers.size() - 1) {

                if (counter != inputNumbers.get(i+1)) {
                    isReverseOrder = true;
                    counter = current - 1;
                }

                while (inputNumbers.get(i+1) == counter) {

                    i++;

                    if (isReverseOrder) {
                        counter--;
                    } else {
                        counter++;
                    }

                    isSequential = true;

                    if (i >= inputNumbers.size() - 1)
                        break;

                }
            }

            if (isReverseOrder) {
                counter++;
            } else {
                counter--;
            }

            char commaFinal = ',';
            if (i + 1 >= inputNumbers.size()) {
                commaFinal = ' ';
            }

            if (isSequential) {
                output += current + "-" + counter + commaFinal + " ";
            } else {
                output += current + "" + commaFinal + " ";
            }

        }

        return output.stripTrailing();
    }



}