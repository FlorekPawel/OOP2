package pl.edu.pw.mini.jrafalko.workers;

import pl.edu.pw.mini.jrafalko.Pracownik;
import pl.edu.pw.mini.jrafalko.adnotacje.PodmianaString;
import pl.edu.pw.mini.jrafalko.adnotacje.StringPodloga;

public class Dyrektor extends Pracownik {

    private String ksywka;
    @PodmianaString(zastap = "Władywostok")
    private String miastoUrodzenia;
    @StringPodloga
    private String charakterystykaOsobowosci;
    private int iloscPodwladnych;

    public Dyrektor(String imie, String nazwisko, int wiek, String ksywka,
                    String miastoUrodzenia, String charakterystykaOsobowosci,
                    int iloscPodwladnych) {
        super(imie, nazwisko, wiek);
        this.ksywka = ksywka;
        this.miastoUrodzenia = miastoUrodzenia;
        this.charakterystykaOsobowosci = charakterystykaOsobowosci;
        this.iloscPodwladnych = iloscPodwladnych;
    }

    @Override
    protected void zwiekszZysk() {
        wypracowanyZysk += 10;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ksywka='" + ksywka + '\'' +
                ", miastoUrodzenia='" + miastoUrodzenia + '\'' +
                ", charakterystykaOsobowosci='" + charakterystykaOsobowosci + '\'' +
                ", iloscPodwladnych=" + iloscPodwladnych +
                ", dyrektor";
    }
}
