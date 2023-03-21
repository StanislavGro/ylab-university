package ru.yalabuniversity.homework.lecture3.passwordvalidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        test("1sjdsAJHJDK1_23", "12345AAb_AA", "12345AAb_AA");
        test("1sjdsAJHJDK1.23", "12345AAb_AA", "12345AAb_AA");
        test("1sjdsAJHJDK1_23", "12345AAb.AA", "12345AAb.AA");
        test("1sjdsAJHJDK1_23", "12345AAb_AA", "12345AAb_A");
    }

    private static void test(String login, String password, String confirmPassword) {
        System.out.println("login: " + login);
        System.out.println("password: " + password);
        System.out.println("confirm: " + confirmPassword);
        System.out.println("Все ли верно?");
        System.out.println(PasswordValidator.check(login, password, confirmPassword) ? "Да, все верно" : "");
        System.out.println("-------------------");
    }
}
