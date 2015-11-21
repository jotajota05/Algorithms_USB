public class Lvac<T> extends Lista<T> {

	public T obtener(int k) {
		return null;
	}
	
	public int length() {
		return(0);
	}
	
	public Lista<T> inserta(int k,T t) {
		return new Cons<T>(t, new Lvac<T>(), k);
	}
	
	public Lista<T> insertaEnOrden(int k,T t) {
		return new Cons<T>(t, new Lvac<T>(), k);
	}
	
	public Lista<T> elimina(int k) {
		return new Lvac<T>();
	}
	
	public int buscar(T t) {
		return(0);
	}
	
	public boolean esVacia() {
		return true;
	}
	
	public T obtObjeto() {
		return null;
	}
	
	public Lista<T> obtLista() {
		return null;
	}
	
	public int obtRango() {
		return(0);	
	}
	
	public Lista<T> concatenar(Lista<T> lp,Lista<T> ls) {
		return new Lvac<T>();
	}
	
	public void escribir() {
		System.out.println("La  lista no tiene elementos");
	}
	
	public Lista<T> invOrden() {
		return new Lvac<T>();
	}
}