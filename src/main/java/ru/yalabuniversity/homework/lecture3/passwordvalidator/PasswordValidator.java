package ru.yalabuniversity.homework.lecture3.passwordvalidator;

import ru.yalabuniversity.homework.lecture3.passwordvalidator.exceptions.WrongLoginException;
import ru.yalabuniversity.homework.lecture3.passwordvalidator.exceptions.WrongPasswordException;

public class PasswordValidator {
    public static boolean check(String login, String password, String confirmPassword) {
        try {
            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            } else if (!login.matches("([a-zA-Z]|[0-9]|\\_)*")) { // Регулярка которая проверяет что все символы либо латинские, либо числа, либо нижние подчеркивания
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            } else if (!password.matches("([a-zA-Z]|[0-9]|\\_)*")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            // Знаем про контракт equals и hashCode, сначала проврим на hash, а уж потом при необходимости тяжеловесный equals
            if (password.hashCode() != confirmPassword.hashCode()) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            } else {
                if (!password.equals(confirmPassword)) {
                    throw new WrongPasswordException("Пароль и подтверждение не совпадают");
                }
            }
        } catch (WrongLoginException wle) {
            System.out.print(wle.getMessage());
            return false;
        } catch (WrongPasswordException wpe) {
            System.out.print(wpe.getMessage());
            return false;
        }
        return true;
    }
}