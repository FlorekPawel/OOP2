package mini.electro;

import java.time.LocalDate;

public abstract class UrzadzenieRTV implements Odbieranie{
    private LocalDate dataProdukcji;
    private String nazwa;
    static int nrObiektu = 0;
    private int iloscWlaczen;
    private boolean wlaczone;

    @Override
    public String toString() {
        return "UrzadzenieRTV{" +
                "dataProdukcji=" + dataProdukcji +
                ", nazwa='" + nazwa + '\'' +
                ", iloscWlaczen=" + iloscWlaczen +
                ", wlaczone=" + wlaczone +
                '}';
    }
}
