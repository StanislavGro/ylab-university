package ru.yalabuniversity.homework.lecture3.passwordvalidator.exceptions;

public class WrongLoginException extends Exception{
    public WrongLoginException() {
        super();
    }

    public WrongLoginException(String message) {
        super(message);
    }
}
