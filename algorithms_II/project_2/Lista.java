/** Se agrego una funcion obtObjeto que retorna el elemento que esta 
 * en la primera posicion de la lista, y que se puede obtener al 
 * momento de crear una instancia de la misma.Esto con el fin de utilizar
 * este elemnto en alguna otra funcion que olo necesite.
 * De igual manera se creo un funcion obtLista, la cual retorna 
 * la cola de la lista que fue instanciada en algun momento. Esto se
 * hace con el fin de utilizar la lista en ciclos y realizar iteraciones
 * con la misma.
 * La funcion obtRango permite conocer el rango de un elemento especifico
 * dentro de la lista.
 * Y por ultimo se creo la funcion concatenar la cual permite, de alguna manera,
 * unir dos listas en una nueva lista, sin perder el orden de las listas
 * originales.*/

public abstract class Lista<T> {
	
	abstract public T obtener(int k);
	/**fun obtener: rango x lista -> T
	 * 	obtener=null
	 * 	obtener= x | obtener(k,y)*/
	
	abstract public int length();
	/**fun length: lista -> int
	 * 	length= 0
	 * 	length= length(y)+1*/
	
	abstract public Lista<T> inserta(int k,T t);
	/**fun inserta: rango x elemento x lista -> lista
	 *  inserta= new Cons(x,Lvac,k)
	 *  inserta=inserta(k,t,y)*/
	
	abstract public Lista<T> insertaEnOrden(int k,T t);
	/**fun inserta: rango x elemento x lista -> lista
	 *  inserta= new Cons(x,Lvac,k)
	 *  inserta=inserta(k,t,y)*/
	
	abstract public Lista<T> elimina(int k);
	/**fun elimina:rango x lista -> lista
	 *  elimina=null
	 *  elimina=elimina(k,y)*/
	
	abstract public int buscar(T t);
	/**fun buscar:elemento -> int
	 * 	buscar=null
	 * 	buscar=t.obtRango()*/
	
	abstract public boolean esVacia();
	/**fun esVacia: lista -> boolean
	 * 	esVacia= true
	 * 	esVacia= false*/
	
	abstract public T obtObjeto();
	/**fun obtObjeto: lista -> T
	 * obtObjeto= null
	 * obtObjeto= x*/
	
	abstract public Lista<T> obtLista();
	/**fun obtLista: lista -> lista
	 * 	obtLista= null
	 *  obtLista= y*/
	
	abstract public int obtRango();
	/**fun obtRango: lista -> int
	 * 	obtRango: null
	 *  obtRango: k*/
	
	abstract public Lista<T> concatenar(Lista<T>lp,Lista<T> ls);
	/**fun concatenar:lista x lista -> lista
	 * 	concatenar= Lvac
	 * 	concatenar= l1 + l2*/
	
	abstract public void escribir();
	
	abstract public Lista<T> invOrden();
	
}