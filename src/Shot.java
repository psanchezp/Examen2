
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Shot
 *
 * Modela la definición de todos los objetos de tipo
 * <code>shot</code>, como hijo de sprite
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */

public class Shot extends Sprite {

    private String strShot = "shot.png"; //nombre de la imagen
    private final int iH_SPACE = 6; //Espacio en horizontal respecto al mono
    private final int iV_SPACE = 1; //Espacio vertical respecto al mono
    private Animacion aniShot;

    /**
     * Shot
     * 
     * constructor de la clase shot
     * 
     */
    public Shot() {
    }

    /**
     * Shot 2
     * 
     * constructor alterno de la clase shot
     * 
     * @param iX es un <code>int</code> que es posicion en x
     * @param iY es un <code>int</code> que es posicion en y
     */
    public Shot(int iX, int iY) {
        /*ImageIcon ii = new ImageIcon(this.getClass().getResource(strShot));
        setImage(ii.getImage());*/
        aniShot = new Animacion ();
        Image imaShot1 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("shot.png"));
        Image imaShot2 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("shot2.png"));
        
        aniShot.sumaCuadro (imaShot1, 50);
        aniShot.sumaCuadro (imaShot2, 50);
        setAnimacion(aniShot);
        
        setX(iX + iH_SPACE);
        setY(iY - iV_SPACE);
    }
}