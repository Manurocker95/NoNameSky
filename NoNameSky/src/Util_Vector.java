public class Util_Vector 
{
	 public float x, y, z;
	 public Util_Vector () 
	 {
		 x = 0; y = 0; z = 0;
	 }
	 public Util_Vector (float _x) {
		 x = _x; y = 0; z = 0;
	 }
	 public Util_Vector (float _x, float _y) {
		 x = _x; y = _y; z = 0;
	 }
	 public Util_Vector (float _x, float _y, float _z) {
		 x = _x; y = _y; z = _z;
	 }
	 public Util_Vector (Util_Vector u) {
		 x = u.x; y = u.y; z = u.z;
	 }
	 public float magnitud () {
		 return (float) Math.sqrt (x*x + y*y + z*z);
	 }
	 // Convierte el vector en un vector normalizado, es decir, de magnitud 1.
	 // Se consigue dividiendo cada componente por la magnitud del vector.
	 public void normalizar () {
		 float m = magnitud();
		 if (m <= Util_Const.TOL) m = Util_Const.TOL;
		 x /= m;
		 y /= m;
		 z /= m;
		 if (Math.abs(x) < Util_Const.TOL) x = 0;
		 if (Math.abs(y) < Util_Const.TOL) y = 0;
		 if (Math.abs(z) < Util_Const.TOL) z = 0;
	 }
	 
	 public void invertir () {
		 x = -x; y = -y; z = -z;
	 }
	 
	 public Util_Vector mas (Util_Vector v) {
		 Util_Vector w = new Util_Vector (x + v.x, y + v.y, z + v.z);
		 return w;
	 }
	 
	 public Util_Vector menos (Util_Vector v) {
		 Util_Vector w = new Util_Vector (x - v.x, y - v.y, z - v.z);
		 return w;
	 }
	 
	 public Util_Vector prod (float p) {
		 Util_Vector w = new Util_Vector (x * p, y * p, z * p);
		 return w;
	 }
	 
	 public Util_Vector div (float p) {
		 Util_Vector w = new Util_Vector (x / p, y / p, z / p);
		 return w;
	 }
	 
	 public Util_Vector neg () {
		 Util_Vector w = new Util_Vector (-x, -y, -z);
		 return w;
	 }
		 
	public float prodEsc (Util_Vector v) {
		 return x * v.x + y * v.y + z * v.z;
	}
	
	// Devuelve el vector que queda después de girarlo un ángulo dado en radianes.
	public Util_Vector rotar2D ( float angulo ) {
		float nuevoX, nuevoY;
		nuevoX = (float) (x * Math.cos(-angulo) + y * Math.sin(-angulo));
		nuevoY = (float) (-x * Math.sin(-angulo) + y * Math.cos(-angulo));
		Util_Vector w = new Util_Vector (nuevoX, nuevoY, 0);
		return w;
	 }
	
	 public int getX()
	 {
		 return (int)x;
	 }
	 
	 public int getY()
	 {
		 return (int)y;
	 }
}