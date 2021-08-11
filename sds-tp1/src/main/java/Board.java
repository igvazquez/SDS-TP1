import java.util.*;

public class Board {

    private final double L;
    private final int M;
    private final double rc;
    private final boolean periodicOutline;
    final List<Particle> particles;
    final Map<Integer, Set<Particle>> neighboursMap;
    final Map<Integer, List<Particle>> particlesInCells;

    private static final int OUT_OF_BOUNDS = -1;

    public Board(double l, int m, double rc, List<Particle> particles, boolean periodicOutline) {
        L = l;
        M = m;
        this.rc = rc;
        this.periodicOutline = periodicOutline;
        this.particles = particles;
        this.particlesInCells = divideParticles(particles);
        this.neighboursMap = calculateNeighboursMap();
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

    private void addNeighboursToCells(Map<Integer, Set<Particle>> neighbours, int currentCell, int neighbourCell) {
        if(neighbourCell != OUT_OF_BOUNDS) {
            if(currentCell != neighbourCell) {
                neighbours.get(currentCell).addAll(particlesInCells.get(neighbourCell));
                neighbours.get(neighbourCell).addAll(particlesInCells.get(currentCell));
            } else {
                neighbours.get(currentCell).addAll(particlesInCells.get(currentCell));
            }
        }
    }

    private int getRightIndex(int currentCellIndex, int row, int col) {
        int rightCol;
        if(periodicOutline) {
            rightCol = Math.floorMod(currentCellIndex + 1, M);
            return rightCol + row*M;
        } else {
            rightCol = col + 1;
            return rightCol >= M ? OUT_OF_BOUNDS : rightCol + row*M;
        }
    }

    private int getUpperRightIndex(int row, int col) {
        int upperRightRow;
        int upperRightCol;

        if(periodicOutline) {
            upperRightRow = Math.floorMod(row - 1, M);
            upperRightCol = Math.floorMod(col + 1, M);
            return upperRightCol + upperRightRow*M;
        } else {
            upperRightCol = col + 1;
            upperRightRow = row - 1;
            return (upperRightRow < 0 || upperRightCol >= M) ? OUT_OF_BOUNDS : upperRightCol + upperRightRow*M;
        }
    }

    private int getLowerRightIndex(int row, int col) {
        int lowerRightRow;
        int lowerRightCol;

        if(periodicOutline) {
            lowerRightRow = Math.floorMod(row + 1, M);
            lowerRightCol = Math.floorMod(col + 1, M);
            return lowerRightCol + lowerRightRow*M;
        } else {
            lowerRightRow = row + 1;
            lowerRightCol = col + 1;
            return (lowerRightRow >= M || lowerRightCol >= M) ? OUT_OF_BOUNDS : lowerRightCol + lowerRightRow*M;
        }
    }

    private int getLowerIndex(int row, int col) {
        int lowerRow;

        if(periodicOutline) {
            lowerRow = Math.floorMod(row + 1, M);
            return col + lowerRow*M;
        } else {
            lowerRow = row + 1;
            return lowerRow >= M ? OUT_OF_BOUNDS : col + lowerRow*M;
        }
    }

    public Map<Integer, Set<Particle>> calculateNeighboursMap(){
        Map<Integer, Set<Particle>> neighbours = new HashMap<>();

        for (int i = 0; i < M * M; i++) {
            neighbours.put(i, new HashSet<>());
        }

        for (int i = 0; i < M * M; i++) {
            int row = i / M;
            int col = i % M;

            //add this cell particles as neighbours
            addNeighboursToCells(neighbours, i, i);
            //add right neighbours
            addNeighboursToCells(neighbours, i, getRightIndex(i, row, col));
            //add upper right neigbours
            addNeighboursToCells(neighbours, i, getUpperRightIndex(row, col));
            //add lower right neighbours
            addNeighboursToCells(neighbours, i, getLowerRightIndex(row, col));
            //add lower neighbours
            addNeighboursToCells(neighbours, i, getLowerIndex(row, col));
        }

        return neighbours;
    }

    public List<Particle> getNeighboursOf(Particle particle) {

        if (!particles.contains(particle)){
            throw new IllegalArgumentException("Particle does not belong to this board");
        }

        List<Particle> ret = new ArrayList<>();

        int idx = calculateCellIndexOnBoard(particle.getX(), particle.getY());

        Set<Particle> neighbours = neighboursMap.get(idx);

        for (Particle n : neighbours){
            if (particle.calculateDistance(n) < rc){
                ret.add(n);
            }
        }

        return ret;
    }

    public static Board getRandomBoard(int p, double l, int m, double rc, boolean periodicOutline) {

        List<Particle> particles = new ArrayList<>();
        double x, y, r;
        for (int i = 0; i < p; i++) {
            x = Math.random() * l;
            y = Math.random() * l;
            r = Math.random() * (l/m);
            particles.add(new Particle(i, x, y, r));
        }

        return new Board(l, m, rc, particles, periodicOutline);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(M*M);
        b.append("Board:\n");
        for (int i = 0; i < M * M; i++) {
            b.append(particlesInCells.get(i).size()).append(" ");
            if (i % M == M-1){
                b.append("\n");
            }
        }
        return b.toString();
    }
}
