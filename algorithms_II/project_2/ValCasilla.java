/**Se agrego una funcion oponente que devuelve la ValCasilla
 * contraria a aquella con la que se este trabajando.*/

public abstract class ValCasilla {
	
	abstract public ValCasilla inserta(String v);
	/**fun inserta:String -> ValCasilla
	 * inserta= ValX| ValO
	 * inserta=null
	 * inserta=null*/
	
	abstract public ValCasilla elimina();
	/**fun elimina: ValCasilla -> ValCasilla
	 * elimina = null
	 * elimina = ValB
	 * elimina = ValB*/
	
	abstract public boolean esVacia();
	/**fun esVacia: ValCasilla -> boolean
	 * esVacia = true
	 * esVacia = false
	 * esVacia = false */
	
	abstract public String obtener();
	/**fun obtener: ValCasilla -> String
	 * obtener = "  "
	 * obtener = "X"
	 * obtener = "O"*/
	
	abstract public ValCasilla oponente();
	/**fun oponente: ValCasilla -> ValCasilla
	 * oponente = ValB
	 * oponente = ValO
	 * oponente = ValX*/
	
}