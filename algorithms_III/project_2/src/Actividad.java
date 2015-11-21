
/**Calse hija de Vertice que relaciona un vertice con una actividad*/
public class Actividad extends Vertice {

	/**Indentificador de la actividad*/
	public String id;
	/**Costo de la actividad, TEC Y TAC asociados*/
	public double costo,TEC,TAC;
	/**Predecesor correspondiente al vertice*/
	public Vertice prede;
	/**Numero de busqueda y grado interno del vertice*/
	public int numBusq,grado;
	/**Booleano que indica si ha sido visitado*/
	public boolean visitado;
	
	/**Constructor de la clase*/
	public Actividad(String id) {
		this.id=id;
		costo=0;
		TEC=0;
		TAC=0;
		numBusq=-1;
		visitado=false;
	}
	
	/**Metodo que retorna el identificador de la actividad*/
	public String obtID() {
		return id;
	}
	
	/**Metodo que modifica el Costo de la actividad*/
	public void modCosto(double c) {
		costo=c;
	}
	
	/**Metodo que modifica el numero de Busqueda de la actividad*/
	public void modBusq(int b) {
		numBusq=b;
	}
	
	/**Metodo que modifica el predesesor de la actividad*/
	public void modPrede(Vertice v) {
		prede=v;
	}
	
	/**Metodo que modifica si el vertice ha sido visitado o no*/
	public void modVisitado(boolean b) {
		visitado=b;
	}
	
	/**Metodo que modifica el grado del vertice*/
	public void modGrado(int g) {
		grado=g;
	}
	
	/**Metodo que modifica el TEC de la actividad*/
	public void modTEC(double t) {
		TEC=t;
	}
	
	/**Metodo que modifica el TAC de la actividad*/
	public void modTAC(double t) {
		TAC=t;
	}
}
