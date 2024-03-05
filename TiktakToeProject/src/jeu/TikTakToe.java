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
        int[] bestMove = minimax(grille, joueurActuel);
        int row = bestMove[0];
        int col = bestMove[1];
        grille[row][col] = joueurActuel;
        joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
    }

    private int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (player == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, player, player == 'X');
                    board[i][j] = ' ';

                    if ((player == 'X' && score > bestScore) || (player == 'O' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimaxHelper(char[][] board, char player, boolean isMaximizing) {
        if (victory()) {
            return (player == 'X') ? -1 : 1;
        } else if (equality()) {
            return 0;
        }

        char opponent = (player == 'X') ? 'O' : 'X';
        int bestScore = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (isMaximizing) ? player : opponent;
                    int score = minimaxHelper(board, player, !isMaximizing);
                    board[i][j] = ' ';

                    if (isMaximizing) {
                        bestScore = Math.max(bestScore, score);
                    } else {
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }

        return bestScore;
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
}
