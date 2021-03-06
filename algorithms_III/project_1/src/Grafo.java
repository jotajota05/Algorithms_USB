import java.util.*;

/**Clase abstracta que representa un grafo y todas sus operaciones*/
public abstract class Grafo {
	
	/**Metodo que devuelve el tipo de grafo con el que estamos trabajando*/
	public abstract String Tipo();
	
	/**Metodo que agrega un vertice dado al grafo*/
	public abstract Grafo agregarVertice(Vertice v);
	
	/**Metodo que elimina un vertice existente en el grafo*/
	public abstract Grafo eliminarVertice(Vertice v);
	
	/**Metodo para modificar la informacion de un vertice*/
	public abstract Grafo modificarVertice(Vertice v);
	
	/**Metodo que devuelve la lista de vertices de un grafo*/
	public abstract LinkedList<Vertice> Vertices();
	
	/**Metodo que devuelve la lista de vertices adyacentes a un vertice dado*/
	public abstract LinkedList<Vertice> adyacentes(Vertice v);
	
	/**Metodo que devuelve un entero que representa el grado de un vertice*/
	public abstract int grado(Vertice v);
	
	/**Metodo que agrega un lado dado al grafo*/
	public abstract Grafo agregarLado(Lado l,boolean cargado);
	
	/**Metodo que elimina un lado lado del grafo*/
	public abstract Grafo eliminarLado(Lado l);
	
	/**Metodo para modificar la informacion de un lado*/
	public abstract Grafo modificarLado(Lado l);
	
	/**Metodo que devulve la lista de lados del grafo*/
	public abstract LinkedList<Lado> Lados();
	
	/**Metodo que devuelve la lista de lados incidentes a un vertice dado*/
	public abstract LinkedList<Lado> incidentes(Vertice v);
	
	/**Metodo que determina si un par de vertices forman un lado*/
	public abstract boolean esLado(Vertice v,Vertice w);
	
	
	
	
}
