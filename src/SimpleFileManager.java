import javax.crypto.spec.PSource;
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
            case 1 -> createDirectory(scanner);
            case 2 -> createFile(scanner);
            case 3 -> deleteFileOrDirectory(scanner);
            case 4 -> renameFileOrDirectory(scanner);
            case 5 -> displayDirectoryContent(scanner);
            case 0 -> System.out.println("Выход из программы ... ");
            default -> System.out.println("Неизвестная команда. Повторите попытку!");
        }
    }

    public static void createDirectory(Scanner scanner) {
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
    }

    public static void createFile(Scanner scanner) {
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
    }

    public static void renameFileOrDirectory(Scanner scanner) {
        System.out.print("Чтобы переименовать - укажите путь до файла или папки: ");
        String inputPathRename = scanner.nextLine();
        Path pathToFileStart = Path.of(inputPathRename);

        if (Files.exists(pathToFileStart)) {
            System.out.println("Задайте новое имя файла или папки: ");
            String newNameFileOrDir = scanner.nextLine();

            // Получаем родительский путь
            Path parentPath = pathToFileStart.getParent();
            // Формируем новый путь с новым именем
            Path newPathForRenameFileOrDir = parentPath.resolve(newNameFileOrDir).normalize();

            try {
                Files.move(pathToFileStart, newPathForRenameFileOrDir);
                System.out.println("Файл или папка успешно создан переименован в директории: " + newPathForRenameFileOrDir.toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Ошибка при переименовании: " + e.getMessage());
            }
        } else {
            System.out.println("Файл или директория не существуют.");
        }
    }

    public static void displayDirectoryContent(Scanner scanner) {
        System.out.print("Для просмотра текущей директории нажмите <.>, либо укажите путь: ");
        String inputPath = scanner.nextLine();
        Path pathDir = Path.of(inputPath);
        if (Files.exists(pathDir)) {
            try (DirectoryStream<Path> displayDirectory = Files.newDirectoryStream(pathDir)) {

                // Добавляем счетчик файлов
                int countFiles = 0;
                // Добавляем счетчик папок
                int countDir = 0;

                for (Path file : displayDirectory) {

                    // Добавляем отображение размера файлов и папок
                    long sizeInBytes = Files.size(file);
                    // Переводим в килобайты
                    double sizeFileKB = Math.round(sizeInBytes / 1024.0);

                    if (Files.isRegularFile(file)) {
                        System.out.println("[FILE] " + "[" + sizeFileKB + " KB" + "] " + file.getFileName());
                        countFiles++;
                    } else if (Files.isDirectory(file.toAbsolutePath())) {
                        long dirSize = calculateDirectorySize(file);
                        double sizeDirKB = Math.round(dirSize / 1024.0);

                        System.out.println("[DIR] " + "[" + sizeDirKB + " KB" + "] " + file.getFileName());
                        countDir++;
                    }
                    currentDirectory = pathDir.toAbsolutePath().normalize();
                }

                System.out.println("┌──────────────┐");
                System.out.println("|   files: " + countFiles);
                System.out.println("|   folders: " + countDir);
                System.out.println("└────────────────────────────────────────────────┘");

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Неправильные данные! Повторите попытку!");
        }
    }

    public static void deleteFileOrDirectory(Scanner scanner) {
        System.out.print("Чтобы удалить - укажите путь до файла или папки: ");
        String inputPathDelete = scanner.nextLine();
        Path pathForDelete = Path.of(inputPathDelete);

        try {
            if (Files.isDirectory(pathForDelete)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathForDelete)) {
                    int countFiles = 0;
                    for (Path file : stream) {
                        countFiles++;
                    }
                    if (countFiles > 0) {
                        System.out.println("Папка содержит вложенные файлы или подпапки!\n" +
                                "Вы уверены, что хотите удалить?\n" +
                                "Для удаления нажмите <Y>, для отмены <N>");
                        String inputActionForDelete = scanner.nextLine();
                        if (inputActionForDelete.equalsIgnoreCase("Y")) {
                            deleteDirectoryRecursively(pathForDelete);
                            System.out.println("Папка успешно удалена по пути: " + pathForDelete.toAbsolutePath().normalize());
                        } else if (inputActionForDelete.equalsIgnoreCase("N")) {
                            System.out.println("Отмена удаления ...");
                        } else {
                            System.out.println("Введена неправильная команда. Повторите попытку!");
                        }
                    } else {
                        Files.delete(pathForDelete);
                        System.out.println("Файл у спешно удалён по пути: " + pathForDelete.toAbsolutePath().normalize());
                    }
                }
            } else if (Files.isRegularFile(pathForDelete)) {
                Files.delete(pathForDelete);
                System.out.println("Файл успешно удалён по пути: " + pathForDelete.toAbsolutePath().normalize());
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    public static void deleteDirectoryRecursively(Path directory) throws IOException {
        if (Files.isDirectory(directory)) {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {
                for (Path file : files) {
                    deleteDirectoryRecursively(file);
                }
            }
        }
        Files.delete(directory);
    }

    public static long calculateDirectorySize(Path directory) throws IOException {
        long size = 0;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {
            for (Path file : files) {
                if (Files.isRegularFile(file)) {
                    size += Files.size(file);
                } else if (Files.isDirectory(file)) {
                    size += calculateDirectorySize(file);
                }
            }
        }
        return size;
    }
}
