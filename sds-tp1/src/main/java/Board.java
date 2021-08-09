import java.util.*;

public class Board {

    private final double L;
    private final int M;
    private final double rc;
    private boolean periodicOutline;
    final List<Particle> particles;
    final Map<Integer, List<Particle>> particlesInCells;

    public Board(double l, int m, double rc, List<Particle> particles, boolean periodicOutline) {
        L = l;
        M = m;
        this.rc = rc;
        this.periodicOutline = periodicOutline;
        this.particles = particles;
        this.particlesInCells = divideParticles(particles);
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

            cells.get(calculateCellIndexOnBoard(p.getX(), p.getY())).add(p);
        }

        return cells;
    }

    private Integer calculateCellIndexOnBoard(double x, double y){
        int i = (int) (x / (L/M));
        int j = (int) (y / (L/M));

        return i + M*j;
    }

    public Map<Integer, Set<Particle>> calculateNeighboursMap(){
        Map<Integer, Set<Particle>> neighbours = new HashMap<>();

        for (int i = 0; i < M * M; i++) {
            neighbours.put(i, new HashSet<>());
        }

        for (int i = 0; i < M * M; i++) {
            int row = i / M;
            int col = i % M;
            int idx;
            neighbours.get(i).addAll(particlesInCells.get(i));

            int rightCol = Math.floorMod(i + 1, M);
            idx = rightCol + row*M;
            neighbours.get(i).addAll(particlesInCells.get(idx));
            neighbours.get(idx).addAll(particlesInCells.get(i));

            int upperRightRow = Math.floorMod(row - 1, M);
            int upperRightCol = Math.floorMod(col + 1, M);
            idx = upperRightCol + upperRightRow*M;
            neighbours.get(i).addAll(particlesInCells.get(idx));
            neighbours.get(idx).addAll(particlesInCells.get(i));

            int lowerRightRow = Math.floorMod(row + 1, M);
            int lowerRightCol = Math.floorMod(col + 1, M);
            idx = lowerRightCol + lowerRightRow*M;
            neighbours.get(i).addAll(particlesInCells.get(idx));
            neighbours.get(idx).addAll(particlesInCells.get(i));

            int lowerRow = Math.floorMod(row + 1, M);
            idx = col + lowerRow*M;
            neighbours.get(i).addAll(particlesInCells.get(idx));
            neighbours.get(idx).addAll(particlesInCells.get(i));
        }

        return neighbours;
    }
}
