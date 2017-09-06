import java.awt.Image;
import java.awt.event.KeyEvent;

public class Ship_Enemy extends Ship_Player
{
	 protected Ship_Group shipGroupReference;
	 protected int radius = Util_Const.SHIP_RADIUS;
	 protected int shipID;
	 protected Util_Const.SHIP_ACTIONS action;
	 public double[] inputs;
	 public int numNeighbor = 0;

	 // Condiciones de genética
	 
	 private int attackDamage;
	 
	 private int hostility;
	 private int escapacity;
	 private int sociability;
	 
	 
	 Util_Const.VISION_TYPE visionType = Util_Const.VISION_TYPE.AMPLIA;
	 
	 public Ship_Enemy(Util_Vector _pos, Util_Vector _dir) 
	 {
		 super(_pos, _dir);
		 action = Util_Const.SHIP_ACTIONS.NOTHING;
		 isPlayer = false;
		 inputs = new double [4];
		 life = Scene_MainGame.GetRandomInt(Util_Const.MAX_HP_ENEMY-1)+1;
		 originalLife = life;
		 attackDamage = Scene_MainGame.GetRandomInt(Util_Const.MAX_SHIP_ATK)+1;
		 hostility = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 escapacity = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 sociability = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 shipID = 0;
	 }
	 
	 public Ship_Enemy(Util_Vector _pos, Util_Vector _dir, String _img, int _numFrames, int _frameSize,int _id, Ship_Group reference)
	 {
		 super(_pos, _dir, _img, _numFrames, _frameSize, _id);
		 action = Util_Const.SHIP_ACTIONS.NOTHING;
		 isPlayer = false;
		 shipGroupReference = reference;
		 inputs = new double [4];
		 life = Scene_MainGame.GetRandomInt(Util_Const.MAX_HP_ENEMY-1)+1;
		 originalLife = life;
		 attackDamage = Scene_MainGame.GetRandomInt(Util_Const.MAX_SHIP_ATK)+1;
		 hostility = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 escapacity = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 sociability = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 shipID = _id;
	 }
	 
	 public Ship_Enemy(Util_Vector _pos, Util_Vector _dir, Image _img, int _numFrames, int _frameSize,int _id)
	 {
		 super(_pos, _dir, _img, _numFrames, _frameSize, _id); 
		 action = Util_Const.SHIP_ACTIONS.NOTHING;
		 isPlayer = false;
		 inputs = new double [4];
		 life = Scene_MainGame.GetRandomInt(Util_Const.MAX_HP_ENEMY-1)+1;
		 originalLife = life;
		 attackDamage = Scene_MainGame.GetRandomInt(Util_Const.MAX_SHIP_ATK)+1;
		 hostility = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 escapacity = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 sociability = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
		 shipID = _id;
	 }
	 
	 public int getAttackDamage(){return attackDamage;}
	 
	 //Mutación random
	 public void mutate()
	 {
		 // Si se da la mutabilidad
		 if (Scene_MainGame.GetRandomInt(10) < Util_Const.MUTABILITY)
		 {
			 //Mutamos el ataque de la siguiente generación
			 attackDamage = Scene_MainGame.GetRandomInt(Util_Const.MAX_SHIP_ATK)+1;
			 
			 //Mutamos la vida de la siguiente generación
			 life = Scene_MainGame.GetRandomInt(Util_Const.MAX_HP_ENEMY-1)+1;
			 originalLife = life;
		 }
		 
		 switch(this.action){
		 case CHASE:
			 mutateHostibility();
			 break;
		 case RUNAWAY:
			 mutateEscapacity();
			 break;
		 default:
			 mutateSociability();
			 break; 
		 }
	 }
	 
	 public void mutateHostibility()
	 {
		 hostility = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
	 }
	 
	 public void mutateEscapacity()
	 {
		 escapacity = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
	 }
	 
	 public void mutateSociability()
	 {
		 sociability = Scene_MainGame.GetRandomInt(Util_Const.MAX_GENETIC_STATS)+1;
	 }
	 
	 public double getCromosomeEffect()
	 {
		 double total = hostility + escapacity + sociability;
		 double hostility_proportion = hostility / total;
		 double escapacity_proportion = escapacity / total;
		 double sociability_proportion = sociability / total;
		 
		 if(hostility_proportion > escapacity_proportion && hostility_proportion > sociability_proportion){
			 return 0.25;
		 }
		 
		 if(escapacity_proportion > hostility_proportion && escapacity_proportion > sociability_proportion){
			 return -0.25;
		 }
		 
		 return 0;
	 }
	 
	 //MÉTODO 1 - SIMPLE
	 public void moveTo(Ship_Player lider)
	 {
		 //Movemos hacia la primera posicion(lider)
		 Util_Vector aux = this.pos.menos(lider.pos);
		 aux = aux.rotar2D(-this.rotacion);
		 if(aux.y > 0){
			 turnLeft = true;
			 turnRight = false;
		 } else {
			 turnLeft = false;
			 turnRight = true;
		 }
		 ApplyMovements();
	 }
	 
	 public void runAwayFrom (Ship_Player objetivo) {

		 Util_Vector v = objetivo.pos;
		 Util_Vector u = v.menos(pos);
		 u.normalizar();
		 Util_Vector w = u.rotar2D(-rotacion);

		 if (w.y < - Util_Const.TOL){
			 turnLeft = false; 
		 }
		 else {
			 turnLeft = true;
		 } 

		 if (w.y > Util_Const.TOL){
			 turnRight = false;
		 }
		 else {
			 turnRight = true;
		 }
	}
	 
	 //MÉTODO 2 - COMPLEJO
	 public float recalcularDir(Ship_Player lider){
		 Util_Vector aux = this.pos.menos(lider.pos);
		 aux = aux.rotar2D(-this.rotacion);
		 if(aux.y > 0){
			 return -1;
		 } else {
			 return 1;
		 }
	 }
	 
	 
	public void DoDamage(Ship_Enemy e) 
	{
		this.life--;
		if(this.life <=0)
		{
			pos = new Util_Vector(Scene_MainGame.GetRandomInt(Util_Const.WINDOW_WIDTH), Scene_MainGame.GetRandomInt(Util_Const.WINDOW_HEIGHT));
			dir = new Util_Vector(1, 1);
			dir.normalizar();
			rotacion = (float) Math.atan2 (dir.y, dir.x);
			this.life = this.originalLife;
			Score_Manager.Instance().Add_Score(Util_Const.SCORE_PER_ENEMY);
			shipGroupReference.getNet().retrainNet(e.inputs, 0.1, 0.1, 0.9);
			
		}
	}
	 
	public void calcularVecinos(Ship_Enemy [] posibles_vecinos)
	{
		 numNeighbor = 0;
		 Util_Vector distanciaVecino;
		 float radioVision = 0;
		 
		 switch (visionType) 
		 {
			 case AMPLIA: radioVision = Util_Const.RADIO_VISION_AMPLIA; break;
			 case LIMITADA: radioVision = Util_Const.RADIO_VISION_LIMITADA; break;
			 case ESTRECHA: radioVision = Util_Const.RADIO_VISION_ESTRECHA; break;
		 }
		 
		//Comprobacion de Vecinos
		 for(Ship_Enemy s : posibles_vecinos)
		 {
			 //No tiene el ID de la nave que analiza
			 if(s.shipID != this.shipID)
			 {
				 //Se calcula el vector de una nave a la otra
				 distanciaVecino = s.pos.menos(this.pos);
				 //Si se encuentra dentro del rango de vecinos
				 if(distanciaVecino.magnitud() <= radioVision)
				 {
					 numNeighbor++;
				 }
			 }
		 }
	}
	
	 public void calcularGiro (Ship_Enemy [] vecinos, Ship_Player player) 
	 {
		 Util_Vector posMedia = new Util_Vector (0, 0, 0);
		 Util_Vector dirMedia = new Util_Vector (0, 0, 0);
		 int cercanos = 0;
		 float giroTotal = 0;
		 double giroParcial;
		 int signoGiro = 0;
		 float radioVision = 0;
		 boolean enRango;
		 Util_Vector distanciaVecino, orientacionVecino;
		 Util_Vector u, v, w;
		 switch (visionType) 
		 {
			 case AMPLIA: radioVision = Util_Const.RADIO_VISION_AMPLIA; break;
			 case LIMITADA: radioVision = Util_Const.RADIO_VISION_LIMITADA; break;
			 case ESTRECHA: radioVision = Util_Const.RADIO_VISION_ESTRECHA; break;
		 }
		 //Comprobacion de Vecinos
		 for(Ship_Enemy s : vecinos)
		 {
			 //No tiene el ID de la nave que analiza
			 if(s.shipID != this.shipID)
			 {
				 //Se calcula el vector de una nave a la otra
				 distanciaVecino = s.pos.menos(this.pos);
				 //Si se encuentra dentro del rango de vecinos
				 if(distanciaVecino.magnitud() <= radioVision)
				 {
					 numNeighbor++;
				 	 distanciaVecino = distanciaVecino.rotar2D(this.rotacion);
					 switch(visionType)
					 {
					 	//180º
					 	case LIMITADA:
					 		//Esta por delante de la nave
					 		if(distanciaVecino.x > 0)
					 		{
					 			posMedia = posMedia.mas(s.pos);
					 			dirMedia = dirMedia.mas(s.dir);
					 			cercanos++;
					 			//Regla de Separación
					 			if(distanciaVecino.magnitud() < Util_Const.FACTOR_DE_SEPARACION * radius)
					 			{
					 				if(distanciaVecino.y < 0)
					 				{
					 					signoGiro = -1;
					 				}
					 				if(distanciaVecino.y >0)
					 				{
					 					signoGiro = 1;
					 				}
					 				giroParcial = signoGiro * Util_Const.MAX_FUERZA_GIRO * ((radius * Util_Const.FACTOR_DE_SEPARACION)/distanciaVecino.magnitud());
					 				giroTotal += giroParcial;
					 			}
					 		}
					 		break;
					 	//270º
						case AMPLIA: 
					 		//Esta en el rango de vision
					 		if(distanciaVecino.x > 0 || (distanciaVecino.x <= 0 && distanciaVecino.y > distanciaVecino.x* Util_Const.FACTOR_ANGULO_TRASERO))
					 		{
					 			posMedia = posMedia.mas(s.pos);
					 			dirMedia = dirMedia.mas(s.dir);
					 			cercanos++;
					 			//Regla de Separación
					 			if(distanciaVecino.magnitud() < Util_Const.FACTOR_DE_SEPARACION * radius)
					 			{
					 				if(distanciaVecino.y < 0)
					 				{
					 					signoGiro = -1;
					 				}
					 				if(distanciaVecino.y >0)
					 				{
					 					signoGiro = 1;
					 				}
					 				giroParcial = signoGiro * Util_Const.MAX_FUERZA_GIRO * ((radius * Util_Const.FACTOR_DE_SEPARACION)/distanciaVecino.magnitud());
					 				giroTotal += giroParcial;
					 			}
					 		}
							break;
						//90º
						case ESTRECHA: 
							//Esta en el rango de vision
					 		if(distanciaVecino.x > 0 && distanciaVecino.y < distanciaVecino.x * Util_Const.FACTOR_ANGULO_DELANTERO)
					 		{
					 			posMedia = posMedia.mas(s.pos);
					 			dirMedia = dirMedia.mas(s.dir);
					 			cercanos++;
					 			//Regla de Separación
					 			if(distanciaVecino.magnitud() < Util_Const.FACTOR_DE_SEPARACION * radius)
					 			{
					 				if(distanciaVecino.y < 0)
					 				{
					 					signoGiro = -1;
					 				}
					 				if(distanciaVecino.y >0)
					 				{
					 					signoGiro = 1;
					 				}
					 				giroParcial = signoGiro * Util_Const.MAX_FUERZA_GIRO * ((radius * Util_Const.FACTOR_DE_SEPARACION)/distanciaVecino.magnitud());
					 				giroTotal += giroParcial;
					 			}
					 		}
					 		break;
					 }
				 }
			 }
		 }
		 if(cercanos > 1)
		 {
			posMedia = posMedia.div(cercanos);
			dirMedia = dirMedia.div(cercanos);
			dirMedia.normalizar();
		 }
		 
		 //Regla de Cohesión
		 if(cercanos > 0)
		 {
			u = posMedia.menos(pos);
			u.normalizar();
			v = new Util_Vector(dir);
			v.normalizar();
			w = u.rotar2D(-rotacion);
			signoGiro = 0;
			if(w.y < 0){
				signoGiro = -1;
			}
			if(w.y > 0){
				signoGiro = 1;
			}
			float prodEsc = u.prodEsc(v);
			if(Math.abs(prodEsc) < 1){
				giroParcial = signoGiro * Util_Const.MAX_FUERZA_GIRO * Math.acos(prodEsc) / Math.PI;
				giroTotal += giroParcial;
			}
		 }
		 
		 //Regla de Alineamiento
		 if(cercanos > 0){
			 u = new Util_Vector (dirMedia);
			 u.normalizar();
			 v = new Util_Vector (dir);
			 v.normalizar();
			 w = u.rotar2D(-rotacion);
			 signoGiro = 0;
			 if (w.y < 0){
				 signoGiro = -1;
			 }
			 if (w.y > 0) {
				 signoGiro = +1;
			 }
			 float prodEsc = u.prodEsc(v);
			 if (Math.abs(prodEsc) < 1) {
				 giroParcial = signoGiro * Util_Const.MAX_FUERZA_GIRO * Math.acos(prodEsc) / Math.PI;
				 giroTotal += giroParcial;
			 }
		 }
		 
		 giroTotal += recalcularDir(player);
		 
		 if(giroTotal > Util_Const.TOL){
			 this.turnRight = true;
		 } else {
			 this.turnRight = false;
		 }
		 if(giroTotal < -Util_Const.TOL){
			 this.turnLeft = true;
		 } else {
			 this.turnLeft = false;
		 }
	 } 
	 
	 public void move(Ship_Enemy [] neighbors, Ship_Player player)
	 {
		calcularGiro(neighbors, player);
		ApplyMovements();
	 }
	 
	 private void CheckBounds()
	 {
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
	 
	 private void ApplyMovements()
	 {
		float rotacionParcial = (float) Math.atan2 (dir.y, dir.x);
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
		pos = pos.mas(dir.prod(Util_Const.ACCELERATION));
		
		CheckBounds();
	 }
	 
	 public int getRadius()
	 {
		return radius; 
	 }	
	 
	 public void keyPressed(KeyEvent e) 
	 {
	 	super.keyPressed(e);
	 }
	 public void keyReleased(KeyEvent e) 
	 {
		 super.keyReleased(e);
	 }
	 
	 public void paintInfo(){
		 System.out.println("\n\n");
		 System.out.println("SHIP ID: " + this.shipID);
		 System.out.println("- - - - -");
		 System.out.println("Health: " + this.life);
		 System.out.println("Attack: " + this.attackDamage);
		 System.out.println("Hostility: " + this.hostility);
		 System.out.println("Escapability: " + this.escapacity);
		 System.out.println("Sociability: " + this.sociability);
		 System.out.println("Neighbors: " + this.numNeighbor);
		 System.out.println("- - - - -");
	 }
}