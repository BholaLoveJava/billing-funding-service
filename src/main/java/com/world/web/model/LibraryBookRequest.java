package com.world.web.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LibraryBookRequest {

    @NotNull
    @NotEmpty
    private String bookId;
    @NotNull
    @NotEmpty
    private String bookName;
    @NotNull
    @NotEmpty
    private String bookAuthor;
    @NotNull
    @NotEmpty
    private String rackSection;
    @NotNull
    @NotEmpty
    private String rackRowNumber;

    public LibraryBookRequest() {

    }

    public LibraryBookRequest(String bookId, String bookName, String bookAuthor, String rackSection, String rackRowNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.rackSection = rackSection;
        this.rackRowNumber = rackRowNumber;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getRackSection() {
        return rackSection;
    }

    public void setRackSection(String rackSection) {
        this.rackSection = rackSection;
    }

    public String getRackRowNumber() {
        return rackRowNumber;
    }

    public void setRackRowNumber(String rackRowNumber) {
        this.rackRowNumber = rackRowNumber;
    }

    @Override
    public String toString() {
        return "LibraryBookRequest{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", rackSection='" + rackSection + '\'' +
                ", rackRowNumber='" + rackRowNumber + '\'' +
                '}';
    }
}
