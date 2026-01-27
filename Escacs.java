import java.util.ArrayList;
import java.util.Scanner;

public class Escacs {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Escacs p = new Escacs();
        p.programa();
    }
    public void programa() {
        int fila = 8;
        int columna = 8;
        char [][] tauler = new char[fila][columna];

        ArrayList<String> movimentsBlanques = new ArrayList<>();
        ArrayList<String> movimentsNegres = new ArrayList<>();

        ArrayList<String> pecesBlanquesEliminades = new ArrayList<>();
        ArrayList<String> pecesNegresEliminades = new ArrayList<>();

        String primerJugador;
        String segonJugador;
        boolean guanyador = false;
        boolean partida = true;

        System.out.println("--------------Benvinguts al Joc d'Escacs!!!--------------");

        System.out.println("Quin es el nom de jugador 1?");
        primerJugador = sc.nextLine();
        System.out.println("Quin es  el nom de jugador 2?");
        segonJugador = sc.nextLine();


        do {
        switch (segonJugador) {
            case "1":
                
                break;
        
            default:
                break;
        }
        assignacioPecesInici(tauler);
        mostrarTauler(tauler);

        } while(partida);
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
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " | ");
            for (int j = 0; j < 8; j++) {
                System.out.print(tauler[i][j] + " | ");
            }
            System.out.println((8 - i));
        }
        System.out.println("  A B C D E F G H");

    }
    
}
