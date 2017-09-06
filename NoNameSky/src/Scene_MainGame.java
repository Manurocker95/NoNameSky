import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//ActionListener nos permitirá responder a eventos que se producen.
/*======================================================================================================================//
 * 																													    *
 *  		Clase Escenario/Juego principal. Es donde se juega realmente. Todo el core del juego está aquí.			    *
 *  								Es un tipo de escena y tiene escuchadores de eventos y acciones.					*
 *  			El juego consiste en salvar a los prisioneros, dejándolos en las plataformas de salvamento.				*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Scene_MainGame extends Scene implements ActionListener 
{

	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	
	private static Random rnd = new Random();				//Random para generar números aleatorios
    private Timer timer;									//Timer, permite tener un contador en milisegundos para JFrame
    
    private boolean isPlaying = true;
   
    private TAdapter t;						//TAdapter de la clase
    /*private Color textColor;				//Color del texto
    private Font font;						//Fuente del texto*/

    private Image bg;			//Imagen de fondo
    private Ship_Player player;
    private Ship_Group group;
    
	//===================================================================================================================//
	// 											**Constructor**														 //
	//===================================================================================================================//
    
    public Scene_MainGame()
    {
    	//Inicializamos los valores necesarios
    	/*textColor = new Color(255,255,255);
    	font = new Font("Impact",Font.BOLD,20);*/
    	rnd.setSeed(System.currentTimeMillis());
    	
    	initialize();
    	
    	t = new TAdapter();
    	setFoc(true);
    	timer = new Timer(Util_Const.DELAY, this); 	// Creamos un Timer, que cada DELAY milisegundos, lanzará una acción, y llamará al método actionPerformed para calcular movimientos y repintar.
    	timer.start();						// Activamos el Timer. 
    }
    
    void initialize()
    {
    	ImageIcon ii = new ImageIcon("Graphics/Background/bg.png"); // Recogemos la imagen que le pasamos como string
        bg = ii.getImage();	// Recogemos la imagen de ese icóno
        player = new Ship_Player(new Util_Vector(Util_Const.WINDOW_WIDTH/2, Util_Const.WINDOW_HEIGHT/2), new Util_Vector(-1, 0), "Graphics/Sprites/leaderShip.png", 3, 32, 0);
        group = new Ship_Group();
        player.setShipGroupReference(group);
    }
    
    @Override
    // Cuando se produce una acción, con cada tic del timer, Java invocará este método. 
    public void actionPerformed(ActionEvent e) 
    {	
        repaint();  
        if (player.getNumLives() > 0)
        {
            player.move();
            
            group.Move(player);
            
            player.CalculateDamages(group.getEnemies());
        }
        else
        {
        	isPlaying = false;
        }
    }
    
    private void doDrawing(Graphics g)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	//imagen del fondo (Espacio)
        g2d.drawImage(bg, 0, 0, this);
        
        if (isPlaying)
        {
            player.paint(g2d, this);
            
            group.Paint(g2d, this);
            
    		g2d.setColor(Util_Const.DEFAULT_TEXT_COLOR);
            g2d.setFont(Util_Const.DEFAULT_FONT);
            g2d.drawString("Player live: " + player.getLife() + "%", 50, 65);	
            g2d.drawString("Number of Lives: " + player.getNumLives() + " Lives", 50, 100);	
            g2d.drawString("Score: " + Score_Manager.Instance().getScore() + " Pts", 50, 140);	
        }
        else
        {
    		g2d.setColor(Util_Const.DEFAULT_TEXT_COLOR);
            g2d.setFont(Util_Const.DEFAULT_FONT);
            g2d.drawString("HAS PERDIDO. PULSA ENTER PARA CONTINUAR", 100, 100);	
        }
    }
    
    @Override
    public void paintComponent(Graphics g) 
    {	// Método que será llamado cuando Java determina que hay que pintar el escenario.
        super.paintComponent(g);				// Llamamos al método de su padre...

        doDrawing(g);							// ... y añadimos nuestra función de pintado de elementos del escenario.

        Toolkit.getDefaultToolkit().sync();		// Forzamos el dibujado de todos los elementos de forma adecuada. Necesario para algunos sistemas.
    }
    
    private class TAdapter extends KeyAdapter 
    {
    	@Override
    	public void keyPressed(KeyEvent e) 
    	{
    		if(isPlaying){
    			player.keyPressed(e);
    		}
    		else{
    			if(e.getKeyCode() == KeyEvent.VK_ENTER){
    				try {
            			timer.stop();
    					SceneManager.Instance().loadScene(3);
    				} catch (ParserConfigurationException | SAXException | IOException | InterruptedException
    						| FontFormatException e1) {
    					e1.printStackTrace();
    				}
    			}
    		}
    	}
    	public void keyReleased(KeyEvent e){
    		if(isPlaying){
    			player.keyReleased(e);
    		}
    	}
    }

	public void setFoc(boolean focusable)
	{
		setFocusable(focusable);
		if (!focusable)
		{
			removeKeyListener(t);
		}
		else
		{
			addKeyListener(t);
		}
	}
    
    public static int GetRandomInt(int max)
    {
    	return rnd.nextInt(max);
    }
}