import java.util.*;

public class Board {

    private final double L;
    private final int M;
    private final List<Particle> particles;
    final Map<Integer, List<Particle>> cells;

    public Board(double l, int m, List<Particle> particles) {
        L = l;
        M = m;
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
            cells.get(calculateCellIndexOnBoard(p.getX(), p.getY())).add(p);
        }
        return cells;
    }

    public Integer calculateCellIndexOnBoard(double x, double y){
        int i = (int) (x / (L/M));
        int j = (int) (y / (L/M));
        return i + M*j;
    }

    public static Board getRandomBoard(int n, double l, int m) {
        List<Particle> particles = new ArrayList<>();
        double x, y, r;
        for (int i = 0; i < n; i++) {
            x = Math.random() * l;
            y = Math.random() * l;
            r = Math.random() * (l/m);
            particles.add(new Particle(i, x, y, r));
        }
        return new Board(l, m, particles);
    }

    public double getL() {
        return L;
    }

    public int getM() {
        return M;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Particle> getCell(int idx) {
        return cells.get(idx);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(M*M);
        b.append("Board:\n");
        for (int i = 0; i < M * M; i++) {
            b.append(cells.get(i).size()).append(" ");
            if (i % M == M-1){
                b.append("\n");
            }
        }
        return b.toString();
    }
}
