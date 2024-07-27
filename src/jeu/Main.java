package jeu;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;


public class Main{

    public static void main(String[] args) {

        //Créer la fenêtre principal
        JFrame frame = new JFrame("Morpion");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Créer le panneau représentant le menu
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Création du menu de bienvenue et de sélection de mode
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Bienvenue au jeu du morpion, style Ynvers<br> votre mode de jeu</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder((null));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrer les boutons avec un espacement

        // Ajouter les boutons pour la sélection du mode de jeu
        JButton modeSolo = new JButton("Mode solo");
        JButton modeDuo = new JButton("Mode duo");

        buttonPanel.add(modeSolo);
        buttonPanel.add(modeDuo);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);        

        //Ajout des éléments de base
        frame.add(mainPanel);
        frame.setVisible(true);
        /* 
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
        */
        TikTakToe jeu = new TikTakToe(); 
        Scanner scanner = new Scanner(System.in);
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
        }
    }
}