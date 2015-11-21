
public class ValB extends ValCasilla {
	
	String val;

	public ValB() {
		val=" ";
	}

	public ValCasilla inserta(String v) {
		ValCasilla valor=new ValB();
		if (v=="X") valor=new ValX(v);
		else if (v=="O") valor=new ValO(v);
		return(valor);
	}
	
	public ValCasilla elimina () {
		return null;
	}
	
	public boolean esVacia() {
		return true;
	}
	
	public String obtener() {
		return(" ");
	}
	
	public ValCasilla oponente() {
		return new ValB();
	}
	
}