import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Lotnisko {

    private List<Samolot> samoloty;

    public Lotnisko(int n) {
        samoloty = new ArrayList<>(n);
        Random r = new Random();
        int p;

        for (int i = 0; i < n; i++) {
            p = r.nextInt(0, 3);

            GeneratorNazwy generator = () -> {
                String alfabet = "abcdefghijklmnopqrstuvwxyz";
                String nazwa = "";
                for (int j = 0; j < r.nextInt(1, 21); j++) {
                    nazwa += alfabet.charAt(r.nextInt(0, 26));
                }
                return nazwa;
            };

            if (p == 0)
                samoloty.add(new SamolotPasazerski(generator.generuj(), r.nextInt(500, 1001), r.nextInt(100, 301)));
            else if (p == 1)
                samoloty.add(new SamolotTowarowy(generator.generuj(), r.nextInt(300, 701), r.nextInt(10, 101)));
            else samoloty.add(new Mysliwiec(generator.generuj(), r.nextInt(900, 3001)));
        }
    }

    protected void odprawaSamolotow() {
        Random r = new Random();
        int l;
        for (Samolot s : samoloty) {
            if (s instanceof SamolotPasazerski) l = r.nextInt(401);
            else if (s instanceof SamolotTowarowy) l = r.nextInt(201);
            else l = r.nextInt(10);
            try {
                s.odprawa(l);
            } catch (WyjatekLotniczy e) {
                System.out.println(e.getMessage());
            }
        }
    }

    protected void dzialaniaLotniskowe() {
        Random r = new Random();
        Consumer<Samolot> operacje = (samolot) -> {
            System.out.println(samolot);
            samolot.laduj();
            try {
                samolot.odprawa(r.nextInt(401));
            } catch (WyjatekLotniczy e) {
                System.out.println(e.getMessage());
            }
            samolot.lec(10);
            if (samolot instanceof Mysliwiec) ((Mysliwiec) samolot).atak();
        };

        samoloty.forEach(operacje);
    }

    protected void sortowanieSamolotow() {
        System.out.println("Sortowanie po prędkości:");
        samoloty.sort(poMaxPredkosc);
        samoloty.forEach(System.out::println);

        System.out.println("Sortowanie alfabetyczne: ");
        samoloty.sort(alfabetyczne);
        samoloty.forEach(System.out::println);
    }

    protected void sortowanieLosowe() {
        LosowyKomparator los = () -> {
            Random r = new Random();
            if (r.nextBoolean()) return poMaxPredkosc;
            else return alfabetyczne;
        };

        samoloty.sort(los.losuj());
        samoloty.forEach(System.out::println);
    }

    protected void samolotyNaLotnisku() {
        System.out.println("Samoloty na lotnisku: \n---------------------");
        samoloty.forEach(System.out::println);
    }

    protected void odlot() {
        Random r = new Random();
        for (Samolot s : samoloty) {
            s.lec(r.nextInt(1, 20));
        }
    }


    private abstract class Samolot {
        protected String nazwa;
        protected int maxPredkosc;
        protected int liczbaGodzinWPowietrzu;
        protected boolean wPowietrzu;
        protected boolean odprawiony;

        public Samolot(String nazwa, int maxPredkosc) {
            this.nazwa = nazwa;
            this.maxPredkosc = maxPredkosc;
            this.liczbaGodzinWPowietrzu = 0;
            this.wPowietrzu = false;
        }

        protected void lec(int n) {
            if (wPowietrzu) {
                liczbaGodzinWPowietrzu += n;
                System.out.println("Lecimy");
            } else {
                if (odprawiony) {
                    wPowietrzu = true;
                    liczbaGodzinWPowietrzu += n;
                    System.out.println("Startujemy");
                } else System.out.println("Nie możemy startować");
            }

        }

        protected void laduj() {
            if (wPowietrzu) System.out.println("Lądujemy");
            else System.out.println("I tak jesteśmy na ziemi");
        }

        protected abstract void odprawa(int l) throws WyjatekLotniczy;

        @Override
        public String toString() {
            return " o nazwie " + nazwa + ". Prędkość maksymalna " + maxPredkosc +
                    ", w powietrzu spędził łącznie " + liczbaGodzinWPowietrzu + " godzin. ";
        }

    }//end samolot

    private class SamolotPasazerski extends Samolot {
        private final int maxPasazerow;
        private int liczbaPasazerow;

        public SamolotPasazerski(String nazwa, int maxPredkosc, int maxPasazerow) {
            super(nazwa, maxPredkosc);
            this.maxPasazerow = maxPasazerow;
            this.liczbaPasazerow = 0;
        }

        @Override
        protected void odprawa(int l) throws WyjatekLotniczy {
            if (l < maxPasazerow / 2) {
                liczbaPasazerow = l;
                throw new WyjatekEkonomiczny("Za mało pasażerów, nie opłaca się lecieć");
            } else if (l > maxPasazerow) {
                liczbaPasazerow = maxPasazerow;
                throw new WyjatekPrzeladowania("Za dużo o " + (l - maxPasazerow) + "pasażerów");
            }
        }

        @Override
        public String toString() {
            if (wPowietrzu)
                return "Samolot pasażerski " + super.toString() + "Może zabrać na pokład " + maxPasazerow + " pasażerów. Aktualnie w powietrzu.";
            else
                return "Samolot pasażerski " + super.toString() + "Może zabrać na pokład " + maxPasazerow + " pasażerów. Aktualnie uziemiony.";
        }
    }//end samolot pasazerski

    private class SamolotTowarowy extends Samolot {
        private final int maxLadunek;
        private int ladunek;

        public SamolotTowarowy(String nazwa, int maxPredkosc, int maxLadunek) {
            super(nazwa, maxPredkosc);
            this.maxLadunek = maxLadunek;
            this.ladunek = 0;
        }

        @Override
        protected void odprawa(int l) throws WyjatekLotniczy {
            if (l < maxLadunek / 2) {
                ladunek = l;
                throw new WyjatekEkonomiczny("Zbyt mało ładunek, nie opłaca się lecieć");
            } else if (l > maxLadunek) {
                ladunek = maxLadunek;
                throw new WyjatekPrzeladowania("Za dużo o " + (l - maxLadunek) + "ton ładunku");
            }
        }

        @Override
        public String toString() {
            if (wPowietrzu)
                return "Samolot towarowy " + super.toString() + "Może zabrać na pokład " + maxLadunek + " ton ładunku. Aktualnie w powietrzu.";
            else
                return "Samolot towarowy " + super.toString() + "Może zabrać na pokład " + maxLadunek + " ton ładunku. Aktualnie uziemiony.";
        }
    }//end samolot towarowy

    private class Mysliwiec extends Samolot {
        private int liczbaRakiet;

        public Mysliwiec(String nazwa, int maxPredkosc) {
            super(nazwa, maxPredkosc);
            this.liczbaRakiet = 0;
        }

        @Override
        protected void odprawa(int l) {
            liczbaRakiet = l;

        }

        private void atak() {
            if (wPowietrzu) {
                liczbaRakiet--;
                System.out.println("Ataaaaaaak");
                if (liczbaRakiet == 0) this.laduj();
            }
        }

        @Override
        public String toString() {
            if (wPowietrzu) return "Myśliwiec " + super.toString() + "Aktualnie w powietrzu.";
            else return "Myśliwiec " + super.toString() + "Aktualnie uziemiony.";
        }
    }//end mysliwiec


    @FunctionalInterface
    private interface GeneratorNazwy {
        String generuj();
    }

    private interface LosowyKomparator {
        Comparator losuj();
    }

    private Comparator<Samolot> poMaxPredkosc = (s1, s2) -> s1.maxPredkosc - s2.maxPredkosc;

    private Comparator<Samolot> alfabetyczne = (s1, s2) -> {
        if (s1.nazwa.length() > 5 & s2.nazwa.length() > 5) return s1.nazwa.compareTo(s2.nazwa);
        else if (s1.nazwa.length() <= 5 & s2.nazwa.length() > 5) return 1;
        else if (s1.nazwa.length() > 5 & s2.nazwa.length() <= 5) return -1;
        else return s1.nazwa.compareTo(s2.nazwa);
    };


    private abstract class WyjatekLotniczy extends Exception {
        public WyjatekLotniczy(String message) {
            super(message);
        }
    }//end wyjatek lotniczy

    private class WyjatekEkonomiczny extends WyjatekLotniczy {
        public WyjatekEkonomiczny(String message) {
            super(message);
        }
    }//end wyjatek ekonomiczny

    private class WyjatekPrzeladowania extends WyjatekLotniczy {
        public WyjatekPrzeladowania(String message) {
            super(message);
        }
    }//end wyjatek przeladowania
}
