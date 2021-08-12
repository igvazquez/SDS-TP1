import java.util.*;

public class BruteForceMethod {

    public static Map<Long, Set<Particle>> BruteForce(List<Particle> particles, final double radius) {
        final Map<Long, Set<Particle>> ret = new HashMap<>();

        for (Particle particle : particles){
            ret.put(particle.getId(), new HashSet<>());
        }

        for(Particle p : particles){

            for (Particle maybeNeighbour : particles) {
                if (p.calculateDistance(maybeNeighbour) < radius) {
                    ret.get(p.getId()).add(maybeNeighbour);
                    ret.get(maybeNeighbour.getId()).add(p);
                }
            }
        }

        return ret;
    }
}
