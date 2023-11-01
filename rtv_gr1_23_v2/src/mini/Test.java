package mini;

import mini.electro.FabrykaRTV;

public class Test {

    public static void main(String[] args) {

        FabrykaRTV fabryka = new FabrykaRTV();

        FabrykaRTV.TelewizorCRT t = fabryka.new TelewizorCRT("Rubin", 500, 27, "kolor");
        System.out.println(t);

        fabryka.infoOFabryce();

        System.out.println(fabryka.telewizor3D);
    }
}
