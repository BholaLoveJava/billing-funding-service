package com.world.web.model;

public class LibraryBookResponse {

    private Long libraryId;
    private String bookName;
    private String bookAuthor;
    private String rackSection;
    private String rackRowNumber;

    public LibraryBookResponse() {

    }
    public LibraryBookResponse(Long libraryId, String bookName, String bookAuthor, String rackSection, String rackRowNumber) {
        this.libraryId = libraryId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.rackSection = rackSection;
        this.rackRowNumber = rackRowNumber;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor(){
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
        return "LibraryBookResponse{" +
                "libraryId=" + libraryId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\''+
                ", rackSection='" + rackSection + '\'' +
                ", rackRowNumber='" + rackRowNumber + '\'' +
                '}';
    }
}
