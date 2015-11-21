class ArbolMax extends ArbolDeJuego {
	
	Tablero t;
	int k;
	ValCasilla jug;
	Lista<ArbolDeJuego> hijos;
	
	/**Funcion que construye un objeto del tipo con una lista vacia de hijos*/
	public ArbolMax (Tablero t,int k, ValCasilla jug) {
		this.t = t;
		this.k = k;
		this.jug = jug;
		hijos = new Lvac<ArbolDeJuego>();
	}
	
	/**Muestra el tablero que va asociado a un ArbolDeJuego
	 * PRE: recibe un objeto del tipo ArbolDeJuego
	 * POST: retorna el tablero del modelo de representacion del parametro de entrada*/
	public Tablero obtTablero(){
		return (this.t);
	}
	
	/**Funcion auxiliar que permite el acceso a la lista de hijos de un ArbolDeJuego
	 * PRE:recibe un objeto del tipo ArbolDeJuego
	 * POST: retorna la lista de hijos del modelo de representacion del objeto de entrada.*/
	public Lista<ArbolDeJuego> obtListaHijos(){
		return(this.hijos);
	}
	
	/**Procedimiento que agrega a la lista de hijos, los subarboles expandidos hasta
	 *el nivel dado por el entero k del modelo de representacion de un ArbolDeJuego
	 *PRE: recibe un objeto del tipo ArbolDeJuego
	 *POST: modifica la lista asociada al ArbolDeJuego de entrada agregandole los arboles
	 *formados por los hijos directos del tablero asociado al parametro de entrada y sus 
	 *respectivas listas de hijos que se forman llamando recursivamente a este procedimiento.*/

	public void expandir(){
		int i = 0;
		int j = 0;
		Tablero tAuxiliar1 = new Tablero();
		Tablero tAuxiliar2 = new Tablero();
		ValCasilla oponente = this.jug.oponente();
		Lista<ArbolDeJuego> y;
		boolean hayTableroEquivalente;
		
		if (this.k>0){
			while (i<3){
				while (j<3){
					if (this.t.tab[i][j].esVacia()){
						tAuxiliar1 = this.t.colocarFicha(this.t,this.jug,i,j);
						y = this.hijos;
						hayTableroEquivalente = false;
						while ((y instanceof Cons)&&!hayTableroEquivalente){
							tAuxiliar2 = y.obtObjeto().obtTablero();
							if (tAuxiliar1.equivalenteA(tAuxiliar2)) hayTableroEquivalente = true;
							y = y.obtLista();
						}
						if (!hayTableroEquivalente){

							ArbolDeJuego hijo= new ArbolMin(tAuxiliar1,this.k-1,oponente);
							hijo.expandir();
							this.hijos = this.hijos.insertaEnOrden(tAuxiliar1.beneficio(this.jug),hijo);
						}
					}
					j++;
				}
				j = 0;
				i++;
			}
		}
	}
	

	/**Expande un arbol hasta el segundo nivel de profundidad que es lo unico que necesita, y a partir de
	 *selecciona el tablero que corresponde a la jugada que le traera mayor beneficio a la casilla 
	 *asociada al ArbolDeJuego
	 *PRE: recibe un objeto del tipo ArbolDeJuego
	 *POST: retorna el tablero cuyo rango es el mayor de una lista de tableros que representan las 
	 *opciones de jugada existentes*/
	public Tablero decisionSobreTablero(){
		Tablero decision = new Tablero();
		ArbolDeJuego ArbolDecision= new ArbolMax(this.t,2,this.jug);
		ArbolDecision.expandir();
		Lista<ArbolDeJuego> Hijos = ArbolDecision.obtListaHijos(); 
		Lista<Tablero> opciones = new Lvac<Tablero>();
		
		while (Hijos instanceof Cons){
			Lista<ArbolDeJuego> subHijos = Hijos.obtObjeto().obtListaHijos();
			int minimoRangoSubHijos = subHijos.obtRango();
			opciones = opciones.insertaEnOrden(minimoRangoSubHijos, Hijos.obtObjeto().obtTablero());
			Hijos = Hijos.obtLista();
		}
		
		while (opciones instanceof Cons){
			decision = opciones.obtObjeto();
			opciones = opciones.obtLista();
		}
		return (decision);
	}
	
	/**Aplica la funcion tableroDecision y luego compara el tablero retornado con el tablero
	asociado al ArbolDeJuego para chequear cual es la posicion del elemento que esta en
	el tablero retornado y no esta en el del ArbolDeJuego.
	PRE: recibe un objeto del tipo ArbolDeJuego
	POST: retorna un arreglo de 2 enteros que representan la posicion del unico elemento que
	esta en el tableroDecision y no el tablero del ArbolDeJuego, es decir, la posicion donde
	se debe efectuar la jugada.*/ 
	
	public int[] jugadaOpt(){
		Tablero tableroDecision = this.decisionSobreTablero();
		int i = 0;
		int j = 0;
		boolean coincidenCasillas = true;
		while (i<3&&coincidenCasillas){
			while (j<3&&coincidenCasillas){
				if (tableroDecision.tab[i][j]!=this.t.tab[i][j]) coincidenCasillas = false;
				j++;
			}
			j = 0;
			i++;
		}
		int[] posicion = {i,j};
		return (posicion);
	}
}