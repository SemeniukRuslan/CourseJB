package bullscows;

import java.util.Scanner;

public class GameView {
    private final BullsCowsController gameController;
    private Scanner scanner;

    public GameView(BullsCowsController game) {
        this.gameController = game;
        this.scanner = new Scanner(System.in);
    }

    public void runGame() {

        InputHolder res = inputToGenerateSecretCode();
        String secret = gameController.generateSecretCode(res);

        System.out.println("Okay, let's start a game!");
        int turn = 1;

        while (true) {
            System.out.printf("Turn %d:%n", turn);

            String input = scanner.next();
            if (input.length() > res.getLength() || input.length() < res.getLength()) {
                System.out.println("Please enter the number of characters which is equal to " + res.getLength());
            }

            ResultHolder resultHolder = gameController.getGrade(secret, input);
            System.out.println(resultHolder.getMessage());
            if (resultHolder.getSuccess()) {
                break;
            }
            turn++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public InputHolder inputToGenerateSecretCode() {
        int charRange;
        int length;
        while (true) {
            String input = null;
            try {
                System.out.println("Input the length of the secret code:");
                length = scanner.nextInt();
                if (length > 9) {
                    System.out.println("Wrong input! Use integers from 1 to 10");
                    inputToGenerateSecretCode();
                }
                input = Integer.toString(length);
                System.out.println("Input the number of possible symbols in the code:");
                charRange = scanner.nextInt();
                ResultHolder resultHolder = gameController.validSecretCode(new InputHolder(length, charRange));
                System.out.println(resultHolder.getMessage());
                if (resultHolder.getSuccess()) {
                    break;
                }
            } catch (Exception e) {
                System.out.printf("Error: %s isn't a valid number.\n", input);
                scanner.next();
            }
        }
        return new InputHolder(length, charRange);
    }
}
