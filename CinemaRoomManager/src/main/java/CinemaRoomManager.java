import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class CinemaRoomManager {

    public static int rowsAll;
    public static int seatsInEachRowAll;
    public static int rows;
    public static int seatsInEachRow;
    public static String[][] placeCinema;
    public static int numberOfTicketsSold;
    public static int totalIncome;
    public static int currentIncome;
    public static final String FREE_SEAT_LETTER = " S";
    public static final String RESERVED_SEAT_LETTER = " B";
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initialization();
        printChoose();

        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    show();
                    printChoose();
                    break;
                case 2:
                    printOnePlaceCinema();
                    printChoose();
                    break;
                case 3:
                    statistics();
                    printChoose();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unexpected action.");
            }
        }
    }

    public static void statistics() {
        double percentage = (double) numberOfTicketsSold * 100 / (rowsAll * seatsInEachRowAll);
        String result = String.format("%.2f", percentage);
        System.out.println();
        System.out.println("Number of purchased tickets: " + numberOfTicketsSold);
        System.out.println("Percentage: " + result + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome + "\n");
    }

    public static void printChoose() {
        System.out.print("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit\n> ");
    }

    public static void initialization() {
        System.out.println("Enter the number of rows:");
        rowsAll = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInEachRowAll = scanner.nextInt();
        System.out.println();
        System.out.print("Cinema:\n ");
        placeCinema = new String[rowsAll + 1][seatsInEachRowAll + 1];
        for (int i = 0; i < placeCinema.length; i++) {
            for (int j = 0; j < placeCinema[i].length; j++) {
                placeCinema[i][j] = FREE_SEAT_LETTER;
                placeCinema[i][0] = String.valueOf(i);
                placeCinema[0][j] = j + " ";
                placeCinema[0][0] = " ";
                System.out.print(placeCinema[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void show() {
        System.out.println();
        System.out.print("Cinema:\n ");
        for (int i = 0; i < placeCinema.length; i++) {
            for (int j = 0; j < placeCinema[0].length; j++) {
                System.out.print(placeCinema[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printOnePlaceCinema() {
        System.out.println();
        System.out.println("Enter a row number:");
        rows = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        seatsInEachRow = scanner.nextInt();
        System.out.println();

        if (rows > rowsAll || seatsInEachRow > seatsInEachRowAll) {
            System.out.println("Wrong input!");
            printOnePlaceCinema();
        } else if (placeCinema[rows][seatsInEachRow].equals(RESERVED_SEAT_LETTER)) {
            System.out.println("That ticket has already been purchased!");
            printOnePlaceCinema();
        } else {
            placeCinema[rows][seatsInEachRow] = RESERVED_SEAT_LETTER;
            show();

            ticketPrice(rowsAll, seatsInEachRowAll);
            System.out.println();

            if (placeCinema[rows][seatsInEachRow].equals(RESERVED_SEAT_LETTER)) {
                numberOfTicketsSold++;
            }
        }
    }

    public static void ticketPrice(int rowsAll, int seatsAll) {
        int tickPrice = 0;
        int[][] onePlace = new int[rowsAll + 1][seatsAll + 1];

        if ((seatsAll * rowsAll) <= 60) {
            totalIncome = seatsAll * rowsAll * 10;
            currentIncome += 10;
            System.out.println("Ticket price: $10");
            return;
        }
        if (rowsAll % 2 == 0) {
            for (int i = 1; i < onePlace.length; i++) {
                tickPrice = (rows < (rowsAll / 2)) ? 8 : 10;
            }
            currentIncome += tickPrice;
            totalIncome = seatsAll * rowsAll * 9;
        } else {
            for (int i = 1; i < onePlace.length; i++) {
                tickPrice = (rows > (rowsAll / 2)) ? 8 : 10;
            }
            currentIncome += tickPrice;
            totalIncome = (((rowsAll / 2) * seatsInEachRowAll * 10) + (((rowsAll / 2) + 1) * seatsInEachRowAll * 8));
            System.out.println("Ticket price: $" + tickPrice);
        }
    }
}