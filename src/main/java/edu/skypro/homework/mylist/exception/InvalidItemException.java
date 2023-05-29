package edu.skypro.homework.mylist.exception;

public class InvalidItemException extends RuntimeException {
    public InvalidItemException() {
    }

    public InvalidItemException(String message) {
        super(message);
    }
}
