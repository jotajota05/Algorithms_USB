
/**Clase hija de lado que representa el lado de un grafo dirigido*/
public class Arco extends Lado {

	/**Campo del identificador del lado*/
	public String id;
	/**Campo del vertice inicial del lado*/
	public Vertice vertInicial;
	/**Campo del vertice final del lado*/
	public Vertice vertFinal;
	/**Campo del peso asociado al lado*/
	public int peso;
	
	/**Constructor de la clase*/
	public Arco(String id,Vertice v,Vertice w,int p) {
		this.id = id;
		vertInicial = v;
		vertFinal = w;
		peso = p;
	}
	
	/**Metodo que devuleve el identificador de un lado*/
	public String identificador() {
		return id;
	}
	
	/**Metodo que devuelve el vertice inicial del lado*/
	public Vertice vertIni() {
		return vertInicial;
	}
	
	/**Metodo que devuelve el vertice final de un lado*/
	public Vertice vertFin() {
		return vertFinal;
	}
	
	/**Metodo que devuelve el peso asociado a un lado*/
	public int peso() {
		return peso;
	}
}
