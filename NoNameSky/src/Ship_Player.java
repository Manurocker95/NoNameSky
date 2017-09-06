import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class Ship_Player extends Util_Sprite
{
	protected Util_Vector pos;
	protected Util_Vector dir; // Vector normalizado de la dirección de la nave.
	protected float rotacion;
	protected boolean turnLeft = false, turnRight = false, goForward = false, goBack = false, dead;
	protected int life;
	protected int originalLife;
	protected int damageDistance = Util_Const.DAMAGE_DISTANCE;
	protected int numberOfLives = Util_Const.MAX_PLAYER_LIVES;
	protected boolean isPlayer = true;
	private boolean continousMovement = true;
	private Ship_Group shipGroupReference;
	
	
	public Ship_Player (Util_Vector _pos, Util_Vector _dir) 
	{
		 pos = _pos;
		 dir = _dir;
		 dir.normalizar();
		 rotacion = (float) Math.atan2 (dir.y, dir.x);
		 dead = false;
		 life = Util_Const.MAX_HP_PLAYER;
		 originalLife = life;
	}
	
	public Ship_Player (Util_Vector _pos, Util_Vector _dir, Image _img, int _numFrames, int _frameSize,int _id) 
	{
		 super (_img, _numFrames, _frameSize, _id);
		 pos = _pos;
		 dir = _dir;
		 dir.normalizar();
		 rotacion = (float) Math.atan2 (dir.y, dir.x);
		 dead = false;
		 life = Util_Const.MAX_HP_PLAYER;
		 originalLife = life;
	}
	
	public Ship_Player (Util_Vector _pos, Util_Vector _dir, String _img, int _numFrames, int _frameSize,int _id) 
	{
		 super (_img, _numFrames, _frameSize, _id);
		 pos = _pos;
		 dir = _dir;
		 dir.normalizar();
		 rotacion = (float) Math.atan2 (dir.y, dir.x);
		 dead = false;
		 life = Util_Const.MAX_HP_PLAYER;
		 originalLife = life;
		 
	}
	
	public void setShipGroupReference(Ship_Group reference)
	{
		shipGroupReference = reference;
	}
	
	//Adaptar métodos de Control de Teclado, dan valor a boolean giroIzq, giroDra, acelerar, retroceder;
	public void keyPressed(KeyEvent e) 
	{
		if (dead)
		{
			return;
		}
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_DOWN)
		{
			goBack = true;
			//e.setKeyCode(40);
		}
		if (key == KeyEvent.VK_UP)
		{
			goForward =  true;
			//e.setKeyCode(38);
		}
		
		if (key == KeyEvent.VK_RIGHT)
		{
			turnRight = true;
			//e.setKeyCode(39);
		}
		if (key == KeyEvent.VK_LEFT)
		{
			turnLeft = true;
			//e.setKeyCode(37);
		}
	}
	public void keyReleased(KeyEvent e) 
	{
		if (dead)
		{
			return;
		}
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_DOWN)
		{
			goBack = false;
			e.setKeyCode(40);
		}
		if (key == KeyEvent.VK_UP)
		{
			goForward =  false;
			e.setKeyCode(38);
		}
		if (key == KeyEvent.VK_RIGHT)
		{
			turnRight = false;
			e.setKeyCode(39);
		}
		if (key == KeyEvent.VK_LEFT)
		{
			turnLeft = false;
			e.setKeyCode(37);
		}
	}
	
	public void paint (Graphics g, JPanel jp) 
	{
		if (dead)
		{
			return;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform (identity);
		trans.translate(pos.x, pos.y);
		trans.rotate(rotacion + Math.toRadians(90), m_frameSize/2,  m_frameSize/2);
		g2d.drawImage(m_sprite, trans, jp);
	}
	
	public void move () {
		//Actualizar el vector de dirección de la nave en función de los atributos de giro.
		//Actualizar la rotación.
		//Actualizar el vector de posición de la nave en función de los atributos de aceleración y retroceso.
		float rotacionParcial = (float) Math.atan2 (dir.y, dir.x);
		if(continousMovement)
		{
			pos = pos.mas(dir.prod(Util_Const.ACCELERATION+0.5f));
		}
		if(goForward)
		{
			pos = pos.mas(dir.prod(1));
		}
		if(goBack)
		{
			pos = pos.mas(dir.neg());
		}
		if(turnRight)
		{
			rotacionParcial += Math.toRadians(Util_Const.TURN_ANGLE);
			dir = dir.rotar2D((float)Math.toRadians(Util_Const.TURN_ANGLE));
		}
		if(turnLeft)
		{
			rotacionParcial -= Math.toRadians(Util_Const.TURN_ANGLE);
			dir = dir.rotar2D((float)Math.toRadians(-Util_Const.TURN_ANGLE));
		}
		rotacion = rotacionParcial;
		
		if(pos.getX() > Util_Const.WINDOW_WIDTH){
			pos.x = 0;
		}
		
		if(pos.getX() < 0){
			pos.x = Util_Const.WINDOW_WIDTH;
		}
		
		if(pos.getY() > Util_Const.WINDOW_HEIGHT){
			pos.y = 0;
		}
		
		if(pos.getY() < 0){
			pos.y = Util_Const.WINDOW_HEIGHT;
		}
	}
	
	public void CalculateDamages(Ship_Enemy []enemies)
	{
		for(Ship_Enemy enemy : enemies)
		{
        	if(this.pos.menos(enemy.pos).magnitud() <= damageDistance)
        	{
        		this.DoDamage(enemy);
        		enemy.DoDamage(enemy);
        	}
        }
	}
	
	public void DoDamage(Ship_Enemy e)
	{
		this.life-=e.getAttackDamage();
		if(this.life <=0)
		{
			pos = new Util_Vector(Scene_MainGame.GetRandomInt(Util_Const.WINDOW_WIDTH), Scene_MainGame.GetRandomInt(Util_Const.WINDOW_HEIGHT));
			dir = new Util_Vector(1, 1);
			dir.normalizar();
			rotacion = (float) Math.atan2 (dir.y, dir.x);
			numberOfLives--;
			if (numberOfLives > 0)
			{
				this.life = this.originalLife;
			}	
			
			// Si la nave que estamos mirando ha contribuido a matar al PJ,
			// entonces reentrenamos la red con sus valores de input, para hacer refuerzo positivo.
			shipGroupReference.getNet().retrainNet (e.inputs, 0.9, 0.1, 0.1);
		}
	}
	
	public int getX()
	{
		return pos.getX();
	}
	
	public int getY()
	{
		return pos.getY();
	}
	
	public int getLife()
	{
		return life;
	}
	
	public int getNumLives()
	{
		return numberOfLives;
	}
	
	/*private int[] setSheetCoord(){
		
		int [] coord = new int [2];
		
		coord[0] = m_offsets[m_currentFrame] % m_numFrames; 
		coord[1] = m_offsets[m_currentFrame] / m_numFrames;
		
		return coord;
	}*/
}
 