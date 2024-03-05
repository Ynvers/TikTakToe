package jeu;

import java.util.Scanner;
import jeu.Exeptions.CaractereExeption;

public class TikTakToe {

    private char[][] grille;
    private char joueurActuel;
    protected char win;

    public TikTakToe() {
        grille = new char[3][3];
        joueurActuel = 'X';
        initialiserGrille();
    }

    public boolean modeDeJeu(Scanner scanner) {
        System.out.println("Choisissez (s) pour affronter l'ordinateur et (d) pour jouer contre un ami");
        char mode;
        try {
            mode = scanner.next().charAt(0);
            if (!Character.isLetter((mode))) {
                throw new CaractereExeption("Vous devez entrer un caractère");
            }
            while (mode != 's' && mode != 'd') {
                System.out.println("Tu n'as compris les choix possibles Kabi, soit (s), soit (d)");
                System.out.println(("Reprends"));
                mode = scanner.next().charAt(0);
            }
            if (mode == 's') {
                System.out.println("Tu es bon");
                return true;
            } else if (mode == 'd') {
                System.out.println("Au lieu que vous aller affronter l'algo un a un\nPFFFFFFFFFFF........... ");
                return false;
            }
        } catch (CaractereExeption e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public void initialiserGrille() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = ' ';
            }
        }
    }

    public void printGrille() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(grille[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            if (i < 2) {
                System.out.println();
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public void takeTurn(Scanner scanner, TikTakToe jeu) {
        win = joueurActuel;
        System.out.println(joueurActuel + "'s turn.");
        jeu.printGrille();
        int position;
        System.out.println("Entrer la position: ");
        try {
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
            System.out.println("Entrée invalide. Nouvelle tentative exigée");
        }
    }

    public void computerTurn() {
        int profondeur = countEmptyCells();
        int[] bestMove = minmax(grille, joueurActuel, profondeur);
        int row = bestMove[0];
        int col = bestMove[1];
        grille[row][col] = joueurActuel;
        joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
    }

    private int[] minmax(char[][] grille, char joueurActuel, int profondeur) {
        if (victory() || equality() || profondeur == 0) {
            return new int[]{evaluate(grille)};
        }

        int[] bestMove = new int[3];
        if (joueurActuel == 'O') {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = joueurActuel;
                        int score = minmax(grille, 'X', profondeur - 1)[0];
                        grille[i][j] = ' '; // Annuler le coup
    
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = score;
                        }
                    }
                }
            }
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = joueurActuel;
                        int score = minmax(grille, 'O', profondeur - 1)[0];
                        grille[i][j] = ' '; // Annuler le coup
    
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = score;
                        }
                    }
                }
            }
        }
        return bestMove;
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public int evaluate(char[][] grille) {
        if (victory() && joueurActuel == 'O') {
            return 10;
        } else if (victory() && joueurActuel == 'X') {
            return -10;
        } else if (equality()) {
            return 0;
        } else {
            int score = 0;
            // Parcourir les lignes, colonnes et diagonales pour évaluer la position
            for (int i = 0; i < 3; i++) {
                // Évaluer les lignes
                score += evaluerConfiguration(grille[i][0], grille[i][1], grille[i][2]);
                // Évaluer les colonnes
                score += evaluerConfiguration(grille[0][i], grille[1][i], grille[2][i]);
            }
            // Évaluer les diagonales
            score += evaluerConfiguration(grille[0][0], grille[1][1], grille[2][2]);
            score += evaluerConfiguration(grille[0][2], grille[1][1], grille[2][0]);

            return score;
        }
    }

    private int evaluerConfiguration(char c1, char c2, char c3) {
        int score = 0;
        if (c1 == 'O') {
            score = 1;
        }
        if (c2 == 'O') {
            score = 10;
        }
        if (c3 == 'O') {
            score = 100;
        }
        // Poids pour 'X'
        if (c1 == 'X') {
            score = -1;
        }
        if (c2 == 'X') {
            score = -10;
        }
        if (c3 == 'X') {
            score = -100;
        }
        return score;
    }

    private int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    count++;
                }
            }
        }
        return count;
    }
}
