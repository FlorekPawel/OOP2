package mini;

import java.time.LocalDate;

public class Person {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private double weight;

    public Person(String name, String surname, LocalDate birthDate, double weight) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.weight = weight;
    }

    public void eatMeal(int kcal) {
        weight += kcal/7000.0;
    }

    @Override
    public String toString() {
        return name + " " + surname + ", ur. " + birthDate + ", waga: " + weight + "kg";
    }
}
