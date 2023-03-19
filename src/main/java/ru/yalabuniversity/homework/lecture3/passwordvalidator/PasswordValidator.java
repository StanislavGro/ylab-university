package ru.yalabuniversity.homework.lecture3.passwordvalidator;

import ru.yalabuniversity.homework.lecture3.passwordvalidator.exceptions.WrongLoginException;
import ru.yalabuniversity.homework.lecture3.passwordvalidator.exceptions.WrongPasswordException;

public class PasswordValidator {
    public static boolean check(String login, String password, String confirmPassword) {
        try {
            if (login.length() >= 20) {
                throw new WrongLoginException("����� ������� �������");
            } else if (!login.matches("([a-zA-Z]|[0-9]|\\_)*")) {
                throw new WrongLoginException("����� �������� ������������ �������");
            }

            if (password.length() >= 20) {
                throw new WrongPasswordException("������ ������� �������");
            } else if (!password.matches("([a-zA-Z]|[0-9]|\\_)*")) {
                throw new WrongLoginException("������ �������� ������������ �������");
            }

            if(password.hashCode() != confirmPassword.hashCode()) {
                throw new WrongPasswordException("������ � ������������� �� ���������");
            }
            else {
                if(!password.equals(confirmPassword)){
                    throw new WrongPasswordException("������ � ������������� �� ���������");
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
