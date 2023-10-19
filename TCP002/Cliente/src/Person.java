import java.io.*;

public class Person implements Serializable {
    private String name;
    private int year;
    private Place place;

    public Person(String name, Place place, int year) {
        this.name = name;
        this.year = year;
        this.place = place;
    }

    public String getName() {
        return this.name;
    }

    public int getYear() {
        return this.year;
    }

    public Place getPlace() {
        return this.place;
    }
}
