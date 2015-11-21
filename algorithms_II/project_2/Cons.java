/**La clase Cons es el constructor de una lista generica para cualquier tipo de dato.*/
public class Cons<T> extends Lista<T> {

	T x;
	Lista<T> y;
	int k;
	
/**Este es el constructor de el tipo lista, recibe como paramatros un objeto,una lista y un rango entero*/	
	public Cons(T x,Lista<T> y,int k) {
		this.x=x;
		this.y=y;
		this.k=k;
	}
	
/**Esta funcion permite,insertando un rango k,obtener el obejto asociado a dicho rango
 * PRe: ingresa una lista y un ango entero k
 * POST:entrega el objeto con rango k de la lista*/	
	public T obtener(int k) {
		Lista<T> l1=this;
		while ((l1 instanceof Cons)&&(k!=l1.obtRango())) {
			l1=l1.obtLista();
		}
		return(l1.obtObjeto());
	}
	
/**Esta funcion arroja el largo de la lista
 * PRE:ingresa una lista
 * POST:entrga en entero que representa el largo del la lista*/	
	public int length() {
		Lista<T> l1=this;
		int c=0;
		while (l1 instanceof Cons) {
			c++;l1=l1.obtLista();
		}
		return(c);
	}
	
/**Esta funcion inserta elementos en la lista en el orden en que son llamados
 * PRE:ingresa un rango y un objeto
 * POST:devuelve una lista con el objeto insertado en la cabeza de la lista*/
	public Lista<T> inserta(int k,T t) {
		return new Cons<T>(t,this,k);
	}
	
/**Esta funcion inserta objetos manteniendo un orden fijo relacionado con el rango k
 * PRE:ingresaun rango k y un objeto
 * POST:devuleve una lista ordenada segun su rango y con el elemento nuevo*/
	public Lista<T> insertaEnOrden(int k,T t){
		Lista<T> l=this;
		Lista<T> min=new Lvac<T>();
		Lista<T> max=new Lvac<T>();
		Lista<T> fin=new Lvac<T>();
		while (l instanceof Cons) { 
			if (l.obtRango()<=k) min=min.inserta(l.obtRango(),l.obtObjeto());
			else max=max.inserta(l.obtRango(),l.obtObjeto());
			l=l.obtLista();
		}
		while (max instanceof Cons) {
			fin=fin.inserta(max.obtRango(),max.obtObjeto());
			max=max.obtLista();
		}
		fin=fin.inserta(k,t);
		while (min instanceof Cons) {
			fin=fin.inserta(min.obtRango(),min.obtObjeto());
			min=min.obtLista();
		}
		return(fin);
	}

/**Esta funcion permite eliminar un objeto de rango k de la lista
 * PRE:ingresa un rango k
 * POST:entrega una lista sin el elemnto de rango k*/
	public Lista<T> elimina(int k) {
		Lista<T> l0=new Lvac<T>();
		Lista<T> l1=new Lvac<T>();
		Lista<T> l2=this;
		boolean encontrado=false;
		while ((l2 instanceof Cons)&&!encontrado) {
			if (k==l2.obtRango()) encontrado=true;
			else {
				l0=l0.inserta(l2.obtRango(),l2.obtObjeto());
				l2=l2.obtLista();
			}
		}
		if (encontrado) {
			l0=l0.invOrden();
			l1=l2.obtLista();
			if (l1 instanceof Cons) 
				l2=l1.concatenar(l0,l1);
			else l2=l0;
		}
		else l2=this;
		return(l2);
		}
	
/**Esta funcion permite obtener el rango de un objeto especifico de la lista
 * PRE:ingresa un objeto t
 * POST:devuleve el rango del objeto t que ingreso*/
	public int buscar(T t) {
		Lista<T> l=this;
		while ((l instanceof Cons)&&(l.obtObjeto()!=t)) {
			l=l.obtLista();
		}
		return(l.obtRango());
	} 
	
/**Esta funcion permite saber si una lista tieneo no tiene elementos
 * PRE:recibe unaa lista
 * POSt: retorna true si la lista no tiene elementos y false si tiene almenos un elemento*/
	public boolean esVacia() {
		return false;
	}
	
/**Esta funcion permite obtener de una lista, el objeto que se encuantra en la cabeza de la misma
 * PRE:ingresa una lista
 * POST:retorna el objeto que esta en la cabeza de la lista*/	
	public T obtObjeto() {
		return this.x;
	}
	
/**Esta funcion permite obtener de una lista, una lista que representa la cola del elemento que 
 * se encuantra en la cabeza de la misma
 * PRE:ingresa una lista
 * POST:devuleve la cola de la lista que ingreso*/	
	public Lista<T> obtLista() {
		return this.y;
	}
	
/**Esta funcion permite obtener de una lista, el rangodel objeto que se encuantra en la cabeza 
 * de la misma
 * PRE:ingresa una lista
 * POST:devulelve el rango del elemento que eesta en la cabeza de la lista*/
	public int obtRango() {
		return this.k;	
	}
	
/**Esta funcion permite concatenar dos listas en una sola
 * PRE:recibe dos listas
 * POST:retorna un alista con las dos listas unidas con el mismo orden*/
	public Lista<T> concatenar(Lista<T> lp,Lista<T> ls) {
		Lista<T> lap=lp;
		Lista<T> las=ls;
		Lista<T> lcon=new Lvac<T>();
		while (lap instanceof Cons) {
			lcon=lcon.inserta(lap.obtRango(),lap.obtObjeto());
			lap=lap.obtLista();
		}
		while (lcon instanceof Cons) {
			las=las.inserta(lcon.obtRango(),lcon.obtObjeto());
			lcon=lcon.obtLista();
		}
		return(las);
	}
	
/**Esta funcion permite escribir a traves de consola los elementos de la lista
 * PRE:ingresa una lista
 * POST:escribe en consola los elementos de la lista*/	
	public void escribir() {
		Lista<T> l=this;
		while (l instanceof Cons) {
			System.out.println(l.obtObjeto());
			l=l.obtLista();
		}
	}
		
/**Esta funcion permite invertir el orden de los objetos de una lista
 * PRE:ingresa una lista
 * POST:retorna una lista pero con el orden de los elementos invertidos*/
	public Lista<T> invOrden() {
		Lista<T> l=this;
		Lista<T> l0=new Lvac<T>();
		while (l instanceof Cons) {
			l0=l0.inserta(l.obtRango(),l.obtObjeto());
			l=l.obtLista();
		}
		return(l0);
	}
	
}