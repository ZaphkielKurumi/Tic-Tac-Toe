import java.util.Scanner;

/*
    Работу выполнил:
    Студент 2 курса, группы ИСП-3,4-2019 БО
    Старков Иван
*/

public class TicTacToe3 {

    public static final String empty = "   ", cross = " X ", zero = " O ";
    // фиксированные переменные, которые указывают на содержимое ячейки

    public static String activePlayer;

    public static final int rows = 3, columns = 3;
    // задаём количество строк и столбцов

    public static String[][] grid = new String[rows][columns];
    /*
       игровое поле состоит из двумерного массива
       каждая ячейка содержит одно из значений: (empty, cross, zero)
    */

    public static int statusPlayer;
    public static final int status_continues = 0, status_draw = 1, status_victory_X = 3, status_victory_0 = 4;

    public static Scanner in = new Scanner(System.in);
    // ввод игроков

    public static void main(String[] args) {
        // инициализирование игры, рисуется игровое поле
        StartPlayer();
        do {
            PlayerInput();
            AnalyzeGrid();
            OutputGrid();
            // полученный ввод от пользователя записать в массив
            if (statusPlayer == status_victory_X){
                System.out.println("'X' победил! Поздравляем!");
            } else if (statusPlayer == status_victory_0){
                System.out.println("'0' победил! Поздравляем!");
            } else if (statusPlayer == status_draw){
                System.out.println("Ничья!");
            }
            // проверить статус игры, а если игра закончилась - вывести результаты
            activePlayer = (activePlayer == cross?zero:cross);
        }
        while (statusPlayer == status_continues);

    }
       // изменение активности игрока


    public static void StartPlayer() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid[row][column] = empty; // все ячейки пустые
            }
        }
        activePlayer = cross; // крестик начинает игру
        OutputGrid(); // вывести игровое поле
    }


    public static void PlayerInput() {
        // выполенение проверки на корректный ввод
        // нельзя вставать указывать на уже использованную ячейку
        boolean validInput = false;
        do {
            System.out.println("Игрок '" + activePlayer + "', введите строку (1-3) и стобец (1-3) через пробел: ");
              /*
                   Порядковый номер элементов массива начинается с 0, а не 1
                   Просим пользователя ввести числа начиная с 1
                   Необходимо вычесть -1 от ввода
              */
            int row = in.nextInt() - 1;
            int column = in.nextInt() - 1;

            if (row >= 0 && row < rows && column >= 0 && column < columns && grid[row][column] == empty) {
                grid[row][column] = activePlayer;
                validInput = true;
                // записываем ввод пользователя в игровое поле
            } else {
                System.out.println("Выбранное размещение (" + (row + 1) + "," + (column + 1)
                        + ") не может быть использовано. Попробуйте еще раз.");
            }
        } while (!validInput);
    }

    public static void AnalyzeGrid() {

        String winner = FindWinner();
        if (winner.equals(cross)){
            statusPlayer = status_victory_X;
        } else if (winner.equals(zero)){
            statusPlayer = status_victory_0;
        } else if (NoPlace()){
            statusPlayer = status_draw;
        } else {
            statusPlayer = status_continues;
        }
    }

    public static boolean NoPlace() {
        for (int row = 0; row < rows; row++){
            for (int column = 0; column < columns; column++){
                if (grid[row][column] == empty){
                    return false;
                }
            }
        }
        return true;
        // пустых ячеек не найдено
    }

    public static String FindWinner() {

        int similar;

        for (int row = 0; row < rows; row++) {
            similar = 0;
            for (int column = 0; column < columns; column++) {
                if (grid[row][0] != empty && grid[row][0] == grid[row][column]) {
                    similar++;
                }
            }
            if (similar == 3) {
                return grid[row][0];
            }
        }

        for (int column = 0; column < columns; column++) {
            similar = 0;
            for (int row = 0; row < rows; row++) {
                if (grid[0][column] != empty && grid[0][column] == grid[row][column]) {
                    similar++;
                }
            }
            if (similar == 3) {
                return grid[0][column];
            }
        }

        if (grid[0][0] != empty && grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2]) {
            return grid[0][0];
        }

        if (grid[0][2] != empty && grid[1][1] == grid[0][2] && grid[2][0] == grid[0][2]) {
            return grid[0][2];
        }

        return empty;
    }

    /** вивести ігрове поле (змінну sitka) в консоль */
    public static void OutputGrid() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                System.out.print(grid[row][column]);
                if (column != columns - 1) {
                    System.out.print("|");
                    // вывести вертикальный разделитель сетки (игрового поля)
                }
            }
            System.out.println();
            if (row != rows - 1) {
                System.out.println("-----------");
                // вывести горизонтальный разделитель сетки (игрового поля)
            }
        }
        System.out.println();
    }

}