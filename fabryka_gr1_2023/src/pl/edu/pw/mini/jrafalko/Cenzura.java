package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.adnotacje.PodmianaString;
import pl.edu.pw.mini.jrafalko.adnotacje.PustyString;
import pl.edu.pw.mini.jrafalko.adnotacje.StringPodloga;
import pl.edu.pw.mini.jrafalko.censor.Censorable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cenzura implements Censorable {
    @Override
    public List<Pracownik> cenzuruj(List<Pracownik> list) {
        for (Pracownik pracownik : list) {
            Class<? extends Pracownik> klasaPracowanika = pracownik.getClass();
            List<Field> fields = pobierzWszystkiePola(pracownik.getClass());

            if (klasaPracowanika.isAnnotationPresent(PustyString.class)) {
                for (Field field : fields) {
                    if (field.getType().equals(String.class)) {
                        field.setAccessible(true);
                        try {
                            field.set(pracownik, "");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            for (Field field : klasaPracowanika.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(StringPodloga.class)) {
                    if (field.getType().equals(String.class)) {
                        try {
                            String s = (String) field.get(pracownik);
                            s = s.substring(0, 3) + "_";
                            field.set(pracownik, s);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (field.isAnnotationPresent(PodmianaString.class)) {
                    if (field.getType().equals(String.class)) {
                        try {
                            field.set(pracownik, field.getAnnotation(PodmianaString.class).zastap());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }


        return list;
    }


    public static List<Field> pobierzWszystkiePola(Class<?> klasa) {
        List<Field> wszystkiePola = new ArrayList<>();

        while (klasa != null) {
            Field[] pola = klasa.getDeclaredFields();
            for (Field pole : pola) {
                wszystkiePola.add(pole);
            }
            klasa = klasa.getSuperclass();
        }

        return wszystkiePola;
    }
}

