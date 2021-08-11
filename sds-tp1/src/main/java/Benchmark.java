import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Benchmark {

    public static void main(String[] args) throws FileNotFoundException {



//        Board board = Board.

//        System.out.println(board);
        long totalNanos = 0;
        for (int i = 0; i < 100; i++) {
            long initTime = System.nanoTime();
//            List<Particle> neighbourLists = board.getNeighboursOf(particleList.get(0));
            long finishTime = System.nanoTime();
            totalNanos += finishTime - initTime;
        }

        double avg = totalNanos/100.0;
        System.out.println("Tiempo transcurrido: " + avg/(Math.pow(10,6)) + "(ms)");
    }
}
