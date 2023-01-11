
import bullscows.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertTrue;

public class BullsCowsTest {
    public static GameView gameView;
    public static BullsCowsController bullsCowsController;
    public static InputHolder inputHolder;
    public static ResultHolder resultHolder;

    @BeforeClass
    public static void initialization() {
        gameView = new GameView(bullsCowsController);
    }

    private void extracted(int length, int charRangeMax) {
        inputHolder = new InputHolder(length, charRangeMax);
        bullsCowsController = new BullsCowsController();
        resultHolder = bullsCowsController.validSecretCode(inputHolder);
        System.out.println(resultHolder.getMessage());
    }

    @Test
    public void maxMinLengthCharRange() {
        extracted(6, 37);
        System.out.println("Test workWithCharRangeMaxSymbols - ok");
        extracted(6, -2);
        System.out.print("Test workWithCharRangeMinSymbols - ok");
    }

    @Test
    public void theDictionaryIsLessThanWordsLength() {
        extracted(6, 5);
        System.out.println("Test workWithIncorrectLengthOffCharRangeSymbols - ok");
    }

    @Test
    public void testOfUsageOfFullDictionary() {
        extracted(6, 36);
        bullsCowsController.generateSecretCode(inputHolder);
        System.out.println("Test workWithFullDictionarySymbols - ok");
    }

    @Test
    public void testedIncorrectWordsLength() {
        extracted(-2, 5);
        System.out.println("Test workWithIncorrectWordsLengthPassword - ok");
    }

    @Test
    public void testOfWordsInputForLengthPassword() {
        String length = "dsff6";

        while (true) {
            try {
                ResultHolder resultHolder = bullsCowsController.validSecretCode(
                        new InputHolder(Integer.parseInt(length), 6));
                System.out.println(resultHolder.getMessage());
                if (resultHolder.getSuccess()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong input! Use integers from 1 to 10");
                System.out.printf("Error: %s isn't a valid number.\n", e.getMessage().substring(18));
                System.out.println("Test withWordsInputForLengthPassword - ok");
                break;
            }
        }
    }

    @Test
    public void workWithBullsAndCows() {

        Comparator<ResultHolder> comparator = Comparator.comparing(ResultHolder::getMessage);

        ResultHolder resultHolder11 = new ResultHolder(false, String.format("Grade: 0 bull(s) and 3 cow(s)%n"));
        ResultHolder resultHolder12 = bullsCowsController.getGrade("234d", "342f");
        if (comparator.compare(resultHolder11, resultHolder12) == 0) {
            System.out.println("Test workWithBullsAndCowsWithThreeLuckyCharacter - ok");
        }

        ResultHolder resultHolder21 = new ResultHolder(true, String.format("Grade: 1 bulls\n"));
        ResultHolder resultHolder22 = bullsCowsController.getGrade("0", "0");
        if (comparator.compare(resultHolder21, resultHolder22) == 0) {
            System.out.println("Test workWithBullsAndCowsWithOneLuckyCharacter - ok");
        }
    }

    @Test//?????
    public void testInputIncorrectLengthSymbolsToGuessThePassword() {
        inputHolder = new InputHolder(5, 6);
        String secret = bullsCowsController.generateSecretCode(inputHolder);
        String input = "f62";
        int turn = 1;

        while (true) {
            System.out.printf("Turn %d:%n", turn);

            if (input.length() > inputHolder.getLength() || input.length() < inputHolder.getLength()) {
                System.out.println("Please enter the number of characters which is equal to " + inputHolder.getLength());
            }

            ResultHolder resultHolder = bullsCowsController.getGrade(secret, "f62");
            System.out.println(resultHolder.getMessage());
            if (resultHolder.getSuccess()) {
                break;
            }
            break;
        }
        System.out.println("Test InputIncorrectLengthSymbolsToGuessThePassword - ok");
    }

    @Test
    public void testCorrectGenerateSecretCode() {
        String secretCode1 = bullsCowsController.generateSecretCode(new InputHolder(36, 36));
        char[] original = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
                'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] symbolsToControl = secretCode1.toCharArray();
        Arrays.sort(symbolsToControl);
        if (Arrays.equals(original, symbolsToControl)) {
            System.out.println("Char[] arrays are the same");
        }
        System.out.println("Test testCorrectGenerateSecretCode - ok");
    }
}