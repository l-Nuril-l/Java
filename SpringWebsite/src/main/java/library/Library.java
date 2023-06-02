package library;

import java.util.ArrayList;

public class Library {
    String name;
    String City;
    public ArrayList<Author> authors = new ArrayList<>();
    public ArrayList<Book> books = new ArrayList<>();

    public Library(String name, String city) {
        this.name = name;
        City = city;
    }

    public Library() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
