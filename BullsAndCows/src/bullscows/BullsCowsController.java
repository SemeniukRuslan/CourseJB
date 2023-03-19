package bullscows;

import java.util.ArrayList;
import java.util.List;

public class BullsCowsController {

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
        return buffer.substring(0, inputHolder.getLength());
    }

    // 12345 -> *****
    public String starPlaceLetter(int length) {
        return new String().replace("\\d{10}", "*");
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
        if (inputHolder.getLength() > sizeOfAlphabet || inputHolder.getLength() <= 0
                || inputHolder.getRange() < inputHolder.getLength()) {
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

