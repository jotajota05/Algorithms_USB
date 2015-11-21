
/**Clase hija de lado que representa el lado de un grafo dirigido*/
public class Arco extends Lado {

	/**Campo del vertice inicial del lado*/
	public Vertice vertInicial;
	/**Campo del vertice final del lado*/
	public Vertice vertFinal;
	/**Campo del peso asociado al lado*/
	public double costo;
	/**Indentificador del arco*/
	public String id;
	
	/**Constructor de la clase*/
	public Arco(String id,Vertice v,Vertice w,double c) {
		vertInicial = v;
		vertFinal = w;
		costo = c;
		this.id = id;
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
	public double costo() {
		return costo;
	}
	
	/**Metodo que retorna el identificador del lado*/
	public String obtID() {
		return id;
	}
}
