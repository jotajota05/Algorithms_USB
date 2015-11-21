import java.util.*;

/**Clase hija de Grafo, la cual representa un grafo no dirigido*/
public class NDGrafo extends Grafo {
	
	/**Campo del tipo de grafo*/
	public String id;
	/**Campo de la lista de vertices del grafo*/
	public LinkedList<Vertice> cjtoVertices;
	/**Campo de la lista de lados del grafo*/
	public LinkedList<Lado> cjtoLados;
	
	/**Constructor de la clase*/
	public NDGrafo() {
		id = "NDgrafo";
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
			existe=v.id.equals(cjtoVertices.get(i).id);
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
		if (!pertenece) 
			System.out.println("El vertice seleccionado no pertenece al grafo.");
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
						cjtoLados.get(i).vertFin().id.equals(l.vertFin().id))||
					   (cjtoLados.get(i).vertIni().id.equals(l.vertFin().id)&&
						cjtoLados.get(i).vertFin().id.equals(l.vertIni().id));
			if (!existe) cjtoLados.addFirst(l);
			else if (!cargado) System.out.println("\nEsta Arista ya existe en el grafo");
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
					System.out.println("\nEsta Arista no existe en el grafo");
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
		int opcion=Console.readInt("Indique el numero del dato que desea modificar: \n");
		String nuevoDato;
		int nuevoPeso;
		Lado ladoModificado = null;
		boolean repetido = false;
		boolean encontrado = false;
		int i = 0;
		Vertice nuevoVert = null;
		switch (opcion){
			case 1:
				nuevoDato = Console.readString("Indique el nuevo identificador: ");
				ladoModificado = new Arista(nuevoDato,l.vertIni(),l.vertFin(),l.peso());
				System.out.println("id lado modificado: "+ladoModificado.identificador());
			break;
			case 2:
				nuevoDato = Console.readString("Indique el identificador del nuevo Vertice Inicial: ");
				nuevoVert = new Vertice(nuevoDato);
				for (i=0;i<cjtoVertices.size()&&!encontrado;i++){
					if (cjtoVertices.get(i).id.equals(nuevoVert.id)) {
						encontrado = true;
						for (int j=0;j<this.incidentes(l.vertFin()).size()&&!repetido;j++)
							repetido=this.incidentes(l.vertFin()).get(j).vertIni().id.equals(nuevoDato)||
									 this.incidentes(l.vertFin()).get(j).vertFin().id.equals(nuevoDato);
					}
				}
				if (encontrado){
					if (repetido)System.out.println("Lado repetido.");
					else {
						System.out.println(nuevoVert.id);
						ladoModificado = new Arista(l.identificador(),nuevoVert,l.vertFin(),l.peso());
					}	
				}
				else System.out.println("El vertice que introdujo no pertenece al grafo.");
			break;
			case 3:	
				nuevoDato = Console.readString("Indique el identificador del nuevo Vertice Final: ");
				nuevoVert = new Vertice(nuevoDato);
				for (i=0;i<cjtoVertices.size()&&!encontrado;i++){
					if (cjtoVertices.get(i).id.equals(nuevoVert.id)) {
						encontrado = true;
						for (int j=0;j<this.incidentes(l.vertIni()).size()&&!repetido;j++)
							repetido=this.incidentes(l.vertIni()).get(j).vertIni().id.equals(nuevoDato)||
									 this.incidentes(l.vertIni()).get(j).vertFin().id.equals(nuevoDato);
					}
				}
				if (encontrado){
					if (repetido)System.out.println("Lado repetido.");
					else {
						System.out.println(nuevoVert.id);
						ladoModificado = new Arista(l.identificador(),nuevoVert,l.vertFin(),l.peso());
					}	
				}
				else System.out.println("El vertice que introdujo no pertenece al grafo.");
			break;	
			case 4:
				nuevoPeso = Console.readInt("Indique el nuevo peso: ");
				ladoModificado = new Arista(l.identificador(),l.vertIni(),l.vertFin(),nuevoPeso);
			break;
			default:
				System.out.print("La opcion no es valida.");
		}
		if (ladoModificado!=null){
		i=cjtoLados.indexOf(l);
		cjtoLados.remove(i);
		agregarLado(ladoModificado,false);
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
	
}
