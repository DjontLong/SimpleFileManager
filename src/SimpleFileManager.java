import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            case 0 -> System.out.println(Messages.EXIT_PROGRAM);
            default -> System.out.println(Messages.UNKNOWN_COMMAND);
        }
    }

    public static void createDirectory(Scanner scanner) {
        System.out.print(Messages.CREATE_FOLDER);
        String inputNameDirPath = scanner.nextLine();
        Path path = Path.of(inputNameDirPath);

        try {
            if (inputNameDirPath.equals(".")) {
                System.out.print(Messages.ENTER_NEW_NAME_FOLDER);
                String inputNewNameDir = scanner.nextLine();

                Path newDirPath = currentDirectory.resolve(inputNewNameDir);
                if (Files.exists(newDirPath)) {
                    System.out.println(Messages.FOLDER_ALREADY_EXISTS + newDirPath.toAbsolutePath().normalize());
                } else {
                    Files.createDirectory(newDirPath);
                    System.out.println(Messages.FOLDER_SUCCESSFULLLY_CREATED + newDirPath.toAbsolutePath().normalize());
                }

            } else {
                if (Files.exists(path)) {
                    System.out.print(Messages.ENTER_NEW_NAME_FOLDER);
                    String newNameDir = scanner.nextLine();

                    Path newDirPath = path.resolve(newNameDir);
                    if (Files.exists(newDirPath)) {
                        System.out.println(Messages.FOLDER_ALREADY_EXISTS + newDirPath.toAbsolutePath().normalize());
                    } else {
                        Files.createDirectory(newDirPath);
                        currentDirectory = path;
                        System.out.println(Messages.FOLDER_SUCCESSFULLLY_CREATED + newDirPath.toAbsolutePath());
                    }
                } else {
                    System.out.println(Messages.INVALID_DATA);
                }
            }
        } catch (InvalidPathException | IOException e) {
            System.out.println("Ошибка: " + e.getMessage() + "\n" + "Повторите попытку!");
        }
    }

    public static void createFile(Scanner scanner) {
        System.out.print(Messages.CREATE_FILE);
        String inputNameFilePath = scanner.nextLine();
        Path pathToFile = Path.of(inputNameFilePath);

        try {
            if (inputNameFilePath.equals(".")) {
                System.out.print(Messages.ENTER_NEW_NAME_FILE);
                String inputNewNameFile = scanner.nextLine();

                Path newFilePath = currentDirectory.resolve(inputNewNameFile);
                if (Files.exists(newFilePath)) {
                    System.out.println(Messages.FILE_ALREADY_EXISTS + newFilePath.toAbsolutePath().normalize());
                } else {
                    Files.createFile(newFilePath);
                    System.out.println(Messages.FILE_SUCCESSFULlLY_CREATED + newFilePath.toAbsolutePath().normalize());
                }
            } else {
                if (Files.exists(pathToFile)) {
                    System.out.print(Messages.ENTER_NEW_NAME_FILE);
                    String inputNewNameFile = scanner.nextLine();

                    Path pathFile = pathToFile.resolve(inputNewNameFile);
                    if (Files.exists(pathFile)) {
                        System.out.println(Messages.FILE_ALREADY_EXISTS + pathFile.toAbsolutePath().normalize());
                    } else {
                        Files.createFile(pathFile);
                        System.out.println(Messages.FILE_SUCCESSFULlLY_CREATED + pathFile.toAbsolutePath());
                    }
                } else {
                    System.out.println(Messages.INVALID_DATA);
                }
            }
        } catch (InvalidPathException | IOException e) {
            System.out.println("Ошибка: " + e.getMessage() + "\n" + "Повторите попытку!");
        }
    }

    public static void renameFileOrDirectory(Scanner scanner) {
        System.out.print(Messages.RENAME_FILE_FOLDER);
        String inputPathRename = scanner.nextLine();
        Path pathToFileStart = Path.of(inputPathRename);

        if (Files.exists(pathToFileStart)) {
            System.out.print(Messages.CREATE_NEW_NAME);
            String newNameFileOrDir = scanner.nextLine();

            // Получаем родительский путь
            Path parentPath = pathToFileStart.getParent();
            // Формируем новый путь с новым именем
            Path newPathForRenameFileOrDir = parentPath.resolve(newNameFileOrDir).normalize();

            try {
                Files.move(pathToFileStart, newPathForRenameFileOrDir);
                System.out.println(Messages.FILE_SUCCESSFULlLY_RENAME + newPathForRenameFileOrDir.toAbsolutePath());
            } catch (IOException e) {
                System.out.println(Messages.RENAME_ERROR + e.getMessage());
            }
        } else {
            System.out.println(Messages.FILE_OR_FOLDER_NOT_EXIST);
        }
    }

    public static void displayDirectoryContent(Scanner scanner) {
        System.out.print(Messages.DISPLAY_CURRENT_DIRECTORY);
        String inputPath = scanner.nextLine();
        Path pathDir = Path.of(inputPath);

        if (Files.exists(pathDir)) {
            try (DirectoryStream<Path> displayDirectory = Files.newDirectoryStream(pathDir)) {
                int countFiles = 0;
                int countDir = 0;

                for (Path file : displayDirectory) {
                    try {
                        if (Files.isRegularFile(file)) {
                            long sizeInBytes = Files.size(file);
                            double sizeFileKB = Math.round(sizeInBytes / 1024.0);

                            System.out.println("[FILE] " + "[" + sizeFileKB + " KB" + " | " + getFormattedDate(file) + "] " + file.getFileName());
                            countFiles++;
                        } else if (Files.isDirectory(file)) {
                            long dirSize = calculateDirectorySize(file);
                            double sizeDirKB = Math.round(dirSize / 1024.0);
                            System.out.println("[DIR] " + "[" + sizeDirKB + " KB" + " | " + getFormattedDate(file) + "] " + file.getFileName());
                            countDir++;
                        }
                    } catch (IOException e) {
                        // Игнорируем ошибки доступа
                    }
                }

                System.out.println("┌──────────────┐");
                System.out.println("|   files: " + countFiles);
                System.out.println("|   folders: " + countDir);
                System.out.println("└──────────────┘");

                currentDirectory = pathDir.toAbsolutePath().normalize();

            } catch (IOException e) {
//                System.out.println("Ошибка при чтении содержимого директории: " + e.getMessage());
            }
        } else {
            System.out.println("Неправильные данные! Повторите попытку!");
        }
    }

    public static void deleteFileOrDirectory(Scanner scanner) {
        System.out.print(Messages.DELETE_FILE_FOLDER);
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
                        System.out.println(Messages.FOLDER_CONTAINS_SUBFOLDER);
                        String inputActionForDelete = scanner.nextLine();
                        if (inputActionForDelete.equalsIgnoreCase("Y")) {
                            deleteDirectoryRecursively(pathForDelete);
                            System.out.println(Messages.FOLDER_SUCCESSFULLY_DELETE + pathForDelete.toAbsolutePath().normalize());
                        } else if (inputActionForDelete.equalsIgnoreCase("N")) {
                            System.out.println(Messages.CANCEL_DELETE);
                        } else {
                            System.out.println(Messages.INVALID_COMMAND);
                        }
                    } else {
                        Files.delete(pathForDelete);
                        System.out.println(Messages.FILE_SUCCESSFULLY_DELETE + pathForDelete.toAbsolutePath().normalize());
                    }
                }
            } else if (Files.isRegularFile(pathForDelete)) {
                Files.delete(pathForDelete);
                System.out.println(Messages.FILE_SUCCESSFULLY_DELETE + pathForDelete.toAbsolutePath().normalize());
            }
        } catch (IOException e) {
            System.out.println(Messages.ERROR_OCCURRED + e.getMessage());
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
                try {
                    if (Files.isRegularFile(file)) {
                        size += Files.size(file);
                    } else if (Files.isDirectory(file)) {
                        size += calculateDirectorySize(file);
                    }
                } catch (IOException e) {
//                    System.out.println("Ошибка при доступе к файлу/папке: " + file + " - " + e.getMessage());
                }
            }
        }
        return size;
    }

    public static String getFormattedDate(Path file) throws IOException {
        FileTime getLastModified = Files.getLastModifiedTime(file);
        // Преобразование FileTime в миллисекунды
        long millis = getLastModified.toMillis();
        // Задаём шаблон отображения
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        // Возвращаем сформированную дату
        return dateFormat.format(new Date(millis));
    }
}
