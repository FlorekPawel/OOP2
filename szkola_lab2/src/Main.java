import mini.Person;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Person ania = new Person("Anna", "Udo", LocalDate.of(2005, 5,12), 50);
        ania.eatMeal(100);
        System.out.println(ania);

    }
}