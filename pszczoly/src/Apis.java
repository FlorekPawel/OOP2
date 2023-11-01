import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Apis {

    protected List<Pszczola> ul;

    public Apis() {
        ul = new ArrayList<>();
        ul.add(new KrolowaMatka("Maja"));
    }

    public void zyciePszczol() {
        int trutenCount = 2;
        for (Pszczola p : ul) {
            if (p instanceof Truten & trutenCount != 0) {
                Thread w = new Thread(p);
                w.start();
                trutenCount--;
            }
            else if(p instanceof Robotnica) {
                Thread w = new Thread(p);
                w.start();
            }
        }
    }

    protected void sortujWgSilyIImienia() {
        PorownanieSily p = new PorownanieSily() {
            @Override
            public int compare(Pszczola p1, Pszczola p2) {
                if (p1.silaAtaku < p2.silaAtaku) return 1;
                else if (p1.silaAtaku > p2.silaAtaku) return -1;
                else return p1.imie.compareTo(p2.imie);
            }
        };
        ul.sort(p);
    }

    public void dodajZolnierza(String imie, int atak) {
        Pszczola p = new Pszczola(imie, atak) {
            @Override
            public String toString() {
                return "Żołnierz " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " i potrafię urządlić.";
            }

            @Override
            public void run() {
                System.out.println("Walka to moje życie!!!");
            }
        };
        ul.add(p);
    }

    public void dodajPszczole(Pszczola p) {
        ul.add(p);
    }

    public void watkiPszczol() throws InterruptedException {
        for (Pszczola p: ul) {
            Thread w = new Thread(p);
            w.start();
            w.join();
        }
    }

    @Override
    public String toString() {
        String s = "W ulu jest " + ul.size() + " pszczół\n";
        for (Pszczola p : ul) {
            s += p.toString() + "\n";
        }
        return s;
    }

    protected abstract class Pszczola implements Runnable{
        protected String imie;
        protected int silaAtaku;
        protected int wiek;

        public Pszczola(String imie, int silaAtaku) {
            this.imie = imie;
            this.silaAtaku = silaAtaku;
            this.wiek = 0;
        }

        @Override
        public void run() {

        }



    }//end pszczola

    protected class KrolowaMatka extends Pszczola {

        protected int iloscJaj;
        public KrolowaMatka(String imie) {
            super(imie, 100);
            this.iloscJaj = 0;
        }

        public void zaplodnienie() {
            iloscJaj += 1000;
        }

        @Override
        public String toString() {
            return "Krolowa " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i będę matką dla " +
                    iloscJaj + " młodych pszczółeek";
        }

        @Override
        public void run() {
            System.out.println("Lot godowy...");
        }
    }//end krolowa

    protected class Truten extends Pszczola{
        protected boolean przydatny;

        public Truten(String imie) {
            super(imie, 0);
            przydatny = true;
        }

        public void zaplodnienie(KrolowaMatka km) {
            if (przydatny) {
                km.zaplodnienie();
                System.out.println(imie + " - byłem z Królową!!! Można umierać...");
                przydatny = false;
            }
        }

        @Override
        public String toString() {
            if (przydatny) {
                return "Truten " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni";
            } else {
                return "Truten " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + ", spełniłem swoje zadanie :(";
            }
        }

        @Override
        public void run() {
            Random r = new Random();
            int i = r.nextInt(0,2);

            if (i == 0) {
                for (Pszczola p: ul) {
                    if (p instanceof KrolowaMatka) {
                        this.zaplodnienie((KrolowaMatka) p);
                        break;
                    }
                }
            } else System.out.println("Jak to przyjemnie nie robić nic...");
        }
    }//end truten

    protected class Robotnica extends Pszczola {
        protected int iloscWyprodukowanegoMiodu;

        public Robotnica(String imie, int silaAtaku) {
            super(imie, silaAtaku);
        }

        public void zbierajNektar(int n) {
            iloscWyprodukowanegoMiodu += n;
            System.out.println(imie + " - kolejna porcja miodu do kubełka");
        }

        @Override
        public String toString() {
            return "Robotnica " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " i zrobiłam "
                    + iloscWyprodukowanegoMiodu + " baryłek miodu :)";
        }

        @Override
        public void run() {
            Random r = new Random();
            int n = r.nextInt(0, 21);
            this.zbierajNektar(n);
        }
    }//end robotnica

    private class PorownanieSily implements Comparator<Pszczola> {

        @Override
        public int compare(Pszczola p1, Pszczola p2) {
            if (p1.silaAtaku < p2.silaAtaku) return 1;
            else if (p1.silaAtaku > p2.silaAtaku) return -1;
            else return 0;
        }
    }


}
