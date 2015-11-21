/**Se agrego una funcion lineasParaCompletar cuyos parametros de entrada son
 * del tipo Tablero y ValCasilla. Esta funcion se encarga de contar el numero de 
 * lineas que podrian completarse en el tablero con la casilla dada y se creo para
 * poder utilizarla luego en la funcion de evaluacion estatica beneficio
 * 
 * La funcion de evaluacion estatica se denomino beneficio. Recibe como parametros
 * de entrada una variable del tipo Tablero y otra del tipo ValCasilla y se encarga
 * de calcular un numero entero equivalente al numero de lineas que se pueden 
 * completar con la marca de la casilla introducida menos el numero de lineas que 
 * se pueden completar con la marca de la casilla oponente*/

abstract class ArbolDeJuego {
	
	abstract public Tablero obtTablero();
	/**fun obtTablero: ArbolDeJuego -> Tablero
	 * ArbolMin.obtTablero() = this.t
	 * ArbolMax.obtTablero() = this.t*/
	
	abstract public Lista<ArbolDeJuego> obtListaHijos();
	/**fun obtListaHijos: ArbolDeJuego -> Lista<ArbolDeJuego>
	 * * ArbolMin.obtTablero() = this.hijos
	 * ArbolMax.obtTablero() = this.hijos*/
	
	abstract public void expandir ();
	/**proc expandir: ArbolDeJuego x int x ValCasilla -> ArbolDeJuego*/
	
	abstract public Tablero decisionSobreTablero();
	/**funcion decisionSobreTablero: ArbolDeJuego -> Tablero
	 * ArbolMin.decisionSobreTablero()= Tablero de minimo rango
	 * ArbolMax.decisionSobreTablero() = Tablero de maximo rango*/
	
	abstract public int[] jugadaOpt();
	/**funcion jugadaOpt: ArbolDeJuego -> int[]
	 * ArbolMin.jugadaOpt() = jugada que reduce al maximo la posibilidad de 
	 * ganar de la ficha contraria a aquella asociada al ArbolDeJuego
	 * ArbolMax.jugadaOpt() = jugada que aumenta al maximo la posibilidad de ganar
	 * de la ficha asociada al AbrolDeJuego*/
	
}