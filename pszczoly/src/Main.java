public class Main {
    public static void main(String[] args) throws InterruptedException {
        Apis apis = new  Apis();

        apis.dodajPszczole(apis.new Truten("Gucio"));
        apis.dodajPszczole(apis.new Truten("Paweł"));
        apis.dodajPszczole(apis.new Robotnica("Krzychu", 100));

        System.out.println(apis.ul);
        apis.sortujWgSilyIImienia();
        System.out.println(apis);

        apis.zyciePszczol();

        apis.dodajZolnierza("Miłosz", 1000);

        apis.watkiPszczol();
    }
}