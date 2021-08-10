import java.util.*;

public class Neighbours {

    public static void main(String[] args) {

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


        Board board = new Board(5, 5, 1, particleList, false);

        Map<Integer, Set<Particle>> neighbours = board.calculateNeighboursMap();
        List<Particle> neighbourLists = board.getNeighboursOf(particleList.get(0));
        
        System.out.println(board);
        System.out.println("Particula" + particleList.get(0));
        System.out.println("Vecinos " + neighbourLists);
        System.out.println("Size " + neighbourLists.size());
    }
}
