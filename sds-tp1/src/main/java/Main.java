import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.*;

public final class Main {

    private final static String outputName = "output";

    public static void main(String[] args) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("sds-tp1/src/main/resources/config.yaml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        if(data.isEmpty()) {
            throw new IllegalArgumentException("No se han detectado argumentos. Ingrese 'ayuda' para más información.");
        }

        int m = (int) data.get("M");
        if(m == 0) {
            m = optM();
        }

        Board board = null;
        if((boolean)data.get("randomize")) {
            int n = (int) data.get("totalParticles");
            double l = (double) data.get("boardLength");
            board = Board.getRandomBoard(n,l,m);
        } else {
            board = inputBoard((String)data.get("staticFile"), (String)data.get("dynamicFile"),m);
        }

        double rc = (double) data.get("radius");
        boolean per = (boolean) data.get("periodicOutline");
        String out = (String) data.get("fileName");
        if(out.isEmpty()) {
            out = outputName;
        }

        switch ((String) data.get("method")) {
            case "cellIdx":
                cellIdxMethod(board, rc, per, out);
                break;

            case "fBruta":
                System.out.println("Implementar fBruta");
                break;

            default:
                throw new IllegalArgumentException("Argumento 'method' inválido.");
        }
    }

    private static int optM() {
        int m = 0;
        // TODO: calcular M óptimo
        return m;
    }

    private static Board inputBoard(String staticFile, String dynamicFile, int m) {
        Scanner st=null, din=null;
        try {
            st = new Scanner(new File(staticFile));
            din = new Scanner(new File(dynamicFile));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(st!=null && din!=null) {
            int n = 0;
            long l = 0;
            if (st.hasNextInt()) {
                n = st.nextInt();
                if (st.hasNextLong()) {
                    l = st.nextLong();
                }
            }
            if (din.hasNext("t.")) {     // t0
                din.next();
            }
            List<Particle> particles = new ArrayList<>();
            for (int i = 0; i < n && st.hasNextLine() && din.hasNextLine(); i++) {
                System.out.println("Particle " + i);
                double x = 0, y = 0, r = 0;
                if (din.hasNextLine()) {;
                    x = din.nextDouble();
                    System.out.println("X "+x);
                    y = din.nextDouble();
                    System.out.println("Y "+y);
                    din.nextLine(); // el resto de los datos no los usamos por ahora
                }
                if (st.hasNextLine()) {
                    r = st.nextDouble();
                    System.out.println("R " + r);
                }
                particles.add(new Particle(i, x, y, r));
            }
            return new Board(l, m, particles);
        }
        return null;
    }

    private static void cellIdxMethod(Board board, double rc, boolean per, String out) {
        Map<Particle, List<Particle>> neighbours = new HashMap<>();
        long initTime = System.nanoTime();
        CellIdxMethod method = new CellIdxMethod(board,rc,per);
        for(Particle p : board.getParticles()) {
            neighbours.put(p, method.getNeighboursOf(p));
        }
        long finishTime = System.nanoTime();
        String benchmark = String.format("Tiempo transcurrido: %g (ms)",(finishTime-initTime)/(Math.pow(10,6)));
        System.out.println(benchmark);

        output(neighbours, out);
        visual(neighbours);
    }

    private static void output(Map<Particle, List<Particle>> neighbours, String fileName) {
        try {
            FileWriter out = new FileWriter(fileName + ".txt",false);
            BufferedWriter buffer = new BufferedWriter(out);
            for(Particle current : neighbours.keySet()) {
                buffer.write("[ " + current.getId() + "\t");
                for(Particle p : neighbours.get(current)) {
                    if(!p.equals(current)) {
                        buffer.write(p.getId() + " ");
                    }
                }
                buffer.write("]");
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
            out.close();
            System.out.println("Resultados en " + fileName + ".txt");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
    }

    private static Particle getParticle(long id, Set<Particle> particles) {
        for(Particle p : particles) {
            if(id == p.getId()) {
                return p;
            }
        }
        throw new IllegalArgumentException("Partícula no encontrada.");
    }

    private static void visual(Map<Particle, List<Particle>> neighbours) {
        System.out.println("\nGenere visualización de vecinos indicando las IDs de las partículas a evaluar separadas por espacios.\n" +
                            "Puede indicar al final un nombre para el archivo de salida. Para terminar, ingrese ; y presione enter");
        Scanner sc = new Scanner(System.in);
        List<Long> ids = new ArrayList<>();
        while (sc.hasNextInt()) {
            ids.add(sc.nextLong());
        }
        String fileName = "visualization";
        if (sc.hasNext()) {
            String next = sc.next();
            if(!next.equals(";")) {
                fileName = next;
            }
        }
        sc.close();
        System.out.println("Procesando " + ids);
        try {
            FileWriter vis = new FileWriter(fileName + ".xyz",false);
            BufferedWriter buffer = new BufferedWriter(vis);

            buffer.write(String.valueOf(neighbours.keySet().size()));
            buffer.newLine();
            buffer.newLine();

            Set<Particle> particles = neighbours.keySet();

            for(long id : ids) {
                try {
                    Particle current = getParticle(id,particles);
                    buffer.write(current.getId() + " " + current.getX() + " " + current.getY() + " " + current.getRadius() + " 255 0 0");
                    buffer.newLine();
                    for(Particle p : neighbours.get(current)) {
                        if(p.getId() != id) {
                            buffer.write(p.getId() + " " + p.getX() + " " + p.getY() + " " + p.getRadius() + " 0 255 0");
                            buffer.newLine();
                            particles.remove(p);
                        }
                    }
                    particles.remove(current);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            for(Particle p : particles) {
                buffer.write(p.getId() + " " + p.getX() + " " + p.getY() + " " + p.getRadius() + " 255 255 255");
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
            vis.close();
            System.out.println("Resultados en " + fileName + ".xyz");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
    }
}
