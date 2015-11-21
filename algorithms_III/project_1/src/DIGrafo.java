import java.util.*;

/**Clase hija de Grafo, la cual representa un grafo dirigido*/
public class DIGrafo extends Grafo {
	
	/**Campo del tipo de grafo*/
	public String id;
	/**Campo de la lista de vertices del grafo*/
	public LinkedList<Vertice> cjtoVertices;
	/**Campo de la lista de lados del grafo*/
	public LinkedList<Lado> cjtoLados;
	
	/**Constructor de la clase*/
	public DIGrafo() {
		id = "Digrafo";
		cjtoVertices = new LinkedList<Vertice>();
		cjtoLados = new LinkedList<Lado>();
	}
	
	/**Metodo que devuelve el campo id del grafo con el q estamos trabajando*/
	public String Tipo() {
		return id;
	}
	
	/**Metodo que dado un vertice, agraga dicho vertice en el grafo.
	 * De ya existir el vertice en el grafo, no lo agrega*/
	public Grafo agregarVertice(Vertice v){
		boolean existe=false;
		for(int i=0;i<cjtoVertices.size()&&!existe;i++) 
			existe=v.id.equals(cjtoVertices.get(i).id);
		if (!existe) cjtoVertices.addFirst(v);
		else System.out.println("\nEste vertice ya existe en el grafo");
		return this;
	}
	
	/**Metodo que dado un vertice, procede a eliminarlo del grafo.
	 * De no existir el vertice dentro del grafo, da un msj de error al usuario*/
	public Grafo eliminarVertice(Vertice v) {
		boolean existe=false;
		for(int i=0;i<cjtoVertices.size()&&!existe;i++) {
			existe = cjtoVertices.get(i).id.equals(v.id);
			if (existe) {
				cjtoVertices.remove(i);
				LinkedList<Lado> list=incidentes(v);
				for(int j=0;j<list.size();j++) eliminarLado(list.get(j));
			}
			else if (i==cjtoVertices.size()-1)
				System.out.println("\nEste vertice NO existe en el grafo");
	}
		return this;
	}
	
	/**Metodo que permite modificar los campos de un vertice, y por
	 * consecuencia, todos los lados relacionados a el mismo*/
	public Grafo modificarVertice(Vertice v) {
		boolean pertenece = false;
		int i = 0;
		for (i=0;i<cjtoVertices.size()&&!pertenece;i++)
			pertenece = cjtoVertices.get(i).id.equals(v.id);
		if (!pertenece) System.out.println("El vertice seleccionado no pertenece al grafo.");
		else {
			Vertice nuevoVertice = new Vertice(Console.readString("Introduzca el nuevo identificador para el vertice seleccionado: ")); 
			cjtoVertices.remove(i-1);
			cjtoVertices.add(i-1,nuevoVertice);
			Lado ladoModificado = null;
			for (int j=0;j<cjtoLados.size();j++){
				if (cjtoLados.get(j).vertIni().id.equals(v.id)){
					ladoModificado = new Arco(cjtoLados.get(j).identificador(),nuevoVertice,cjtoLados.get(j).vertFin(),cjtoLados.get(j).peso());
					cjtoLados.remove(j);
					cjtoLados.add(j,ladoModificado);
					}
			}
			for (int k=0;k<cjtoLados.size();k++){	
				if (cjtoLados.get(k).vertFin().id.equals(v.id)){
					ladoModificado = new Arco(cjtoLados.get(k).identificador(),cjtoLados.get(k).vertIni(),nuevoVertice,cjtoLados.get(k).peso());
					cjtoLados.remove(k);
					cjtoLados.add(k,ladoModificado);
				}
			}
		}
		return this;
	}
	
	/**Metodo que devuelve la lista de vertices de un grafo*/
	public LinkedList<Vertice> Vertices() {
		return cjtoVertices;
	}
	
	/**Metodo que devuelve la lista de vertices adyacentes a un vertice dado*/
	public LinkedList<Vertice> adyacentes(Vertice v) {
		LinkedList<Vertice> list2 = new LinkedList<Vertice>();
		for(int i=0;i<Vertices().size();i++) {
			if (esLado(v,Vertices().get(i))||esLado(Vertices().get(i),v)) {
				list2.addFirst(Vertices().get(i));
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
			if (cjtoLados.get(i).vertFin().id.equals(v.id)) gradoInt++;
		}	
		return gradoInt;
	}
	
	/**Metodo que devuleve el grado externo de un vertice dado*/
	public int gradoExt(Vertice v) {
		int gradoExt = 0;
		for (int i = 0;i<cjtoLados.size();i++){
			if (cjtoLados.get(i).vertIni().id.equals(v.id)) gradoExt++;
		}	
		return gradoExt;
	}
	
	/**Metodo que dado unlado, agraga dicho lado en el grafo.
	 * De ya existir el lado en el grafo, no lo agrega*/
	public Grafo agregarLado(Lado l,boolean cargado) {
		boolean existe=false;
		boolean existeVi=false;
		boolean existeVf=false;
		for(int j=0;j<cjtoVertices.size()&&!existeVi;j++) 
			existeVi=cjtoVertices.get(j).id.equals(l.vertIni().id);
		if (existeVi)
			for(int j=0;j<cjtoVertices.size()&&!existeVf;j++)
				existeVf=cjtoVertices.get(j).id.equals(l.vertFin().id);
		if (existeVf) {	
			for(int i=0;i<cjtoLados.size()&&!existe;i++)
				existe=cjtoLados.get(i).identificador().equals(l.identificador())||
					   (cjtoLados.get(i).vertIni().id.equals(l.vertIni().id)&&
						cjtoLados.get(i).vertFin().id.equals(l.vertFin().id));
			if (!existe) cjtoLados.addFirst(l);
			else if (!cargado) System.out.println("\nEste arco ya existe en el grafo");
		}
		else System.out.println("Alguno de los vertices no pertenece al grafo");
		return this;
	}
	
	/**Metodo que dado un lado, procede a eliminarlo del grafo.
	 * De no existir el lado dentro del grafo, da un msj de error al usuario*/
	public Grafo eliminarLado(Lado l) {
		boolean existe=false;
		for(int j=0;j<cjtoLados.size()&&!existe;j++) {
			existe=cjtoLados.get(j).identificador().equals(l.identificador())||
			   	   (cjtoLados.get(j).vertIni().id.equals(l.vertIni().id)&&
					cjtoLados.get(j).vertFin().id.equals(l.vertFin().id))||
				   (cjtoLados.get(j).vertIni().id.equals(l.vertFin().id)&&
					cjtoLados.get(j).vertFin().id.equals(l.vertIni().id));
			if (existe) cjtoLados.remove(j);
			else if (j==cjtoLados.size())
				System.out.println("\nEste arco no existe en el grafo");
		}
		return this;
	}
	
	/**Metodo que permite modificar los campos de un lado*/
	public Grafo modificarLado(Lado l) {
		System.out.println("Datos del lado que puede modificar: ");
		System.out.println("1)Identificador");
		System.out.println("2)Vertice inicial");
		System.out.println("3)Vertice final");
		System.out.println("4)Peso");
		int opcion = Console.readInt("Indique el numero del dato que desea modificar: \n");
		String nuevoDato;
		int nuevoPeso;
		Lado ladoModificado = null;
		Vertice nuevoVert = null;
		int i = 0;
		boolean encontrado = false;
		switch (opcion){
			case 1:
				nuevoDato = Console.readString("Indique el nuevo identificador: ");
				ladoModificado = new Arco(nuevoDato,l.vertIni(),l.vertFin(),l.peso());
				break;
			case 2:
				nuevoVert = new Vertice(Console.readString("Indique el identificador del nuevo Vertice Inicial: "));
				for (i=0;i<cjtoVertices.size()&&!encontrado;i++)
					encontrado = cjtoVertices.get(i).id.equals(nuevoVert.id);
				if (encontrado)
					ladoModificado = new Arco(l.identificador(),nuevoVert,l.vertFin(),l.peso());	
				else 
					System.out.println("El vertice que introdujo no pertenece al grafo.");
				break;
				
			case 3:	
				nuevoVert = new Vertice(Console.readString("Indique el identificador del nuevo Vertice Final: "));
				for (i=0;i<cjtoVertices.size()&&!encontrado;i++)
					encontrado=cjtoVertices.get(i).id.equals(nuevoVert.id);					
				if (encontrado)
					ladoModificado = new Arco(l.identificador(),l.vertIni(),nuevoVert,l.peso());	
				else 
					System.out.println("El vertice que introdujo no pertenece al grafo.");
				break;
				case 4:
				nuevoPeso = Console.readInt("Indique el nuevo peso: ");
				ladoModificado = new Arco(l.identificador(),l.vertIni(),l.vertFin(),nuevoPeso);
				break;
			default: System.out.print("La opcion no es valida.");
		}
		if (ladoModificado!=null){
		i=this.Lados().indexOf(l);
		this.Lados().remove(i);
		this.Lados().add(i,ladoModificado);
		}
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
			if (Lados().get(i).vertIni().id.equals(v.id)||
				Lados().get(i).vertFin().id.equals(v.id))
				ladosInc.addFirst(Lados().get(i));
		}
	
		return ladosInc;
	}
	
	/**Metodo que determina, dado dos vertices, si forman un lado*/
	public boolean esLado(Vertice v,Vertice w) {
		boolean b=false;
		LinkedList<Lado> list=Lados();
		for(int i=0;i<list.size()&&!b;i++) {
			Lado l=list.get(i);
			if (l.vertIni().id.equals(v.id)&&l.vertFin().id.equals(w.id))
				b=true;
		}
		return b;
	}
	
	/**Metodo que devuelve la lista de vertices sucesores a un vertice*/
	public LinkedList<Vertice> sucesores(Vertice v) {
		LinkedList<Vertice> sucesores = new LinkedList<Vertice>();
		for(int i=0;i<this.incidentes(v).size();i++)
			if (this.incidentes(v).get(i).vertIni().id.equals(v.id))
				sucesores.addFirst(this.incidentes(v).get(i).vertFin());
		return sucesores;
	}
	
	/**Metodo qye devuelve la lista de vertices predecesores a un vertice*/
	public LinkedList<Vertice> predecesores(Vertice v) {
		LinkedList<Vertice> predecesores = new LinkedList<Vertice>();
		for(int i=0;i<this.incidentes(v).size();i++)
			if (this.incidentes(v).get(i).vertFin().id.equals(v.id))
				predecesores.addFirst(this.incidentes(v).get(i).vertIni());
		return predecesores;
	}
	
}
