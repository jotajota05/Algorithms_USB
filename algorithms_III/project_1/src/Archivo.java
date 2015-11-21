import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**Clase que permite leerun grafo desde un archivo de texto y permite representar un 
 *grafo*/
public class Archivo {

	/**Campo identificador del archivo que se va a leer*/
	public String nomArchivo;
	
	/**Constructor de la clase*/
	public Archivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}
	
	/**Metodo para leer un grafo de un archivo de texto*/
	public Grafo leerGrafo()  {
		Grafo G=null;
		int i;
		int n=0;
		int l=0;
		int p;
		String id, vertI, vertF,vert;
		boolean digrafo = false;
		Lado lado;
		Vertice v, w;

		try {
		// Se pasa el nombre del archivo por la linea de comando
		BufferedReader in = new BufferedReader( new FileReader(nomArchivo) );
		String s;
		String[] tok;
			
		// Se lee si el grafo es dirigido o no 
		if( (s = in.readLine()) != null ){
		    tok = s.split("\\b\\s");
		    System.out.println( tok[0].trim() );
		    if( tok[0].trim().equals("d") )
			digrafo = true;
		}
		
		if (digrafo) G=new DIGrafo();
		else G=new NDGrafo();
		
		// Se lee el numero de nodos
		if( (s = in.readLine()) != null ){
		    tok = s.split("\\b\\s");
		    n = Integer.valueOf( tok[0].trim() ).intValue();
		    System.out.println( tok[0].trim() );
		}
		
		// Se lee el numero de lados
		if( (s = in.readLine()) != null ){
		    tok = s.split("\\b\\s");
		    l = Integer.valueOf( tok[0].trim() ).intValue();
		    System.out.println( tok[0].trim() );
		}
		
		//Se lee la lista de vertices
		for( i = 0; i < n; i++ ){
		    if( (s = in.readLine()) != null ){
			tok = s.split("\\b\\s");
			System.out.println( tok[0].trim()+" ");
			vert = tok[0].trim();
			v=new Vertice(vert);
			G.agregarVertice(v);
		    }
		}
		
		// Se leen los lados del grafo
		for( i = 0; i < l; i++ ){
		    if( (s = in.readLine()) != null ){
			tok = s.split("\\b\\s");
			System.out.println( tok[0].trim()+" "+tok[1].trim()
								+" "+tok[2].trim()+" "+tok[3].trim());
			vertI = tok[0].trim();
			vertF = tok[1].trim();
			id = tok[2].trim();
			p = (new Integer(tok[3].trim())).intValue();
			v=new Vertice(vertI);
			w=new Vertice(vertF);
			if (digrafo) lado=new Arco(id,v,w,p);
			else lado=new Arista(id,v,w,p);
			G.agregarLado(lado,true);
		    }
		}
		System.out.println( "\n***Fin de la lectura del archivo " +nomArchivo+"***\n" );
		in.close();
		
	    }
		catch(IOException e) {
			System.out.println("\nArchivo no encontrado,verifique el nombre!");
		}
		return G;
	}
	
	/**Metodo para mostrar la representacion de los vertices con 
	 * sus respectivos vertices adyacentes*/
	static void show( Grafo G ){
		System.out.println("Representacion del Grafo:\n");
		LinkedList<Vertice> A=G.Vertices();
		for (int s = 0; s < A.size(); s++){
		    System.out.print(A.get(s).id + ": ");
		    LinkedList<Vertice> B = G.adyacentes(A.get(s));
		    for (int t = 0; t<B.size(); t++)
		    	if (G.esLado(A.get(s),B.get(t))) System.out.print(B.get(t).id + " ");
		    System.out.println("");
		}
		System.out.println("");
	}
       
	/**Metodo que representa un grafo en forma de matriz de adyacencias*/
	static void showMatrix( Grafo G ){ 
		System.out.println("\nMatriz de adyacencias\n");
		System.out.print("   ");
		LinkedList<Vertice> A=G.Vertices();
		for (int s=0;s < A.size();s++) System.out.print(A.get(s).id+" ");
		System.out.println("");
		for (int i=0;i < A.size()+2;i++) System.out.print("- ");
		System.out.println("");
		for (int s=0;s<A.size();s++){
			System.out.print(A.get(s).id+"| ");
			for(int j=0;j<A.size();j++) {
				if(G.esLado(A.get(s), A.get(j))) System.out.print("1 ");
				else System.out.print("0 ");
			}
			System.out.println("");
		}
    }
}
