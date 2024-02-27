package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.cargo.BagOfPotatoes;
import pl.edu.pw.mini.jrafalko.cargo.Barrel;
import pl.edu.pw.mini.jrafalko.cargo.Cargo;
import pl.edu.pw.mini.jrafalko.cargo.Chest;
import pl.edu.pw.mini.jrafalko.truck.SpareWeel;
import pl.edu.pw.mini.jrafalko.truck.Truck;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //Można to zrobić za pomocą metod, ale trzeba pamiętać,
        // że obiekty stworzone w poprzednich punktach
        // mają być wykorzystane w kolejnych

        //task1();
        //task2();
        //todo

        //Lub można też zrobić wszystko bezpośrednio w mainie,
        //ale umieśćcie komentarze, który to punkt jest realizowany

        /* 1. Wypisz listę konstruktorów wraz z parametrami klasy Barrel
         * korzystając z informacji możliwych do uzyskania z obiektu klasy.*/
        System.out.println("--------------1-------------");
        Class<?> c1 = Barrel.class;
        Arrays.stream(c1.getDeclaredConstructors()).toList().forEach(System.out::println);

        /*2.	Ustal, czy klasa Chest posiada konstruktor o modyfikatorze protected,
        przyjmujący dwa parametry: boolean i String. Odpowiedź wypisz w konsoli.*/
        System.out.println("--------------2-------------");
        Class<?> c2 = Chest.class;
        Class<?>[] parameters = {boolean.class, String.class};
        System.out.println(Arrays.stream(c2.getDeclaredConstructors()).anyMatch(constructor -> constructor.getParameterTypes() == parameters | Modifier.isProtected(constructor.getModifiers())));

        //3
        System.out.println("--------------3-------------");
        System.out.println(Cargo.class.getPackage());

        //4
        System.out.println("--------------4-------------");
        Arrays.stream(BagOfPotatoes.class.getDeclaredMethods()).filter(method -> Modifier.isPrivate(method.getModifiers())).forEach(System.out::println);

        //5
        System.out.println("--------------5-------------");
        try {
            System.out.println(SpareWeel.class.getDeclaredField("sticker").get(null));
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
        }

        //6
        System.out.println("--------------6-------------");
        System.out.println(Barrel.class.getSuperclass());

        //7
        System.out.println("--------------7-------------");
        Map<Class<?>, String> m = Arrays.stream(Cargo.class.getInterfaces()).collect(Collectors.toMap(i -> i, i -> i.getPackage() == Cargo.class.getPackage() ? "Ten sam package" : "Inny package"));

        for (Map.Entry<Class<?>, String> entry : m.entrySet()) {
            System.out.println(entry.getKey() + " / " + entry.getValue());
        }

        //8
        System.out.println("--------------8-------------");
        SpareWeel kolo = null;
        try {
            kolo = SpareWeel.class.getDeclaredConstructor().newInstance();
            Field f = SpareWeel.class.getDeclaredField("tireSize");
            f.setAccessible(true);
            System.out.println(f.get(kolo));
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 NoSuchFieldException ignored) {
        }

        //9
        System.out.println("--------------9-------------");
        List<Class<?>> classes = List.of(Barrel.class, Chest.class, BagOfPotatoes.class);

        List<Object> cargo = new ArrayList<>();

        for (Class<?> c : classes) {
            try {
                Constructor<?>[] constructors = c.getDeclaredConstructors();

                for (Constructor<?> constructor : constructors) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 1) {
                        cargo.add(constructor.newInstance(true));
                    } else if (constructor.getParameterCount() == 2) {
                        cargo.add(constructor.newInstance(false, "wejfnwjer"));
                    } else {
                        cargo.add(constructor.newInstance());
                    }
                }
            } catch (Exception ignored) {
            }
        }
        cargo.forEach(System.out::println);

        //10
        System.out.println("--------------10-------------");
        Truck truck = null;
        try {
            truck = Truck.class.getDeclaredConstructor().newInstance();
        } catch (Exception ignored) {
        }

        System.out.println(truck);

        //11
        System.out.println("--------------11-------------");

        try {
            Field loadingbody = Truck.class.getDeclaredField("loadingBody");
            loadingbody.setAccessible(true);
            Method method = loadingbody.getClass().getMethod("addTowar");
            method.invoke(truck, cargo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //12
        System.out.println("--------------12-------------");

        try {
            Truck.class.getField("spareWeel").set(truck, kolo);
            Truck.FuelTank fuelTank = Truck.FuelTank.class.newInstance();
            Truck.class.getField("fuelTank").set(truck, fuelTank);
        } catch (Exception ignored) {
        }

        //13
        System.out.println("--------------13-------------");
        Field f13 = null;
        try {
            f13 = Truck.class.getField("driversCabin");
            f13.setAccessible(true);
            if (f13.get(truck) == null) {
                f13.set(truck, Truck.DriversCabin.class.newInstance());
            }

        } catch (Exception ignored) {
        }

        //14
        System.out.println("--------------14-------------");

        Method drive = null;
        try {
            drive = Truck.DriversCabin.Driver.class.getMethod("drive");
            drive.setAccessible(true);
            drive.invoke(truck);
        } catch (Exception ignored) {

        }


    }


    /**
     1.	Wypisz listę konstruktorów wraz z parametrami klasy Barrel
     korzystając z informacji możliwych do uzyskania z obiektu klasy.
     2.	Ustal, czy klasa Chest posiada konstruktor o modyfikatorze protected,
     przyjmujący dwa parametry: boolean i String. Odpowiedź wypisz w konsoli.
     3.	Uzyskaj informacje o nazwie pakietu klasy Cargo. Wypisz w konsoli.
     4.	Wylistuj prywatne metody klasy BagOfPotatoes wraz z parametrami.
     5.	Ustal (pobierz) wartość pola sticker znajdującego się w klasie KoloOdCiezarowki i wypisz na konsoli.
     6.	Wypisz nazwę nadklasy klasy Barrel.
     7.	Wylistuj interfejsy implementowane przez klasę Cargo. Sprawdź,
     czy któryś z nich pochodzi z tego samego pakietu, co ta klasa.
     8.	Stwórz obiekt klasy KoloOdCiezarowki i sprawdź wartość pola tireSize na tym obiekcie.
     9.	Utwórz kolekcję beczek, skrzyń i worków z ziemniakami używając
     każdego z konstruktorów w tych klasach.
     10.Utwórz obiekt klasy Truck.
     11.Uzyskaj dostęp do pola loadingBody w utworzonym wcześniej obiekcie i wywołaj na nim
     metodę addCargo, dodając obiekty utworzone w punkcie 9-tym.
     12.Przypisz polom (instancji klasy Truck) spareWeel i fuelTank instancje stworzonych
     obiektów klas SpareWeel i FuelTank(napełniony). Informację o typach zaczerpnij z klas tych pól.
     13.Uzyskaj dostęp do pola: driversCabin i sprawdź czy elementy tego pola mają wartość null.
     Jeśli tak, to wstaw tam utworzone obiekty odpowiedniego typu.
     Informację o typach zaczerpnij z klas tych pól.
     14.Wywołaj metodę drive zdefiniowaną dla kierowcy.
     15.Stwórz adnotację TireCompany działającą podczas wykonania programu zawierającą dwie
     informacje będące ciągami znaków: nazwa producenta oraz rozmiar opony.
     "Ozdób" nią klasę spareWeel. Wstaw dowolne wartości.
     */

}
