import java.util.List;

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
                break;
            case "fBrutaRand":
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

        long initTime = System.nanoTime();
        CellIdxMethod method = new CellIdxMethod(board,rc,per);
        for(Particle p : board.getParticles()) {
            System.out.println("Vecinos de " + p.getId() + "\t" + method.getNeighboursOf(p));
        }
        long finishTime = System.nanoTime();
        String benchmark = String.format("Tiempo transcurrido: %g (ms)",(finishTime-initTime)/(Math.pow(10,6)));
        System.out.println(benchmark);
    }
}
