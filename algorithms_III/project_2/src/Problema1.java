import java.io.IOException;
import java.util.*;
public class Problema1 {
	
	public DIGrafo G;
	LinkedList<LinkedList<Vertice>> vertices;

	public Problema1(DIGrafo G) {
		
		this.G = G;
		vertices = G.ListAdy;
	}
	
	/**Metodo que recibe dos vertices y determina la distancia
	  euclidiana entre ellos*/
	public double distancia(Vertice v,Vertice w){
		Punto v1 = (Punto)v;
		Punto v2 = (Punto)w;		
		return Math.sqrt(Math.pow(v1.coorX-v2.coorX,2)+Math.pow(v1.coorY-v2.coorY,2));
	}
	
	/**Metodo que dado un grafo retorna la longitud minima de
	 * una cuerda que recorre todos los vertices del grafo.
	 */
	public double longitud() {
		double longitudMinima = 1000000;
		for(int i=0;i<vertices.size();i++){
			G.cjtoLados = new LinkedList<Lado>();
			LinkedList<Vertice> abiertos = new LinkedList<Vertice>();
			LinkedList<Vertice> cerrados = new LinkedList<Vertice>();
			Vertice Vinicial = vertices.get(i).getFirst();
			cerrados.addLast(Vinicial);
			int k = 1;
			for (int j=0;j<vertices.size();j++){
				Vertice Vady = vertices.get(j).getFirst();
				if (Vinicial.obtID()!=Vady.obtID()){
					String ladoID = ""+k;
					k++;
					Lado e = new Arco(ladoID,Vinicial,Vady,distancia(Vinicial,Vady));
					/*Agrega los vertices en la lista de abiertos dependiendo
					 * de su cercania a Vinicial.*/
					int t=0;
					if (abiertos.size()==0){
						abiertos.addFirst(Vady);
					}	
					else {
						while (t<abiertos.size()&&distancia(abiertos.get(t),Vinicial)<e.costo())
							t++;
						abiertos.add(t,Vady);
					}
					G.agregarLado(e);
				}
				
			}
			while (abiertos.size()!=0){
				Vertice Vactual = abiertos.getFirst();
				cerrados.addLast(Vactual);
				abiertos.removeFirst();
				for (int j=0;j<abiertos.size();j++){
					LinkedList<Lado> lado= G.obtListaLados(abiertos.get(j));
					if (lado.getFirst().costo()>distancia(abiertos.get(j),Vactual)){
						String ladoID = lado.getFirst().obtID();
						G.eliminarLado(lado.getFirst());
						Lado l = new Arco(ladoID,Vactual,abiertos.get(j),distancia(abiertos.get(j),Vactual));
						G.agregarLado(l);
					}
				}
			}
			double longitud = G.sumaLados();
			if (longitud<longitudMinima) longitudMinima = longitud;
		}
		return longitudMinima;
		
	}
	
	/**Programa principal que lee un archivo y le aplica el algoritmo que 
	 * resuelve al problema 1.*/
	public static void main (String[] args)  throws IOException {
		Archivo A=new Archivo(args[0]);
		A.leerGrafoProblema1();
	}
}
