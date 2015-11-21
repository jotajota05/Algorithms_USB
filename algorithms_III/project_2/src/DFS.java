import java.util.LinkedList;

/**Implementacion de Busqueda en profuncidad, si existen arcos de vuelta, entonces
 * el grafo posee circuitos y no se puede realizar planificacion de proyectos*/
public class DFS {
	
	/**Grafo donde se aplica DFS*/
	public Grafo G;
	/**Contador para ir etiquetando los vertices*/
	public int cnt; 
	/**Booleano que me indica si hay circuito en el grafo*/
	public boolean hayCircuito;
	
	/**Metodo recursivo para ir buscando en profuncdidad, de igual manera
	 * se evalua para cada lado si es de vuelta, en caso de serlo, se rompe
	 * la recursion y se le asigna a hayCiruito true*/
	private void dfsR(Lado e) { 
		Vertice w = e.vertFin(); 
		((Actividad)w).modBusq(cnt++);
		LinkedList<Vertice> A = ((DIGrafo)G).sucesores(w);
		for (int t=0;t<A.size();t++) { 
			Vertice y=A.get(t);
			Lado x = new Arco("",w,y,0);
			if ( ((Actividad)y).numBusq == -1) dfsR(x);
			else if (((Actividad)y).prede == null) {
				hayCircuito=true;
				break;
			}
			else continue;
        }
		((Actividad)w).modPrede(e.vertIni());
    }
  
	/**Constructor de la clase, inicia la busqueda en profuncidad*/
	DFS(Grafo G, Vertice v)  { 
		this.G = G;  
		cnt = 0; 
		hayCircuito=false;
		for (int t = 0; t < G.Vertices().size(); t++) { 
			((Actividad)G.Vertices().get(t)).modBusq(-1); 
			((Actividad)G.Vertices().get(t)).modPrede(null); 
		}
		for (int t = 0; t < G.Vertices().size(); t++)
			if (((Actividad)G.Vertices().get(t)).numBusq == -1) 
				dfsR(new Arco("",((Actividad)G.Vertices().get(t)),((Actividad)G.Vertices().get(t)),0)); 
    }

}
