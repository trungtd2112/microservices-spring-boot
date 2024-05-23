package com.trung2112.microservice.bookservice.command_side.event;

public class BookDeletedEvent {
    private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
