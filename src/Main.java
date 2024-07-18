package SimpleFileManager.src;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                SimpleFileManager.printMenu();
                if (scanner.hasNextInt()) {
                    int command = Integer.parseInt(scanner.nextLine());
                    if (command < 0 || command > 5) {
                        System.out.println("Введена неправильная команда. Повторите попытку!");
                        continue;
                    }

                    if (command == 0) {
                        break;
                    }

                    SimpleFileManager.handleCommand(command, scanner);
                } else {
                    System.out.println("Введена неправильная команда. Повторите попытку!");
                    scanner.nextLine();
                }
            }
        }
    }
}