import java.io.IOException;

/**Calse cliente que permite encontrar el camino de costo maximo en un proyecto
 * y ademas refleja los tiempos TAC Y TEC  de cada actividad, asi como su holgura*/
public class Problema2 {

	/**Programa principal de problema 2*/
	public static void main(String[] args) throws IOException{
		Archivo arch=new Archivo(args[0]);
		Grafo G=arch.leerGrafoProblema2();
		Vertice Inicial=G.obtVert(((DIGrafo)G).VertIni);
		Vertice Final=G.obtVert(((DIGrafo)G).VertFin);
		System.out.println("\n          ***** SALIDA PROBLEMA 2 *****\n");
		DFS F=new DFS(G,G.obtVert(((DIGrafo)G).VertIni));
		if (F.hayCircuito)  
			System.out.println("Este grafo posee circuitos!! no se puede ejecutar el programa... Adios!");
		else {
			Bellman B=new Bellman(G,Inicial);
			TECTAC T=new TECTAC(G,Inicial,Final);
			System.out.println(" >> Tiempo minimo: "+((Actividad)Final).costo);
			System.out.println("\n >> Camino de costo maximo: \n");
			mostrarCaminoMax(G,Inicial,Final);
			System.out.println("\n\n >> Intervalos de tiempo:\n");
			for (int i=0;i<G.Vertices().size();i++) {
				Vertice V=G.Vertices().get(i);
				System.out.print("     "+V.obtID()+": ");
				System.out.print("TEC: "+((Actividad)V).TEC+" , TAC: "+((Actividad)V).TAC+" , ");
				System.out.println("Holgura: "+(((Actividad)V).TAC - ((Actividad)V).TEC));
			}
		}
		
	}
	
	/**Metodo recursivo que permite imprimir el camino de costo maximo en el grafo
	 * de planificacion de proyectos*/
	public static void mostrarCaminoMax(Grafo G,Vertice v,Vertice w) {
		Vertice Vini=G.obtVert(((DIGrafo)G).VertIni);
		Vertice Vfin=G.obtVert(((DIGrafo)G).VertFin);
		if (((Actividad)w).prede.obtID().equals(Vini.obtID())) 
			System.out.print("  -> "+v.obtID()+" ("+G.obtLado(v,w).obtID()+") "+w.obtID());
		else {
			mostrarCaminoMax(G,v,((Actividad)w).prede);
			System.out.print(" ("+G.obtLado(((Actividad)w).prede,w).obtID()+") "+((Actividad)w).obtID());
		}
		
	}
}
