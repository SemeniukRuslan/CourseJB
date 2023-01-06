package bullscows;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;

public class BullsCowsTest {
    public static BullsCows.GameView gameView;
    public static BullsCows.BullsCowsController bullsCowsController;
    public static BullsCows.InputHolder inputHolder;
    public static BullsCows.ResultHolder resultHolder;

    //чи потрібно зрівнювати в кінці кожного метода з assertom?

    //testOfWordsInputForLengthPassword закинув повністю кусок метода з класу, за того, що вилітає Exception, а обробляю його тільки в тому методі,
    //але у методі, який з класу, не можу вивести другий sout, бо в блоці try input не записався і пише null. Як з цим бути?

    //testInputIncorrectLengthSymbolsToGuessThePassword мінімально змінена логіка в тесті, але для того, щоб перевірити лише лічильник і нову умову
    //довжину при угадуванні, вона не повина перевищувати і не повинна бути менша довжини самого пароля

    @BeforeClass
    public void initialization() {
        bullsCowsController = new BullsCows.BullsCowsController();
        gameView = new BullsCows.GameView(bullsCowsController);
    }

    private static void extracted(int length, int charRangeMax) {
        inputHolder = new BullsCows.InputHolder(length, charRangeMax);
        resultHolder = bullsCowsController.validSecretCode(inputHolder);
        System.out.println(resultHolder.getMessage());
    }

    @Test
    public void maxMinLengthCharRange() {
        extracted(6, 37);
        System.out.println("Test workWithCharRangeMaxSymbols - ok");
        extracted(6, -2);
        System.out.println("Test workWithCharRangeMinSymbols - ok");
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

    @Test//?????
    public void testOfWordsInputForLengthPassword() {
        String length = "dsff6";

        while (true) {
            try {
                BullsCows.ResultHolder resultHolder = bullsCowsController.validSecretCode(
                        new BullsCows.InputHolder(Integer.parseInt(length), 6));
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

        Comparator<BullsCows.ResultHolder> comparator = Comparator.comparing(BullsCows.ResultHolder::getMessage);

        BullsCows.ResultHolder resultHolder11 = new BullsCows.ResultHolder(false, String.format("Grade: 0 bull(s) and 3 cow(s)%n"));
        BullsCows.ResultHolder resultHolder12 = bullsCowsController.getGrade("234d", "342f");
        if (comparator.compare(resultHolder11, resultHolder12) == 0) {
            System.out.println("Test workWithBullsAndCowsWithThreeLuckyCharacter - ok");
        }

        BullsCows.ResultHolder resultHolder21 = new BullsCows.ResultHolder(true, String.format("Grade: 1 bulls\n"));
        BullsCows.ResultHolder resultHolder22 = bullsCowsController.getGrade("0", "0");
        if (comparator.compare(resultHolder21, resultHolder22) == 0) {
            System.out.println("Test workWithBullsAndCowsWithOneLuckyCharacter - ok");
        }
    }

    @Test//?????
    public void testInputIncorrectLengthSymbolsToGuessThePassword() {
        inputHolder = new BullsCows.InputHolder(5, 6);
        String secret = bullsCowsController.generateSecretCode(inputHolder);
        String input = "f62";
        int turn = 1;

        while (true) {
            System.out.printf("Turn %d:%n", turn);

            if (input.length() > inputHolder.getLength() || input.length() < inputHolder.getLength()) {
                System.out.println("Please enter the number of characters which is equal to " + inputHolder.getLength());
            }

            BullsCows.ResultHolder resultHolder = bullsCowsController.getGrade(secret, "f62");
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
        String secretCode1 = bullsCowsController.generateSecretCode(new BullsCows.InputHolder(36, 36));
        char[] original = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
                'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] symbolsToControl = secretCode1.toCharArray();
        Arrays.sort(symbolsToControl);
        if (Arrays.compare(original, symbolsToControl) == 0) {
            System.out.println("Char[] arrays are the same");
        }

        System.out.println("Test testCorrectGenerateSecretCode - ok");
    }


}