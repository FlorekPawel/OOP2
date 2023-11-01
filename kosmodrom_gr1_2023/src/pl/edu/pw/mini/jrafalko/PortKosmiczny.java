package pl.edu.pw.mini.jrafalko;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class PortKosmiczny {

    private List<StatekKosmiczny> statkiKosmiczne;
    Random rand = new Random();

    public PortKosmiczny(int iloscSamolotow) {
        this.statkiKosmiczne = new ArrayList<>();

        GeneratorNazwy generator = () -> {
            String alfabet = "abcdefghijklmnopqrstuvwxyz";
            String nazwa = "";
            for (int j = 0; j < rand.nextInt(1, 21); j++) {
                nazwa += alfabet.charAt(rand.nextInt(0, 26));
            }
            return nazwa;
        };

        for (int i = 0; i < iloscSamolotow; i++) {
            int r = rand.nextInt(3);
            String nazwa = generator.generuj();
            switch (r) {
                case 0:
                    int predkoscMax = 500 + rand.nextInt(500);
                    int maxIloscPasazerow = 100 + rand.nextInt(200);
                    statkiKosmiczne.add(new StatekKosmicznyPasazerski(nazwa, predkoscMax, maxIloscPasazerow));
                    break;
                case 1:
                    predkoscMax = 300 + rand.nextInt(400);
                    int maxZaladunek = 10 + rand.nextInt(90);
                    statkiKosmiczne.add(new StatekKosmicznyTowarowy(nazwa, predkoscMax, maxZaladunek));
                    break;
                case 2:
                    predkoscMax = 900 + rand.nextInt(2100);
                    statkiKosmiczne.add(new Mysliwiec(nazwa, predkoscMax));
                    break;
            }
        }
    }//end konstruktor PortKosmiczny

    public void wypiszStatkiKosmiczne() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            System.out.println(this.statkiKosmiczne.get(i));
        }
    }

    public void startSststkowKosmicznych() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            statkiKosmiczne.get(i).start(1 + rand.nextInt(24));
        }
    }

    public void zaladunekStatkowKosmicznych() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            if (statkiKosmiczne.get(i) instanceof StatekKosmicznyPasazerski) {
                try {
                    statkiKosmiczne.get(i).zaladunek(rand.nextInt(400));
                } catch (WyjatekKosmiczny wyjatekLotniczy) {
                    System.out.println(wyjatekLotniczy.getMessage());
                }
            } else if (statkiKosmiczne.get(i) instanceof StatekKosmicznyTowarowy) {
                try {
                    statkiKosmiczne.get(i).zaladunek(rand.nextInt(200));
                } catch (WyjatekKosmiczny wyjatekLotniczy) {
                    System.out.println(wyjatekLotniczy.getMessage());
                }
            } else {
                statkiKosmiczne.get(i).zaladunek(rand.nextInt(10));
            }
        }
    }

    public void dzialaniaKosmiczne() {
        statkiKosmiczne.forEach(info);
        statkiKosmiczne.forEach(ladowanie);
        statkiKosmiczne.forEach(info);
        statkiKosmiczne.forEach(zaladowanie);
        statkiKosmiczne.forEach(info);
        statkiKosmiczne.forEach(startowanie);
        statkiKosmiczne.forEach(info);
        statkiKosmiczne.forEach(atakowanie);
    }

    public void sortowanieStatkowKosmicznych() {
        System.out.println("Sortowanie po prędkości:");
        statkiKosmiczne.sort(poMaxPredkosc);
        statkiKosmiczne.forEach(info);

        System.out.println("Sortowanie alfabetyczne: ");
        statkiKosmiczne.sort(alfabetyczne);
        statkiKosmiczne.forEach(info);
    }

    public void sortowanieLosowe() {
        LosowyKomparator los = () -> {
            if (rand.nextBoolean()) return poMaxPredkosc;
            else return alfabetyczne;
        };

        statkiKosmiczne.sort(los.losuj());
        statkiKosmiczne.forEach(info);
    }

    protected static abstract class StatekKosmiczny {
        protected String nazwa;
        protected int predkoscMax;
        protected int iloscGodzinWPowietrzu;
        protected boolean wPowietrzu;
        protected boolean poOdprawie;

        public StatekKosmiczny(String nazwa, int predkoscMax) {
            this.nazwa = nazwa;
            this.predkoscMax = predkoscMax;
        }

        public void start(int godziny) {
            if (poOdprawie) {
                iloscGodzinWPowietrzu += godziny;
                if (!wPowietrzu) {
                    wPowietrzu = true;
                    System.out.println("Startujemy...");
                } else {
                    System.out.println("Lecimy...");
                }
            } else {
                System.out.println("Nie możemy wystartować");
            }
        }

        public void laduj() {
            if (wPowietrzu) {
                wPowietrzu = false;
                poOdprawie = false;
                System.out.println("Lądujemy...");
            } else {
                System.out.println("I tak jesteśmy na ziemi");
            }
        }

        public abstract void zaladunek(int iloscZaladuku) throws WyjatekKosmiczny;
    }//end StatekKosmiczny

    private static class StatekKosmicznyPasazerski extends StatekKosmiczny {

        private final int maxIloscPasazerow;
        private int iloscPasazerow;

        public StatekKosmicznyPasazerski(String nazwa, int maxPredkosc, int maxPasazerow) {
            super(nazwa, maxPredkosc);
            this.maxIloscPasazerow = maxPasazerow;
            this.iloscPasazerow = 0;
        }

        @Override
        public void zaladunek(int l) throws WyjatekKosmiczny {
            if (l < maxIloscPasazerow / 2) {
                iloscPasazerow = l;
                throw new WyjatekEkonomiczny("Za mało pasażerów, nie opłaca się lecieć");
            } else if (l > maxIloscPasazerow) {
                iloscPasazerow = maxIloscPasazerow;
                poOdprawie = true;
                throw new WyjatekPrzeladowania("pasażerów", (l - maxIloscPasazerow));
            } else {
                iloscPasazerow = l;
                poOdprawie = true;
            }
        }

        @Override
        public String toString() {
            return "Statek kosmiczny pasażerski " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin" +
                    ", moze zabrac na pokład " + maxIloscPasazerow + " pasażerów. " +
                    (wPowietrzu ? "Obecnie leci z " + iloscPasazerow + " pasażerami na pokładzie." :
                            "Aktualnie uziemiony");
        }
    }//end StatekKosmicznyPasazerski

    private static class StatekKosmicznyTowarowy extends StatekKosmiczny {
        private final int maxZaladunek;
        private int ladunek;

        public StatekKosmicznyTowarowy(String nazwa, int maxPredkosc, int maxLadunek) {
            super(nazwa, maxPredkosc);
            this.maxZaladunek = maxLadunek;
            this.ladunek = 0;
        }

        @Override
        public void zaladunek(int l) throws WyjatekKosmiczny {
            if (l < maxZaladunek / 2) {
                ladunek = l;
                throw new WyjatekEkonomiczny("Zbyt mało ładunek, nie opłaca się lecieć");
            } else if (l > maxZaladunek) {
                ladunek = maxZaladunek;
                poOdprawie = true;
                throw new WyjatekPrzeladowania("ton ładunku", (l - maxZaladunek));
            } else {
                ladunek = l;
                poOdprawie = true;
            }
        }

        @Override
        public String toString() {
            return "Statek kosmiczny towarowy " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin" +
                    ", moze zabrac na pokład " + maxZaladunek + " ton ładunku. " +
                    (wPowietrzu ? "Obecnie leci z " + ladunek + " t. ładunku." :
                            "Aktualnie uziemiony");
        }
    }//end StatekKosmicznyTowarowy

    protected static class Mysliwiec extends StatekKosmiczny {
        private int iloscRakiet;

        public Mysliwiec(String nazwa, int maxPredkosc) {
            super(nazwa, maxPredkosc);
            this.iloscRakiet = 0;
        }

        private void atak() {
            if (wPowietrzu) {
                iloscRakiet--;
                System.out.println("Ataaaaaaak");
                if (iloscRakiet == 0) this.laduj();
            }
        }

        @Override
        public void zaladunek(int l) {
            iloscRakiet = l;
            poOdprawie = true;
        }

        @Override
        public String toString() {
            return "Myśliwiec " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin. " +
                    (wPowietrzu ? "Obecnie leci, rakiet: " + iloscRakiet + "." :
                            "Aktualnie uziemiony.");
        }
    }//end Mysliwiec


    @FunctionalInterface
    private interface GeneratorNazwy {
        String generuj();
    }

    private interface LosowyKomparator {
        Comparator losuj();
    }

    private Comparator<StatekKosmiczny> poMaxPredkosc = (s1, s2) -> {
        return s1.predkoscMax - s2.predkoscMax;
    };

    private Comparator<StatekKosmiczny> alfabetyczne = (s1, s2) -> {
        if (s1.nazwa.length() > 5 & s2.nazwa.length() > 5) return s1.nazwa.compareTo(s2.nazwa);
        else if (s1.nazwa.length() <= 5 & s2.nazwa.length() > 5) return 1;
        else if (s1.nazwa.length() > 5 & s2.nazwa.length() <= 5) return -1;
        else return 0;
    };

    private Consumer<StatekKosmiczny> info = (statekKosmiczny) -> {
        System.out.println(statekKosmiczny);
    };
    private Consumer<StatekKosmiczny> ladowanie = (statekKosmiczny) -> {
        statekKosmiczny.laduj();
    };
    private Consumer<StatekKosmiczny> zaladowanie = (statekKosmiczny) -> {
        try {
            statekKosmiczny.zaladunek(rand.nextInt(401));
        } catch (WyjatekKosmiczny e) {
            System.out.println(e.getMessage());
        }
    };
    private Consumer<StatekKosmiczny> startowanie = (statekKosmiczny) -> {
        statekKosmiczny.start(10);
    };
    private Consumer<StatekKosmiczny> atakowanie = (statekKosmiczny) -> {
        if (statekKosmiczny instanceof Mysliwiec) ((Mysliwiec) statekKosmiczny).atak();
    };


}//end PortKosmiczny