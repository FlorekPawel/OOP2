package Test;

import Ciezarowka.*;
import Ladunek.*;


import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        // task 1
        System.out.println("--------------------1--------------------");

        Class<?> c1 = Barrel.class;
        Arrays.stream(c1.getDeclaredConstructors()).toList().forEach(System.out::println);

        // task 2
        System.out.println("--------------------2--------------------");

        Class<?> c2 = Chest.class;
        Class<?>[] parameters = {boolean.class, String.class};
        System.out.println(Arrays.stream(c2.getDeclaredConstructors()).anyMatch(constructor -> constructor.getParameterTypes() == parameters | Modifier.isProtected(constructor.getModifiers())));

        // task 3
        System.out.println("--------------------3--------------------");

        Class<?> c3 = Cargo.class;
        System.out.println(c3.getPackage());

        // task 4
        System.out.println("--------------------4--------------------");

        Class<?> c4 = BagOfPotatoes.class;
        Arrays.stream(c4.getDeclaredMethods()).filter(method -> Modifier.isPrivate(method.getModifiers())).toList().forEach(System.out::println);

        // task 5
        System.out.println("--------------------5--------------------");

        Class<?> c5 = KoloOdCiezarowki.class;
        try {
            Field f5 = c5.getDeclaredField("sticker");
            f5.setAccessible(true);
            KoloOdCiezarowki kolo = (KoloOdCiezarowki) c5.getDeclaredConstructor().newInstance();
            f5.set(kolo, "Nalypka");
            System.out.println(f5.get(kolo));

        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException ignored) {
        }

        // task 6
        System.out.println("--------------------6--------------------");

        Class<?> c6 = Barrel.class;
        System.out.println(c6.getSuperclass());

        // task 7
        System.out.println("--------------------7--------------------");

        Class<?> c7 = Cargo.class;
        Map<Class<?>, String> m = Arrays.stream(c7.getInterfaces()).collect(Collectors.toMap(i -> i, i -> i.getPackage() == c7.getPackage() ? "Ten sam package" : "Inny package"));

        for (Map.Entry<Class<?>, String> entry : m.entrySet()) {
            System.out.println(entry.getKey() + " / " + entry.getValue());
        }

        // task 8
        System.out.println("--------------------8--------------------");


        Class<?> c8 = KoloOdCiezarowki.class;
        try {
            KoloOdCiezarowki k8 = (KoloOdCiezarowki) c8.getDeclaredConstructor().newInstance();
            Field f8 = c8.getDeclaredField("tiresize");
            f8.setAccessible(true);
            f8.set(k8, 20);
            System.out.println(f8.get(k8));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NoSuchFieldException ignored) {

        }

        // task 9
        System.out.println("--------------------9--------------------");

        // task 10
        System.out.println("--------------------10--------------------");

        Class<?> c10 = Truck.class;
        Truck t10 = null;
        try {
            t10 = (Truck) c10.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException ignored) {
        }
        System.out.println(t10);

        // task 11
        System.out.println("--------------------11--------------------");


        // task 12
        System.out.println("--------------------12--------------------");

        // task 13
        System.out.println("--------------------13--------------------");

        // task 14
        System.out.println("--------------------14--------------------");
    }
}
