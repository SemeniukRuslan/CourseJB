package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BullsCows {
    static class Main {
        public static void main(String[] args) {
            BullsCowsController controller = new BullsCowsController();
            GameView gameview = new GameView(controller);
            gameview.runGame();
        }
    }

    static class GameView {
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
                if (input.length() > res.length || input.length() < res.length) {
                    System.out.println("Please enter the number of characters which is equal to " + res.length);
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
                    if (resultHolder.success) {
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

    static class BullsCowsController {

        // creating a random password with a check for uniqueness (there should not be repeated elements)
        public String generateSecretCode(InputHolder inputHolder) {
            StringBuilder buffer = new StringBuilder(inputHolder.getLength());
            // generate code alphabet
            // "0 1 2 3...9 a b c d e c"
            // get random index considering array size in order to get element from alphabet and remove afterwards
            List<Character> alphabet = new ArrayList<>();
            int position = 0;
            for (int i = 0; i < inputHolder.getRange(); i++) {
                if (i < 10) {
                    alphabet.add((char) (i + 48));
                } else {
                    alphabet.add((char) (position + 97));
                    position++;
                }
            }

            while (!alphabet.isEmpty()) {
                int randomIndex = (int) (Math.random() * alphabet.size());
                buffer.append(alphabet.remove(randomIndex));
            }

            System.out.printf("The secret is prepared: %s %n", starPlaceLetter(inputHolder.getLength()));
            return buffer.substring(0, inputHolder.length);
        }

        // 12345 -> *****
        public String starPlaceLetter(int length) {
            return "*".repeat(length);
        }

        public ResultHolder getGrade(String secret, String guess) {
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < secret.length(); i++) {
                for (int j = 0; j < guess.length(); j++) {
                    if (secret.charAt(i) == guess.charAt(j)) {
                        if (i == j) {
                            bulls++;
                        } else {
                            cows++;
                        }
                    }
                }
            }

            if (secret.equals(guess)) {
                return new ResultHolder(true,
                        String.format("Grade: " + guess.length() + " bulls\n"));
            }


            if (bulls != 0 || cows != 0) {
                return new ResultHolder(false,
                        String.format("Grade: %d bull(s) and %d cow(s)%n", bulls, cows));
            }

            return new ResultHolder(false, "Grade: None.");
        }

        public ResultHolder validSecretCode(InputHolder inputHolder) {
            int sizeOfAlphabet = 36;
            if (inputHolder.length > sizeOfAlphabet || inputHolder.length <= 0 || inputHolder.getRange() < inputHolder.length) {
                return new ResultHolder(false,
                        String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n",
                                inputHolder.getLength(), inputHolder.getRange()));
            }
            if (inputHolder.getRange() > sizeOfAlphabet) {
                return new ResultHolder(false, "Error: maximum number of possible symbols in the code is 36 (0-9, A-Z)");
            }
            return new ResultHolder(true, "");
        }
    }

    static class ResultHolder {
        private Boolean success;
        private String message;

        public ResultHolder(Boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public Boolean getSuccess() {
            return success;
        }
        public String getMessage() {
            return message;
        }
    }

    static class InputHolder {
        private int length;
        private int range;

        public InputHolder(int length, int range) {
            this.length = length;
            this.range = range;
        }

        public int getLength() {
            return length;
        }
        public int getRange() {
            return range;
        }
    }
}