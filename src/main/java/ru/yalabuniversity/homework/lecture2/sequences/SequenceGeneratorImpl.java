package ru.yalabuniversity.homework.lecture2.sequences;

import java.math.BigInteger;

// Класс SequenceGeneratorImpl позволяет вычислить n-ый элементов одной из последовательностей
public class SequenceGeneratorImpl implements SequenceGenerator {

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void a(int n) {
        if (n < 0)
            return;
        for (int i = 0; i < n; i++)
            System.out.print((2 * i + 2) + ", ");
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//    Кастуем к лонг, так как масимальное число int, введенное пользователем входит в диапазон long
//
//    @Override
//    public void a(int n) {
//        if (n < 0)
//            return;
//        for (int i = 0; i < n; i++)
//            System.out.print((2L * i + 2) + ", ");
//        System.out.println("...");
//    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void b(int n) {
        if (n < 0)
            return;
        for (int i = 0; i < n; i++)
            System.out.print((2 * i + 1) + ", ");
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//    Кастуем к лонг, так как масимальное число int, введенное пользователем входит в диапазон long
//
//    @Override
//    public void b(int n) {
//        if (n < 0)
//            return;
//        for (int i = 0; i < n; i++)
//            System.out.print((2L * i + 1) + ", ");
//        System.out.println("...");
//
//    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void c(int n) {
        if (n < 0)
            return;
        for (int i = 0; i < n; i++)
            System.out.print((i + 1) * (i + 1) + ", ");
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//    Кастуем к лонг, так как масимальное число int, введенное пользователем входит в диапахон long
//
//    @Override
//    public void c(int n) {
//        if (n < 0)
//            return;
//        for (int i = 0; i < n; i++)
//            System.out.print(((long) (i + 1) * (long) (i + 1)) + ", ");
//        System.out.println("...");
//    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void d(int n) {
        if (n < 0)
            return;
        for (int i = 1; i <= n; i++) {
            System.out.print((int) Math.pow(i, 3) + ", ");
        }
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//    Создаем объект BigInt, так как диапазона long будет недостаточно
//
//    @Override
//    public void d(int n) {
//        if (n < 0)
//            return;
//        BigInteger bigInteger;
//        for (int i = 0; i < n; i++) {
//            bigInteger = new BigInteger(String.valueOf((long) i + 1));
//            System.out.print(bigInteger.pow(3) + ", ");
//        }
//        System.out.println("...");
//    }

//     В данном случае n последовательности ограничивается только диапазоном int
    @Override
    public void e(int n) {
        if (n < 0)
            return;
        for (int i = 0; i < n; i++) {
            System.out.print(i % 2 == 0 ? 1 : -1);
            System.out.print(", ");
        }
        System.out.println("...");
    }

//     В данном случае n последовательности ограничивается только диапазоном int
    @Override
    public void f(int n) {
        if (n < 0)
            return;
        for (int i = 1; i <= n; i++) {
            System.out.print(i % 2 == 0 ? -i : i);
            System.out.print(", ");
        }
        System.out.println("...");
    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void g(int n) {
        if (n < 0)
            return;
        for (int i = 1; i <= n; i++) {
            System.out.print(i % 2 == 0 ? - (int) Math.pow(i, 2) : (int) Math.pow(i, 2));
            System.out.print(", ");
        }
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//
//    @Override
//    public void g(int n) {
//        if (n < 0)
//            return;
//        long res;
//        for (int i = 0; i < n; i++) {
//            res = (long) (i + 1) * (long) (i + 1);
//            System.out.print(i % 2 == 0 ? res : -res);
//            System.out.print(", ");
//        }
//        System.out.println("...");
//    }

//     В данном случае n последовательности ограничивается только диапазоном int
    @Override
    public void h(int n) {
        if (n < 0)
            return;
        long res = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(i % 2 == 0 ? res++ : 0);
            System.out.print(", ");
        }
        System.out.println("...");
    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void i(int n) {
        if (n < 0)
            return;
        int res;
        int prev = 1;
        for (int i = 1; i <= n; i++) {
            res = i;
            res = res * prev;
            prev = res;
            System.out.print(res + ", ");
        }
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//
//    @Override
//    public void i(int n) {
//        if (n < 0)
//            return;
//
//        BigInteger res;
//        BigInteger prev = BigInteger.ONE;
//        for (int i = 0; i < n; i++) {
//            res = new BigInteger(String.valueOf(i + 1));
//            res = res.multiply(prev);
//            prev = res;
//            System.out.print(res + ", ");
//        }
//        System.out.println("...");
//    }

//     Обозначим, что максимальное число n в последовательности равно 10, тогда
    @Override
    public void j(int n) {
        if (n < 0)
            return;

        int prevOne = 1;
        System.out.print(prevOne + ", ");
        if (n == 1) {
            System.out.println("...");
            return;
        }

        int prevTwo = 1;
        System.out.print(prevTwo + ", ");
        if (n == 2) {
            System.out.println("...");
            return;
        }

        int sum;
        for (int i = 2; i < n; i++) {
            sum = prevOne + prevTwo;
            System.out.print(sum + ", ");
            prevOne = prevTwo;
            prevTwo = sum;
        }
        System.out.println("...");
    }

//    Альтернативный вариант при n = Integer.MAX_VALUE
//    Использую BigInt так как последовательность при n равном Integer.MAX_VALUE не влезет в long
//
//    @Override
//    public void j(int n) {
//        if (n < 0)
//            return;
//
//        BigInteger prevOne = BigInteger.ONE;
//        System.out.print(prevOne + ", ");
//        if (n == 1) {
//            System.out.println("...");
//            return;
//        }
//
//        BigInteger prevTwo = BigInteger.ONE;
//        System.out.print(prevTwo + ", ");
//        if (n == 2) {
//            System.out.println("...");
//            return;
//        }
//
//        BigInteger sum;
//        for (int i = 2; i < n; i++) {
//            sum = prevOne.add(prevTwo);
//            System.out.print(sum + ", ");
//            prevOne = prevTwo;
//            prevTwo = sum;
//        }
//        System.out.println("...");
//    }
}
