import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Neighbours {

    public static void main(String[] args) throws FileNotFoundException {

        InputStream inputStream = new FileInputStream("sds-tp1/src/main/resources/config.yaml");

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        List<Particle> particleList = new ArrayList<>();

        particleList.add(new Particle(1, 1.5, 0.5, 0));
        particleList.add(new Particle(2, 0.5, 1.5, 0));
        particleList.add(new Particle(3, 0, 0, 0));
        particleList.add(new Particle(6, 0.6, 0.5, 0));
        particleList.add(new Particle(7, 0, 0, 0));
        particleList.add(new Particle(4, 2.5, 1.5, 0));
        particleList.add(new Particle(5, 1.5, 2.5, 0));
        particleList.add(new Particle(8, 3.5, 3.5, 0));
        particleList.add(new Particle(9, 1.5, 4.5, 0));


        Board board = new Board((double)data.get("boardLength"), (int)data.get("M"),
                (double)data.get("radius"), particleList, (Boolean)data.get("periodicOutline"));

        List<Particle> neighbourLists = board.getNeighboursOf(particleList.get(0));
        
        System.out.println(board);
        System.out.println("Particula" + particleList.get(0));
        System.out.println("Vecinos " + neighbourLists);
        System.out.println("Size " + neighbourLists.size());
    }
}
