package jeu;

import java.util.Scanner;
/*import javax.swing.*;
import java.awt.*;
*/
public class Main{

    public static void main(String[] args) {

        /*JFrame frame = new JFrame("Accueil");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel1 = new JPanel(new BorderLayout());
        
        JPanel toPanel = new JPanel();
        toPanel.setBackground(Color.DARK_GRAY);
        JLabel bienvenue = new JLabel("Bienvenue");
        bienvenue.setForeground(Color.WHITE);
        toPanel.add(bienvenue);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JButton play = new JButton("PLAY");
        centerPanel.add(play);

        ImageIcon icon = new ImageIcon("ressources/img/play.jpg");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        JLabel play_icon = new JLabel(icon);
        centerPanel.add(play_icon);

        panel1.add(toPanel, BorderLayout.NORTH);
        panel1.add(centerPanel, BorderLayout.CENTER);

        frame.add(panel1);
        frame.setVisible(true);*/
        TikTakToe jeu = new TikTakToe(); 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue au jeu du morpion");
        if (jeu.modeDeJeu(scanner)) {
            while(true) {
                jeu.takeTurn(scanner, jeu);
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

                jeu.computerTurn();
                if (jeu.victory()) {
                    jeu.printGrille();
                    System.out.println("L'ordinateur a gagné.");
                    scanner.close();
                    break;
                }

                if (jeu.equality()) {
                    jeu.printGrille();
                    System.out.println("Égalité.");
                    scanner.close();
                    break;
                }
            
            }
            
        }else {
            while (true){
                jeu.takeTurn(scanner, jeu);
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
}