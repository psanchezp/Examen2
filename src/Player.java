
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Player extends Sprite implements Commons{

    private final int START_Y = 340; 
    private final int START_X = 270;

    private final String player = "player.png";
    private int width;

    public Player() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null); 

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }

    public void act() {
        x += dx;
        //Checa si se sale del board
        if (x <= 2) {
            x = 2;
        }
        if (x + width >= BOARD_WIDTH) { 
            x = BOARD_WIDTH - width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //La direccion es -2 si es a la izq
        if (key == KeyEvent.VK_LEFT)
        {
            dx = -2;
        }

        //La direccion es 2 si es a la derecha
        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
        }
    }
}
