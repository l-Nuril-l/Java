package library;

import org.springframework.stereotype.Component;

public class Book   {
    String name;
    Author author;

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
