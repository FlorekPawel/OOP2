package mini.electro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FabrykaRTV {

    private List<UrzadzenieRTV> urzadzenia;

    public FabrykaRTV() {
        urzadzenia = new ArrayList<>();
        urzadzenia.add(new Radio("Ela", 10000));
        urzadzenia.add(new TelewizorCRT("QWERTYI", 8874332, 20, "czarno-biały"));
        urzadzenia.add(telewizor3D);
    }

    public void dodajUrzadzenie(UrzadzenieRTV u){
        urzadzenia.add(u);
    }

    public void infoOFabryce(){
        System.out.println("--------------------------");
        System.out.println("Stan magazynowy: ");
        for (UrzadzenieRTV u : urzadzenia) {

            System.out.println(u);
        }
        System.out.println("--------------------------");
    }

    private static abstract class UrzadzenieRTV{
        protected LocalDate dataProdukcji;
        protected String nazwa;
        protected int iloscWlaczen;
        protected boolean czyWlaczone;
        protected static int iloscUrzadzen;
        protected final int nrSeryjny;

        public UrzadzenieRTV(String nazwa, int iloscWlaczen) {
            this.iloscWlaczen = iloscWlaczen;
            this.czyWlaczone = false;
            this.dataProdukcji = LocalDate.now();
            iloscUrzadzen++;
            this.nrSeryjny = iloscUrzadzen;
            this.nazwa = nazwa + "_" + nrSeryjny;
        }

        @Override
        public String toString() {
            return "UrzadzenieRTV{" +
                    "dataProdukcji=" + dataProdukcji +
                    ", nazwa='" + nazwa + '\'' +
                    ", iloscWlaczen=" + iloscWlaczen +
                    ", czyWlaczone=" + czyWlaczone +
                    ", nrSeryjny=" + nrSeryjny +
                    '}';
        }
    }//end Urzadzenie

    public class Radio extends UrzadzenieRTV{
        protected int czestotliwosc;

        public Radio(String nazwa, int iloscWlaczen) {
            super(nazwa, iloscWlaczen);
            this.czestotliwosc = 1000;
        }

    }//end Radio

    public abstract class Telewizor extends UrzadzenieRTV{
        protected int przekatnaEkranu;

        public Telewizor(String nazwa, int iloscWlaczen, int przekatnaEkranu) {
            super(nazwa, iloscWlaczen);
            this.przekatnaEkranu = przekatnaEkranu;
        }
    }//end Telewizor

    public class TelewizorCRT extends Telewizor{
        private String kolor;

        public TelewizorCRT(String nazwa, int iloscWlaczen, int przekatnaEkranu, String kolor) {
            super(nazwa, iloscWlaczen, przekatnaEkranu);
            this.kolor = kolor;
        }

        @Override
        public String toString() {
            return "TelewizorCRT{" +
                    "kolor='" + kolor + '\'' +
                    ", przekatnaEkranu=" + przekatnaEkranu +
                    ", dataProdukcji=" + dataProdukcji +
                    ", nazwa='" + nazwa + '\'' +
                    ", iloscWlaczen=" + iloscWlaczen +
                    ", czyWlaczone=" + czyWlaczone +
                    ", nrSeryjny=" + nrSeryjny +
                    '}';
        }
    }// end TelewizorCRT


    public Telewizor telewizor3D = new Telewizor("3D", 10, 100) {
        @Override
        public String toString() {
            return "Telewizor 3D";
        }
    };

    public Radio radio3D = new Radio("R3D", 10) {
        @Override
        public String toString() {
            return "$classname{" +
                    "czestotliwosc=" + czestotliwosc +
                    '}';
        }
    };
}
