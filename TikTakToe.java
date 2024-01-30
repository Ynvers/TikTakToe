import java.util.Scanner;

public class TikTakToe {

    private char[][] grille;
    private char joueurActuel;
    private char win;

    public TikTakToe() {
        grille = new char[3][3];
        joueurActuel = 'X';
        initialiserGrille();
    }

    public void initialiserGrille(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                grille[i][j] = ' ';
            }
        }
    }

    public void printGrille(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(grille[i][j]);
                if (j < 2){
                    System.out.print(" | ");
                }
            }
            if (i < 2){
                System.out.println();
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public void takeTurn(Scanner scanner) {
        win = joueurActuel;
        System.out.println(joueurActuel + "'s turn.");
        int position;
        System.out.println("Entrrer la position: ");
        try{
            position = scanner.nextInt();
            int row = (position - 1) / 3;
            int col = (position - 1) % 3;
            if (grille[row][col] == ' ') {
                grille[row][col] = joueurActuel;
                joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Position occupée");
            }
        } catch (Exception e) {
            System.out.println("Entrée envaalide. Nouvelle tentative exigée");
        }

    }

    public boolean victory() {
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == grille[i][1] && grille[i][1] == grille[i][2] && grille[i][0] != ' ') {
                return true;
            } 
        }
        for (int j = 0; j < 3; j++) {
            if (grille[0][j] == grille[1][j] && grille[1][j] == grille[2][j] && grille[0][j] != ' ') {
                return true;
            }
        }
        if (grille[0][0] == grille[1][1] && grille[1][1] == grille[2][2] && grille[0][0] != ' ') {
            return true;
        }
        if (grille[2][0] == grille[1][1] && grille[1][1] == grille[0][2] && grille[2][0] != ' ') {
            return true;
        }
        return false;
         
    }

    public boolean equality() {
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        
        TikTakToe jeu = new TikTakToe(); 
        Scanner scanner = new Scanner(System.in);
        while (true){
            jeu.printGrille();
            jeu.takeTurn(scanner);
            if (jeu.victory()) {
                jeu.printGrille();
                System.out.println("Le joueur " + jeu.win + " a gagner" );
                scanner.close();
                break;
            }
            if (jeu.equality()) {
                jeu.printGrille();
                System.out.println("Equality");
                scanner.close();
                break;
            }
        }
        
    }
}
