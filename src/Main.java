import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int command = -1;

            do {
                SimpleFileManager.printMenu();
                if (scanner.hasNextInt()) {
                    command = Integer.parseInt(scanner.nextLine());
                } else {
                    scanner.nextLine();
                }
                SimpleFileManager.handleCommand(command, scanner);
            } while (command != 0);
        }
    }
}