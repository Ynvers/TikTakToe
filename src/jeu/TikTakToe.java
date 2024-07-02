package jeu;

import java.util.Scanner;

public class TikTakToe {

    private char[][] board;
    private char currentPlayer;
    protected char winner;

    public TikTakToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    public boolean gameMode(Scanner scanner) {
        System.out.println("Choisissez (s) pour affronter l'ordinateur et (d) pour jouer contre un ami");
        char mode;
        try {
            mode = scanner.next().charAt(0);
            if (!Character.isLetter((mode))) {
                throw new IllegalArgumentException("Vous devez entrer un caractère");
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
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
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

    public void takeTurn(Scanner scanner, TikTakToe game) {
        winner = currentPlayer;
        System.out.println(currentPlayer + "'s turn.");
        game.printBoard();
        int position;
        System.out.println("Entrer la position: ");
        try {
            position = scanner.nextInt();
            int row = (position - 1) / 3;
            int col = (position - 1) % 3;
            if (board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Position occupée");
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide. Nouvelle tentative exigée");
        }
    }

    public void computerTurn() {
        int depth = countEmptyCells(board);
        int[] bestMove = minmax(board, currentPlayer, depth);
        int row = bestMove[1];
        int col = bestMove[2];
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private int[] minmax(char[][] grille, char joueurActuel, int profondeur) {
        if (victory() || equality() || profondeur == 0) {
            return new int[]{evaluate(grille)};
        }        
        if (joueurActuel == 'O') {
            int maxEval = Integer.MIN_VALUE;
            int[] bestMove = new int[2];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = joueurActuel;
                        int eval = minmax(grille, 'X', profondeur - 1)[0];
                        grille[i][j] = ' ';
                        if (eval > maxEval) {
                            maxEval = eval;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
            return new int[] {maxEval, bestMove[0], bestMove[1]};
        } else {
            int minEval = Integer.MAX_VALUE;
            int[] bestMove = new int[2];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = joueurActuel;
                        int eval = minmax(grille, 'O', profondeur - 1)[0];
                        grille[i][j] = ' '; // Annuler le coup
    
                        if (eval < minEval) {
                            minEval = eval;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
            return new int[] {minEval, bestMove[0], bestMove[1]};
        }
    } 
    
    public boolean victory() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != ' ') {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return true;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != ' ') {
            return true;
        }
        return false;
    }
    
    public boolean equality() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int evaluate(char[][] grille) {
        int score = 0;
        score += evaluateLine(grille, 'O'); // Joueur 'O'
        score -= evaluateLine(grille, 'X'); // Joueur 'X'
    
        return score;
    }
    
    private int evaluateLine(char[][] grille, char player) {
        int score = 0;
    
        for (int i = 0; i < 3; i++) {
            int countPlayer = 0; // Compter les jetons du joueur dans la ligne
            int countOpponent = 0; // Compter les jetons de l'adversaire dans la ligne
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == player) {
                    countPlayer++;
                } else if (grille[i][j] != ' ') {
                    countOpponent++;
                }
            }
            score += calculateScore(countPlayer, countOpponent);
        }
    
        // Évaluer les colonnes
        for (int j = 0; j < 3; j++) {
            int countPlayer = 0; // Compter les jetons du joueur dans la colonne
            int countOpponent = 0; // Compter les jetons de l'adversaire dans la colonne
            for (int i = 0; i < 3; i++) {
                if (grille[i][j] == player) {
                    countPlayer++;
                } else if (grille[i][j] != ' ') {
                    countOpponent++;
                }
            }
            score += calculateScore(countPlayer, countOpponent);
        }
    
        // Évaluer les diagonales
        int countPlayerDiagonal1 = 0; // Compter les jetons du joueur dans la première diagonale
        int countOpponentDiagonal1 = 0; // Compter les jetons de l'adversaire dans la première diagonale
        int countPlayerDiagonal2 = 0; // Compter les jetons du joueur dans la deuxième diagonale
        int countOpponentDiagonal2 = 0; // Compter les jetons de l'adversaire dans la deuxième diagonale
        for (int i = 0; i < 3; i++) {
            if (grille[i][i] == player) {
                countPlayerDiagonal1++;
            } else if (grille[i][i] != ' ') {
                countOpponentDiagonal1++;
            }
            if (grille[i][2 - i] == player) {
                countPlayerDiagonal2++;
            } else if (grille[i][2 - i] != ' ') {
                countOpponentDiagonal2++;
            }
        }
        score += calculateScore(countPlayerDiagonal1, countOpponentDiagonal1);
        score += calculateScore(countPlayerDiagonal2, countOpponentDiagonal2);
    
        return score;
    }
    
    private int calculateScore(int countPlayer, int countOpponent) {
        if (countPlayer == 3) {
            return 1000; // Trois jetons alignés du joueur
        } else if (countOpponent == 3) {
            return -1000; // Trois jetons alignés de l'adversaire (blocage)
        } else if (countPlayer == 2 && countOpponent == 0) {
            return 100; // Deux jetons alignés du joueur
        } else if (countPlayer == 0 && countOpponent == 2) {
            return -100; // Deux jetons alignés de l'adversaire (risque de perte)
        } else if (countPlayer == 1 && countOpponent == 0) {
            return 10; // Un jeton du joueur
        } else if (countPlayer == 0 && countOpponent == 1) {
            return -10; // Un jeton de l'adversaire (risque)
        } else {
            return 0; // Aucune configuration significative
        }
    }
    
    private int countEmptyCells(char[][] grille) {
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
