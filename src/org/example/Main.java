package org.example;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Оберіть клас гладіатора:");
        System.out.println("1. Сігітаріус (Лучник) - більше спритності.");
        System.out.println("2. Галус (Танк) - більше здоров'я.");
        System.out.println("3. Спартак (Воїн) - більше сили.");
        int choice = scanner.nextInt();

        Gladiator gladiator = null;
        switch (choice) {
            case 1:
                gladiator = new Gallus(5, 2, 0, 1, 0, 3, 10, 30, true);
                System.out.println("Вітаю, ти вибрав Галуса (Танка)");
                break;
            case 2:
                gladiator = new Sagittarius(2, 2, 0, 5, 0, 2, 8, 20, false);
                System.out.println("Вітаю, ти вибрав Сігітаріуса (Лучник)");
                break;
            case 3:
                gladiator = new Spartac(2, 5, 0, 2, 0, 1, 14, 15, false);
                System.out.println("Вітаю, ти вибрав Спартака (Воїна)");
                break;
            default:
                System.out.println("Невірний вибір!");
        }

        scanner.close();
    }
}