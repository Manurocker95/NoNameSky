import java.awt.Graphics2D;
import java.util.Random;

public class Ship_Group 
{
	// Atributos Nuevos para uso de Redes Neuronales
	Random rnd;
	
	protected Ship_Neuronal_Net net;
	private Ship_Enemy[] enemies;

	public Ship_Group () {

		// Inicializar los nuevos atributos
		rnd = new Random();
		net = new Ship_Neuronal_Net();
		Initialize();
	}	

	public void Initialize()
	{
        enemies = new Ship_Enemy[Util_Const.CANTIDAD_NAVES_ENEMIGAS];
        for(int i = 0; i < Util_Const.CANTIDAD_NAVES_ENEMIGAS; i++)
        {
        	enemies[i] = new Ship_Enemy(new Util_Vector(Scene_MainGame.GetRandomInt(Util_Const.WINDOW_WIDTH), Scene_MainGame.GetRandomInt(Util_Const.WINDOW_HEIGHT)), new Util_Vector(1, 1), "Graphics/Sprites/regularShip.png", 3, 32, i, this);
        }
	}
	
	public Ship_Neuronal_Net getNet()
	{
		return net;
	}
	
	public void Move(Ship_Player player)
	{	
		Ship_Enemy previousEnemy = enemies[enemies.length-1];
        for(Ship_Enemy enemy : enemies)
        {        	
        	enemy.calcularVecinos(enemies);
        	
        	if(Util_Const.SHOW_SHIPS_INFO)
        	{
        		enemy.paintInfo();
        	}
        	// Recalcular los valores de inputs, todos ellos convertidos a un valor entre 0 y 1.
    		// Amigos
    		enemy.inputs[0] = (double) enemy.numNeighbor / Util_Const.CANTIDAD_NAVES_ENEMIGAS;

    		// Vida
    		enemy.inputs[1] = (double) enemy.getLife() / Util_Const.MAX_HP_ENEMY;

    		// PJ Luchando
    		if (enemy.numNeighbor > 0) 
    		{
    			enemy.inputs[2] = 1;
    		}
    		else 
    		{
    			enemy.inputs[2] = 0;
    		}

    		// Rango
    		double range = enemy.pos.menos(player.pos).magnitud() / Util_Const.WINDOW_WIDTH;
    		double margin = enemy.getCromosomeEffect();
    		enemy.inputs[3] = Math.max(0, Math.min(range + margin, 1));

    		// Obtener una nueva orden de acción dados estos inputs.

    		net.setInput(0, enemy.inputs[0]);
    		net.setInput(1, enemy.inputs[1]);
    		net.setInput(2, enemy.inputs[2]);
    		net.setInput(3, enemy.inputs[3]);

    		net.feedForward();

    		switch(net.getMaxOutputId())
    		{
	    		case 0:
	    			enemy.action = Util_Const.SHIP_ACTIONS.CHASE;
	    			enemy.moveTo(player);
	    			break;
	    		case 2:
	    			enemy.action = Util_Const.SHIP_ACTIONS.RUNAWAY;
	    			enemy.runAwayFrom(player);
	    			break;
	    		default:
	    			enemy.action = Util_Const.SHIP_ACTIONS.MOVETO;
	    			enemy.calcularGiro(enemies, previousEnemy);
	    			break;
    		}
    		
        	enemy.move(enemies,player);  	
        	previousEnemy = enemy;
        }
	}
	
	public void Paint(Graphics2D g2d, Scene_MainGame scene)
	{
        for(Ship_Enemy enemy : enemies){
        	enemy.paint(g2d, scene);
        }
	}
	
	public Ship_Enemy[] getEnemies()
	{
		return enemies;
	}
	
}
