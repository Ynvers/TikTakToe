package jeu;

public class Exeptions {

    public static class CaractereExeption extends Exception {
        public CaractereExeption(String message) {
            super(message);
        }
    }
    
}

/*Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue au jeu du morpion");
        if (jeu.gameMode(scanner)) {
            while(true) {
                jeu.takeTurn(scanner, jeu);
                if (jeu.victory()) {
                    jeu.printBoard();
                    System.out.println("Le joueur " + jeu.winner + " a gagner" );
                    scanner.close();
                    break;
                }
                if (jeu.equality()) {
                    jeu.printBoard();
                    System.out.println("Equality");
                    scanner.close();
                    break;
                }

                jeu.computerTurn();
                if (jeu.victory()) {
                    jeu.printBoard();
                    System.out.println("L'ordinateur a gagné.");
                    scanner.close();
                    break;
                }

                if (jeu.equality()) {
                    jeu.printBoard();
                    System.out.println("Égalité.");
                    scanner.close();
                    break;
                }
            
            }
            
        }else {
            while (true){
                jeu.takeTurn(scanner, jeu);
                if (jeu.victory()) {
                    jeu.printBoard();
                    System.out.println("Le joueur " + jeu.winner + " a gagner" );
                    scanner.close();
                    break;
                }
                if (jeu.equality()) {
                    jeu.printBoard();
                    System.out.println("Equality");
                    scanner.close();
                    break;
                }
            }
        }*/