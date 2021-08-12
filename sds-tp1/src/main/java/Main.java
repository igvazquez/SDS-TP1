import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public final class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            throw new IllegalArgumentException("No se han detectado argumentos. Ingrese 'ayuda' para más información.");
        }
        switch (args[0]) {
            case "ayuda":
                System.out.println("NOTA: Los parámetros marcados con * son opcionales.\n" +
                                    "\tcellIdx <STATIC DATA FILE> <DINAMIC DATA FILE> <Rc> <per | nper> <M*>\n" +
                                    "\tcellIdxRand <N> <L> <Rc> <per | nper> <M*>\n" +
                                    "\tfBruta <STATIC DATA FILE> <DINAMIC DATA FILE> <Rc> <per | nper>\n" +
                                    "\tfBrutaRand <N> <L> <Rc> <per | nper>\n");
                break;
            case "cellIdx":
                break;
            case "cellIdxRand":
                cellIdxMethod(args);
                break;
            case "fBruta":
                System.out.println("Implementar fBruta");
                break;
            case "fBrutaRand":
                System.out.println("Implementar fBrutaRand");
                break;
            default:
                throw new IllegalArgumentException("Argumento inválido. Ingrese 'ayuda' para más información.");
        }
    }

    // cellIdxRand <N> <L> <Rc> <per | nper> <M*>
    private static void cellIdxMethod(String[] args) {
        if(args.length < 5 || args.length > 6) {  // M es opcional
            throw new IllegalArgumentException("Cantidad de argumentos inválida. Ingrese 'ayuda' para más información.");
        }
        int m = Integer.parseInt(args[5]);
//        int m;
//        if(args.length == 5) {
            // TODO: calcular M óptimo
//        } else {
//            m = Integer.parseInt(args[5]);
//        }
        int n = Integer.parseInt(args[1]);
        double l = Double.parseDouble(args[2]);
        Board board = Board.getRandomBoard(n,l,m);

        double rc = Double.parseDouble(args[3]);
        boolean per = args[4].equals("per");
        Map<Particle, List<Particle>> neighbours = new HashMap<>();

        long initTime = System.nanoTime();
        CellIdxMethod method = new CellIdxMethod(board,rc,per);
        for(Particle p : board.getParticles()) {
            neighbours.put(p, method.getNeighboursOf(p));
//            System.out.println("Vecinos de " + p.getId() + "\t" + neighbours.get(p));
        }
        long finishTime = System.nanoTime();
        String benchmark = String.format("Tiempo transcurrido: %g (ms)",(finishTime-initTime)/(Math.pow(10,6)));
        System.out.println(benchmark);

        output(neighbours);
        visual(neighbours);
    }

    private static void output(Map<Particle, List<Particle>> neighbours) {
        try {
            // TODO: Permitir nombre de archivo como parámetro
            FileWriter out = new FileWriter("output.txt",false);
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
            System.out.println("Resultados en output.txt");
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
                            "Para terminar, ingrese 'f' y presione enter.");
        Scanner sc = new Scanner(System.in);
        List<Long> ids = new ArrayList<>();
        while (sc.hasNextInt()) {
            ids.add(sc.nextLong());
        }
        sc.close();
        System.out.println("Procesando " + ids);
        try {
            FileWriter vis = new FileWriter("visualization.xyz",false);
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
            System.out.println("Resultados en visualization.xyz");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
    }
}
