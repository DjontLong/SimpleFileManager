import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class SimpleFileManager {
    // Создадим константу, которая будет содержать путь директории по умолчанию
    private static Path currentDirectory = Paths.get(".");

    public static void printMenu() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│        Файловый менеджер                                                     │");
        System.out.println("├──────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1 - Добавить папку    2 - Добавить файл    3 - Удалить    4 - Переименовать  │");
        System.out.println("│ 5 - Просмотр          0 - Выйти                                              │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────┘");
        System.out.print("Введите команду: ");
    }

    public static void handleCommand(int command, Scanner scanner) {
        switch (command) {
            case 1:
                System.out.print("Введите имя новой папки: ");
                String newNameDir = scanner.nextLine();

                try {
                    Path newDirPath = currentDirectory.resolve(newNameDir);
                    Files.createDirectory(newDirPath);
                } catch (InvalidPathException | IOException e) {
                    System.out.println("Ошибка: " + e.getMessage() + "\n" + "Повторите попытку!");
                }
                break;

            case 2:


            case 5:
                System.out.print("Для просмотра текущей директории нажмите <.>, либо введите путь: ");
                String choice = scanner.nextLine();

                if (choice.equals(".")) {
                    displayDirectoryContent(currentDirectory);
                } else {
                    Path path = Path.of(choice);
                    if (Files.exists(path)) {
                        displayDirectoryContent(path);
                        currentDirectory = path;
                    } else {
                        System.out.println("Указанной директории не существует! Повторите попытку!");
                    }
                }
                break;
        }
    }

    public static void displayDirectoryContent(Path path) {
        try (DirectoryStream<Path> displayDirectory = Files.newDirectoryStream(path)) {
            for (Path file : displayDirectory) {
                if (Files.isRegularFile(file)) {
                    System.out.println("[FILE] " + file.getFileName());
                } else if (Files.isDirectory(file)) {
                    System.out.println("[DIR] " + file.getFileName());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
