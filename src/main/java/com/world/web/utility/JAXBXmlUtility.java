package com.world.web.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.File;
import java.util.Date;

@XmlRootElement(name = "book")
@XmlType(propOrder = {"id", "name", "date"})
class Book {
    private Long id;
    private String name;
    private String author;
    private Date date;

    public Book() {
        super();
    }
    public Book(Long id, String name, String author, Date date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "title")
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    @XmlTransient
    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                '}';
    }
}
public class JAXBXmlUtility {

    public static void main(String[] args ) {

        processMarshalling();
        processUnMarshalling();

    }


    /**
     * Converting Java object to XML
     */
    public static void processMarshalling() {
      Book book = new Book(1234L, "Core Java","James Gosling", new Date());
      try {
          JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
          Marshaller marshaller = jaxbContext.createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
          marshaller.marshal(book, new File("./book.xml"));
      }catch(JAXBException exp) {
          System.out.println("Exception message :: "+exp.getMessage());
      }
    }

    /**
     * Converting XML to Java object
     */
    public static void processUnMarshalling() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Book bookObject = (Book)jaxbContext.createUnmarshaller().unmarshal(new File("./book.xml"));
            System.out.println("Book Unmarshalled object :: "+bookObject);
        }catch(JAXBException exp) {
            System.out.println("Exception message :: "+exp.getMessage());
        }
    }
}
