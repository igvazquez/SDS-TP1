import java.util.Objects;

public class Particle {

    private final long id;
    private final State state;
    private final double radius;

    public Particle(long id, double x, double y, double radius) {
        this.id = id;
        this.radius = radius;
        this.state = new State(x, y);
    }

    public Particle(long id, double x, double y, double radius, double vx, double vy) {
        this.id = id;
        this.radius = radius;
        this.state = new State(x, y, vx, vy);
    }

    public double calculateDistance(Particle p) {
        return Math.sqrt(Math.pow((getX() - p.getX()), 2) + Math.pow((getY() - p.getY()), 2))
                - radius - p.getRadius();
    }

    public long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public double getX(){
        return state.x;
    }

    public double getY(){
        return state.y;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id && radius == particle.radius && Objects.equals(state, particle.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, radius);
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", x=" + state.x +
                ", y=" + state.y +
                ", radius=" + radius +
                '}';
    }

    private static class State{

        private double x;
        private double y;
        private double vx;
        private double vy;

        public State(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public State(double x, double y, double vx, double vy) {
            this(x,y);
            this.vx = vx;
            this.vy = vy;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getVX() {
            return vx;
        }

        public double getVY() {
            return vy;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setVX(double vx) {
            this.vx = vx;
        }

        public void setVY(double vy) {
            this.vy = vy;
        }
    }
}
