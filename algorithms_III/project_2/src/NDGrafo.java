import java.util.*;

/**Clase hija de Grafo, la cual representa un grafo no dirigido*/
public class NDGrafo extends Grafo {
	
	/**Campo del tipo de grafo*/
	public String id;
	/**Campo de la lista de vertices del grafo*/
	public LinkedList<LinkedList<Vertice>> ListAdy;
	/**Campo de la lista de lados del grafo*/
	public LinkedList<Lado> cjtoLados;
	
	/**Constructor de la clase*/
	public NDGrafo() {
		id = "NDgrafo";
		ListAdy = new LinkedList<LinkedList<Vertice>>();
		cjtoLados = new LinkedList<Lado>();
	}
	
	/**Metodo que devuelve el campo id del grafo con el q estamos trabajando*/
	public String Tipo() {
		return id;
	}
	
	/**Metodo que dado un vertice, agrega dicho vertice en el grafo.
	 * De ya existir el vertice en el grafo, no lo agrega*/
	public Grafo agregarVertice(Vertice v){
		boolean existe=false;
		for(int i=0;i<ListAdy.size()&&!existe;i++) 
			existe=v.obtID().equals(ListAdy.get(i).getFirst().obtID());
		LinkedList<Vertice> L=new LinkedList<Vertice>();
		L.addFirst(v);
		if (!existe) ListAdy.addLast(L);
		else System.out.println("\nEste vertice ya existe en el grafo");
		return this;
	}
	
	/**Metodo que dado un vertice, procede a eliminarlo del grafo.
	 * De no existir el vertice dentro del grafo, da un msj de error al usuario*/
	public Grafo eliminarVertice(Vertice v) {
		boolean existe=false;
		for(int i=0;i<ListAdy.size()&&!existe;i++) {
			existe = ListAdy.get(i).getFirst().obtID().equals(v.obtID());
			if (existe) {
				ListAdy.remove(i);
				LinkedList<Lado> list=incidentes(v);
				for(int j=0;j<list.size();j++) eliminarLado(list.get(j));
			}
			else if (i==ListAdy.size()-1)
				System.out.println("\nEste vertice NO existe en el grafo");
	}
		return this;
	}
	
	/**Metodo que devuelve la lista de vertices de un grafo*/
	public LinkedList<Vertice> Vertices() {
		LinkedList<Vertice> L=new LinkedList<Vertice>();
		for (int i=0;i<ListAdy.size();i++) 
			L.addLast(ListAdy.get(i).getFirst());
		return L;
	}
	
	/**Metodo que devuelve la lista de vertices adyacentes a un vertice dado*/
	public LinkedList<Vertice> adyacentes(Vertice v) {
		LinkedList<Vertice> list2 = new LinkedList<Vertice>();
		for(int i=0;i<Vertices().size();i++) {
			if (esLado(v,Vertices().get(i))) {
				list2.addLast(Vertices().get(i));
			}
		}
		return list2;
	}
	
	/**Metodo que devuleve el grado de un vertice dado*/
	public int grado(Vertice v) {
		return this.incidentes(v).size();
	}
	
	/**Metodo que dado un lado, agrega dicho lado en el grafo.
	 * De ya existir el lado en el grafo, no lo agrega*/
	public Grafo agregarLado(Lado l) {
		boolean existe=false;
		boolean existeVi=false;
		boolean existeVf=false;
		for(int j=0;j<ListAdy.size()&&!existeVi;j++) 
			existeVi=ListAdy.get(j).getFirst().obtID().equals(l.vertIni().obtID());
		if (existeVi)
			for(int j=0;j<ListAdy.size()&&!existeVf;j++)
				existeVf=ListAdy.get(j).getFirst().obtID().equals(l.vertFin().obtID());
		if (existeVf) {	
			for(int i=0;i<cjtoLados.size()&&!existe;i++)
				existe=cjtoLados.get(i).obtID().equals(l.obtID())||
					   (cjtoLados.get(i).vertIni().obtID().equals(l.vertIni().obtID())&&
						cjtoLados.get(i).vertFin().obtID().equals(l.vertFin().obtID()));
			if (!existe) {
				cjtoLados.addLast(l);
				for(int k=0;k<ListAdy.size();k++) {
					if (ListAdy.get(k).getFirst().obtID().equals(l.vertIni().obtID()))
						ListAdy.get(k).addLast(l.vertFin());
				}
			}
			else System.out.println("\nEste arco ya existe en el grafo");
		}
		else System.out.println("Alguno de los vertices no pertenece al grafo");
		return this;
	}
	
	/**Metodo que dado un lado, procede a eliminarlo del grafo.
	 * De no existir el lado dentro del grafo, da un msj de error al usuario*/
	public Grafo eliminarLado(Lado l) {
		boolean existe=false;
		for(int j=0;j<cjtoLados.size()&&!existe;j++) {
			existe=cjtoLados.get(j).obtID().equals(l.obtID())||
			   	   (cjtoLados.get(j).vertIni().obtID().equals(l.vertIni().obtID())&&
					cjtoLados.get(j).vertFin().obtID().equals(l.vertFin().obtID()))||
				   (cjtoLados.get(j).vertIni().obtID().equals(l.vertFin().obtID())&&
					cjtoLados.get(j).vertFin().obtID().equals(l.vertIni().obtID()));
			if (existe) {
				cjtoLados.remove(j);
				for(int k=0;k<ListAdy.size();k++) {
					if (ListAdy.get(k).getFirst().obtID().equals(l.vertIni().obtID()))
						ListAdy.get(k).remove(l.vertFin());
				}
			}
			else if (j==cjtoLados.size())
				System.out.println("\nEste arco no existe en el grafo");
		}
		return this;
	}
	
	/**Metodo que permite modificar los campos de un lado*/
	public Grafo modificarLado(Lado l,Vertice nuevoVi,Vertice nuevoVf,double nuevoP) {
		eliminarLado(l);
		Lado ladoModificado = null;
		ladoModificado = new Arista(l.obtID(),nuevoVi,nuevoVf,nuevoP);
		agregarLado(ladoModificado);
		return this;
	}
	
	/**Metodo que devuelve la lista de lados de un grafo*/
	public LinkedList<Lado> Lados() {
		return cjtoLados;
	}
	
	/**Metodo que devuleve la lista de lados incidentes a un vertice dado*/
	public LinkedList<Lado> incidentes(Vertice v) {
		LinkedList<Lado> ladosInc = new LinkedList<Lado>();
		for (int i = 0;i<Lados().size();i++){
			if (Lados().get(i).vertIni().obtID().equals(v.obtID())||
				Lados().get(i).vertFin().obtID().equals(v.obtID()))
				ladosInc.addFirst(Lados().get(i));
		}
		return ladosInc;
	}
	
	/**Metodo que determina, dado dos vertices, si forman un lado*/
	public boolean esLado(Vertice v,Vertice w) {

		boolean b=false;
		for (int i=0;i<ListAdy.size()&&!b;i++){
			if (ListAdy.get(i).getFirst().obtID().equals(v.obtID())){
				for (int j=0;j<ListAdy.get(i).size()&&!b;j++){
					if (ListAdy.get(i).get(j).obtID().equals(w.obtID()))
							b = true;
				}
			}
		}
		return b;
	}
	
	/**Metodo que calcula la suma del peso de todos los lados del grafo.*/
	public double sumaLados(){ 
		double sumatoria = 0;
		System.out.println("ciclo nuevo: ");
		for (int i=0;i<cjtoLados.size();i++){	
		sumatoria = sumatoria + cjtoLados.get(i).costo();
		System.out.println("peso: "+cjtoLados.get(i).costo());
		}
		return sumatoria;
	}
	
	public Vertice obtVert(String id) {
		for(int i=0;i<Vertices().size();i++) 
			if (Vertices().get(i).obtID().equals(id)) return Vertices().get(i);
		return null;
	}
	
	/**Metodo que devuelve un lado del grafo, introduciendo los vertices extremos
	 * de ese lado*/
	public Lado obtLado(Vertice v,Vertice w) {
		for(int i=0;i<Lados().size();i++)
			if (Lados().get(i).vertIni().equals(v) && Lados().get(i).vertFin().equals(w))
				return Lados().get(i);
		return null;
	}

}
