package mini.electro;

public final class Radio extends UrzadzenieRTV implements Udziekowanie{
    private Fale czestotliwosc;


    @Override
    public void odbieranie(int dana) {
        if (20000<= dana & dana < 1000) {
            setCzestotliwosc(Fale.DLUGIE);
        } else if (dana == 1) {

        }
    }

    @Override
    public void wlaczDzwiek() {

    }

    @Override
    public void wylaczDzwiek() {

    }

    public void setCzestotliwosc(Fale czestotliwosc) {
        this.czestotliwosc = czestotliwosc;
    }
}
