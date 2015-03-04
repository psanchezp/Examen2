
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Alien
 *
 * Modela la definición de todos los objetos de tipo
 * <code>alien</code>, como hijo de sprite
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */
public class Alien extends Sprite {

    private Bomb bmbBomb; //Objeto bomba
    private final String strShot = "alien.png"; //Nombre de la imagen alien

    /**
     * Alien
     * 
     * constructor de la clase alien
     * 
     * @param iX es un <code>int</code> que es posicion en x
     * @param iY es un <code>int</code> que es posicion en y
     */
    public Alien(int iX, int iY) {
        this.iX = iX;
        this.iY = iY;

        bmbBomb = new Bomb(iX, iY);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(strShot));
        setImage(ii.getImage());

    }

    /**
     * act
     * 
     * Metodo que actualiza la posicion del alien
     * 
     * @param direction es un <code>int</code> la direccion del alien
     * 
     */
    public void act(int iDirection) {
        this.iX += iDirection;
    }

    /**
     * getBomb
     * 
     * Metodo que regresa un objeto bomba
     * 
     * @return<code>Bomb</code> que es un objeto bomba
     * 
     */
    public Bomb getBomb() {
        return bmbBomb;
    }
    
    public class Bomb extends Sprite {

        private final String bmbBomb = "bomb.png"; //Nombre de la imagen bomba
        private boolean bDestroyed; //la bomba esta destruida

        /**
        * Bomb
        * 
        * constructor de la clase Bomb
        * 
        * @param iX es un <code>int</code> que es posicion en x
        * @param iY es un <code>int</code> que es posicion en y
        */
        public Bomb(int iX, int iY) {
            setDestroyed(true);
            this.iX = iX;
            this.iY = iY;
            ImageIcon iI = new ImageIcon(this.getClass().getResource(bmbBomb));
            setImage(iI.getImage());
        }

        /**
        * setDestroyed
        * 
        * Metodo que modifica si la bomba se destruye
        * 
        * @param bDestroyed es un <code>bool</code> que es si esta destruido
        * 
        */
        public void setDestroyed(boolean bDestroyed) {
            this.bDestroyed = bDestroyed;
        }

       /**
        * isDestroyed
        * 
        * Metodo que regresa si la bomba esta destruida o no
        * 
        * @return <code>bool</code> bDestroyed, que es si esta destruido
        * 
        */
        public boolean isDestroyed() {
            return bDestroyed;
        }
    }
}
