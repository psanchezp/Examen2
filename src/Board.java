
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Board
 *
 * Aquí corre el juego
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 3/4/2015
 */
public class Board extends JPanel implements Runnable, Commons { 

    private Dimension d; //guarda el tamano de la pantalla
    private ArrayList aliens; //guarda los aliens
    private Player player; //objeto jugador
    private Shot shot; //objeto shot

    private int alienX = 150; //posicion inicial del alien en x
    private int alienY = 5; //posicion inicial del alien en y
    private int direction = -1; //direccion inicial
    private int deaths = 0; //cantidad de aliens destruidos

    private boolean ingame = true; //si el juego debe de correr
    private final String expl = "explosion.png"; //imagen de la explosion
    private final String alienpix = "alien.png"; //imagen del alien
    //private String message = "Game Over"; //mensaje a mostrar
    private boolean bPausa; //si esta en pausa
    private boolean bInstrucciones; //si esta en las instrucciones
    private boolean bGameOver; //si se acabo el juego
    private boolean bCredits;
    private boolean bWin; //Ganaste o no?

    
    private SoundClip scSonido1; //Explosion aliens
    private SoundClip scSonido2; //Explosion jugador
    private SoundClip scSonido3; //Disparo
    private SoundClip scBackground; //Background sound
    
    private Animacion aniShot;
    private Animacion aniBomb;
    
    private long lTiempo;
    private long lDiffTiempo;
    
    private Thread animator;

   /**
    * Board
    * 
    * constructor de la clase board
    *
    */
    public Board() 
    {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        setBackground(Color.black);
        scBackground = new SoundClip ("darudesandstorm.wav");
        scBackground.setLooping (true);
        scBackground.play();

        gameInit();
        setDoubleBuffered(true);
    }

   /**
    * Notify
    * 
    * agrega una notificacion
    *
    */
    public void addNotify() {
        super.addNotify();
        gameInit();
    }

   /**
    * gameInit
    * 
    * inicializa variables de juego
    *
    */
    public void gameInit() {
        //Inicializacion de Animacion de la Bala "Shot"
        /*aniShot = new Animacion ();
        Image imaShot1 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("shot.png"));
        Image imaShot2 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("shot2.png"));
        
        aniShot.sumaCuadro (imaShot1, 50);
        aniShot.sumaCuadro (imaShot2, 50);*/
        
        /*Inicialiacion de Animacion de la Bala "Bomb"
        aniBomb = new Animacion ();
        Image imaBomb1 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("bomb.png"));
        Image imaBomb2 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("bomb2.png"));
        
        aniBomb.sumaCuadro(imaBomb1, 50);
        aniBomb.sumaCuadro(imaBomb2, 50); */
        
        bPausa = false; //no esta en pausa
        bInstrucciones = false; //no lee las instrucciones
        bGameOver = false; //no ha perdido
        bCredits = false; //no lee los creditos

        
        aliens = new ArrayList(); //aqui se guardaran los aliens

        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        //dibuja cada alien y los agrega al vector
        for (int i=0; i < 4; i++) {
            for (int j=0; j < 6; j++) {
                Alien alien = new Alien(alienX + 18*j, alienY + 18*i);
                alien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }

        //crea un nuevo jugador
        player = new Player();
        
        //crea un nuevo shot
        shot = new Shot();
        shot.setAnimacion(aniShot);

        //empieza el thread
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        
        //se crean los sonidos
        scSonido1 = new SoundClip ("explosion1.wav");
        scSonido2 = new SoundClip ("explosion2.wav");
        scSonido3 = new SoundClip ("laser.wav");
    }

   /**
    * drawAliens
    * 
    * dibuja los aliens en el jPanel
    *
    */
    public void drawAliens(Graphics g) 
    {
        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            //dibuja al alien
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            //destruye al alien
            if (alien.isDying()) {
                alien.die();
            }
        }
    }

   /**
    * drawPlayer
    * 
    * dibuja al jugador en el jPanel
    *
    */
    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) { //lleva a game over
            player.die();
            ingame = false;
        }
    }

   /**
    * drawShot
    * 
    * dibuja los shot en el jPanel
    *
    */
    public void drawShot(Graphics g) {
        if (shot.isVisible()){ //Dibuja el shot
            shot.getAnimacion().actualiza(lTiempo); 
            g.drawImage(shot.getAnimacion().getImagen(), shot.getX(), shot.getY(), this);
        }
    }

   /**
    * drawBombing
    * 
    * dibuja las bombas en el jPanel
    *
    */
    public void drawBombing(Graphics g) {

        Iterator i3 = aliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) { //dibuja la bomba si no ha sido destruida
                b.getAnimacion().actualiza(lTiempo);
                g.drawImage(b.getAnimacion().getImagen(), b.getX(), b.getY(), this); 
            }
        }
    }

   /**
    * paint
    * 
    * pinta los diferentes componentes en el jPanel
    *
    */
    public void paint(Graphics g)
    {
      super.paint(g);

      //Dibuja el fondo
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
      /*else{
        URL urlImagenFondo = this.getClass().getResource("background.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
      }*/

      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }

   /**
    * gameOver
    * 
    * maneja el fin de juego
    *
    */
    public void gameOver()
    {
        Graphics g = this.getGraphics();
        scBackground.stop();
        Image imaImagenFondo;
        
        
        // Actualiza la imagen de fondo.
        if (bGameOver){
           URL urlImagenFondo = this.getClass().getResource("backgroundInvasion.png");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo); 
        }
        else if (bWin){
            URL urlImagenFondo = this.getClass().getResource("backgroundwin.png");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        }
        else{
            URL urlImagenFondo = this.getClass().getResource("backgroundgameover.png");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        }
        
        while(!ingame){
           g.drawImage(imaImagenFondo, 0, 0, 400, 500, this); 
        }
    }
    
   /**
    * menu
    * 
    * maneja los menus de pausa e instrucciones
    *
    */
    public void menu()
    {

        Graphics g = this.getGraphics();

        // Actualiza la imagen de fondo.
        if (bInstrucciones){ //muestra menu de instrucciones
            URL urlImagenFondo = this.getClass().getResource("backgroundInstrucciones.png");
            Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
            g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        }
        else if (bPausa){ //muestra menu de pausa
            URL urlImagenFondo = this.getClass().getResource("backgroundPausa.png");
            Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
            g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        }
        else if (bCredits){
            URL urlImagenFondo = this.getClass().getResource("backgroundcreditos.png");
            Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
            g.drawImage(imaImagenFondo, 0, 0, 400, 500, this);
        }
         
    }

   /**
    * animationCycle
    * 
    * checa colisiones
    *
    */
    public void animationCycle()  {
        
        //Si ya mataron a todos los jugadores
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            ingame = false;
            bWin = true;
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
                            ImageIcon iI = 
                                new ImageIcon(getClass().getResource(expl));
                            alien.setImage(iI.getImage());
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
                    bGameOver = true;
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
        
        /*if (shot.isVisible()){
            //Animacion Shot
            long lTime = System.currentTimeMillis();
            long timeDiff = System.currentTimeMillis() - lTime;
            shot.getAnimacion().actualiza(timeDiff);
        }*/
    }

   /**
    * run
    * 
    * aquí corre el juego
    *
    */
    public void run() {
        long sleep;

        lTiempo = System.currentTimeMillis();
        
        while (ingame) {
            if (bPausa || bInstrucciones || bCredits){
                menu();
            }
            else{
                repaint();
                animationCycle();

                lDiffTiempo = System.currentTimeMillis() - lTiempo;
                sleep = DELAY - lDiffTiempo;

                if (sleep < 0) 
                    sleep = 2;
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                lTiempo = System.currentTimeMillis();
            }   
        }
        gameOver();
    }
    
    /**
     * Save
     * 
     * Clase para guardar juego
     * 
     * @throws IOException 
     */
    public void save()  throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter("gameData.txt"));

        fileOut.println(bPausa); //se guarda si el juego estaba en pausa
        fileOut.println(bInstrucciones); //se guarda si el juego estaba en pausa
        fileOut.println(direction); //Se guarda direccion de aliens
        fileOut.println(deaths); //Se guarda cantidad de aliens destruidos
        
        //Se guardan variables del alien
        Iterator it = aliens.iterator();
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            Alien.Bomb b = alien.getBomb(); //Se crea la bomba

            fileOut.println(alien.getX()); //se guarda x del alien
            fileOut.println(alien.getY()); //se guarda y del alien
            fileOut.println(alien.isVisible()); //se guarda si el alien es visible
            fileOut.println(b.getX());
            fileOut.println(b.getY());
            fileOut.println(b.isDestroyed());
        }
        
        //Se guardan variables del jugador
        fileOut.println(player.getX()); //se guarda x del jugador
        fileOut.println(player.getY()); //se guarda y del jugador
        fileOut.println(player.bDying); //se guarda bDying del jugador
        
        //Se guardan variables del shot
        fileOut.println(shot.getX()); //se guarda x del shot
        fileOut.println(shot.getY()); //se guarda y del shot
        fileOut.println(shot.isVisible()); //se guarda bVisible del shot
     
        fileOut.close();    //Se cierra el archivo
    }
    
    /**
     * Load
     * 
     * Clase para cargar juego
     * 
     * @throws IOException 
     */
    public void load() throws IOException {
                                                                  
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader("gameData.txt"));
        } catch (FileNotFoundException e){
            File puntos = new File("gameData.txt");
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("100, demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader("gameData.txt"));
        }
        
        String aux = fileIn.readLine();
        bPausa = (Boolean.parseBoolean(aux)); //Leo si el juego está en pausa
        
        aux = fileIn.readLine();
        bInstrucciones = (Boolean.parseBoolean(aux)); //Leo si el juego está en instrucciones
        
        aux = fileIn.readLine();
        direction = (Integer.parseInt(aux)); //Leo direccion de aliens
        
        aux = fileIn.readLine();
        deaths = (Integer.parseInt(aux)); //Leo muertes de aliens
        
        //Se cargan variables del alien
        Iterator it = aliens.iterator();
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            Alien.Bomb b = alien.getBomb(); //Se crea la bomba
            
            aux = fileIn.readLine();
            alien.setX((Integer.parseInt(aux))); //Leo x de aliens
            
            aux = fileIn.readLine();
            alien.setY((Integer.parseInt(aux))); //Leo x de aliens
            
            aux = fileIn.readLine();
            alien.setVisible((Boolean.parseBoolean(aux))); //Leo si el alien es visible
            
            aux = fileIn.readLine();
            b.setX((Integer.parseInt(aux))); //Leo x de bomba de alien
            
            aux = fileIn.readLine();
            b.setY((Integer.parseInt(aux))); //Leo x de bomba de alien
            
            aux = fileIn.readLine();
            b.setDestroyed((Boolean.parseBoolean(aux))); //Leo si la bomba de alien esta destruida
        }
        
        aux = fileIn.readLine();
        player.setX((Integer.parseInt(aux))); //Leo x de jugador
        
        aux = fileIn.readLine();
        player.setY((Integer.parseInt(aux))); //Leo y de jugador
        
        aux = fileIn.readLine();
        player.setDying((Boolean.parseBoolean(aux))); //Leo si el jugador se esta muriendo
        
        aux = fileIn.readLine();
        shot.setX((Integer.parseInt(aux))); //Leo x de shot
        
        aux = fileIn.readLine();
        shot.setY((Integer.parseInt(aux))); //Leo y de shot
        
        aux = fileIn.readLine();
        shot.setVisible((Boolean.parseBoolean(aux))); //Leo si el shot es visible

        fileIn.close();
    }

   /**
    * TAdapter
    * 
    * dibuja los aliens en el jPanel
    *
    */
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
            bPausa = !bPausa;
          }
          
          //Se muestran instrucciones
          if(e.getKeyCode() == KeyEvent.VK_I) {
            bInstrucciones = !bInstrucciones;
          }
          
         //Se muestran autores
          if(e.getKeyCode() == KeyEvent.VK_R) {
            bCredits = !bCredits;
          }
          
          //Se guarda juego
          if(e.getKeyCode() == KeyEvent.VK_G) {
              try {
                  save();
              } catch (IOException ex) {
                  Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
          //Se carga juego
          if(e.getKeyCode() == KeyEvent.VK_C) {
              try {
                  load();
              } catch (IOException ex) {
                  Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
        }
    }
}