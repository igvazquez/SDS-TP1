import java.util.*;

public class Neighbours {

    public static void main(String[] args) {

        List<Particle> particleList = new ArrayList<>();

        particleList.add(new Particle(1, 1.5, 0.5, 1));
        particleList.add(new Particle(2, 0.5, 1.5, 1));
        particleList.add(new Particle(3, 0, 0, 1));
        particleList.add(new Particle(6, 0, 0, 1));
        particleList.add(new Particle(7, 0, 0, 1));
        particleList.add(new Particle(4, 2.5, 1.5, 1));
        particleList.add(new Particle(5, 1.5, 2.5, 1));
        particleList.add(new Particle(8, 3.5, 3.5, 1));
        particleList.add(new Particle(9, 1.5, 4.5, 1));


        Board board = new Board(5, 5, 1, particleList, false);

        Map<Integer, Set<Particle>> neighbours = board.calculateNeighboursMap();
        //List<List<Particle>> neighbourLists = board.getNeighbours(particleList);
        
        System.out.println(board);

    }
}
