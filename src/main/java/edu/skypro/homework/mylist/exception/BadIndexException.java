package edu.skypro.homework.mylist.exception;

public class BadIndexException extends RuntimeException {
    public BadIndexException() {
    }

    public BadIndexException(String message) {
        super(message);
    }
}
