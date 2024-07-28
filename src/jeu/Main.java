package jeu;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main{

    public static void main(String[] args) {

        //Créer la fenêtre principal
        JFrame frame = new JFrame("Morpion");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Créer le panneau principal du jeu avec Cardlayout
        JPanel mainPanel = new JPanel(new CardLayout());

        //Création du panneau de Bienvenue
        JPanel welcomPanel = new JPanel(new BorderLayout());

        //Création du menu de bienvenue et de sélection de mode
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Bienvenue au jeu du morpion, style Ynvers<br> votre mode de jeu</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder((null));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        welcomPanel.add(titleLabel,BorderLayout.NORTH);
        
        //Création du menu des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrer les boutons avec un espacement

        JButton modeSolo = new JButton("Mode solo");
        JButton modeDuo = new JButton("Mode duo");

        buttonPanel.add(modeSolo);
        buttonPanel.add(modeDuo);
        welcomPanel.add(buttonPanel, BorderLayout.SOUTH);        


        //Ajout du panneau de bienveunue au panneau principal
        mainPanel.add(welcomPanel, "Welcome");

        //Panneau du jeu en mode solo
        JPanel soloPanel = createGamePanel(mainPanel, frame, "Morpion - Mode Solo");
        mainPanel.add(soloPanel, "Solo");
        
        //Panneau du jeu en duo
        JPanel duoPanel = createGamePanel(mainPanel, frame, "Morpion - Mode Duo");
        mainPanel.add(duoPanel, "Duo");

        //Implémentation des actions pour changer de panneau
        modeSolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Solo");
                frame.setTitle("Tu es bon");
            }            
        });

        modeDuo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Duo");
                frame.setTitle("Faux type");
            }      
        });

        frame.add(mainPanel);
        frame.setVisible(true);
        
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

    private static JPanel createGamePanel(JPanel mainPanel, JFrame frame, String mode) {
        JPanel gamePanel = new JPanel(new BorderLayout());
        
        JPanel gridPanel = new JPanel( new GridLayout(3, 3));

        //Créer les boutons pour la grille
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.PLAIN, 60));
            gridPanel.add(button);
        }

        gamePanel.add(gridPanel, BorderLayout.CENTER);

        // Panneau des boutons Replay et Retour
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton replayButton = new JButton("Replay");
        JButton backButton = new JButton("Retour");

        controlPanel.add(replayButton);
        controlPanel.add(backButton);
        gamePanel.add(controlPanel, BorderLayout.SOUTH);

        // Action pour le bouton Replay
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component component : gridPanel.getComponents()) {
                    if (component instanceof JButton) {
                        ((JButton) component).setText("");
                    }
                }
            }
        });

        // Action pour le bouton Retour
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Welcome");
                frame.setTitle("Morpion");
            }
        });

        return gamePanel;
    }
    
}