import java.awt.Color;
import java.awt.Font;

public class Util_Const 
{
	 public static final String BEST_TITLE_EVER = "No Name Sky 2: The Return Of The Leader"; // LIIIIIIIIIIIIIIIDER
	 public static final int WINDOW_WIDTH = 1024;					// Ancho de la pantalla
	 public static final int WINDOW_HEIGHT = 768;					// Alto de la pantalla
	 public static final int DELAY = 24;							// Retraso para refrescar el JFrame
	 
	 public static final boolean SHOW_SHIPS_INFO = false;
	 
	 public static final float TOL = 0.001f;						// Valor mínimo para calcular un vector normalizado
	 public static final float ANGULO_GIRO = 0.010f; 				// En radianes.
	 
	 public static final float MAX_FUERZA_GIRO = 300f;				// Fuerza de giro
	 public static final float FACTOR_DE_SEPARACION = 1.0f;			// Factor separación
	 public static enum VISION_TYPE { LIMITADA, AMPLIA, ESTRECHA }	// Tipos de vision
	 public static final float RADIO_VISION_AMPLIA = 200;			// Valor del 
	 public static final float RADIO_VISION_LIMITADA = 100;
	 public static final float RADIO_VISION_ESTRECHA = 50; 
	 public static final float FACTOR_ANGULO_TRASERO = 1; 			// 1: 45º. Mayor factor, mayor ángulo muerto.
	 public static final float FACTOR_ANGULO_DELANTERO = 1; 		// 1: 45º. Mayor factor, mayor ángulo de visión.

	 public static final int ACCELERATION = 2;
	 public static final int TURN_ANGLE = 5;
	 public static final int CANTIDAD_NAVES_ENEMIGAS = 8;
	 public static final int DEFAULT_TEXT_SIZE = 20;
	 public static final Color DEFAULT_TEXT_COLOR = new Color(255,255,255);
	 public static final Font DEFAULT_FONT = new Font("Impact",Font.BOLD,DEFAULT_TEXT_SIZE);
	 
	 //Constantentes Neuronales
	 
	 public static final float LEARNING_RATIO = 0.3f;
	 public static final float INERTIA_FACTOR = 0.5f;
	 public static final boolean OUTPUT_LINEAL = false;
	 public static final boolean USED_INERTIA = true;
	 
	 // Constantes de errores
	 
	 public static final double OUTPUT_ERROR = Double.MAX_VALUE; // Un valor para indicar error. No debe estar dentro del rango de outputs.
	 
	 public static final boolean PRINT_EPOCHS = false; 
	 
	 public static enum SHIP_ACTIONS {CHASE, MOVETO, RUNAWAY, NOTHING};
	 
	 public static final int MAX_HP_ENEMY = 10;
	 public static final int MAX_HP_PLAYER = 100;
	 
	 public static final int SHIP_RADIUS = 23;
	 public static final int DAMAGE_DISTANCE = 46;
	 
	 public static final int MAX_PLAYER_LIVES = 3;
	 
	 public static final int SCORE_PER_ENEMY = 100;
	 public static final int MAX_SCORES = 3;
	 
	 
//=========================================================================

	 public static final double ATAQUE_NAVE_PJ = 1;
	 
//=========================================================================
	 
	 public static final int MUTABILITY = 5; 
	 public static final int SURVIVAL_PER_GEN = 8;
	 public static final int MAX_SHIP_ATK = 4;
	 public static final int MAX_GENETIC_STATS = 5;
}