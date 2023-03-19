package ru.yalabuniversity.homework.lecture3.passwordvalidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "1sjdsAJHJDK1_23";
        String password = "12345AAb_AA";
        String confirm = "12345AAb_AA";
        System.out.println("login: " + login);
        System.out.println("password: " + password);
        System.out.println("confirm: " + confirm);
        System.out.println("Все ли верно?");
        System.out.println(PasswordValidator.check(login, password, confirm) ? "Да, все верно" : "");
        System.out.println("-------------------");

        String login2 = "1sjdsAJHJDK1.23";
        String password2 = "12345AAb_AA";
        String confirm2 = "12345AAb_AA";
        System.out.println("login: " + login2);
        System.out.println("password: " + password2);
        System.out.println("confirm: " + confirm2);
        System.out.println("Все ли верно?");
        System.out.println(PasswordValidator.check(login2, password2, confirm2) ? "Да, все верно" : "");
        System.out.println("-------------------");

        String login3 = "1sjdsAJHJDK1_23";
        String password3 = "12345AAb.AA";
        String confirm3 = "12345AAb.AA";
        System.out.println("login: " + login3);
        System.out.println("password: " + password3);
        System.out.println("confirm: " + confirm3);
        System.out.println("Все ли верно?");
        System.out.println(PasswordValidator.check(login3, password3, confirm3) ? "Да, все верно" : "");
        System.out.println("-------------------");

        String login4 = "1sjdsAJHJDK1_23";
        String password4 = "12345AAb_AA";
        String confirm4 = "12345AAb_A";
        System.out.println("login: " + login4);
        System.out.println("password: " + password4);
        System.out.println("confirm: " + confirm4);
        System.out.println("Все ли верно?");
        System.out.println(PasswordValidator.check(login4, password4, confirm4) ? "Да, все верно" : "");
        System.out.println("-------------------");
    }
}
