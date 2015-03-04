
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Board extends JPanel implements Runnable, Commons { 

    private Dimension d;
    private ArrayList aliens;
    private Player player;
    private Shot shot;

    private int alienX = 150;
    private int alienY = 5;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private final String expl = "explosion.png";
    private final String alienpix = "alien.png";
    private String message = "Game Over";
    private boolean bPausa;
    private boolean bInstrucciones;
    private boolean bGameOver;
    
    private SoundClip scSonido1; //Explosion aliens
    private SoundClip scSonido2; //Explosion jugador
    private SoundClip scSonido3; //Disparo
    
    private Thread animator;

    public Board() 
    {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        bPausa = false;
        bInstrucciones = false;
        bGameOver = false;
        
        aliens = new ArrayList();

        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        for (int i=0; i < 4; i++) {
            for (int j=0; j < 6; j++) {
                Alien alien = new Alien(alienX + 18*j, alienY + 18*i);
                alien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();

        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        
        scSonido1 = new SoundClip ("explosion1.wav");
        scSonido2 = new SoundClip ("explosion2.wav");
        scSonido3 = new SoundClip ("laser.wav");
    }

    public void drawAliens(Graphics g) 
    {
        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();

            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {
        if (shot.isVisible())
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
    }

    public void drawBombing(Graphics g) {

        Iterator i3 = aliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this); 
            }
        }
    }

    public void paint(Graphics g)
    {
      super.paint(g);

      g.setColor(Color.black);
      g.fillRect(0, 0, d.width, d.height);
      g.setColor(Color.green);   

      if (ingame) {
        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("background.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        
        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
        drawAliens(g);
        drawPlayer(g);
        drawShot(g);
        drawBombing(g);
      }

      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }

    public void gameOver()
    {

        Graphics g = this.getGraphics();

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("background.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message))/2, 
            BOARD_WIDTH/2);
    }
    
    public void menu()
    {

        Graphics g = this.getGraphics();

        // Actualiza la imagen de fondo.
        if (bInstrucciones){
            URL urlImagenFondo = this.getClass().getResource("backgroundInstrucciones.png");
            Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
            g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        }
        else if (bPausa){
            URL urlImagenFondo = this.getClass().getResource("backgroundPausa.png");
            Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
            g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        }
         
    }

    public void animationCycle()  {

        //Si ya mataron a todos los jugadores
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            ingame = false;
            message = "Game won!";
        }

        // player
        player.act();

        //Colisiones del disparo del jugador
        if (shot.isVisible()) {
            Iterator it = aliens.iterator();
            int shotX = shot.getX();
            int shotY = shot.getY();

            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();

                //Checa colision del shot con el alien
                if (alien.isVisible() && shot.isVisible()) {
                    if (alien.intersecta(shot, ALIEN_HEIGHT, ALIEN_WIDTH, 30, 30)){
                            ImageIcon ii = 
                                new ImageIcon(getClass().getResource(expl));
                            alien.setImage(ii.getImage());
                            alien.setDying(true);
                            scSonido1.play();
                            deaths++;
                            shot.die();
                        }
                }
            }

            //Si se sale del board
            int y = shot.getY();
            y -= 4;
            if (y < 0)
                shot.die();
            else shot.setY(y);
        }

        //colision de alien con las paredes
         Iterator it1 = aliens.iterator();
         while (it1.hasNext()) {
             Alien a1 = (Alien) it1.next();
             int x = a1.getX();

             if (x  >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                 direction = -1;
                 Iterator i1 = aliens.iterator();
                 while (i1.hasNext()) {
                     Alien a2 = (Alien) i1.next();
                     a2.setY(a2.getY() + GO_DOWN);
                 }
             }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                Iterator i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien)i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = aliens.iterator();
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                //si los aliens llegan al piso
                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        
        //Colisiones de las bombas de los aliens
        Iterator i3 = aliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {
                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();
                    
            //Colision entre bomba del alien y el jugador
            if (player.isVisible() && !b.isDestroyed()) {
                if (player.intersecta(b, PLAYER_HEIGHT, PLAYER_WIDTH, 15, 15)) {
                        ImageIcon ii = 
                            new ImageIcon(this.getClass().getResource(expl));
                        player.setImage(ii.getImage());
                        player.setDying(true);
                        scSonido2.play();
                        b.setDestroyed(true);;
                    }
            }

            //Si se sale del board
            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);   
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        
        while (ingame) {
            if (bPausa || bInstrucciones){
                menu();
            }
            else{
                repaint();
                animationCycle();

                timeDiff = System.currentTimeMillis() - beforeTime;
                sleep = DELAY - timeDiff;

                if (sleep < 0) 
                    sleep = 2;
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                beforeTime = System.currentTimeMillis();
            }   
        }
        gameOver();
    }
    
    public void save() {
        
    }
    
    public void load() {
        
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {

          player.keyPressed(e);

          int x = player.getX();
          int y = player.getY();

          //Si dispara
          if (ingame)
          {
            if (e.isAltDown()) {
                if (!shot.isVisible()){
                    shot = new Shot(x, y);
                    scSonido3.play();
                }
            }
          }
          
          //Se pausa el juego
          if(e.getKeyCode() == KeyEvent.VK_P) {
            //ingame = !ingame;
            bPausa = !bPausa;
            message = "Pausa";
          }
          
          //Se muestran instrucciones
          if(e.getKeyCode() == KeyEvent.VK_I) {
            //ingame = !ingame;
            message = "Instrucciones: Usa la tecla ALT para disparar a los aliens"
                    + " y destruirlos antes de que lleguen al suelo. El jugador"
                    + " se mueve con las flechas izquierda y derecha";
            bInstrucciones = !bInstrucciones;
          }
          
         //Se muestran autores
          if(e.getKeyCode() == KeyEvent.VK_R) {
            ingame = !ingame;
            message = "Autores: David Benítez y Patricio Sánchez";
          }
          
          //Se guarda juego
          if(e.getKeyCode() == KeyEvent.VK_G) {
              //bPausa = !bPausa;
          }
          
          //Se carga juego
          if(e.getKeyCode() == KeyEvent.VK_C) {
              //bPausa = !bPausa;
          }
          
        }
    }
}