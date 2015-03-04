
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Player
 *
 * Modela la definición de todos los objetos de tipo
 * <code>player</code>, como hijo de sprite con los variables de Commons
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */
public class Player extends Sprite implements Commons{

    private final int iSTART_Y = 340; //Posicion inicial x
    private final int iSTART_X = 270; //Posicion inicial y

    private final String player = "player.png"; //Nombre de imagen
    private int width; //Ancho de player

   /**
    * Player
    * 
    * constructor de la clase player
    *
    */
    public Player() {

        ImageIcon iI = new ImageIcon(this.getClass().getResource(player));

        width = iI.getImage().getWidth(null); 

        setImage(iI.getImage());
        setX(iSTART_X);
        setY(iSTART_Y);
    }

    /**
     * act
     * 
     * Metodo que checa colision del jugador con las paredes
     * 
     */
    public void act() {
        iX += iDx;
        //Checa si se sale del board
        if (iX <= 2) {
            iX = 2;
        }
        if (iX + width >= BOARD_WIDTH) { 
            iX = BOARD_WIDTH - width;
        }
    }

    /**
     * keyPressed
     * 
     * Metodo que checa que tecla fue oprimida
     * 
     * @param e es un <code>KeyEvent</code> que es la interfaz de teclado
     * 
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //La direccion es -2 si es a la izq
        if (key == KeyEvent.VK_LEFT)
        {
            iDx = -2;
        }

        //La direccion es 2 si es a la derecha
        if (key == KeyEvent.VK_RIGHT)
        {
            iDx = 2;
        }
    }

    /**
     * keyReleased
     * 
     * Metodo que checa que tecla fue soltada
     * 
     * @param e es un <code>KeyEvent</code> que es la interfaz de teclado
     * 
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            iDx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            iDx = 0;
        }
    }
}
