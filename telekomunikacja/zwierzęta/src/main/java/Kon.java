public class Kon extends Zwierze{
    public void xd(){
        if(super.getRandomNumber(0, 100) >= 0) {
            System.out.println("xd Koń");
        } else {
            System.out.println("xd coś nie wyszło");
        }
    }
}
