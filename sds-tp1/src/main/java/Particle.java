import java.util.Objects;

public class Particle {

    private final long id;
    private final Point coordinates;
    private final double radius;

    public Particle(long id, long x, long y, double radius) {
        this.id = id;
        this.radius = radius;
        this.coordinates = new Point(x, y);
    }

    public long getId() {
        return id;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id && radius == particle.radius && Objects.equals(coordinates, particle.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinates, radius);
    }

    private static class Point{

        private long x;
        private long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
