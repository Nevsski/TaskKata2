package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test2 {

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("^\"([^\"]*)\"\\s*([+\\-*/])\\s*(?:\"([^\"]*)\"|(\\d+))$");

    public static String processExpression(String expression) throws IllegalArgumentException {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String str1 = matcher.group(1);
        String operation = matcher.group(2);
        String str2 = matcher.group(3);
        String num2Str = matcher.group(4);

        if (str2 != null) {
            // str2 is used
            if (str2.length() > 10) {
                throw new IllegalArgumentException("Строка слишком длинная");
            }
        } else {
            // num2 is used
            int num2 = Integer.parseInt(num2Str);
            if (num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10");
            }

            switch (operation) {
                case "*":
                    return truncateString(str1.repeat(num2));
                case "/":
                    if (num2 > str1.length()) {
                        return "";
                    }
                    return truncateString(str1.substring(0, str1.length() / num2));
                default:
                    throw new IllegalArgumentException("Операция не поддерживается");
            }
        }

        switch (operation) {
            case "+":
                return truncateString(str1 + str2);
            case "-":
                return truncateString(str1.replace(str2, ""));
            default:
                throw new IllegalArgumentException("Операция не поддерживается");
        }
    }



    private static String truncateString(String result) {
        if (result.length() > 40) {
            return result.substring(0, 40) + "...";
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String expression = scanner.nextLine().trim();
            String result = processExpression(expression);
            System.out.println("\"" + result + "\"");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
