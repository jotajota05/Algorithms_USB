import java.io.IOException;

/**Clase cliente para probar las operaciones de un grafo*/
public class GrafoCliente {

	/**Metodo que muestra el menu principal de las operaciones de un grafo*/
	public static Grafo menuPrincipal(Grafo G,boolean digrafo) {
		for(int j=0;j<1000;j++) {
			int opcion3=0;
			System.out.println("\nAhora que desea hacer?\n");
			System.out.println("1)Agregar vertice");
			System.out.println("2)Eliminar vertice");
			System.out.println("3)Modificar vertice");
			System.out.println("4)Agregar lado");
			System.out.println("5)Eliminar lado");
			System.out.println("6)Modificar lado");
			System.out.println("7)Mostrar representaciones del grafo");
			System.out.println("8)Conocer numero de vertices");
			System.out.println("9)Conocer numero de lados");
			System.out.println("10)Otras acciones sobre el grafo");
			System.out.println("11)Salir del Menu");
			opcion3=Console.readInt("Opcion: ");
			switch (opcion3) {
			case 1 :
				Vertice vi=new Vertice(Console.readString("\nId. del vertice: "));
				G.agregarVertice(vi);
			break;
			case 2 :
				Vertice ve=new Vertice(Console.readString("\nId. del vertice: "));
				G.eliminarVertice(ve);
			break;
			case 3 :
				Vertice vm=new Vertice(Console.readString("\nId. del vertice: "));
				G.modificarVertice(vm);
			break;
			case 4 :
				Lado li;
				String id=Console.readString("\nId. del lado: ");
				Vertice vini=new Vertice(Console.readString("Id. del vertice inicial : "));
				Vertice vfin=new Vertice(Console.readString("Id. del vertice final : "));
				int peso=Console.readInt("Peso del lado: ");
				if (digrafo) li=new Arco(id,vini,vfin,peso);
				else li=new Arista(id,vini,vfin,peso);
				G.agregarLado(li,false);
			break;
			case 5 :
				Lado l=null;
				boolean b=false;
				String idl=Console.readString("\nId. del lado: ");
				for(int i=0;i<G.Lados().size()&&!b;i++) {
					b=G.Lados().get(i).identificador().equals(idl);
					l=G.Lados().get(i);
				}
				if (b) G.eliminarLado(l);
				else {
					if (digrafo) System.out.println("\nEste arco no existe en el grafo");
					else System.out.println("\nEsta arista no existe en el grafo");
				}
			break;
			case 6 :
				Lado l0=null;
				boolean r=false;
				String idlm=Console.readString("\nId. del lado: ");
				for(int i=0;i<G.Lados().size()&&!r;i++) {
					r=G.Lados().get(i).identificador().equals(idlm);
					l0=G.Lados().get(i);
				}
				if (r) G.modificarLado(l0);
				else {
					if (digrafo) System.out.println("\nEste arco no existe en el grafo");
					else System.out.println("\nEsta arista no existe en el grafo");
				}
			break;
			case 7 :
				Archivo.show(G);
				Archivo.showMatrix(G);
			break;
			case 8 : System.out.println("\nEl numero de vertices es: "+G.Vertices().size());
			break;
			case 9 : System.out.println("\nEl numero de lados es: "+G.Lados().size());
			break;
			case 10 :
				for(int k=0;k<1000;k++) {
					int opcion4=0;
					System.out.println("\nAhora que desea hacer?\n");
					System.out.println("1)Saber el tipo de grafo");
					System.out.println("2)Imprimir lista de vertices del grafo");
					System.out.println("3)Imprimir lista de vertices adyacentes a un vertice");
					System.out.println("4)Conocer grado de un vertice");
					System.out.println("5)Conocer el peso de un lado");
					System.out.println("6)Imprimir lista de lados del grafo");
					System.out.println("7)Imprimir lista de lados incidentes a un vertice");
					System.out.println("8)Acciones sobre un digrafo");
					System.out.println("9)Salir del Menu");
					opcion4=Console.readInt("Opcion: ");
					switch (opcion4) {
					case 1 :
						System.out.println("\nEl grafo es un "+G.Tipo());
					break;
					case 2 :
						System.out.print("\nLista de vertices del grafo: ");
						for(int i=0;i<G.Vertices().size();i++) 
							System.out.print(G.Vertices().get(i).id+" ");
					break;
					case 3 :
						Vertice v0=null;
						String v=Console.readString("\nId. del vertice: ");
						boolean p=false;
						for(int i=0;i<G.Vertices().size()&&!p;i++) {
							p=G.Vertices().get(i).id.equals(v);
							v0=G.Vertices().get(i);
						}
						if (p){
							System.out.print("\nLista de vertices adyacentes a "+v+": ");
							for(int i=0;i<G.adyacentes(v0).size();i++) 
									System.out.print(G.adyacentes(v0).get(i).id+" ");
						}
						else System.out.println("\nEste vertice no existe o no tiene vertices adyacentes");
					break;
					case 4 :
						Vertice vg=new Vertice(Console.readString("\nId. del vertice: "));
						System.out.println("\nEl grado del vertice "+vg.id+" es: "+G.grado(vg));
					break;
					case 5 :
						Lado lp=null;
						boolean s=false;
						String idlp=Console.readString("\nId. del lado: ");
						for(int i=0;i<G.Lados().size()&&!s;i++) {
							s=G.Lados().get(i).identificador().equals(idlp);
							lp=G.Lados().get(i);
						}
						if (s) System.out.println("\nEl peso del lado "+lp.identificador()+" es: "+lp.peso());
						else System.out.println("\nEse lado no pertenece al grafo");
					break;
					case 6 :
						System.out.print("\nLista de lados del grafo: ");
						for(int i=0;i<G.Lados().size();i++) 
							System.out.print(G.Lados().get(i).identificador()+" ");
					break;
					case 7 :
						Vertice v1=null;
						String vin=Console.readString("\nId. del vertice: ");
						boolean q=false;
						for(int i=0;i<G.Vertices().size()&&!q;i++) {
							q=G.Vertices().get(i).id.equals(vin);
							v1=G.Vertices().get(i);
						}
						if (q){
							System.out.print("\nLista de lados incidentes a "+vin+" : ");
							for(int i=0;i<G.incidentes(v1).size();i++) 
									System.out.print(G.incidentes(v1).get(i).identificador()+" ");
						}
						else System.out.println("\nEste vertice no existe o no tiene lados incidentes");
					break;
					case 8 :
						if (digrafo) {
							for(int n=0;n<1000;n++) {
								int opcion5=0;
								System.out.println("\nAhora que desea hacer?\n");
								System.out.println("1)Conocer grado interior de un vertice");
								System.out.println("2)COnocer grado exterior de un vertice");
								System.out.println("3)Imprimir lista de vertices sucesores a un vertice");
								System.out.println("4)Imprimir lista de vertices predecesores a un vertice");
								System.out.println("5)Salir del menu");
								opcion5=Console.readInt("Opcion: ");
								switch (opcion5) {
								case 1 :
									DIGrafo G0=(DIGrafo)G;
									Vertice vgi=new Vertice(Console.readString("\nId. del vertice: "));
									System.out.println("\nEl grado interno del vertice "+vgi.id+" es: "+G0.gradoInt(vgi));
								break;
								case 2 :
									DIGrafo G1=(DIGrafo)G;
									Vertice vge=new Vertice(Console.readString("\nId. del vertice: "));
									System.out.println("\nEl grado externo del vertice "+vge.id+" es: "+G1.gradoExt(vge));
								break;
								case 3 :
									DIGrafo G2=(DIGrafo)G;
									Vertice vgs=new Vertice(Console.readString("\nId. del vertice: "));
									System.out.print("\nLista de vertices sucesores a "+vgs.id+" :");
									for(int i=0;i<G2.sucesores(vgs).size();i++) 
									System.out.print(G2.sucesores(vgs).get(i).id+" ");
								break;
								case 4 :
									DIGrafo G3=(DIGrafo)G;
									Vertice vgp=new Vertice(Console.readString("\nId. del vertice: "));
									System.out.print("\nLista de vertices predecesores a "+vgp.id+" :");
									for(int i=0;i<G3.predecesores(vgp).size();i++) 
									System.out.print(G3.predecesores(vgp).get(i).id+" ");
								break;
								case 5 : n=1000;
								break;
								default : System.out.println("\nOpcion no valida");
								}
							}
						}
						else System.out.println("\nEstas operaciones son sobre DIgrafos no sobre NDGrafos");
					break;
					case 9 : k=1000;
					break;
					default : System.out.println("\nOpcion no valida");
					}
				}
			break;
			case 11 : j=1000;
			break;
			default : System.out.println("\nOpcion no valida");
			}
		}
		return G;
	}
	
	/**Metodo Main de la clase*/
	public static void main (String[] args)  throws IOException {
		
		System.out.println("\n\t******TAD GRAFO******\n");
		System.out.println("por:");
		System.out.println("\tTamara Mendt 05-38546");
		System.out.println("\tJuan Garcia 05-38207\n");
		
		Grafo G;
		boolean digrafo;
		
		for(int i=0;i<1000;i++) {
			int opcion=0;
			System.out.println("\nQue desea hacer?\n");
			System.out.println("1)Crear un grafo vacio");
			System.out.println("2)Cargar un grafo de archivo");
			System.out.println("3)Salir del Sistema");
			opcion=Console.readInt("Opcion: ");
			switch (opcion) {
			case 1 :
				int opcion2=0;
				System.out.println("\nQue tipo de grafo desea crear?\n");
				System.out.println("1)Grafo NO dirigido");
				System.out.println("2)DIGrafo");
				opcion2=Console.readInt("Opcion: ");
				switch (opcion2) {
				case 1 : 
					G=new NDGrafo();
					digrafo=false;
					G=menuPrincipal(G,digrafo);
				break;
				case 2 : 
					G=new DIGrafo();
					digrafo=true;
					G=menuPrincipal(G,digrafo);
				break;
				default : System.out.println("\nOpcion no valida");
				}
			break;
			case 2 : 
				Archivo A=new Archivo(Console.readString("\nIndique nombre archivo: "));
				G=A.leerGrafo();
				if (G!=null){
					if (G.Tipo().equals("Digrafo")) digrafo=true;
					else digrafo=false;
					menuPrincipal(G,digrafo);
				}
			break;
			case 3 : i=1000;
			break;
			default : System.out.println("\nOpcion no valida");
			}
		}
	}
}
