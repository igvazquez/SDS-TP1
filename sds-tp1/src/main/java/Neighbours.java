import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neighbours {

    public static void main(String[] args) {

        List<Particle> particleList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            particleList.add(new Particle(i, i*0.5, i*0.5, 0.1));
        }
        Board board = new Board(10, 10, 1, particleList);


    }
}
