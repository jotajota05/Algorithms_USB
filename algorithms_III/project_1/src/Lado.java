
/**Clase abstracta que representa un lado y todas sus operaciones*/
public abstract class Lado {

	/**Metodo que devuleve el identificador de un lado*/
	public abstract String identificador();
	
	/**Metodo que devuelve el vertice inicial del lado*/
	public abstract Vertice vertIni();
	
	/**Metodo que devuelve el vertice final de un lado*/
	public abstract Vertice vertFin();
	
	/**Metodo que devuelve el peso asociado a un lado*/
	public abstract int peso();
	
}
