package pl.edu.pw.mini.owady;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Apis {

    private ArrayList<Pszczola> pszczoly;

    public int iloscPszczolWUlu() {return pszczoly.size();}
    public ArrayList<Pszczola> getPszczoly() {return pszczoly;}
    public void infoOUlu() {for (Pszczola p : pszczoly) System.out.println(p);}

    public Apis() {
        pszczoly = new ArrayList<>();
        pszczoly.add(new KrolowaMatka("Maja", 18));
        pszczoly.add(new Truten("Mata", 30));
        pszczoly.add(new Truten("Mateusz", 8));
        pszczoly.add(new Truten("Hubert", 16));
        pszczoly.add(new Robotnica("Krzychu", 101, 2));
        pszczoly.add(new Robotnica("Michał", 0, 23));
        pszczoly.add(new Robotnica("Bartek", 200, 15));
    }

    public void zyciePszczol() throws InterruptedException {
        int trutenCount = 2;
        int robotniceCount = 2;
        for (Pszczola p : pszczoly) {
            if (p instanceof Truten & trutenCount != 0) {
                Thread w = new Thread(p);
                w.start();
                w.join();
                trutenCount--;
            }
            else if(p instanceof Robotnica & robotniceCount != 0) {
                Thread w = new Thread(p);
                w.start();
                w.join();
                robotniceCount--;
            }
            p.wiek += 1;
        }
    }

    public void sortujWgSilyIImienia() {
        PorownanieWieku p = new PorownanieWieku() {
            @Override
            public int compare(Pszczola p1, Pszczola p2) {
                if (p1.silaAtaku < p2.silaAtaku) return 1;
                else if (p1.silaAtaku > p2.silaAtaku) return -1;
                else return p1.imie.compareTo(p2.imie);
            }
        };
        pszczoly.sort(p);
    }

    public void sortujWgWieku() {
        pszczoly.sort(new PorownanieWieku());
    }

    public void dodajZolnierza(String imie, int atak, int wiek) {
        Pszczola p = new Pszczola(imie, atak, wiek) {
            @Override
            public String toString() {
                return "Żołnierz " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i potrafię urządlić.";
            }

            @Override
            public void run() {
                System.out.println("Walka to moje życie!!!");
            }
        };
        pszczoly.add(p);
    }

    public void dodajPszczole(Pszczola p) {
        pszczoly.add(p);
    }

    public void watkiPszczol() throws InterruptedException {
        for (Pszczola p: pszczoly) {
            Thread w = new Thread(p);
            w.start();
            w.join();
            p.wiek += 1;
        }
    }

    protected abstract static class Pszczola implements Runnable{
        protected String imie;
        protected int silaAtaku;
        protected int wiek;

        public Pszczola(String imie, int silaAtaku, int wiek) {
            this.imie = imie;
            this.silaAtaku = silaAtaku;
            this.wiek = wiek;
        }

        @Override
        public void run() {

        }



    }//end pszczola

    public class KrolowaMatka extends Pszczola {

        protected int iloscJaj;
        public KrolowaMatka(String imie, int wiek) {
            super(imie, 100, wiek);
            this.iloscJaj = 0;
        }

        public void zaplodnienie() {
            iloscJaj += 1000;
        }

        @Override
        public String toString() {
            return "Krolowa " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i będę matką dla " +
                    iloscJaj + " młodych pszczółek";
        }

        @Override
        public void run() {
            System.out.println("Lot godowy...");
        }
    }//end krolowa

    public class Truten extends Pszczola{
        protected boolean przydatny;

        public Truten(String imie, int wiek) {
            super(imie, 0, wiek);
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
                return "Truten " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni, spełniłem swoje zadanie :(";
            }
        }

        @Override
        public void run() {
            Random r = new Random();
            int i = r.nextInt(0,2);

            if (i == 0) {
                for (Pszczola p: pszczoly) {
                    if (p instanceof KrolowaMatka) {
                        this.zaplodnienie((KrolowaMatka) p);
                        break;
                    }
                }
            } else System.out.println("Jak to przyjemnie nie robić nic...");
        }
    }//end truten

    public class Robotnica extends Pszczola {
        protected int iloscWyprodukowanegoMiodu;

        public Robotnica(String imie, int silaAtaku, int wiek) {
            super(imie, silaAtaku, wiek);
        }

        public void zbierajNektar(int n) {
            iloscWyprodukowanegoMiodu += n;
            System.out.println(imie + " - kolejna porcja miodu do kubełka");
        }

        @Override
        public String toString() {
            return "Robotnica " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i zrobiłam "
                    + iloscWyprodukowanegoMiodu + " baryłek miodu :)";
        }

        @Override
        public void run() {
            Random r = new Random();
            this.zbierajNektar(r.nextInt(0, 21));
        }
    }//end robotnica

    private static class PorownanieWieku implements Comparator<Pszczola> {

        @Override
        public int compare(Pszczola p1, Pszczola p2) {
            return Integer.compare(p2.wiek, p1.wiek);
        }
    }
}
