package SimpleFileManager.src;

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

    // Метод, который проверяет регистр букв у создаваемых директорий
//    public static boolean directoryExistsIgnoreCase(Path directory, String name) {
//        try (DirectoryStream<Path> content = Files.newDirectoryStream(directory)) {
//            for (Path entry : content) {
//                if (entry.getFileName().toString().equalsIgnoreCase(name)) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }

    public static void handleCommand(int command, Scanner scanner) {
        switch (command) {
            case 1:
                System.out.print("Для создания папки в текущей директории нажмите <.>, либо укажите путь: ");
                String inputNameDirPath = scanner.nextLine();
                Path path = Path.of(inputNameDirPath);

                try {
                    if (inputNameDirPath.equals(".")) {
                        System.out.print("Введите имя новой папки: ");
                        String inputNewNameDir = scanner.nextLine();

                        Path newDirPath = currentDirectory.resolve(inputNewNameDir);
                        if (Files.exists(newDirPath)) {
                            System.out.println("Папка с таким именем уже существует: " + newDirPath.toAbsolutePath().normalize());
                        } else {
                            Files.createDirectory(newDirPath);
                            System.out.println("Папка успешно создана в директории: " + newDirPath.toAbsolutePath().normalize());
                        }

                    } else {
                        if (Files.exists(path)) {
                            System.out.print("Введите имя новой папки: ");
                            String newNameDir = scanner.nextLine();

                            Path newDirPath = path.resolve(newNameDir);
                            if (Files.exists(newDirPath)) {
                                System.out.println("Папка с таким именем уже существует: " + newDirPath.toAbsolutePath().normalize());
                            } else {
                                Files.createDirectory(newDirPath);
                                currentDirectory = path;
                                System.out.println("Папка успешно создана в директори: " + newDirPath.toAbsolutePath());
                            }
                        } else {
                            System.out.println("Неправильные данные! Повторите попытку!");
                        }
                    }
                } catch (InvalidPathException | IOException e) {
                    System.out.println("Ошибка: " + e.getMessage() + "\n" + "Повторите попытку!");
                }
                break;

            case 2:
                System.out.print("Для создания файла в текущей директории нажмите <.>, либо укажите путь: ");
                String inputNameFilePath = scanner.nextLine();
                Path pathToFile = Path.of(inputNameFilePath);

                try {
                    if (inputNameFilePath.equals(".")) {
                        System.out.print("Введите имя нового файла: ");
                        String inputNewNameFile = scanner.nextLine();

                        Path newFilePath = currentDirectory.resolve(inputNewNameFile);
                        if (Files.exists(newFilePath)) {
                            System.out.println("Файл с таким именем уже существует: " + newFilePath.toAbsolutePath().normalize());
                        } else {
                            Files.createFile(newFilePath);
                            System.out.println("Файл успешно создан в директории: " + newFilePath.toAbsolutePath().normalize());
                        }
                    } else {
                        if (Files.exists(pathToFile)) {
                            System.out.print("Введите имя нового файла: ");
                            String inputNewNameFile = scanner.nextLine();

                            Path pathFile = pathToFile.resolve(inputNewNameFile);
                            if (Files.exists(pathFile)) {
                                System.out.println("Файл с таким именем уже существует: " + pathFile.toAbsolutePath().normalize());
                            } else {
                                Files.createFile(pathFile);
                                System.out.println("Файл успешно создан в директории: " + pathFile.toAbsolutePath());
                            }
                        } else {
                            System.out.println("Неправильные данные! Повторите попытку!");
                        }
                    }
                } catch (InvalidPathException | IOException e) {
                    System.out.println("Ошибка: " + e.getMessage() + "\n" + "Повторите попытку!");
                }
                break;

            case 5:
                System.out.print("Для просмотра текущей директории нажмите <.>, либо укажите путь: ");
                String choice = scanner.nextLine();

                if (choice.equals(".")) {
                    displayDirectoryContent(currentDirectory);
                } else {
                    Path pathDirectory = Path.of(choice);
                    if (Files.exists(pathDirectory)) {
                        displayDirectoryContent(pathDirectory);
                        currentDirectory = pathDirectory;
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
