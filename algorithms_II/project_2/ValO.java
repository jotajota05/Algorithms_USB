
public class ValO extends ValCasilla {

	String val;
	
	public ValO(String O) {
		val=O;
	}
	
	public ValCasilla inserta(String O) {
		return null;
	}
	
	public ValCasilla elimina() {
		return new ValB();
	}
	
	public boolean esVacia() {
		return false;
	}
	
	public String obtener() {
		return("O");
	}
	
	public ValCasilla oponente() {
		return new ValX("X");
	}
	
		
}