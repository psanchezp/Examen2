
import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/ modified by David Benítez and Patricio Sánchez
 */
public class Sprite {

    private boolean visible; //si el sprite esta visible (muerto)
    private Image image; //imagen del sprite
    protected int x; //posicion en x
    protected int y; //posicion en y
    protected boolean dying; //si el sprite esta muriendo
    protected int dx; //diferencial de movimiento en x

    
    /**
     * Sprite
     * 
     * constructor de la clase sprite
     * 
     */
    public Sprite() {
        visible = true;
    }

    /**
     * die
     * 
     * Metodo que modifica si el sprite esta visible al momento de morir
     * 
     */
    public void die() {
        visible = false;
    }
    
    /**
     * isVisible
     * 
     * Metodo que regresa si el sprite esta visible
     * 
     * @return <code>bool</code> que es si esta visible o no
     * 
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * setVisible
     * 
     * Metodo que modifica si el sprite esta visible
     * 
     * @param visible es un <code>bool</code> que es si esta visible o no
     * 
     */
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * setImage
     * 
     * Metodo que modifica la imagen del sprite
     * 
     * @param image es un <code>Image</code> que es la imagen
     * 
     */
    public void setImage(Image image) {
        this.image = image;
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
        return image;
    }
    
    /**
     * setX
     * 
     * Metodo que modifica la posicion en X del sprite
     * 
     * @param x es un <code>int</code> que es su posicion en x
     * 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setY
     * 
     * Metodo que modifica la posicion en y del sprite
     * 
     * @param y es un <code>int</code> que es su posicion en y
     * 
     */
    public void setY(int y) {
        this.y = y;
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
        return y;
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
        return x;
    }

     /**
     * setDying
     * 
     * Metodo modificador para cambiar si el sprite esta muriendo o no
     * 
     * @param dying es un <code>bool</code> que es si esta muriendo o no
     * 
     */
    public void setDying(boolean dying) {
        this.dying = dying;
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
        return this.dying;
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
