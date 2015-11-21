import java.util.*;

/**Clase que calcula los TEC y TAC de una actividad*/
public class TECTAC {

	/**Constructor de la clase*/
	public TECTAC(Grafo G,Vertice s,Vertice t) {
		TAC(G,s,t);
	}
	
	public void TAC(Grafo G,Vertice s,Vertice t) {
		for (int i=0;i<G.Vertices().size();i++) 
			((Actividad)G.Vertices().get(i)).modVisitado(false);
		for (int i=0;i<G.Vertices().size();i++) 
			((Actividad)G.Vertices().get(i)).modTAC(Double.POSITIVE_INFINITY);
		Busqueda(G,s,t);
	}
	
	public void Busqueda(Grafo G,Vertice x,Vertice t) {
		((Actividad)x).modVisitado(true);
		if (x.obtID().equals(t.obtID())) ((Actividad)x).modTAC(((Actividad)x).TEC);
		else {
			LinkedList<Vertice> LSX=((DIGrafo)G).sucesores(x);
			for (int i=0;i<LSX.size();i++) {
				Vertice y=LSX.get(i);
				if (!((Actividad)y).visitado) Busqueda(G,y,t);
			}
			for (int i=0;i<LSX.size();i++) {
				Vertice y=LSX.get(i);
				if (((Actividad)y).TAC!=Double.POSITIVE_INFINITY && 
				((Actividad)x).TAC > ((Actividad)y).TAC - ((Arco)G.obtLado(x,y)).costo)
				((Actividad)x).modTAC(((Actividad)y).TAC - ((Arco)G.obtLado(x,y)).costo);
			}
		}
	}
}
