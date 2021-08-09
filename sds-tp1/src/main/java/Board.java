import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final double L;
    private final int M;
    private final double rc;
    final List<Particle> particles;
    final Map<Integer, List<Particle>> cells;

    public Board(double l, int m, double rc, List<Particle> particles) {
        L = l;
        M = m;
        this.rc = rc;
        this.particles = particles;
        this.cells = divideParticles(particles);
    }

    private Map<Integer, List<Particle>> divideParticles(List<Particle> particles){

        Map<Integer, List<Particle>> cells = new HashMap<>();

        for (int i = 0; i < M * M; i++) {
            cells.put(i, new ArrayList<>());
        }

        for(Particle p : particles){

            if (p.getX() < 0 || p.getX() > L || p.getY() < 0 || p.getY() > L){
                throw new IllegalArgumentException();
            }

            int x = (int) (p.getX() / (L/M));
            int y = (int) (p.getY() / (L/M));

            cells.get(x + M*y).add(p);
            /*
           8 6 7 8 6
           2 0 1 2 0
           5 3 4 5 3
           8 6 7 8 6
           2 0 1 2 0
            */
        }

        return cells;
    }
}
