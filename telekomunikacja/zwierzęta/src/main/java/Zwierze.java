import java.util.Random;

public class Zwierze {
    public void xd(){
        if(getRandomNumber(0, 100) > 2) {
            System.out.println("xd zwierze");
        } else {
            System.out.println("xd coś nie wyszło");
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
