public class Chomik extends Zwierze{
    public void xd(){
        if(super.getRandomNumber(0, 100) >= 20) {
            System.out.println("xd Chomik");
        } else {
            System.out.println("xd coś nie wyszło");
        }
    }
}
