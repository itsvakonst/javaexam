// Напишите программу, которая по дате определяет день недели, на который эта дата приходится.

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату в формате ДД.ММ.ГГГГ (например, 01.01.2025):");

        try {
            // Чтение даты из ввода пользователя
            String inputDate = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            // Преобразование строки в объект LocalDate
            LocalDate date = LocalDate.parse(inputDate, formatter);

            // Определение дня недели
            String dayOfWeek = date.getDayOfWeek().toString();
            System.out.println("День недели: " + translateDayOfWeek(dayOfWeek));
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Попробуйте снова.");
        }
    }

    // Метод для перевода дня недели на русский язык
    private static String translateDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek) {
            case "MONDAY":
                return "Понедельник";
            case "TUESDAY":
                return "Вторник";
            case "WEDNESDAY":
                return "Среда";
            case "THURSDAY":
                return "Четверг";
            case "FRIDAY":
                return "Пятница";
            case "SATURDAY":
                return "Суббота";
            case "SUNDAY":
                return "Воскресенье";
            default:
                return "Неизвестный день";
        }
    }
}
