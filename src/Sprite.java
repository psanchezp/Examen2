
import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Sprite {

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        public Sprite() {
            visible = true;
        }

        public void die() {
            visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return image;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        
        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public void setDying(boolean dying) {
            this.dying = dying;
        }

        public boolean isDying() {
            return this.dying;
        }
        
            /*
     * intersecta
     *
     * Metodo que checa si un objeto intersecta a otro
     *
     * @param objObjeto es un objeto de la clase <code>Object</code>
     * @return un boleano para saber si intersecta o no
     */
    public boolean intersecta(Object objObjeto, int alto1, int ancho1, int alto2, int ancho2) {
            Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                    ancho1, alto1);
            
            Sprite aniObjeto = (Sprite) objObjeto;
            Rectangle rctObjetoParam = new Rectangle(aniObjeto.getX(),
                    aniObjeto.getY(), ancho2, alto2);
            
            return rctEsteObjeto.intersects(rctObjetoParam);
    }
    
}
