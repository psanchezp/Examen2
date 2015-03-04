/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;

/**
 * Commons
 *
 * constantes del juego
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */
public class SpaceInvaders extends JFrame implements Commons {

    /**
     * SpaceInvaders
     * 
     * constructor del juego
     * 
     */
    public SpaceInvaders()
    {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Main
     * 
     * Llama al constructor del juego
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SpaceInvaders();
    }
}