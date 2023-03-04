package ru.yalabuniversity.homework.lecture1;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(recursive(n));
//            Дополнительно добавил один из вариантов нерекурсвивного решения задачи
//            System.out.println(nonrecursive(n));
        }
    }

    private static long recursive(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return 2 * recursive(n - 1) + recursive(n - 2);
//        В одну строчку, но плохочитаемо, при помощи тернарных выражений
//        return n != 0 ? n != 1 ? 2 * recursive(n - 1) + recursive(n - 2) : 1  : 0;
    }

    private static long nonrecursive(int n) {
        if (n == 0)
            return 0;
        long prev = 0, res = 1;
        for (int i = 2; i <= n; i++) {
            long temp = res;
            res = 2 * res + prev;
            prev = temp;
        }
        return res;
    }
}
