import java.util.List;
import java.util.Map;

public class Board {

    private final double L;
    private final int M;
    private final double rc;
    final List<Particle> particles;
//    final Map<Integer, List<Particle>> cells;

    public Board(double l, int m, double rc, List<Particle> particles) {
        L = l;
        M = m;
        this.rc = rc;
        this.particles = particles;
//        this.cells = divideParticles(particles);
    }

//    private Map<Integer, List<Particle>> divideParticles(List<Particle> particles){
//
//    }
}
