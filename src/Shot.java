
import java.awt.Image;
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
    private Animacion aniShot; //Animacion del disparo

    /**
     * Shot
     * 
     * constructor de la clase shot
     * @param aniShot es un <code>Animacion</code> que da la animacion
     */
    public Shot(Animacion aniShot) {
        this.aniShot = aniShot;
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
        ImageIcon ii = new ImageIcon(this.getClass().getResource(strShot));
        setImage(ii.getImage());
        setX(iX + iH_SPACE);
        setY(iY - iV_SPACE);
    }
    
    /**
     * Shot 3
     * 
     * otro constructor alterno de la clase shot
     * 
     * @param iX es un <code>int</code> que es posicion en x
     * @param iY es un <code>int</code> que es posicion en y
     * @param aniShot es un <code>Animacion</code> que da la animacion
     */
    public Shot(int iX, int iY, Animacion aniShot) {
        setX(iX + iH_SPACE);
        setY(iY - iV_SPACE);
        this.aniShot = aniShot;
    }
    
    /*public Image getAnimacion (){
        return aniShot.getImagen();
    }*/
}