import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Visual {

    Map<Particle, List<Particle>> neighbours;

    public Visual(Map<Particle, List<Particle>> neighbours) {
        this.neighbours = neighbours;
    }

    private static Particle getParticle(long id, Set<Particle> particles) {
        for(Particle p : particles) {
            if(id == p.getId()) {
                return p;
            }
        }
        throw new IllegalArgumentException("Partícula no encontrada.");
    }

    public void visual(List<Long> ids) {
        String fileName = "visualization";
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
                        if(p.getId() != id && particles.contains(p)) {
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

    public void visual() {
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
                        if(p.getId() != id && particles.contains(p)) {
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
