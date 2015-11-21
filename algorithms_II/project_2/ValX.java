
public class ValX extends ValCasilla {

	String val;
	
	public ValX (String X) {
		val=X;
	}
	
	public ValCasilla inserta(String x) {
		return null;
	}
	
	public ValCasilla elimina() {
		return new ValB();
	}
	
	public boolean esVacia() {
		return false;
	}
	
	public String obtener() {
		return("X");
	}
	
	public ValCasilla oponente() {
		return new ValO("O");
	}
	
}