// Реализовать класс MyMath для работы с числами. 
// Реализовать статический метод класса MyMath.round(), который округляет дробь до целого числа. 
// Также статический метод abs(), который находит модуль числа. 
// Статический метод MyMath.pow() для нахождения степени числа. Библиотеку Math не использовать.

public class MyMath {

    // округл дробного числа
    public static int round(double value) {
        int integerPart = (int) value; // Целая часть числа
        double fractionalPart = value - integerPart; // Дробная часть числа

        // если дробная часть >= 0.5, прибавляем 1, иначе просто отбрасываем дробь
        if (fractionalPart >= 0.5) {
            return integerPart + 1;
        } else if (fractionalPart <= -0.5) { // Для отрицательных чисел
            return integerPart - 1;
        } else {
            return integerPart;
        }
    }

    // нахождение модуля числа
    public static double abs(double value) {
        return value < 0 ? -value : value;
    }
    // перегрузка abs() для целых чисел (чтобы и дробные, и целые числа можно было)
    public static int abs(int value) {
        return value < 0 ? -value : value;
    }

    // возведение в степень
    public static double pow(double base, int power) {
        if (power == 0) {
            return 1;
        }

        double result = 1;
        boolean isNegative = power < 0; // флаг что степень отрицательная
        int positivePower = abs(power); // берем модуль степени

        // умножаем число само на себя столько раз какая степень
        for (int i = 0; i < positivePower; i++) {
            result *= base;
        }

        // если степень была отрицательной, возвращаем 1/результат
        return isNegative ? 1 / result : result;
    }


    // использование
    public static void main(String[] args) {
        System.out.println("Округление 3.6: " + MyMath.round(3.6)); //  4
        System.out.println("Округление -2.4: " + MyMath.round(-2.4)); //  -2
        System.out.println("Модуль -5: " + MyMath.abs(-5)); //  5
        System.out.println("Модуль 4.2: " + MyMath.abs(4.2)); //  4.2
        System.out.println("2 в степени 3: " + MyMath.pow(2, 3)); //  8
        System.out.println("2 в степени -3: " + MyMath.pow(2, -3)); //  0.125
    }
}
