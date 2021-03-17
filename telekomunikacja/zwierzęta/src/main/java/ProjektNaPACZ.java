public class ProjektNaPACZ {
    //autorzy: Antoni Karwowski, Michał Gebel
    public static void main(String args[]){
        Zwierze niezidentyfikowaneZwierze = new Zwierze();
        Chomik chomik = new Chomik();
        Kon kon = new Kon();
        while (true) {
            xd(niezidentyfikowaneZwierze);
            xd(chomik);
            xd(kon);
        }

    }

    private static void xd(Zwierze zwierze) {
        zwierze.xd();
    }
}
