package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.streams.GeneratorFigur;
import pl.edu.pw.mini.jrafalko.streams.ImplementacjaInterfejsu;

public class Main {

    public static void main(String[] args) {

        ImplementacjaInterfejsu imp = new ImplementacjaInterfejsu(GeneratorFigur.generuj());

        System.out.println("1. Największa figura względem pola wysokosc:");
        System.out.println(imp.getNajwiekszaFigure());

        System.out.println("2. Figura2D o najmniejszym polu powierzchni:");
        System.out.println(imp.getFigureONajmniejszymPolu());

        System.out.println("3. Najwyższa figura 3D:");
        System.out.println(imp.getNajwyzszaFigure3D());

        System.out.println("4. Najmniejszy sześcian względem objętości:");
        System.out.println(imp.getNajmniejszySzescian());

        System.out.println("5. Lista figur posortowanych względem pola powierzchni:");
        System.out.println(imp.getPosortowaneWzgledemPowiezchni());

        System.out.println("6. 3 figura z posortowanych malejaco względem obwodu:");
        System.out.println(imp.getFigureZPosortowanychMalejacoWgObwodu(3));

        System.out.println("7. Lista 6 pierwszych figur posortowanych rosnąco względem pola powierzchni:");
        System.out.println(imp.getPierwszePosortowaneRosnacoWgPowierzchni(6));

        System.out.println("8. Lista wszystkich sześcianów o długości boku nie większej niż 8:");
        System.out.println(imp.getSzesciany(8));

        System.out.println("9. Koło o najmniejszym polu powierzchni:");
        System.out.println(imp.getNajmniejszeKolo());

        System.out.println("10. Mapa figur względem ID:");
        System.out.println(imp.mapaWgId());

        System.out.println("11. Ilość figur o polu powierzchni nie większym niż 210:");
        System.out.println(imp.getiloscMalych(210));

        System.out.println("12. Posortowany ciąg figur zaczynając od 3:");
        System.out.println(imp.posortowaneWgPolaZaczynajacOd(3));
    }
}
