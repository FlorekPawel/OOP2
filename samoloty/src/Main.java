public class Main {
    public static void main(String[] args) {
        Lotnisko lotnisko = new Lotnisko(5);
        lotnisko.samolotyNaLotnisku();

        System.out.println("\nPróba odlotu: \n---------------------");
        lotnisko.odlot();

        System.out.println("\nOdprawa: \n---------------------");
        lotnisko.odprawaSamolotow();

        System.out.println("\nOdlot: \n---------------------");
        lotnisko.odlot();

        System.out.println("\nDziałania lotniskowe: \n---------------------");
        lotnisko.dzialaniaLotniskowe();

        System.out.println("\nSortowanie samolotów: \n---------------------");
        lotnisko.sortowanieSamolotow();

        System.out.println("\nSortowanie losowe: \n---------------------");
        lotnisko.sortowanieLosowe();
    }
}