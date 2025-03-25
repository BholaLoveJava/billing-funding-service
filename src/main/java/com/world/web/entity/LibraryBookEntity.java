package com.world.web.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "LibraryBook")
public class LibraryBookEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3736398798L;

    public LibraryBookEntity() {}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long libraryBookId;

    @Column(name = "bookId")
    private String bookId;
    @Column(name = "bookName")
    private String bookName;
    @Column(name = "bookAuthor")
    private String bookAuthor;
    @Column(name = "rackSection")
    private String rackSection;
    @Column(name = "rackRowNumber")
    private String rackRowNumber;

    public Long getLibraryBookId() {
        return libraryBookId;
    }

    public void setLibraryBookId(Long libraryBookId) {
        this.libraryBookId = libraryBookId;
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
        return "LibraryBookEntity{" +
                "libraryBookId=" + libraryBookId +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", rackSection='" + rackSection + '\'' +
                ", rackRowNumber='" + rackRowNumber + '\'' +
                '}';
    }
}
