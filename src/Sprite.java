
import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Sprite
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Sprite</code>
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */

public class Sprite {

    private boolean bVisible; //si el sprite esta bVisible (muerto)
    private Image ImaImagen; //imagen del sprite
    protected int iX; //posicion en x
    protected int iY; //posicion en iY
    protected boolean bDying; //si el sprite esta muriendo
    protected int iDx; //diferencial de movimiento en x

    
    /**
     * Sprite
     * 
     * constructor de la clase sprite
     * 
     */
    public Sprite() {
        bVisible = true;
    }

    /**
     * die
     * 
     * Metodo que modifica si el sprite esta bVisible al momento de morir
     * 
     */
    public void die() {
        bVisible = false;
    }
    
    /**
     * isVisible
     * 
     * Metodo que regresa si el sprite esta bVisible
     * 
     * @return <code>bool</code> que es si esta bVisible o no
     * 
     */
    public boolean isVisible() {
        return bVisible;
    }

    /**
     * setVisible
     * 
     * Metodo que modifica si el sprite esta visible
     * 
     * @param bVisible es un <code>bool</code> que es si esta visible o no
     * 
     */
    protected void setVisible(boolean bVisible) {
        this.bVisible = bVisible;
    }

    /**
     * setImage
     * 
     * Metodo que modifica la imagen del sprite
     * 
     * @param ImaImagen es un <code>Image</code> que es la imagen
     * 
     */
    public void setImage(Image ImaImagen) {
        this.ImaImagen = ImaImagen;
    }

    /**
     * getImage
     * 
     * Metodo que regresa la imagen del sprite
     * 
     * @return <code>Image</code> que es la imagen del sprite
     * 
     */
    public Image getImage() {
        return ImaImagen;
    }
    
    /**
     * setX
     * 
     * Metodo que modifica la posicion en X del sprite
     * 
     * @param x es un <code>int</code> que es su posicion en x
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * setY
     * 
     * Metodo que modifica la posicion en y del sprite
     * 
     * @param y es un <code>int</code> que es su posicion en y
     * 
     */
    public void setY(int iY) {
        this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo que da la posicion en y del sprite
     * 
     * @return un <code>int</code> que es su posicion en y
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * getX
     * 
     * Metodo que da la posicion en x del sprite
     * 
     * @return un <code>int</code> que es su posicion en x
     * 
     */
    public int getX() {
        return iX;
    }

     /**
     * setDying
     * 
     * Metodo modificador para cambiar si el sprite esta muriendo o no
     * 
     * @param dying es un <code>bool</code> que es si esta muriendo o no
     * 
     */
    public void setDying(boolean bDying) {
        this.bDying = bDying;
    }

     /**
     * isDying
     * 
     * Metodo que da si el sprite se está muriendo
     * 
     * @return un <code>bool</code> que es si esta muriendo o no
     * 
     */
    public boolean isDying() {
        return this.bDying;
    }
        
    /**
     * intersecta
     *
     * Metodo que checa si un objeto intersecta a otro
     *
     * @param objObjeto es un objeto de la clase <code>Object</code>
     * @return un boleano para saber si intersecta o no
     */
    public boolean intersecta(Object objObjeto, int alto1, int ancho1, 
            int alto2, int ancho2) {
            Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                    ancho1, alto1);
            
            Sprite aniObjeto = (Sprite) objObjeto;
            Rectangle rctObjetoParam = new Rectangle(aniObjeto.getX(),
                    aniObjeto.getY(), ancho2, alto2);
            
            return rctEsteObjeto.intersects(rctObjetoParam);
    }
    
}