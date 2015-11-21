import java.util.*;

/**Clase hija de Grafo, la cual representa un grafo dirigido*/
public class DIGrafo extends Grafo {
	
	/**Campo del tipo de grafo*/
	public String id;
	/**Campo de la lista de vertices con sus respectivas adyacencias del grafo*/
	public LinkedList<LinkedList<Vertice>> ListAdy;
	/**Campo de la lista de lados del grafo*/
	public LinkedList<Lado> cjtoLados;
	/**Vertices inicial y final de donde se van a relaizar la busqueda*/
	public String VertIni,VertFin;
	
	/**Constructor de la clase*/
	public DIGrafo() {
		id = "Digrafo";
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
	
	/**Metodo que permite modificar los campos de un vertice, y por
	 * consecuencia, todos los lados relacionados a el mismo*/
	public Grafo modificarVertice(Vertice v) {
		boolean pertenece = false;
		int i = 0;
		for (i=0;i<ListAdy.size()&&!pertenece;i++)
			pertenece = ListAdy.get(i).getFirst().obtID().equals(v.obtID());
		if (!pertenece) System.out.println("El vertice seleccionado no pertenece al grafo.");
		else {
			Vertice nuevoVertice = new Actividad(Console.readString("Introduzca el nuevo identificador para el vertice seleccionado: ")); 
			ListAdy.get(i).remove(i-1);
			ListAdy.get(i).add(i-1,nuevoVertice);
			Lado ladoModificado = null;
			for (int j=0;j<cjtoLados.size();j++){
				if (cjtoLados.get(j).vertIni().obtID().equals(v.obtID())){
					ladoModificado = new Arco(cjtoLados.get(j).obtID(),nuevoVertice,cjtoLados.get(j).vertFin(),cjtoLados.get(j).costo());
					cjtoLados.remove(j);
					cjtoLados.add(j,ladoModificado);
					}
			}
			for (int k=0;k<cjtoLados.size();k++){	
				if (cjtoLados.get(k).vertFin().obtID().equals(v.obtID())){
					ladoModificado = new Arco(cjtoLados.get(k).obtID(),cjtoLados.get(k).vertIni(),nuevoVertice,cjtoLados.get(k).costo());
					cjtoLados.remove(k);
					cjtoLados.add(k,ladoModificado);
				}
			}
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
	
	/**Metodo que devuleve el grado interno de un vertice dado*/
	public int gradoInt(Vertice v) {
		int gradoInt = 0;
		for (int i = 0;i<cjtoLados.size();i++){
			if (cjtoLados.get(i).vertFin().obtID().equals(v.obtID())) gradoInt++;
		}	
		return gradoInt;
	}
	
	/**Metodo que devuleve el grado externo de un vertice dado*/
	public int gradoExt(Vertice v) {
		int gradoExt = 0;
		for (int i = 0;i<cjtoLados.size();i++){
			if (cjtoLados.get(i).vertIni().obtID().equals(v.obtID())) gradoExt++;
		}	
		return gradoExt;
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
		Lado ladoModificado = new Arco(l.obtID(),nuevoVi,nuevoVf,nuevoP);;
		agregarLado(ladoModificado);
		return this;
	}
	
	/**Metodo que devuelve la lista de lados de un grafo*/
	public LinkedList<Lado> Lados() {
		return cjtoLados;
	}
	
	/**Metodo que calcula la suma del costo de todos los lados del grafo.*/
	public double sumaLados(){ 
		double sumatoria = 0;
		for (int i=0;i<cjtoLados.size();i++)
		sumatoria = sumatoria + cjtoLados.get(i).costo();
		return sumatoria;
	}
	
	/**Metodo que devuleve la lista de lados incidentes a un vertice dado*/
	public LinkedList<Lado> incidentes(Vertice v) {
		LinkedList<Lado> ladosInc = new LinkedList<Lado>();
		for (int i = 0;i<Lados().size();i++){
			if (Lados().get(i).vertIni().obtID().equals(v.obtID())||
				Lados().get(i).vertFin().obtID().equals(v.obtID()))
				ladosInc.addLast(Lados().get(i));
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
	
	/**Metodo que devuelve la lista de vertices sucesores a un vertice*/
	public LinkedList<Vertice> sucesores(Vertice v) {
		LinkedList<Vertice> sucesores = new LinkedList<Vertice>();
		for(int i=0;i<this.incidentes(v).size();i++)
			if (this.incidentes(v).get(i).vertIni().obtID().equals(v.obtID()))
				sucesores.addFirst(this.incidentes(v).get(i).vertFin());
		return sucesores;
	}
	
	/**Metodo qye devuelve la lista de vertices predecesores a un vertice*/
	public LinkedList<Vertice> predecesores(Vertice v) {
		LinkedList<Vertice> predecesores = new LinkedList<Vertice>();
		for(int i=0;i<this.incidentes(v).size();i++)
			if (this.incidentes(v).get(i).vertFin().obtID().equals(v.obtID()))
				predecesores.addLast(this.incidentes(v).get(i).vertIni());
		return predecesores;
	}
	
	/**Metodo que devuelve un verice del grafo, introduciendo el identificador
	 * especifico*/
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
	
	/**Metodo que, dado un vertice, determina la lista de lados cuyo extremo final
	 * es ese vertice*/
	public LinkedList<Lado> obtListaLados(Vertice v) {
		LinkedList<Lado> lados = new LinkedList<Lado>();
		for(int i=0;i<Lados().size();i++){
			if (Lados().get(i).vertFin().equals(v))
				lados.add(Lados().get(i));
		}		
		return lados;		
	}
	
	/**Metodo que permite modificar el vertice inicial de la busqueda*/
	public void modVertIni(String v) {
		VertIni=v;
	}
	
	/**Metodo que permite modificar el vertice final de la busqueda*/
	public void modVertFin(String w) {
		VertFin=w;
	}
	
}
