import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Escacs {
    Scanner sc = new Scanner(System.in);

    String jugadorBlanques;
    String jugadorNegres;

    ArrayList<String> movimentsBlanques = new ArrayList<>();
    ArrayList<String> movimentsNegres = new ArrayList<>();

    ArrayList<String> pecesBlanquesEliminades = new ArrayList<>();
    ArrayList<String> pecesNegresEliminades = new ArrayList<>();
    
    public static void main(String[] args) {
        Escacs p = new Escacs();
        p.programa();
    }
    public void programa() {
        int fila = 8;
        int columna = 8;
        char [][] tauler = new char[fila][columna];

        String primerJugador;
        String segonJugador;
        int decisio = 0;
        boolean partida = true;
        int torn = 0;

        System.out.println("--------------Benvinguts al Joc d'Escacs!!!--------------");

        System.out.println("Quin es el nom de jugador 1?");
        primerJugador = sc.nextLine();
        System.out.println("Quin es  el nom de jugador 2?");
        segonJugador = sc.nextLine();


        quiComenca(primerJugador, segonJugador);
        assignacioPecesInici(tauler);
        mostrarTauler(tauler);


         while (partida) {

            do {
                System.out.println("Siusplau introdueix la teva decisio: (1) Continuar 2) Rendirse");
                decisio = llegirEnter();
            } while(decisio < 1 || decisio > 2);

            boolean abandonar = false;

            if (decisio == 2) {
                abandonar = true;
                partida = false;
                System.out.println("El jugador s'ha rendit.");
                System.out.println("Guanya " + (torn % 2 == 0 ? jugadorNegres : jugadorBlanques));
            }

            if (!abandonar) {

                boolean tornBlanques = (torn % 2 == 0);

                System.out.println("Torn de " + (tornBlanques
                        ? "les blanques: " + jugadorBlanques
                        : "les negres: " + jugadorNegres));

                sc.nextLine();
                System.out.print("Introdueix moviment (ex: e2 e4 o Abandonar): ");
                String entrada = sc.nextLine().trim();
                
                boolean movimentProcessat = false;

                if (entrada.equalsIgnoreCase("Abandonar")) {
                    movimentProcessat = true;
                    partida = false;
                    System.out.println("El jugador ha abandonat.");
                    System.out.println("Guanya " + (tornBlanques ? jugadorNegres : jugadorBlanques));
                }

                if (!movimentProcessat) {

                    if (!entrada.matches("^[a-h][1-8] [a-h][1-8]$")) {
                        System.out.println("FORMAT INCORRECTE");
                        movimentProcessat = true;
                    }

                    if (!movimentProcessat) {

                        String[] parts = entrada.split(" ");
                        int[] origen = convertirCoordenada(parts[0]);
                        int[] desti = convertirCoordenada(parts[1]);

                        boolean valid = movimentValid(tauler, origen, desti, tornBlanques);

                        if (!valid) {
                            System.out.println("MOVIMENT NO PERMÈS");
                            movimentProcessat = true;
                        }

                        if (!movimentProcessat) {

                            mourePeca(tauler, origen, desti, tornBlanques);

                            if (tornBlanques)
                                movimentsBlanques.add(entrada);
                            else
                                movimentsNegres.add(entrada);

                            mostrarTauler(tauler);
                            mostrarCaptures();

                            torn++;
                        }
                    }
                }
            }
        }

        resumFinal();
    }
    public void assignacioPecesInici(char[][] tauler) {
        for (int i = 0;i < 8;i++) {
            for(int j = 0;j < 8;j++){
                tauler [i][j] = '.';
            }
        }

        tauler [0][0] = 't';
        tauler [0][1] = 'c';
        tauler [0][2] = 'a';
        tauler [0][3] = 'q';
        tauler [0][4] = 'k';
        tauler [0][5] = 'a';
        tauler [0][6] = 'c';
        tauler [0][7] = 't';

        for (int i = 0; i < 8; i++) {
            tauler [1][i] = 'p';
        }

        tauler [7][0] = 'T';
        tauler [7][1] = 'C';
        tauler [7][2] = 'A';
        tauler [7][3] = 'Q';
        tauler [7][4] = 'K';
        tauler [7][5] = 'A';
        tauler [7][6] = 'C';
        tauler [7][7] = 'T';

        for (int i = 0; i < 8;i++) {
            tauler [6][i] = 'P';
        }

    }
    public void mostrarTauler(char[][] tauler) {
        System.out.println("       A      B      C      D      E      F      G      H");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " | ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" |_" +tauler[i][j] + "_| ");
            }
            System.out.println((8 - i));
        }
        System.out.println("       A      B      C      D      E      F      G      H");

    }
    public void quiComenca(String primerJugador, String segonJugador) {
        int resultat = (int)(Math.random() * 2);
        
        if (resultat == 0) {
            jugadorBlanques = primerJugador;
            jugadorNegres = segonJugador;
        }
        else {
            jugadorBlanques = segonJugador;
            jugadorNegres = primerJugador;
        }

        System.out.println(jugadorBlanques + " comença amb les peces blanques.");
        System.out.println(jugadorNegres + " comença amb les peces negres.");
    }
    public int llegirEnter(){
        int numero = 0;
        boolean correcta = false;

        while(!correcta)
        try {
            numero = sc.nextInt();
            correcta = true;
        }
        catch(InputMismatchException err){
            System.out.println("Has d'introduir un nombre enter");
            sc.nextLine();
        }
        return numero;
    }
    public int[] convertirCoordenades(String pos) {
        int col = pos.charAt(0) - 'a';
        int fila = 8 - Character.getNumericValue(pos.charAt(1));
        return new int[]{fila, col};
    }
    public boolean movimentValid(char[][] t, int[] o, int[] d, boolean blanques) {
        char p = t[o[0]][o[1]];
        char dest = t[d[0]][d[1]];

        if (p == '.') return false;
        if (blanques && Character.isLowerCase(p)) return false;
        if (!blanques && Character.isUpperCase(p)) return false;
        if (dest != '.' && Character.isUpperCase(dest) == Character.isUpperCase(p)) return false;

        switch (Character.toLowerCase(p)) {
            case 'p': return movimentPeo(t, o, d, blanques);
            case 't': return o[0] == d[0] || o[1] == d[1];
            case 'c':
                int f = Math.abs(o[0] - d[0]);
                int c = Math.abs(o[1] - d[1]);
                return (f == 2 && c == 1) || (f == 1 && c == 2);
            case 'a': return Math.abs(o[0] - d[0]) == Math.abs(o[1] - d[1]);
            case 'q': return o[0] == d[0] || o[1] == d[1] ||
                             Math.abs(o[0] - d[0]) == Math.abs(o[1] - d[1]);
            case 'k': return Math.abs(o[0] - d[0]) <= 1 && Math.abs(o[1] - d[1]) <= 1;
        }
        return false;
    }
    public void mourePeca(char[][] t, int[] o, int[] d, boolean blanques) {
        if (t[d[0]][d[1]] != '.') {
            if (blanques)
                pecesNegresEliminades.add("" + t[d[0]][d[1]]);
            else
                pecesBlanquesEliminades.add("" + t[d[0]][d[1]]);
        }
        t[d[0]][d[1]] = t[o[0]][o[1]];
        t[o[0]][o[1]] = '.';
    }
    public void mostrarCaptures() {

        System.out.println("Peces blanques eliminades: " + pecesBlanquesEliminades);
        System.out.println("Peces negres eliminades: " + pecesNegresEliminades);
    
    }
    public void resumFinal() {

        System.out.println("\n--- RESUM FINAL ---");
        System.out.println("Moviments blanques: " + movimentsBlanques);
        System.out.println("Moviments negres: " + movimentsNegres);
    
    }
    public boolean movimentPeo(char[][] t, int[] o, int[] d, boolean blanques) {

    }
}
