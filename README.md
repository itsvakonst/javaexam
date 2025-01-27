
1.. Условие: «Реализовать программу для выполнения следующих математических операций с целочисленным, байтовым и вещественным типами данных: сложение, вычитание, умножение, деление, деление по модулю (остаток), модуль числа, возведение в степень. Все данные вводятся с клавиатуры (класс Scanner, System.in, nextint).» По данному условию необходимо реализовать программу с интерактивным консольным меню, (т.е. вывод списка действий по цифрам. При этом при нажатии на цифру у нас должно выполняться определенное действие). При этом в программе данные пункты должны называться следующим образом:

- Вывести все таблицы из MySQL.
- Создать таблицу в MySQL.
- Сложение чисел, результат сохранить в MySQL с последующим выводом в консоль.
- Вычитание чисел, результат сохранить в MySQL с последующим выводом в консоль.
- Умножение чисел, результат сохранить в MySQL с последующим выводом в консоль.
- Деление чисел, результат сохранить в MySQL с последующим выводом в консоль.
- Деление чисел по модулю (остаток), результат сохранить в MySQL с последующим выводом в консоль.
- Возведение числа в модуль, результат сохранить в MySQL с последующим выводом в консоль.
- Возведение числа в степень, результат сохранить в MySQL с последующим выводом в консоль.
- Сохранить все данные (вышеполученные результаты) из MySQL в Excel и вывести на экран.
[Решение 1 ] (https://github.com/AliceAnd0/java_exam/tree/main/NumbersInDB)
[Решение 2 ] (https://github.com/Katerina0088/tiska/blob/master/1/MathOperations.java)
``` 
package com.example.demo;

import java.sql.*;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class MathOperations {
	private static Connection connect() {
		String url = "jdbc:h2:~/testdb"; // Путь к базе данных H2
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "sa", "");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private static void createTable(Connection conn) {
		String sql = "CREATE TABLE IF NOT EXISTS Results (id INT AUTO_INCREMENT PRIMARY KEY, operation VARCHAR(255), result DOUBLE)";
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void saveResult(Connection conn, String operation, double result) {
		String sql = "INSERT INTO Results(operation, result) VALUES(?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, operation);
			pstmt.setDouble(2, result);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void displayResults(Connection conn) {
		String sql = "SELECT * FROM Results";
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id") + ", Operation: " + rs.getString("operation") + ", Result: " + rs.getDouble("result"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void exportToExcel(Connection conn) {
		String sql = "SELECT * FROM Results";
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql);
			 Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("Results");
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Operation");
			headerRow.createCell(2).setCellValue("Result");

			int rowNum = 1;
			while (rs.next()) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(rs.getInt("id"));
				row.createCell(1).setCellValue(rs.getString("operation"));
				row.createCell(2).setCellValue(rs.getDouble("result"));
			}

			try (FileOutputStream fileOut = new FileOutputStream("Results.xlsx")) {
				workbook.write(fileOut);
			}
			System.out.println("Данные успешно экспортированы в Results.xlsx");
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Connection conn = connect();
		createTable(conn);

		while (true) {
			System.out.println("Выберите действие:");
			System.out.println("1. Вывести все таблицы из H2");
			System.out.println("2. Создать таблицу в H2");
			System.out.println("3. Сложение чисел");
			System.out.println("4. Вычитание чисел");
			System.out.println("5. Умножение чисел");
			System.out.println("6. Деление чисел");
			System.out.println("7. Деление по модулю");
			System.out.println("8. Возведение в степень по модулю");
			System.out.println("9. Возведение в степень");
			System.out.println("10. Экспортировать данные в Excel");
			System.out.println("11. Выход");

			int choice = scanner.nextInt();
			if (choice == 11) break;

			double num1, num2, result = 0;
			String operation = "";

			switch (choice) {
				case 1:
					displayResults(conn);
					break;
				case 2:
					createTable(conn);
					System.out.println("Таблица создана.");
					break;
				case 3:
					System.out.print("Введите первое число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите второе число: ");
					num2 = scanner.nextDouble();
					result = num1 + num2;
					operation = "Сложение";
					break;
				case 4:
					System.out.print("Введите первое число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите второе число: ");
					num2 = scanner.nextDouble();
					result = num1 - num2;
					operation = "Вычитание";
					break;
				case 5:
					System.out.print("Введите первое число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите второе число: ");
					num2 = scanner.nextDouble();
					result = num1 * num2;
					operation = "Умножение";
					break;
				case 6:
					System.out.print("Введите первое число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите второе число: ");
					num2 = scanner.nextDouble();
					result = num1 / num2;
					operation = "Деление";
					break;
				case 7:
					System.out.print("Введите первое число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите второе число: ");
					num2 = scanner.nextDouble();
					result = num1 % num2;
					operation = "Деление по модулю";
					break;
				case 8:
					System.out.print("Введите число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите степень: ");
					num2 = scanner.nextDouble();
					result = Math.pow(num1, num2) % num2;
					operation = "Возведение в степень по модулю";
					break;
				case 9:
					System.out.print("Введите число: ");
					num1 = scanner.nextDouble();
					System.out.print("Введите степень: ");
					num2 = scanner.nextDouble();
					result = Math.pow(num1, num2);
					operation = "Возведение в степень";
					break;
				case 10:
					exportToExcel(conn);
					displayResults(conn);

					break;
				default:
					System.out.println("Неверный выбор. Попробуйте снова.");
					continue;
			}


			saveResult(conn, operation, result);
			System.out.println("Результат: " + result);
		}

		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		scanner.close();
	}
}
```
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>org.example</groupId>
	<artifactId>task_23</artifactId>
	<version>1.0-SNAPSHOT</version>

	<properties>
		<maven.compiler.source>22</maven.compiler.source>
		<maven.compiler.target>22</maven.compiler.target>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
	        <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->

	        <dependency>
	            <groupId>com.h2database</groupId>
	            <artifactId>h2</artifactId>
	            <version>2.3.232</version>
	        </dependency>

	        <dependency>
	            <groupId>javax.persistence</groupId>
	            <artifactId>javax.persistence-api</artifactId>
	            <version>2.2</version>
	        </dependency>

	        <dependency>
	            <groupId>org.springframework.boot</groupId>
	            <artifactId>spring-boot-starter-data-jpa</artifactId>
	            <version>2.7.12</version>
	        </dependency>
	        <dependency>
	            <groupId>org.apache.poi</groupId>
	            <artifactId>poi</artifactId>
	            <version>5.2.3</version>
	        </dependency>
	        <dependency>
	            <groupId>org.apache.poi</groupId>
	            <artifactId>poi-ooxml</artifactId>
	            <version>5.2.3</version>
	        </dependency>

	    </dependencies>
</project>
```


2.. Напишите программу, в которой из строки "I have 3 cats, 4 dogs, and 1 turtle" отбираются цифры. Из этих цифр формируется массив.
[Решение](https://github.com/polinatut/exem_java/blob/main/ex_2/ExtractNumbers.java)

```
package com.example.demo;

import java.util.ArrayList;

public class ExtractNumbers {
	public static void main(String[] args) {
		String input = "I have 3 cats, 4 dogs, and 1 turtle";

		ArrayList<Integer> numbers = new ArrayList<>();

		for (char ch : input.toCharArray()) {
			if (Character.isDigit(ch)) {
				numbers.add(Character.getNumericValue(ch));
			}
		}

		int[] resultArray = numbers.stream().mapToInt(Integer::intValue).toArray();

		System.out.print("Извлеченные числа: ");
		for (int num : resultArray) {
			System.out.print(num + " ");
		}
	}
}


```


3.. Разработайте программу, которая выводит в консоль все цифры, входящие в натуральное число n. К примеру, если дано число 2359, то в консоль выводятся отдельно числа 2, 3, 5, 9.
[Решение](https://github.com/mirrrler/task3)

```
package com.example.demo;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input;

		while (true) {
			System.out.print("Введите натуральное число: ");
			input = scanner.nextLine(); // Считываем ввод как строку

			// Проверяем, что строка состоит только из цифр
			if (input.matches("\\d+")) {
				break; // Если строка валидна, выходим из цикла
			} else {
				System.out.println("Ошибка ввода. Пожалуйста, введите натуральное число.");
			}
		}

		// Вывод цифр числа
		System.out.println("Цифры в числе " + input + ":");
		for (char digit : input.toCharArray()) {
			System.out.println(digit);
		}

		scanner.close();
	}
}

```

4.. Написать калькулятор для строковых выражений вида "<число> <операция> <число>", где <число> - положительное целое число меньшее 10, записанное словами, например, "четыре", <арифметическая операция> - одна из операций "плюс", "минус", "умножить". Результат выполнения операции вернуть в виде текстового представления числа. Пример: "пять плюс четыре" --> "девять".
[Решение](https://github.com/dariadegt/java_exam/blob/main/Task_4.java)
```
import java.util.HashMap;
import java.util.Map;

public class Task_4 {
    private static final Map<String, Integer> numberMap = new HashMap<>();
    private static final Map<Integer, String> reverseNumberMap = new HashMap<>();

    static {
        String[] numbers = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        for (int i = 0; i < numbers.length; i++) {
            numberMap.put(numbers[i], i);
            reverseNumberMap.put(i, numbers[i]);
        }
    }

    public static String calculate(String expression) {
        String[] parts = expression.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }

        Integer num1 = numberMap.get(parts[0]);
        String operation = parts[1];
        Integer num2 = numberMap.get(parts[2]);

        if (num1 == null || num2 == null) {
            throw new IllegalArgumentException("Неверное число в выражении");
        }

        int result;
        switch (operation) {
            case "плюс":
                result = num1 + num2;
                break;
            case "минус":
                result = num1 - num2;
                break;
            case "умножить":
                result = num1 * num2;
                break;
            default:
                throw new IllegalArgumentException("Неверная операция");
        }

        return reverseNumberMap.getOrDefault(result, "неизвестно");
    }

    public static void main(String[] args) {
        System.out.println(calculate("пять плюс четыре"));  // девять
        System.out.println(calculate("два умножить три"));  // шесть
        System.out.println(calculate("семь минус один"));   // шесть
    }
}
```

5.. Напишите программную реализацию бинарного дерева поиска.
[Решение](https://github.com/gavalone/for_work/blob/main/src/Task_5.java)

```
package com.example.demo;

class Tree {
    int value;
    Tree left;
    Tree right;

    public Tree(int value) {
        this.value = value;
        left = null;
        right = null;
    }
}
```
```
package com.example.demo;

class OperationTree {
    Tree root;

    public OperationTree() {
        this.root = null;
    }

    void insert(int value) {
        root = insertRec(root, value);
    }

    Tree insertRec(Tree root, int value) {
        if (root == null) {
            root = new Tree(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        }
        else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    void delete(int value) {
        root = deleteRec(root, value);
    }
    Tree deleteRec(Tree root, int value) {
        if (root == null) {
            return null;
        }
        if (value < root.value)
            root.left = deleteRec(root.left, value);
        else if (value > root.value)
            root.right = deleteRec(root.right, value);
        else{
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        return root;
    }

    int minValue(Tree root) {
        int minValue = root.value;
        while (root.left != null) {
            minValue = root.left.value;
            root = root.left;
        }
        return minValue;
    }

    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(Tree root, int value) {
        if (root == null)
            return false;
        if (root.value == value)
            return true;
        if (root.value < value)
            return searchRec(root.right, value);
        return searchRec(root.left, value);
    }

    void inOrder() {
        inOrderRec(root);
    }

    void inOrderRec(Tree root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.value + " ");
            inOrderRec(root.right);
        }
    }
}
```
```
package com.example.demo;

public class Task_5 {
    public static void main(String[] args) {
        OperationTree tree = new OperationTree();

        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);

        System.out.println("Обход дерева: ");
        tree.inOrder();

        System.out.println("\nПоиск 5: " + tree.search(5));

        System.out.println("Удаление 4: ");
        tree.delete(4);
        tree.inOrder();
    }
}
```

6.. Разработайте программу, которая выводит буквы английского алфавита, используя цикл while в MySQL/PostgreSQL.
[Решение](https://github.com/bshkrrr/java_exam/blob/main/src/main/java/com/example/demo/Alph6.java)

```
package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@SpringBootApplication
public class Alph6 {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:h2:mem:testdb";
        String username = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String createTableSQL = "CREATE TABLE Alphabet (id INT AUTO_INCREMENT PRIMARY KEY, letter CHAR(1))";
            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableSQL);
            }

            String insertSQL = "INSERT INTO Alphabet (letter) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                char letter = 'A';
                while (letter <= 'Z') {
                    preparedStatement.setString(1, String.valueOf(letter));
                    preparedStatement.executeUpdate();
                    letter++;
                }
            }

            String selectSQL = "SELECT * FROM Alphabet";
            try (Statement statement = connection.createStatement();
                 var resultSet = statement.executeQuery(selectSQL)) {
                while (resultSet.next()) {
                    String letter = resultSet.getString("letter");
                    System.out.println(letter);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

7.. Напишите программу, которая будет выводить в консоль введенное слово 6 раз и сохранять в MySQL/PostgreSQL.
[Решение](https://github.com/flamingo99900/JavaEkz/tree/task_7) 
```
package com.example.demo;

import java.sql.*;
import java.util.Scanner;

public class WordSaver {

    private static Connection connect() {
        String url = "jdbc:h2:mem:testdb";
        String user = "sa";
        String password = "";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }

        return conn;
    }

   
    private static void createTable(Connection conn) {
        String sql = """
            CREATE TABLE IF NOT EXISTS Words (
                id INT AUTO_INCREMENT PRIMARY KEY,
                word VARCHAR(255) NOT NULL
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }
    
    private static void saveWord(Connection conn, String word) {
        String sql = "INSERT INTO Words (word) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения слова: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        
        Connection conn = connect();

        if (conn == null) {
            System.out.println("Не удалось подключиться к базе данных. Программа завершена.");
            return;
        }
        
        createTable(conn);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите слово: ");
        String word = scanner.nextLine();

        
        System.out.println("Ваше слово:");
        for (int i = 0; i < 6; i++) {
            System.out.println(word);
        }

        
        saveWord(conn, word);
        System.out.println("Слово успешно сохранено в базу данных.");

        
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия подключения: " + e.getMessage());
        }

        scanner.close();
    }
}

```

8..Разработать программу для вывода на экран кубов первых десяти положительных чисел.
[Решение](https://github.com/gavalone/for_work/blob/main/src/Task_8.java)
```
package com.example.demo;

import javax.swing.*;

public class Task_8 {
    public static void main(String[] args){
        JFrame frame = new JFrame("Задание 8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // только для чтения

        StringBuilder taskText = new StringBuilder(" Кубы первых десяти положительных чисел: \n");

        for (int i = 1; i < 11; i++){
            int result = i * i * i;
            taskText.append(i).append("^3 = ").append(result).append("\n");
        }

        textArea.setText(taskText.toString());
        frame.add(textArea);
        frame.setVisible(true);
    }

}
```


9.. Напишите программу, которая по дате определяет день недели, на который эта дата приходится.
[Решение](https://github.com/itsvakonst/javaexam/blob/main/Task9.java)
```
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
```

10.. Написать класс, который при введении даты в формате ДД.ММ.ГГ (к примеру, 22.10.20) выводит номер недели. Даты начиная с 2020 по 2022 годы. К примеру, первая неделя в 2020 году: 1-5 января, вторая неделя – 6-12 января. Значит при вводе 08.01.20 вывод должен быть: Неделя 2.
[Решение](https://github.com/Delia0001/java_exam/blob/main/Task_10.java)
```
//Написать класс, который при введении даты в формате ДД.ММ.ГГ (к примеру, 22.10.20) выводит номер недели. 
//Даты начиная с 2020 по 2022 годы. К примеру, первая неделя в 2020 году: 1-5 января, вторая неделя – 6-12 января. 
//Значит при вводе 08.01.20 вывод должен быть: Неделя 2.

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        System.out.println("Введите дату в формате ДД.ММ.ГГ (например, 08.01.20).");
        System.out.print("Введите дату: ");
        
        String input = scanner.nextLine();
        
        try {
            // Преобразуем строку в объект LocalDate
            LocalDate date = LocalDate.parse(input, formatter);

            // Проверяем диапазон годов
            if (date.getYear() < 2020 || date.getYear() > 2022) {
                System.out.println("Ошибка: введите дату в диапазоне с 2020 по 2022 год.");
            }

            // Получаем номер недели
            int weekNumber = getWeekNumber(date);

            // Выводим результат
            System.out.println("Неделя " + weekNumber);
        } 
        catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты. Убедитесь, что используете ДД.ММ.ГГ.");
        }
    }

    private static int getWeekNumber(LocalDate date) {
        // Определяем, что неделя начинается с понедельника
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Вычисляем номер недели
        return date.get(weekFields.weekOfWeekBasedYear());
    }
}

```

11.. Разработайте программу, реализующую рекурсивное вычисление факториала. 
[Решение](https://github.com/NafaNaniz/exam/blob/main/RecursiveFactorial.java)
```
public class RecursiveFactorial {
    static int factorial (int n){
        if (n == 0 || n == 1){
            return 1;
        }
        return n * factorial(n - 1);
    }
    public static void main(String[] args) {
        System.out.println(factorial(3));
    }
}
```


12.. Разработать класс-оболочку для числового типа double. Реализовать статические методы сложения, деления, возведения в степень.
[Решение](https://github.com/AliceAnd0/java_exam/blob/main/DoubleWrapper.java)
```
public class DoubleWrapper {
    private double value;

    public DoubleWrapper(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    @Override
    public String toString() {
        return "DoubleWrapper{value=" + value + "}";
    }

    public static void main(String[] args) {
        DoubleWrapper num1 = new DoubleWrapper(5.0);
        DoubleWrapper num2 = new DoubleWrapper(2.0);

        // Использование статических методов
        double sum = DoubleWrapper.add(num1.getValue(), num2.getValue());
        double division = DoubleWrapper.divide(num1.getValue(), num2.getValue());
        double exponentiation = DoubleWrapper.power(num1.getValue(), num2.getValue());

        // Вывод результатов
        System.out.println("Сложение: " + sum);
        System.out.println("Деление: " + division);
        System.out.println("Возведение в степень: " + exponentiation);
    }
}
```

13.. Разработать программу, которая заполняет двумерный массив случайными положительными числами в диапазоне от 1 до 100 до тех пор, пока сумма граничных элементов не станет равной 666. Пользователь вначале вводит размер матрицы.
[Решение](https://github.com/polinatut/exem_java/blob/main/ex_13/MatrixSum.java)
```

import java.util.Random;
import java.util.Scanner;

public class MatrixSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введите количество строк матрицы: ");
        int rows = scanner.nextInt();

        System.out.print("Введите количество столбцов матрицы: ");
        int cols = scanner.nextInt();

        if (rows < 2 || cols < 2) {
            System.out.println("Размер матрицы должен быть не менее 2x2.");
            return;
        }

        int[][] matrix = new int[rows][cols];
        int borderSum = 0;
        int attempts = 0; 
        final int MAX_ATTEMPTS = 10000000; 

        while (borderSum != 666 && attempts < MAX_ATTEMPTS) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = random.nextInt(100) + 1;
                }
            }

            borderSum = calculateBorderSum(matrix, rows, cols);
            attempts++;
        }

        if (borderSum == 666) {
            System.out.println("Матрица, где сумма граничных элементов равна 666:");
            printMatrix(matrix);
        } else {
            System.out.println("Не удалось найти подходящую матрицу за " + MAX_ATTEMPTS + " попыток.");
        }
    }

    private static int calculateBorderSum(int[][] matrix, int rows, int cols) {
        int sum = 0;

        for (int j = 0; j < cols; j++) {
            sum += matrix[0][j]; 
            sum += matrix[rows - 1][j]; 
        }

        for (int i = 1; i < rows - 1; i++) {
            sum += matrix[i][0]; 
            sum += matrix[i][cols - 1]; 
        }

        return sum;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
```


14.. Разработать программу, в которой требуется создать класс, описывающий геометрическую фигуру – треугольник. Методами класса должны быть – вычисление площади, периметра. Создать класс-наследник, определяющий прямоугольный треугольник.
[Решение](https://github.com/mirrrler/task14)
```
public class Main {
    public static void main(String[] args) {
        // Создаем треугольник
        Triangle triangle = new Triangle(3, 4, 5);
        System.out.println("Треугольник:");
        System.out.println("Периметр: " + triangle.getPerimeter());
        System.out.println("Площадь: " + triangle.getArea());

        // Создаем прямоугольный треугольник
        RightTriangle rightTriangle = new RightTriangle(3, 4);
        System.out.println("\nПрямоугольный треугольник:");
        System.out.println("Периметр: " + rightTriangle.getPerimeter());
        System.out.println("Площадь: " + rightTriangle.getArea());
    }
}
```

```
class RightTriangle extends Triangle {
    // Конструктор
    public RightTriangle(double base, double height) {
        super(base, height, Math.sqrt(base * base + height * height)); // c = √(a² + b²)
    }

    // Переопределение метода для вычисления площади
    @Override
    public double getArea() {
        return (a * b) / 2; // Площадь прямоугольного треугольника
    }
}
```
```
class Triangle {
    protected double a; // Сторона a
    protected double b; // Сторона b
    protected double c; // Сторона c

    // Конструктор
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Метод для вычисления периметра
    public double getPerimeter() {
        return a + b + c;
    }

    // Метод для вычисления площади по формуле Герона
    public double getArea() {
        double s = getPerimeter() / 2; // Полупериметр
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

```

15.. Разработать программу, в которой требуется создать абстрактный класс. В этом абстрактном классе определить абстрактные методы вычисления функции в определенной точке. Создать классы-наследники абстрактного класса, описывающими уравнения прямой и параболы. Программа должна выводить в консоль значение функции при вводе определенного значения.
[Решение](https://github.com/dariadegt/java_exam/blob/main/Task_15.java)
```
import java.util.Scanner;

abstract class Function {
    public abstract double calculate(double x);
}

class LinearFunction extends Function {
    private double a, b;

    public LinearFunction(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double calculate(double x) {
        return a * x + b;
    }
}

class QuadraticFunction extends Function {
    private double a, b, c;

    public QuadraticFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculate(double x) {
        return a * x * x + b * x + c;
    }
}

public class Task_15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите значение x: ");
        double x = scanner.nextDouble();

        Function linear = new LinearFunction(2, 3);
        Function quadratic = new QuadraticFunction(1, -2, 1);

        System.out.println("Значение линейной функции: " + linear.calculate(x));
        System.out.println("Значение квадратичной функции: " + quadratic.calculate(x));
    }
}
```
16.. Создать интерфейс Progress c методами вычисления любого элемента прогрессии и суммы прогрессии. Разработать классы арифметической и геометрической прогрессии, которые имплементируют интерфейс Progress.
https://github.com/max33128/tasks

17..Разработать интерфейс InArray, в котором предусмотреть метод сложения двух массивов. Создать класс ArraySum, в котором имплементируется метод сложения массивов. Создать класс OrArray, в котором метод сложения массивов имплементируется как логическая операция ИЛИ между элементами массива.
https://github.com/bshkrrr/java_exam/tree/main/src/main/java/com/example/demo

18.. Создать класс Binary для работы с двоичными числами фиксированной длины. Число должно быть массивом тип char, каждый элемент которого принимает значение 0 или 1. Младший бит имеет младший индекс. Отрицательные числа представляются в дополнительном коде. Дополнительный код получается инверсией всех битов с прибавлением 1 к младшему биту. Например, +1 – это в двоичном коде будет выглядеть, как 0000 0001. А -1 в двоичном коде будет выглядеть, как 1111 1110 + 0000 0001 = 1111 1111. Создать методы конвертации десятичного числа в массив и обратно.
https://github.com/flamingo99900/JavaEkz/tree/task_18 

19.. Создать класс Matrix для работы с двумерными матрицами. Создать методы для генерации нулевой матрицы, а также для генерации матрицы со случайными величинами – применить Math.random(). Реализовать метод сложения матриц.
https://github.com/gavalone/for_work/blob/main/src/Matrix.java 


20.. Реализовать класс MyMath для работы с числами. Реализовать статический метод класса MyMath.round(), который округляет дробь до целого числа. Также статический метод abs(), который находит модуль числа. Статический метод MyMath.pow() для нахождения степени числа. Библиотеку Math не использовать.
https://github.com/itsvakonst/javaexam/blob/main/Task20.java

21.. Разработать программу для игры «Угадайка». Программа загадывает случайное число от 1 до 10, требуется его отгадать с трех попыток. После каждой попытки, если результат неверен, игроку выводится сообщение, меньше или больше названное игроком число, чем загаданное. Сет заканчивается или если игрок угадывает число, или если исчерпывает три попытки, не угадав. Игра должна быть выполнена в бесконечном цикле, и продолжается до тех пор, пока на предложение «Сыграем еще раз?» игрок не напишет «Нет».
https://github.com/Delia0001/java_exam/blob/main/Task_21.java 


22.. Разработайте программу-генератор рабочего календаря. Слесарь механосборочного цеха работает сутки через трое. Если смена попадает на воскресенье, то переносится на понедельник. По введенной дате программа должна генерировать расписание из дат на текущий месяц на 2022 год.
https://github.com/NafaNaniz/exam/blob/main/WorkScheduleGenerator.java

23.. Разработать класс для представления комплексных чисел с возможностью задания вещественной и мнимой частей в виде массива из двух чисел типа int. Определить методы для выполнения операций сложения, вычитания и умножения комплексных чисел.
https://github.com/AliceAnd0/java_exam/blob/main/ComplexNumber.java


24.. Создайте класс Form - оболочку для создания и ввода пароля. Он должен иметь методы input, submit, password. Создайте класс SmartForm, который будет наследовать от Form и сохранять значения password.
https://github.com/polinatut/exem_java/tree/main/ex_24 


25.. Сделайте класс User, в котором будут следующие protected поля - name (имя), age (возраст), public методы setName, getName, setAge, getAge. Сделайте класс Worker, который наследует от класса User и вносит дополнительное private поле salary (зарплата), а также методы public getSalary и setSalary. Создайте объект этого класса 'Иван', возраст 25, зарплата 1000. Создайте второй объект этого класса 'Вася', возраст 26, зарплата 2000. Найдите сумму зарплата Ивана и Васи. Сделайте класс Student, который наследует от класса User и вносит дополнительные private поля стипендия, курс, а также геттеры и сеттеры для них.
https://github.com/mirrrler/task25


26..Создайте класс ColorModel для определения цветовой модели. Разработайте подклассы RGBconverter и CMYKconverter для конвертации цвета из одной модели в другую. Конвертация CMYK в RGB производится по следующим формулам: R = 255 × (1-C) × (1-K), G = 255 × (1-M) × (1-K), B = 255 × (1-Y) × (1-K) (где R – red, G – green, B – black, C – Cyan, M - Magenta, Y - Yellow, K- Black))
https://github.com/dariadegt/java_exam/blob/main/Task_26.java

27.. Создайте класс Number для конвертации десятичного числа в бинарный, восьмеричный, шестнадцатеричный вид. Реализовать в виде статических методов класса. Числа вводятся с клавиатуры с запросом в какой численный вид конвертировать.
https://github.com/gavalone/for_work/blob/main/src/Number.java 


28.. Разработать класс Neuron для реализации нейронной сети из двух нейронов и одного выхода. Сделать функцию прямого распространения с функцией активации в виде сигмоиды.
не стала даже комменты убирать чтобы хоть чето было понятно
https://github.com/bshkrrr/java_exam/tree/main/src/main/java/com/example/demo


29.. Напишите программу, которая заполняет списочный массив случайными числами типа Integer (значения этих чисел были от 1 до 100). Список должен содержать 100 элементов. Затем отсортируйте по убыванию список и выведите первые 10 значений в консоль. Результаты сохраните в MySQL/PostgreSQL.
https://github.com/flamingo99900/JavaEkz/tree/task_29 


30.. Разработайте программу, которая заполняет список случайными числами. Количество элементов и числовой диапазон вводятся пользователем. Программа должна проверять, входит ли число (также вводится пользователем) в данный список. Должен быть реализован бинарный поиск. Результаты должны сохраняться в MySQL/PostgreSQL и выводиться оттуда же.
https://github.com/max33128/tasks

31.. На основе класса BitSet разработайте программу для реализации битовых операций AND, OR, XOR, а также маскирования.
https://github.com/itsvakonst/javaexam/blob/main/Task31.java

32.. Напишите программу, которая получает в качестве входных данных два числа. Эти числа являются количество строк и столбцов двумерной коллекции целых чисел. Далее элементы заполняются случайными числами и выводятся в консоль в виде таблицы.
https://github.com/Delia0001/java_exam/blob/main/Task_32.java 


33.. Разработайте программу, которая получает в качестве параметра два числа –количество строк и столбцов двумерной коллекции целых чисел. Коллекция заполняется случайными числами, после чего на экран выводятся максимальное и минимальное значения с индексами ячеек.
https://github.com/NafaNaniz/exam/blob/main/Array2D.java


34.. Разработайте программу, в которой создайте две коллекции с именами людей (строковые переменные). Результат сохранить в MySQL/PostgreSQL. Затем последовательно выводите в консоль имена.
https://github.com/AliceAnd0/java_exam/tree/main/NamesToDB

35.. Напишите программу, которая реализует класс Matrix и следующие методы:
- Сложение и вычитание матриц.
- Умножение матрицы на число.
- Произведение двух матриц.
- Транспонированная матрица.
- Возведение матрицы в степень.
Если метод, возвращает матрицу, то он должен возвращать новый объект, а не менять базовый.
	https://github.com/polinatut/exem_java/tree/main/ex_35 
  
  
36.. Разработать программу для поочередной обработки текстовых файлов. Файлы созданы со следующими именами: n.txt, где n – натуральное число. В файлах записаны: в первой строке одно число с плавающей запятой, во второй строке – второе число. Пользователь вводит название файла и требуемую операцию над числами (сложение, умножение, разность). Результат выводится на экран и файл n_out.txt.
https://github.com/mirrrler/task36


37.. Написать приложение для сложения, вычитания, умножения, деления, возведения в степень логарифмов. Программа должна выполнять ввод данных, проверку правильности введенных данных, выдачу сообщений в случае ошибок. Результат выводится на экран и записывается в файл.
https://github.com/dariadegt/java_exam/blob/main/Task_37.java

38.. Разработать программу шифровки-дешифровки по алгоритму AES-128. Данные берутся из файла, зашифрованные данные сохраняются в указанный файл.
https://github.com/gavalone/for_work/blob/main/src/Task_38.java 


39.. Разработать программу нахождения наибольшего общего делителя двух натуральных чисел. Требуется реализовать рекурсивный и без рекурсии варианты. Результат сохранить в MySQL/PostgreSQL.
https://github.com/bshkrrr/java_exam/tree/main/src/main/java/com/example/demo


40.. Напишите программу, которая каждые 5 секунд отображает на экране данные о времени, прошедшем от начала запуска программы, а другой её поток выводит сообщение каждые 7 секунд. Третий поток выводит на экран сообщение каждые 10 секунд. Программа работает одну минуту, затем останавливается. Все результаты после вывода необходимо сохранить в MySQL/PostgreSQL.
https://github.com/flamingo99900/JavaEkz/tree/task_40 


41.. Условие задачи: «Ввести две строки (не менее 50 символов каждая) с клавиатуры. Необходимо вывести на экран две введенных ранее строки, подсчитать и вывести размер длины каждой строки, объединить данные строки в одну, сравнить данные строки и результат сравнения вывести на экран». По данному условию необходимо реализовать программу с интерактивным консольным меню, (т.е. вывод списка действий по цифрам. При этом при нажатии на цифру у нас должно выполняться определенное действие). При этом в программе данные пункты должны называться следующим образом:
- Вывести все таблицы из MySQL.
- Создать таблицу в MySQL.
- Ввести две строки с клавиатуры, результат сохранить в MySQL с последующим выводом в консоль.
- Подсчитать размер ранее введенных строк, результат сохранить в MySQL с последующим выводом в консоль.
- Объединить две строки в единое целое, результат сохранить в MySQL с последующим выводом в консоль.
- Сравнить две ранее введенные строки, результат сохранить в MySQL с последующим выводом в консоль.
- Сохранить все данные (вышеполученные результаты) из MySQL в Excel и вывести на экран.
https://github.com/max33128/tasks

42.. Написать на основе Spring Boot клиент-серверное приложение MyUser, в котором можно управлять данными пользователей из базы данных через веб-интерфейс: имя, фамилия, возраст, номер группы. База данных может быть любой – MySQL, PostgreSQL и т.д. При этом должна быть доступна возможность добавления/удаления/редактирования пользователей.
https://github.com/itsvakonst/javaexam/tree/main/Task42/main

43.. Написать на основе Spring Boot Security форму для авторизации и регистрации пользователя. При этом после авторизации пользователя должно быть перенаправление на главную страницу. Главная страница должна содержать запись «Hello World!». При этом до авторизации главная страница не должна быть доступна для пользователя.

Код из main: https://github.com/Delia0001/java_exam/tree/main/Task43/main 

Весь проект в формате zip: https://github.com/Delia0001/java_exam/blob/main/task_43.zip  


44.. Разработать MVC-приложение арифметический калькулятор на основе Spring Boot. Применить шаблонизатор Thymeleaf. Все результаты вычисления должны сохраняться и выводиться из MySQL.
https://github.com/NafaNaniz/calc

45.. Разработка веб-MVC приложения на основе Spring Boot. Приложение должно генерировать последовательность из 1000 случайных чисел в диапазоне, заданном пользователем, и выводит эти числа на экран и вычисляет их среднее арифметическое.
https://github.com/AliceAnd0/java_exam/tree/main/WebMVCRandomNumbers


46.. Разработать приложение для работы с локальной базой данных MySQL. Создайте базу данных мобильных телефонов (не менее 10 позиций), со следующими полями: производитель, модель, год выпуска, диагональ экрана. Напишите методы для выполнения запросов к базе данных. Все данные должны выводиться в консоли на экран.
https://github.com/polinatut/exem_java/tree/main/ex_46 
