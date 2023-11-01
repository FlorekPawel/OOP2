import mini.Person;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Person adas = new Person("Adam", "Kolano", LocalDate.of(2000, 3,16), 78);
        System.out.println(adas);
        adas.eatMeal(400);
        System.out.println(adas);
    }
}
