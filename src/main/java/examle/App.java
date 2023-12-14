package examle;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите опцию:");
            System.out.println("1. Получить средний курс валюты");
            System.out.println("2. Получить курс валюты на указанную дату");

            int choice = scanner.nextInt();

            if (choice == 1) {
                MainApp.getAverageCurrency();
            } else if (choice == 2) {
                String date = getDateFromUser();
                double exchangeRate = MainApp2.getExchangeRateByDate(date);
                System.out.printf("Курс валюты на указанную дату: %.2f%n", exchangeRate);
            } else {
                System.out.println("Неверный выбор. Пожалуйста, выберите 1 или 2.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDateFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату в формате yyyyMMdd:");
        return scanner.next();
    }
}
