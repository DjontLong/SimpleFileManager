<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Manager Console Output</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .console {
            background-color: #000;
            color: #00ff00;
            padding: 10px;
            border-radius: 5px;
            white-space: pre-wrap;
            font-family: 'Courier New', Courier, monospace;
        }
        .menu {
            background-color: #333;
            color: #fff;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .stats {
            background-color: #333;
            color: #fff;
            padding: 10px;
            border-radius: 5px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="menu">
        ┌──────────────────────────────────────────────────────────────────────────────┐<br>
        │        Файловый менеджер                                                     │<br>
        ├──────────────────────────────────────────────────────────────────────────────┤<br>
        │ 1 - Добавить папку    2 - Добавить файл    3 - Удалить    4 - Переименовать  │<br>
        │ 5 - Просмотр          0 - Выйти                                              │<br>
        └──────────────────────────────────────────────────────────────────────────────┘<br>
        Введите команду: 5<br>
        Для просмотра текущей директории нажмите <.>, либо укажите путь: D:/
    </div>
    <div class="console">
        [DIR] [0.0 KB | 23.02.2024 23:21:23] $RECYCLE.BIN<br>
        [FILE] [0.0 KB | 11.12.2023 22:12:02] .GamingRoot<br>
        [FILE] [8.0 KB | 22.01.2023 21:07:44] DumpStack.log.tmp<br>
        [DIR] [1.9938375E7 KB | 23.02.2024 14:11:28] Edu<br>
        [DIR] [4.2487766E7 KB | 22.06.2024 16:13:05] Games<br>
        [DIR] [0.0 KB | 08.06.2023 20:03:07] msdownld.tmp<br>
        [DIR] [227659.0 KB | 09.06.2024 22:42:31] Programms<br>
        [DIR] [0.0 KB | 11.12.2023 22:12:02] XboxGames
    </div>
    <div class="stats">
        ┌──────────────┐<br>
        |   files: 2<br>
        |   folders: 6<br>
        └──────────────┘
    </div>
</body>
</html>