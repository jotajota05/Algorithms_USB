
/**Clase hija de lado que representa el lado de un grafo no dirigido*/
public class Arista extends Lado {

	/**Campo del identificador del lado*/
	public String id;
	/**Campo del vertice inicial del lado*/
	public Vertice v;
	/**Campo del vertice final del lado*/
	public Vertice w;
	/**Campo del costo asociado al lado*/
	public double c;
	
	/**Constructor de la clase*/
	public Arista(String id,Vertice v,Vertice w,double c) {
		this.id = id;
		this.v = v;
		this.w = w;
		this.c = c;
	}
	
	/**Metodo que devuleve el identificador de un lado*/
	public String obtID() {
		return id;
	}
	
	/**Metodo que devuelve el vertice inicial del lado*/
	public Vertice vertIni() {
		return v;
	}
	
	/**Metodo que devuelve el vertice final de un lado*/
	public Vertice vertFin() {
		return w;
	}
	
	/**Metodo que devuelve el peso asociado a un lado*/
	public double costo() {
		return c;
	}
}
