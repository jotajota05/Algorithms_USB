
/**Clase hija de lado que representa el lado de un grafo no dirigido*/
public class Arista extends Lado {

	/**Campo del identificador del lado*/
	public String id;
	/**Campo del vertice inicial del lado*/
	public Vertice v;
	/**Campo del vertice final del lado*/
	public Vertice w;
	/**Campo del peso asociado al lado*/
	public int peso;
	
	/**Constructor de la clase*/
	public Arista(String id,Vertice v,Vertice w,int p) {
		this.id = id;
		this.v = v;
		this.w = w;
		peso = p;
	}
	
	/**Metodo que devuleve el identificador de un lado*/
	public String identificador() {
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
	public int peso() {
		return peso;
	}
}
