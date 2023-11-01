package pl.edu.pw.mini.test;

import pl.edu.pw.mini.owady.Apis;

public class Main {

    /*
     * Klasa Main - 1.5p
     * Klasa Apis - 6.5p
     * Przejrzysty/czytelny kod - 1p
     */
    public static void main(String[] args) throws InterruptedException {

        Apis ul = new Apis();
        System.out.println("W ulu jest: " + ul.iloscPszczolWUlu() + " pszczół");
        ul.infoOUlu();
        //--------TODO-------------------------
        // Dodanie pszczół do listy
        //a)
        ul.dodajPszczole(ul.new Truten("Gucio", 10));
        ul.dodajPszczole(ul.new KrolowaMatka("Pipi", 20));
        ul.dodajPszczole(ul.new Robotnica("Paweł", 99, 17));

        //-------------------------------------
        ul.zyciePszczol();
        System.out.println("\nW ulu jest: " + ul.iloscPszczolWUlu() + " pszczół");
        ul.infoOUlu();
        System.out.println("\nPszczoły posortowane wg siły i imienia:");
        ul.sortujWgSilyIImienia();
        ul.infoOUlu();
        System.out.println("\nPszczoły posortowane wg wieku:");
        //--------TODO-------------------------
        //Sortowanie listy pszczół za pomocą komparatora
        //b)
        ul.sortujWgWieku();

        //-------------------------------------
        ul.infoOUlu();
        System.out.println("\nŻołnierz:");
        //--------TODO-------------------------
        //Dodanie żołnierza
        //c)

        ul.dodajZolnierza("Miłosz", 1000, 30);

        //-------------------------------------
        System.out.println((ul.getPszczoly().get(ul.getPszczoly().size() - 1)));
        System.out.println("\nWątki pszczół:");
        ul.watkiPszczol();
    }

}
