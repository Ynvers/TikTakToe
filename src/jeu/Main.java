package jeu;

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


        //Créer l'instance du jeu TiktakToe:
        TikTakToe jeu = new TikTakToe(); 

        //Panneau du jeu en mode solo
        JPanel soloPanel = createGamePanel(mainPanel, frame, "Morpion - Mode Solo", jeu, true);
        mainPanel.add(soloPanel, "Solo");
        
        //Panneau du jeu en duo
        JPanel duoPanel = createGamePanel(mainPanel, frame, "Morpion - Mode Duo", jeu, false);
        mainPanel.add(duoPanel, "Duo");

        //Implémentation des actions pour changer de panneau
        modeSolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Solo");
                frame.setTitle("Tu es bon");
                jeu.initializeBoard();
                jeu.gameMode(true);
                resetButtons(soloPanel);
            }            
        });

        modeDuo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Duo");
                frame.setTitle("Faux type");
                jeu.initializeBoard();
                jeu.gameMode(false);
                resetButtons(duoPanel);
            }      
        });

        frame.add(mainPanel);
        frame.setVisible(true);
        
    }

    private static JPanel createGamePanel(JPanel mainPanel, JFrame frame, String mode, TikTakToe jeu, boolean isSinglePlayer) {
        JPanel gamePanel = new JPanel(new BorderLayout());
        
        JPanel gridPanel = new JPanel( new GridLayout(3, 3));

        //Créer les boutons pour la grille
        JButton[] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            int index = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = index / 3;
                    int col = index % 3;
                    if (buttons[index].getText().equals("") && !jeu.victory() && !jeu.equality()) {
                        buttons[index].setText(Character.toString(jeu.getCurrentPlayer()));
                        jeu.makeMove(row, col);
                        if (jeu.victory() || jeu.equality()) {
                            JOptionPane.showMessageDialog(frame, jeu.victory() ? "Le joueur " + jeu.getCurrentPlayer() + " a gagné !" : "Match nul !");
                        } else if (isSinglePlayer) {
                            jeu.computerTurn();
                            updateButtons(buttons, jeu);
                            if (jeu.victory() || jeu.equality()) {
                                JOptionPane.showMessageDialog(frame, jeu.victory() ? "L'ordinateur a gagné !" : "Match nul !");
                            }
                        }
                    }
                }
            });
            gridPanel.add(buttons[i]);
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
                jeu.initializeBoard();
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

        gamePanel.putClientProperty("gameInstance", jeu);
        gamePanel.putClientProperty("buttonArray", buttons);


        return gamePanel;
    }
    
    private static void updateButtons(JButton[] buttons, TikTakToe jeu) {
        for (int i= 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            char value= jeu.getBoard()[row][col];
            buttons[i].setText(value == ' ' ? "" : Character.toString(value));
        }
    }

    private static void resetButtons(JPanel panel){
        for (Component component : panel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setText(" ");
            }
        }

        TikTakToe jeu = (TikTakToe) panel.getClientProperty("gameInstance");
        if (jeu != null) {
            jeu.initializeBoard();
            updateButtons((JButton[]) panel.getClientProperty("buttonArray"), jeu);
        }
    }
}